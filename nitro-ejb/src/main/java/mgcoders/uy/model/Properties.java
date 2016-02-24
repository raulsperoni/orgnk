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
