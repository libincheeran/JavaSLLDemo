import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/* what I did
 * in AWS created a keystore for this server with the following command -
 * "keytool -genkey -alias domain -keyalg RSA -validity 365 -keystore mystore.jks"
 * point to the key store in this server file.
 */
public class SSLServerDemo {
    public static void main(String[] arstring) {
     SSLContext sslContext;
     System.out.println("** Servlet start with inbuild SSL **");
        try {
         
         InputStream kis = new FileInputStream("keystore/mystore.jks");
         KeyStore keyStore = KeyStore.getInstance("jks");
         keyStore.load(kis, "libin3137".toCharArray());
         KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
         keyManagerFactory.init(keyStore, "libin3137".toCharArray());
             
         sslContext = SSLContext.getInstance("TLS");
         sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
            
         SSLServerSocketFactory sslserversocketfactory = sslContext.getServerSocketFactory();
         SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9008);
         SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

         InputStream inputstream = sslsocket.getInputStream();
         InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

         String string = null;
         while ((string = bufferedreader.readLine()) != null) {
                System.out.println("From SSL Server :"+string);
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
