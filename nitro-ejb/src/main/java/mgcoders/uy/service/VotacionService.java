package mgcoders.uy.service;

import mgcoders.uy.model.Votacion;
import mgcoders.uy.model.VotacionAsociacion;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 24/11/2015.
 */
@Stateless
public class VotacionService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private VotantesService votantesService;

    public List<Votacion> getVotacionesActivas(String tokenVotante) throws Exception {
        javax.persistence.Query query = em.createNamedQuery("VotantesService.getAsociacionActivaPorToken", VotacionAsociacion.class);
        query.setParameter("token", tokenVotante);
        VotacionAsociacion res = (VotacionAsociacion) query.getSingleResult();
    }
}
