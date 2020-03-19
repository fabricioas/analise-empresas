package br.com.fabricio.cvm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Utils {

	public static String generateHash(InputStream input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			while (input.read(b) > -1) {
				out.write(b);
			}
			return Base64.getEncoder().encodeToString(digest.digest(out.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String generateHash(byte[] input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return Base64.getEncoder().encodeToString(digest.digest(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String generateHash(List<String> input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			input.forEach( s -> digest.update(s.getBytes()));
			return Base64.getEncoder().encodeToString(digest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> stringToList(String csv){
		return Arrays.asList(csv.split("\r\n"));
	}
}
