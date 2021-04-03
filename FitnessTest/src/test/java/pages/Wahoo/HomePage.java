package pages.Wahoo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	WebDriver driver;
	WebDriverWait wait;
	public HomePage(WebDriver dr)
	{
		this.driver = dr;
	}		
	
	By btnShop = By.xpath("//span[text()='SHOP']");
	By covidMsg = By.xpath("//span[contains(text(),'COVID')]");
	
	public void navigateToProducts(){
		
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		driver.findElement(btnShop).click();
		
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(covidMsg)).click().build().perform();	

	}	
	
}
