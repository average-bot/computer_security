package lecture5;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Encryption {
    public byte[] encrypt(byte[] plaintext, char[] password) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        byte[] ciphertext = null;
        byte[] salt = new byte[]{0, 1, 2, 3, 4, 5, 6, 7};
        int iterations = 16;
        PBEParameterSpec pbeParamSpec;
        pbeParamSpec = new PBEParameterSpec(salt, iterations);
        PBEKeySpec pbeSpec = new PBEKeySpec(password);
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey myKey = factory.generateSecret(pbeSpec);
        Cipher enc = Cipher.getInstance("PBEWithMD5AndDES");
        enc.init(Cipher.ENCRYPT_MODE, myKey, pbeParamSpec);
        int outlength = enc.getOutputSize(plaintext.length);
        ciphertext = new byte[outlength];
        int decLength = enc.update(plaintext, 0, plaintext.length, ciphertext);
        enc.doFinal(ciphertext, decLength);
        return ciphertext;
    }

    public byte[] decrypt(byte[] ciphertext, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] plaintext = null;
        byte[] salt = new byte[]{0, 1, 2, 3, 4, 5, 6, 7};
        int iterations = 16;
        PBEParameterSpec pbeParamSpec;
        pbeParamSpec = new PBEParameterSpec(salt, iterations);
        PBEKeySpec pbeSpec = new PBEKeySpec(password);
        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey myKey = factory.generateSecret(pbeSpec);
        Cipher dec = Cipher.getInstance("PBEWithMD5AndDES");
        dec.init(Cipher.DECRYPT_MODE, myKey, pbeParamSpec);
        plaintext = dec.doFinal(ciphertext);
        return plaintext;
    }
}
