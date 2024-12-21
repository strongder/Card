/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.connect;

import card.model.User;
import card.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author admin
 */
public class UserDAO {

    public static void insertUser(User user) {
        String sql = "INSERT INTO user (id, full_name, dob, phone, car_number,public_key) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(1, user.getId());
            sttm.setString(2, user.getFullName());
            sttm.setString(3, user.getDateOfBirth());
            sttm.setString(4, user.getPhoneNumber());
            sttm.setString(5, user.getBienSo());
            sttm.setBytes(6, user.getPublicKey());
             System.out.println(user.getPublicKey());
            System.out.println("them thanh cong");
            sttm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        String sql = "UPDATE user SET full_name = ?, dob = ?, phone = ?, car_number = ?, public_key = ? WHERE id = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(6, user.getId());
            sttm.setString(1, user.getFullName());
            sttm.setString(2, user.getDateOfBirth());
            sttm.setString(3, user.getPhoneNumber());
            sttm.setString(4, user.getBienSo());
            sttm.setBytes(5, user.getPublicKey());

            sttm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMoney(User user, double money) {
        double updateMoney = user.getMoney() + money;
        String sql = "UPDATE user SET money = ? WHERE id = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setDouble(1, updateMoney);
            sttm.setString(2, user.getId());

            sttm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String userId) {
        String sql = "Select * from user where id = ?";
        User user = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(1, userId);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("full_name");
                String dob = rs.getString("dob");
                String phone = rs.getString("phone");
                String bienSo = rs.getString("car_number");
                long money = rs.getInt("money");
                int status = rs.getInt("status");
                user = new User(id, name, dob, phone, bienSo, money, status);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void deductMoney(User user, double money) {
        double updateMoney = user.getMoney() - money;
        String sql = "UPDATE user SET money = ? WHERE id = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setDouble(1, updateMoney);
            sttm.setString(2, user.getId());

            sttm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserWithMaxId() {
        String sql = "SELECT * FROM user WHERE id = (SELECT MAX(id) FROM user)";
        User user = null;

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {

                String id = rs.getString("id");
                String ten = rs.getString("full_name");
                String dob = rs.getString("dob");
                String phone = rs.getString("phone");
                String car_number = rs.getString("car_number");
                long money = Long.parseLong(rs.getString("money"));

                user = new User(id, ten, dob, phone, car_number, money);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static byte[] gePublicKey(String userId) {
        String sql = "SELECT public_key FROM user WHERE id = ?";
        byte[] publicKey = null;
        try (Connection connection = JDBCUtil.getConnection(); PreparedStatement sttm = connection.prepareStatement(sql)) {
            sttm.setString(1, userId);
            try (ResultSet rs = sttm.executeQuery()) {
                if (rs.next()) {
                    publicKey = rs.getBytes("public_key");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static void updateStatus(String id, int status) {

        String sql = "UPDATE user SET status  = ? WHERE id = ?";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setInt(1, status);
            sttm.setString(2, id);
            sttm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checStatus(String id) {
        String sql = "Select count(*) from user where id = ? and status = 1 ";

        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement sttm = connection.prepareStatement(sql);
            sttm.setString(1, id);
            ResultSet rs = sttm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
