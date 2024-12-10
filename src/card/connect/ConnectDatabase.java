/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class ConnectDatabase {
       private String host =  "jdbc:mysql://localhost:3306";
    private static final String databaseName="card";
    private static final String user="student";
    private static final String password="student";
    private static ConnectDatabase instance=null;
    public ConnectDatabase(){}
    public static ConnectDatabase getInstance(){
        if (instance==null){
            instance=new ConnectDatabase();
        }
        return instance;
    }
    public Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(host + "/"+databaseName,user,password);
            return conn;
        } catch (SQLException ex) {
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
