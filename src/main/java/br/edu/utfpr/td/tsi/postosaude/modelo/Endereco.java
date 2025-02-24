package br.edu.utfpr.td.tsi.postosaude.modelo;


public class Endereco {
	
	private String rua;
	private String numero;
	private String complemento;
	private Bairro bairro;

	public Endereco(String rua, String numero, String complemento, Bairro bairro) {
		super();
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		
	}

	public String getRua() {
		return rua;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

}
