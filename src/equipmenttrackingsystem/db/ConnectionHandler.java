
package equipmenttrackingsystem.db;

import equipmenttrackingsystem.exceptions.DataStorageException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 
 */
public class ConnectionHandler {
 
      private static String dbLocation = "jdbc:mysql://localhost/trackingdb";
    private static String username = "root";
    private static String pass = "";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static Connection con = null;

    public static Connection getConnection() throws DataStorageException {

        try {
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbLocation, username, pass);
           
        } catch (ClassNotFoundException exec) {
        throw new DataStorageException("Database  is not found or not properly installed");
             
        } catch (SQLException exec) {
        throw new DataStorageException("Connection failed");
       
        }
        return con;
    }
    
}
