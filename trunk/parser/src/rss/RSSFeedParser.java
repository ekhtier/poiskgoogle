package rss;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RSSFeedParser {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	final URL url;

	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
public void testPrint(){
	
	URLConnection conn = null;
	try {
		conn = url.openConnection();
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	XPathExpression expr = null;
	Document doc = null;
	try {
		builder = docFactory.newDocumentBuilder();
		doc = builder.parse(conn.getInputStream(),"windows-1251");
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer xform = null;
		xform = factory.newTransformer();
		xform.transform(new DOMSource(doc), new StreamResult(System.out));
		
		
		XPathFactory xFactory = XPathFactory.newInstance();

		// Create a XPath object
		XPath xpath = xFactory.newXPath();

		// Compile the XPath expression
		expr = xpath.compile("//channel/item");
		// Run the query and get a nodeset
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		
		// Cast the result to a DOM NodeList
		NodeList nodes = (NodeList) result;
		for (int i=0; i<nodes.getLength();i++){
			//System.out.println("node name: "+nodes.item(i).getNodeName());
			//System.out.println("node Text Content: "+nodes.item(i).getTextContent());
			
			//expr = xpath.compile("title");
			Element el = (Element) nodes.item(i);
			System.out.println(el.getElementsByTagName(TITLE).item(0).getFirstChild().getNodeValue());
			System.out.println(el.getElementsByTagName(LINK).item(0).getFirstChild().getNodeValue());
			System.out.println(el.getElementsByTagName(PUB_DATE).item(0).getFirstChild().getNodeValue());
			System.out.println(el.getElementsByTagName(DESCRIPTION).item(0).getFirstChild().getNodeValue());
			
			
			/*
			NodeList params = nodes.item(i).getChildNodes();
			for(int j=0;j<params.getLength();j++){
				System.out.println("item's tags: "+params.item(j).getNodeName()+" getContent:"+params.item(j).getTextContent());
			}
				*/
			
		}
		
		
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (XPathExpressionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}






}
	
	@SuppressWarnings("null")
	public Feed readFeed() {
		Feed feed = null;
		try {

			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			
			
			
			
			
			
			
			
			
			
			
			InputStream in = read();
			int length  = in.available();
			System.out.println(length);
			int offset = 0;
			int numRead = 0;
			byte[] b  = new byte[length];
			while(offset < b.length
			&& (numRead = in.read(b, offset, b.length - offset)) >= 0) {
			offset += numRead;
			}
			
				System.out.println(new String(b,"windows-1251"));
						//
				System.exit(0);
			//System.out.println(new String(b,"windows-1251"));
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in,"windows-1251");
			// Read the XML document
			while (eventReader.hasNext()) {

				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					if (event.asStartElement().getName().getLocalPart() == (ITEM)) {
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(title, link, description, language,
									copyright, pubdate);
						}
						event = eventReader.nextEvent();
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (TITLE)) {
						event = eventReader.nextEvent();
						title = event.asCharacters().getData();
						System.out.println(title);
						//System.out.println(event.asCharacters().getData());
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (DESCRIPTION)) {
						event = eventReader.nextEvent();
						description = event.asCharacters().getData();
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (LINK)) {
						event = eventReader.nextEvent();
						link = event.asCharacters().getData();
						continue;
					}

					if (event.asStartElement().getName().getLocalPart() == (GUID)) {
						event = eventReader.nextEvent();
						guid = event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (LANGUAGE)) {
						event = eventReader.nextEvent();
						language = event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (AUTHOR)) {
						event = eventReader.nextEvent();
						author = event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (PUB_DATE)) {
						event = eventReader.nextEvent();
						pubdate = event.asCharacters().getData();
						continue;
					}
					if (event.asStartElement().getName().getLocalPart() == (COPYRIGHT)) {
						event = eventReader.nextEvent();
						copyright = event.asCharacters().getData();
						continue;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						FeedMessage message = new FeedMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						message.setPubDate(pubdate);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feed;

	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
} 