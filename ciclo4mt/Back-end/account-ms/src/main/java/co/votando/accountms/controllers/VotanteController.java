package co.votando.accountms.controllers;

import co.votando.accountms.models.Votante;
import co.votando.accountms.repositories.VotanteRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class VotanteController {

    private final VotanteRepository votanteRepository;

    public VotanteController(VotanteRepository votanteRepository) {
        this.votanteRepository = votanteRepository;
    }

    @PostMapping("/votantes")
    Votante newVotante(@RequestBody Votante votante){
        return votanteRepository.save(votante);
    }

}
