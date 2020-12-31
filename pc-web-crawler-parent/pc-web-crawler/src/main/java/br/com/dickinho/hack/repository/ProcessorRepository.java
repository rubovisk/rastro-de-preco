package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Processor;

public interface ProcessorRepository extends MongoRepository<Processor, String>{

}
