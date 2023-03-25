package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static db.assign.obj1;
import static db.assign.flag;

public class base_information {
    public  JTextField id;
    public JTextField code;
    public JPanel inf;
    public JButton sure;
    JTextField name;


    public base_information() {
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String ID= id.getText();
                //String Code=code.getText();
                //System.out.println(ID+Code);

                synchronized (obj1){
                    obj1.notify();
                }
            }
        });
    }

    public void assign() {
        if(flag==1)
        {
            this.id.setText("学号:");
        }
        if(flag==4)
        {
            this.id.setText("教职工号:");
        }
        if(flag==5)
        {
            this.id.setText("管理员姓名:");
        }
        this.code.setText("密码:");
        this.name.setText("姓名:");
        System.out.println(1);
    }
    /*public static void main(String[] args) {
        JFrame frame = new JFrame("base_information");
        base_information baseInformation=new base_information();
        frame.setContentPane(baseInformation.inf);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/
}
