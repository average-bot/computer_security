/*package lecture4;

import org.graalvm.compiler .java.LambdaUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashTest {
    private MessageDigest md;
    private LambdaUtils ByteTool;

    public void createMessageDigest(){
        //create the message digest
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problems");
        }
    }

    public String calculateHash(String message){
        byte[] data=message.getBytes();
        md.update(data);
        byte[] hashVal=md.digest();
        return ByteTool.toHex(hashVal); //, hashVal.length
    }
}*/
//Control shift NUM/