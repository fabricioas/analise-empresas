package br.com.fabricio.analise.empresas.core.enuns;

import br.com.fabricio.analise.empresas.core.converters.Converter;

public interface EnumField {

	Converter<?> converter();
	Integer index();
}
