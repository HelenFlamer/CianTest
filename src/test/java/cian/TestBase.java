package cian;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static cian.Helper.generalSite;
import static cian.Helper.driver;
import static cian.Helper.wait;


public class TestBase {

    public TestBase() {
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
        driver.get(generalSite);
    }

    @AfterMethod
    public void closeDriver() {
        driver.close();
    }


}
