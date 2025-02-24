package br.edu.utfpr.td.tsi.postosaude.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.lang.Boolean;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.safeguard.check.SafeguardCheck;
import br.com.safeguard.interfaces.Check;
import br.com.safeguard.types.ParametroTipo;
import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;
import br.edu.utfpr.td.tsi.postosaude.persistencia.PacienteDao;

@Service
public class PacienteServiceImp implements PacienteService {
	
	@Autowired
	private PacienteDao pacienteDao;
	
	@Override
	public void inserirPaciente(Paciente paciente) {
		if (pacienteDao.findByCpf(paciente.getCpf()) != null) {
            return;
        }
		
		pacienteDao.save(paciente);
    }
	
	@Override
	public void removerPaciente(Paciente paciente) {
		pacienteDao.delete(paciente);
	}
	
	@Override
	public void alterarPaciente(Paciente paciente) {
		pacienteDao.save(paciente);
	}
	@Override
	public List<Paciente> listarPacientes() {
		List<Paciente> pacientes = new java.util.ArrayList<>();
		for (Paciente paciente : pacienteDao.findAll()) {
			pacientes.add(paciente);
		}
		
		return pacientes;
	}
	
	@Override
	public List<Paciente> listarPacientesNome(String nome) {
		return pacienteDao.findAllByNomeContaining(nome);
	}
	
	@Override
	public List<Paciente> listarPacientesCpf(String cpf) {
		return pacienteDao.findAllByCpfContaining(cpf);
	}
	
	@Override
	public List<Paciente> listarPacientesEmail(String email) {
		return pacienteDao.findAllByEmailContaining(email);
	}
	
	
	@Override
	public List<String>checarCampos(Paciente paciente) {
		
		List<String> erros = new java.util.ArrayList<>();
		
		Check check = new SafeguardCheck();
		
		if (paciente.getNome().isEmpty() || paciente.getCpf().isEmpty() || paciente.getDataNascimento().isEmpty()
				|| paciente.getEmail().isEmpty() || paciente.getTelefone().isEmpty()
				|| paciente.getEndereco().getRua().isEmpty() || paciente.getEndereco().getNumero().isEmpty()
				|| paciente.getEndereco().getBairro().getNome().isEmpty()) {
			erros.add("Todos os campos devem ser preenchidos");
		}
		
		
	
		boolean CpfInvalido = check.elementOf(paciente.getCpf(), ParametroTipo.CPF_FORMATADO).validate().hasError();
		if (CpfInvalido) {
			erros.add("CPF invalido. Digite os numeros com pontos e traco");
		}
		
		
		boolean emailInvalido = check.elementOf(paciente.getEmail(), ParametroTipo.EMAIL).validate().hasError();
		if (emailInvalido && !paciente.getEmail().isEmpty()) {
			erros.add("Email invalido");
		}
		
		
		boolean telefoneInvalido = check.elementOf(paciente.getTelefone(), ParametroTipo.TELEFONE).validate().hasError();
		if (telefoneInvalido && !paciente.getTelefone().isEmpty()) {
            erros.add("Telefone invalido");
        }

		
		String dataAtual = LocalDate.now().toString();
		if ((paciente.getDataNascimento().compareTo(dataAtual) > 0) || (paciente.getDataNascimento().isEmpty())) {
			erros.add("Data de nascimento invalida");
		}
		
		return erros;
	}

	@Override
	public Paciente buscarPaciente(String nome) {
		return pacienteDao.findByNomeContaining(nome);
	}
	
	@Override
	public Paciente buscarPacienteCpf(String cpf) {
		return pacienteDao.findByCpfContaining(cpf);
	}
	
	@Override
	public Paciente buscarPacienteId(ObjectId id) {
		Optional<Paciente> paciente = pacienteDao.findById(id);
		return paciente.orElse(null);
	}
	@Override
	public List<Paciente> listarPacientesId(List<String> id) {
		List<Paciente> pacientes = new java.util.ArrayList<>();
		for (String i : id) {
			ObjectId idPaciente = new ObjectId(i);
            pacientes.add(pacienteDao.findById(idPaciente).get());
        }
		
		return pacientes;
	}
	
}
