package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.SSD;

public interface SsdRepository extends MongoRepository<SSD, String> {

}
