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


    private Persona nuevaPersona;
    private int departamentoSeleccionado;
    private int localidadSeleccionada;
    private String frecuenciaSeleccionada;
    private List<Localidad> localidades;
    private boolean success = false;
    private String passwordConfirmation;
    private boolean exterior = false;
    private int paisSeleccionado;


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
        departamentoSeleccionado = 1;
        cambioDepartamento(null);
        localidadSeleccionada = 1;
    }

    public void registrar() throws Exception {
        try {
            nuevaPersona.setDepartamento(auxController.getDepartamento(departamentoSeleccionado));
            nuevaPersona.setLocalidad(auxController.getLocalidad(localidadSeleccionada));
            nuevaPersona.setPais(auxController.getPais(paisSeleccionado));
            if (frecuenciaSeleccionada != null) {
                nuevaPersona.setFrecuencia_aporte(Frecuencia.valueOf(frecuenciaSeleccionada));
            }
            if (nuevaPersona.getPassword().equals(passwordConfirmation)) {
                personaService.registrar(nuevaPersona);
                success = true;
                if (!personaService.existe(nuevaPersona.getCi(), nuevaPersona.getEmail())) {
                    personaService.registrar(nuevaPersona);
                    success = true;
                } else {
                    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo registrar", "El email o la cédula ya existen");
                    facesContext.addMessage(null, m);
                }
            } else {
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "Las contraseñas no coinciden");
                facesContext.addMessage(null, m);
            }
        } catch (ConstraintViolationException e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "La cédula o el email ya existen");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "error del sistema, contáctenos");
            facesContext.addMessage(null, m);
        }
    }

    public void cambioDepartamento(AjaxBehaviorEvent event) {
        exterior = (departamentoSeleccionado == 20);
        localidades = auxController.getLocalidades(departamentoSeleccionado);
        paisSeleccionado = departamentoSeleccionado == 20 ? 0 : 229;
        System.out.println("Depto: " + departamentoSeleccionado + " Loc: " + localidadSeleccionada + " Pais: " + paisSeleccionado);
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
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

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public int getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(int paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }
}
