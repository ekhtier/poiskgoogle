import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            System.setProperty("http.proxyHost", "proxy.stusta.mhn.de");

            System.setProperty("http.proxyPort", "3130");

           

           

                  String link = "http://maps.google.com/m?q=55.711098%2C37.610092&hl=de";

                  URL url = new URL(link);

                  URLConnection connection = url.openConnection();

                  connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");

                  BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                  String inputLine;  
                  StringBuilder str = new StringBuilder();

                  while ((inputLine = in.readLine()) != null)

                   {
                	  	 str.append(inputLine);
                   }

                  in.close();
                  
                  System.out.println(str);
                  
                  Pattern p = Pattern.compile("<div class=\"qovsme\">.*?</div>");
                  Matcher m = p.matcher(str);
                  String s; 
                  while (m.find()){
                	  s = str.substring(m.start(), m.end());
                	  System.out.println(s);
                	  System.out.println(s.substring(20, s.indexOf("</div>")));
                	  
                  }

            }

      

}

 