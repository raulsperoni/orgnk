package mgcoders.uy.service.discourse;

import mgcoders.uy.model.Persona;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by RSperoni on 19/02/2016.
 */
@Entity
public class DiscourseUser implements Serializable {

    @Id
    private long id;
    private String username;
    private String email;
    private Boolean active;
    private Boolean approved;
    private String name;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Persona persona;

    public DiscourseUser() {
    }

    public DiscourseUser(Persona persona) {
        this.username = persona.getNombre().toLowerCase().replace(' ', '_');
        this.email = persona.getEmail();
        this.active = true;
        this.persona = persona;
        this.approved = false;
        this.name = persona.getNombre();
    }


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "DiscourseUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", approved=" + approved +
                '}';
    }
}
