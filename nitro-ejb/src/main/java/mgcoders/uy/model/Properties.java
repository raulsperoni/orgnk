package mgcoders.uy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Created by raul on 23/02/16.
 */
@Entity
@NamedQuery(name = "Properties.findAll", query = "select p from Properties p")
public class Properties {

    public static final String NOTIFICATION_SENDGRID_API_KEY = "sendgrid_api_key";
    public static final String NOTIFICATION_TRAP_EMAILS = "trap_emails";
    public static final String NOTIFICATION_TRAP_EMAILS_ADDRESS = "trap_emails_address";
    public static final String NOTIFICATION_FROM_ADDRESS = "from_address";
    public static final String ORGANIKA_IP = "organika_ip";
    public static final String ORGANIKA_PORT = "organika_port";
    public static final String ORGANIKA_APP = "organika_app";
    public static final String DISCOURSE_API_KEY = "discourse_api_key";
    public static final String DISCOURSE_API_USER = "discourse_api_user";
    public static final String DISCOURSE_API_HOST = "discourse_api_host";



    @Id
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
