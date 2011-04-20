import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url;
		String r;
		StringBuilder str = new StringBuilder();
		try {
			url = new URL("http://lenta.ru");
			URLConnection c = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			while((r = in.readLine())!=null)
				str.append(r);	
			in.close();
			//System.out.println(str);
			//String test = "asdf <div lfkdjg>Hello lell</div>";
			//Pattern p = Pattern.compile("<((div)[^<]*</div>)|((b)[^<]*</b>)");
			Pattern p_script = Pattern.compile("<script[^<]*</script>");
			Matcher m = p_script.matcher(str);
			while(m.find()){
				System.out.println("\n!"+str.substring(m.start(),m.end()));
			}
			//String clear = m.replaceAll("");
			//System.out.println(clear);
			
			Pattern p = Pattern.compile(">[^<]+<");
			Pattern clear_p = Pattern.compile("<[^<]*>");
			m = clear_p.matcher(str);
			//clear = m.replaceAll("");
			/*while(m.find()){
				System.out.println("\n!"+str.substring(m.start(),m.end()));
			}
			*/
			//System.out.println(clear);
			//String [] words = clear.split(" ");
			//for(int i=0;i<words.length;i++);
			//	System.out.println(words[i]);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
