package br.edu.utfpr.td.tsi.postosaude.service;

import java.util.List;

import org.bson.types.ObjectId;

import br.edu.utfpr.td.tsi.postosaude.modelo.Consulta;

public interface ConsultaService {

	public void inserirConsulta(Consulta consulta);

	public void removerConsulta(Consulta consulta);

	public void alterarConsulta(Consulta consulta);

	public Consulta buscarConsulta(ObjectId id);

	public List<Consulta> listarConsultas();
	
	public List<Consulta> listarConsultas(List<String> ids);
	
	public List<Consulta> findAllByPacienteId(ObjectId id);
	
	public List<Consulta> findAllByMedicoId(ObjectId id);
	
	public void cancelarConsulta(Consulta consulta);
	
	public void concluirConsulta(Consulta consulta);

}
