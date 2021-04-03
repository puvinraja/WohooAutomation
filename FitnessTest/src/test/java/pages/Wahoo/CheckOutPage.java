package pages.Wahoo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutPage {
	
	WebDriver driver;
	
	By eleShipingPrice =  By.xpath("//tr[@class='totals shipping incl']//span[@class='price']");
	By rdoExpressShipping =  By.xpath("//input[@id='s_method_amstrates_amstrates22']");
	
	By rdoCreditCard =  By.xpath("//input[@id='amasty_stripe']");	
	
	By txtEmail =  By.xpath("//div[@class='field required']//input[@type='email']");
	By txtCardNo =  By.xpath("//input[@name='cardnumber']");
	By txtExpiry =  By.xpath("//input[@name='exp-date']");
	By txtCvc =  By.xpath("//input[@name='cvc']");

	By txtFirstName=  By.xpath("//div[@name='shippingAddress.firstname']//input[@name='firstname']");
	By txtLastName =  By.xpath("//div[@name='shippingAddress.lastname']//input[@name='lastname']");
	By txtStreet =  By.xpath("//div[@name='shippingAddress.street.0']//input[@name='street[0]']");
	By txtCity =  By.xpath("//div[@name='shippingAddress.city']//input[@name='city']");
	By txtPostCode =  By.xpath("//div[@name='shippingAddress.postcode']//input[@name='postcode']");	
	By txtPhone=  By.xpath("//div[@name='shippingAddress.telephone']//input[@name='telephone']");

	By errEmail =  By.id("customer-email-error");
	By errFirstName =  By.xpath("//div[@name='shippingAddress.firstname']//div[@class='field-error']");
	By errLastName =  By.xpath("//div[@name='shippingAddress.lastname']//div[@class='field-error']");
	By errStreet =  By.xpath("//div[@name='shippingAddress.street.0']//div[@class='field-error']");
	By errCity =  By.xpath("//div[@name='shippingAddress.city']//div[@class='field-error']");
	By errPostCode =  By.xpath("//div[@name='shippingAddress.postcode']//div[@class='field-error']");
	By errPhone =  By.xpath("//div[@name='shippingAddress.telephone']//div[@class='field-error']");
	
	//By btnPlaceOrder = By.cssSelector(".action.primary.checkout.amasty");
	By btnPlaceOrder = By.xpath("//div[@class='checkout-block']//div[@class='checkout-payment-method submit']//button");	

	public CheckOutPage(WebDriver dr) {
		this.driver = dr;
	}
	
	public boolean chkError(By by){
		try {
			Boolean bln = driver.findElement(by).isDisplayed();
			return bln;
		} catch (NoSuchElementException e) {			
			e.printStackTrace();
			return false;
		}		
	}
	
	//click place order
	public void clickPlaceOrder(){	
		
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.elementToBeClickable(rdoCreditCard));		
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath("//div[@class='loading-mask']")), "style", "display: none;"));		
		driver.findElement(btnPlaceOrder).click();
		
		waitForLoaderDisappear();
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath("//div[@class='loading-mask']")), "style", "display: none;"));
	}
	
	//error display
	public boolean chkEmailError(){
		return chkError(errEmail);
	}
	public boolean chkFirstNameError(){
		return chkError(errFirstName);
	}	
	public boolean chkLastNameError(){
		return chkError(errLastName);
	}	
	public boolean chkStreetError(){
		return chkError(errStreet);
	}	
	public boolean chkCityError(){
		return chkError(errCity);
	}	
	public boolean chkPostError(){
		return chkError(errPostCode);
	}	
	public boolean chkPhoneError(){
		return chkError(errPhone);
	}	
	
	public void changeShipping(){
		double oldAmountD,newAmountD;
		String oldShippingPrice = driver.findElement(eleShipingPrice).getText();		
		System.out.println("old shipping price - "+oldShippingPrice);
		driver.findElement(rdoExpressShipping).click();		
		waitForLoaderDisappear();
		String newShippingPrice = driver.findElement(eleShipingPrice).getText();
		System.out.println("new shipping price - "+newShippingPrice);
				
		oldAmountD = Double.parseDouble(oldShippingPrice.replace("€", ""));
		newAmountD = Double.parseDouble(newShippingPrice.replace("€", ""));
		
		if(Double.compare(newAmountD, oldAmountD) !=0)
		{
			System.out.println("shipping price updated");
		}else{
			System.out.println("shipping price not updated");
		}
	}
	
	
	public void waitForLoaderDisappear(){
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath("//body[@aria-busy]")), "aria-busy", "false"));
	
	}
	
	public void enterInputText(WebElement inpEle, String Value){
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.elementToBeClickable(rdoCreditCard));
		this.waitForLoaderDisappear();	
		inpEle.clear();
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.elementToBeClickable(rdoCreditCard));	
		this.waitForLoaderDisappear();
		inpEle.sendKeys(Value);
	}
	
	
	public void enterInvalidCheckoutData(){
		enterInputText(driver.findElement(txtEmail),"someone@whocares.com");
		enterInputText(driver.findElement(txtFirstName),"Test");
		enterInputText(driver.findElement(txtLastName),"Tester");
		enterInputText(driver.findElement(txtStreet),"Comandante Izarduy 67");
		enterInputText(driver.findElement(txtCity),"Barcelona");
		enterInputText(driver.findElement(txtPostCode),"08940");
		enterInputText(driver.findElement(txtPhone),"5555555555");
		
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.elementToBeClickable(rdoCreditCard));	
		
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@name,'privateStripeFrame')]")));
		
		driver.findElement(txtCardNo).sendKeys("4111111111111111");
		driver.findElement(txtExpiry).sendKeys("0824");
		driver.findElement(txtCvc).sendKeys("111");	
		
		driver.switchTo().defaultContent();		
		
	}
	
	
	public String paymentDeclinedMessage(){
		
		WebElement viewLink = new WebDriverWait(driver, 10)
		        .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='checkout']//div[@data-ui-id='checkout-cart-validationmessages-message-error']")));
		
		return viewLink.getText().trim();
	}
		
}