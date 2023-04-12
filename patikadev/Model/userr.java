package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.helper;

import java.sql.*;
import java.util.ArrayList;

public class userr{
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String typee;

    public userr(){

    }

    public userr(int id, String name, String uname, String pass, String typee) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.typee = typee;
    }

    public static ArrayList<userr> getList(){
        String query = ("SELECT * FROM userr");
        userr obj;
        ArrayList<userr> userList = new ArrayList<>();
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                obj = new userr();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setTypee(rs.getString("typee"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean add(String name, String uname, String pass, String typee){
        String query = "INSERT INTO userr (name, uname, pass, typee) VALUES (?, ?, ?, ?)";
        userr findUser = userr.getFetch(uname);
        if(findUser != null){
            helper.showMsg("Bu kullanıcı adı daha önceden alındı.");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, pass);
            pr.setObject(4, typee, Types.OTHER);

            int response = pr.executeUpdate();

            if(response == -1){
                helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static userr getFetch(String uname){
        userr obj = null;
        String sql = "SELECT * FROM userr WHERE uname = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1, uname);
            ResultSet rs = pr.executeQuery(); //HATA BURDAAAA EXECUTEQUERY İÇİNİ BOŞ BIRAK AQ sql KOYMUŞSUN
            if(rs .next()){
                obj = new userr();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setTypee(rs.getString("typee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }






    public static userr getFetch(int id){
        userr obj = null;
        String sql = "SELECT * FROM userr WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery(); //HATA BURDAAAA EXECUTEQUERY İÇİNİ BOŞ BIRAK AQ sql KOYMUŞSUN
            if(rs .next()){
                obj = new userr();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setTypee(rs.getString("typee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static userr getFetch(String uname, String pass){
        userr obj = null;
        String sql = "SELECT * FROM userr WHERE uname = ? AND pass = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(sql);
            pr.setString(1, uname);
            pr.setString(2, pass);
            ResultSet rs = pr.executeQuery(); //HATA BURDAAAA EXECUTEQUERY İÇİNİ BOŞ BIRAK AQ sql KOYMUŞSUN
            if(rs .next()){
                switch(rs.getString("typee")){
                    case "operator":
                        obj = new operator();
                        break;
                    default:
                        obj = new userr();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setTypee(rs.getString("typee"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }











    public static boolean delete(int id){
        String query = "DELETE FROM userr WHERE id = ?";
        ArrayList<course> courseList = course.getListByUser(id);
        for(course c: courseList){
            course.delete(c.getId());
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name, String uname, String pass, String typee){
        String query = "UPDATE userr SET name=?, uname=?, pass=?, typee=? WHERE id = ?";
        userr findUser = userr.getFetch(uname);
        if(findUser != null && findUser.getId() != id){
            helper.showMsg("Bu kullanıcı adı daha önceden alındı.");
            return false;
        }
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, pass);
            pr.setObject(4, typee, Types.OTHER);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<userr> searchUserList(String query){
        userr obj;
        ArrayList<userr> userList = new ArrayList<>();
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                obj = new userr();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPass(rs.getString("pass"));
                obj.setTypee(rs.getString("typee"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT * FROM userr WHERE uname ILIKE '%{{uname}}%' AND name ILIKE '%{{name}}%'";
        query = query.replace("{{uname}}", uname);
        query = query.replace("{{name}}", name);
        if(!type.isEmpty()){
            query += " AND typee='{{typee}}'";
            query = query.replace("{{typee}}", type);
        }
        return query;
    }







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTypee() {
        return typee;
    }

    public void setTypee(String typee) {
        this.typee = typee;
    }
}
