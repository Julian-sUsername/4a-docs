package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Candidato {
    @Id
    private String id;
    private String codigoUrna;
    private String nombreCompleto;
    private String descripcion;
    private List<Voto> votos;

    public Candidato(String codigoUrna, String nombreCompleto, String descripcion, List<Voto> votos) {
        this.codigoUrna = codigoUrna;
        this.nombreCompleto = nombreCompleto;
        this.descripcion = descripcion;
        this.votos = votos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

    public String getCodigoUrna() {
        return codigoUrna;
    }

    public void setCodigoUrna(String codigoUrna) {
        this.codigoUrna = codigoUrna;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }
}
