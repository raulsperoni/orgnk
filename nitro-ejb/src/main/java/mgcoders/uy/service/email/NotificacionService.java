package mgcoders.uy.service.email;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import mgcoders.uy.model.ActivationToken;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.NuevoRegistroEvent;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 18/02/2016.
 */
@Stateless
public class NotificacionService {

    private static final String SENDGRID_API_KEY = "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";
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendNotificacionRegistro(@Observes(during= TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event){

        //Persist verification token
        String token = UUID.randomUUID().toString();
        Persona persona = em.find(Persona.class, event.getPersona_id());
        ActivationToken activationToken = new ActivationToken(token, persona);
        em.persist(activationToken);

        //Send it
        SendGrid sendgrid = new SendGrid(SENDGRID_API_KEY);

        SendGrid.Email email = new SendGrid.Email();
        email.addTo("raulsperoni@gmail.com");//persona.getEmail());
        String confirmationUrl = "http://localhost:8080/nitro-web/activacion.jsf?token=" + token;
        email.setFrom("padron@agoracasagrande.uy");
        email.setText("Gracias, " + persona.getNombre() + " Se ha registrado en el Padrón de Casa Grande, debemos verificar su email, por favor haga click en el siguiente link: " + confirmationUrl);
        email.setSubject("Padrón Casa Grande, activación");

        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println(response.getMessage());
        }
        catch (SendGridException e) {
            System.err.println(e);
        }

    }
}
