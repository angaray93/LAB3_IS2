/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.bean;

/**
 *
 * @author Ever
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DataConnect {
 
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/new_database", 
                    "postgres", 
                    "ever1991");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }
 
    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }

}

//static Connection getConnection() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
//}

/**
 *
 * @author Acer
 */
//public class DataConnect {
    
//}
