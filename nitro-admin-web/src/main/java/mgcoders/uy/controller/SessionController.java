package mgcoders.uy.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by raul on 21/02/16.
 */
@Named
@SessionScoped
public class SessionController implements Serializable {


    private boolean adminConectado = false;

    public boolean isAdminConectado() {
        return adminConectado;
    }

    public void setAdminConectado(boolean adminConectado) {
        this.adminConectado = adminConectado;
    }
}
