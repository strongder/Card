/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package card.connect;

import card.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author hieu.hua
 */
public class UserDAO {

    

    // Constructor
   

    public static void insertUser(User user)  {
        String sql = "INSERT INTO user (id, full_name, dob, phone, car_number, public_key) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(1, user.getId());
            sttm.setString(2, user.getFullName());
            sttm.setString(3, user.getDateOfBirth());
            sttm.setString(4, user.getPhoneNumber());
            sttm.setString(5, user.getBienSo());      
            sttm.setString(6, user.getPublicKey());
            System.out.println("them thanh cong");
            sttm.execute();
            
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    
       public static void updateUser(User user)  {
        String sql = "UPDATE user SET full_name = ?, dob = ?, phone = ?, car_number = ?, public_key = ? WHERE id = ?";
        
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(6, user.getId());
            sttm.setString(1, user.getFullName());
            sttm.setString(2, user.getDateOfBirth());
            sttm.setString(3, user.getPhoneNumber());
            sttm.setString(4, user.getBienSo());      
            sttm.setString(5, user.getPublicKey());
            
            sttm.execute();
            
        }catch(Exception e ){
            e.printStackTrace();
        }
       }
        
        
    public static void addMoney(User user, double money)  {
        double updateMoney = user.getMoney() + money;
        String sql = "UPDATE user SET money = ? WHERE id = ?";
        
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setDouble(1,updateMoney);
            sttm.setString(2, user.getId());
           
            
            sttm.execute();
            
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    
     public static void deductMoney(User user, double money)  {
        double updateMoney = user.getMoney() - money;
        String sql = "UPDATE user SET money = ? WHERE id = ?";
        
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setDouble(1,updateMoney);
            sttm.setString(2, user.getId());
           
            
            sttm.execute();
            
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        User user = new User("A1","Hieu5555555","07/08/2002","18A-11111","1233","aaaaaaaaaaaaa");
        //insertUser(user);
        addMoney(user, 500);
    }
}

