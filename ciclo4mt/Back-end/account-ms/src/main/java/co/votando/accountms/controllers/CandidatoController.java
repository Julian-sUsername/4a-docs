package co.votando.accountms.controllers;

import co.votando.accountms.models.Candidato;
import co.votando.accountms.models.Urna;
import co.votando.accountms.models.Voto;
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
