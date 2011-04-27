import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String zeile = "<div> aaa,aaaa aaaa: aaaaaa aaaaaaa!<script> function f (){test teste tsete{ttt tttt tttt tttt} zzz zzzz zzzzz zzzz}</script> bbbb&, hhhhhh, bbbbb_bbb, bbbb </div>";		
		TestParse yahoo = new TestParse();
		String zeile;
		zeile = yahoo.getFromSite();
		yahoo.parseSite(zeile);
	}
	public String getFromSite(){
	      //System.setProperty("http.proxyHost", "proxy.stusta.mhn.de");
	      //System.setProperty("http.proxyPort", "3130");
		URL url;
		String r;
		StringBuilder str = new StringBuilder();
		try {
			url = new URL("http://yahoo.de");
			URLConnection c = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
			while((r = in.readLine())!=null)
				str.append(r);
				in.close();

			//System.out.println(str);
		}
		 catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{

				return str.toString();
				
			}
	}
	
	
	public void parseSite(String str){

			//String test = "asdf <div lfkdjg>Hello lell</div>";
			//Pattern p = Pattern.compile("<((div)[^<]*</div>)|((b)[^<]*</b>)");
//			Pattern p_script = Pattern.compile("<script[^<]*</script>");

//			while(m.find()){
//				System.out.println("\n!"+str.substring(m.start(),m.end()));
//			}
//			String clear = m.replaceAll("");
//			System.out.println(clear);
			
			System.out.println(str);
			//String a = "sdfgsracacdbcbedgdesabrg";
			
			//Pattern p = Pattern.compile(">[^<]+<");
			//Pattern br = Pattern.compile("\\{[^\\}]*\\}");
			//Pattern simple = Pattern.compile("a[[a[.]b]]??b");
			Pattern r;
			Matcher m;
			Pattern pNotA = Pattern.compile("\\{[^\\{]*\\}");
			//Pattern clear_p = Pattern.compile("<[^<]*>");

			//m = clear_p.matcher(str);
			//String clear = m.replaceAll("");
			ArrayList <String> l = new ArrayList<String>();
			boolean b;
			
			do{
				l.clear();
				Matcher m_a = pNotA.matcher(str);
			
			while(m_a.find()){
				
				Pattern pNotB = Pattern.compile("\\{[^\\}]*\\}");
				Matcher m_b = pNotB.matcher(str.substring(m_a.start(),m_a.end()));
				while(m_b.find())
				{
				l.add(str.substring(m_a.start(),m_a.end()).substring(m_b.start(),m_b.end()));
				System.out.println("\n!"+str.substring(m_a.start(),m_a.end()).substring(m_b.start(),m_b.end()));
				//System.out.println("\n"+m.start()+" "+m.end());
				}
			}
			
			
			System.out.println(l.size());
			for(int i=0;i<l.size();i++)
			{
			System.out.println(i + " before" + " " + l.get(i));
			l.set(i, l.get(i).replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}"));
			System.out.println(i + " after" + " " + l.get(i));
			//l.set(i, l.get(i).replaceAll("}", "\\}"));
			r = Pattern.compile(l.get(i));
			m = r.matcher(str);
			str = m.replaceAll("");
			//System.out.println(clear);
			//String [] words = clear.split(" ");
			//for(int i=0;i<words.length;i++);
			//	System.out.println(words[i]);
			}
			r = Pattern.compile("[\\{\\}]");
			m = r.matcher(str);
			
			} while(m.find());
			System.out.println(str);
		} 
		
	
	}
	
	




