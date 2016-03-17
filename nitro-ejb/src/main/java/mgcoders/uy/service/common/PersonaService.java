package mgcoders.uy.service.common;

import mgcoders.uy.events.RegistrationEvent;
import mgcoders.uy.model.Persona;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
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

    public boolean existe(String ci, String email) {
        Query query = em.createQuery("SELECT p FROM Persona p where p.ci = :ci or p.email = :email ");
        query.setParameter("ci", ci);
        query.setParameter("email", email);
        return query.getResultList().size() > 0;
    }

    public Persona buscar(long id) {
        return em.find(Persona.class, id);
    }

    public List<Persona> buscarTodas() {
        return (List<Persona>) em.createQuery("SELECT p from Persona p").getResultList();
    }

    public List<Persona> buscar(String criteria) {
        Query query = em.createQuery("SELECT p FROM Persona p where p.ci like :criteria or p.nombre like :criteria or p.email like :criteria");
        query.setParameter("criteria", criteria);
        return (List<Persona>) query.getResultList();
    }


}
