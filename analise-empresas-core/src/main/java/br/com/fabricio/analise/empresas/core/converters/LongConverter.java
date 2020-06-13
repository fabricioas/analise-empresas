package br.com.fabricio.analise.empresas.core.converters;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class LongConverter implements Converter<Long> {

	@Override
	public Long convertToObject(String value) {
		try {
			return (Long) NumberFormat.getInstance(new Locale("pt","br")).parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	
}
