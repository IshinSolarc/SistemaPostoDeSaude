package br.edu.utfpr.td.tsi.postosaude.persistencia;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;

public interface PacienteDao extends MongoRepository<Paciente, ObjectId> {
	
	public Paciente findByNome(String nome);
	
	public void deleteByNome(String nome);
	
	public Paciente findByCpf(String cpf);
	
	public Paciente findByEmail(String email);
	
	public Paciente findByNomeContaining(String nome);
	
	public Paciente findByCpfContaining(String cpf);
	
	public List<Paciente> findByEmailContaining(String email);
	
	public List<Paciente> findAllByNomeContaining(String nome);
	
	public List<Paciente> findAllByCpfContaining(String cpf);
	
	public List<Paciente> findAllByEmailContaining(String email);
	

};