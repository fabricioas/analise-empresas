package br.com.fabricio.analise.empresas.indicadores;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cotacao {
	private String ticket;
	private String cnpj;
	private LocalDate dataCotacao;
	private BigDecimal valor;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public LocalDate getDataCotacao() {
		return dataCotacao;
	}

	public void setDataCotacao(LocalDate dataCotacao) {
		this.dataCotacao = dataCotacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cotacao [ticket=");
		builder.append(ticket);
		builder.append(", cnpj=");
		builder.append(cnpj);
		builder.append(", dataCotacao=");
		builder.append(dataCotacao);
		builder.append(", valor=");
		builder.append(valor);
		builder.append("]");
		return builder.toString();
	}
	
}
