import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;

public class UrlGrabber {
	private Connection connection = null;

	public UrlGrabber() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@oracle-sb.tsretail.ru:1551/TEST",
					"word", "word");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void writeLinksToDB(List<Element> links) {
			String ins = null;
			
	       ins = "insert into url_links(url_id, link, last_check) values(URL_LINKS_SEQ.nextval,?,sysdate)";
	       PreparedStatement stmt = null;
	       try {
			stmt = connection.prepareStatement(ins);
			
	       for (Element linkElement : links) {
			String href = linkElement.getAttributeValue("href");
			if (href == null)
				continue;

			Pattern wiki = Pattern.compile("/wiki/[^:]+");
			Matcher m = wiki.matcher(href);
			if (m.matches()) {
				try{
				stmt.setString(1, "http://en.wikipedia.org" + href);
				stmt.execute();
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					  System.err.println(ins);
					e.printStackTrace();
				}
			}
	       }
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				  System.err.println(ins);
				e.printStackTrace();
			}
		

		
	}   
}
