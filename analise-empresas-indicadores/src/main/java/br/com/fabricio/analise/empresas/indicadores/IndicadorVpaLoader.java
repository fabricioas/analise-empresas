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
import org.springframework.util.CollectionUtils;

import com.mongodb.client.MongoClients;

import br.com.fabricio.analise.empresas.core.entities.AtivoBolsa;
import br.com.fabricio.analise.empresas.core.entities.DemonstrativoFinanceiro;

public class IndicadorVpaLoader {
	private MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
			"analise-empresas");
	private static Integer count = 0;

	public static void main(String... strings) throws Exception {
		IndicadorVpaLoader indicador = new IndicadorVpaLoader();
		// indicador.teste();
		indicador.deleteAll();
		indicador.handler();
	}

	public void handler() {
		try {
			List<Indicador> list = findAtivos().parallelStream().map(this::toIndicadorVpa)
					.map(this::enrichPatrimoniLiquido).map(this::calculoIndicador)
					.filter(f -> !Objects.isNull(f.getVlrPatrimonioLiquido())).collect(Collectors.toList());
			saveAll(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Indicador toIndicadorVpa(AtivoBolsa ativoBolsa) {
		synchronized (count) {
			System.out.println(count++);
		}
		Indicador vpa = new Indicador();
		vpa.setTipoIndicador(EnumTipoIndicador.VPA);
		vpa.setCnpj(ativoBolsa.getCnpj());
		vpa.setTicket(ativoBolsa.getTicket());
		vpa.setQtdTotalAcoes(ativoBolsa.getQtdAcoesOrdinarias() + ativoBolsa.getQtdAcoesPreferenciais());
		return vpa;
	}

	private Indicador enrichPatrimoniLiquido(Indicador vpa) {
		DemonstrativoFinanceiro patrimoniLiquido = findPatrimonioLiquido(vpa.getCnpj());
		if (patrimoniLiquido != null) {
			vpa.setDataReferencia(patrimoniLiquido.getDataReferencia());
			vpa.setVlrPatrimonioLiquido(patrimoniLiquido.getValorConta());
		}
		return vpa;
	}

	private Indicador calculoIndicador(Indicador vpa) {
		if (vpa.getVlrPatrimonioLiquido() != null) {
			vpa.setValor(
					vpa.getVlrPatrimonioLiquido().divide(new BigDecimal(vpa.getQtdTotalAcoes()), 3, RoundingMode.UP));
		}
		return vpa;
	}

	private List<AtivoBolsa> findAtivos() {
		return mongoOps.findAll(AtivoBolsa.class);
	}

	private DemonstrativoFinanceiro findPatrimonioLiquido(String cnpj) {
		Document doc = new Document("cnpj", cnpj);
		doc.append("descricaoConta", "Patrimônio Líquido");
		List<DemonstrativoFinanceiro> list = mongoOps.find(new BasicQuery(doc), DemonstrativoFinanceiro.class);
		if (!CollectionUtils.isEmpty(list)) {
			return list.stream().max((a, b) -> a.getDataReferencia().compareTo(b.getDataReferencia())).orElse(null);
		}
		return null;
	}

	public void deleteAll() {
		mongoOps.dropCollection("IndicadorVPA");
	}

	private void saveAll(List<Indicador> list) {
		if (!CollectionUtils.isEmpty(list)) {
			BulkOperations operations = mongoOps.bulkOps(BulkMode.UNORDERED, "IndicadorVPA");
			operations.insert(list);
			operations.execute();
		}
	}

}
