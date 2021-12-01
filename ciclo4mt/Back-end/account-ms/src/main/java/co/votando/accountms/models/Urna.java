package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Urna {
    @Id
    private String codigo;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private boolean esDisponible;
    private List<Candidato> candidatos;

    public Urna(String codigo, String nombre, String descripcion, Date fecha, boolean esDisponible, List<Candidato> candidatos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esDisponible = esDisponible;
        this.candidatos = candidatos;
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

    public boolean esDisponible() {
        return esDisponible;
    }

    public void setEsDisponible(boolean esDisponible) {
        this.esDisponible = esDisponible;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
}
