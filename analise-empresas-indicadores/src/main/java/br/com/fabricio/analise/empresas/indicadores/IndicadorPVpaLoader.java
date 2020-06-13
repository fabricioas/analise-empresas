package br.com.fabricio.analise.empresas.indicadores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;

import com.mongodb.client.MongoClients;

public class IndicadorPVpaLoader {
	private MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
			"analise-empresas");
	private Integer count = 0;

	public void handler() {
		try {
			List<Indicador> list = findIndicadorVpa().parallelStream().map(this::toIndicador)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			saveAll(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Indicador toIndicador(Indicador vpa) {
		synchronized (count) {
			System.out.println(count++);
		}
		Cotacao cotacao = findCotacao(vpa.getTicket());
		if (cotacao != null) {
			Indicador indicador = new Indicador();
			indicador.setTipoIndicador(EnumTipoIndicador.PVPA);
			indicador.setCnpj(vpa.getCnpj());
			indicador.setTicket(vpa.getTicket());
			indicador.setDataReferencia(vpa.getDataReferencia());
			indicador.setQtdTotalAcoes(vpa.getQtdTotalAcoes());
			indicador.setVlrPatrimonioLiquido(vpa.getVlrPatrimonioLiquido());
			indicador.setValor(calculoIndicador(vpa, cotacao));
			return indicador;
		} else {
			return null;
		}
	}

	private BigDecimal calculoIndicador(Indicador vpa, Cotacao cotacao) {
		return cotacao.getValor().divide(vpa.getValor(), 4, RoundingMode.HALF_UP);
	}

	private List<Indicador> findIndicadorVpa() {
		return mongoOps.findAll(Indicador.class,"IndicadorVPA");
	}

	private Cotacao findCotacao(String ticket) {
		Document doc = new Document("ticket", ticket);
		return mongoOps.findOne(new BasicQuery(doc), Cotacao.class);
	}

	public void deleteAll() {
		mongoOps.dropCollection("IndicadorPVPA");
	}

	private void saveAll(List<Indicador> list) {
		BulkOperations operations = mongoOps.bulkOps(BulkMode.UNORDERED, "IndicadorPVPA");
		operations.insert(list);
		operations.execute();
	}

}
