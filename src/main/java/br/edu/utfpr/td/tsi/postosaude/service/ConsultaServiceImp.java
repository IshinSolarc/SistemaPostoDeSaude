package br.edu.utfpr.td.tsi.postosaude.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.postosaude.modelo.Consulta;
import br.edu.utfpr.td.tsi.postosaude.persistencia.ConsultaDao;

@Service
public class ConsultaServiceImp implements ConsultaService {
	
	@Autowired
	private ConsultaDao consultaDao;
	@Autowired
	private ContadorService contadorService;
	
	@Override
	public void inserirConsulta(Consulta consulta) {
		Consulta retorno = consultaDao.findByPacienteIdAndSituacao(consulta.getPaciente().getId(), "agendada");
		if (retorno != null) {
            throw new RuntimeException("Paciente já tem consulta agendada");
        }
		
		retorno = consultaDao.findByMedicoIdAndDataAndHorario(consulta.getMedico().getId(), consulta.getData(), consulta.getHorario());
		
		if (retorno != null) {
			throw new RuntimeException("Médico está ocupado nesse horário");
		}
		
		String dataAtual = LocalDate.now().toString();
		if (consulta.getData().compareTo(dataAtual) < 0) {
			throw new RuntimeException("Data da consulta é inválida");
		}
		
		consulta.setSituacao("agendada");
		
		consulta.setIdConsulta(contadorService.proximaConsulta());
		consultaDao.save(consulta);
		
	}

	@Override
	public void removerConsulta(Consulta consulta) {
		consultaDao.delete(consulta);
	}

	@Override
	public void alterarConsulta(Consulta consulta) {
		consultaDao.save(consulta);
	}

	@Override
	public Consulta buscarConsulta(ObjectId id) {
		return consultaDao.findById(id).get();
	}

	@Override
	public List<Consulta> listarConsultas() {
		return consultaDao.findAll();
	}
	
	@Override
	public List<Consulta> listarConsultas(List<String> ids) {
		List<ObjectId> idsObj = ids.stream().map(ObjectId::new).toList();
		List<Consulta> consultas = new ArrayList<>();
		consultaDao.findAllById(idsObj).forEach(consultas::add);
		return consultas;
	}

	@Override
	public List<Consulta> findAllByPacienteId(ObjectId id) {
		return consultaDao.findAllByPacienteId(id);
	}

	@Override
	public List<Consulta> findAllByMedicoId(ObjectId id) {
		return consultaDao.findAllByMedicoId(id);
	}
	
	@Override
	public void cancelarConsulta(Consulta consulta) {
		consulta.consultaCancelada();
		consultaDao.save(consulta);
	}
	
	@Override
	public void concluirConsulta(Consulta consulta) {
		consulta.consultaRealizada();
		consultaDao.save(consulta);
	}

}
