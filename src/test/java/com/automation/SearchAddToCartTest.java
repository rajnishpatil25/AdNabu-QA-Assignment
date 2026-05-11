package com.automation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SearchAddToCartTest {

    @Test
    public void addProductToCartTest() {

        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            // Open website
            driver.get("https://adnabu-store-assignment1.myshopify.com");

            // Enter password
            driver.findElement(By.id("password")).sendKeys("AdNabuQA");

            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Wait for search icon
            WebElement searchIcon = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("summary[aria-label='Search']"))
            );

            // Click on search icon
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", searchIcon);

            // Search product
            WebElement searchBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("Search-In-Modal"))
            );

            searchBox.sendKeys("The Multi-location Snowboard");
            searchBox.sendKeys(Keys.ENTER);

            // Open product
            WebElement product = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.linkText("The Multi-location Snowboard"))
            );

            product.click();

            // Click add to cart
            WebElement addToCartBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.name("add"))
            );

            addToCartBtn.click();

            // Verify cart item
            WebElement cartItem = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.className("cart-item"))
            );

            Assert.assertTrue(cartItem.isDisplayed());

            System.out.println("Product added to cart successfully");

        } catch (Exception e) {

            System.out.println("Test Failed : " + e.getMessage());

        } finally {

            driver.quit();
        }
    }
}