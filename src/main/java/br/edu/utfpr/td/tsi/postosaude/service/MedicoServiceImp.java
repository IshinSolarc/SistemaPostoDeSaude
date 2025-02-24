package br.edu.utfpr.td.tsi.postosaude.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.interfaces.Check;
import br.com.safeguard.types.ParametroTipo;
import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;
import br.edu.utfpr.td.tsi.postosaude.persistencia.MedicoDao;

@Service
public class MedicoServiceImp implements MedicoService {
	
	@Autowired
	ContadorService contadorService;
	@Autowired
	MedicoDao medicoDao;
	
	@Override
	public List<Medico> buscarMedicoPorNome(String nome) {
		return medicoDao.findAllByNomeContaining(nome);
	}
	
	@Override
	public void inserirMedico(Medico medico) {
		
		if (medico.getNome().isEmpty() || medico.getCpf().isEmpty() || medico.getEspecialidade().isEmpty() || (medico.getBairro() == null) || medico.getTelefone().isEmpty()){
			throw new RuntimeException("Campos obrigatórios não preenchidos");
		}
		
		Check check = new SafeguardCheck();
		boolean cpfValido = check.elementOf(medico.getCpf(), ParametroTipo.CPF_FORMATADO).validate().hasError();
		if (cpfValido) {
			throw new RuntimeException("CPF inválido");
		}
		
		if (medicoDao.findByCpf(medico.getCpf()) != null) {
			throw new RuntimeException("CPF já cadastrado");
		}
		
		boolean telefoneInvalido = check.elementOf(medico.getTelefone(), ParametroTipo.TELEFONE).validate().hasError();
		if (telefoneInvalido) {
			throw new RuntimeException("Telefone inválido");
		}
		
		LocalDate DataAtual = LocalDate.now();
		
		boolean nascimentoInvalido = medico.getDataNascimento().isEmpty() || LocalDate.parse(medico.getDataNascimento()).compareTo(DataAtual) > 0;
		
		if (nascimentoInvalido) {
			throw new RuntimeException("Data de nascimento inválida");
		}
		
		medico.setIdMedico(contadorService.proximoMedico());
		medicoDao.save(medico);
	}
	
	@Override
	public void removerMedico(Medico medico) {
		medicoDao.delete(medico);
	}
	
	@Override
	public void alterarMedico(Medico medico) {
		medicoDao.save(medico);
	}
	
	@Override
	public Medico buscarMedicoPorIdMedico(String idMedico) {
		int id = Integer.parseInt(idMedico);
		return medicoDao.findByIdMedico(id);
	}
	
	@Override
	public Medico buscarMedicoPorId(ObjectId id) {
		return medicoDao.findById(id).get();
	}
	
	@Override
	public List<Medico> buscarMedicoPorEspecialidade(String especialidade) {
		return medicoDao.findAllByEspecialidadeContaining(especialidade);
	}
	
	@Override
	public List<Medico> listarMedicos() {
		return medicoDao.findAll();
	}

	@Override
	public List<Medico> listarMedicosNome(String busca) {
		
		return medicoDao.findAllByNomeContaining(busca);
    }
	
	@Override
	public List<Medico> listarMedicosEspecialidade(String busca) {	
		return medicoDao.findAllByEspecialidadeContaining(busca);
    }
	
	@Override
	public List<Medico> listarMedicosIdMedico(String busca) {
		int id = Integer.parseInt(busca);
		return medicoDao.findAllByIdMedicoContaining(id);
    }
	
	@Override
	public List<Medico> listarMedicosCpf(String busca) {
		return medicoDao.findAllByCpfContaining(busca);
	}

	@Override
	public List<Medico> listarMedicosIdMedico(int busca) {
		return medicoDao.findAllByIdMedicoContaining(busca);
	}
	
	@Override
	public List<Medico> listarMedicosPorIds(List<String> ids) {
		List<Medico> medicos = new ArrayList<>();
		for (String id : ids) {
			ObjectId idMedico = new ObjectId(id);
			medicos.add(medicoDao.findById(idMedico).get());
		}
		return medicos;
	}
}
