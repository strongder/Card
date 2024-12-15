/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package card.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author admin
 */
public class User {
    private String id;
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
    private String bienSo;
    private double money;
    private byte[]avatar;

    public User(){};
    public User(String id, String fullName, String dateOfBirth, String phoneNumber, String bienSo) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.bienSo = bienSo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex='" + phoneNumber + '\'' +
                ", address='" + bienSo + '\'' +
                ", money=" + money +
                ", avatar=" + Arrays.toString(avatar) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    
    public String generateId()
    {
        long generateId = System.currentTimeMillis();
        return String.valueOf(generateId);
    }
}
