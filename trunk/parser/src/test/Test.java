package test;

 
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
 
public class Test 
{
    public static void main( String[] args )
    {
    	Properties prop = new Properties();
 
    	try {
    		//set the properties value
    		prop.setProperty("hostname", "192.168.1.151");
    		prop.setProperty("port", "1521");
    		prop.setProperty("servicename", "HOMEDB");
    		prop.setProperty("dbuser", "word");
    		prop.setProperty("dbpassword", "word");
 
    		//save properties to project root folder
    		prop.store(new FileOutputStream("config.properties"), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
}