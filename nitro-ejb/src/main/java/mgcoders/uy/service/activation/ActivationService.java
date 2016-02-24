package mgcoders.uy.service.activation;

import mgcoders.uy.events.ActivationEvent;
import mgcoders.uy.model.ActivationToken;
import mgcoders.uy.model.Persona;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by raul on 21/02/16.
 */
@Stateless
public class ActivationService {

    @Inject
    Event<ActivationEvent> personaActivadaEvent;
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    public Persona check(String key) {

        try {

            Query query = em.createNamedQuery("checkToken");
            query.setParameter("token", key);
            ActivationToken activationToken = (ActivationToken) query.getSingleResult();
            if (activationToken.getExpiryDate().after(new Date()) && !activationToken.isVerified()) {
                activationToken.setVerified(true);
                activationToken.getPersona().setEnabled(true);
                personaActivadaEvent.fire(new ActivationEvent(activationToken.getPersona().getId(), new Date()));
                return activationToken.getPersona();
            } else return activationToken.getPersona();

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Verification token was not found");
            return null;

        }

    }


}
