package mgcoders.uy.data;

import mgcoders.uy.discourse.DiscourseAPIService;
import mgcoders.uy.model.Properties;
import mgcoders.uy.service.voting.VotacionService;
import mgcoders.uy.service.voting.VotantesService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by raul on 24/11/15.
 */
@Singleton(name = "SessionEJB")
@Startup
public class SessionBean {

    @Produces
    private static Map<String, String> organikaProperties = new HashMap<>();
    @Inject
    VotacionService votacionService;
    @Inject
    VotantesService votantesService;
    @Inject
    DiscourseAPIService discourseAPIService;
    @Inject
    private EntityManager em;
    @Inject
    private Logger log;
    @Inject
    private SimpleDateFormat simpleDateFormat;



    public SessionBean() {
    }

    @PostConstruct
    public void init() {

        //Properties
        List<Properties> propertiesList = em.createNamedQuery("Properties.findAll", Properties.class).getResultList();
        for (Properties p : propertiesList) {
            organikaProperties.put(p.getKey(), p.getValue());
        }
    }
}
