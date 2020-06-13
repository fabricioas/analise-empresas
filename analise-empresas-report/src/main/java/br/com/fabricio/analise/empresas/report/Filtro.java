package br.com.fabricio.analise.empresas.report;

import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class Filtro {

	private String cnpj;
	private Integer nivel;
	private EnumTipoDemonstrativo tipoDemonstrativo;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public EnumTipoDemonstrativo getTipoDemonstrativo() {
		return tipoDemonstrativo;
	}

	public void setTipoDemonstrativo(EnumTipoDemonstrativo tipoDemonstrativo) {
		this.tipoDemonstrativo = tipoDemonstrativo;
	}

}
