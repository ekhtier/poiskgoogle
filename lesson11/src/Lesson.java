import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lesson {
	  static URL url;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		System.setProperty("http.proxyHost", "proxy.stusta.mhn.de");
        System.setProperty("http.proxyPort", "3130");
        //System.setProperty("http.proxyHost", null);
		
		  try {
			 url = new URL("http://www.google.com/search?q=BMW");
			 URLConnection connection = url.openConnection();
			 connection.setRequestProperty("User-Agent", "google");
			 BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
             String s = r.readLine();
             sb.append(s);
             while(s!=null){
                 //System.out.println(s);
                 s = r.readLine();
                 sb.append(s);
             }
             
             
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb);
		int i = sb.indexOf("<a");
		if (i>0){
			int ii = sb.indexOf("</a>", i);
			//ii = ii+4;
			System.out.println(sb.substring(i, ii+4));
		}
		else
			System.out.println("no string found");	
		
		Pattern p = Pattern.compile("<form.*?</form>?");
		//Pattern f = Pattern.compile
		
		String str;
		str = null;
		Matcher m = p.matcher(sb);
		 if(m.find()){
		       //System.out.println("ок "+m.start()+" "+m.end());
			 str = sb.substring(m.start(),m.end());
		           System.out.println(sb.substring(m.start(),m.end()));
		   }
		 p = Pattern.compile("<input.*?>");
		 m = p.matcher(str);
		 
			 while(m.find()){
			       //System.out.println("ок "+m.start()+" "+m.end());
				 //st = str.substring(m.start(),m.end());
			           System.out.println(str.substring(m.start(),m.end()));
		 }
	}
}
//NID 45=PBb6Fnor30syckkmoFVRK4cuH27kh4-9NsTVEFFeYLDcxmdYF46aJ5PTGlfgsyfkSJJ7rlstFWp2RnYGauohsyQAMOCDBeG6mgaKjsP7ekbaOJwhwgVbphDEcAzKhC2t
//PREF ID=e4a3133c3921e984:U=31e28fca68dc248d:FF=0:NW=1:TM=1301164756:LM=1301164776:S=vu1ncbO8E6mTO9I1
