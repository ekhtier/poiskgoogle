import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;


public class Splitter {
	public ArrayList<String> splitToSentences(String s){
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
//		System.out.println("Sentence: " + sentence);
		index = bi.current();
		}
		return str;
		
		
	}

    public ArrayList<String> splitToWords(String s){
        
        String [] arr_s = s.split(" ");
        
    ArrayList<String>l = new ArrayList<String>();
    for (int i=0;i<arr_s.length;i++){
        if(arr_s[i].length()>0)
//          if(s[i].charAt(0) != '/'&&!(Character.isDigit(s[i].charAt(0))))
//          {
                l.add(arr_s[i]);
                //System.out.println(s[i]);
//          }                  
    }
    ArrayList<String> res_coll = new ExtraxtTest().clearByFirstChar(l, '#');
    res_coll = new ExtraxtTest().clearByFirstChar(res_coll, '#');
    Collections.sort(res_coll, String.CASE_INSENSITIVE_ORDER);
        return res_coll;
}
	
	
}
