package br.com.dickinho.hack.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.PriceHistory;

public interface PriceHistoryCustomizedRepository<T, string>{
	List<PriceHistory> findByName(string tipo);
	List<String> findAllDates();
	/*<S extends T> S save(S entity);*/
}
