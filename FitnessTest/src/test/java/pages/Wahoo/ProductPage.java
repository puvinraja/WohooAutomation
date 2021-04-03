package pages.Wahoo;


import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Utility;


public class ProductPage extends BasePageFit{
	WebDriver driver;
	WebDriverWait wait;
	
	public ProductPage(WebDriver dr)
	{
		this.driver = dr;
	}	
	
	WebElement ele;
	List<WebElement> prodList;
	Utility ul;

	public void addToCart(){
		String prod="";
		prodList = driver.findElements(By.xpath("//ul[@class='products-grid']/li[@class='item']"));
		do {
		     
			Random rd = new Random(); // creating Random object for random number
			int rndNo = rd.nextInt(prodList.size()-1);
			
			prod = addRandomItem(prodList,rndNo);
			  
		}while(prod.isEmpty());
		
		WebElement sideBar = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.visibilityOfElementLocated(By.className("minicart-messages")));
				

		String sideBarMsg = "You added "+prod.trim()+" to your shopping cart.";
		System.out.println("expMsg - "+sideBarMsg);

		String sideBarMsgActual = sideBar.getText();
		
		if(sideBarMsg.equalsIgnoreCase(sideBarMsgActual.trim())){
			System.out.println("Cart added successful: "+sideBarMsgActual);
		}else{
			System.out.println("Cart added not successful: expcted msg -"+sideBarMsg );
		}		
		
	}	
	
	
	public String addRandomItem(List<WebElement> ele, int rnd){
			
		WebElement webElement = ele.get(rnd);			
		WebElement cartButton;
		WebElement product;
		String prodName="";

		try {
			
			cartButton = webElement.findElement(By.xpath(".//div[@class='actions']//button[@title='Add to Cart']"));			
			product = webElement.findElement(By.xpath("./div[@class='text']/p[@class='product-name']"));
			
			//check random item has add to cart button
			//if yes click else return empty
			if(cartButton.isEnabled()){
				prodName = product.getText();
				System.out.println("random no - "+ rnd+"-"+ prodName);

				Actions actions = new Actions(driver);
				actions.moveToElement(cartButton).click().build().perform();					
				
			}
			return prodName;
		} catch (NoSuchElementException e) {
			return prodName;
		}
	}

	
	public void closeSideBar(){
		WebElement sideBarClose = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-minicart-close")));
		sideBarClose.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='SHOP']")));		
	}
	
	
	public String removeItem(){
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='action delete']")));
		List<WebElement> removeItem =  driver.findElements(By.xpath("//a[@class='action delete']"));
		removeItem.get((removeItem.size()-1)).click();
		
		
		WebElement dialogOK = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//footer[@class='modal-footer']/button[@class='action-primary action-accept']")));
		dialogOK.click();
		
		WebElement removeMsg = new WebDriverWait(driver, 10)
	        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Item was removed successfully']")));
		System.out.println(removeMsg.getText());
		return removeMsg.getText().trim();
		
	}
	
	
	public void clickViewCart(){
		WebElement viewLink = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='action viewcart']")));
		
		viewLink.click();		
	}		
}
