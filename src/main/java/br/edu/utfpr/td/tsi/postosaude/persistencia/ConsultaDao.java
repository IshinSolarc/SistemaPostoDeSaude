package br.edu.utfpr.td.tsi.postosaude.persistencia;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.postosaude.modelo.Consulta;

public interface ConsultaDao extends MongoRepository<Consulta, ObjectId> {
	
	public List<Consulta> findAllByPacienteId(ObjectId id);
	
	public List<Consulta> findAllByMedicoId(ObjectId id);
	
    public Consulta findByPacienteIdAndSituacao(ObjectId id, String situacao);
    
    public Consulta findByMedicoIdAndDataAndHorario(ObjectId id, String data, String horario);

};