package mgcoders.uy.controller;

import mgcoders.uy.model.Departamento;
import mgcoders.uy.model.Localidad;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.AuxService;
import mgcoders.uy.service.PersonaService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by raul on 16/02/16.
 */
@Named
@ViewScoped
public class RegistroController implements Serializable {

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonaService personaService;

    @Inject
    private AuxService auxService;

    private Persona nuevaPersona;
    private List<Departamento> departamentos;
    private List<Localidad> localidades;

    @Produces
    @Named
    public Persona getNuevaPersona() {
        return nuevaPersona;
    }

    public void setNuevaPersona(Persona nuevaPersona) {
        this.nuevaPersona = nuevaPersona;
    }

    @PostConstruct
    public void initNewMember() {
        nuevaPersona = new Persona();
        departamentos = auxService.getDepartamentos();
    }

    public void registrar() throws Exception {
        try {
            personaService.registrar(nuevaPersona);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado!", "Registro exitoso"));
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "El registro falló");
            facesContext.addMessage(null, m);
        }
    }

    public void cambioDepartamento(AjaxBehaviorEvent event) {
        localidades = auxService.getLocalidades(nuevaPersona.getDepartamento());
        System.out.println("Cambio loc " + localidades.size());
    }


    @Produces
    @Named
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
}