package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class patikas {
    private int id;
    private String name;

    public patikas(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<patikas> getList(){
        ArrayList<patikas> patikasList = new ArrayList<>();
        patikas obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patikas");
            while (rs.next()){
                obj = new patikas(rs.getInt("id"), rs.getString("name"));
                patikasList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patikasList;
    }

    public static boolean add(String name){
        String query = "INSERT INTO patikas (name) VALUES (?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name){
        String query = "UPDATE patikas SET name = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static patikas getFetch(int id){
        patikas obj = null;
        String query = "SELECT * FROM patikas WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = new patikas(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM patikas WHERE id = ?";
        ArrayList<course> courseList = course.getList();
        for(course obj: courseList){
            if(obj.getPatikas().getId() == id){
                course.delete(obj.getId());
            }
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
}
