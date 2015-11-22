package mgcoders.uy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Respuesta {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Votante votante;

    @ManyToOne
    private Opcion opcion;

    @ManyToOne
    private Pregunta pregunta;

}
