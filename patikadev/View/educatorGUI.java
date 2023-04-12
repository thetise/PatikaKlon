package com.patikadev.View;

import com.patikadev.Helper.config;
import com.patikadev.Helper.helper;

import javax.swing.*;

public class educatorGUI extends JFrame{
    private JPanel wrapper;

    public educatorGUI(){
        add(wrapper);
        setSize(400,500);
        setLocation(helper.screenCenter("x", getSize()), helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
    }
}
