package mgcoders.uy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Votante {

    @Id
    @GeneratedValue
    private long id;

    private String nombreUsuario;

    @OneToMany(mappedBy = "votante")
    private List<VotacionAsociacion> votaciones = new ArrayList<>();

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public Votante setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        return this;
    }

    public long getId() {
        return id;
    }

    public List<VotacionAsociacion> getVotaciones() {
        return votaciones;
    }
}
