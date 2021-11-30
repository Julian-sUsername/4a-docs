package co.votando.accountms.controllers;

import co.votando.accountms.exceptions.CandidatoNoEncontradoException;
import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CandidatoController {

    private final CandidatoRepository candidatoRepository;
    private final UrnaRepository urnaRepository;

    public CandidatoController(CandidatoRepository candidatoRepository, UrnaRepository urnaRepository) {
        this.candidatoRepository = candidatoRepository;
        this.urnaRepository = urnaRepository;
    }

    @GetMapping("/candidatos/{codigo}")
    Candidato getCandidatos(@PathVariable String codigo) {
        return candidatoRepository.findById(codigo)
                .orElseThrow(() -> new CandidatoNoEncontradoException("No se encontró un candidato con el código: " + codigo));
    }


    @PostMapping("/candidatos/eliminar/{codigo}")
    String removeCandidato(@PathVariable String codigo) {
        Candidato candidato = candidatoRepository.findById(codigo)
                .orElseThrow(() -> new CandidatoNoEncontradoException("No se encontró un candidato con el código: " + codigo));

        candidatoRepository.delete(candidato);
        return "Candidato eliminado: " + codigo;
    }

    @PostMapping("/candidatos")
    Candidato newCandidato(@RequestBody Candidato candidato){

        List<Urna> urnas = urnaRepository.findAll();

        urnas.forEach((urna) -> {
            if(urna.getCodigo().equals(candidato.getCodigoUrna())){
                List<Candidato> candidatos = urna.getCandidatos();
                candidatos.add(candidato);
                urna.setCandidatos(candidatos);
                urnaRepository.save(urna);
            }
        });

        List<Voto> votos = new ArrayList<>();
        candidato.setVotos(votos);

        return candidatoRepository.save(candidato);
    }

}
