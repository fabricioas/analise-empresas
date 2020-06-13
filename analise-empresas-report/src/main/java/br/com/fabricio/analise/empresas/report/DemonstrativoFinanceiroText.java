package br.com.fabricio.analise.empresas.report;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.fabricio.analise.empresas.core.DataBaseService;
import br.com.fabricio.analise.empresas.core.Report;
import br.com.fabricio.analise.empresas.core.entities.DemonstrativoFinanceiro;
import br.com.fabricio.analise.empresas.core.enuns.EnumTipoDemonstrativo;
import br.com.fabricio.analise.empresas.core.enuns.ReportAlign;

public class DemonstrativoFinanceiroText {

	private DataBaseService dataBaseService = new DataBaseService("localhost:27017");

	public void print(Filtro filtro, String fileOut) {
		try (OutputStream outputStream = Files.newOutputStream(Paths.get(fileOut), StandardOpenOption.CREATE);) {
			PrintStream printStream = new PrintStream(outputStream);
			Report report = new Report(printStream, DemonstrativoFinanceiro.class);
			// report.addColumn("Tipo Relatorio", 20,"tipoRelatorio",ReportAlign.LEFT);
			report.addColumn("Ano", 5, "ano", ReportAlign.RIGTH);
			report.addColumn("Trimestre", 10, "trimestre", ReportAlign.RIGTH);
			report.addColumn("Codigo Conta", 15, "codigoConta", ReportAlign.LEFT);
			report.addColumn("Descrição da Conta", 70, "descricaoConta", ReportAlign.LEFT);
			report.addColumn("Valor", 15, "valorConta", ReportAlign.RIGTH);

			List<DemonstrativoFinanceiro> list = findDemonstrativo(filtro.getCnpj(), filtro.getTipoDemonstrativo())
					.stream().filter(f -> filterDemonstrativo(f, filtro.getNivel()))
					.sorted((a, b) -> a.getCodigoConta().compareTo(b.getCodigoConta()))
					.sorted((a, b) -> a.getAno().compareTo(b.getAno())).collect(Collectors.toList());
			report.setDataSouce(list);
			report.print();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Boolean filterDemonstrativo(DemonstrativoFinanceiro dre, Integer nivel) {
		return NivelReport.fitro(nivel).matcher(dre.getCodigoConta()).find() && dre.getTrimestre().equals(4);
	}

	public List<DemonstrativoFinanceiro> findDemonstrativo(String cnpj, EnumTipoDemonstrativo tipoDemonstrativo) {
		Map<String, Object> filter = new HashMap<>();
		filter.put("cnpj", cnpj);
		filter.put("tipoDemonstrativo", tipoDemonstrativo);
		return dataBaseService.find(filter, DemonstrativoFinanceiro.class);
	}
}
