package br.edu.utfpr.td.tsi.postosaude.modelo;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bairro {
	
	@Id
	private ObjectId id;
	
	public String nome;

	public Bairro(ObjectId id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		
	}

	public ObjectId getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
