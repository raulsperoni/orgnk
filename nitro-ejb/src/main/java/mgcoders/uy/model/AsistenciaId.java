package mgcoders.uy.model;

import java.io.Serializable;

/**
 * Created by RSperoni on 23/02/2016.
 */
public class AsistenciaId implements Serializable {

    private long persona;
    private long actividad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AsistenciaId that = (AsistenciaId) o;

        if (persona != that.persona) return false;
        return actividad == that.actividad;

    }

    @Override
    public int hashCode() {
        int result = (int) (persona ^ (persona >>> 32));
        result = 31 * result + (int) (actividad ^ (actividad >>> 32));
        return result;
    }
}
