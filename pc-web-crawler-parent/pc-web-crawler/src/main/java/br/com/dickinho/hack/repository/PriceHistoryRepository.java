package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.PriceHistory;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String>, PriceHistoryCustomizedRepository<PriceHistory, String>{

}
