package com.patikadev.View;

import com.patikadev.Helper.config;
import com.patikadev.Helper.helper;
import com.patikadev.Model.operator;
import com.patikadev.Model.userr;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wrappertop;
    private JPanel wrapperbottom;
    private JTextField field_user_uname;
    private JPasswordField field_user_pass;
    private JButton btn_login;

    public loginGUI(){
        add(wrapper);
        setSize(400,500);
        setLocation(helper.screenCenter("x", getSize()), helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);


        btn_login.addActionListener(e -> {
            if(helper.isFieldEmpty(field_user_uname) || helper.isFieldEmpty(field_user_pass)){
                helper.showMsg("fill");
            }
            else{
                userr u = userr.getFetch(field_user_uname.getText(), field_user_pass.getText());
                if(u == null){
                    helper.showMsg("Kullanıcı bulunamadı.");
                }
                else{
                    switch(u.getTypee()){
                        case "operator":
                            operatorGUI opGUI = new operatorGUI((operator) u);
                            break;
                        case "educator":
                            educatorGUI edGUI = new educatorGUI();
                            break;
                        case "student":
                            studentGUI stuGUI = new studentGUI();
                            break;
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        helper.setLayout();
        loginGUI login = new loginGUI();
    }
}
