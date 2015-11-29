package mgcoders.uy.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Opcion implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    private String descripcion;

    public long getId() {
        return id;
    }

    public Opcion setId(long id) {
        this.id = id;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Opcion setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
}
