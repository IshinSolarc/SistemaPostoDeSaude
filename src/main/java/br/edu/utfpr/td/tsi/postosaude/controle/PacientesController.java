package br.edu.utfpr.td.tsi.postosaude.controle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;
import br.edu.utfpr.td.tsi.postosaude.modelo.Endereco;
import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;
import br.edu.utfpr.td.tsi.postosaude.persistencia.IndexadorPaciente;
import br.edu.utfpr.td.tsi.postosaude.service.BairroServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.PacienteServiceImp;

@Controller
public class PacientesController {
	
	@Autowired
	private PacienteServiceImp pacienteService;
	@Autowired
	private BairroServiceImp bairroService;
	@Autowired
	private IndexadorPaciente indexadorPaciente = new IndexadorPaciente();
	
	@GetMapping(value = "/pacientes")
	public String pacientes(Model model, String busca, String tipoDeBusca) {
		List<Paciente> pacientes = new ArrayList<>();
		
		List<String> ids_pacientes = new ArrayList<>();
		
		if (busca != null || tipoDeBusca != null) {
			ids_pacientes = indexadorPaciente.procurar(busca, tipoDeBusca);
			pacientes = pacienteService.listarPacientesId(ids_pacientes);
		}
		else {
			pacientes = pacienteService.listarPacientes();
		}
		
		model.addAttribute("pacientes", pacientes);
		
		List<Bairro> bairros = new ArrayList<>();
		
		bairros = bairroService.listarBairros();
		bairros.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		model.addAttribute("bairros", bairros);
		
		return "pacientes";
	}
	
	@PostMapping(value = "/inserirPaciente") 
	public String inserirPaciente(String nome, String cpf, String dataNascimento, String email,
			String telefone, String bairro, String rua, String numero, String complemento){
		
		Bairro b = bairroService.findByNome(bairro);
		Endereco endereco = new Endereco(rua, numero, complemento, b);
		Paciente paciente = new Paciente(nome, cpf, dataNascimento, telefone, email, endereco);
		
		List<String> erros = pacienteService.checarCampos(paciente);
		if (erros.size() > 0) {
            return "redirect:/erroInserirPaciente" + "?erros=" + String.join(",", erros);
		}
		
		pacienteService.inserirPaciente(paciente);
		indexadorPaciente.indexar(paciente);
		
		return "redirect:/pacientes";
	}
	
	@PostMapping(value = "/excluirPaciente") 
	public String removerPaciente(Paciente paciente) {
		pacienteService.removerPaciente(paciente);
		String id = String.valueOf(paciente.getId());
		indexadorPaciente.deletar(id);
		return "redirect:/pacientes";
	}
	
	@GetMapping(value = "/editarPaciente")
	public String editarPaciente(Model model, Paciente paciente) {
	    List<Bairro> bairros = bairroService.listarBairros();
		bairros.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		
		Paciente p = pacienteService.buscarPacienteId(paciente.getId());
		
		model.addAttribute("bairros", bairros);
		model.addAttribute("paciente", p);

		
        return "editarPaciente";
    }
	
	@PostMapping(value = "/alterarPaciente")
	public String alterarPaciente(Paciente paciente, String bairro, String rua, String numero, String complemento) {
		
		Bairro b = bairroService.findByNome(bairro);
		Endereco endereco = new Endereco(rua, numero, complemento, b);
		paciente.setEndereco(endereco);
		if( pacienteService.checarCampos(paciente).size() > 0) {
            return "redirect:/erroInserirPaciente";
		}
		
		String id = String.valueOf(paciente.getId());
		pacienteService.alterarPaciente(paciente);
		indexadorPaciente.deletar(id);
		indexadorPaciente.indexar(paciente);
		
		return "redirect:/pacientes";
	}
	
	@GetMapping(value = "/erroInserirPaciente")
	public String erroInserirPaciente(Model model, String[] erros) {
		model.addAttribute("erros", erros);
		return "erroInserirPaciente";
	}
}