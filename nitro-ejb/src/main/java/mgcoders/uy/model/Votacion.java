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

    @OneToMany
    private List<Votante> votantes;
}
