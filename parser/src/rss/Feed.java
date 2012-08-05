package rss;
import java.util.ArrayList;
import java.util.List;

/*
 * Stores an RSS feed
 */
public class Feed {

	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	final String pubDate;
	long db_id;
	List<FeedMessage> entries = new ArrayList<FeedMessage>();

	public Feed(String title, String link, String description, String language,
			String copyright, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}
	public Feed(Long feedId,String feedUrl) {
		title = null;
		link = feedUrl;
		db_id = feedId; 
		 description = null;
		language = null;
		copyright = null;
		pubDate = null;
		
	}
	public void refresh(){
		entries = new RSSFeedParser().readMessages(link);
	}

	public Feed() {
		title = null;
		link = null;
		 description = null;
		language = null;
		copyright = null;
		pubDate = null;
	}


	public List<FeedMessage> getMessages() {
		return entries;
	}

	public long getID() {
		return db_id;
	}
	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	@Override
	public String toString() {
		return "Feed [copyright=" + copyright + ", description=" + description
				+ ", language=" + language + ", link=" + link + ", pubDate="
				+ pubDate + ", title=" + title + "]";
	}

} 