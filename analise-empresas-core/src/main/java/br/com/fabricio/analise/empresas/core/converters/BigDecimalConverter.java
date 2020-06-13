package br.com.fabricio.analise.empresas.core.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

public class BigDecimalConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal convertToObject(String value) {
		if( StringUtils.isEmpty(value) ) {
			return null;
		}
		return new BigDecimal(value).setScale(5, RoundingMode.HALF_UP);
	}

}
