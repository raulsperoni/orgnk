package mgcoders.uy.data;

import mgcoders.uy.model.*;
import mgcoders.uy.service.VotacionService;
import mgcoders.uy.service.VotantesService;
import mgcoders.uy.service.discourse.DiscourseAPIService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        //Esto deberia ir en el import.sql

        Votante votante = new Votante();
        votante.setNombreUsuario("raul");
        votantesService.guardarVotante(votante);
        Votacion votacion = new Votacion();
        Pregunta pregunta = new Pregunta();
        pregunta.setExplicacion("Algo aca");
        pregunta.setPregunta("MgCoders o los bobos de la ORT?");
        pregunta.setRespuestasMinimas(0);
        pregunta.setRespuestasMaximas(1);
        Opcion opcion = new Opcion();
        opcion.setDescripcion("si");
        pregunta.getOpciones().add(opcion);
        Opcion opcionno = new Opcion();
        opcionno.setDescripcion("no");
        pregunta.getOpciones().add(opcionno);
        votacion.getPreguntas().add(pregunta);
        votacion.setFechaCreacion(new Date());
        votacion.setFechaInicio(new Date());
        votacion.setFechaFin(new Date());
        votacion.addVotante(votante);
        votacionService.guardarVotacion(votacion);


        em.flush();


        log.info("################# BD" + votante.getVotaciones().get(0).getTokenAutorizacion());


        Actividad actividad = new Actividad();
        actividad.setNombre("Encuentro Nacional");

        try {
            actividad.setFechaDesde(simpleDateFormat.parse("12/03/2016 08:30"));
            actividad.setFechaHasta(simpleDateFormat.parse("13/03/2016 16:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        actividad.setDescripcion("Seguiremos consolidandonos como organización política y estaremos discutiendo también sobre nuestra otra casa, el Frente Amplio.");
        em.persist(actividad);
    }


}
