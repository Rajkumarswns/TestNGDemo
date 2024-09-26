// import testng
package test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.testng.Assert.*;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;

// how to enable TestNGReport for Intellj IDEA
//https://www.jetbrains.com/help/idea/testng.html#run-testng-with-reports

public class AuthenticationTest extends TestManager {

    int numberOfItemsInCart = 0;

    @Test(priority = 1)
    public void testLoginPage() {

        try {
            driver.get("https://www.saucedemo.com/");

            String title = driver.getTitle();
            if (title.equals("Swag Labs")) {
                System.out.println("Test Passed");
                assert true;
            } else {
                System.out.println("Test Failed");
                assert false;
                return;
            }
        } catch (Exception e) {
            System.out.println("Test Failed");
            assert false;
            driver.quit();

        } finally {
            // driver.quit();
        }


    }

    @Test(dependsOnMethods = {"testLoginPage"}, priority = 2)
    public void testSuccessfulLogin() {

        WebElement txtuserName = driver.findElement(By.id("user-name"));
        WebElement txtpassword = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        txtuserName.sendKeys("standard_user");
        txtpassword.sendKeys("secret_sauce");

        btnLogin.click();

        // Look for app_logo
        WebElement appLogo = driver.findElement(By.className("app_logo"));
        if (appLogo.isDisplayed()) {
            System.out.println("Test Passed");
            assert true;
        } else {
            System.out.println("Test Failed");
            assert false;
        }

    }

    @Test(dependsOnMethods = {"testSuccessfulLogin"}, dataProvider = "itemsProvider", priority = 3)
    public void testAddItemToCart(String itemName) {

        System.out.println(itemName);
        System.out.println("-----------------");
        List<WebElement> productsList = driver.findElements(By.className("inventory_item"));

        // verify if there are 6 products in the list

        System.out.println("Number of products: " + productsList.size());


        assertEquals(productsList.size(), 6, "Number of products is not 6");

        for (WebElement item : productsList) {
            WebElement product = item.findElement(By.className("inventory_item_label"));
            WebElement pricebar = item.findElement(By.className("pricebar"));
            WebElement price = pricebar.findElement(By.className("inventory_item_price"));

            WebElement cartbtn = pricebar.findElement(By.className("btn_inventory"));
            WebElement productname = product.findElement(By.className("inventory_item_name"));

            if (productname.getText().equals(itemName)) {
                cartbtn.click();
                System.out.println("Item added to cart");
                // verify the add to cart button is now remove
                cartbtn = pricebar.findElement(By.className("btn_inventory"));
                System.out.println("Add to cart button is now remove " + cartbtn.getText());
                numberOfItemsInCart++;
                WebElement numberofItemInCart = driver.findElement(By.className("shopping_cart_badge"));
                System.out.println("Number of items in the cart: " + numberofItemInCart.getText());
                break;

            }

        }
    }

    @Test(dependsOnMethods = {"testAddItemToCart"}, priority = 4)
    public void testCartList() {

        WebElement cart = driver.findElement(By.className("shopping_cart_link"));
        cart.click();

        // verify if the cart page is displayed
        WebElement cartPageLabel = driver.findElement(By.className("title"));
        String cartPageLabelText = cartPageLabel.getText();
        assertEquals(cartPageLabelText, "Your Cart", "Cart page is not displayed");
        if (cartPageLabelText.equals("Your Cart")) {
            System.out.println("Cart page is displayed");
        } else {
            System.out.println("Cart page is not displayed");
        }

        // Get the Cart List
        List<WebElement> cartList = driver.findElements(By.className("cart_list"));

        for (WebElement item : cartList) {

            WebElement cartItem = item.findElement(By.className("cart_item"));
            WebElement cartItemLabel = cartItem.findElement(By.className("inventory_item_name"));
            WebElement cartItemPrice = item.findElement(By.className("inventory_item_price"));
            WebElement cartItemQty = item.findElement(By.className("cart_quantity"));

            System.out.println("Cart Item Name:" + cartItemLabel.getText() + " | " +
                    "Cart Item Price :" + " | " + cartItemPrice.getText() + " | " +
                    "Cart Item Qty :" + cartItemQty.getText() + "</br>");

            System.out.println(cartItemLabel.getText());
            System.out.println(cartItemPrice.getText());
            System.out.println(cartItemQty.getText());

            System.out.println("---------------------------");
        }


    }

    @Test(dependsOnMethods = {"testCartList"}, priority = 5)
    public void testLogout() {
        WebElement btnMenu = driver.findElement(By.id("react-burger-menu-btn"));
        btnMenu.click();

        // add explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> driver.findElement(By.id("logout_sidebar_link")).isDisplayed());

        WebElement btnLogout = driver.findElement(By.id("logout_sidebar_link"));
        btnLogout.click();

        wait.until(driver -> driver.findElement(By.id("login-button")));

        WebElement btnLogin = driver.findElement(By.id("login-button"));
        if (btnLogin.isDisplayed()) {
            System.out.println("Test Passed");
            assert true;
        } else {
            System.out.println("Test Failed");
            assert false;
        }
      //  driver.quit();

    }

    @DataProvider(name = "itemsProvider")
    public Object[][] Items() {

        return new Object[][]{new String[]{"Sauce Labs Backpack"},
                {"Sauce Labs Bike Light"},
                {"Sauce Labs Fleece Jacket"},
                {"Sauce Labs Bolt T-Shirt"},
                {"Sauce Labs Onesie"},
                {"Test.allTheThings() T-Shirt (Red)"}


        };
    }


}