package pages.Wahoo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageFit {
	public WebDriver driver;
	public WebDriverWait wait;
	
	
	public WebDriver InvokeApplication(){
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://eu.wahoofitness.com/");
		driver.manage().window().maximize();
		
		return driver;

}
}