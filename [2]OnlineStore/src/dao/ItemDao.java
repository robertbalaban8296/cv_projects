/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import databeans.DepartmentType;
import databeans.Item;
import databeans.OrderTypes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author robert.balaban
 */
public class ItemDao {
    private Connection con;
    
    public ItemDao(Connection con) {
        this.con = con;
    }
    
    public void adaugaItem(Item p, DepartmentType dt) throws SQLException {
        String sql = "INSERT INTO " + dt.toString().trim() + " VALUES (null,?,?)";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, p.getNume());
        stm.setDouble(2, p.getPret());
        stm.executeUpdate();
    }
    
    public ResultSet getItems(OrderTypes o, DepartmentType d) throws SQLException {
        String sql = null;
        if (o.equals(OrderTypes.DEFAULT)) {
            sql = "SELECT * FROM " + d.toString().trim();
        } else {
            sql = "SELECT * FROM " + d.toString().trim() + " ORDER BY Pret "
                    + o.toString();
        }
        return con.prepareStatement(sql).executeQuery();
    }
    
    public void removeItem(int id, DepartmentType d) throws SQLException {
        String sql = "DELETE FROM " + d.toString().trim() + " WHERE ID = ?" ;
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setInt(1, id);
        stm.executeUpdate();  
    }
    
    public void applyDiscount(String discount, DepartmentType d) throws SQLException {
        String sql = null;
        if (discount.equals("10%")) {
            sql = "UPDATE " + d.toString() + " SET Pret = Pret - Pret/" + 10;
        }
        if (discount.equals("20%")) {
            sql = "UPDATE " + d.toString() + " SET Pret = Pret - Pret/" + 5;
        }
        if (discount.equals("25%")) {
            sql = "UPDATE " + d.toString() + " SET Pret = Pret - Pret/" + 4;
        }
        PreparedStatement stm = con.prepareStatement(sql);
        stm.executeUpdate();
    }
    
    public void modifyPrice(DepartmentType d, int id, double newPrice) throws SQLException {
        String sql = "UPDATE " + d.toString() + " SET Pret = ? WHERE id = ?";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setDouble(1, newPrice);
        stm.setInt(2, id);
        stm.executeUpdate();
    }
} 
