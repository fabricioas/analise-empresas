package br.com.fabricio.analise.empresas.core;

import br.com.fabricio.analise.empresas.core.enuns.EnumField;

public class CsvLinhaParser {

	private String[] linha;

	public CsvLinhaParser(String linha) {
		super();
		this.linha = linha.split(";");
	}
	
	
	public <T> T getValue(EnumField field) {
		String value = linha[field.index()];
		return (T)field.converter().convertToObject(value);
	}
}
