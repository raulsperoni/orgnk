package mgcoders.uy.util;

import mgcoders.uy.model.Actividad;
import mgcoders.uy.service.activities.ActivityService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by raul on 17/03/16.
 */


@FacesConverter("activityConverter")
public class ActivityConverter implements Converter {

    @Inject
    private ActivityService activityService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Actividad actividad = null;
        if (s != null && s.trim().length() > 0) {
            long id = Long.valueOf(s).longValue();
            actividad = activityService.buscar(id);
        }
        return actividad;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Actividad actividad = (Actividad) o;
        return actividad != null ? String.valueOf(actividad.getId()) : null;
    }
}
