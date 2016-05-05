package mgcoders.uy.util;

import java.util.UUID;

/**
 * Created by RSperoni on 26/02/2016.
 */
public class Util {

    public static String obtenerHashPassword(long id, String password) {
        String seed = "$$.password.seed.$$";
        String hash = id + password + seed;
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(hash);
        return sha256hex;
    }

    public static String generarPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(11);
    }

    public static String generarNombreUsuario(String nombreCompleto) {
        String base = nombreCompleto.toLowerCase();
        base = base.replaceAll("[^A-Za-z0-9]", "");
        return base.length() > 20 ? base.substring(0, 19) : base;
    }
}
