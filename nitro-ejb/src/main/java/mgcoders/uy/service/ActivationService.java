package mgcoders.uy.service;

import mgcoders.uy.model.ActivationToken;

import javax.ejb.Stateless;
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
    private Logger log;
    @Inject
    private EntityManager em;

    public boolean check(String key) {

        try {

            Query query = em.createNamedQuery("checkToken");
            query.setParameter("token", key);
            ActivationToken activationToken = (ActivationToken) query.getSingleResult();
            if (activationToken.getExpiryDate().after(new Date()) && !activationToken.isVerified()) {
                activationToken.setVerified(true);
                activationToken.getPersona().setEnabled(true);
                return true;
            } else return activationToken.isVerified();

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Verification token was not found");
            return false;

        }

    }
}
