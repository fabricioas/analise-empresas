package br.com.fabricio.analise.empresas.core;

import java.lang.reflect.Field;

import br.com.fabricio.analise.empresas.core.enuns.ReportAlign;

public class ReportColumn {

	private final String displayName;
	private final Integer size;
	private final Field property;
	private final ReportAlign align;

	public ReportColumn(String displayName, Integer size, Field property, ReportAlign align) {
		super();
		this.displayName = displayName;
		this.size = size;
		this.property = property;
		this.align = align;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Integer getSize() {
		return size;
	}

	public Field getProperty() {
		return property;
	}

	public ReportAlign getAlign() {
		return align;
	}

}
