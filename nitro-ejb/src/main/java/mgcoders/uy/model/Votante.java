package mgcoders.uy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private List<VotacionAsociacion> votaciones;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public long getId() {
        return id;
    }

    public List<VotacionAsociacion> getVotaciones() {
        return votaciones;
    }
}
