package br.com.dickinho.hack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dickinho.hack.model.Motherboard;

public interface MotherboardRepository extends MongoRepository<Motherboard, String>  {

}
