package mgcoders.uy.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 22/11/15.
 */
@Entity
@Inheritance
public class Pregunta {

    @OneToMany(fetch = FetchType.EAGER)
    List<Respuesta> respuestas = new ArrayList<>();
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @NotEmpty
    private String pregunta;
    private String explicacion;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Opcion> opciones = new ArrayList<>();
    private int respuestasMinimas;
    private int respuestasMaximas;

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public Pregunta setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
        return this;
    }

    public long getId() {
        return id;
    }

    public Pregunta setId(long id) {
        this.id = id;
        return this;
    }

    public String getPregunta() {
        return pregunta;
    }

    public Pregunta setPregunta(String pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public Pregunta setExplicacion(String explicacion) {
        this.explicacion = explicacion;
        return this;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public Pregunta setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
        return this;
    }

    public int getRespuestasMinimas() {
        return respuestasMinimas;
    }

    public Pregunta setRespuestasMinimas(int respuestasMinimas) {
        this.respuestasMinimas = respuestasMinimas;
        return this;
    }

    public int getRespuestasMaximas() {
        return respuestasMaximas;
    }

    public Pregunta setRespuestasMaximas(int respuestasMaximas) {
        this.respuestasMaximas = respuestasMaximas;
        return this;
    }
}
