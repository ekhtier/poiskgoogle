import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;

public class UrlGrabber {

	public void filterLinks(List<Element> links) {
		for (Element linkElement : links) {
			String href = linkElement.getAttributeValue("href");
			if (href == null)
				continue;

			Pattern wiki = Pattern.compile("/wiki/[^:]+");
			Matcher m = wiki.matcher(href);
			if (m.matches()) {
				System.out.println("http://en.wikipedia.org" + href);
				writeToDB("http://en.wikipedia.org" + href);
			}

		}
	}
		
	   public void writeToDB(String s){

		   String ins = null;
		   
		   try {
		       // Load the JDBC driver
		       String driverName = "oracle.jdbc.driver.OracleDriver";
		       Class.forName(driverName);

		       // Create a connection to the database
		       
		       String serverName = "127.0.0.1";
		       String portNumber = "1521";
		       String sid = "XE";
//		       String url = "jdbc:oracle:thin:@//localhost:1521/XE"; //host_name:port_number/service_name
		       String username = "word";
		       String password = "word";
		       Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@alexander-PC:1521/XE", "word", "word");
		       //("jdbc:oracle:oci8:@xe", "word", "word");
		       
		       
		       
		       Statement stmt = connection.createStatement();
//		       for (int i=0;i<al.size();i++){
		    
//		    		System.out.println(res_coll.get(i));
		       ins = "insert into links(link, last_check) values(" + s + "," + "";
		       try{
		       stmt.execute(ins); }
		       catch (SQLException e) {
				   System.err.println(ins);
				   e.printStackTrace();
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
	   
}
