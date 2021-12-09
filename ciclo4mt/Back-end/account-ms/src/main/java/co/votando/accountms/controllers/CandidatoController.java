package co.votando.accountms.controllers;

import co.votando.accountms.exceptions.CandidatoNoEncontradoException;
import co.votando.accountms.exceptions.UrnaAbiertaException;
import co.votando.accountms.exceptions.UrnaNoEncontradaException;
import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        codigoUrna = codigo.substring(0, 15);
        codigoCandidato = codigo.substring(15);

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));

        Candidato candidato = candidatoRepository.findById(codigoCandidato)
                .orElseThrow(() -> new CandidatoNoEncontradoException("No se encontró un candidato con el código: " + codigoCandidato));

        if (urna.esDisponible()) {
            throw new UrnaAbiertaException("No se puede eliminar un candidato cuya urna está abierta. Código urna: " + codigoUrna);
        }

        String mensaje = "";

        if (candidato.getId().equals(codigoCandidato)) {
            candidatoRepository.delete(candidato);
            List<Candidato> candidatos = candidatoRepository.findAll();

            urna.setCandidatos(candidatos);
            urna.setEsDisponible(false);
            urnaRepository.save(urna);

            List<Voto> votos = votoRepository.findAll();
            votos.forEach((unVoto) -> {
                if (candidato.getNombreCompleto().equals(unVoto.getIdCandidato())) {
                    votoRepository.delete(unVoto);
                }
            });
            mensaje = "Candidato eliminado: " + codigoCandidato;
        } else {
            mensaje = "Candidato no eliminado: " + codigoCandidato;
        }

        return mensaje;
    }

    @PostMapping("/candidatos")
    Candidato newCandidato(@RequestBody Candidato candidato) {

        String cadena1 = "";
        String cadena2 = "";
        cadena1 = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        cadena2 = UUID.randomUUID().toString().toLowerCase().substring(0, 8);
        String codigoCandidato = cadena1 + cadena2;

        Urna urna = urnaRepository.findById(candidato.getCodigoUrna()).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + candidato.getCodigoUrna()));
        List<Candidato> candidatos = urna.getCandidatos();
        List<Voto> votos = new ArrayList<>();
        candidato.setVotos(votos);
        candidato.setId(codigoCandidato);
        candidatos.add(candidato);

        urna.setCandidatos(candidatos);
        urnaRepository.save(urna);

        return candidatoRepository.save(candidato);
    }

}
