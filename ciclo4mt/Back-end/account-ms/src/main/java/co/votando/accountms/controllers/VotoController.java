package co.votando.accountms.controllers;

import co.votando.accountms.exceptions.*;
import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
public class VotoController {

    private final VotoRepository votoRepository;
    private final UrnaRepository urnaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VotanteRepository votanteRepository;

    public VotoController(VotoRepository votoRepository, UrnaRepository urnaRepository, CandidatoRepository candidatoRepository, VotanteRepository votanteRepository) {
        this.votoRepository = votoRepository;
        this.urnaRepository = urnaRepository;
        this.candidatoRepository = candidatoRepository;
        this.votanteRepository = votanteRepository;
    }

    @PostMapping("/votos")
    Voto newVoto(@RequestBody Voto voto){
        Votante votante = votanteRepository.findById(voto.getId()).orElse(null);
        Candidato candidato = candidatoRepository.findById(voto.getId()).orElse(null);
        Urna urna = urnaRepository.findById(voto.getId()).orElse(null);

        if(urna.isEsVotada()){
            throw new UrnaVotadaException("Ya has votado en esta urna: " + urna.getCodigo());
        }

        if(!urna.isEsDisponible()){
            throw new UrnaCerradaException("Esta urna ha sido cerrada por el administrador: " + urna.getCodigo());
        }

        List<Urna> urnas = votante.getUrnas();
        urna.setEsVotada(true);
        urnas.add(urna);
        votante.setUrnas(urnas);
        votanteRepository.save(votante);
        urnaRepository.save(urna);

        voto.setNombreVotante(votante.getNombreCompleto());
        voto.setNombreCandidato(candidato.getNombreCompleto());
        voto.setFecha(new Date());

        List<Voto> votos = candidato.getVotos();
        votos.add(voto);
        candidato.setVotos(votos);
        candidatoRepository.save(candidato);

        return votoRepository.save(voto);
    }

}

