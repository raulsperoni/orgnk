package mgcoders.uy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by RSperoni on 24/11/2015.
 */
@Entity
@IdClass(VotacionAsociacionId.class)
@NamedQueries({@NamedQuery(name = "VotantesService.getAsociacionPorToken", query = "SELECT va from VotacionAsociacion va where va.tokenAutorizacion = :token"),
        @NamedQuery(name = "VotantesService.getAsociacionPorChatId", query = "SELECT va from VotacionAsociacion va where va.chatId = :chatId and va.tokenAutorizacion = :token")})
public class VotacionAsociacion {


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "votacionAsociacion")
    List<Respuesta> respuestas = new ArrayList<>();
    @Id
    private String tokenAutorizacion;
    private String chatId;
    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "votacion_id", referencedColumnName = "id")
    private Votacion votacion;
    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "votante_id", referencedColumnName = "id")
    private VotingUser votingUser;

    public VotacionAsociacion(Votacion votacion, VotingUser votingUser) {
        this.votacion = votacion;
        this.votingUser = votingUser;
        this.tokenAutorizacion = UUID.randomUUID().toString();
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

    public VotingUser getVotingUser() {
        return votingUser;
    }
}
