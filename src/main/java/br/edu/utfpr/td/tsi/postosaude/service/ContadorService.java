package br.edu.utfpr.td.tsi.postosaude.service;

public interface ContadorService{
	
	public void criarContador(String tipo, int valor_inicial);
	
	public void checarContadores();
	
	public int proximoMedico();

	public int proximaConsulta();
	
}
