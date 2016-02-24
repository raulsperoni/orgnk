package mgcoders.uy.events;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RSperoni on 18/02/2016.
 */
public class RegistrationEvent implements Serializable {

    private long persona_id;
    private Date date;

    public RegistrationEvent(long persona_id, Date date) {
        this.persona_id = persona_id;
        this.date = date;
    }

    public long getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(long persona_id) {
        this.persona_id = persona_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RegistrationEvent{" +
                "personaId=" + persona_id +
                ", date=" + date +
                '}';
    }
}
