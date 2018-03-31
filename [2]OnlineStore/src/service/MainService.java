/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ItemDao;
import databeans.DepartmentType;
import databeans.Item;
import databeans.OrderTypes;
import gui.LoginFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;

/**
 *
 * @author robert.balaban
 */
public class MainService {
    
    private Connection con;
    private static MainService SINGLETON;
            
    private MainService(String user, String password) {   
        try {
            if (DataBaseService.getStatus() == false) {
                DataBaseService.createDatabase(user, password);
            }
            String url = "jdbc:mysql://localhost/my_store";
            con = DriverManager.getConnection(url, user, password);
            DataBaseService.createTables(con);
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
    
    public static MainService getMainService() {
        if (SINGLETON == null) {
            new LoginFrame();
        }
        return SINGLETON;
    }
    
    public static MainService createMainService(String user, String password) {
        if (SINGLETON == null) {
            SINGLETON = new MainService(user, password);
        }
        return SINGLETON;
    }

    public void adaugaItem(Item i, DepartmentType d) {
        try {
            ItemDao id = new ItemDao(con);
            id.adaugaItem(i, d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeItem(int id, DepartmentType d) {
        try {
            ItemDao ids = new ItemDao(con);
            ids.removeItem(id, d);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public DefaultListModel<String> getItems(OrderTypes o, DepartmentType d) {
        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            ResultSet rs = new ItemDao(con).getItems(o,d);
            while (rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("id"));
                i.setNume(rs.getString("nume"));
                i.setPret(rs.getDouble("pret"));
                model.addElement(i.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
        
    public void applyDiscount(String discount, DepartmentType d) {
        try {
            ItemDao id = new ItemDao(con);
            id.applyDiscount(discount, d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void modifyPrice(DepartmentType d, int id, double newPrice) {
        try {
            ItemDao i = new ItemDao(con);
            i.modifyPrice(d, id, newPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
