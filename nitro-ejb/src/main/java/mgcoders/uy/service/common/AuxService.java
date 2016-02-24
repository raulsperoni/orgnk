package mgcoders.uy.service.common;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.model.Departamento;
import mgcoders.uy.model.Localidad;

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
public class AuxService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;


    public List<Departamento> getDepartamentos() {
        Query query = em.createQuery("SELECT d FROM Departamento d");
        return (List<Departamento>) query.getResultList();
    }

    public List<Localidad> getLocalidades(Departamento d) {
        Query query = em.createQuery("SELECT l FROM Localidad l where l.departamento.id = :depto");
        query.setParameter("depto", d.getId());
        return (List<Localidad>) query.getResultList();
    }

    public List<Actividad> getActividades() {
        Query query = em.createQuery("SELECT a FROM Actividad a");
        return (List<Actividad>) query.getResultList();
    }
}
