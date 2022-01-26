package lecture3;

import jdk.jshell.Snippet;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        byte[] message={0,0,1,2,3,4,5,6,7,6,5,4,2,3,8,9};
        byte[] key={1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4};
        byte[] ciphertext;
        byte[] decrypted;
        String s1;
        String d;

        Sender s = new Sender();
        s.setKey(key);
        ciphertext = s.send(message);
        s1 = Arrays.toString(ciphertext);
        System.out.println(s1);


        Receiver1 r = new Receiver1();
        r.setKey(key);
        decrypted = r.receive(ciphertext);
        d = Arrays.toString(decrypted);
        System.out.println(d);
    }
}
