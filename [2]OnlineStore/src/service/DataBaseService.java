/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.sun.tracing.dtrace.DependencyClass;
import databeans.DepartmentType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author robert.balaban
 */
public class DataBaseService {
    
    private static boolean status = false;
    
    private DataBaseService() {
        
    }

    public static void createDatabase(String user, String password) {
        Connection con = null;
        try  {
            String url = "jdbc:mysql://localhost/";
            con = DriverManager.getConnection(url, user, password);
            PreparedStatement stm1;

            try {
                dropDB(con);
                System.out.print("Deleting old 'my_store' database. ");
            } catch (Exception e) {
                System.out.print("Database 'my_store' doesn't exists. ");
            } finally {
                System.out.println("Creating a new 'my_store' database.");
                createDB(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void dropDB(Connection con) throws SQLException {
        PreparedStatement stm = con.prepareStatement("DROP DATABASE my_store");
        stm.executeUpdate();
        status = false;
    }
    
    private static void createDB(Connection con) throws SQLException {
        PreparedStatement stm = con.prepareStatement("CREATE DATABASE my_store");
        stm.executeUpdate();
        status = true;
    }
    
    public static boolean getStatus() {
        return status;
    }

    public static void createTables(Connection con) throws SQLException {
        for (DepartmentType d : DepartmentType.values()) {
            PreparedStatement stmt = con.prepareStatement("CREATE TABLE " 
                + d.toString().trim() + " (" 
                + "ID int NOT NULL AUTO_INCREMENT," 
                + "Nume varchar(50)," 
                + "Pret double,"
                + "PRIMARY KEY (ID))");
            stmt.executeUpdate();
        }
    }
}
