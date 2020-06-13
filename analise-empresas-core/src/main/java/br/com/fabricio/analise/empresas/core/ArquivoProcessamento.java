package br.com.fabricio.analise.empresas.core;

import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class ArquivoProcessamento {
	private final String fileName;
	private final EnumRelatorios tipoRelatorio;
	private final EnumTipoDemonstrativo tipoDemonstrativo;

	public ArquivoProcessamento(String fileName, EnumRelatorios tipoRelatorio,
			EnumTipoDemonstrativo tipoDemonstrativo) {
		super();
		this.fileName = fileName;
		this.tipoRelatorio = tipoRelatorio;
		this.tipoDemonstrativo = tipoDemonstrativo;
	}

	public String getFileName() {
		return fileName;
	}

	public EnumRelatorios getTipoRelatorio() {
		return tipoRelatorio;
	}

	public EnumTipoDemonstrativo getTipoDemonstrativo() {
		return tipoDemonstrativo;
	}

}