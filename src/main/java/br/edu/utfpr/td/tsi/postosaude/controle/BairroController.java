package br.edu.utfpr.td.tsi.postosaude.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.utfpr.td.tsi.postosaude.modelo.Bairro;
import br.edu.utfpr.td.tsi.postosaude.service.BairroServiceImp;

@Controller
public class BairroController {
	
	@Autowired
	private BairroServiceImp bairroService;
	
	@GetMapping(value = "/bairros")
	public String bairros(Model model) {
		List<Bairro> bairro = bairroService.listarBairros();
		if (bairro == null) {
            return "pacientes";
        }
		
		bairro.sort((a, b) -> a.getNome().compareTo(b.getNome()));
		model.addAttribute("bairros", bairro);
		return "bairros";
	}
	
	@PostMapping(value = "/inserirBairro")
	public String inserirBairro(Bairro bairro) {
		bairroService.inserirBairro(bairro);

        return "redirect:/bairros";
    }
	
	@PostMapping(value = "/removerBairro")
	public String removerBairro(Bairro bairro) {
		bairroService.removerBairro(bairro);
		return "redirect:/bairros";
	}
	
	@PostMapping(value = "/alterarBairro")
	public String alterarBairro(Bairro bairro) {	
		bairroService.alterarBairro(bairro);
		return "redirect:/bairros";
	}

}
