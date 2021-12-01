package co.votando.accountms.controllers;

import co.votando.accountms.exceptions.CandidatoNoEncontradoException;
import co.votando.accountms.exceptions.UrnaAbiertaException;
import co.votando.accountms.exceptions.UrnaNoEncontradaException;
import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CandidatoController {

    private final CandidatoRepository candidatoRepository;
    private final UrnaRepository urnaRepository;
    private final VotoRepository votoRepository;

    public CandidatoController(CandidatoRepository candidatoRepository, UrnaRepository urnaRepository, VotoRepository votoRepository) {
        this.candidatoRepository = candidatoRepository;
        this.urnaRepository = urnaRepository;
        this.votoRepository = votoRepository;
    }

    @PostMapping("/candidatos/eliminar/{codigo}")
    String removeCandidato(@PathVariable String codigo) {

        String codigoUrna;
        String codigoCandidato;

        codigoUrna = codigo.substring(0,15);
        codigoCandidato = codigo.substring(15);

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));

        Candidato candidato = candidatoRepository.findById(codigoCandidato)
                .orElseThrow(() -> new CandidatoNoEncontradoException("No se encontró un candidato con el código: " + codigoCandidato));

        if(urna.esDisponible()){
            throw new UrnaAbiertaException("No se puede eliminar un candidato cuya urna está abierta. Código urna: " + codigoUrna);
        }

        if(candidato.getNombreCompleto().equals(codigoCandidato)){
            candidatoRepository.delete(candidato);
        }

        List<Candidato> candidatos = candidatoRepository.findAll();

        urna.setCandidatos(candidatos);
        urna.setEsDisponible(false);
        urnaRepository.save(urna);

        List<Voto> votos = votoRepository.findAll();
        votos.forEach((unVoto) -> {
            if(candidato.getNombreCompleto().equals(unVoto.getIdCandidato())){
                votoRepository.delete(unVoto);
            }
        });

        return "Candidato eliminado: " + codigoCandidato;
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
            } else {
                throw new UrnaNoEncontradaException("No se encontró una urna con el código: " + candidato.getCodigoUrna());
            }
        });

        List<Voto> votos = new ArrayList<>();
        candidato.setVotos(votos);

        return candidatoRepository.save(candidato);
    }

}
