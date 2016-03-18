package mgcoders.uy.controller;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.model.Asistencia;
import mgcoders.uy.model.Persona;
import mgcoders.uy.service.activities.ActivityService;
import mgcoders.uy.service.admin.AdminService;
import mgcoders.uy.service.common.PersonaService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 08/03/16.
 */
@ManagedBean
@ViewScoped
public class CheckinController {

    List<Asistencia> asistenciaList;
    @Inject
    private SessionController sessionController;
    @Inject
    private AdminService adminService;
    @Inject
    private ActivityService activityService;
    @Inject
    private FacesContext facesContext;
    @Inject
    private PersonaService personaService;
    private Actividad actividadSeleccionada;
    private List<Actividad> actividadList;
    private String criterioBusqueda;
    private List<Persona> personasList;
    private Persona personaSeleccionada;
    private String key;
    private boolean valid;

    @PostConstruct
    public void init() {
        key = String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token"));
        if (key != null) {
            sessionController.setAdminConectado(adminService.checkIfAdmin(key));
        }
        valid = sessionController.isAdminConectado();
        actividadList = activityService.actividadesAbiertas();
        actividadSeleccionada = actividadList.size() > 0 ? actividadList.get(0) : null;
        personasList = personaService.buscarTodas();
        buscarAsistencias();
    }

    public void buscarAsistencias() {
        asistenciaList = activityService.buscarAsistencias(actividadSeleccionada);
    }

    public void registrar() throws Exception {
        try {
            activityService.registrarAsistencia(personaSeleccionada, actividadSeleccionada);
            personaSeleccionada = null;
            buscarAsistencias();
        } catch (ConstraintViolationException e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "La cédula o el email ya existen");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "error del sistema, contáctenos");
            facesContext.addMessage(null, m);
        }
    }

    public List<Persona> buscarPersona(String criterioBusqueda) {
        List<Persona> selected = new ArrayList<>();
        for (Persona p : personasList) {
            if (p.getNombre().toLowerCase().contains(criterioBusqueda.toLowerCase()) || p.getCi().contains(criterioBusqueda) || p.getEmail().toLowerCase().contains(criterioBusqueda.toLowerCase())) {
                selected.add(p);
            }
        }
        return selected;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Actividad getActividadSeleccionada() {
        return actividadSeleccionada;
    }

    public void setActividadSeleccionada(Actividad actividadSeleccionada) {
        this.actividadSeleccionada = actividadSeleccionada;
    }

    public List<Actividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public List<Persona> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Persona> personasList) {
        this.personasList = personasList;
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
    }
}
