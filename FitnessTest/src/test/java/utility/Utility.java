package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {

	WebDriver driver;	
	
	public WebElement ClickLink(WebElement ele){

		WebElement firstResult = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.elementToBeClickable(ele));
		
		// Print the first result
		System.out.println(firstResult.getText());	
		
		return firstResult;
		
	}
	
	
}
