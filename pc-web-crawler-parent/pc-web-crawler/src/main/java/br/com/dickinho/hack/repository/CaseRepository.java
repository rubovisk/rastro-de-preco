package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Case;

public interface CaseRepository extends MongoRepository<Case, String> {

}
