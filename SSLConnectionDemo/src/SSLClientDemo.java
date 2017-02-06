import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/* 
 * copy the .jks file from the server and put in the client's truststore.
 */
public class SSLClientDemo {
    public static void main(String[] arstring) {
     SSLContext sslContext;
        try {
         
           InputStream kis = new FileInputStream("C:\\libin\\test\\mystore.jks");
            KeyStore trustStore = KeyStore.getInstance("jks");
            trustStore.load(kis, "libin3137".toCharArray());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
                     
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            
             SSLSocketFactory sslsocketfactory = sslContext.getSocketFactory();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("54.245.38.237", 9008);

            InputStream inputstream = System.in;
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            OutputStream outputstream = sslsocket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

            String string = null;
            while ((string = bufferedreader.readLine()) != null) {
                bufferedwriter.write(string + '\n');
                bufferedwriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}