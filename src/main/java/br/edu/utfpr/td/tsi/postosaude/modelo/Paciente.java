package br.edu.utfpr.td.tsi.postosaude.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Paciente {
    
	@Id
	ObjectId id;
	
	String nome;
	String dataNascimento;
	String cpf;
	String telefone;
	String email;
	Endereco endereco;
	
	
	public Paciente() {
		super();
	}
	
	public Paciente(String nome, String cpf, String dataNascimento, String telefone, String email, Endereco endereco) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
}
