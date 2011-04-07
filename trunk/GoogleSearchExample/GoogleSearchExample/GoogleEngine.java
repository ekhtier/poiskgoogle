import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleEngine {
	String link;
	URL url;
	
	public void getGoogleDescription(){

        //Authenticator.setDefault(new ProxyAuthenticator("proxyuserName", "proxyPwd"));

        System.setProperty("http.proxyHost", "proxy.stusta.mhn.de");

        System.setProperty("http.proxyPort", "3130");
        
        
        Pattern p = Pattern.compile("<div class=\"qovsme\">.*?</div>");            
       //double [][] koordinaten = new double [10][2];
        double [][]k = {{55.89014327, 13.43569105},{53.89014327, 11.43569105}};
        String [] arr_adr = new String [2];
 	   try {
        for (int i=0; i<k.length; i++)
       {
    	   link = "http://maps.google.com/m?q="+k[i][0]+"%2C"+k[i][1]+"&hl=de";
    	   System.out.println(link);

			url = new URL(link);
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
           
           //System.out.println(str);
           

           Matcher m = p.matcher(str);
           String s; 
           while (m.find()){
         	  s = str.substring(m.start(), m.end());
         	  //System.out.println(s);
         	  arr_adr[i] = s.substring(20, s.indexOf("</div>")); 
         	  System.out.println(arr_adr[i]);
         	  
           }
           
          // System.out.println(s.substring(20, s.indexOf("</div>")));
       
       }

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


              

               




		
	}

}
