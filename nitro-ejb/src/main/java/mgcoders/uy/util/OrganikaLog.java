package mgcoders.uy.util;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by raul on 05/03/16.
 */
@Entity
public class OrganikaLog {

    @Id
    private long id;

    private String className;

    private long classId;

    private String eventDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private boolean error;

    private String errorMessage;

    protected OrganikaLog() {

    }

    public OrganikaLog(Class objectClass, long objectId, String eventDescription) {
        this.error = false;
        this.classId = objectId;
        this.eventDescription = eventDescription;
        this.className = objectClass.getSimpleName();
    }

    public OrganikaLog(Class objectClass, long objectId, String eventDescription, String errorMessage) {
        this.error = true;
        this.errorMessage = errorMessage;
        this.classId = objectId;
        this.eventDescription = eventDescription;
        this.className = objectClass.getSimpleName();
    }
}
