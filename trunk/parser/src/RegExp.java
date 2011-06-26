import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegExp{
	public ArrayList<String> split(String s){
		ArrayList<String> str = new ArrayList<String>();
		
		//Pattern pattern = Pattern.compile("(^[A-Z]). [A-Z][a-z]+");
		// In case you would like to ignore case sensitivity you could use this
		// statement
		// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		//Matcher matcher = pattern.matcher(s);
		
		BreakIterator bi = BreakIterator.getSentenceInstance();
		bi.setText(s);
		int index = 0;
		while (bi.next() != BreakIterator.DONE) {
		String sentence = s.substring(index, bi.current());
		str.add(sentence);
		System.out.println("Sentence: " + sentence);
		index = bi.current();
		}
		return str;
		
		
	}
	//"House home. Test version. A. Bosch. A cat."
	
	

}
