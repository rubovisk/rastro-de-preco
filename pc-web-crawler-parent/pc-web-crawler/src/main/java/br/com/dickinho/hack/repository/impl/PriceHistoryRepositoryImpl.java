package br.com.dickinho.hack.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.dickinho.hack.model.PriceHistory;
import br.com.dickinho.hack.repository.PriceHistoryCustomizedRepository;

@Repository
public class PriceHistoryRepositoryImpl<T,string> implements PriceHistoryCustomizedRepository<T,string> {
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<PriceHistory> findByName(string tipo) {
		Query qry = new Query();
		Criteria criteria = Criteria.where("tipoProduto").is(tipo);
		qry.with(Sort.by(Sort.Direction.ASC, "preco"));
		qry.addCriteria(criteria);
		return mongoTemplate.find(qry, PriceHistory.class);
	}
	
	
	public List<String> findAllDates() {
		return mongoTemplate.findDistinct("dataRegistro",PriceHistory.class,String.class);
	}

	/*@Override
	public <S extends T> S save(S entity) {
		PriceHistory ph = (PriceHistory) entity;
		mongoTemplate.save(ph);
		return (S)ph;
	}*/


}
