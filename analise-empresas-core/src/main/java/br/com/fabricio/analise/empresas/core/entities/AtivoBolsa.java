package br.com.fabricio.analise.empresas.core.entities;

import java.time.LocalDate;

public class AtivoBolsa {

	private String ticket;
	private String cnpj;
	private String codigoPregao;
	private String codigoCvm;
	private Long qtdAcoesOrdinarias;
	private Long qtdAcoesPreferenciais;
	private LocalDate dataAtualizacaoCapitalSocial;

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

	public String getCodigoPregao() {
		return codigoPregao;
	}

	public void setCodigoPregao(String codigoPregao) {
		this.codigoPregao = codigoPregao;
	}

	public String getCodigoCvm() {
		return codigoCvm;
	}

	public void setCodigoCvm(String codigoCvm) {
		this.codigoCvm = codigoCvm;
	}

	public Long getQtdAcoesOrdinarias() {
		return qtdAcoesOrdinarias;
	}

	public void setQtdAcoesOrdinarias(Long qtdAcoesOrdinarias) {
		this.qtdAcoesOrdinarias = qtdAcoesOrdinarias;
	}

	public Long getQtdAcoesPreferenciais() {
		return qtdAcoesPreferenciais;
	}

	public void setQtdAcoesPreferenciais(Long qtdAcoesPreferenciais) {
		this.qtdAcoesPreferenciais = qtdAcoesPreferenciais;
	}

	public LocalDate getDataAtualizacaoCapitalSocial() {
		return dataAtualizacaoCapitalSocial;
	}

	public void setDataAtualizacaoCapitalSocial(LocalDate dataAtualizacaoCapitalSocial) {
		this.dataAtualizacaoCapitalSocial = dataAtualizacaoCapitalSocial;
	}

}
