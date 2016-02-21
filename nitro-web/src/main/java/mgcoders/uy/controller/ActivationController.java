package mgcoders.uy.controller;

import mgcoders.uy.service.ActivationService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by raul on 21/02/16.
 */
@ManagedBean
@RequestScoped
public class ActivationController {

    @Inject
    ActivationService activationService;

    @Inject
    private Logger log;


    @ManagedProperty(value = "#{param.token}")
    private String key;
    private boolean valid;

    @PostConstruct
    public void init() {
        log.info("## token recibido" + key);
        valid = activationService.check(key); // And auto-login if valid?
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
}
