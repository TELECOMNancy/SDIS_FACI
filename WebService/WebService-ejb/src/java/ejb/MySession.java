/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
public class MySession implements MySessionRemote {
    
    private static final String MY_DB_USERNAME = "test";
    private static final String MY_DB_PASSWORD = "test";
    private static final String MY_DB_NAME = "//localhost:1527/MyDatabase";
    private static final String MY_TABLE = "MESSAGES";
    
    @Override
    public void post(String key, String value) throws SQLException
    {
         Statement stmt = null;
         String query = "INSERT INTO " + MY_TABLE + " (" + key + ") "
                                   +"VALUES ('" + value + "')";
        try 
        {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }   
        catch (SQLException ex) 
        {
            Logger.getLogger(MySession.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally 
        {
            if (stmt != null)
            {
                stmt.close();
            }
        }
    }
        
    public Connection getConnection() throws SQLException 
    {
        
        Connection conn;
        Properties connectionProps = new Properties();
        
        conn = DriverManager.getConnection(
                "jdbc:derby:" + MY_DB_NAME +
                        ";create=true",
                MY_DB_USERNAME,
                MY_DB_PASSWORD);
        System.out.println("Connected to database");
        return conn; 
    }
}
