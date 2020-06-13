package br.com.fabricio.analise.empresas.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadFile {
	private static final Logger log = LoggerFactory.getLogger(DownloadFile.class);
	
	private String host;
	private String path;
	private String repositorio;
	private String fileName;
	private LocalDate lastUpdate;
	private LocalDateTime updateTime;
	private String extension;

	public DownloadFile(String host, String path, String repositorio, LocalDate lastUpdate) {
		super();
		this.host = host;
		this.path = path;
		this.repositorio = repositorio;
		this.lastUpdate = lastUpdate;
		if (lastUpdate == null) {
			this.lastUpdate = LocalDate.MIN;
		}
		this.fileName = getFileName(path);
		this.extension = getExtension(this.fileName);
	}

	public void download() {
		try {
			URLConnection connection = new URL(host + path).openConnection();
			updateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(connection.getLastModified()),
					ZoneOffset.ofHours(-3));
			if (lastUpdate.isBefore(updateTime.toLocalDate())) {
				if (!Files.exists(Paths.get(repositorio))) {
					Files.createDirectories(Paths.get(repositorio));
				}
				log.info("Baixando arquivo: {}{}",host,path);
				if (isZIP()) {
					downloadZIP(connection.getInputStream());
				} else if (isCSV()) {
					downloadCSV(connection.getInputStream());
				}
			}else {
				log.info("Arquivo '{}' já está na ultima versão",fileName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void downloadZIP(InputStream is) throws IOException, MalformedURLException {
		ZipInputStream zip = new ZipInputStream(is);
		ZipEntry zipEntry = zip.getNextEntry();
		while (zipEntry != null) {
			InputStreamReader isr = new InputStreamReader(zip, StandardCharsets.ISO_8859_1);
			BufferedReader buffer = new BufferedReader(isr);
			String fileNameOrigin = zipEntry.getName();
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(repositorio+"/"+ fileNameOrigin),
					StandardOpenOption.CREATE);
			buffer.lines().forEach(l -> {
				copy(writer, l);
			});
			writer.close();
			zipEntry = zip.getNextEntry();
		}
	}

	private void downloadCSV(InputStream is) throws IOException, MalformedURLException {
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.ISO_8859_1);
		BufferedReader buffer = new BufferedReader(isr);
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(repositorio+"/"+fileName), StandardOpenOption.CREATE);
		buffer.lines().forEach(l -> {
			copy(writer, l);
		});
		writer.close();
	}

	private String getExtension(String fileName) {
		Integer index = fileName.lastIndexOf('.');
		return fileName.substring(index + 1, fileName.length());
	}

	private String getFileName(String path) {
		Integer index = path.lastIndexOf('/');
		return path.substring(index + 1, path.length());
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	private void copy(BufferedWriter writer, String l) {
		try {
			writer.write(l);
			writer.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isZIP() {
		return "zip".equalsIgnoreCase(extension);
	}

	private boolean isCSV() {
		return "csv".equalsIgnoreCase(extension);
	}
}
