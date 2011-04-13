import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ReadFile {

        /**
         * @param args
         * @throws FileNotFoundException
         */
	
	  public ArrayList<Coordinates> getCoordinatesFromGoogle(){
      	ArrayList<Coordinates> koord = new ArrayList<Coordinates>();
        String str  = "";
       
        FileReader fr;
		try {
			fr = new FileReader("haus_nummer_fehler.txt");

			BufferedReader br = new BufferedReader(fr);

                while((str = br.readLine())!= null)
                                {
                		Coordinates x = new Coordinates();
                        String [] k = str.split(" ");
                        x.key = k[0];
                        x.lat = k[1];
                        x.lon = k[2];
                        koord.add(x);
                        //System.out.println(Arrays.toString(koordi));
                                }
//                for(int i=1; i<=koord.size(); i++){
//                	System.out.println(koord.get(i));
//                	
//                }
                
                for (Coordinates item: koord){
                	System.out.println(item.key + " " + item.lat + " " + item.lon);
                	
                }
               
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        finally{
        	 return koord;
        }
	  }
               
 //       HashMap<String, Coordinates> koord = new HashMap<String, Coordinates>();
//        koord.put("1","11,43569105    53,89014327");
//        koord.put("2", "11,43569105   53,89014327");
//             
}        





