package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Urna {
    @Id
    private String codigo;
    private String nombre;
    private String descripcion;
    private String idAdmin;
    private Date fecha;
    private boolean esDisponible;
    private List<Candidato> candidatos;
    private String resultados;
    private String ganador;

    public Urna(String codigo, String nombre, String descripcion, String idAdmin, Date fecha, boolean esDisponible, List<Candidato> candidatos, String resultados, String ganador) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idAdmin = idAdmin;
        this.fecha = fecha;
        this.esDisponible = esDisponible;
        this.candidatos = candidatos;
        this.resultados = resultados;
        this.ganador = ganador;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getResultados() {
        return resultados;
    }

    public String getGanador() {
        return ganador;
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
