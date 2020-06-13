package br.com.fabricio.analise.empresas.core.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class DateConverter implements Converter<LocalDate> {
	private DateTimeFormatter pattern;
	
	public DateConverter(DateTimeFormatter pattern) {
		super();
		this.pattern = pattern;
	}

	public DateConverter() {
		this(DateTimeFormatter.ISO_DATE);
	}

	@Override
	public LocalDate convertToObject(String value) {
		if( StringUtils.isEmpty(value) ) {
			return null;
		}
		return LocalDate.parse(value, pattern);
	}

	
}
