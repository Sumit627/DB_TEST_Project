package com.twitter.pages;

import com.twitter.utils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class HomePage extends Utils {

    WebDriver driver;

    @FindBy(xpath="*//h2[contains(@role,'heading')]/span[text()='Home']")
    WebElement homePageTitle;

    @FindBy(xpath="*//span[text()='Profile']")
    WebElement profileLeftPaneOption;

    @FindBy(xpath="*//span[text()='Edit profile']")
    WebElement editProfileBtn;

    @FindBy(xpath="*//span[text()='Save']")
    WebElement saveProfileButton;

    @FindBy(xpath="*//*[@data-testid='SideNav_AccountSwitcher_Button']")
    WebElement switchAccountMenu;

    @FindBy(xpath="*//a[@data-testid='AccountSwitcher_Logout_Button']")
    WebElement logoutOptionLink;

    @FindBy(xpath="*//div[@data-testid='confirmationSheetConfirm']")
    WebElement logoutConfirmButton;

    public HomePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void homePageTitleCheck(){
        try{
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            highlightElement(driver, homePageTitle);
            assertTrue(homePageTitle.isDisplayed());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void navigateToProfileSection(){
        click(profileLeftPaneOption);
        assertTrue(editProfileBtn.isDisplayed());
        highlightElement(driver,editProfileBtn);
        click(editProfileBtn);
    }

    public void logout(){
        try{
            highlightElement(driver, switchAccountMenu);
            click(switchAccountMenu);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            highlightElement(driver,logoutOptionLink);
            click(logoutOptionLink);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            highlightElement(driver, logoutConfirmButton);
            click(logoutConfirmButton);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
