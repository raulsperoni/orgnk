package mgcoders.uy.service.common;

import mgcoders.uy.model.Departamento;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by raul on 16/02/16.
 */
@Stateless
public class StatsService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public long countPersonas() {
        Query query = em.createQuery("SELECT p FROM Persona p");
        return ((List<Departamento>) query.getResultList()).size();
    }

    public long countActivadas() {
        Query query = em.createQuery("SELECT p FROM Persona p join ActivationToken a on a.id=p.id where a.verified = True");
        return ((List<Departamento>) query.getResultList()).size();
    }


}
