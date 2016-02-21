package mgcoders.uy.service.users;

import mgcoders.uy.model.Persona;
import mgcoders.uy.service.PersonaActivadaEvent;
import mgcoders.uy.service.discourse.DiscourseAPIService;
import mgcoders.uy.service.discourse.DiscourseUser;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
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
    public void nuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) PersonaActivadaEvent event) {

        Persona persona = em.find(Persona.class, event.getPersona_id());


        DiscourseUser nuevoUsuario = discourseAPIService.searchUser(persona.getEmail());

        if (nuevoUsuario == null) {
            nuevoUsuario = new DiscourseUser(persona);

        }

        em.persist(nuevoUsuario);

        long discourse_user_id = discourseAPIService.createUser(nuevoUsuario);
        nuevoUsuario.setDiscourseUserId(discourse_user_id);

        log.info("Usuario creado: " + nuevoUsuario.toString());


    }

}
