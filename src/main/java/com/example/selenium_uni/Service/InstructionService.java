package com.example.selenium_uni.Service;
import com.example.selenium_uni.dao.Entity.InstructionEntity;
import com.example.selenium_uni.dao.Entity.LogEntity;
import com.example.selenium_uni.dao.InstructionEntityMapper;
import com.example.selenium_uni.dao.LogEntityMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
@Service
public class InstructionService {
    @Autowired
    private LogEntityMapper logEntityMapper;
    @Autowired
    private InstructionEntityMapper instructionEntityMapper;
    @Value("${config.autoPass}")
    private String choice;
    @Value("${config.url}")
    private String url;

    /**
     * 插入多条指令
     * @param string
     * @return
     */
    public boolean insert_instructions(String string){
        string  = string.substring(0,string.length()-1);
        String[] temp_str = string.split("# ");
        for(String str:temp_str){
            InstructionEntity instructionEntity = new InstructionEntity();
            String[] ins = str.split(" ");
            instructionEntity.setType(ins[0]);
            instructionEntity.setElement(ins[1]);
            if(ins.length==3){
                instructionEntity.setParam(ins[2]);
            }
            if(insert_one_instruction(instructionEntity)==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 插入单条指令
     * @param instructionEntity
     * @return
     */
    public int insert_one_instruction(InstructionEntity instructionEntity){
        return instructionEntityMapper.insert_instruction(instructionEntity);
    }

    /**
     * 插入日志
     * @param id
     * @param type
     * @param state
     * @param exception
     */
    private boolean insert_log(String id,String type,String state,String exception) {//日志插入数据库
        java.util.Date date = new Date();//获得当前时间
        Timestamp timestamp = new Timestamp(date.getTime());//将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
        LogEntity logEntity=new LogEntity();
        logEntity.setId(id);
        logEntity.setTime(timestamp);
        logEntity.setType(type);
        logEntity.setState(state);
        logEntity.setException(exception);
        return logEntityMapper.insert_log(logEntity) != 0;
    }


    /**
     * 查询指令
     */
    public Vector<String []> getInstruction() {
        Vector<String[]> outList = new Vector<String[]>();
        List<InstructionEntity>result=instructionEntityMapper.queryInstructionList();
        if(!result.isEmpty()){
            for(InstructionEntity instructionEntity:result){
                String[] item = {String.valueOf(instructionEntity.getId()),
                        instructionEntity.getType(),
                        instructionEntity.getElement(),
                        instructionEntity.getParam()};
                outList.add(item);
            }
        }
        return outList;
    }

    /**
     * 查询日志
     * @return
     */
    public Vector<String[]> getLog() {
        Vector<String[]> outList = new Vector<String[]>();
        List<LogEntity> result = logEntityMapper.queryLogList();
        if(!result.isEmpty()){
            for(LogEntity logEntity:result){
                String[] item = {String.valueOf(logEntity.getLogid()),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logEntity.getTime()),
                        logEntity.getId(),
                        logEntity.getType(),
                        logEntity.getState(),
                        logEntity.getException()};
                outList.add(item);
            }
        }
        return outList;
    }

    /**
     * 初始化数据表
     */
    public boolean initial_table() {
        dropInstructionTable();
        dropLogTable();
        return createInstructionTable() && creatLogTable();
    }

    public boolean createInstructionTable(){
        try {
            // 创建数据库表
            instructionEntityMapper.createTable();
            return true;
        } catch (Exception e) {
            System.out.println("创建指令表失败");
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean creatLogTable(){
        try {
            logEntityMapper.createTable();
            return true;
        }catch (Exception e){
            System.out.println("创建日志表失败");
            System.out.println(e.getMessage());
            return false;
        }
    }
    public void dropInstructionTable(){
        instructionEntityMapper.dropTable();
    }
    public void dropLogTable(){
        logEntityMapper.dropTable();
    }

    /**
     * 创建web驱动
     * @param
     * @return
     */
    public WebDriver initWebDriver(String choice) {
        if(Objects.equals(choice, "chrome_windows")){
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");
            // 初始化配置Chrome浏览器的WebDriver
            System.setProperty("webdriver.chrome.driver", url + "/chrome_windows/chromedriver.exe");
            // 创建驱动
            WebDriver driver = new ChromeDriver(option);
            driver.manage().window().maximize();
            return driver;
        }else if(Objects.equals(choice, "chrome_mac")){
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");
            // 初始化配置Chrome浏览器的WebDriver
            System.setProperty("webdriver.chrome.driver", url + "/chrome_mac/chromedriver");
            // 创建驱动
            WebDriver driver = new ChromeDriver(option);
            driver.manage().window().maximize();
            return driver;
        }else if(Objects.equals(choice, "chrome_linux")){
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");
            option.addArguments("--headless");
            option.addArguments("--disable-gpu");
            option.addArguments("--no-sandbox");
            // 初始化配置Chrome浏览器的WebDriver
            System.setProperty("webdriver.chrome.driver", "BOOT-INF/classes/chrome_linux/chromedriver");
            // 创建驱动
            WebDriver driver = new ChromeDriver(option);
            Dimension dimension = new Dimension(1920,1080);
            driver.manage().window().setSize(dimension);
            return driver;
        }
        return null;
    }

    /**
     * 测试指令
     * @return
     */
    public boolean testInstruction() {
        WebDriver driver = initWebDriver(choice);//改成自己的驱动路径
        Vector<String[]> outList=getInstruction();//获取指令
        for(String[] item :outList){
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


}
