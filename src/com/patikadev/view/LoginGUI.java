package com.patikadev.view;

import com.patikadev.helper.MyHelper;
import com.patikadev.model.Educator;
import com.patikadev.model.Operator;
import com.patikadev.model.Student;
import com.patikadev.model.User;

import javax.swing.*;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wrapper_top;
    private JPanel wrapper_center;
    private JButton button_signup;
    private JPanel panel_a;
    private JPasswordField field_login_confirm_password;
    private JPanel panel_b;
    private JTextField field_login_full_name;
    private JButton button_login;
    private JTextField field_login_user_name;
    private JPasswordField field_login_password;

    public LoginGUI(){
        int guiWidth = 600, guiHeight = 450;

        setContentPane(wrapper);
        setTitle(MyHelper.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(guiWidth,guiHeight);
        setResizable(false);
        setLocation((MyHelper.SCREEN_WIDTH - guiWidth) / 2, (MyHelper.SCREEN_HEIGHT - guiHeight) / 2);
        setVisible(true);

        button_login.addActionListener(e -> {
            if(MyHelper.isFieldEmpty(field_login_user_name, field_login_password)){
                MyHelper.showMessage("Please fill in all fields.", "ERROR!");
            }
            else {
                String userName = field_login_user_name.getText();
                String password = new String(field_login_password.getPassword());

                User user = User.fetchByUsername(userName);
                if(user != null){
                    if(user.getPassword().equals(password)){
                        switch (user.getType()){
                            case MyHelper.USER_TYPE_NAME_OPERATOR:
                                OperatorGUI operatorGUI = new OperatorGUI(new Operator(user.getId(), user.getFullname(), user.getUsername(), user.getPassword()));
                                break;
                            case MyHelper.USER_TYPE_NAME_EDUCATOR:
                                EducatorGUI educatorGUI= new EducatorGUI(new Educator(user.getId(), user.getFullname(), user.getUsername(), user.getPassword()));
                                break;
                            default:
                                StudentGUI studentGUI = new StudentGUI(new Student(user.getId(), user.getFullname(), user.getUsername(), user.getPassword()));
                                break;
                        }
                        dispose();
                    }
                    else {
                        MyHelper.showMessage("Incorrect Password!", "Password Error!");
                    }
                }
                else {
                    MyHelper.showMessage("User Not Found!!", "Username Error!");
                }
            }
        });

        button_signup.addActionListener(e -> {
            if(MyHelper.isFieldEmpty(field_login_user_name, field_login_password, field_login_full_name, field_login_confirm_password)){
                MyHelper.showMessage("Please fill in all fields.", "ERROR!");
            }
            else {
                String userName = field_login_user_name.getText();
                String password = new String(field_login_password.getPassword());
                String fullName = field_login_full_name.getText();
                String confirmPassword = new String(field_login_confirm_password.getPassword());

                if(!password.equals(confirmPassword)){
                    MyHelper.showMessage("Password fields do not match!", "Password Mismatch Error!");
                    field_login_password.setText(null);
                    field_login_confirm_password.setText(null);
                }
                else {
                    User user = User.fetchByUsername(userName);
                    if(user != null){
                        MyHelper.showMessage("The username is already registered. Try registering with a different username.", "Username Error!");
                        field_login_user_name.setText(null);
                    }
                    else {
                        if(User.add(fullName,userName,password, MyHelper.USER_TYPE_NAME_STUDENT)){
                            MyHelper.showMessage("Registration successful.\nYou can now Log in...", "Registration Message");
                            field_login_confirm_password.setText(null);
                            field_login_full_name.setText(null);
                        }
                    }
                }
            }
        });
    }
}
