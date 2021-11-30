package co.votando.accountms.controllers;

import co.votando.accountms.models.Urna;
import co.votando.accountms.models.Votante;
import co.votando.accountms.repositories.VotanteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VotanteController {

    private final VotanteRepository votanteRepository;

    public VotanteController(VotanteRepository votanteRepository) {
        this.votanteRepository = votanteRepository;
    }

    @PostMapping("/votantes")
    Votante newVotante(@RequestBody Votante votante){

        List<Urna> urnas = new ArrayList<>();
        votante.setUrnas(urnas);

        return votanteRepository.save(votante);
    }

}
