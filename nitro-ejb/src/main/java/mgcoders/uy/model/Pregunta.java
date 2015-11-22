package mgcoders.uy.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
@Inheritance
public abstract class Pregunta {

    @OneToMany
    List<Respuesta> respuestas;
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @NotEmpty
    private String pregunta;
    private String explicacion;
    @OneToMany
    private List<Opcion> opciones;
    private int respuestasMinimas;
    private int respuestasMaximas;


}
