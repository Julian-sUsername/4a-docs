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

        Votante votante = votanteRepository.findById(voto.getIdVotante()).orElseThrow(() -> new VotanteNoEncontradoException("No se encontró un votante con el código: " + voto.getIdVotante()));

        if(voto.getIdCandidato().equalsIgnoreCase("blanco") || voto.getIdCandidato().equals("") || voto.getIdCandidato()==null){

            Candidato candidato = candidatoRepository.findById("Voto en blanco").get();
            Urna urna = urnaRepository.findById(voto.getCodigoUrna()).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + voto.getCodigoUrna()));

            if(!urna.esDisponible()){
                throw new UrnaCerradaException("El administrador ha cerrado esta urna: " + urna.getCodigo());
            }

            List<Urna> urnas = votante.getUrnas();

            urnas.forEach((unaUrna) -> {
                if(unaUrna.getCodigo().equals(voto.getCodigoUrna())){
                    throw new UrnaVotadaException("Ya has votado en esta urna: " + urna.getCodigo());
                }
            });

            urnas.add(urna);
            votante.setUrnas(urnas);
            votanteRepository.save(votante);
            urnaRepository.save(urna);

            voto.setFecha(new Date());

            List<Voto> votos = candidato.getVotos();
            votos.add(voto);
            candidato.setVotos(votos);
            candidatoRepository.save(candidato);

        } else {
            Candidato candidato = candidatoRepository.findById(voto.getIdCandidato()).orElseThrow(() -> new CandidatoNoEncontradoException("No se encontró un candidato con el código: " + voto.getIdCandidato()));
            Urna urna = urnaRepository.findById(voto.getCodigoUrna()).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + voto.getCodigoUrna()));

            if(!urna.esDisponible()){
                throw new UrnaCerradaException("El administrador ha cerrado esta urna: " + urna.getCodigo());
            }

            List<Urna> urnas = votante.getUrnas();

            urnas.forEach((unaUrna) -> {
                if(unaUrna.getCodigo().equals(voto.getCodigoUrna())){
                    throw new UrnaVotadaException("Ya has votado en esta urna: " + urna.getCodigo());
                }
            });

            urnas.add(urna);
            votante.setUrnas(urnas);
            votanteRepository.save(votante);
            urnaRepository.save(urna);

            voto.setFecha(new Date());

            List<Voto> votos = candidato.getVotos();
            votos.add(voto);
            candidato.setVotos(votos);
            candidatoRepository.save(candidato);

        }


        return votoRepository.save(voto);
    }

}

