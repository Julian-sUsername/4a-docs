package co.votando.accountms.controllers;


import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import co.votando.accountms.exceptions.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Date;
import java.util.UUID;

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
        // validaciones

        Long numeroCandidatos = candidatoRepository.count();
        if (numeroCandidatos < 2) {
            throw new CandidatosInsuficientesException(
                    "Deben haber por lo menos dos candidatos para poder crear la urna");
        }

        List<Candidato> candidatos = candidatoRepository.findAll();

        String cadena1 = "";
        String cadena2 = "";
        cadena1 = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        cadena2 = UUID.randomUUID().toString().toLowerCase().substring(0, 8);

        urna.setEsDisponible(true);
        urna.setEsVotada(false);
        urna.setFecha(new Date());
        urna.setCandidatos(candidatos);
        urna.setCodigo(cadena1 + cadena2);

        return urnaRepository.save(urna);
    }
}
