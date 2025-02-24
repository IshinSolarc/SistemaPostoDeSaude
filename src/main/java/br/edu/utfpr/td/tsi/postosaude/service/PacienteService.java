package br.edu.utfpr.td.tsi.postosaude.service;

import java.util.List;

import org.bson.types.ObjectId;

import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;

public interface PacienteService {
	
	public void inserirPaciente(Paciente paciente);
	
	public void removerPaciente(Paciente paciente);
	
	public void alterarPaciente(Paciente paciente);
	
    public List<Paciente> listarPacientes();
    
    public List<String> checarCampos(Paciente paciente);
    
    public Paciente buscarPacienteId(ObjectId id);
    
    public Paciente buscarPaciente(String nome);
    
    public Paciente buscarPacienteCpf(String cpf);

	List<Paciente> listarPacientesNome(String nome);
	
	List<Paciente> listarPacientesCpf(String cpf);
	
	List<Paciente> listarPacientesEmail(String email);
	
	List<Paciente> listarPacientesId(List<String> ids);
    
}
