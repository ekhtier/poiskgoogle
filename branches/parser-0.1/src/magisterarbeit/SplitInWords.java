package magisterarbeit;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
	    ArrayList<String> res_coll = clearByFirstChar(l, '#');
	    res_coll = clearByFirstChar(res_coll, '#');
	    Collections.sort(res_coll, String.CASE_INSENSITIVE_ORDER);

		return res_coll;
		
		
	}
	public void writeToDB(ArrayList al) {

		String ins = null;

		try {
			// Load the JDBC driver
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);

			// Create a connection to the database

			String serverName = "127.0.0.1";
			String portNumber = "1521";
			String sid = "XE";
			// String url = "jdbc:oracle:thin:@//localhost:1521/XE";
			// //host_name:port_number/service_name
			String username = "word";
			String password = "word";
			Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@alexander-PC:1521/XE", "word", "word");
			// ("jdbc:oracle:oci8:@xe", "word", "word");

			Statement stmt = connection.createStatement();
			for (int i = 0; i < al.size(); i++) {

				// System.out.println(res_coll.get(i));
				ins = "insert into terms(page_id, term_name) values(1,'"
						+ al.get(i) + "')";
				try {
					stmt.execute(ins);
				} catch (SQLException e) {
					// System.err.println(ins);
					// e.printStackTrace();
				}
			}
			stmt.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// Could not find the database driver
		} catch (SQLException e) {
			// System.err.println(ins);
			// e.printStackTrace();
			// Could not connect to the database
		}
	}
	

	public ArrayList<String> clearByFirstChar(ArrayList<String> ar, char ch) {
		for (int i = 0; i < ar.size(); i++) {

			if (ar.get(i).charAt(0) == ch) {
				ar.remove(i);
				// System.out.println(s[i]);
			}

		}
		return ar;

	}

	
}