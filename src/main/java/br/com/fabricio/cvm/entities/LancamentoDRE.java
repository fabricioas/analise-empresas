package br.com.fabricio.cvm.entities;

import java.time.LocalDate;
import java.util.Objects;

public class LancamentoDRE {
	private String cnpj;
	private String dtReferencia;
	private Integer versao;
	private String nomeEmpresa;
	private String codigoCvm;
	private String grupoDfp;
	private String moeda;
	private String escalaMoeda;
	private String ordemExercicio;
	private String dtInicioExercicio;
	private String dtFimExercicio;
	private String codigoConta;
	private String descConta;
	private String vlConta;
	private Integer trimestre;
	private Integer ano;

	public LancamentoDRE(String... csv) {
		super();
		this.cnpj = csv[0];
		this.dtReferencia = csv[1];
		this.versao = Integer.valueOf(csv[2]);
		this.nomeEmpresa = csv[3];
		this.codigoCvm = csv[4];
		this.grupoDfp = csv[5];
		this.moeda = csv[6];
		this.escalaMoeda = csv[7];
		this.ordemExercicio = csv[8];
		this.dtInicioExercicio = csv[9];
		this.dtFimExercicio = csv[10];
		this.codigoConta = csv[11];
		this.descConta = csv[12];
		this.vlConta = csv[13];
		LocalDate data = LocalDate.parse(this.dtFimExercicio);
		this.trimestre = (data.getMonth().getValue()/3);
		this.ano = data.getYear(); 

	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getDtReferencia() {
		return dtReferencia;
	}

	public void setDtReferencia(String dtReferencia) {
		this.dtReferencia = dtReferencia;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCodigoCvm() {
		return codigoCvm;
	}

	public void setCodigoCvm(String codigoCvm) {
		this.codigoCvm = codigoCvm;
	}

	public String getGrupoDfp() {
		return grupoDfp;
	}

	public void setGrupoDfp(String grupoDfp) {
		this.grupoDfp = grupoDfp;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public String getEscalaMoeda() {
		return escalaMoeda;
	}

	public void setEscalaMoeda(String escalaMoeda) {
		this.escalaMoeda = escalaMoeda;
	}

	public String getOrdemExercicio() {
		return ordemExercicio;
	}

	public void setOrdemExercicio(String ordemExercicio) {
		this.ordemExercicio = ordemExercicio;
	}

	public String getDtInicioExercicio() {
		return dtInicioExercicio;
	}

	public void setDtInicioExercicio(String dtInicioExercicio) {
		this.dtInicioExercicio = dtInicioExercicio;
	}

	public String getDtFimExercicio() {
		return dtFimExercicio;
	}

	public void setDtFimExercicio(String dtFimExercicio) {
		this.dtFimExercicio = dtFimExercicio;
	}

	public String getCodigoConta() {
		return codigoConta;
	}

	public void setCodigoConta(String codigoConta) {
		this.codigoConta = codigoConta;
	}

	public String getDescConta() {
		return descConta;
	}

	public void setDescConta(String descConta) {
		this.descConta = descConta;
	}

	public String getVlConta() {
		return vlConta;
	}

	public void setVlConta(String vlConta) {
		this.vlConta = vlConta;
	}

	public Integer getKey() {
		return Objects.hash(cnpj, codigoCvm, codigoConta, ordemExercicio, trimestre, ano);
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LancamentoDRE [cnpj=");
		builder.append(cnpj);
		builder.append(", dtReferencia=");
		builder.append(dtReferencia);
		builder.append(", versao=");
		builder.append(versao);
		builder.append(", nomeEmpresa=");
		builder.append(nomeEmpresa);
		builder.append(", codigoCvm=");
		builder.append(codigoCvm);
		builder.append(", grupoDfp=");
		builder.append(grupoDfp);
		builder.append(", moeda=");
		builder.append(moeda);
		builder.append(", escalaMoeda=");
		builder.append(escalaMoeda);
		builder.append(", ordemExercicio=");
		builder.append(ordemExercicio);
		builder.append(", dtInicioExercicio=");
		builder.append(dtInicioExercicio);
		builder.append(", dtFimExercicio=");
		builder.append(dtFimExercicio);
		builder.append(", codigoConta=");
		builder.append(codigoConta);
		builder.append(", descConta=");
		builder.append(descConta);
		builder.append(", vlConta=");
		builder.append(vlConta);
		builder.append("]");
		return builder.toString();
	}

}
