/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.common.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author manosahu
 */
public class EncryptionUtil {

    public static String encrypt(String plainPassword) throws Exception {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }
        try {
            md.update(plainPassword.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }

        byte raw[] = md.digest();
        //TODO look for alternative.
        //String base64String = DatatypeConverter.printBase64Binary(baos.toByteArray());
        //byte[] bytearray = DatatypeConverter.parseBase64Binary(base64String);
        String hash = (new BASE64Encoder()).encode(raw);
        return hash; //step 6
    }
}
