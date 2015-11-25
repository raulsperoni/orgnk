package mgcoders.uy.model;

import java.io.Serializable;

/**
 * Created by RSperoni on 24/11/2015.
 */
public class VotacionAsociacionId implements Serializable {

    private long votacionId;
    private long votanteId;
    private String tokenAutorizacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotacionAsociacionId that = (VotacionAsociacionId) o;

        if (votacionId != that.votacionId) return false;
        if (votanteId != that.votanteId) return false;
        return !(tokenAutorizacion != null ? !tokenAutorizacion.equals(that.tokenAutorizacion) : that.tokenAutorizacion != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (votacionId ^ (votacionId >>> 32));
        result = 31 * result + (int) (votanteId ^ (votanteId >>> 32));
        result = 31 * result + (tokenAutorizacion != null ? tokenAutorizacion.hashCode() : 0);
        return result;
    }
}
