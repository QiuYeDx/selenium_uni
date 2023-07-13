package com.example.selenium_test.selenium_questionnaire;

import com.example.selenium_test.dao.Entity.InstructionEntity;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
import java.util.*;
import java.util.Date;

//@PropertySource("classpath:application.yml")
public class SeleniumQuestionnaire {

    private Connection con; // 声明Connection链接数据库对象
    private static String API_BASE_URL = "http:127.0.0.1:8085";

    public SeleniumQuestionnaire(){
        getConnection();//建立数据库链接
    }


    /**
     * 初始化WebDriver
     *
     * @param url_driver 驱动的本地路径
     * @return WebDriver对象
     */
    public WebDriver initWebDriver(String url_driver) {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        // 初始化配置Chrome浏览器的WebDriver
        System.setProperty("webdriver.chrome.driver", url_driver.length() > 0 ? url_driver : "src/main/resources/chromedriver");
        // 创建驱动
        WebDriver driver = new ChromeDriver(option);
        // 最大化浏览器
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * 关闭浏览器
     *
     * @param driver WebDriver对象
     * @return 是否关闭成功
     */
    public boolean closeWebDriver(WebDriver driver) {
        // 关闭浏览器
        driver.quit();
        return true;
    }

    /**
     * 模拟用户登录
     *
     * @param driver WebDriver对象
     * @return 是否登录成功
     */
    public boolean testLogin(WebDriver driver) {
        // 与将要爬取的网站建立连接
        driver.get(API_BASE_URL + "/pages/login/index.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("login-div")));
        // 在username输入框中输入测试用户名
        driver.findElement(By.id("username")).sendKeys("Selenium");
        // 在password输入框中输入测试密码
        driver.findElement(By.id("password")).sendKeys("123456789");
        // 点击登录按钮
        driver.findElement(By.className("btn-primary")).click();
        return true;
    }

    public boolean testInstruction() {
        WebDriver driver = initWebDriver("src/main/resources/chromedriver");//改成自己的驱动路径
        Vector<String[]> outList=getInstruction();//获取指令
        for(String item[]:outList){
            try{
                if(Objects.equals(item[1], "wait")){
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(item[2])));
                    Thread.sleep(1000);
                }else if(Objects.equals(item[1], "input")){
                    driver.findElement(By.xpath(item[2])).clear();
                    driver.findElement(By.xpath(item[2])).sendKeys(item[3]);
                }else if(Objects.equals(item[1], "click")){
                    driver.findElement(By.xpath(item[2])).click();
                }else if(Objects.equals(item[1], "jump")){
                    driver.get(item[2]);

                }
                insert_log(item[0],item[1],"成功","");//插入指令日志
            }catch (Exception e){
                insert_log(item[0],item[1],"异常", e.getMessage());//插入指令日志
                return false;
            }
        }
        return true;
    }


    public void getConnection() {
        String Url = "jdbc:mysql://localhost/training524";//参数参考MySql连接数据库常用参数及代码示例
        String name = "root";//数据库用户名
        String psd = "20011216";//数据库密码
        String jdbcName = "com.mysql.cj.jdbc.Driver";//连接MySql数据库
        try { // 加载数据库驱动类
            Class.forName(jdbcName);
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try { // 通过访问数据库的URL获取数据库连接对象
            con = DriverManager.getConnection(Url, name, psd);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Vector<String[]> getInstruction() {
        System.out.println("调用获取指令函数");
        //未加限制limit num 限制获取几行，即全部获取
        //目前是升序排列的id，加上 desc 则为降序
        String sql = "select id,type,element,param from instruction_info order by id";
        Vector<String[]> outList=new Vector<>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getInt("id"));
//                System.out.println(resultSet.getString("type"));
//                System.out.println(resultSet.getString("element"));
//                System.out.println(resultSet.getString("param"));
                String item[] = {String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("type"),
                        resultSet.getString("element"),
                        resultSet.getString("param")};
                outList.add(item);
            }
            resultSet.close();
            pst.close();//关闭连接
            return outList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Vector<String[]> temp=new Vector<>();
            String s[]={"false"};
            temp.add(s);
            return temp;
        }
    }

