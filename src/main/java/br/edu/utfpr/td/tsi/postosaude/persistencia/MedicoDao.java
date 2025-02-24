package br.edu.utfpr.td.tsi.postosaude.persistencia;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;

public interface MedicoDao extends MongoRepository<Medico, ObjectId> {

	public List<Medico> findAllByNomeContaining(String nome);
	
	public List<Medico> findAllByEspecialidadeContaining(String especialidade);
	
	public List<Medico> findAllByIdMedicoContaining(int idMedico);
	
	public Medico findByIdMedico(int idMedico);
	
	public Medico findByCpf(String cpf);

	public List<Medico> findAllByCpfContaining(String cpf);
	
};
