package com.twitter.utils;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.sun.glass.events.KeyEvent;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static long IMPLICIT_WAIT = 10;
    public static long PAGE_LOAD_TIMEOUT = 20;
    public static Properties prop;

    public boolean isElementPrsent(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public void type(WebElement element, String str) {
        element.click();
        element.clear();
        element.sendKeys(str.toString());

    }

    public void highlightElement(WebDriver driver, WebElement element)
    {
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'background: LightGreen; border: 2px solid yellow;' )", element);
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
            js.executeScript("arguments[0].setAttribute('style', 'border: 2px white')", element);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void selectElementFromDropDown(WebDriver driver, WebElement element, String visibleText) {

        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(visibleText);

    }

    public void click(WebElement element) {
        element.click();
    }

    public static Properties propLoad(){
        prop = new Properties();
        try{
            prop.load(new FileReader(System.getProperty("user.dir")+"/src/test/java/com/twitter/config/Config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public void scrollToElement(WebDriver driver,WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);",element);

    }

    public static File getLastModified(String directoryFilePath)
    {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }

    public static void robotFileUpload(String absFileLocation) throws AWTException, InterruptedException {
        Thread.sleep(5000);
        Robot rb = new Robot();
        // copying File path to Clipboard
        StringSelection str = new StringSelection(absFileLocation);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
        // press Contol+V for pasting
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // release Contol+V for pasting
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        // for pressing and releasing Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
    }



}
