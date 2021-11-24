package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Urna {
    @Id
    private String id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private boolean esDisponible;
    private boolean esVotada;
    private List<Candidato> candidatos;

    public Urna(String id, String codigo, String nombre, String descripcion, Date fecha, boolean esDisponible, boolean esVotada, List<Candidato> candidatos) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esDisponible = esDisponible;
        this.esVotada = esVotada;
        this.candidatos = candidatos;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEsDisponible() {
        return esDisponible;
    }

    public void setEsDisponible(boolean esDisponible) {
        this.esDisponible = esDisponible;
    }

    public boolean isEsVotada() {
        return esVotada;
    }

    public void setEsVotada(boolean esVotada) {
        this.esVotada = esVotada;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
}
