package lab1;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

public class KeyStoreClass { //Example: Create KeyStore
    public void createKeystore(String filename, char[] password) {
        try {
            KeyStore myStore = KeyStore.getInstance("JCEKS");
            myStore.load(null, null); //first time no file or password
            FileOutputStream storeFile = new FileOutputStream(filename);
            myStore.store(storeFile, password);
            storeFile.close();
        } catch (Exception e) {
            System.out.println("Next time I would maybe throw the exception instead");
            e.printStackTrace();
        }
    }

    public void storeKey(String storeFilename, char[] //Example: Store a key
            storePassword, SecretKey key, String alias, char[] keyPassword) {
        try {
            KeyStore myStore = KeyStore.getInstance("JCEKS");
            FileInputStream loadFile = new FileInputStream(storeFilename);
            myStore.load(loadFile, storePassword); //filename and password that protects the keystore
            loadFile.close();
            KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);
            myStore.setEntry(alias, skEntry, new
                    KeyStore.PasswordProtection(keyPassword));
            FileOutputStream storeFile = new FileOutputStream(storeFilename);
            myStore.store(storeFile, storePassword);
            storeFile.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public PrivateKey loadKey(String storeFilename, char[]
            storePassword, String alias, char[] keyPassword) { //Example: Load a key
        try {
            //start by loading keystore
            KeyStore myStore = KeyStore.getInstance("JCEKS");
            FileInputStream loadFile = new FileInputStream(storeFilename);
            myStore.load(loadFile, storePassword); //filename and password that protects the keystore
            loadFile.close();
            //load key
            PrivateKey theKey = (PrivateKey) myStore.getKey(alias, keyPassword);
            return theKey;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}
