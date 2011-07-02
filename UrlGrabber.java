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
			}

		}
	}
}
