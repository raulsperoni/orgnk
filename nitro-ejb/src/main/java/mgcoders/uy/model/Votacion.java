package mgcoders.uy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Votacion implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Pregunta> preguntas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "votacion", cascade = CascadeType.PERSIST)
    private List<VotacionAsociacion> votantes = new ArrayList<>();

    public void addVotante(VotingUser votingUser) {
        VotacionAsociacion asociacion = new VotacionAsociacion(this, votingUser);
        this.votantes.add(asociacion);
        votingUser.getVotaciones().add(asociacion);
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

    @Override
    public String toString() {
        return "Votacion{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
