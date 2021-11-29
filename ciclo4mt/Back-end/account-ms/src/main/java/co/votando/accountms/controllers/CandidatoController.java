package co.votando.accountms.controllers;

import co.votando.accountms.models.Candidato;
import co.votando.accountms.repositories.CandidatoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class CandidatoController {

    private final CandidatoRepository candidatoRepository;

    public CandidatoController(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @PostMapping("/candidatos")
    Candidato newCandidato(@RequestBody Candidato candidato){
        return candidatoRepository.save(candidato);
    }

}
