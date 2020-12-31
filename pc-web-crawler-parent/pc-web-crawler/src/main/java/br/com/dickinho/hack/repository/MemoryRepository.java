package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Memory;

public interface MemoryRepository extends MongoRepository<Memory, String> {

}
