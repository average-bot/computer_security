package lab1;

//import org.graalvm.compiler.java.LambdaUtils; did not work

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;

/*      0. Get the private key to encrypt the other keys from the lab1Store
        1. EncKey 1: 128-bytes (1024 bits) of a symmetric key encrypted with RSA using the public key “lab1EncKeys”
        2. EncIV value: 128-byte of encrypted IV-value to be used in the decryption of the data
        3. EncHmacMD5 key: 128-byte containing encrypted key for a HmacMD5
        4. Ciphertext: Finally we have the encrypted data. The encryption is made with AES in CBC mode, using PKCS5 padding.*/
/*
        To get the keys and IV to be used to decrypt we need a private key.
        The key is stored in the keystore “lab1Store”. The password to access lab1Store is “lab1StorePass”. The
        alias for the private key is “lab1EncKeys” (bad name as already used to name its
        associated public key), and the password is “lab1KeyPass”.*/

public class Main {
    public static void main(String[] args) throws IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, CertificateException, SignatureException {
        // Get the encrypted file
        File f = new File("src/lab1/ciphertext.enc");
        FileInputStream fis = new FileInputStream(f);

        //Get the private key from lab1Store  LAB1 encKey
        char [] storePassword = "lab1StorePass".toCharArray();
        char [] keyPassword = "lab1KeyPass".toCharArray();
        KeyStoreClass keyStore = new KeyStoreClass();
        PrivateKey Lab1EncKey = keyStore.loadKey("src/lab1/lab1Store", storePassword, "lab1EncKeys", keyPassword);
        //loadKey parameters:            loadKey(String storeFilename, char[] storePassword, String alias, char[] keyPassword)


        //Find keys 1, 2 and IV (decrypt with the private key)
        //Key1 from EncKey1
        byte[] encKey1 = new byte[128];
        fis.read(encKey1); // Reads the encKey1.length bytes of data
        Cipher rsaDec=Cipher.getInstance("RSA");
        rsaDec.init(Cipher.DECRYPT_MODE, Lab1EncKey);
        byte[] key1 = rsaDec.doFinal(encKey1);

        //IV from EncIV
        byte[] encIV = new byte[128];
        fis.read(encIV);
        byte[] IV = rsaDec.doFinal(encIV);

        //Key2 from encKey2 (EncHmacMD5)
        byte[] encKey2 = new byte[128];
        fis.read(encKey2);
        byte [] key2 = rsaDec.doFinal(encKey2);

        //Ciphertext
        byte[] ciphertext = fis.readAllBytes();
        fis.close();


        //Decrypt to plaintext byte array
        Cipher decoder = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keyOne = new SecretKeySpec(key1, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        decoder.init(Cipher.DECRYPT_MODE, keyOne, ivSpec);
        byte[] message = decoder.doFinal(ciphertext);

        String[] plain = new String[]{new String(message, StandardCharsets.UTF_8)};
        String plaintext = plain[0];
        System.out.println(plaintext);

        //Verify MAC
        verifyMAC(message, key2);

        //Verify the digital signature
        verifyDigitalSignature(message);

    }

    public static void verifyMAC(byte[] message, byte[]key2) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        //comparing MAC
        File file1 = new File("src/lab1/ciphertext.mac1.txt");
        FileInputStream fis1 = new FileInputStream(file1);
        byte [] MAC1bytes = fis1.readAllBytes();
        char[] MAC1chars = new String(MAC1bytes, "UTF-8").toCharArray();
        String MAC1value = String.valueOf(MAC1chars);

        File file2 = new File("src/lab1/ciphertext.mac2.txt");
        FileInputStream fis2 = new FileInputStream(file2);
        byte [] MAC2bytes = fis2.readAllBytes();
        char[] MAC2chars = new String(MAC2bytes, "UTF-8").toCharArray();
        String MAC2value = String.valueOf(MAC2chars);


        Mac mac = Mac.getInstance("HmacMD5");
        SecretKeySpec keyTwo = new SecretKeySpec(key2, "AES");
        mac.init(keyTwo);
        byte [] MACbytes = mac.doFinal(message);

        StringBuilder stringBuilder = new StringBuilder();
        for (byte bytes: MACbytes){
            //System.out.println("bytes"+bytes);
            stringBuilder.append(String.format("%02X", bytes).toLowerCase(Locale.ROOT));
            //System.out.println(String.format("%02X", bytes));
        }
        String MACvalue = stringBuilder.toString();


        if (MACvalue.equals(MAC1value)){
            System.out.println("MAC1 is true");
        }
        if (MACvalue.equals(MAC2value)){
            System.out.println("MAC2 is true");
        }
    }


    public static void verifyDigitalSignature(byte[] message) throws IOException, CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //Verify the digital signature
        //Source: https://www.tabnine.com/code/java/methods/java.security.cert.CertificateFactory/getInstance
        FileInputStream readPuKey = new FileInputStream("src/lab1/lab1Sign.cert");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate)cf.generateCertificate(readPuKey);
        //Certificate certificate = (Certificate) cf.generateCertificate(readPuKey);
        //PublicKey puKey = certificate.getPublicKey();
        PublicKey puKey = certificate.getPublicKey();

        //SHA1withRSA
        FileInputStream signature1 = new FileInputStream("src/lab1/ciphertext.enc.sig1");
        byte[] signatures1 = signature1.readAllBytes();

        FileInputStream signature2 = new FileInputStream("src/lab1/ciphertext.enc.sig2");
        byte[] signatures2 = signature2.readAllBytes();

        Signature myVerify = Signature.getInstance("SHA1withRSA");
        myVerify.initVerify(puKey);

        myVerify.update(message);
        Boolean firstSignature = myVerify.verify(signatures1);
        myVerify.update(message);
        Boolean secondSignature = myVerify.verify(signatures2);
        System.out.println("First signature is "+firstSignature+" and the second signature is "+secondSignature+"");
    }
}
