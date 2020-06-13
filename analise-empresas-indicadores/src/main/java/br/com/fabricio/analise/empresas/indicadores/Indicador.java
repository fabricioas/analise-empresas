package br.com.fabricio.analise.empresas.indicadores;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Indicador {
	private String ticket;
	private String cnpj;
	private EnumTipoIndicador tipoIndicador;
	private BigDecimal valor;
	private BigDecimal vlrPatrimonioLiquido;
	private LocalDate dataReferencia;
	private Long qtdTotalAcoes;
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
	public EnumTipoIndicador getTipoIndicador() {
		return tipoIndicador;
	}
	public void setTipoIndicador(EnumTipoIndicador tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getVlrPatrimonioLiquido() {
		return vlrPatrimonioLiquido;
	}
	public void setVlrPatrimonioLiquido(BigDecimal vlrPatrimonioLiquido) {
		this.vlrPatrimonioLiquido = vlrPatrimonioLiquido;
	}
	public LocalDate getDataReferencia() {
		return dataReferencia;
	}
	public void setDataReferencia(LocalDate dataReferencia) {
		this.dataReferencia = dataReferencia;
	}
	public Long getQtdTotalAcoes() {
		return qtdTotalAcoes;
	}
	public void setQtdTotalAcoes(Long qtdTotalAcoes) {
		this.qtdTotalAcoes = qtdTotalAcoes;
	}
		
	
}
