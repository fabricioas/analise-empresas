package br.com.fabricio.cvm.novo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import br.com.fabricio.cvm.app.ArquivoInfo;

public class Downloads {
	private String pathIn;
	private String pathOut;
	private String fileName;

	public static void main(String... args) {
		for (Integer ano = 2010; ano <= LocalDate.now().getYear(); ano++) {
			Downloads downloads = new Downloads("http://dados.cvm.gov.br/dados/CIA_ABERTA/DOC/ITR/DADOS/",
					"./app/pages/cvm/arquivos/", "itr_cia_aberta_" + ano + ".zip");
			downloads.download();
		}

	}

	public Downloads(String pathIn, String pathOut, String fileName) {
		super();
		this.pathIn = pathIn;
		this.pathOut = pathOut;
		this.fileName = fileName;
	}

	public void download() {
		try {
			URI uri = new URI(pathIn + fileName);
			URLConnection conn = uri.toURL().openConnection();
			conn.connect();
			if (fileName.endsWith(".zip")) {
				ArquivoInfo arquivoInfo = criarArquivoInfo(fileName, conn.getLastModified());
				ZipInputStream zip = new ZipInputStream(conn.getInputStream());
				descompataArquivoZip(zip);
				zip.closeEntry();
				zip.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não disponivel: " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArquivoInfo criarArquivoInfo(String fileName, Long lastModification) {
		ArquivoInfo arquivoInfo = new ArquivoInfo();
		arquivoInfo.setName(fileName);
		arquivoInfo.setDataUltimaModificacao(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModification), ZoneOffset.UTC));
		return arquivoInfo;
	}

	private void descompataArquivoZip(ZipInputStream zip) throws IOException {
		ZipEntry nextEntry = null;
		do {
			nextEntry = zip.getNextEntry();
			if (nextEntry != null) {
				saveFile(zip,nextEntry.getName());
			}
		} while (nextEntry != null);
	}

	private void saveFile(InputStream input, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(pathOut + fileName));
			new BufferedReader(new InputStreamReader(input, StandardCharsets.ISO_8859_1)).lines().forEachOrdered( writer::println );
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
