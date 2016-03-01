package mgcoders.uy.model;

import java.io.Serializable;

/**
 * Created by RSperoni on 24/11/2015.
 */
public class VotacionAsociacionId implements Serializable {

    private long votacion;
    private long votingUser;
    private String tokenAutorizacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotacionAsociacionId that = (VotacionAsociacionId) o;

        if (votacion != that.votacion) return false;
        if (votingUser != that.votingUser) return false;
        return !(tokenAutorizacion != null ? !tokenAutorizacion.equals(that.tokenAutorizacion) : that.tokenAutorizacion != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (votacion ^ (votacion >>> 32));
        result = 31 * result + (int) (votingUser ^ (votingUser >>> 32));
        result = 31 * result + (tokenAutorizacion != null ? tokenAutorizacion.hashCode() : 0);
        return result;
    }
}
