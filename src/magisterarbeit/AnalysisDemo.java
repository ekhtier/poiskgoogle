package magisterarbeit;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishMinimalStemmer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.apache.lucene.util.AttributeSource;
 
import java.io.IOException;
import java.io.StringReader;
 

public class AnalysisDemo {
	    private static final String[] strings = {
            "The quick brown fox jumped over the LaZy dog dogs hause hauses boss bosses is",
	            "Быстрая, рыжая лисица препрыгнула через ленивых собак и бысто убежала",
	            "XY&Z Corporation - xyz@Example.com"};
	 
	    private static final Analyzer[] analyzers = new Analyzer[]{
	            new WhitespaceAnalyzer(Version.LUCENE_31),
	            new SimpleAnalyzer(Version.LUCENE_31),
	            new EnglishAnalyzer(Version.LUCENE_31),
	            new RussianAnalyzer(Version.LUCENE_31),
	            new StopAnalyzer(Version.LUCENE_31),
	            new StandardAnalyzer(Version.LUCENE_31)
	    };
 
	    public static void main(String[] args) throws IOException {
	        for (int i = 0; i < strings.length; i++) {
	            analyze(strings[i]);
	        }
	    }
	 
	    private static void analyze(String text) throws IOException {
	        System.out.println("Analzying \"" + text + "\"");
	        for (int i = 0; i < analyzers.length; i++) {
	            Analyzer analyzer = analyzers[i];
	            
	            System.out.println("\t" + analyzer.getClass().getName() + ":");
	            System.out.print("\t\t");
	            TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
	            while (true) {
	                if (!stream.incrementToken()) break;
	                AttributeSource token = stream.cloneAttributes();
	                CharTermAttribute term =(CharTermAttribute) token.addAttribute(CharTermAttribute.class);
	                System.out.print("[" + term.toString() + "] "); //2
	            }
	            System.out.println("\n");
	        }
	    }
}	 