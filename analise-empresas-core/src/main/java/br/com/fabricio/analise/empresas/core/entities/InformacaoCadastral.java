package br.com.fabricio.analise.empresas.core.entities;

import java.time.LocalDate;

import br.com.fabricio.analise.empresas.core.EntityBasic;
import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;

public class InformacaoCadastral implements EntityBasic {
	private String id;
	private String nomeComercial;
	private LocalDate dataRegistro;
	private String situcao;
	private EnumRelatorios tipoRelatorio;
	private String codigoCvm;
	private String setorAtividade;
	private String tipoMercado;
	private String categoriaRegistro;
	private String municipio;
	private String uf;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNomeComercial() {
		return nomeComercial;
	}

	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getSitucao() {
		return situcao;
	}

	public void setSitucao(String situcao) {
		this.situcao = situcao;
	}

	public EnumRelatorios getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(EnumRelatorios tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public String getCodigoCvm() {
		return codigoCvm;
	}

	public void setCodigoCvm(String codigoCvm) {
		this.codigoCvm = codigoCvm;
	}

	public String getSetorAtividade() {
		return setorAtividade;
	}

	public void setSetorAtividade(String setorAtividade) {
		this.setorAtividade = setorAtividade;
	}

	public String getTipoMercado() {
		return tipoMercado;
	}

	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}

	public String getCategoriaRegistro() {
		return categoriaRegistro;
	}

	public void setCategoriaRegistro(String categoriaRegistro) {
		this.categoriaRegistro = categoriaRegistro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformacaoCadastral other = (InformacaoCadastral) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
