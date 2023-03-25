package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static db.assign.obj2;

public class up_information {
    JTextField id;
    JTextField code;
    JButton sure;
    public JPanel up;

    public up_information() {
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (obj2)
                {
                    obj2.notify();
                }
            }
        });
    }

}
