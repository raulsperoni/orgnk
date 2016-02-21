package mgcoders.uy.controller;

import mgcoders.uy.model.Persona;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by raul on 21/02/16.
 */
@Named
@SessionScoped
public class SessionController implements Serializable {

    private Persona personaConectada;

    public Persona getPersonaConectada() {
        return personaConectada;
    }

    public void setPersonaConectada(Persona personaConectada) {
        this.personaConectada = personaConectada;
    }
}
