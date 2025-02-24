package br.edu.utfpr.td.tsi.postosaude.modelo;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Consulta{
	
	@Id
	public ObjectId id;
	int idConsulta;
	String data;
	String horario;
	Medico medico;
	Paciente paciente;
	String situacao;
	String diagnostico;
	String receita;
	
	public Consulta() {
		super();
	}

	public Consulta(String data, String horario, Medico medico, Paciente paciente, String situacao) {
		super();
		this.data = data;
		this.horario = horario;
		this.medico = medico;
		this.paciente = paciente;
		this.situacao = situacao;
		
	}

	public int getIdConsulta() {
		return idConsulta;
	}

	public String getData() {
		return data;
	}

	public String getHorario() {
		return horario;
	}

	public Medico getMedico() {
		return medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setIdConsulta(int idConsulta) {
		this.idConsulta = idConsulta;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public void consultaRealizada() {
		this.situacao = "realizada";
	}
	
	public void consultaCancelada() {
		this.situacao = "cancelada";
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getDiagnostico() {
		return diagnostico;
	}
	
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	
	public String getReceita() {
		return receita;
	}
	
	public void setReceita(String receita) {
		this.receita = receita;
	}
}