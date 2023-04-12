package com.patikadev.Model;


import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class course {

    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private patikas patikas;
    private userr educator;

    public course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patikas = patikas.getFetch(patika_id);
        this.educator = userr.getFetch(user_id);
    }


    public static ArrayList<course> getList(){
        ArrayList<course> courseList = new ArrayList<>();
        course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");
            while(rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new course(id, user_id, patika_id, name, language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static boolean add(int user_id, int patika_id, String name, String language){
        String query = "INSERT INTO course (user_id, patika_id, name, language) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, patika_id);
            pr.setString(3, name);
            pr.setString(4, language);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }




    public static ArrayList<course> getListByUser(int user_id){
        ArrayList<course> courseList = new ArrayList<>();
        course obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id = " + user_id);
            while(rs.next()){
                int id = rs.getInt("id");
                int user_ID = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new course(id, user_ID, patika_id, name, language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM course WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public com.patikadev.Model.patikas getPatikas() {
        return patikas;
    }

    public void setPatikas(com.patikadev.Model.patikas patikas) {
        this.patikas = patikas;
    }

    public userr getEducator() {
        return educator;
    }

    public void setEducator(userr educator) {
        this.educator = educator;
    }
}
