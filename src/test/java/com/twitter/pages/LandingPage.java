package com.twitter.pages;

import com.twitter.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends Utils {

    WebDriver driver;

    //pagefactory - OR

    @FindBy(xpath="*//span[contains(normalize-space(),'Join Twitter today.')]")
    WebElement landingPage_header;

    @FindBy(xpath="*//a[@data-testid='loginButton']")
    WebElement loginButton;

    public LandingPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    ///actions
    public boolean validateLandingPageIsDisplayed(){
        return landingPage_header.isDisplayed();
    }

    public void clickOnLoginButton(){
        highlightElement(driver, loginButton);
        click(loginButton);
    }
}
