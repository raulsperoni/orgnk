package mgcoders.uy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class VotingUser {

    @Id
    private long persona_id;
    @JoinColumn(name = "persona_id")
    @OneToOne
    @MapsId
    private Persona persona;

    private String nombreUsuario;

    @OneToMany(mappedBy = "votingUser")
    private List<VotacionAsociacion> votaciones = new ArrayList<>();

    protected VotingUser() {
    }

    public VotingUser(Persona persona) {
        this.persona = persona;
        this.nombreUsuario = persona.getNombre();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public VotingUser setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        return this;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<VotacionAsociacion> getVotaciones() {
        return votaciones;
    }
}
