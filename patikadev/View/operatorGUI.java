package com.patikadev.View;

import com.patikadev.Helper.Item;
import com.patikadev.Helper.config;
import com.patikadev.Helper.helper;
import com.patikadev.Model.course;
import com.patikadev.Model.operator;
import com.patikadev.Model.patikas;
import com.patikadev.Model.userr;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class operatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane p;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_userList;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField field_user_name;
    private JTextField field_user_uname;
    private JTextField field_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField field_user_id;
    private JButton btn_user_delete;
    private JTextField field_sh_user_name;
    private JTextField field_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patikas_list;
    private JTable tbl_patikas_list;
    private JPanel pnl_patikas_add;
    private JTextField field_patikas_name;
    private JButton btn_patikas_add;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JLabel dersAdıLabel;
    private JPanel pnl_course_add;
    private JTextField field_course_name;
    private JTextField field_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JPanel panel_course_list;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patikas_list;
    private Object[] row_patikas_list;
    private JPopupMenu patikasMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final operator operator;
    public operatorGUI(operator operator){
        this.operator = operator;
        add(wrapper);
        setSize(1000,500);
        int x = helper.screenCenter("x", getSize());
        int y = helper.screenCenter("y", getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin " + operator.getName());


        //ModelUserList
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name", "UserName", "Password", "Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                System.out.println(select_user_id);
                field_user_id.setText(select_user_id);
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_typee = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if(userr.update(user_id, user_name, user_uname, user_pass, user_typee)){
                    helper.showMsg("done");
                }
                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });

        //patikasList(start)

        patikasMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sİl");
        patikasMenu.add(updateMenu);
        patikasMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patikas_list.getValueAt(tbl_patikas_list.getSelectedRow(), 0).toString());
            updatePatikaGUI updateGuI = new updatePatikaGUI(patikas.getFetch(select_id));
            updateGuI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikasModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if(helper.confirm("sure")){
                int select_id = Integer.parseInt(tbl_patikas_list.getValueAt(tbl_patikas_list.getSelectedRow(), 0).toString());
                if(patikas.delete(select_id)){
                    helper.showMsg("done");
                    loadPatikasModel();
                    loadPatikaCombo();
                    loadCourseModel();

                }
                else{
                    helper.showMsg("error");
                }
            }
        });



        mdl_patikas_list = new DefaultTableModel();
        Object[] col_patikas_list = {"ID", "Patika Adı"};
        mdl_patikas_list.setColumnIdentifiers(col_patikas_list);
        row_patikas_list = new Object[col_patikas_list.length];
        loadPatikasModel();


        tbl_patikas_list.setModel(mdl_patikas_list);
        tbl_patikas_list.setComponentPopupMenu(patikasMenu);
        tbl_patikas_list.getTableHeader().setReorderingAllowed(false);
        tbl_patikas_list.getColumnModel().getColumn(0).setMaxWidth(75);


        tbl_patikas_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patikas_list.rowAtPoint(point);
                tbl_patikas_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        // ##patikasList(finish)


        //CourseList (start)
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadEducatorCombo();
        // ## CourseList (finish)



        btn_user_add.addActionListener(e -> {
            if(helper.isFieldEmpty(field_user_name) || helper.isFieldEmpty(field_user_uname) || helper.isFieldEmpty(field_user_pass)){
                helper.showMsg("fill");
            }
            else{
                String name = field_user_name.getText();
                String uname = field_user_uname.getText();
                String pass = field_user_pass.getText();
                String typee = cmb_user_type.getSelectedItem().toString();
                if(userr.add(name, uname, pass, typee)){
                    helper.showMsg("done");
                    loadUserModel();
                    loadEducatorCombo();
                    field_user_name.setText(null);
                    field_user_uname.setText(null);
                    field_user_pass.setText(null);
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if(helper.isFieldEmpty(field_user_id)){
                helper.showMsg("fill");
            }
            else{
                if(helper.confirm("sure")){
                    int user_id = Integer.parseInt(field_user_id.getText());
                    if(userr.delete(user_id)){
                        helper.showMsg("done");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                        field_user_id.setText(null);
                    }
                    else{
                        helper.showMsg("error");
                    }
                }
            }
        });



        btn_user_sh.addActionListener(e -> {
            String name = field_sh_user_name.getText();
            String uname = field_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();
            String query = userr.searchQuery(name, uname, type);
            ArrayList<userr> searchingUser = userr.searchUserList(query);
            loadUserModel(searchingUser);
        });


        btn_logout.addActionListener(e -> {
            dispose();
            loginGUI login = new loginGUI();
        });


        btn_patikas_add.addActionListener(e -> {
            if(helper.isFieldEmpty(field_patikas_name)){
                helper.showMsg("fill");
            }
            else{
                if(patikas.add(field_patikas_name.getText())){
                    helper.showMsg("done");
                    loadPatikasModel();
                    loadPatikaCombo();
                    field_patikas_name.setText(null);
                }
                else{
                    helper.showMsg("error");
                }
            }
        });



        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if(helper.isFieldEmpty(field_course_name) || helper.isFieldEmpty(field_course_lang)){
                helper.showMsg("fill");
            }
            else{
                if(course.add(userItem.getKey(), patikaItem.getKey(), field_course_name.getText(), field_course_lang.getText())){
                    helper.showMsg("done");
                    loadCourseModel();
                    field_course_lang.setText(null);
                    field_course_name.setText(null);
                }
                else{
                    helper.showMsg("error");
                }
            }
        });
    }



    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(course obj: course.getList()){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatikas().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }


    }

    private void loadPatikasModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patikas_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(patikas obj: patikas.getList()){
            i = 0;
            row_patikas_list[i++] = obj.getId();
            row_patikas_list[i++] = obj.getName();
            mdl_patikas_list.addRow(row_patikas_list);
        }
    }

    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(userr obj: userr.getList()){
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getTypee();
            mdl_user_list.addRow(row_user_list);
        }
    }




    public void loadUserModel(ArrayList<userr> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for(userr obj: list){
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getTypee();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for(patikas obj: patikas.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCombo(){
        cmb_course_user.removeAllItems();
        for(userr obj: userr.getList()){
            if(obj.getTypee().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }











    public static void main(String[] args) {
        helper.setLayout();
        operator op = new operator();
        op.setId(1);
        op.setName("Oğuzhan Baba");
        op.setUname("krasicOguz18");
        op.setPass("123");
        op.setTypee("student");
        operatorGUI opGUI = new operatorGUI(op);
    }
}
