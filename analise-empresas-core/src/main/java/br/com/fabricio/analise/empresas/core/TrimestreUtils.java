package br.com.fabricio.analise.empresas.core;

import java.time.LocalDate;
import java.time.temporal.ValueRange;

public final class TrimestreUtils {
	
	private TrimestreUtils() {
		super();
	}

	public static Integer getTrimestre(LocalDate mes) {
		if( ValueRange.of(1, 3).isValidIntValue(mes.getMonthValue())) {
			return 1;
		}
		if( ValueRange.of(4, 6).isValidIntValue(mes.getMonthValue())) {
			return 2;
		}
		if( ValueRange.of(7, 9).isValidIntValue(mes.getMonthValue())) {
			return 3;
		}
		if( ValueRange.of(10, 12).isValidIntValue(mes.getMonthValue())) {
			return 4;
		}
		throw new RuntimeException("Mes informado não existe");

	}
}
