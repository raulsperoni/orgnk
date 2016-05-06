package mgcoders.uy.service.notifications;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import mgcoders.uy.events.ActivationEvent;
import mgcoders.uy.events.DiscourseUserCreatedEvent;
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
        String host;
        if (organikaProperties.get(Properties.ORGANIKA_USE_HOST).equals("1")) {
            host = organikaProperties.get(Properties.ORGANIKA_HOST);
        } else {
            host = "http://" + organikaProperties.get(Properties.ORGANIKA_IP) + ":" + organikaProperties.get(Properties.ORGANIKA_PORT);
        }
        String confirmationUrl = host + "/activacion.jsf?token=" + token;
        email.setFrom(organikaProperties.get(Properties.NOTIFICATION_FROM_ADDRESS));

        String cuerpo = "<div>Gracias " + persona.getNombre() + "!\n" +
                "<div>\n</div>" +
                "Te registraste correctamente en el Padr&oacute;n de Casa Grande.\n" +
                "<div>Para poder verificar tu email, por favor haz click en el siguiente enlace:</div>\n" +
                "<div>\n</div>" +
                "<div><a href=\"" + confirmationUrl + "\">Click aqu&iacute; para verificar Email</a></div>\n" +
                "<div>\n\n\n\n</div>";

        email.setHtml(cuerpo);
        email.setSubject("Padrón Casa Grande, activación");

        try {
            SendGrid.Response response = sendgrid.send(email);
            log.info(response.getMessage());
        }
        catch (SendGridException e) {
            log.severe(e.getMessage());
        }

    }

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendNotificacionResponsablesRegistro(@Observes(during = TransactionPhase.AFTER_SUCCESS) ActivationEvent event) {


        Persona persona = em.find(Persona.class, event.getPersona_id());


        //Send it
        SendGrid sendgrid = new SendGrid(organikaProperties.get(Properties.NOTIFICATION_SENDGRID_API_KEY));

        SendGrid.Email email = new SendGrid.Email();
        if (organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS).equals("1")) {
            email.addTo(organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS_ADDRESS));
        } else {
            email.addTo(new String[]{organikaProperties.get(Properties.NOTIFICATION_NUEVO_AGORA), organikaProperties.get(Properties.NOTIFICATION_NUEVO_COMUNICACION),
                    organikaProperties.get(Properties.NOTIFICATION_NUEVO_FINANZAS), organikaProperties.get(Properties.NOTIFICATION_NUEVO_ORGANIZACION)});
        }


        email.setFrom(organikaProperties.get(Properties.NOTIFICATION_FROM_ADDRESS));

        String cuerpo = "<div>Responsables, se acaba de registrar en el padr&oacute;n " + persona.getNombre() + ".\n" +
                "<div>\n</div>" +
                "<div>Su email es: " + persona.getEmail() + "\n</div>" +
                "<div>Su telefono 1 es: " + persona.getTelefono_1() + "\n</div>" +
                "<div>Su telefono 2 es: " + persona.getTelefono_2() + "\n</div>" +
                "<div>Departamento: " + persona.getDepartamento().getNombre() + "\n</div>" +
                "<div>Aporte: " + persona.getFrecuencia_aporte().name() + " de: " + persona.getMonto_aporte() + "\n</div>" +
                "<div>A lo suyo! ;)\n</div>" +
                "<div>\n\n\n\n</div>";

        email.setHtml(cuerpo);
        email.setSubject("[Casa Grande] Nuevo integrante del Padrón");

        try {
            SendGrid.Response response = sendgrid.send(email);
            log.info(response.getMessage());
        } catch (SendGridException e) {
            log.severe(e.getMessage());
        }

    }

    @Asynchronous
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendNotificacionNuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) DiscourseUserCreatedEvent event) {

        //Persist verification token
        Persona persona = em.find(Persona.class, event.getPersona_id());

        //Send it
        SendGrid sendgrid = new SendGrid(organikaProperties.get(Properties.NOTIFICATION_SENDGRID_API_KEY));

        SendGrid.Email email = new SendGrid.Email();
        if (organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS).equals("1")) {
            email.addTo(organikaProperties.get(Properties.NOTIFICATION_TRAP_EMAILS_ADDRESS));
        } else {
            email.addTo(persona.getEmail());
        }

        email.setFrom(organikaProperties.get(Properties.NOTIFICATION_FROM_ADDRESS));

        String cuerpo = "<div>" + persona.getNombre() + ", ya sos parte.\n</div>" +
                "<div>\n</div>" +
                "<div>Podr&aacute;s ingresar en nuestro espacio de discusi&oacute;n online haciendo click en\n</div>" +
                "<div><a href=\"" + "http://agoracasagrande.uy" + "\">http://agoracasagrande.uy</a>\n</div>" +
                "<div>\n</div>" +
                "<div>Tu nombre de usuario es tu email, tu contraseña temporal es:\n</div>" +
                "<div>\n</div>" +
                "<div>" + event.getPassword() + "\n</div>" +
                "<div>\n\n\n\n</div>";

        email.setHtml(cuerpo);
        email.setSubject("Plataforma de participación, nuevo usuario");

        try {
            SendGrid.Response response = sendgrid.send(email);
            log.info(response.getMessage());
        } catch (SendGridException e) {
            log.severe(e.getMessage());
        }

    }
}
