package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Videocard;

public interface VideocardRepository extends MongoRepository<Videocard, String> {

}
