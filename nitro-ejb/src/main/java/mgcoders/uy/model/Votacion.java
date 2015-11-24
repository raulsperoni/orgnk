package mgcoders.uy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Votacion {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date fechaInicio;
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date fechaFin;

    @OneToMany
    private List<Pregunta> preguntas;

    @OneToMany(mappedBy = "votacion")
    private List<VotacionAsociacion> votantes;

    public void addVotante(Votante votante) {
        VotacionAsociacion asociacion = new VotacionAsociacion(this, votante);
        this.votantes.add(asociacion);
        votante.getVotaciones().add(asociacion);
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public List<VotacionAsociacion> getVotantes() {
        return votantes;
    }

    public long getId() {
        return id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
