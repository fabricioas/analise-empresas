package br.com.fabricio.analise.empresas.indicadores;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;

public class PrintIndicadorPVpa {
	private MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
			"analise-empresas");

	public void print(PrintStream out) {
		try {
			findIndicador()
			.stream()
			.filter( f -> f.getValor().signum() == 1)
			.filter( f -> f.getValor().compareTo(BigDecimal.ONE) < 0)
			.sorted((p1,p2) -> p1.getValor().abs().compareTo(p2.getValor().abs()))
			.forEachOrdered( p -> {
				out.print(p.getTicket());
				out.print(" - ");
				out.print(p.getCnpj().replaceAll("\\.", "").replaceAll("\\/", "").replaceAll("\\-", ""));
				out.print(" : ");
				out.print(p.getValor());
				out.print(" => ");
				out.print(p.getDataReferencia().format(DateTimeFormatter.ISO_DATE));
				out.print(", ");
				out.print(p.getQtdTotalAcoes());
				out.print(", ");
				out.print(p.getVlrPatrimonioLiquido());
				out.println();
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		out.flush();
		out.close();
	}

	private List<Indicador> findIndicador() {
		return mongoOps.findAll(Indicador.class,"IndicadorPVPA");
	}
}
