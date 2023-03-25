package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static db.assign.ThisId;

public class verify_activity {
    private JTable apply_info;
    private JButton pass;
    private JButton nopass;
    private JScrollPane js;
    JPanel jpanel;
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/?serverTimezone=UTC" ;
    static String USER = "root";
    static String PASS="123456";
    static Connection conn = null;
    static Statement stmt = null;




    public verify_activity() {
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int size=0;
            String sql;
            sql="select count(*) c from hello.apply_category,hello.activity_info where hello.apply_category.apply_id not in\n" +
                    "                    ((select hello.apply_category.apply_id from hello.apply_category,hello.pass_category  where hello.apply_category.apply_id=hello.pass_category.apply_id )\n" +
                    "                    union all\n" +
                    "                    (select hello.apply_category.apply_id from hello.apply_category,hello.notpass_category where hello.notpass_category.apply_id=hello.apply_category.apply_id))\n" +
                    "and hello.apply_category.activity_id=hello.activity_info.activity_id\n" +
                    "and hello.activity_info.manager_id="+ThisId;
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                size=rs.getInt(1);
            }


            String[] columnNames={"申请号","用户编号","申请的活动编号","申请时间","负责的管理员编号","活动时间","活动地点","需要人数","活动名称"};
            String[][] o=new String[size][];

            sql="select apply_id,user_id,apply_category.activity_id,apply_time,manager_id,activity_time,activity_location,activity_need,activity_name  from hello.apply_category,hello.activity_info where hello.apply_category.apply_id not in\n" +
                    "                    ((select hello.apply_category.apply_id from hello.apply_category,hello.pass_category  where hello.apply_category.apply_id=hello.pass_category.apply_id )\n" +
                    "                    union all\n" +
                    "                    (select hello.apply_category.apply_id from hello.apply_category,hello.notpass_category where hello.notpass_category.apply_id=hello.apply_category.apply_id))\n" +
                    "and hello.apply_category.activity_id=hello.activity_info.activity_id\n" +
                    "and hello.activity_info.manager_id="+ThisId;
            rs=stmt.executeQuery(sql);
            int cur=0;
            while(rs.next())
            {
                String[] s=new String[9];
                for(int i=0;i<9;i++)
                {
                    s[i]=rs.getString(i+1);
                }
                o[cur]=s;
                cur++;
            }

            apply_info=new JTable(o,columnNames);
            js.setViewportView(apply_info);







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


        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=apply_info.getSelectedRow();
                int count=apply_info.getColumnCount();
                int h=Integer.parseInt((String) apply_info.getValueAt(row,0));
                doPass(h);
            }
        });
        nopass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=apply_info.getSelectedRow();
                int count=apply_info.getColumnCount();
                int h=Integer.parseInt((String) apply_info.getValueAt(row,0));
                doNopass(h);
            }
        });


    }

    private void doPass(int apply_id) {
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int pass_id=0;
            String sql;
            sql="select count(*) c from hello.pass_category";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                pass_id=rs.getInt(1);
            }
            pass_id++;

            sql="insert into hello.pass_category VALUES ("+pass_id+","+apply_id+")";
            stmt.executeLargeUpdate(sql);


            JOptionPane.showMessageDialog(null,"此申请已被您通过");







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

    private void doNopass(int apply_id){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int pass_id=0;
            String sql;
            sql="select count(*) c from hello.notpass_category";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                pass_id=rs.getInt(1);
            }
            pass_id++;

            sql="insert into hello.notpass_category VALUES ("+pass_id+","+apply_id+")";
            stmt.executeLargeUpdate(sql);


            JOptionPane.showMessageDialog(null,"此申请已被您拒绝");







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
