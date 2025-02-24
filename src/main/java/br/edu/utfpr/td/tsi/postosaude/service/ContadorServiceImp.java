package br.edu.utfpr.td.tsi.postosaude.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.postosaude.modelo.Contadores;
import br.edu.utfpr.td.tsi.postosaude.persistencia.ContadoresDao;

@Service
public class ContadorServiceImp implements ContadorService {
	
	@Autowired
	private ContadoresDao contadoresDao;
	
	@Override
	public void criarContador(String tipo, int valor_inicial) {
		Contadores contador = new Contadores(tipo, valor_inicial);
		contadoresDao.save(contador);
	}
	
	@Override
	public void checarContadores() {
		if (contadoresDao.findByTipo("proxmedico") == null) {
			criarContador("proxmedico", 1000);
		}
		if (contadoresDao.findByTipo("proxconsulta") == null) {
			criarContador("proxconsulta", 1);
		}
	}
	
	@Override
	public int proximoMedico() {
		Contadores contador = contadoresDao.findByTipo("proxmedico");
		int valor = contador.novaAdicao();
		contadoresDao.save(contador);
		return valor;
	}
	
	@Override
	public int proximaConsulta() {
		Contadores contador = contadoresDao.findByTipo("proxconsulta");
		int valor = contador.novaAdicao();
		contadoresDao.save(contador);
		return valor;
	}
	
	

}
