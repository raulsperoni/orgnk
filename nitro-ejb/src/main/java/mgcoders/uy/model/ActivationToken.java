package mgcoders.uy.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by raul on 21/02/16.
 */
@Entity
@NamedQuery(name = "checkToken", query = "SELECT a from ActivationToken a where token = :token")
public class ActivationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "persona_id")
    private Persona persona;

    private boolean verified = false;

    private Date expiryDate;

    public ActivationToken() {
        super();
    }

    public ActivationToken(String token, Persona persona) {
        super();
        this.token = token;
        this.persona = persona;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.verified = false;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
