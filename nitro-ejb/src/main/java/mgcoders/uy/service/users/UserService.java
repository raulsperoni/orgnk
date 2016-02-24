package mgcoders.uy.service.users;

import mgcoders.uy.discourse.DiscourseAPIService;
import mgcoders.uy.discourse.DiscourseCreateUserResponse;
import mgcoders.uy.discourse.DiscourseUser;
import mgcoders.uy.events.ActivationEvent;
import mgcoders.uy.model.Persona;

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
    public DiscourseUser nuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) ActivationEvent event) {

        Persona persona = em.find(Persona.class, event.getPersona_id());
        DiscourseUser nuevoUsuario = discourseAPIService.searchUser(persona.getEmail());

        if (nuevoUsuario == null) {
            nuevoUsuario = new DiscourseUser(persona);
            log.info("Usuario no existe creando en discourse... ");
            DiscourseCreateUserResponse response = discourseAPIService.createUser(nuevoUsuario);
            if (response.isSuccess()) {
                nuevoUsuario.setId(response.getUser_id());
                log.info("Usuario creado en el sistema: " + nuevoUsuario.toString());
                if (discourseAPIService.aprobarUsuario(nuevoUsuario)) {
                    nuevoUsuario.setApproved(true);
                    log.info("Usuario aprobado en discourse: " + nuevoUsuario.toString());
                }
            } else {
                nuevoUsuario.setError(true);
                nuevoUsuario.setErrorMessage(response.getMessage());
                log.severe("No se pudo crear usuario en discourse: " + nuevoUsuario.toString());
            }
        } else {
            nuevoUsuario.setPersona(persona);
            nuevoUsuario.setName(persona.getNombre());
            log.info("Usuario existe en discourse... " + nuevoUsuario.getId());
        }
        em.persist(nuevoUsuario);
        log.info("Usuario creado en el sistema: " + nuevoUsuario.toString());
        return nuevoUsuario;

    }

}
