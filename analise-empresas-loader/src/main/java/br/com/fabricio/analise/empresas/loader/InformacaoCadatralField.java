package br.com.fabricio.analise.empresas.loader;

import br.com.fabricio.analise.empresas.core.converters.Converter;
import br.com.fabricio.analise.empresas.core.converters.DateConverter;
import br.com.fabricio.analise.empresas.core.converters.StringConverter;
import br.com.fabricio.analise.empresas.core.enuns.EnumField;

public enum InformacaoCadatralField implements EnumField {
	CNPJ_CIA(new StringConverter()),
	DENOM_SOCIAL(new StringConverter()),
	DENOM_COMERC(new StringConverter()),
	DT_REG(new DateConverter()),
	DT_CONST(new DateConverter()),
	DT_CANCEL(new DateConverter()),
	MOTIVO_CANCEL(new StringConverter()),
	SIT(new StringConverter()),
	DT_INI_SIT(new DateConverter()),
	CD_CVM(new StringConverter()),
	SETOR_ATIV(new StringConverter()),
	TP_MERC(new StringConverter()),
	CATEG_REG(new StringConverter()),
	DT_INI_CATEG(new DateConverter()),
	SIT_EMISSOR(new StringConverter()),
	DT_INI_SIT_EMISSOR(new DateConverter()),
	TP_ENDER(new StringConverter()),
	LOGRADOURO(new StringConverter()),
	COMPL(new StringConverter()),
	BAIRRO(new StringConverter()),
	MUN(new StringConverter()),
	UF(new StringConverter()),
	PAIS(new StringConverter()),
	CEP(new StringConverter()),
	DDD_TEL(new StringConverter()),
	TEL(new StringConverter()),
	DDD_FAX(new StringConverter()),
	FAX(new StringConverter()),
	EMAIL(new StringConverter()),
	TP_RESP(new StringConverter()),
	RESP(new StringConverter()),
	DT_INI_RESP(new StringConverter()),
	LOGRADOURO_RESP(new StringConverter()),
	COMPL_RESP(new StringConverter()),
	BAIRRO_RESP(new StringConverter()),
	MUN_RESP(new StringConverter()),
	UF_RESP(new StringConverter()),
	PAIS_RESP(new StringConverter()),
	CEP_RESP(new StringConverter()),
	DDD_TEL_RESP(new StringConverter()),
	TEL_RESP(new StringConverter()),
	DDD_FAX_RESP(new StringConverter()),
	FAX_RESP(new StringConverter()),
	EMAIL_RESP(new StringConverter()),
	CNPJ_AUDITOR(new StringConverter()),
	AUDITOR(new StringConverter());

	private Converter<?> converter;
	
	InformacaoCadatralField(Converter<?> converter) {
		this.converter = converter;
	}

	@Override
	public Converter<?> converter() {
		return converter;
	}

	@Override
	public Integer index() {
		return ordinal();
	}	


}

