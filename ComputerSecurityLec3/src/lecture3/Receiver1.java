package lecture3;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Receiver1 {
    private Cipher decoder;
    private SecretKeySpec myKey;

    public Receiver1() throws Exception {
        //create the cipher
        decoder = Cipher.getInstance("AES");
    }

    public void setKey(byte[] key) throws Exception {
        //creates the key using provided bytes
        //and initiate the cipher for encryption
        myKey = new SecretKeySpec(key, "AES");
        decoder.init(Cipher.DECRYPT_MODE, myKey);
    }

    public byte[] receive(byte[] message) throws Exception {
        return decoder.doFinal(message);
    }
}
