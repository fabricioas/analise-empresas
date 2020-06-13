package br.com.fabricio.analise.empresas.core.converters;

public interface Converter<T> {

	T convertToObject( String value );
}
