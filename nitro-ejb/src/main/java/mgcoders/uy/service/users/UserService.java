package mgcoders.uy.service.users;

import mgcoders.uy.model.Persona;
import mgcoders.uy.model.Usuario;
import mgcoders.uy.service.NuevoRegistroEvent;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by RSperoni on 18/02/2016.
 */
public class UserService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public void nuevoUsuario(@Observes(during = TransactionPhase.AFTER_SUCCESS) NuevoRegistroEvent event) {

        Persona persona = event.getPersona();
        boolean usuarioHabilitado = persona.getEmail() != null;

        Usuario usuario = new Usuario();
        usuario.setPersona(persona);
        usuario.setNombreUsuario(usuarioHabilitado ? persona.getEmail() : persona.getNombre().toLowerCase().replace(' ', '_'));
        usuario.setAllowLogin(usuarioHabilitado);

        try {
            em.persist(usuario);
            log.info("Usuario creado: " + usuario.toString());
        } catch (Exception e) {
            log.severe(e.getMessage());
        }


    }

}
