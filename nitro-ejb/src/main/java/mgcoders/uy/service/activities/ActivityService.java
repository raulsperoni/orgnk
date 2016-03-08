package mgcoders.uy.service.activities;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.model.Asistencia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 23/02/2016.
 */
@Stateless
public class ActivityService {

    @Inject
    private Logger log;
    @Inject
    private EntityManager em;

    public void registrarAsistencia(Asistencia asistencia) {
        em.persist(asistencia);
    }

    public List<Actividad> actividadesAbiertas() {
        Query query = em.createQuery("SELECT a FROM Actividad a where a.fechaDesde >= :now");
        query.setParameter("now", new Date());
        return (List<Actividad>) query.getResultList();
    }


}
