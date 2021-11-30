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

    @GetMapping("/urnas/{codigo}")
    Urna getUrna(@PathVariable String codigo) {
        return urnaRepository.findById(codigo)
                .orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigo));
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

        Urna urna = urnaRepository.findById(codigoUrna).get();
        urna.setEsDisponible(false);
        return urnaRepository.save(urna);
    }

    @PostMapping("/urnas/abrir/{codigoUrna}")
    Urna abrirUrna(@PathVariable String codigoUrna) {

        Urna urna = urnaRepository.findById(codigoUrna).get();
        List<Candidato> candidatos = urna.getCandidatos();

        if(candidatos.size() < 2){
            throw new CandidatosInsuficientesException("Deben haber por lo menos dos candidatos para " +
                    "que se pueda abrir la urna");
        }

        urna.setEsDisponible(true);
        return urnaRepository.save(urna);
    }

}
