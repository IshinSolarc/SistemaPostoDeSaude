package br.edu.utfpr.td.tsi.postosaude.service;

import java.util.List;

import org.bson.types.ObjectId;

import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;

public interface MedicoService {
	
	public List<Medico> buscarMedicoPorNome(String nome);
	
	public List<Medico> buscarMedicoPorEspecialidade(String especialidade);
	
	public Medico buscarMedicoPorIdMedico(String idMedico);
	
	public Medico buscarMedicoPorId(ObjectId id);
	
	public void inserirMedico(Medico medico);
	
	public void removerMedico(Medico medico);
	
	public void alterarMedico(Medico medico);
	
	public List<Medico> listarMedicos();
	
	public List<Medico> listarMedicosNome(String busca);
	
	public List<Medico> listarMedicosEspecialidade(String busca);
	
	public List<Medico> listarMedicosIdMedico(int busca);
	
	public List<Medico> listarMedicosCpf(String busca);

	List<Medico> listarMedicosIdMedico(String busca);
	
	List<Medico> listarMedicosPorIds(List<String> ids);
}
