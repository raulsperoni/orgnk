package mgcoders.uy.service.users;

import mgcoders.uy.model.Persona;
import mgcoders.uy.service.NuevoRegistroEvent;
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
    private Logger log;

    @Inject
    private EntityManager em;

    //@Inject
    //DiscourseAPIService discourseAPIService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void nuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event) {

        Persona persona = em.find(Persona.class, event.getPersona_id());
        DiscourseUser nuevoUsuario = new DiscourseUser(persona);
        em.persist(nuevoUsuario);

        log.info("Usuario creado: " + nuevoUsuario.toString());
        //discourseAPIService.searchUser("raulsperoni@gmail.com");


        //TODO: ACA INVOCAR DISCOURSEBEAN Y METER EL USUARIO POR REST

    }

}
