package br.com.fabricio.analise.empresas.core.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fabricio.analise.empresas.core.EntityBasic;
import br.com.fabricio.analise.empresas.core.GenerateId;
import br.com.fabricio.analise.empresas.core.enuns.EnumEscalaMoeda;
import br.com.fabricio.analise.empresas.core.enuns.EnumRelatorios;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;

public class DemonstrativoFinanceiro implements EntityBasic {

	private String id;
	private String cnpj;
	private LocalDate dataReferencia;
	private Integer ano;
	private Integer trimestre;
	private String versao;
	private EnumRelatorios tipoRelatorio;
	private EnumTipoDemonstrativo tipoDemonstrativo;
	private String grupoDFP;
	private EnumEscalaMoeda escalaMoeda;
	private String ordemExercicio;
	private LocalDate dataInicioExercicio;
	private LocalDate dataFimExercicio;
	private String codigoConta;
	private String descricaoConta;
	private BigDecimal valorConta;

	public DemonstrativoFinanceiro() {
		super();
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public LocalDate getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(LocalDate dataReferencia) {
		this.dataReferencia = dataReferencia;
	}
	
	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public EnumRelatorios getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(EnumRelatorios tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public EnumTipoDemonstrativo getTipoDemonstrativo() {
		return tipoDemonstrativo;
	}

	public void setTipoDemonstrativo(EnumTipoDemonstrativo tipoDemonstrativo) {
		this.tipoDemonstrativo = tipoDemonstrativo;
	}

	public String getGrupoDFP() {
		return grupoDFP;
	}

	public void setGrupoDFP(String grupoDFP) {
		this.grupoDFP = grupoDFP;
	}

	public EnumEscalaMoeda getEscalaMoeda() {
		return escalaMoeda;
	}

	public void setEscalaMoeda(EnumEscalaMoeda escalaMoeda) {
		this.escalaMoeda = escalaMoeda;
	}

	public String getOrdemExercicio() {
		return ordemExercicio;
	}

	public void setOrdemExercicio(String ordemExercicio) {
		this.ordemExercicio = ordemExercicio;
	}

	public LocalDate getDataInicioExercicio() {
		return dataInicioExercicio;
	}

	public void setDataInicioExercicio(LocalDate dataInicioExercicio) {
		this.dataInicioExercicio = dataInicioExercicio;
	}

	public LocalDate getDataFimExercicio() {
		return dataFimExercicio;
	}

	public void setDataFimExercicio(LocalDate dataFimExercicio) {
		this.dataFimExercicio = dataFimExercicio;
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

	public BigDecimal getValorConta() {
		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta) {
		this.valorConta = valorConta;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
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
		DemonstrativoFinanceiro other = (DemonstrativoFinanceiro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void generateId() {
		this.id = GenerateId.start()
		.append(cnpj)
		.append(tipoRelatorio)
		.append(tipoDemonstrativo)
		.append(ano)
		.append(trimestre)
		.append(ordemExercicio)
		.append(codigoConta).build();
	}
}
