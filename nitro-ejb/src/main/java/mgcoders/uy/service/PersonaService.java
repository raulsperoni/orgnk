package mgcoders.uy.service;

import mgcoders.uy.model.Persona;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by raul on 16/02/16.
 */
@Stateless
public class PersonaService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public void registrar(Persona nuevaPersona) {

    }


}
