package db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static db.assign.ThisId;

public class shenhe {
    JPanel p;
    private JTable table;
    private JScrollPane js;
    private JButton apply;
    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/?serverTimezone=UTC" ;
    static String USER = "root";
    static String PASS="123456";
    static Connection conn = null;
    static Statement stmt = null;


    public shenhe() {
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int size=1;
            String sql;
            sql="select max(activity_id) mx from hello.activity_info";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                size=rs.getInt("mx");
            }


            String[] columnNames={"活动编号","管理员编号","活动时间","活动地点","活动需要人数","活动发布时间","活动名称"};
            String[][] o=new String[size][];

            sql="select * from hello.activity_info";
            rs=stmt.executeQuery(sql);
            int cur=0;
            while(rs.next())
            {
                String[] s=new String[7];
                for(int i=0;i<7;i++)
                {
                    s[i]=rs.getString(i+1);
                }
                o[cur]=s;
                cur++;
            }

            table=new JTable(o,columnNames);
            js.setViewportView(table);







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




        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op=JOptionPane.showConfirmDialog(null,"确定发送请求吗");
                if(op==JOptionPane.YES_OPTION)
                {
                    int activity_id=table.getSelectedRow();
                    activity_id++;
                    System.out.println(activity_id);
                    makeApply(activity_id);

                }
            }
        });
    }

    public static void makeApply(int activity_id){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();

            int user_id = 0;
            String sql;
            sql="select user_id from hello.students where student_number="+ThisId;
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                user_id=rs.getInt(1);
            }


            int apply_id=0;
            sql="select count(*) c from hello.apply_category";
            rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                apply_id=rs.getInt(1);
            }
            apply_id++;

            sql="insert into hello.apply_category VALUES ("+apply_id+","+user_id+","+activity_id+",'')";
            stmt.executeLargeUpdate(sql);
            JOptionPane.showMessageDialog(null,"已经发送成功");







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
