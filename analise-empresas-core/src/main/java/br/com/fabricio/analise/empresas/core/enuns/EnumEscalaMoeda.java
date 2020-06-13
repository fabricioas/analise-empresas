package br.com.fabricio.analise.empresas.core.enuns;

import java.math.BigDecimal;

public enum EnumEscalaMoeda {

	UNIDADE(1), 
	MILHAR(1000), 
	MIL(1000);

	private BigDecimal escala;

	private EnumEscalaMoeda(Integer escala) {
		this.escala = new BigDecimal(escala);
	}

	public BigDecimal convert(BigDecimal valor) {
		return valor.multiply(escala);
	}
}
