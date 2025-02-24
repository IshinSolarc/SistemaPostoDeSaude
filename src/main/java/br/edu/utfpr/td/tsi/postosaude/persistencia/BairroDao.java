package br.edu.utfpr.td.tsi.postosaude.persistencia;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;

public interface BairroDao extends MongoRepository<Bairro, ObjectId> {
	
	public Bairro findByNome(String nome);
	public void deleteByNome(String nome);
};