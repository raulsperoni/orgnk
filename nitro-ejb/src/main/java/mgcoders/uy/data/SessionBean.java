package mgcoders.uy.data;

import mgcoders.uy.model.Votacion;
import mgcoders.uy.model.Votante;
import mgcoders.uy.service.VotacionService;
import mgcoders.uy.service.VotantesService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by raul on 24/11/15.
 */
@Singleton(name = "SessionEJB")
@Startup
public class SessionBean {

    @Inject
    VotacionService votacionService;

    @Inject
    VotantesService votantesService;

    @Inject
    private EntityManager em;

    @Inject
    private Logger log;


    public SessionBean() {
    }

    @PostConstruct
    public void init() {
        Votante votante = new Votante();
        votante.setNombreUsuario("raul");
        votantesService.guardarVotante(votante);
        Votacion votacion = new Votacion();
        votacion.setFechaCreacion(new Date());
        votacion.setFechaInicio(new Date());
        votacion.setFechaFin(new Date());
        votacion.addVotante(votante);
        votacionService.guardarVotacion(votacion);

        em.flush();


        log.info("################# BD" + votante.getVotaciones().get(0).getTokenAutorizacion());

    }
}
