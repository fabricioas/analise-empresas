package br.com.fabricio.analise.empresas.cotacoes;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;

import br.com.fabricio.analise.empresas.core.converters.DateConverter;
import br.com.fabricio.analise.empresas.core.entities.AtivoBolsa;

public class CotacoesLoader {
	private static final String GET = "http://cotacao.b3.com.br/mds/api/v1/DailyFluctuationHistory/";
	private static final DateConverter DATE_CONVERTER = new DateConverter();

	private MongoOperations mongoOps = new MongoTemplate(MongoClients.create("mongodb://localhost:27017"),
			"analise-empresas");
	private ObjectMapper mapper = new ObjectMapper();
	private static  Integer count=0;

	public void start(){
		deleteAll();
		handler();
	}

	public void handler(){
		List<Cotacao> list = findAtivos()
		.parallelStream()
		.map(this::mapCotacao)
		.filter( Objects::nonNull )
		.collect(Collectors.toList());
		saveAll(list);
	}

	private void deleteAll() {
		mongoOps.dropCollection(Cotacao.class);
	}

	private void saveAll(List<Cotacao> list) {
		BulkOperations operations = mongoOps.bulkOps(BulkMode.UNORDERED, Cotacao.class);
		operations.insert(list);
		operations.execute();
//		list.forEach( System.out::println );
	}
	
	private List<AtivoBolsa> findAtivos() {
		return mongoOps.findAll(AtivoBolsa.class);
	}
	
	private Cotacao mapCotacao( AtivoBolsa ativoBolsa ) {
		synchronized(count) {
			System.out.println(count++);
		}
		Map<String,Object> json = findCotacao(ativoBolsa.getTicket());
		Cotacao cotacao = new Cotacao();
		cotacao.setCnpj(ativoBolsa.getCnpj());
		cotacao.setTicket(ativoBolsa.getTicket());
		if( json != null ) {
			Map<String,Object> tradgFlr = (Map<String,Object>)json.get("TradgFlr");
			LocalDate date = DATE_CONVERTER.convertToObject((String)tradgFlr.get("date"));
			cotacao.setDataCotacao(date);
			Map<String,Object> scty = (Map<String,Object>)tradgFlr.get("scty");
			List<Map<String,Object>> list = (List<Map<String,Object>>)scty.get("lstQtn");
			if( !list.isEmpty() ) {
				Map<String,Object> lstCotacao = list.get(list.size()-1);
				cotacao.setValor(new BigDecimal(lstCotacao.get("closPric").toString()));
			}else {
				return null;
			}
		}
		return cotacao;
	}
	
	private Map<String,Object> findCotacao(String ticket){
		try {
			InputStream inputStream = new URL(GET+ticket).openStream();
			return mapper.readValue(inputStream, Map.class);		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
