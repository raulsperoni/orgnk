package mgcoders.uy.controller;

import mgcoders.uy.model.Frecuencia;
import mgcoders.uy.model.Localidad;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.common.AuxService;
import mgcoders.uy.service.common.PersonaService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by raul on 16/02/16.
 */
@Named
@ViewScoped
public class RegistrationController implements Serializable {

    @Inject
    private FacesContext facesContext;

    @Inject
    private PersonaService personaService;

    @Inject
    private AuxService auxService;

    @Inject
    private AuxController auxController;


    @Inject
    private SessionController sessionController;

    private Persona nuevaPersona;
    private int departamentoSeleccionado;
    private int localidadSeleccionada;
    private String frecuenciaSeleccionada;
    private List<Localidad> localidades;
    private boolean success;


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
    }

    public void registrar() throws Exception {
        try {
            nuevaPersona.setLocalidad(auxController.getLocalidad(localidadSeleccionada));
            if (frecuenciaSeleccionada != null) {
                nuevaPersona.setFrecuencia_aporte(Frecuencia.valueOf(frecuenciaSeleccionada));
            }

            personaService.registrar(nuevaPersona);
            sessionController.setPersonaConectada(nuevaPersona);
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado!", "Le hemos enviado un correo para verificar su notifications."));
            initNewMember();
            success = true;
        } catch (ConstraintViolationException e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "La cédula o el notifications ya existen");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "error del sistema, contáctenos");
            facesContext.addMessage(null, m);
        }
    }

    public void cambioDepartamento(AjaxBehaviorEvent event) {
        localidades = auxController.getLocalidades(departamentoSeleccionado);
        nuevaPersona.setDepartamento(auxController.getDepartamento(departamentoSeleccionado));
        System.out.println("cambio depto");
    }


    public int getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(int departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }

    public int getLocalidadSeleccionada() {
        return localidadSeleccionada;
    }

    public void setLocalidadSeleccionada(int localidadSeleccionada) {
        this.localidadSeleccionada = localidadSeleccionada;
    }

    public String getFrecuenciaSeleccionada() {
        return frecuenciaSeleccionada;
    }

    public void setFrecuenciaSeleccionada(String frecuenciaSeleccionada) {
        this.frecuenciaSeleccionada = frecuenciaSeleccionada;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
