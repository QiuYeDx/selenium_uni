package selenium_questionnaire;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
//@PropertySource("classpath:application.properties")
public class SeleniumQuestionnaire {
    private static String API_BASE_URL = "http:127.0.0.1:8085";

    /**
     * 初始化WebDriver
     * @param url_driver 驱动的本地路径
     * @return WebDriver对象
     */
    public WebDriver initWebDriver(String url_driver){
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");
        // 初始化配置Chrome浏览器的WebDriver
        System.setProperty("webdriver.chrome.driver", url_driver.length() > 0 ? url_driver : "/Users/baizihan/Documents/学习空间/Java/selenium_test/src/main/resources/chromedriver");
        // 创建驱动
        WebDriver driver = new ChromeDriver(option);
        // 最大化浏览器
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * 关闭浏览器
     * @param driver WebDriver对象
     * @return 是否关闭成功
     */
    public boolean closeWebDriver(WebDriver driver){
        // 关闭浏览器
        driver.quit();
        return true;
    }

    /**
     * 模拟用户登录
     * @param driver WebDriver对象
     * @return 是否登录成功
     */
    public boolean testLogin(WebDriver driver){
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
}
