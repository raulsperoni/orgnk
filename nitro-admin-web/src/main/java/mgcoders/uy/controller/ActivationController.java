package mgcoders.uy.controller;

import mgcoders.uy.model.Asistencia;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.activation.ActivationService;
import mgcoders.uy.service.activities.ActivityService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by raul on 21/02/16.
 */
@ManagedBean
@RequestScoped
public class ActivationController {

    @Inject
    ActivationService activationService;
    @Inject
    ActivityService activityService;
    @Inject
    private FacesContext facesContext;


    @ManagedProperty(value = "#{param.token}")
    private String key;
    private boolean valid;
    private Persona persona;
    private Asistencia asistencia;

    @PostConstruct
    public void init() {
        //log.info("## token recibido" + key);
        persona = activationService.check(key);
        valid = persona != null;
        asistencia = new Asistencia();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }
}
