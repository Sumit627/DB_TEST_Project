package com.twitter.pages;

import com.twitter.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class LoginPage extends Utils {

    WebDriver driver;

    @FindBy(xpath="*//span[text()='Log in to Twitter']")
    WebElement loginHeader;

    @FindBy(xpath="*//input[@name='session[username_or_email]' and @type='text']")
    WebElement userName;

    @FindBy(xpath="*//input[@type='password']")
    WebElement passWord;

    @FindBy(xpath="*//div[@data-testid='LoginForm_Login_Button' and not(@aria-disabled='true')]")
    WebElement loginButton_enabled;

    @FindBy(xpath="*//div[@data-testid='LoginForm_Login_Button' and @aria-disabled='true']")
    WebElement loginButton_disabled;

    @FindBy(xpath="*//div[contains(text(),'Please double-check and try again.')]")
    WebElement loginFailedErrorMessage;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public boolean loginPageHeaderCheck(){
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        highlightElement(driver, loginHeader);
        return loginHeader.isDisplayed();
    }

    public void loginFieldValidation_OnlyUserName() throws InterruptedException {
        if (loginButton_disabled.isDisplayed() == true) {
            System.out.println("Login button is disabled");
            highlightElement(driver, userName);
            System.out.println("Username input :"+prop.getProperty("UserName"));
            type(userName,prop.getProperty("UserName"));
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
            Thread.sleep(2000);
            highlightElement(driver, userName);
            assertTrue(loginButton_disabled.isDisplayed());
        }
    }

    public void loginFieldValidation_OnlyPassword() {
        try {
            driver.navigate().refresh();
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            if (loginButton_disabled.isDisplayed()) {
                System.out.println("Login button is disabled");
                highlightElement(driver, passWord);
                System.out.println("Password input :" + prop.getProperty("Password"));
                type(passWord, prop.getProperty("Password"));
                driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
                highlightElement(driver, passWord);
                assertTrue(loginButton_disabled.isDisplayed());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loginCredentialInput(String str) {
        if (str.equalsIgnoreCase("INVALID_CREDENTIAL")) {
            highlightElement(driver, userName);
            System.out.println("Invalid Username input : "+prop.getProperty("Invalid_UserName"));
            type(userName,prop.getProperty("Invalid_UserName"));
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
            highlightElement(driver, passWord);
            System.out.println("Invalid Password input :"+prop.getProperty("Invalid_Password"));
            type(passWord,prop.getProperty("Invalid_Password"));
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
            highlightElement(driver, passWord);
        }
        else{
            highlightElement(driver, userName);
            System.out.println("Username input :"+prop.getProperty("UserName"));
            type(userName,prop.getProperty("UserName"));
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
            highlightElement(driver, passWord);
            System.out.println("Username input :"+prop.getProperty("Password"));
            type(passWord,prop.getProperty("Password"));
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
            highlightElement(driver, passWord);
        }
    }

    public void loginButtonClick(){
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
        highlightElement(driver,loginButton_enabled);
        click(loginButton_enabled);
    }

    public void errorMessageValidation(String errMsg){
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT,TimeUnit.SECONDS);
        highlightElement(driver,loginFailedErrorMessage);
        assertTrue(loginFailedErrorMessage.getText().trim().contains(errMsg));
    }

}
