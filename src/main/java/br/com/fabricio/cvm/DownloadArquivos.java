package br.com.fabricio.cvm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import br.com.fabricio.cvm.config.TransactonManager;
import br.com.fabricio.cvm.entities.ArquivoCVM;
import br.com.fabricio.cvm.entities.DadosImpotacao;
import br.com.fabricio.cvm.repositories.ArquivoCVMRepositorio;
import br.com.fabricio.cvm.repositories.DadosImportacaoRepositorio;

public class DownloadArquivos {
	private TransactonManager transactonManager = new TransactonManager();
	
	public void download(String path, String fileName) {
		transactonManager.begin();
		DadosImportacaoRepositorio repositorio = new DadosImportacaoRepositorio(transactonManager);
		try {
			URI uri = new URI(path + fileName);
			URLConnection conn = uri.toURL().openConnection();
			conn.connect();
			DadosImpotacao dadosImpotacao = criarDadosImportacao(path + fileName, conn.getLastModified());
			if (repositorio.isExists(dadosImpotacao)) {
				System.out.println("Arquivo modificado na data: " + dadosImpotacao.getDataUltimaModificacao().format(DateTimeFormatter.ISO_DATE_TIME));
			}else {
				if( fileName.endsWith(".zip") ) {
					ZipInputStream zip = new ZipInputStream(conn.getInputStream());
					lerArquivoZip(zip);
					zip.closeEntry();
					zip.close();
				}else if(fileName.endsWith(".csv")) {
					lerArquivoCsv(conn.getInputStream(),fileName, conn.getLastModified());
				}
			}
			repositorio.save(dadosImpotacao);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não disponivel: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactonManager.end();
	}

	private DadosImpotacao criarDadosImportacao(String url, Long lastModification) {
		DadosImpotacao dadosImpotacao = new DadosImpotacao();
		dadosImpotacao.setUrl(url);
		dadosImpotacao.setDataUltimaModificacao(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModification), ZoneOffset.UTC));
		return dadosImpotacao;
	}

	private void lerArquivoZip(ZipInputStream zip) throws IOException {
		ArquivoCVMRepositorio repositorio = new ArquivoCVMRepositorio(transactonManager);
		ZipEntry nextEntry = null;
		do {
			nextEntry = zip.getNextEntry();
			if (nextEntry != null) {
				ArquivoCVM arquivo = new ArquivoCVM();
				arquivo.setNome(nextEntry.getName());
				arquivo.setDataUltimaModificacao(
						LocalDateTime.ofInstant(nextEntry.getLastModifiedTime().toInstant(), ZoneOffset.UTC));
				String csv = getCsv(zip);
				arquivo.setHash(Utils.generateHash(csv.getBytes()));
				arquivo.setCsv(csv);
				if (repositorio.isExists(arquivo)){
					System.out.println("Arquivo já importado, Ultima Modificação:" + arquivo.getDataUltimaModificacao().format(DateTimeFormatter.ISO_DATE_TIME));
				}else{
					System.out.println("Processando arquivo:" + arquivo.getNome());
					repositorio.save(arquivo);
				}
			}
		} while (nextEntry != null);
	}
	
	private void lerArquivoCsv(InputStream input,String fileName, Long lastModifield) throws IOException {
		ArquivoCVMRepositorio repositorio = new ArquivoCVMRepositorio(transactonManager);
		ArquivoCVM arquivo = new ArquivoCVM();
		arquivo.setNome(fileName);
		arquivo.setDataUltimaModificacao(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModifield), ZoneOffset.UTC));
		String csv = getCsv(input);
		arquivo.setHash(Utils.generateHash(csv.getBytes()));
		arquivo.setCsv(csv);
		if (repositorio.isExists(arquivo)){
			System.out.println("Arquivo já importado, Ultima Modificação:" + arquivo.getDataUltimaModificacao().format(DateTimeFormatter.ISO_DATE_TIME));
		}else{
			System.out.println("Processando arquivo:" + arquivo.getNome());
			repositorio.save(arquivo);
		}
	}
	
	
	

	private String getCsv(InputStream input) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] buffer = new byte[1024];
		while (input.read(buffer) > -1) {
			sb.append(new String(buffer,StandardCharsets.ISO_8859_1));
		}
		return sb.toString();
	}

}
