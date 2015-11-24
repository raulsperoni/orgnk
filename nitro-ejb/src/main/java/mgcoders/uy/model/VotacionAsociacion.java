package mgcoders.uy.model;

import javax.persistence.*;

/**
 * Created by RSperoni on 24/11/2015.
 */
@Entity
@IdClass(VotacionAsociacionId.class)
@NamedQueries({@NamedQuery(name = "VotantesService.getAsociacionPorToken", query = "SELECT va from VotacionAsociacion va where va.tokenAutorizacion = :token"),
        @NamedQuery(name = "VotantesService.getAsociacionPorChatId", query = "SELECT va from VotacionAsociacion va where va.chatId = :chatId")})
public class VotacionAsociacion {

    @Id
    private long votacionId;
    @Id
    private long votanteId;
    private String tokenAutorizacion;
    private String chatId;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "votacionId", referencedColumnName = "id")
    private Votacion votacion;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "votanteId", referencedColumnName = "id")
    private Votante votante;

    public VotacionAsociacion(Votacion votacion, Votante votante) {
        this.votacion = votacion;
        this.votacionId = votacion.getId();
        this.votante = votante;
        this.votanteId = votante.getId();
    }

    public VotacionAsociacion() {
    }

    public String getTokenAutorizacion() {
        return tokenAutorizacion;
    }

    public void setTokenAutorizacion(String tokenAutorizacion) {
        this.tokenAutorizacion = tokenAutorizacion;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Votacion getVotacion() {
        return votacion;
    }

    public Votante getVotante() {
        return votante;
    }
}
