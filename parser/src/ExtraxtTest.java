import net.htmlparser.jericho.*;
import java.util.*;
import java.io.*;
import java.net.*;



public class ExtraxtTest {
   
public void doSome(String[] args) throws MalformedURLException, IOException{
    String sourceUrlString="http://de.wikipedia.org/wiki/Michael_Michalsky";
    System.setProperty("http.proxyHost", "proxy.stusta.mhn.de");
    System.setProperty("http.proxyPort", "3130");
    System.getProperties().put("proxySet", "true");     
    if (args.length==0)
      System.err.println("Using default argument of \""+sourceUrlString+'"');
    else
        sourceUrlString=args[0];
    if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
    MicrosoftConditionalCommentTagTypes.register();
    PHPTagTypes.register();
    PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
    MasonTagTypes.register();
    Source source=new Source(new URL(sourceUrlString));

    // Call fullSequentialParse manually as most of the source will be parsed.
    source.fullSequentialParse();

    System.out.println("Document title:");
    String title=getTitle(source);
    System.out.println(title==null ? "(none)" : title);

    System.out.println("\nDocument description:");
    String description=getMetaValue(source,"description");
    System.out.println(description==null ? "(none)" : description);

    System.out.println("\nDocument keywords:");
    String keywords=getMetaValue(source,"keywords");
    System.out.println(keywords==null ? "(none)" : keywords);

    System.out.println("\nLinks to other documents:");
    List<Element> linkElements=source.getAllElements(HTMLElementName.A);
    for (Element linkElement : linkElements) {
        String href=linkElement.getAttributeValue("href");
        if (href==null) continue;
        // A element can contain other tags so need to extract the text from it:
        String label=linkElement.getContent().getTextExtractor().toString();
        //System.out.println(label+" <"+href+'>');
    }

    System.out.println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
    //System.out.println(source.getTextExtractor().setIncludeAttributes(true).toString());
    String [] s = source.getTextExtractor().setIncludeAttributes(true).toString().split(" ");
    ArrayList<String>l = new ArrayList<String>();
    for (int i=0;i<s.length;i++){
        if(s[i].length()>0)
//            if(s[i].charAt(0) != '/'&&!(Character.isDigit(s[i].charAt(0))))
//            {
                l.add(s[i]);
                //System.out.println(s[i]);
//            }                  
    }
ArrayList<String> res_coll = clearByFirstChar(l, '#');
res_coll = clearByFirstChar(res_coll, '#');
Collections.sort(res_coll, String.CASE_INSENSITIVE_ORDER);
HashMap<String, Integer> res_freq = new HashMap<String, Integer>(); 




int amm = 1;

for (int i=1; i<res_coll.size(); i++){
	if (res_coll.get(i-1).equalsIgnoreCase(res_coll.get(i))){
		amm++;
	}
	else{
		res_freq.put(res_coll.get(i-1), amm);
		amm=1;
	}	
}




for(String key: res_freq.keySet()){
	System.out.println(key + " " + res_freq.get(key));
}


//for (int i=0;i<res_coll.size();i++){
//	System.out.println(res_coll.get(i));
//}
//	
    
}

	public ArrayList<String> clearByFirstChar(ArrayList<String> ar, char ch){	
	    for (int i=0;i<ar.size();i++){

	            if(ar.get(i).charAt(0) == ch)
	            {
	            	ar.remove(i);
	               // System.out.println(s[i]);
	            }
	       
	    }
	    return ar; 
	    
	    
	}


    public static void main(String[] args) throws Exception {
        new ExtraxtTest().doSome(args);
  }

    private static String getTitle(Source source) {
        Element titleElement=source.getFirstElement(HTMLElementName.TITLE);
        if (titleElement==null) return null;
        // TITLE element never contains other tags so just decode it collapsing whitespace:
        return CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent());
    }

    private static String getMetaValue(Source source, String key) {
        for (int pos=0; pos<source.length();) {
            StartTag startTag=source.getNextStartTag(pos,"name",key,false);
            if (startTag==null) return null;
            if (startTag.getName()==HTMLElementName.META)
                return startTag.getAttributeValue("content"); // Attribute values are automatically decoded
            pos=startTag.getEnd();
        }
        return null;
    }

    
}

