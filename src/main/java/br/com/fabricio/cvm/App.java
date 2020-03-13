package br.com.fabricio.cvm;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		
		List<String> linhas = Files.readAllLines(Paths.get("./arquivos/itr_cia_aberta_2011.csv"),StandardCharsets.ISO_8859_1);
		
		System.out.println(linhas.stream().count());
		
		System.out.println(linhas.stream().distinct());
		
	}
}
