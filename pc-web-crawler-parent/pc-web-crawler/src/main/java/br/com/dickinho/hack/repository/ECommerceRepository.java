package br.com.dickinho.hack.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.ECommerce;

public interface ECommerceRepository {
	List<ECommerce> findByName(String name);
}
