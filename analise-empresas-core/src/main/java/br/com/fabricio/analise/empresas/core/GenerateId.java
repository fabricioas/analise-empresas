package br.com.fabricio.analise.empresas.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Base64;

public class GenerateId {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(out);

	private GenerateId() {
		super();
	}

	public static GenerateId start() {
		return new GenerateId();
	}
	
	public GenerateId append(Object value) {
		writer.print(value);
		return this;
	}
	
	public String build() {
		try {
			writer.close();
			out.close();
			return Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest(out.toByteArray()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
