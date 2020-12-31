package br.com.dickinho.hack.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.dickinho.hack.model.ECommerce;
import br.com.dickinho.hack.repository.ECommerceRepository;

@Repository
public class ECommerceRepositoryImpl implements ECommerceRepository {
	@Autowired
	private MongoTemplate template;
	
	@Override
	public List<ECommerce> findByName(String name) {
		Query qry = new Query();
		Criteria criteria = Criteria.where("nome").is(name);
		qry.addCriteria(criteria);
		return template.find(qry, ECommerce.class);
	}

}
