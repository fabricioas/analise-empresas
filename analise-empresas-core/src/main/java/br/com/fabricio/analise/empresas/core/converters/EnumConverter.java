package br.com.fabricio.analise.empresas.core.converters;

import org.apache.commons.lang3.EnumUtils;

public class EnumConverter<E extends Enum<E>> implements Converter<E> {

	private Class<E> enumClass;
	
	public EnumConverter(Class<E> enumClass) {
		super();
		this.enumClass = enumClass;
	}

	@Override
	public E convertToObject(String value) {
		return EnumUtils.getEnum(enumClass, value);
	}

	
}
