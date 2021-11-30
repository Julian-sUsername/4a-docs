package co.votando.accountms.controllers;


import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import co.votando.accountms.exceptions.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UrnaController {

    private final UrnaRepository urnaRepository;
    private final CandidatoRepository candidatoRepository;

    public UrnaController(UrnaRepository urnaRepository, CandidatoRepository candidatoRepository) {
        this.urnaRepository = urnaRepository;
        this.candidatoRepository = candidatoRepository;
    }

    @PostMapping("/urnas")
    Urna newUrna(@RequestBody Urna urna) {

        String cadena1 = "";
        String cadena2 = "";
        cadena1 = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        cadena2 = UUID.randomUUID().toString().toLowerCase().substring(0, 8);
        String codigoUrna = cadena1 + cadena2;

        List<Candidato> candidatos = new ArrayList<>();

        urna.setEsDisponible(false);
        urna.setFecha(new Date());
        urna.setCodigo(codigoUrna);
        urna.setCandidatos(candidatos);

        return urnaRepository.save(urna);
    }

    @PostMapping("/urnas/cerrar/{codigoUrna}")
    Urna cerrarUrna(@PathVariable String codigoUrna) {

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));
        urna.setEsDisponible(false);
        return urnaRepository.save(urna);
    }

    @PostMapping("/urnas/abrir/{codigoUrna}")
    Urna abrirUrna(@PathVariable String codigoUrna) {

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));
        List<Candidato> candidatos = urna.getCandidatos();

        if(candidatos.size() < 2){
            throw new CandidatosInsuficientesException("Deben haber por lo menos dos candidatos para " +
                    "que se pueda abrir la urna");
        }

        urna.setEsDisponible(true);
        return urnaRepository.save(urna);
    }

    @GetMapping("/urnas/resultados/{codigo}")
    String getResultadosUrna(@PathVariable String codigo) {

        Urna urna = urnaRepository.findById(codigo).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigo));
        if(urna.EsDisponible()){
            throw new UrnaAbiertaException("No se puede consultar resultados porque " +
                    "el administrador no ha cerrado la urna: " + codigo);
        }
        List<Candidato> candidatos = urna.getCandidatos();

        StringBuilder infoCandidatos = new StringBuilder();

        candidatos.forEach((unCandidato) -> {
            Candidato candidato = candidatoRepository.findById(unCandidato.getNombreCompleto()).get();
            List<Voto> votos = candidato.getVotos();
            int numeroVotos = votos.size();
            String a = unCandidato.getCodigoUrna();
            infoCandidatos.append(unCandidato + "\nTotal votos: " + numeroVotos + "\n");
        });

        String totalInfo = infoCandidatos.toString();

        return totalInfo;
    }

    @PostMapping("/urnas/eliminar/{codigo}")
    String removeUrna(@PathVariable String codigo) {
        Urna urna = urnaRepository.findById(codigo)
                .orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigo));

        urnaRepository.delete(urna);
        return "Urna eliminada: " + codigo;
    }

}
