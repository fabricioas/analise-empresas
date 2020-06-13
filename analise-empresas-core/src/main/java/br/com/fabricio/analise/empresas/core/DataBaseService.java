package br.com.fabricio.analise.empresas.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClients;

public class DataBaseService {
	
	private MongoOperations mongoOps; 

	public DataBaseService(String host) {
		super();
//		localhost:27017
		mongoOps = new MongoTemplate(MongoClients.create("mongodb://"+host),
				"analise-empresas");
	}

	public <T> List<T> findAll(Class<T> clazz) {
		return mongoOps.findAll(clazz);
	}

	public <T> List<T> find(Map<String, Object> filters, Class<T> clazz) {
		Document document = new Document(filters);
		return mongoOps.find(new BasicQuery(document),clazz);
	}
	
	public <T> T findById(Object id, Class<T> clazz) {
		return mongoOps.findById(id,clazz);
	}

	public <T extends EntityBasic> void saveAll(List<? extends EntityBasic> list, Class<T> clazz) {
		List<Query> removes = list
			.stream()
			.map( i -> Query.query(Criteria.where("id").is(i.getId())))
			.collect(Collectors.toList());
		BulkOperations operations = mongoOps.bulkOps(BulkMode.UNORDERED, clazz);
		if( !removes.isEmpty() ) {
			operations.remove(removes);
			operations.execute();
		}
		if( !list.isEmpty() ) {
			operations.insert(list);
			operations.execute();
		}
	}
	
	public <T> void save(T object) {
		mongoOps.save(object);
	}

	public <T> void drop(Class<T> clazz) {
		mongoOps.dropCollection(clazz);
	}
}
