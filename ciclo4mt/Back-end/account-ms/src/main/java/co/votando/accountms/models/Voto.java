package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Voto {
    @Id
    private String id;
    private String nombreVotante;
    private String nombreCandidato;
    private Date fecha;

    public Voto(String id, String nombreVotante, String nombreCandidato, Date fecha) {
        this.id = id;
        this.nombreVotante = nombreVotante;
        this.nombreCandidato = nombreCandidato;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public String getNombreVotante() {
        return nombreVotante;
    }

    public void setNombreVotante(String nombreVotante) {
        this.nombreVotante = nombreVotante;
    }

    public String getNombreCandidato() {
        return nombreCandidato;
    }

    public void setNombreCandidato(String nombreCandidato) {
        this.nombreCandidato = nombreCandidato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
