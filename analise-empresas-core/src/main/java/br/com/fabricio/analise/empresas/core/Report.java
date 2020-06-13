package br.com.fabricio.analise.empresas.core;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.fabricio.analise.empresas.core.enuns.ReportAlign;

public class Report {

	private PrintStream out;
	private List<ReportColumn> columns = new LinkedList<>();
	private List<?> dataSource;
	private Class<?> clazz;

	public Report(PrintStream out,Class<?> clazz) {
		super();
		this.out = out;
		this.clazz = clazz;
	}

	public Report addColumn(String displayName, Integer size, String property,ReportAlign align) {
		Field field;
		try {
			field = clazz.getDeclaredField(property);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		columns.add(new ReportColumn(displayName, size, field, align));
		return this;
	}

	public Report setDataSouce(List<?> dataSource) {
		this.dataSource = dataSource;
		return this;
	}
	
	public void print() {
		out.println(columns.stream().map(this::writeColumn).collect(Collectors.joining("|")));
		dataSource.stream().forEachOrdered(i ->{
			out.println(columns.stream().map(c -> writeValue(c, i)).collect(Collectors.joining("|")));
		});
	}

	private String writeColumn(ReportColumn column) {
		return StringUtils.rightPad(" " + column.getDisplayName(), column.getSize(), ' ');
	}

	private String writeValue(ReportColumn column, Object dados) {
		try {
			Field field = column.getProperty();
			field.setAccessible(true);
			Object value = field.get(dados);
			field.setAccessible(false);
			if( ReportAlign.LEFT.equals(column.getAlign())) {
				return StringUtils.rightPad(value.toString(), column.getSize(), ' ');
			}else if( ReportAlign.RIGTH.equals(column.getAlign())) {
				return StringUtils.leftPad(value.toString(), column.getSize(), ' ');
			}else {
				return StringUtils.leftPad(value.toString(), column.getSize(), ' ');
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
