package mgcoders.uy.events;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RSperoni on 18/02/2016.
 */
public class DiscourseUserCreatedEvent implements Serializable {

    private long persona_id;
    private Date date;
    private String password;

    public DiscourseUserCreatedEvent(long persona_id, Date date, String password) {
        this.persona_id = persona_id;
        this.date = date;
        this.password = password;
    }

    public String getPassword() {
        return password;
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
        return "DiscourseUserCreatedEvent{" +
                "persona_id=" + persona_id +
                ", date=" + date +
                '}';
    }
}
