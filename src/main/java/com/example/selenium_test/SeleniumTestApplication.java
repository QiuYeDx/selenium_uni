package com.example.selenium_test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import selenium_questionnaire.SeleniumQuestionnaire;

import java.sql.*;
import java.util.Calendar;
import java.util.UUID;
import java.util.Date;

@SpringBootApplication
public class SeleniumTestApplication {
    static Logger log = Logger.getLogger(SeleniumTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SeleniumTestApplication.class, args);
        SeleniumQuestionnaire seleniumQuestionnaire = new SeleniumQuestionnaire();
        WebDriver driver =  seleniumQuestionnaire.initWebDriver("/Users/baizihan/Documents/学习空间/Java/selenium_test/src/main/resources/chromedriver");

    }


//    private static void insert(String logName,String logStatue) {//日志插入数据库
//        String Url = "jdbc:mysql://localhost/training524";//参数参考MySql连接数据库常用参数及代码示例
//        String name = "root";//数据库用户名
//        String psd = "20011216";//数据库密码
//        String jdbcName = "com.mysql.cj.jdbc.Driver";//连接MySql数据库
//        java.util.Date date = new Date();//获得当前时间
//        Timestamp timestamp = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
//        String sql = "insert into log_info(id,logName,logTime,logStatue) values(?,?,?,?)";//数据库操作语句（插入）
//        try {
//            Class.forName(jdbcName);//向DriverManager注册自己
//            Connection con = DriverManager.getConnection(Url, name, psd);//与数据库建立连接
//            PreparedStatement pst = con.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
//            String s = UUID.randomUUID().toString();//产生一个随机序列作为id号
//            pst.setString(1, s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24));
//            pst.setString(2,logName);
//            pst.setTimestamp(3,timestamp);
//            pst.setString(4,logStatue);
//            pst.executeUpdate();
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {//执行与数据库建立连接需要抛出SQL异常
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
