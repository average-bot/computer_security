package lecture4;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        RSATest RsaTest = new RSATest();
        RsaTest.generateKeys();
        RsaTest.createCipher();

        byte[] encrypted = RsaTest.encryptText("Hello world!");
        System.out.println(Arrays.toString(encrypted));


        String decryptedText = RsaTest.decryptText();
        System.out.println(decryptedText);
    }
}
