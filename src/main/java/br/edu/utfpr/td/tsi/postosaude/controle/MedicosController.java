package br.edu.utfpr.td.tsi.postosaude.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;
import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;
import br.edu.utfpr.td.tsi.postosaude.persistencia.IndexadorMedico;
import br.edu.utfpr.td.tsi.postosaude.service.BairroServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.MedicoServiceImp;

@Controller
public class MedicosController {
	
	@Autowired
	private MedicoServiceImp medicoService;
	
	@Autowired
	private BairroServiceImp bairroService;
	@Autowired
	private IndexadorMedico indexadorMedico = new IndexadorMedico();
	
	
	@GetMapping(value = "/medicos")
	public String medicos(Model model, String busca, String tipoDeBusca) {
		List<Medico> medicos = new ArrayList<>();
		List<String> ids_medicos = new ArrayList<>();

		if (busca != null && !busca.isEmpty() && tipoDeBusca != null && !tipoDeBusca.isEmpty()) {
			ids_medicos = indexadorMedico.procurar(busca, tipoDeBusca);
			medicos = medicoService.listarMedicosPorIds(ids_medicos);
		} else {
			medicos = medicoService.listarMedicos();
		}

		model.addAttribute("medicos", medicos);
		
		List<Bairro> bairros = new ArrayList<>();
		bairros = bairroService.listarBairros();
		bairros.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		model.addAttribute("bairros", bairros);
		
		
		return "medicos";
	}
	
	@PostMapping(value = "/inserirMedico")
	public String inserirMedico(Medico medico) {
		try {
		medicoService.inserirMedico(medico);
		indexadorMedico.indexar(medico);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
            return "redirect:/erroInserirMedico?erro=" + e.getMessage();
       }
		return "redirect:/medicos";
	}
	
	@PostMapping(value = "/excluirMedico")
	public String removerMedico(Medico medico) {
		String id = String.valueOf(medico.getId());
		medicoService.removerMedico(medico);
		indexadorMedico.deletar(id);
		return "redirect:/medicos";
	}
	
	@PostMapping(value = "/alterarMedico")
	public String alterarMedico(Medico medico) {
		String id = String.valueOf(medico.getId());
		medicoService.alterarMedico(medico);
		indexadorMedico.deletar(id);
		indexadorMedico.indexar(medico);
		return "redirect:/medicos";
	}
	
	@GetMapping(value = "/editarMedico")
	public String editarMedico(Model model, Medico medico) {
		
		List<Bairro> bairros = bairroService.listarBairros();
		bairros.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		
		Medico omedico = medicoService.buscarMedicoPorId(medico.getId());
		
		model.addAttribute("bairros", bairros);
		model.addAttribute("medico", omedico);
		return "editarMedico";
	}
	
	@GetMapping(value = "/erroInserirMedico")
	public String erroInserirPaciente(Model model, String erro) {
		model.addAttribute("erro", erro);
		return "erroInserirMedico";
	}
	
}
