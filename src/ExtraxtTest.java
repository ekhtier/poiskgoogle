import magisterarbeit.DBWorker;
import net.htmlparser.jericho.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.io.*;
import java.net.*;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.SingularValueDecompositionImpl;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

public class ExtraxtTest {

	public void doSome() throws MalformedURLException, IOException {

		Statement stmt;

		ArrayList<String> l_snt = null;
		ArrayList<String> res_coll = null;
		ArrayList<String> filtered_links;

		UrlGrabber u = new UrlGrabber();
//		try {
//			stmt = DBWorker.getDBConnection().createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("select link_id, link from uniqlinks");
//			Source source = null;
//			while (rs.next()) {
//				System.out.println(rs.getString("LINK"));
//				source = UrlGrabber.getHtmlContent(rs.getString(2));
//				source.fullSequentialParse();
//				// System.out.println("\nDocument keydic:");
//				// String keydic=getMetaValue(source,"keydic");
//
//				// List<Element> linkElements =
//				// source.getAllElements(HTMLElementName.A);
//				// filtered_links = u.filterLinks(linkElements);
//				// System.out.println(filtered_links.size());
//				// UrlGrabber.writeToDB(filtered_links);
//
//				// res_coll = new SplitInWords().split(source.getTextExtractor()
//				// .setIncludeAttributes(true).toString());
//				// l_snt = new Sentence();
//
//				// l_snt = new SplitInSents().split(source.getTextExtractor()
//				// .setIncludeAttributes(true).toString());
//				// DBWorker.writeTermsToDB(res_coll, "terms");
//				DBWorker.extractWordsfromTerms();
//
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public HashMap countFreq(ArrayList<String> al) {
		HashMap<String, Integer> res_freq = new HashMap<String, Integer>();
		int amm = 1;
		for (int i = 1; i < al.size(); i++) {
			if (al.get(i - 1).equalsIgnoreCase(al.get(i))) {
				amm++;
			} else {
				res_freq.put(al.get(i - 1), amm);
				amm = 1;
			}
		}
		return res_freq;
	}

	public static void main(String[] args) throws Exception {
		new ExtraxtTest().doSome();
		new ExtraxtTest().stringAnalize();

	}

	private static String getTitle(Source source) {
		Element titleElement = source.getFirstElement(HTMLElementName.TITLE);
		if (titleElement == null)
			return null;
		// TITLE element never contains other tags so just decode it collapsing
		// whitespace:
		return CharacterReference.decodeCollapseWhiteSpace(titleElement
				.getContent());
	}

	private static String getMetaValue(Source source, String key) {
		for (int pos = 0; pos < source.length();) {
			StartTag startTag = source.getNextStartTag(pos, "name", key, false);
			if (startTag == null)
				return null;
			if (startTag.getName() == HTMLElementName.META)
				return startTag.getAttributeValue("content"); // Attribute
																// values are
																// automatically
																// decoded
			pos = startTag.getEnd();
		}
		return null;
	}

	public void stringAnalize() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("eurocrisis.txt"));
		String str = null;
		String text;
		text = "";
		ArrayList<String> dic = new ArrayList<String>();

		while ((str = in.readLine()) != null) {
			text = text + str;
		}

		ArrayList<String> sentences = new SplitInSents().split(text);

		Analyzer analyzer = new EnglishAnalyzer(Version.LUCENE_31);
		TokenStream stream = analyzer.tokenStream("contents", new StringReader(
				text));

		while (true) {
			if (!stream.incrementToken())
				break;
			AttributeSource token = stream.cloneAttributes();
			CharTermAttribute term = (CharTermAttribute) token
					.addAttribute(CharTermAttribute.class);
			dic.add(term.toString());

		}
		System.out.println(dic.size());
		HashSet<String> hashSet = new HashSet<String>(dic);
		dic.clear();
		dic.addAll(hashSet);
		System.out.println("__" + dic.size());

		double[][] stmatrix = new double[dic.size()][sentences.size()];

		for (int j = 0; j < sentences.size(); j++) {
			for (int i = 0; i < dic.size(); i++) {
				//ArrayList<String> terms = new ArrayList<String>();
				//ArrayList<String> words = new ArrayList<String>();
				TokenStream streaming = analyzer.tokenStream("contents",
						new StringReader(sentences.get(j)));
				int count = 0;
				while (true) {
					if (!streaming.incrementToken())
						break;
					AttributeSource token = streaming.cloneAttributes();
					CharTermAttribute term = (CharTermAttribute) token
							.addAttribute(CharTermAttribute.class);
					//System.out.println(term.toString());
					if (term.toString().equalsIgnoreCase(dic.get(i))) {
						count++;
						//System.out.println(dic.get(i));
					}
					stmatrix[i][j] = count;
				}

			}

		}

		for (int i = 0; i < dic.size(); i++) {
			for (int j = 0; j < sentences.size(); j++) {
				
				//System.out.print(stmatrix[i][j] + " ");
			}
			    //System.out.println();
		}
		RealMatrix m = new Array2DRowRealMatrix(stmatrix);
		SingularValueDecompositionImpl svd = new SingularValueDecompositionImpl(m);
		RealMatrix u = svd.getS();
		double [][] u_arr = u.getData();
		
	     DecimalFormat df = new DecimalFormat("#####0.00");
	     DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

	     dfs.setDecimalSeparator('.');
	     df.setDecimalFormatSymbols(dfs);
		
		for (int i = 0; i < u_arr.length; i++) {
			for (int j = 0; j < u_arr[0].length; j++) {
				
				System.out.print(df.format(u_arr[i][j]) + " ");
			}
			    System.out.println();
		   }
		System.out.println(u_arr.length);
		System.out.println(u_arr[0].length);
	}

}
