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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Created by raul on 08/03/16.
 */
@ManagedBean
@RequestScoped
public class CheckinController {

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

    private Asistencia nuevaAsistencia;
    private int actividadSeleccionada;
    private List<Actividad> actividadList;
    private String criterioBusqueda;
    private List<Persona> personasList;
    private long personaSeleccionada;


    @ManagedProperty(value = "#{param.token}")
    private String key;
    private boolean valid;

    @PostConstruct
    public void init() {
        if (key != null) {
            sessionController.setAdminConectado(adminService.checkIfAdmin(key));
        }
        valid = sessionController.isAdminConectado();
        actividadList = activityService.actividadesAbiertas();
        nuevaAsistencia = new Asistencia();
    }

    public void registrar() throws Exception {
        try {
            activityService.registrarAsistencia(nuevaAsistencia);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "CheckIn correcto", "bla");
            facesContext.addMessage(null, m);
        } catch (ConstraintViolationException e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "La cédula o el email ya existen");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El registro falló", "error del sistema, contáctenos");
            facesContext.addMessage(null, m);
        }
    }

    public void buscarPersona() {
        personasList = personaService.buscar(criterioBusqueda);
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

    public int getActividadSeleccionada() {
        return actividadSeleccionada;
    }

    public void setActividadSeleccionada(int actividadSeleccionada) {
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

    public long getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(long personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }
}
