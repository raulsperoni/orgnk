package mgcoders.uy.service.users;

import mgcoders.uy.service.NuevoRegistroEvent;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 18/02/2016.
 */
public class UserService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public void nuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event) {

        log.info("Usuario creado: " + event.getPersona().getUsuario().toString());



    }

}
