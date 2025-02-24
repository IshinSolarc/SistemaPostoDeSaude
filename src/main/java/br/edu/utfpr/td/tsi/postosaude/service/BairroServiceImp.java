package br.edu.utfpr.td.tsi.postosaude.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;
import br.edu.utfpr.td.tsi.postosaude.persistencia.BairroDao;

@Service
public class BairroServiceImp implements BairroService {
	@Autowired
	private BairroDao bairroDao;
	
	@Override
	public void inserirBairro(Bairro bairro) {
		bairroDao.save(bairro);
	}

	@Override
	public void removerBairro(Bairro bairro) {
		bairroDao.delete(bairro);

	}

	@Override
	public void alterarBairro(Bairro bairro) {
		bairroDao.save(bairro);
	}

	@Override
	public Bairro buscarBairro(String nome) {
		
		for (Bairro bairro : bairroDao.findAll()) {
			if (bairro.getNome().equals(nome)) {
				return bairro;
			}
		}
		
		return null;
	}
	
	@Override
	public Bairro buscarBairro(ObjectId id) {
		for (Bairro bairro : bairroDao.findAll()) {
			if (bairro.getId().equals(id)) {
				return bairro;
			}
		}

		return null;
	}

	@Override
	public List<Bairro> listarBairros() {
		return bairroDao.findAll();
	}
	
	@Override
	public Bairro findByNome(String nome) {
		return bairroDao.findByNome(nome);
	}
}
