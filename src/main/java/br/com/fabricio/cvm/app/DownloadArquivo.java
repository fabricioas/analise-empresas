package br.com.fabricio.cvm.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadArquivo {
	private String path;
	private String fileName;
	private Consumer<? super ArquivoInfo> action;
	private Predicate<ArquivoInfo> filter;

	public DownloadArquivo(String path, String fileName) {
		super();
		this.path = path;
		this.fileName = fileName;
	}

	public void filter(Predicate<ArquivoInfo> filter) {
		this.filter = filter;
	}

	public void run(Consumer<? super ArquivoInfo> action) {
		this.action = action;
		try {
			URI uri = new URI(path + fileName);
			URLConnection conn = uri.toURL().openConnection();
			conn.connect();
			if (fileName.endsWith(".zip")) {
				ZipInputStream zip = new ZipInputStream(conn.getInputStream());
				lerArquivoZip(zip);
				zip.closeEntry();
				zip.close();
			} else if (fileName.endsWith(".csv")) {
				lerArquivoCsv(conn.getInputStream(), conn.getLastModified());
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
		arquivoInfo.setUrl(getURL());
		arquivoInfo.setDataUltimaModificacao(
				LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModification), ZoneOffset.UTC));
		return arquivoInfo;
	}

	private void lerArquivoZip(ZipInputStream zip) throws IOException {
		ZipEntry nextEntry = null;
		do {
			nextEntry = zip.getNextEntry();
			if (nextEntry != null) {
				ArquivoInfo arquivoInfo = criarArquivoInfo(nextEntry.getName(),
						nextEntry.getLastModifiedTime().toMillis());
				boolean test = (filter == null || filter.test(arquivoInfo));
				if( test ){
					arquivoInfo.setContent(getCsv(zip));
					System.out.println("Processando arquivo: " + arquivoInfo.getName());
					if (action != null) {
						action.accept(arquivoInfo);
					} else {
						System.out.println("Implementar o metodo Foreach");
					}
				}
			}
		} while (nextEntry != null);
	}

	private void lerArquivoCsv(InputStream input, Long lastModifield){
		ArquivoInfo arquivoInfo = criarArquivoInfo(fileName, lastModifield);
		System.out.println("Processando arquivo: " + arquivoInfo.getName());
		arquivoInfo.setContent(getCsv(input));
		action.accept(arquivoInfo);
	}

	private List<String> getCsv(InputStream input){
		return new BufferedReader(new InputStreamReader(input,
		          StandardCharsets.ISO_8859_1)).lines().collect(Collectors.toList());
	}

	private String getURL() {
		return path + fileName;
	}

}
