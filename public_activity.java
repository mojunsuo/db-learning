package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static db.assign.ThisId;

public class public_activity {
    public JTextField activity_time;
    public JTextField public_time;
    public JTextField activity_location;
    public JTextField activity_name;
    public JTextField activity_need;
    JPanel pub_ac;
    private JLabel activity_time_label;
    private JLabel activity_location_label;
    private JLabel public_time_label;
    private JLabel activity_name_label;
    private JLabel activity_need_label;
    private JButton allButton;
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/?serverTimezone=UTC" ;
    static String USER = "root";
    static String PASS="123456";
    static Connection conn = null;
    static Statement stmt = null;


    public public_activity() {
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActivity();
            }
        });
    }

    public void addActivity(){
        String ac_time=activity_time.getText();
        String pc_time=public_time.getText();
        String a_location=activity_location.getText();
        String a_name=activity_name.getText();
        String a_need=activity_need.getText();
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int activity_id = 0;
            String sql;
            sql="select max(activity_id) from hello.activity_info";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                activity_id=rs.getInt(1);
            }
            activity_id++;
            sql="insert into hello.activity_info VALUES("+activity_id+","+ThisId+","+"'"+ac_time+"',"+"'"+a_location+"',"+a_need+","+"'"+pc_time+"',"+"'"+a_name+"')";
            stmt.executeLargeUpdate(sql);
            JOptionPane.showMessageDialog(null,"已经录入成功");







            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) {
                    stmt.close();
                }
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) {
                    conn.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
