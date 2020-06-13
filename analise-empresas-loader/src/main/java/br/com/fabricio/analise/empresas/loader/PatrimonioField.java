package br.com.fabricio.analise.empresas.loader;

import br.com.fabricio.analise.empresas.core.converters.BigDecimalConverter;
import br.com.fabricio.analise.empresas.core.converters.Converter;
import br.com.fabricio.analise.empresas.core.converters.DateConverter;
import br.com.fabricio.analise.empresas.core.converters.StringConverter;
import br.com.fabricio.analise.empresas.core.enuns.EnumField;

public enum PatrimonioField implements EnumField {
	CNPJ_CIA(new StringConverter()),
	DT_REFER(new DateConverter()),
	VERSAO(new StringConverter()),
	DENOM_CIA(new StringConverter()),
	CD_CVM(new StringConverter()),
	GRUPO_DFP(new StringConverter()),
	MOEDA(new StringConverter()),
	ESCALA_MOEDA(new StringConverter()),
	ORDEM_EXERC(new StringConverter()),
	DT_FIM_EXERC(new DateConverter()),
	CD_CONTA(new StringConverter()),
	DS_CONTA(new StringConverter()),
	VL_CONTA(new BigDecimalConverter());
	private Converter<?> converter;
	
	PatrimonioField(Converter<?> converter) {
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
