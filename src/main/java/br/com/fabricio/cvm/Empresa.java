package br.com.fabricio.cvm;

import java.util.LinkedList;
import java.util.List;

public class Empresa {
	private String cnpj;
	private String dtReferencia;
	private Integer versao;
	private String nome;
	private String codigoCvm;
	private String tipoDocumento;
	private String idDocumento;
	private String dtRecebimento;
	private String linkDocumento;
	private List<Demonstrativo> demonstrativos = new LinkedList<>();
	
	public Empresa(String... csv) {
		super();
		this.cnpj = csv[0];
		this.dtReferencia = csv[1];
		this.versao = Integer.valueOf(csv[2]);
		this.nome = csv[3];
		this.codigoCvm = csv[4];
		this.tipoDocumento = csv[5];
		this.idDocumento = csv[6];
		this.dtRecebimento = csv[7];
		this.linkDocumento = csv[8];
	}

	public Empresa() {
		super();
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigoCvm() {
		return codigoCvm;
	}

	public void setCodigoCvm(String codigoCvm) {
		this.codigoCvm = codigoCvm;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getDtRecebimento() {
		return dtRecebimento;
	}

	public void setDtRecebimento(String dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}

	public String getLinkDocumento() {
		return linkDocumento;
	}

	public void setLinkDocumento(String linkDocumento) {
		this.linkDocumento = linkDocumento;
	}
	

	public List<Demonstrativo> getDemonstrativos() {
		return demonstrativos;
	}

	public void setDemonstrativos(List<Demonstrativo> demonstrativos) {
		this.demonstrativos = demonstrativos;
	}

	@Override
	public String toString() {
		return "Empresa [cnpj=" + cnpj + " dtReferencia=" + dtReferencia + " versao=" + versao + " nome=" + nome
				+ " codigoCvm=" + codigoCvm + " tipoDocumento=" + tipoDocumento + " idDocumento=" + idDocumento
				+ " dtRecebimento=" + dtRecebimento + " linkDocumento=" + linkDocumento + "]";
	}
}
