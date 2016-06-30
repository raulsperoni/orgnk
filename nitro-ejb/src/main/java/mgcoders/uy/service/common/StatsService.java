package mgcoders.uy.service.common;

import mgcoders.uy.model.ActivationToken;
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
        Query query = em.createQuery("SELECT a FROM ActivationToken a where a.verified = True");
        return ((List<Departamento>) query.getResultList()).size();
    }

    public String personaInfo(String email) {
        Query query = em.createQuery("SELECT a FROM ActivationToken a,Persona p where a.persona = p and p.email = :email");
        query.setParameter("email", email);
        List<ActivationToken> personas = (List<ActivationToken>) query.getResultList();
        String result = "";
        for (ActivationToken p : personas) {
            result += "Verificado: " + p.isVerified() + (p.isVerified() ? "" : p.getToken()) + "\n";
        }
        return result;
    }


}
