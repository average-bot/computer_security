package lecture4;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSATest {
    private Cipher encoder;
    private Cipher decoder;
    private byte[] ciphertext;
    private byte[] plaintext;
    private byte[] decrypted;
    private RSAPublicKey myPublic;
    private RSAPrivateKey myPrivate;

    public void generateKeys(){
        try{
            //create random key 
            KeyPairGenerator keyGen=
                    KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            KeyPair myPair=keyGen.generateKeyPair();
            myPublic = (RSAPublicKey) myPair.getPublic();
            myPrivate = (RSAPrivateKey) myPair.getPrivate();
        }
        catch(Exception ex){
            System.out.println("Problems");
        }
    }
    public void createCipher(){
        //initiates encryptionBox1 and decryptionBox2
        try{
            encoder = Cipher.getInstance("RSA");
            encoder.init(Cipher.ENCRYPT_MODE,myPublic);
            decoder = Cipher.getInstance("RSA");
            decoder.init(Cipher.DECRYPT_MODE,myPrivate);
        }
        catch(Exception ex){
            System.out.println("Problems");
        }
    }

    public byte[] encryptText(String message){
        try{
            plaintext = message.getBytes(); //transform message to byte[]
            ciphertext = new byte[encoder.getOutputSize(plaintext.length)];
            int ctLength = encoder.update(plaintext,0,plaintext.length,ciphertext,0);
            encoder.doFinal(ciphertext,ctLength);//int doFinal(byte[] output, int outputOffset)
            return ciphertext;
        }
        catch(Exception ex){
            System.out.println("Problems");
        }
        return new byte[0];
    } //getOutputSize() returns the length in bytes that the output buffer would need. The actual output length of the next update or doFinal may be smaller .


    public String decryptText(){
        try{
            decrypted = new byte[decoder.getOutputSize(ciphertext.length)];
            int decLength = decoder.doFinal(ciphertext,0,ciphertext.length,decrypted);
            String decryptedString = new String(decrypted,0,decLength);
            return decryptedString;
        }
        catch(Exception ex){
            System.out.println("Problems");
        }
        return "";
    }
}
