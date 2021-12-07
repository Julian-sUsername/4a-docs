package co.votando.accountms.controllers;


import co.votando.accountms.models.*;
import co.votando.accountms.repositories.*;
import co.votando.accountms.exceptions.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UrnaController {

    private final UrnaRepository urnaRepository;
    private final CandidatoRepository candidatoRepository;
    private final VotoRepository votoRepository;
    private final VotanteRepository votanteRepository;

    public UrnaController(UrnaRepository urnaRepository, CandidatoRepository candidatoRepository, VotoRepository votoRepository, VotanteRepository votanteRepository) {
        this.urnaRepository = urnaRepository;
        this.candidatoRepository = candidatoRepository;
        this.votoRepository = votoRepository;
        this.votanteRepository = votanteRepository;
    }

    @PostMapping("/urnas")
    Urna newUrna(@RequestBody Urna urna) {

        String cadena1 = "";
        String cadena2 = "";
        cadena1 = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        cadena2 = UUID.randomUUID().toString().toLowerCase().substring(0, 8);
        String codigoUrna = cadena1 + cadena2;

        String cadena3 = "";
        String cadena4 = "";
        cadena3 = UUID.randomUUID().toString().toUpperCase().substring(0, 7);
        cadena4 = UUID.randomUUID().toString().toLowerCase().substring(0, 8);
        String codigoCandidato = cadena3 + cadena4;

        List<Candidato> candidatos = new ArrayList<>();

        Candidato candidato = new Candidato(codigoUrna, "Voto en blanco", "No hay descripción para el voto en blanco", new ArrayList<Voto>());
        candidato.setId(codigoCandidato);
        candidatos.add(candidato);
        candidatoRepository.save(candidato);

        urna.setEsDisponible(false);
        urna.setFecha(new Date());
        urna.setCodigo(codigoUrna);
        urna.setCandidatos(candidatos);
        urna.setResultados("");
        urna.setGanador("");

        return urnaRepository.save(urna);
    }

    @PostMapping("/urnas/cerrar/{codigoUrna}")
    Urna cerrarUrna(@PathVariable String codigoUrna) {

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));
        urna.setEsDisponible(false);
        return urnaRepository.save(urna);
    }

    @PostMapping("/urnas/abrir/{codigoUrna}")
    Urna abrirUrna(@PathVariable String codigoUrna) {

        Urna urna = urnaRepository.findById(codigoUrna).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigoUrna));
        List<Candidato> candidatos = urna.getCandidatos();

        if (candidatos.size() < 3) {
            throw new CandidatosInsuficientesException("Deben haber por lo menos dos candidatos para " +
                    "que se pueda abrir la urna");
        }

        urna.setEsDisponible(true);
        return urnaRepository.save(urna);
    }

    @GetMapping("/urnas/resultados/{codigo}")
    Urna getResultadosUrna(@PathVariable String codigo) {

        Urna urna = urnaRepository.findById(codigo).orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigo));

        List<Candidato> candidatos = urna.getCandidatos();
        List<Voto> votos = votoRepository.findAll();
        List<Voto> votosFiltrados = new ArrayList<>();

        votos.forEach((voto) -> {
            if (voto.getCodigoUrna().equals(codigo)) {
                votosFiltrados.add(voto);
            }
        });

        if (votosFiltrados.isEmpty()) {
            throw new UrnaCerradaException("No se han creado votos en esta urna: " + codigo);
        }

        if (urna.esDisponible()) {
            throw new UrnaAbiertaException("No se puede consultar resultados porque " +
                    "el administrador no ha cerrado la urna: " + codigo);
        }

        StringBuilder infoCandidatos = new StringBuilder();

        List<String> ganador = new ArrayList<>();
        List<String> nombreCandidato = new ArrayList<>();
        List<Integer> listaVotosGanador = new ArrayList<>();

        candidatos.forEach((unCandidato) -> {

            int numeroVotosGanador = 0;
            Candidato candidato = candidatoRepository.findById(unCandidato.getId()).get();
            List<Voto> votosTotales = candidato.getVotos();
            int numeroVotos = votosTotales.size();

            int indice = candidatos.indexOf(unCandidato);
            int ultimoIndice = candidatos.indexOf(candidatos.get(candidatos.size()-1));

            if (indice == ultimoIndice) {
                infoCandidatos.append(unCandidato + ", total votos: " + numeroVotos + ".");
            } else {
                infoCandidatos.append(unCandidato + ", total votos: " + numeroVotos + ". ");
            }

            if (!listaVotosGanador.isEmpty()) {
                numeroVotosGanador = listaVotosGanador.get(listaVotosGanador.size() - 1);
            }

            if (numeroVotos >= numeroVotosGanador) {
                numeroVotosGanador = numeroVotos;
                listaVotosGanador.add(numeroVotosGanador);
                nombreCandidato.add(unCandidato.getNombreCompleto());
                if (numeroVotosGanador == 1) {
                    ganador.add((candidato.getNombreCompleto() + ", con " + numeroVotosGanador + " voto en total."));
                } else {
                    ganador.add((candidato.getNombreCompleto() + ", con " + numeroVotosGanador + " votos en total."));
                }
            }

        });

        urna.setResultados(infoCandidatos.toString());

        List<String> infoVictoria = new ArrayList<>();
        int ultimoRegistro = 0;
        int penultimoRegistro = 0;
        String ultimoCandidato = "";
        String penultimoCandidato = "";

        if (listaVotosGanador.size() >= 2) {

            ultimoRegistro = listaVotosGanador.get(listaVotosGanador.size() - 1);
            penultimoRegistro = listaVotosGanador.get(listaVotosGanador.size() - 2);
            ultimoCandidato = nombreCandidato.get(nombreCandidato.size() - 1);
            penultimoCandidato = nombreCandidato.get(nombreCandidato.size() - 2);

        }

        if (ultimoRegistro == penultimoRegistro & listaVotosGanador.size() > 1) {
            urna.setGanador("Hay un empate entre " + penultimoCandidato + " y " + ultimoCandidato + ".");
        } else {
            urna.setGanador("El ganador de la urna " + codigo + " es: " + ganador.get(ganador.size() - 1));
        }

        urnaRepository.save(urna);

        return urnaRepository.findById(codigo).get();
    }

    @PostMapping("/urnas/eliminar/{codigo}")
    String removeUrna(@PathVariable String codigo) {
        Urna urna = urnaRepository.findById(codigo)
                .orElseThrow(() -> new UrnaNoEncontradaException("No se encontró una urna con el código: " + codigo));

        if (urna.esDisponible()) {
            throw new UrnaAbiertaException("No se puede eliminar una urna que está abierta. Cóndigo urna: " + urna.getCodigo());
        }

        List<Candidato> candidatos = candidatoRepository.findAll();

        candidatos.forEach((unCandidato) -> {
            if (unCandidato.getCodigoUrna().equals(codigo)) {
                List<Voto> votos = votoRepository.findAll();
                votos.forEach((unVoto) -> {
                    if (unCandidato.getCodigoUrna().equals(unVoto.getCodigoUrna())) {
                        votoRepository.delete(unVoto);
                    }
                });
                candidatoRepository.delete(unCandidato);
            }
        });

        List<Votante> votantes = votanteRepository.findAll();
        votantes.forEach((unVotante) -> {
            List<Urna> urnas = unVotante.getUrnas();
            List<Urna> urnasFiltradas = new ArrayList<>();
            urnasFiltradas.addAll(urnas);
            urnas.forEach((unaUrna) -> {
                if (unaUrna.getCodigo().equals(codigo)) {
                    urnasFiltradas.remove(unaUrna);
                }
            });
            unVotante.setUrnas(urnasFiltradas);
            votanteRepository.save(unVotante);
        });

        urnaRepository.delete(urna);
        return "Urna eliminada: " + codigo;
    }

}
