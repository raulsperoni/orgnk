package mgcoders.uy.service.activities;

import mgcoders.uy.model.Asistencia;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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


}
