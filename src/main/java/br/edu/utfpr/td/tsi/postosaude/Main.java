package br.edu.utfpr.td.tsi.postosaude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import br.edu.utfpr.td.tsi.postosaude.persistencia.IndexadorMedico;
import br.edu.utfpr.td.tsi.postosaude.persistencia.IndexadorPaciente;
import br.edu.utfpr.td.tsi.postosaude.service.ContadorServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.MedicoServiceImp;
import br.edu.utfpr.td.tsi.postosaude.service.PacienteServiceImp;

@SpringBootApplication
@ComponentScan("br.edu.utfpr.td.tsi.postosaude")
public class Main {

    @Autowired
    private PacienteServiceImp pacienteService;
    @Autowired
    private IndexadorPaciente indexadorPaciente;
    @Autowired
    private MedicoServiceImp medicoService;
    @Autowired
    private IndexadorMedico indexadorMedico;
    @Autowired
    private ContadorServiceImp contadorService;

    public static void main(String[] args) {
		
    	
    	System.setProperty("server.port", "8084");
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
    	contadorService.checarContadores();
    	System.out.println("Contadores checados");
    	
    	/*
        IndexadorPaciente indexadorPaciente = new IndexadorPaciente();

        List<Paciente> pacientes = pacienteService.listarPacientes();
        for (Paciente paciente : pacientes) {
            indexadorPaciente.indexar(paciente);
        }
        
        IndexadorMedico indexadorMedico = new IndexadorMedico();
        
        List<Medico> medicos = medicoService.listarMedicos();
		for (Medico medico : medicos) {
			indexadorMedico.indexar(medico);
		}
		*/
    }
}