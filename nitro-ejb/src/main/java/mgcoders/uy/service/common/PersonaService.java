package mgcoders.uy.service.common;

import mgcoders.uy.events.RegistrationEvent;
import mgcoders.uy.model.Persona;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by raul on 16/02/16.
 */
@Stateless
public class PersonaService {

    @Inject
    Event<RegistrationEvent> nuevoRegistroEvent;
    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    public void registrar(Persona nuevaPersona) {
        em.persist(nuevaPersona);
        RegistrationEvent evt = new RegistrationEvent(nuevaPersona.getId(), new Date());
        nuevoRegistroEvent.fire(evt);
    }


}
