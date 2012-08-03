import net.htmlparser.jericho.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import java.io.*;
import java.net.*;



public class ExtraxtTest {
   public List<Element> extractLinksFromPage(){
	    String sourceUrlString="http://en.wikipedia.org/wiki/Singular_value_decomposition";
	    System.setProperty("http.proxyHost", "192.168.20.4");
	    System.setProperty("http.proxyPort", "3128");
	    System.getProperties().put("proxySet", "true");    
	    Authenticator.setDefault(new ProxyAuthenticator("tsretail\\sitroniks", "123qwE"));  

	    if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
	    MicrosoftConditionalCommentTagTypes.register();
	    PHPTagTypes.register();
	    PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
	    MasonTagTypes.register();
	    Source source = null;
		try {
			source = new Source(new URL(sourceUrlString));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Call fullSequentialParse manually as most of the source will be parsed.
	    source.fullSequentialParse();

//	    System.out.println("Document title:");
//	    String title=getTitle(source);
//	    System.out.println(title==null ? "(none)" : title);

//	    System.out.println("\nDocument description:");
//	    String description=getMetaValue(source,"description");
//	    System.out.println(description==null ? "(none)" : description);

//	    System.out.println("\nDocument keywords:");
//	    String keywords=getMetaValue(source,"keywords");
//	    System.out.println(keywords==null ? "(none)" : keywords);
	    List<Element> linkElements=source.getAllElements(HTMLElementName.A);
	   return linkElements;
   }
public static void main(String[] args) throws MalformedURLException, IOException{
	List<Element> linkElements = new ExtraxtTest().extractLinksFromPage();
    for (Element linkElement : linkElements) 
		System.out.println(linkElement.getAttributeValue("href"));
		
    
    UrlGrabber u = new UrlGrabber();
    u.writeLinksToDB(linkElements);
/*
    
    



    System.out.println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
    //System.out.println(source.getTextExtractor().setIncludeAttributes(true).toString());
//    String [] s = source.getTextExtractor().setIncludeAttributes(true).toString().split(" "); // sentence split ". "
//    ArrayList<String>l = new ArrayList<String>();
//    for (int i=0;i<s.length;i++){
//        if(s[i].length()>0)
////            if(s[i].charAt(0) != '/'&&!(Character.isDigit(s[i].charAt(0))))
////            {
//                l.add(s[i]);
//                //System.out.println(s[i]);
////            }                  
//    }
//ArrayList<String> res_coll = clearByFirstChar(l, '#');
//res_coll = clearByFirstChar(res_coll, '#');
//Collections.sort(res_coll, String.CASE_INSENSITIVE_ORDER);

ArrayList<String>res_coll = new SplitInWords().split(source.getTextExtractor().setIncludeAttributes(true).toString());

//String [] snt = source.getTextExtractor().setIncludeAttributes(true).toString().split(". "); // sentence split ". "
ArrayList<String>l_snt = new SplitInSents().split(source.getTextExtractor().setIncludeAttributes(true).toString());
//for (int i=0;i<snt.length;i++){
//    if(snt[i].length()>0)
////        if(s[i].charAt(0) != '/'&&!(Character.isDigit(s[i].charAt(0))))
////        {
//            l_snt.add(s[i]);
//            //System.out.println(s[i]);
////        }                  
//}
//ArrayList<String> res_coll_snt = clearByFirstChar(l_snt, '#');
//res_coll = clearByFirstChar(res_coll_snt, '#');
//Collections.sort(res_coll_snt, String.CASE_INSENSITIVE_ORDER);




//writeToDB(l_snt);
new ExtraxtTest().writeToDB(res_coll);

//for(String key: res_freq.keySet()){
//	System.out.println(key + " " + res_freq.get(key));
//}


for (int i=0;i<l_snt.size();i++){
	//System.out.println(l_snt.get(i));
}
	*/
    
}


public HashMap countFreq(ArrayList<String> al){
	HashMap<String, Integer> res_freq = new HashMap<String, Integer>();
	int amm = 1;	
	for (int i=1; i<al.size(); i++){
		if (al.get(i-1).equalsIgnoreCase(al.get(i))){
			amm++;
		}
		else{
			res_freq.put(al.get(i-1), amm);
			amm=1;
		}	
	}
	return res_freq;
	}




	public ArrayList<String> clearByFirstChar(ArrayList<String> ar, char ch){	
	    for (int i=0;i<ar.size();i++){

	            if(ar.get(i).charAt(0) == ch)
	            {
	            	ar.remove(i);
	               // System.out.println(s[i]);
	            }
	       
	    }
	    return ar; 
	    
	    
	}




    private static String getTitle(Source source) {
        Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
        if (titleElement==null) return null;
        // TITLE element never contains other tags so just decode it collapsing whitespace:
        return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
    }

    private static String getMetaValue(Source source, String key) {
        for (int pos=0; pos<source.length();) {
            StartTag startTag=source.getNextStartTag(pos,"name",key,false);
            if (startTag==null) return null;
            if (startTag.getName()==HTMLElementName.META)
                return startTag.getAttributeValue("content"); // Attribute values are automatically decoded
            pos=startTag.getEnd();
        }
        return null;
    }

   public void writeToDB(ArrayList al){

	   String ins = null;
	   
	   try {
	       // Load the JDBC driver
	       String driverName = "oracle.jdbc.driver.OracleDriver";
	       Class.forName(driverName);

	       // Create a connection to the database
	       
	       //String serverName = "127.0.0.1";
	       //String portNumber = "1521";
	       //String sid = "XE";
//	       String url = "jdbc:oracle:thin:@//localhost:1521/XE"; //host_name:port_number/service_name
	       //String username = "word";
	       //String password = "word";
	       Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle-sb.tsretail.ru:1551/TEST", "word", "word");
	       //("jdbc:oracle:oci8:@xe", "word", "word");
	       
	       
	       
	       Statement stmt = connection.createStatement();
	       PreparedStatement pstmt = connection.prepareStatement("insert into terms(page_id, term_name) values(1,?)");
	       for (int i=0;i<al.size();i++){
	    
//	    		System.out.println(res_coll.get(i));
	       pstmt.setString(1, al.get(i).toString());
	       try{
	       pstmt.execute();}
	       catch (SQLException e) {
			   System.err.println(ins);
			   e.printStackTrace();
	       }
	       }
	       stmt.close();
	       connection.close();
	   } catch (ClassNotFoundException e) {
		   e.printStackTrace();
	       // Could not find the database driver
	   } catch (SQLException e) {
		   System.err.println(ins);
		   e.printStackTrace();
	       // Could not connect to the database
	   }
   }
   
   
   
    
    
    
    
    
    
    
    
   static class ProxyAuthenticator extends Authenticator {
	    private PasswordAuthentication auth;

	    private ProxyAuthenticator(String user, String password) {
	        auth = new PasswordAuthentication(user, password == null ? new char[]{} : password.toCharArray());
	    }

	    protected PasswordAuthentication getPasswordAuthentication() {
	        return auth;
	    }
	}
  
    
    
}

