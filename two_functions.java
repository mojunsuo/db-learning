package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static db.assign.flag_manager;
import static db.assign.obj_manager;

public class two_functions {
    private JButton publicButton;
    private JButton verifyButton;
    JPanel mission;

    public two_functions() {
        publicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (obj_manager){
                    obj_manager.notify();
                }

            }
        });
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag_manager=1;
                synchronized (obj_manager){
                    obj_manager.notify();
                }

            }
        });
    }


}
