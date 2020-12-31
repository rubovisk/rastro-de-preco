package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.HD;

public interface HdRepository extends MongoRepository<HD, String> {

}
