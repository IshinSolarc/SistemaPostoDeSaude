package br.edu.utfpr.td.tsi.postosaude.modelo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Contadores {
	
	@Id
	ObjectId id;
	String tipo;
	int valor;
	

	public Contadores() {
		super();
	}
	
	public Contadores(String tipo, int valor) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public int novaAdicao() {
		return ++valor;
	}
	
	public int getValor() {
		return valor;
	}
}
