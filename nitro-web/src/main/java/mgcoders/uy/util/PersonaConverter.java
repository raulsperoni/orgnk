package mgcoders.uy.util;

import mgcoders.uy.model.Persona;
import mgcoders.uy.service.common.PersonaService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by raul on 17/03/16.
 */


@FacesConverter("personaConverter")
public class PersonaConverter implements Converter {

    @Inject
    private PersonaService personaService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Persona persona = null;
        if (s != null && s.trim().length() > 0) {
            long id = Long.valueOf(s).longValue();
            persona = personaService.buscar(id);
        }
        return persona;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Persona persona = (Persona) o;
        return persona != null ? String.valueOf(persona.getId()) : null;
    }
}
