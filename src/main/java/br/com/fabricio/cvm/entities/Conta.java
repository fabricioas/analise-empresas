package br.com.fabricio.cvm.entities;

import java.math.BigDecimal;

public class Conta {
	private String idDemonstrativo;
	private String codigoConta;
	private String descricaoConta;
	private BigDecimal vlConta;
	
	public Conta(LancamentoDRE lancamento) {
		super();
		codigoConta = lancamento.getCodigoConta();
		descricaoConta = lancamento.getDescConta();
		vlConta = new BigDecimal(lancamento.getVlConta());
	}

	public String getIdDemonstrativo() {
		return idDemonstrativo;
	}

	public void setIdDemonstrativo(String idDemonstrativo) {
		this.idDemonstrativo = idDemonstrativo;
	}

	public String getCodigoConta() {
		return codigoConta;
	}

	public void setCodigoConta(String codigoConta) {
		this.codigoConta = codigoConta;
	}

	public String getDescricaoConta() {
		return descricaoConta;
	}

	public void setDescricaoConta(String descricaoConta) {
		this.descricaoConta = descricaoConta;
	}

	public BigDecimal getVlConta() {
		return vlConta;
	}

	public void setVlConta(BigDecimal vlConta) {
		this.vlConta = vlConta;
	}

}
