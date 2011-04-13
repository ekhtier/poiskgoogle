import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
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
//        double [][]k = {{55.89014327, 13.43569105},{53.89014327, 11.43569105}};
       HashMap<String, String> arr_adr = new HashMap<String, String>();
        
       ArrayList<Coordinates> c = getCoordinatesFromGoogle();
 	   try {
        for (Coordinates item: c)
       {
    	   link = "http://maps.google.com/m?q="+item.lon+"%2C"+item.lat+"&hl=de";
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
         	  arr_adr.put(item.key, s.substring(20, s.indexOf("</div>"))); 
         	  System.out.println(arr_adr.get(item.key));
         	  
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
	  public ArrayList<Coordinates> getCoordinatesFromGoogle(){
	      	ArrayList<Coordinates> koord = new ArrayList<Coordinates>();
	        String str  = "";
	       
	        FileReader fr;
			try {
				fr = new FileReader("haus_nummer_fehler.txt");

				BufferedReader br = new BufferedReader(fr);

	                while((str = br.readLine())!= null)
	                                {
	                		Coordinates x = new Coordinates();
	                        String [] k = str.split(" ");
	                        x.key = k[0];
	                        x.lat = k[1];
	                        x.lon = k[2];
	                        koord.add(x);
	                        //System.out.println(Arrays.toString(koordi));
	                                }
//	                for(int i=1; i<=koord.size(); i++){
//	                	System.out.println(koord.get(i));
//	                	
//	                }
	                
	                for (Coordinates item: koord){
	                	System.out.println(item.key + " " + item.lat + " " + item.lon);
	                	
	                }
	               
	        } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	        }
	        finally{
	        	 return koord;
	        }
		  }
}