    public Vector<String[]> getLog() {
        System.out.println("调用获取日志函数");
        //未加限制limit num 限制获取几行，即全部获取
        //目前是升序排列的id，加上 desc 则为降序
        String sql = "select logid,time,id,type,state,exception from instruction_log order by logid";
        Vector<String[]> outList=new Vector<>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String item[] = {String.valueOf(resultSet.getInt("logid")),
                        resultSet.getString("time"),
                        resultSet.getString("id"),
                        resultSet.getString("type"),
                        resultSet.getString("state"),
                        resultSet.getString("exception")};
                outList.add(item);
            }
            resultSet.close();
            pst.close();//关闭连接
            return outList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Vector<String[]> temp=new Vector<>();
            String s[]={"false"};
            temp.add(s);
            return temp;
        }
    }

    private void insert_log(String id,String type,String state,String exception) {//日志插入数据库
        System.out.println("插入一条日志 "+"id:"+id);
        java.util.Date date = new Date();//获得当前时间
        Timestamp timestamp = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
        String sql = "insert into instruction_log(time,id,type,state,exception) values(?,?,?,?,?)";//数据库操作语句（插入）
        try {
            PreparedStatement pst = con.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
            pst.setTimestamp(1, timestamp);
            pst.setString(2,id);
            pst.setString(3,type);
            pst.setString(4,state);
            pst.setString(5,exception);
            pst.executeUpdate();
            pst.close();//关闭连接
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean initial_table() {
        System.out.println("调用初始化表函数");
        String sql_log = "CREATE TABLE `instruction_log` (\n" +
                "  `logid` int NOT NULL AUTO_INCREMENT,\n" +
                "  `time` timestamp NULL DEFAULT NULL,\n" +
                "  `id` varchar(255) DEFAULT NULL,\n" +
                "  `type` varchar(255) DEFAULT NULL,\n" +
                "  `state` varchar(255) DEFAULT NULL,\n" +
                "  `exception` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,\n" +
                "  PRIMARY KEY (`logid`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
        String sql_instruction = "CREATE TABLE `instruction_info` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `type` varchar(255) DEFAULT NULL,\n" +
                "  `element` varchar(255) DEFAULT NULL,\n" +
                "  `param` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
        String delete_log="drop table if exists instruction_log";
        String delete_instruction="drop table if exists instruction_info";
        try{
            PreparedStatement pst =con.prepareStatement(delete_log);
            pst.execute();
            pst=con.prepareStatement(delete_instruction);
            pst.execute();
            pst=con.prepareStatement(sql_log);
            pst.execute();
            pst=con.prepareStatement(sql_instruction);
            pst.execute();
            pst.close();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean insert_one_instruction(InstructionEntity instructionEntity) {//日志插入数据库
        System.out.println("插入一条指令");
        String sql = "insert into instruction_info(type,element,param) values(?,?,?)";//数据库操作语句（插入）
        try {
            PreparedStatement pst = con.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
            pst.setString(1,instructionEntity.getType());
            pst.setString(2,instructionEntity.getElement());
            pst.setString(3,instructionEntity.getParam());
            pst.executeUpdate();
            pst.close();//关闭连接
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean insert_instruction(String string){
        System.out.println("调用插入指令函数");
        string  = string.substring(0,string.length()-1);
        try {
            String[] temp_str = string.split("# ");
            for(String str:temp_str){
                System.out.println(str);
                InstructionEntity instructionEntity = new InstructionEntity();
                String[] ins = str.split(" ");
                instructionEntity.setType(ins[0]);
                instructionEntity.setElement(ins[1]);
                if(ins.length==3){
                    instructionEntity.setParam(ins[2]);
                }
                insert_one_instruction(instructionEntity);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
