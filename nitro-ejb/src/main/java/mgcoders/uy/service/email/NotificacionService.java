package mgcoders.uy.service.email;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import mgcoders.uy.service.NuevoRegistroEvent;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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

    public void sendNotificacionRegistro(@Observes(during= TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event){

        log.info(event.toString());

        /*SendGrid sendgrid = new SendGrid("YOUR_SENDGRID_API_KEY");

        SendGrid.Email email = new SendGrid.Email();
        email.addTo("example@example.com");
        email.setFrom("other@example.com");
        email.setSubject("Hello World");
        email.setText("My first email with SendGrid Java!");

        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println(response.getMessage());
        }
        catch (SendGridException e) {
            System.err.println(e);
        } */

    }
}
