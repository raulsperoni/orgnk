package mgcoders.uy.service.voting;

/**
 * Created by RSperoni on 24/11/2015.
 */

import mgcoders.uy.model.VotacionAsociacion;
import mgcoders.uy.model.Votante;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class VotantesService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public boolean existeAsociacionPorToken(String token) {
        Query query = em.createNamedQuery("VotantesService.getAsociacionPorToken", VotacionAsociacion.class);
        query.setParameter("token", token);
        return query.getResultList().size() >= 1;
    }

    public Votante getVotantePorToken(String token) throws Exception {
        Query query = em.createNamedQuery("VotantesService.getAsociacionPorToken", VotacionAsociacion.class);
        query.setParameter("token", token);
        VotacionAsociacion res = (VotacionAsociacion) query.getSingleResult();
        return res.getVotante();
    }

    public Votante findVotantePorId(String id) throws Exception {
        return em.find(Votante.class, id);
    }

    public void setVotanteChatIdPorToken(String token, String chatId) throws Exception {
        Query query = em.createNamedQuery("VotantesService.getAsociacionPorToken", VotacionAsociacion.class);
        query.setParameter("token", token);
        VotacionAsociacion res = (VotacionAsociacion) query.getSingleResult();
        res.setChatId(chatId);
        log.log(Level.INFO, "#########CHATID" + chatId);
        em.merge(res);
    }

    public Votante getVotantePorChatId(String chatId) throws Exception {
        Query query = em.createNamedQuery("VotantesService.getAsociacionPorChatId", VotacionAsociacion.class);
        query.setParameter("chatId", chatId);
        VotacionAsociacion res = (VotacionAsociacion) query.getSingleResult();
        return res.getVotante();
    }

    public void guardarVotante(Votante votante) {
        em.persist(votante);
    }
}
