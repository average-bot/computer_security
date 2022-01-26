package lecture5;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args){

        KeyStoreClass keystore = new KeyStoreClass();
        String s = "KeyStorePassword";
        char[] keystorePassword = s.toCharArray();
        keystore.createKeystore("clients", keystorePassword); // Store filename, Store password


        String key = "Key"; // Create a secret key for the entry
        byte[] encoded = key.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(encoded, "AES");

        String sPassword = "hobune"; // Password for the keyEntry
        char[] keyPassword = sPassword.toCharArray();

        keystore.storeKey("clients",keystorePassword, secretKey, "Sandra", keyPassword); //Store a key in the keyStore (KeyEntry)
        //String storeFilename, char[] storePassword, SecretKey key, String alias, char[] keyPassword


        //String storeFilename, char[] storePassword, String alias, char[] keyPassword
        SecretKey theKey= keystore.loadKey("clients", keystorePassword, "Sandra", keyPassword);
        byte[] key2 = theKey.getEncoded();
        System.out.println(key2.toString());
    }
}
