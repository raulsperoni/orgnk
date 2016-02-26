package mgcoders.uy.util;

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
}
