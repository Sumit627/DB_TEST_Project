package com.twitter.stepdefs;

import com.twitter.pages.HomePage;
import com.twitter.pages.LandingPage;
import com.twitter.pages.LoginPage;
import com.twitter.pages.ProfilePage;
import com.twitter.utils.Utils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class TwitterStepDefinition {

    public static WebDriver driver;
    public static Properties prop = Utils.propLoad();
    private LandingPage landingPageObj;
    private LoginPage loginPageObj;
    private HomePage homePageObj;
    private ProfilePage profilePageObj;

    @Before
    public void Setup(Scenario scenario) {
        System.out.println("-------------------Start of execution of Test Scenario : "+scenario.getName()+" ----------------------");
        String browserName = prop.getProperty("DefaultBrowser");
        if(browserName.equalsIgnoreCase("CHROME")){
            WebDriverManager.getInstance(DriverManagerType.CHROME).clearDriverCache().setup();
            driver = new ChromeDriver();
        }
        else if(browserName.equalsIgnoreCase("FIREFOX")){
            WebDriverManager.getInstance(DriverManagerType.FIREFOX).clearDriverCache().setup();
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.navigate().to(prop.getProperty("TwitterLandingPage"));
        driver.manage().timeouts().pageLoadTimeout(Utils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
    }

    @After
    public void teardown(Scenario scenario) throws IOException {
        System.out.println("--------------------End of execution of Test Scenario : "+scenario.getName()+" --------------------------");
        if(scenario.isFailed()){
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String currentDir = System.getProperty("user.dir");
            FileUtils.copyFile(scrFile, new File(currentDir + "\\screenshots\\"+"\\"+scenario.getName()+"\\" + System.currentTimeMillis() + ".png"));
        }
        driver.close();
        driver.quit();
    }

    @Given("User is navigate to Twitter Landing page")
    public void userIsNavigateToTwitterLandingPage() {
        landingPageObj= new LandingPage(driver);
        assertTrue(landingPageObj.validateLandingPageIsDisplayed());
        System.out.println("Landing page is displayed successfully!!!");
    }

    @Then("Click on Login button")
    public void clickOnLoginButton(){
        landingPageObj.clickOnLoginButton();
    }

    @When("Login page is displayed")
    public void loginPageIsDisplayed() {
        loginPageObj= new LoginPage(driver);
        assertTrue(loginPageObj.loginPageHeaderCheck());
        System.out.println("Login page is displayed successfully!!!");
    }

    @Given("Verify that Login button is disabled if user provide only Username details")
    public void verifyThatLoginButtonIsDisabledIfUserProvideOnlyUserName() throws InterruptedException {
        System.out.println("UserName field validate method!!!");
        loginPageObj.loginFieldValidation_OnlyUserName();
    }

    @Given("Verify that Login button is disabled if user provide only Password details")
    public void verifyThatLoginButtonIsDisabledIfUserProvideOnlyPassword() {
        loginPageObj.loginFieldValidation_OnlyPassword();
    }

    @Then("Verify that Login fails if user provide both Username and Password details incorrect")
    public void verify_that_login_fails_if_user_provide_both_username_and_password_details_incorrect() {
        loginPageObj.loginCredentialInput("INVALID_CREDENTIAL");
    }

    @Then("Verify error message {string}")
    public void verify_error_message(String errMsg) {
        loginPageObj.loginButtonClick();
        loginPageObj.errorMessageValidation(errMsg);
    }

    @Then("user provide both Username and Password details correctly")
    public void user_provide_both_username_and_password_details_correctly() {
        loginPageObj.loginCredentialInput("VALID_CREDENTIAL");
        loginPageObj.loginButtonClick();
    }

    @When("Verify that Twitter Homepage is displayed")
    public void verifyThatTwitterHomepageIsDisplayed() {
        homePageObj=new HomePage(driver);
        homePageObj.homePageTitleCheck();
    }


    @Given("User is logged to Twitter")
    public void user_is_logged_to_twitter() {
        userIsNavigateToTwitterLandingPage();
        clickOnLoginButton();
        loginPageIsDisplayed();
        user_provide_both_username_and_password_details_correctly();
        verifyThatTwitterHomepageIsDisplayed();
    }

    @When("User is navigated to Edit Profile page")
    public void user_is_navigated_to_edit_profile_page() {
        homePageObj.navigateToProfileSection();
        profilePageObj = new ProfilePage(driver);
    }

    @Then("Update the BIO field as {string}")
    public void update_the_bio_field_as(String Bio_text) {
        profilePageObj.updateBioSection(Bio_text);
    }

    @Then("Update the Location field as {string}")
    public void update_the_location_field_as(String loc) {
        profilePageObj.updateLocationSection(loc);
    }

    @Then("Update Website field as {string}")
    public void update_website_field_as(String websiteName) {
        profilePageObj.updateWebsiteSection(websiteName);
    }

    @Then("Save the Profile page")
    public void save_the_profile_page() {
        profilePageObj.saveProfile();
    }

    @Then("Revisit the profile page and revalidate the submitted value {string} {string} {string}")
    public void revisit_the_profile_page_and_revalidate_the_submitted_value(String BioText, String Location, String websiteURL) {
        user_is_navigated_to_edit_profile_page();
        profilePageObj.revalidateDataInProfilePage(BioText,Location,websiteURL);
    }

    @Then("Upload Profile Picture")
    public void uploadProfilePicture() {
        profilePageObj.profilePictureUpload();
    }

    @Then("Logout")
    public void logout() {
        homePageObj.logout();
    }
}
