package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestPayementCas1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPayementCas1() throws Exception {
    driver.get(baseUrl + "/SecuriteWeb/signUpCheck.jsp");
    driver.findElement(By.linkText("go back")).click();
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.id("amount")).clear();
    driver.findElement(By.id("amount")).sendKeys("50");
    driver.findElement(By.id("nomberCard")).clear();
    driver.findElement(By.id("nomberCard")).sendKeys("4970123498746541");
    driver.findElement(By.id("inputPassword")).clear();
    driver.findElement(By.id("inputPassword")).sendKeys("001");
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("M. DUPONT");
    driver.findElement(By.id("date")).clear();
    driver.findElement(By.id("date")).sendKeys("2018-05-01");
    driver.findElement(By.id("amount")).clear();
    driver.findElement(By.id("amount")).sendKeys("500");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("go back")).click();
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.id("amount")).clear();
    driver.findElement(By.id("amount")).sendKeys("30");
    driver.findElement(By.id("inputPassword")).clear();
    driver.findElement(By.id("inputPassword")).sendKeys("001");
    driver.findElement(By.id("nomberCard")).clear();
    driver.findElement(By.id("nomberCard")).sendKeys("4970123498746541");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
