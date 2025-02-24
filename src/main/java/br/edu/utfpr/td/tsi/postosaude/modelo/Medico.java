package br.edu.utfpr.td.tsi.postosaude.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Medico {
	
	@Id
	ObjectId id;
	
	int idMedico;
	String nome;
	String cpf;
	String dataNascimento;
	String especialidade;
	String telefone;
	Bairro bairro;
	
	
	public Medico() {
		super();
	}
	
	public Medico(int idMedico, String nome, String cpf, String dataNascimento, String especialidade,
			String telefone, Bairro bairro) {
		super();
		this.idMedico = idMedico;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.especialidade = especialidade;
		this.telefone = telefone;
		this.bairro = bairro;
	}
	
	public int getId_medico() {
		return idMedico;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public String getEspecialidade() {
		return especialidade;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId_medico(int id_medico) {
		this.idMedico = id_medico;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Bairro getBairro() {
		return bairro;
	}
	
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public void setIdMedico(int proximoMedico) {
		this.idMedico = proximoMedico;
	}
	
	public int getIdMedico() {
		return idMedico;
	}
}
