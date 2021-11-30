package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Votante {
    @Id
    private String nombreUsuario;
    private String nombreCompleto;
    private String correo;
    private List<Urna> urnas;

    public Votante(String nombreUsuario, String nombreCompleto, String correo, List<Urna> urnas) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.urnas = urnas;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Urna> getUrnas() {
        return urnas;
    }

    public void setUrnas(List<Urna> urnas) {
        this.urnas = urnas;
    }
}
