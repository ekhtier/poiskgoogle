import java.util.ArrayList;
import java.util.Collections;


public class SplitInWords {
	public ArrayList<String> split(String s){
		
		String [] arr_s = s.split(" ");
		
	    ArrayList<String>l = new ArrayList<String>();
	    for (int i=0;i<arr_s.length;i++){
	        if(arr_s[i].length()>0)
//	            if(s[i].charAt(0) != '/'&&!(Character.isDigit(s[i].charAt(0))))
//	            {
	                l.add(arr_s[i]);
	                //System.out.println(s[i]);
//	            }                  
	    }
	    ArrayList<String> res_coll = new ExtraxtTest().clearByFirstChar(l, '#');
	    res_coll = new ExtraxtTest().clearByFirstChar(res_coll, '#');
	    Collections.sort(res_coll, String.CASE_INSENSITIVE_ORDER);

		return res_coll;
		
		
	}

}