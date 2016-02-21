package mgcoders.uy.service.users;

import mgcoders.uy.model.Persona;
import mgcoders.uy.service.NuevoRegistroEvent;
import mgcoders.uy.service.discourse.DiscourseAPIService;
import mgcoders.uy.service.discourse.DiscourseUser;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 18/02/2016.
 */
@Stateless
public class UserService {

    @Inject
    DiscourseAPIService discourseAPIService;
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //@Observes(during = TransactionPhase.AFTER_SUCCESS)
    public void nuevoUsuario(NuevoRegistroEvent event) {

        Persona persona = em.find(Persona.class, event.getPersona_id());
        DiscourseUser nuevoUsuario = new DiscourseUser(persona);
        em.persist(nuevoUsuario);

        discourseAPIService.createUser(nuevoUsuario);

        log.info("Usuario creado: " + nuevoUsuario.toString());


    }

}
