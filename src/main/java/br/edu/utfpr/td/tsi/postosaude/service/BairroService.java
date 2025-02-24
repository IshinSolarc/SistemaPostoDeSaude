package br.edu.utfpr.td.tsi.postosaude.service;

import java.util.List;

import org.bson.types.ObjectId;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;

public interface BairroService {
	
	public void inserirBairro(Bairro bairro);
	
	public void removerBairro(Bairro bairro);
	
	public void alterarBairro(Bairro bairro);
	
	public Bairro buscarBairro(String nome);

	public List<Bairro> listarBairros();

	Bairro findByNome(String nome);
	
	Bairro buscarBairro(ObjectId id);
}
