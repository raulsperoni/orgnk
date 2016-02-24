package mgcoders.uy.service.email;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import mgcoders.uy.model.ActivationToken;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.NuevoRegistroEvent;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 18/02/2016.
 */
@Stateless
public class NotificacionService {

    @Inject
    private Logger log;
    @Inject
    private EntityManager em;
    @Inject
    private Map<String, String> organikaProperties;

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendNotificacionRegistro(@Observes(during= TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event){

        //Persist verification token
        String token = UUID.randomUUID().toString();
        Persona persona = em.find(Persona.class, event.getPersona_id());
        ActivationToken activationToken = new ActivationToken(token, persona);
        em.persist(activationToken);

        //Send it
        SendGrid sendgrid = new SendGrid(organikaProperties.get("sendgrid_api_key"));

        SendGrid.Email email = new SendGrid.Email();
        if (organikaProperties.get("trap_emails").equals("1")) {
            email.addTo(organikaProperties.get("trap_emails_address"));
        } else {
            email.addTo(persona.getEmail());
        }
        String confirmationUrl = "http://" + organikaProperties.get("organika_ip") + ":" + organikaProperties.get("organika_port") + "/" + organikaProperties.get("organika_app") + "/activacion.jsf?token=" + token;
        email.setFrom("padron@agoracasagrande.uy");
        email.setText("Gracias, " + persona.getNombre() + "\n Te registraste en el Padrón de Casa Grande, debemos verificar tu email, por favor haz click en el siguiente link: \n\n" + confirmationUrl);
        email.setSubject("Padrón Casa Grande, activación");

        try {
            SendGrid.Response response = sendgrid.send(email);
            log.info(response.getMessage());
        }
        catch (SendGridException e) {
            log.severe(e.getMessage());
        }

    }
}
