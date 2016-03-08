package mgcoders.uy.service.admin;

import mgcoders.uy.model.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by raul on 08/03/16.
 */
@Stateless
public class AdminService {

    @Inject
    private Map<String, String> organikaProperties;

    public boolean checkIfAdmin(String token) {
        return token.equals(organikaProperties.get(Properties.ADMIN_TOKEN));
    }

}
