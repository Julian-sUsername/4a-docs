package co.votando.accountms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Voto {
    @Id
    private String id;
    private String codigoUrna;
    private String idVotante;
    private String idCandidato;
    private Date fecha;

    public Voto(String id, String codigoUrna, String idVotante, String idCandidato, Date fecha) {
        this.id = id;
        this.codigoUrna = codigoUrna;
        this.idVotante = idVotante;
        this.idCandidato = idCandidato;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoUrna() {
        return codigoUrna;
    }

    public String getIdVotante() {
        return idVotante;
    }

    public String getIdCandidato() {
        return idCandidato;
    }
}
