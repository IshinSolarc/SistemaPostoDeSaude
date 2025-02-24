package br.edu.utfpr.td.tsi.postosaude.controle;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.postosaude.modelo.Consulta;
import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;
import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;
import br.edu.utfpr.td.tsi.postosaude.persistencia.IndexadorConsulta;
import br.edu.utfpr.td.tsi.postosaude.service.ConsultaServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.MedicoServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.PacienteServiceImp;

@Controller
public class ConsultasController {
	
	@Autowired
	private PacienteServiceImp pacienteService;
	@Autowired
	private ConsultaServiceImp consultaService;
	@Autowired
	private MedicoServiceImp medicoService;
	@Autowired
	private IndexadorConsulta indexadorConsulta = new IndexadorConsulta();
	
	@GetMapping(value = "/consultas")
	public String consultas(Model model, Paciente paciente, String buscadiagnostico, String buscareceita) {
		List<Consulta> consultas = new ArrayList<>();
		ObjectId id_paciente = null;
		if (paciente != null)
			id_paciente = paciente.getId();
		
		if ((buscadiagnostico != null || buscareceita != null) && id_paciente != null) {
			List<String> idConsultas = indexadorConsulta.procuraDetalhada(String.valueOf(id_paciente), buscadiagnostico, buscareceita);
			for (String id : idConsultas) {
				System.out.println(id);
			}
			consultas = consultaService.listarConsultas(idConsultas);
			for (Consulta c : consultas) {
                System.out.println(c.getId());
            }
		} else if (buscadiagnostico != null || buscareceita != null) {
			List<String> idConsultas = indexadorConsulta.procurar(buscadiagnostico, buscareceita);
			consultas = consultaService.listarConsultas(idConsultas);
		} else if (id_paciente != null) {
			List<String> idConsultas = indexadorConsulta.procurarPorPaciente(String.valueOf(id_paciente));
			consultas = consultaService.listarConsultas(idConsultas);
		} else {
			consultas = consultaService.listarConsultas();
		}
		model.addAttribute("consultas", consultas);


		return "consultas";
	}
	
	@GetMapping(value = "/novaConsulta")
	public String novaConsulta(Model model, Paciente paciente) {
		List<Medico> medicos = new ArrayList<>();
		Paciente p = pacienteService.buscarPacienteId(paciente.getId());
		
		medicos = medicoService.listarMedicos();
		medicos.removeIf(m -> !m.getBairro().getNome().equals(p.getEndereco().getBairro().getNome()));
		
		model.addAttribute("medicos", medicos);
		model.addAttribute("paciente", p);
		return "novaConsulta";
	}
	
	@PostMapping(value = "/inserirConsulta")
	public String inserirConsulta(Medico medico, Paciente paciente, String data, String horario){
		
		if (medico == null || medico.getId() == null) {
			return "redirect:/erroInserirConsulta" + "?erro=Medico e obrigatorio";
		}
		
		if (data == null || data.isEmpty() || horario == null || horario.isEmpty()) {
			return "redirect:/erroInserirConsulta" + "?erro=Data e horário são obrigatórios";
		}
		
		Consulta consulta = new Consulta();
		consulta.setData(data);
		consulta.setHorario(horario);
        consulta.setMedico(medicoService.buscarMedicoPorId(medico.getId()));
        consulta.setPaciente(pacienteService.buscarPacienteId(paciente.getId()));
		
        try {
            consultaService.inserirConsulta(consulta);
            indexadorConsulta.indexar(consulta);
            
        } catch (Exception e) {
            return "redirect:/erroInserirConsulta" + "?erro=" + e.getMessage();
        }
		
		return "redirect:/consultas";
	}
	
	@GetMapping(value = "/cancelarConsulta")
	public String cancelarConsulta(Consulta consulta) {
		Consulta c = consultaService.buscarConsulta(consulta.getId());
		consultaService.cancelarConsulta(c);
		indexadorConsulta.deletar(String.valueOf(c.getId()));
		indexadorConsulta.indexar(c);
		return "redirect:/consultas";
	}
	
	@GetMapping(value = "/realizarConsulta")
	public String realizarConsulta(Model model, Consulta consulta) {
		Consulta c = consultaService.buscarConsulta(consulta.getId());
		model.addAttribute("consulta", c);
		Paciente p = pacienteService.buscarPacienteId(c.getPaciente().getId());
		model.addAttribute("paciente", p);
		Medico m = medicoService.buscarMedicoPorId(c.getMedico().getId());
		model.addAttribute("medico", m);
		
		return "realizarConsulta";
	}
	
	@PostMapping(value = "/concluirConsulta")
	public String concluirConsulta(Consulta consulta, String diagnostico, String receita) {
        Consulta c = consultaService.buscarConsulta(consulta.getId());
        c.setDiagnostico(diagnostico);
        c.setReceita(receita);
        consultaService.concluirConsulta(c);
        indexadorConsulta.deletar(String.valueOf(c.getId()));
        indexadorConsulta.indexar(c);
        return "redirect:/consultas";
	}
	
	@GetMapping(value = "/erroInserirConsulta")
	public String erroInserirConsulta(Model model, String erro) {
		model.addAttribute("erro", erro);
		return "erroInserirConsulta";
	}
	
	@GetMapping(value = "/verConsulta")
	public String verConsulta(Model model, Consulta consulta) {
		Consulta c = consultaService.buscarConsulta(consulta.getId());
		model.addAttribute("consulta", c);
		Paciente p = pacienteService.buscarPacienteId(c.getPaciente().getId());
		model.addAttribute("paciente", p);
		Medico m = medicoService.buscarMedicoPorId(c.getMedico().getId());
		model.addAttribute("medico", m);

		return "verConsulta";
	}
	@GetMapping(value = "/deletarConsulta")
	public String deletarConsulta(Consulta consulta) {
		consultaService.removerConsulta(consulta);
		indexadorConsulta.deletar(String.valueOf(consulta.getId()));
		return "redirect:/consultas";
	}
	

}