package db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class assign {
    private JButton student_assign;
    private JButton student_up;
    private JButton teacher_up;
    private JButton manager_up;
    private JButton manager_assign;
    private JButton teacher_assign;
    JPanel as;
    public static Object obj=new Object();
    public static Object obj1=new Object();
    public static Object obj2=new Object();
    public static Object obj_manager=new Object();
    public static int flag=0;
    public static int flag_manager=0;
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/?serverTimezone=UTC" ;
    static String USER = "root";
    static String PASS="123456";
    static Connection conn = null;
    static Statement stmt = null;
    static String ThisId =null;
    static String ThisCode=null;
    static String ThisManager=null;

    public assign() {
        student_assign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=1;
                synchronized (obj)
                {
                    obj.notify();
                }

            }
        });
        student_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=2;
                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        manager_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=3;
                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        teacher_assign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=4;
                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        manager_assign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=5;
                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        teacher_up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag=6;
                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
    }
    static JFrame frame = new JFrame("assign");
    public static void main(String[] args) throws InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setContentPane(new assign().as);

        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        synchronized (obj)
        {
            obj.wait();
        }
        frame.dispose();
        //System.out.println(flag);
        if(flag==1||flag==4||flag==5)
        {
            base_information baseInformation=new base_information();

            JFrame frame1=new JFrame("base_information");
            frame1.setContentPane(baseInformation.inf);
            frame1.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                    WINDOW_WIDTH, WINDOW_HEIGHT);
            baseInformation.assign();
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.pack();
            frame1.setVisible(true);
            synchronized (obj1)
            {
                obj1.wait();
            }
            String ID= baseInformation.id.getText();
            String CODE= baseInformation.code.getText();
            String NAME= baseInformation.name.getText();
            ID=ID.substring(3);
            CODE=CODE.substring(3);
            NAME=NAME.substring(3);
            assignthis(ID,CODE,NAME);
            System.out.println(ID+CODE+NAME);
            frame1.dispose();
        }
        if(flag==2||flag==6||flag==3)
        {
            up_information upInformation=new up_information();

            JFrame frame1=new JFrame("base_information");
            frame1.setContentPane(upInformation.up);
            frame1.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                    WINDOW_WIDTH, WINDOW_HEIGHT);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.pack();
            frame1.setVisible(true);
            synchronized (obj2)
            {
                obj2.wait();
            }
            String ID= upInformation.id.getText();
            String CODE= upInformation.code.getText();
            ID=ID.substring(5);
            CODE=CODE.substring(5);
            while(!upthis(ID,CODE,flag))
            {
                JOptionPane.showMessageDialog(null, "你的密码貌似错了噢，请重新输入");
                synchronized (obj2)
                {
                    obj2.wait();
                }
                ID=upInformation.id.getText();
                CODE=upInformation.code.getText();
                ID=ID.substring(5);
                CODE=CODE.substring(5);
            }
            JOptionPane.showMessageDialog(null, "登录成功");
            frame1.dispose();
            ThisId=ID;
            ThisCode=CODE;

            if(flag==2)
            {

                JFrame frame2 = new JFrame("shenhe");
                frame2.setContentPane(new shenhe().p);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.pack();
                frame2.setVisible(true);
            }
            else{
                JFrame frame2 = new JFrame("two_functions");
                frame2.setContentPane(new two_functions().mission);
                frame2.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                        WINDOW_WIDTH, WINDOW_HEIGHT);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.pack();
                frame2.setVisible(true);
                synchronized (obj_manager)
                {
                    obj_manager.wait();
                }
                frame2.dispose();
                if(flag_manager==0)
                {
                    JFrame frame3 = new JFrame("public_activity");
                    frame3.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                            WINDOW_WIDTH, WINDOW_HEIGHT);
                    frame3.setContentPane(new public_activity().pub_ac);
                    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame3.pack();
                    frame3.setVisible(true);
                }
                else{
                    JFrame frame3 = new JFrame("verify_activity");
                    frame3.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                            WINDOW_WIDTH, WINDOW_HEIGHT);
                    frame3.setContentPane(new verify_activity().jpanel);
                    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame3.pack();
                    frame3.setVisible(true);
                }
            }
        }


    }

    public static void assignthis(String Id, String Code,String Name)
    {

        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();


            String sql;
            sql="select max(user_id) mx from hello.users";
            ResultSet rs = stmt.executeQuery(sql);
            int mx=0;
            while(rs.next())
            {
                mx=rs.getInt("mx");
                //System.out.println(mx);
                mx++;

            }
            sql="insert into hello.users (user_id) VALUES ("+mx+")";

            stmt.executeLargeUpdate(sql);

            sql="insert into hello.students (student_number, user_id, student_code, student_name) VALUES ("+Id+","+mx+","+"'"+Code+"'"+","+"'"+Name+"')";

            stmt.executeLargeUpdate(sql);

            JOptionPane.showMessageDialog(null,"欢迎您,您的用户编号是"+mx+",您的姓名是:"+Name+",您已经注册成功");


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
        System.out.println("Goodbye!");
    }



    public static boolean upthis(String Id,String Code,int flag){
        String get = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();


            String sql=null;

            if(flag==2)
            {
                sql="select student_code code from hello.students where student_number="+Id;
            }
            else{
                sql="select manager_code code from hello.manager where manager_id="+Id;
            }
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                get=rs.getString("code");

            }
            
            

            



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
        if(Code.equals(get))
        {
            return true;
        }
        else{
            return false;
        }
    }
}
