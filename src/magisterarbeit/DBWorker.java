package magisterarbeit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.htmlparser.jericho.Source;

public class DBWorker {

	public static void writeTermsToDB(ArrayList<String> al, String tablename) {

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

			PreparedStatement stmt = connection.prepareStatement("insert into "
					+ tablename + " (page_id, term_name) values(1, ?)");
			for (int i = 0; i < al.size(); i++) {

				// System.out.println(res_coll.get(i));

				try {
					stmt.setString(1, al.get(i));
					stmt.execute();
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

	public static void writeTermsToDB(ArrayList<String> al, String tablename,
			int url_id) {

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

			PreparedStatement stmt = connection.prepareStatement("insert into "
					+ tablename + " (page_id, term_name) values(?, ?)");
			for (int i = 0; i < al.size(); i++) {

				// System.out.println(res_coll.get(i));

				try {
					stmt.setInt(1, url_id);
					stmt.setString(2, al.get(i));
					stmt.execute();
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

	public static void writeWordsToDB(ArrayList<String> al, int term_id, Connection connection) {

		String ins = null;

		try {

			PreparedStatement stmt = connection
					.prepareStatement("insert into words (term_id, word, word_id) values(?, ?, words_seq.nextval)");
			for (int i = 0; i < al.size(); i++) {

				// System.out.println(res_coll.get(i));

				try {
					stmt.setInt(1, term_id);
					stmt.setString(2, al.get(i));
					stmt.execute();
				} catch (SQLException e) {
					// System.err.println(ins);
					// e.printStackTrace();
				}
			}
			stmt.close();

		} catch (SQLException e) {
			// System.err.println(ins);
			// e.printStackTrace();
			// Could not connect to the database
		}
	}

	public static void writeURLsToDB(ArrayList<String> hrfs) {

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

			PreparedStatement stmt = connection
					.prepareStatement("insert into links(link_id, link, last_check) values(links_seq.nextval,?, sysdate)");
			// for (int i=0;i<al.size();i++){

			// System.out.println(res_coll.get(i));

			try {

				for (String link : hrfs) {
					stmt.setString(1, link);
					stmt.execute();
				}
			} catch (SQLException e) {
				// System.err.println(ins);
				e.printStackTrace();
			}

			stmt.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// Could not find the database driver
		} catch (SQLException e) {
			// System.err.println(ins);
			// e.printStackTrace();
			// Could not connect to the database
		}
	}

	public static Connection getDBConnection() {
		String driverName = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(driverName);
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@alexander-PC:1521/XE", "word", "word");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void extractWordsfromTerms() {
		Statement stmt;
		ArrayList<String> l_words;

		try {
			Connection c = getDBConnection();
			stmt = c.createStatement();

			ResultSet rs = stmt
					.executeQuery("select term_name, term_id from termsunique");
			while (rs.next()) {
				int i = rs.getInt("TERM_ID");
				System.out.println(i);
				l_words = new SplitInWords().split(rs.getString("TERM_NAME"));
				// DBWorker.writeTermsToDB(res_coll, "terms");
				DBWorker.writeWordsToDB(l_words, rs.getInt("TERM_ID"), c);

			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
