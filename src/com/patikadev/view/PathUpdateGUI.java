package com.patikadev.view;

import com.patikadev.helper.MyHelper;
import com.patikadev.model.Path;

import javax.swing.*;

public class PathUpdateGUI extends JFrame{
    private JPanel wrapper;
    private JTextField field_path_update_name;
    private JButton btn_path_update;
    private Path path;

    public PathUpdateGUI(Path path){
        this.path = path;

        int guiWidth = 300, guiHeight = 180;

        setContentPane(wrapper);
        setTitle(MyHelper.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(guiWidth,guiHeight);
        setLocation((MyHelper.SCREEN_WIDTH - guiWidth) / 2, (MyHelper.SCREEN_HEIGHT - guiHeight) / 2);
        setVisible(true);
        field_path_update_name.setText(path.getName());
        btn_path_update.addActionListener(e -> {
            if(MyHelper.isFieldEmpty(field_path_update_name))
            {
                MyHelper.showMessage("Please fill in all fields.", "ERROR!");
            }
            else {
                if(Path.update(path.getId(), field_path_update_name.getText())){
                    MyHelper.showMessage("The update process is complete.", "Update Message");
                }
                dispose();
            }
        });
    }
}
