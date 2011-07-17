import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;

public class UrlGrabber {

	public static Source getHtmlContent(String url) {

		Properties defaultProps = new Properties();
		FileInputStream in;
		Source source = null;
		try {
			in = new FileInputStream("db.conf");
			defaultProps.load(in);

			in.close();

			System.setProperty("http.proxyHost",
					defaultProps.getProperty("http.proxyHost"));
			System.setProperty("http.proxyPort",
					defaultProps.getProperty("http.proxyPort"));
			System.getProperties().put("proxySet",
					defaultProps.getProperty("proxySet"));

			MicrosoftConditionalCommentTagTypes.register();
			PHPTagTypes.register();
			PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for
												// this example otherwise they
												// override processing
												// instructions
			MasonTagTypes.register();

			source = new Source(new URL(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return source;

	}

	public ArrayList<String> filterLinks(List<Element> links) {
		ArrayList<String> hrefs = new ArrayList<String>();
		for (Element linkElement : links) {
			String href = linkElement.getAttributeValue("href");
			if (href == null)
				continue;

			Pattern wiki = Pattern.compile("/wiki/[^:]+");
			Matcher m = wiki.matcher(href);
			if (m.matches()) {
				// System.out.println("http://en.wikipedia.org" + href);
				hrefs.add("http://en.wikipedia.org" + href);
			}

		}
		return hrefs;
	}

}
