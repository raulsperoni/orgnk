package mgcoders.uy.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Opcion {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Pregunta pregunta;

    @NotNull
    @NotEmpty
    private String descripcion;
}
