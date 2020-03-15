package br.com.fabricio.cvm;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Demonstrativo {

	private String cnpj;
	private String nome;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private LocalDate dataReferencia;
	private Integer numeroTrimestre;
	private Integer ano;
	private List<Conta> contas = new LinkedList<>();

	public Integer getId() {
		return Objects.hash(cnpj, numeroTrimestre, ano);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public LocalDate getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(LocalDate dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public Integer getNumeroTrimestre() {
		return numeroTrimestre;
	}

	public void setNumeroTrimestre(Integer numeroTrimestre) {
		this.numeroTrimestre = numeroTrimestre;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

}
