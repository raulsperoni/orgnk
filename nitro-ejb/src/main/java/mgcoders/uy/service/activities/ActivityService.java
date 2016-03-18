package mgcoders.uy.service.activities;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.model.Asistencia;
import mgcoders.uy.model.Persona;

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

    public void registrarAsistencia(Persona p, Actividad a) {
        a = em.find(Actividad.class, a.getId());
        p = em.find(Persona.class, p.getId());
        Asistencia asistencia = new Asistencia();
        asistencia.setActividad(a);
        asistencia.setPersona(p);
        em.persist(asistencia);
    }

    public List<Actividad> actividadesAbiertas() {
        Query query = em.createQuery("SELECT a FROM Actividad a where a.fechaDesde >= :now");
        query.setParameter("now", new Date());
        return (List<Actividad>) query.getResultList();
    }


    public Actividad buscar(long id) {
        return em.find(Actividad.class, id);
    }

    public List<Asistencia> buscarAsistencias(Actividad actividadSeleccionada) {
        Query query = em.createQuery("SELECT a FROM Asistencia a where a.actividad = :actividad order by a.persona.nombre asc");
        query.setParameter("actividad", actividadSeleccionada);
        return (List<Asistencia>) query.getResultList();
    }

    public long getActividadCuorum(long id) {
        Query query = em.createQuery("SELECT a FROM Asistencia a where a.actividad.id = :id");
        query.setParameter("id", id);
        return ((List<Asistencia>) query.getResultList()).size();
    }
}
