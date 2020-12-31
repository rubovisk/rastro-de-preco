package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Cooler;

public interface CoolerRepository extends MongoRepository<Cooler, String>{

}
