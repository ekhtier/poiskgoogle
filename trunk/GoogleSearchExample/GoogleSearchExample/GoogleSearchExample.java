import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/** 

 * @author Ganesh Gowtham

 * Proxy class (HttpAuthenticateProxy) needs to be used

 * when you are using the internet using the proxy authetication

 * http.proxyHost- Proxy Server Name

 * http.proxyPort- Proxy Port Number

 * proxyuserName - Proxy Server UserName

 * proxyPwd - Proxy Server Password 

 */

public class GoogleSearchExample {

      public static void main(String[] args) throws Exception {

            //Authenticator.setDefault(new HttpAuthenticateProxy("proxyuserName", "proxyPwd"));

            //System.setProperty("http.proxyHost", "proxyServer");

            //System.setProperty("http.proxyPort", "8080");

           

            while (true) {

                  String link = "http://www.google.co.in/search?q=ganesh+gowtham";

                  URL url = new URL(link);

                  URLConnection connection = url.openConnection();

                  connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");

                  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                  String inputLine;                              

                   while ((inputLine = in.readLine()) != null)

                   {

                         System.out.println(inputLine);

                   }

                  in.close();

            }

      }

}

 