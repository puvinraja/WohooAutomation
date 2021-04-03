package tests.Wahoo;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.Wahoo.BasePageFit;
import pages.Wahoo.CheckOutPage;
import pages.Wahoo.HomePage;

import pages.Wahoo.ProductPage;
import pages.Wahoo.CartPage;

public class Test1 {
	public static WebDriver driver;
	
	HomePage home;
	ProductPage prod;	
	CartPage cart;
	CheckOutPage chkoutpge;
	
	@BeforeTest
	public void setup(){
		BasePageFit bp = new BasePageFit();
		driver = bp.InvokeApplication();		//launch application
	}
	
	@Test
	public void loginTest() throws InterruptedException{
		
		home = new HomePage(driver);
		home.navigateToProducts(); //navigate to products page
		
		prod = new ProductPage(driver);
		
		prod.addToCart();  // adding 1st random item
		prod.closeSideBar();
		prod.addToCart();  // adding 2nd random item
		Assert.assertEquals(prod.removeItem(), "Item was removed successfully", "Item Removed");  //remove an item
		prod.clickViewCart(); 
		
		cart = new CartPage(driver);
		cart.changeItemQuantity();  //change item quantity
		cart.clickUpdateCart();
		cart.validateUpdatedAmount(); //validate new amount
		cart.clickProceedToCheckOut(); 
		
		chkoutpge = new CheckOutPage(driver);
		chkoutpge.clickPlaceOrder(); //place order without any details
		
		SoftAssert softAssert= new SoftAssert();
		Assert.assertTrue(chkoutpge.chkEmailError(),"Email empty error");
		Assert.assertTrue(chkoutpge.chkFirstNameError(),"First name empty error") ;
		Assert.assertTrue(chkoutpge.chkLastNameError(),"Last Name empty error");
		Assert.assertTrue(chkoutpge.chkStreetError(),"Street empty error");
		Assert.assertTrue(chkoutpge.chkCityError(),"City empty error");
		Assert.assertTrue(chkoutpge.chkPhoneError(),"Phone Number empty error ");
		softAssert.assertAll();
		
		chkoutpge.changeShipping(); //change shipping mode
		
		
		chkoutpge.enterInvalidCheckoutData(); //enter provided data
		chkoutpge.clickPlaceOrder(); //placing order
		
		//validate error message
		Assert.assertEquals(chkoutpge.paymentDeclinedMessage() 
				, "Your card was declined. Your request was in live mode, but used a known test card."
				, "Payment Declined Message"); 
	}
	
	@AfterTest
    public void closeDriver() {  
        driver.close();  
   }  	
	
}
