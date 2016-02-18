package mgcoders.uy.service;

import mgcoders.uy.model.Persona;

import javax.naming.directory.SearchResult;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by RSperoni on 18/02/2016.
 */
public class NuevoRegistroEvent implements Serializable{

    private Persona persona;
    private Date date;

    public NuevoRegistroEvent(Persona persona, Date date) {
        this.persona = persona;
        this.date = date;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NuevoRegistroEvent{" +
                "persona=" + persona +
                ", date=" + date +
                '}';
    }
}
