package lecture3;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Sender {
    private Cipher encoder;
    private SecretKeySpec myKey;
    public Sender()throws Exception{
        //create the cipher
        encoder= Cipher.getInstance("AES");
    }
    public void setKey(byte[] key) throws Exception{
        //creates the key using provided bytes
        //and initiate the cipher for encryption
        myKey= new SecretKeySpec(key,"AES");
        encoder.init(Cipher.ENCRYPT_MODE, myKey);
    }
    public byte[] send(byte[] message) throws Exception{
        return encoder.doFinal(message);
    }
}
