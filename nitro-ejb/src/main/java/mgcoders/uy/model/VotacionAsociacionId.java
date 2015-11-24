package mgcoders.uy.model;

import java.io.Serializable;

/**
 * Created by RSperoni on 24/11/2015.
 */
public class VotacionAsociacionId implements Serializable {

    private long votacionId;
    private long votanteId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotacionAsociacionId that = (VotacionAsociacionId) o;

        if (votacionId != that.votacionId) return false;
        return votanteId == that.votanteId;

    }

    @Override
    public int hashCode() {
        int result = (int) (votacionId ^ (votacionId >>> 32));
        result = 31 * result + (int) (votanteId ^ (votanteId >>> 32));
        return result;
    }
}
