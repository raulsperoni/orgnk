package mgcoders.uy.service.voting;

import mgcoders.uy.model.Pregunta;
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

    public Votacion getVotacionPorChatIdToken(String chatId, String token) throws Exception {
        javax.persistence.Query query = em.createNamedQuery("VotantesService.getAsociacionPorChatId", VotacionAsociacion.class);
        query.setParameter("chatId", chatId);
        query.setParameter("token", token);
        VotacionAsociacion res = (VotacionAsociacion) query.getSingleResult();
        return res.getVotacion();
    }

    public void guardarVotacion(Votacion votacion) {
        em.persist(votacion);
    }

    public List<Pregunta> getPreguntasPorChatIdToken(String chatId, String token) throws Exception {
        return getVotacionPorChatIdToken(chatId, token).getPreguntas();
    }
}
