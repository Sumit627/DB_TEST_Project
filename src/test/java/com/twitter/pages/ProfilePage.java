package com.twitter.pages;

import com.twitter.utils.Utils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.assertTrue;

public class ProfilePage extends Utils {

    WebDriver driver;

    @FindBy(xpath="*//span[text()='Edit profile']")
    WebElement editProfileBtn;

    @FindBy(xpath="*//textarea[@name='description']")
    WebElement editBioSection;

    @FindBy(xpath="*//input[@name='location']")
    WebElement editLocation;

    @FindBy(xpath="*//input[@name='url']")
    WebElement editWebsite;

    @FindBy(xpath="*//span[text()='Save']")
    WebElement saveBtn;

    @FindBy(xpath="*//div[@aria-label='Add avatar photo']")
    WebElement addProfilePictureButton;

    @FindBy(xpath="*//span[text()='Apply']")
    WebElement applyButton;

    public ProfilePage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void updateBioSection(String Text){
        try{
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(saveBtn.isEnabled());
            scrollToElement(driver, editBioSection);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            type(editBioSection,Text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLocationSection(String location){
        try{
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(saveBtn.isEnabled());
            scrollToElement(driver, editLocation);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            type(editLocation,location);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateWebsiteSection(String websiteURL){
        try{
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(saveBtn.isEnabled());
            scrollToElement(driver, editWebsite);
            highlightElement(driver,editWebsite);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            type(editWebsite,websiteURL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveProfile(){
        highlightElement(driver,saveBtn);
        click(saveBtn);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        assertTrue(editProfileBtn.isEnabled());
    }

    public void revalidateDataInProfilePage(String bio, String Loc, String URL){
        try{
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(saveBtn.isEnabled());
            scrollToElement(driver, editWebsite);
            highlightElement(driver,editBioSection);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(editBioSection.getText().trim().equalsIgnoreCase(bio),"Bio field is not correctly updated");
            highlightElement(driver,editLocation);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(editLocation.getAttribute("value").trim().equalsIgnoreCase(Loc),"Location field is not correctly updated");
            highlightElement(driver,editBioSection);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            assertTrue(editWebsite.getAttribute("value").trim().contains(URL), "Website section is not correctly updated");
            saveProfile();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void profilePictureUpload(){
        try{
            click(addProfilePictureButton);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            File desiredFile = getLastModified(System.getProperty("user.dir")+"/ProfilePicture");
            System.out.println(desiredFile.getAbsolutePath());
            robotFileUpload(desiredFile.getAbsolutePath());
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            highlightElement(driver, applyButton);
            click(applyButton);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            assertTrue(saveBtn.isDisplayed());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
