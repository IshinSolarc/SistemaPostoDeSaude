package br.edu.utfpr.td.tsi.postosaude.persistencia;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import br.edu.utfpr.td.tsi.postosaude.modelo.Contadores;

public interface ContadoresDao extends MongoRepository<Contadores, ObjectId> {
	
	public Contadores findByTipo(String tipo);

};