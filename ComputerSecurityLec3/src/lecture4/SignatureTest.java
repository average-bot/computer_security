package lecture4;

import sun.misc.SignalHandler;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SignatureTest {
    Signature mySign;
    Signature myVerify;

    public void createSignature(){
        try {
            mySign = Signature.getInstance("SHA1withRSA");
            myVerify = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problem");
            System.out.println(e.toString());
        }
    }

    public byte[] Sign(String message, PrivateKey prK){
        try {
            mySign.initSign(prK);
            mySign.update(message.getBytes());
            return mySign.sign();
        } catch (Exception e) {
            System.out.println("Problems");
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean Verify(String message, PublicKey
            puK,byte[] sig){
        try {
            myVerify.initVerify(puK);
            myVerify.update(message.getBytes());
            return myVerify.verify(sig);
        } catch (Exception e) {
            System.out.println("Problems");
            System.out.println(e.toString());
        }
        return false;
    }
}
