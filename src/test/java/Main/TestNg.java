package Main;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static java.time.Duration.ofSeconds;

public class TestNg {

    // Инициализируем Webdriver при помощи метода public Static, для автоматического использования в других классах
    public static WebDriver driver;

    @BeforeMethod(groups = {"test1"})
    @Parameters({"preferredBrowser"})
    public void setUp(String preferredBrowser) {

        if (preferredBrowser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(ofSeconds(10));

        } else if (preferredBrowser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(co);
            driver.manage().timeouts().implicitlyWait(ofSeconds(10));
        }
    }

    @Parameters({"StartUrlLogin"})
    @Test(testName = "Pixel_Perfect test", groups = {"test1"})
    public  void case_1(String StartUrlLogin) throws IOException, InterruptedException, UnsupportedFlavorException {
        System.out.println("*** Pixel_Perfect ***");

        // Переход на начальную страницу
        driver.get(StartUrlLogin);

        Thread.sleep(2500);
        // Развертывание страницы на весь экран
        driver.manage().window().maximize();

        try {
            //ofSeconds(6)
            //Ожидаем появление accept button, и в случае когда она появилась решаем ее не более  минут
            WebElement instExchBtn = new WebDriverWait(driver, ofSeconds(6)).until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//button[@class=\"close-button\"]")));
            instExchBtn.click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

