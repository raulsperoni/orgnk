package mgcoders.uy.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by RSperoni on 23/02/2016.
 */
@Entity
public class Actividad {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}
