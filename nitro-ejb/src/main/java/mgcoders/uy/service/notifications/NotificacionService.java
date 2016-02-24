package mgcoders.uy.service.notifications;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import mgcoders.uy.events.RegistrationEvent;
import mgcoders.uy.model.ActivationToken;
import mgcoders.uy.model.Persona;
import mgcoders.uy.model.Properties;

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
    public void sendNotificacionRegistro(@Observes(during = TransactionPhase.AFTER_SUCCESS) RegistrationEvent event) {

        //Persist verification token
        String token = UUID.randomUUID().toString();
        Persona persona = em.find(Persona.class, event.getPersona_id());
        ActivationToken activationToken = new ActivationToken(token, persona);
        em.persist(activationToken);

        //Send it
        SendGrid sendgrid = new SendGrid(organikaProperties.get(Properties.NOTIFICATION_SENDGRID_API_KEY));

        SendGrid.Email email = new SendGrid.Email();
        if (organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS).equals("1")) {
            email.addTo(organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS_ADDRESS));
        } else {
            email.addTo(persona.getEmail());
        }
        String confirmationUrl = "http://" + organikaProperties.get(Properties.ORGANIKA_IP) + ":" + organikaProperties.get(Properties.ORGANIKA_PORT) + "/" + organikaProperties.get(Properties.ORGANIKA_APP) + "/activacion.jsf?token=" + token;
        email.setFrom(organikaProperties.get(Properties.NOTIFICATION_FROM_ADDRESS));

        String cuerpo = "<div>Gracias " + persona.getNombre() + "!\n" +
                "<div></div>\n" +
                "Te registraste en correctamente el Padr&oacute;n de Casa Grande.\n" +
                "<div>Para poder verificar tu notifications, por favor haz click en el siguiente enlace:</div>\n" +
                "<div></div>\n" +
                "<div><a href=\"" + confirmationUrl + "\">Verificar Email</a></div>\n" +
                "</div>\n" +
                "<div></div>";

        email.setHtml(cuerpo);
        email.setSubject("Padr&oacute;n Casa Grande, activaci&oacute;n");

        try {
            SendGrid.Response response = sendgrid.send(email);
            log.info(response.getMessage());
        }
        catch (SendGridException e) {
            log.severe(e.getMessage());
        }

    }
}
