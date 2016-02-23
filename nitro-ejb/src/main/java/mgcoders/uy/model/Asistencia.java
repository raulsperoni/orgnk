package mgcoders.uy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by RSperoni on 23/02/2016.
 */

@Entity
@IdClass(AsistenciaId.class)
public class Asistencia implements Serializable {

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private Actividad actividad;

    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(referencedColumnName = "id")
    private Persona persona;

    @NotNull
    private boolean confirmado;

    private boolean asiste;

    private boolean requiereAlojamiento;

    private String comentario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConfirmacion;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isRequiereAlojamiento() {
        return requiereAlojamiento;
    }

    public void setRequiereAlojamiento(boolean requiereAlojamiento) {
        this.requiereAlojamiento = requiereAlojamiento;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public boolean isAsiste() {
        return asiste;
    }

    public void setAsiste(boolean asiste) {
        this.asiste = asiste;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Date fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    @PreUpdate
    @PrePersist
    private void actualizarFecha() {
        if (confirmado) {
            this.fechaConfirmacion = new Date();
        } else {
            this.fechaConfirmacion = null;
        }
    }
}
