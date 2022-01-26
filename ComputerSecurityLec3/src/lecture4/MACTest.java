package lecture4;

//import org.graalvm.compiler.java.LambdaUtils;
import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class MACTest {
    Mac mac;
    public void createMac(){
        try {
            mac= Mac.getInstance("HmacMD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Problems");
        }
    }

    public String calculateMac(String message, Key key){
        byte[] macVal=null;
        try {
            mac.init(key);
            mac.update(message.getBytes());
            macVal=mac.doFinal();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte i: macVal){
                stringBuilder.append(String.format("%02%", i));
            }
            return stringBuilder.toString();
            //return(DatatypeConverter.printHexBinary(macVal));
        } catch (InvalidKeyException e) {
            System.out.println("Problems");
        }
        return "";
    }
}
