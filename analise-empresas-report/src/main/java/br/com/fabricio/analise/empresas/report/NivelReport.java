package br.com.fabricio.analise.empresas.report;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NivelReport {

	private Map<Integer, Pattern> niveis = new HashMap<>();

	private NivelReport() {
		super();
		niveis.put(0,Pattern.compile("^[0-9]{1}$"));
		niveis.put(1,Pattern.compile("^[0-9]{1}\\.[0-9]{2}$"));
		niveis.put(2,Pattern.compile("^[0-9]{1}\\.[0-9]{2}\\.[0-9]{2}$"));
		niveis.put(3,Pattern.compile("^[0-9]{1}\\.[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$"));
	}
	
	public static Pattern fitro(Integer nivel) {
		return new NivelReport().niveis.get(nivel);
	}

}
