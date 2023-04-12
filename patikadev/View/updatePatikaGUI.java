package com.patikadev.View;
import com.patikadev.Helper.config;
import com.patikadev.Helper.helper;
import com.patikadev.Model.patikas;

import javax.swing.*;

public class updatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField field_patikass_name;
    private JButton btn_update;
    private patikas patikass;


    public updatePatikaGUI(patikas patikass){
        this.patikass = patikass;
        add(wrapper);
        setSize(300,150);
        setLocation(helper.screenCenter("x", getSize()), helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setVisible(true);

        field_patikass_name.setText(patikass.getName());

        btn_update.addActionListener(e -> {
            if(helper.isFieldEmpty(field_patikass_name)){
                helper.showMsg("fill");
            }
            else{
                if(patikas.update(patikass.getId(), field_patikass_name.getText())){
                    helper.showMsg("done");
                }
                dispose();
            }
        });
    }
}
