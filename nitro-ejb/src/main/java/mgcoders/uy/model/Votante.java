package mgcoders.uy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by raul on 22/11/15.
 */
@Entity
public class Votante {

    @Id
    @GeneratedValue
    private long id;
}
