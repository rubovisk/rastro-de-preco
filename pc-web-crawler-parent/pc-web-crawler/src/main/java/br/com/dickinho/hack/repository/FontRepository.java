package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Font;

public interface FontRepository extends MongoRepository<Font, String> {

}
