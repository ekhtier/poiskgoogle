import net.htmlparser.jericho.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.*;
import rss.*;
import org.apache.log4j.Logger;




public class ExtraxtTest {
	static DBWriter dbw = null;
	 public static final Logger log=Logger.getLogger(ExtraxtTest.class);
	public Source getSource(String sourceUrlString){
		Source source = null;
	    //System.setProperty("http.proxyHost", "192.168.20.4");
	    //System.setProperty("http.proxyPort", "3128");
	    //System.getProperties().put("proxySet", "true");    
	    //Authenticator.setDefault(new ProxyAuthenticator("tsretail\\sitroniks", "123qwE"));  

	    //if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
	    //MicrosoftConditionalCommentTagTypes.register();
	    PHPTagTypes.register();
	    PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
	    MasonTagTypes.register();
	    
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
		return source;
	}
   public List<Element> extractLinksFromSource(Source source){
	    List<Element> linkElements=source.getAllElements(HTMLElementName.A);
	   return linkElements;
   }
public static void main(String[] args) throws MalformedURLException, IOException{
	
	
	dbw = new DBWriter();
	String feedUrl = null;
	long feed_id = 0;
	//Source source = new ExtraxtTest().getSource("http://en.wikipedia.org/wiki/Singular_value_decomposition");
/*
	List<Element> linkElements = new ExtraxtTest().extractLinksFromSource(source);
    for (Element linkElement : linkElements) 
		System.out.println(linkElement.getAttributeValue("href"));
    UrlGrabber u = new UrlGrabber();
    u.writeLinksToDB(linkElements);


ArrayList<String>res_coll = new Splitter().splitToWords(source.getTextExtractor().setIncludeAttributes(true).toString());
ArrayList<String>l_snt = new Splitter().splitToSentences(source.getTextExtractor().setIncludeAttributes(true).toString());
new ExtraxtTest().writeToDB(l_snt);
//new ExtraxtTest().writeToDB(res_coll);
    */
	

	
	//RSSFeedParser parser = new RSSFeedParser("http://www.vogella.com/article.rss");
	//RSSFeedParser parser = new RSSFeedParser("http://lenta.ru/rss");
	//parser.testPrint();


try {
	
   Connection connection = dbw.getConnection();
   Statement stmt = connection.createStatement();


   ResultSet rs = stmt.executeQuery("select feed_id, feed_url from feed where nvl(last_update,sysdate-10)<sysdate-1/24");
   while(rs.next()){
	   feed_id = rs.getLong(1);
	   feedUrl = rs.getString(2);
		Feed feed = new Feed(feed_id,feedUrl);
		feed.refresh();
		
		new ExtraxtTest().writeFeedToDB(feed);	
		log.debug("running with feed "+feedUrl);
   }
   rs.close();
   stmt.close();
   connection.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

   //Feed feed = parser.readFeed();

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
   
    public HashSet<String> getLastFeedMessages(long feed_id){
    	//List<FeedMessage> messages = new ArrayList<FeedMessage>();
    Connection c = dbw.getConnection();
    HashSet<String> links = new HashSet<String>(); 
    try {
		PreparedStatement stmt = c.prepareStatement("select link from (select link From feed_message where feed_id = ? order by pub_date desc) where rownum<100");
		stmt.setLong(1, feed_id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			links.add(rs.getString(1));
		}
		rs.close();
		stmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    	return links;
    }
    public void writeFeedToDB(Feed feed){
	       Date pubDate = null;
	       List<FeedMessage> feedList = feed.getMessages();
	       long feed_id = feed.getID();
	       java.sql.Timestamp dt = new java.sql.Timestamp(System.currentTimeMillis());
	       HashSet savedLinks = getLastFeedMessages(feed_id);
	       try {

		       Connection connection = dbw.getConnection();
		    //   Statement stmt = connection.createStatement();
		       PreparedStatement pstmt = connection.prepareStatement("insert into feed_message(feed_id,message,link,pub_date,description,insert_date) values(?,?,?,?,?,sysdate)");
	    		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
	    	for(FeedMessage message:feedList){
	    		if(!savedLinks.contains(message.getLink())){
	    		System.out.println(message.getTitle());
	    		System.out.println(message.getPubDate());
	    		System.out.println(message.getLink());
	    		pubDate = formatter.parse(message.getPubDate());
	    		dt.setTime(pubDate.getTime());
	    		pstmt.setLong(1, feed_id);
	    		pstmt.setString(2, message.getTitle());
	    		pstmt.setString(3,  message.getLink());
	    		pstmt.setTimestamp(4,  dt);
	    		pstmt.setString(5,  message.getDescription());
	    		
	    		pstmt.execute();
	    		}
	    	}
	    	
	    	
		    	pstmt = connection.prepareStatement("update feed set last_update=sysdate where feed_id = ?");
		    	pstmt.setLong(1, feed_id);
		    	pstmt.execute();
		    	pstmt.close();
	    	
	       }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
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

