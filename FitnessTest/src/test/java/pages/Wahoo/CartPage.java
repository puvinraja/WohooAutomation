package pages.Wahoo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class CartPage {
	
	WebDriver driver;
	public CartPage(WebDriver dr)
	{
		this.driver = dr;
	}		
	
	WebElement updateCart;
	double totAmountD;
	
	public void changeItemQuantity(){
		updateCart =  new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span[text()='Update Cart']")));		
	      WebElement quantity=driver.findElement(By.cssSelector("input[id^='cart']"));
	      
	      String qnty = quantity.getAttribute("value");
	      
	      System.out.println("old quantity - "+qnty);
	      
	      quantity.clear();
	      
	      int newQnty = Integer.parseInt(qnty)+1;
	      
	      String totAmount = driver.findElement(By.xpath("//table[@id ='shopping-cart-table']//td[@class='col subtotal']//span[@class='price']")).getText();
	      
	      System.out.println("old amount - "+totAmount);	    

	      totAmount = totAmount.replace("€", "").replace(",", "");
	      totAmountD = Double.parseDouble(totAmount);	      
	      
	      quantity.sendKeys(String.valueOf(newQnty));		
	
	}
	
	
	public void clickUpdateCart(){
	      updateCart.click();
	}
	
	public void validateUpdatedAmount(){
		
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)   
			      .withTimeout(Duration.ofSeconds(30))
			      .pollingEvery(Duration.ofSeconds(6))
			      .ignoring(NoSuchElementException.class);
		
			    WebElement newAmtEle = wait.until(new Function<WebDriver, WebElement>() {
			      public WebElement apply(WebDriver driver) {
			    	  WebElement newAmtEle = driver.findElement(By.xpath("//table[@id ='shopping-cart-table']//td[@class='col subtotal']//span[@class='price']"));
				  		String newTotAmt = newAmtEle.getText();
				  		newTotAmt = newTotAmt.replace("€", "").replace(",","");
				        double newTotAmtD = Double.parseDouble(newTotAmt);		
				        
						if(Double.compare(newTotAmtD, totAmountD) > 0 ){					
							return newAmtEle;
						}else{
							System.out.println("FluentWait Failed");
							return null;
						}
			      	}
			    });
		System.out.println("new quantity - "+2);
		System.out.println("new amount - "+newAmtEle.getText());		
	}
	
	
	public void clickProceedToCheckOut(){
		driver.findElement(By.xpath("//button[@title='Proceed to Checkout']")).click();	
	}
}
