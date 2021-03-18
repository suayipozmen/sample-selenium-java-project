package com.sahabt;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {

  private Logger logger = LoggerFactory.getLogger(getClass());

  public WebDriver webDriver;
  public WebDriverWait webDriverWait;
  private final int timeOut = 30;
  private final int sleepTime = 150;

  public BasePage(WebDriver webDriver) {
    this.webDriver = webDriver;
    webDriverWait = new WebDriverWait(webDriver, timeOut, sleepTime);
  }

  public List<WebElement> finds(By by) {
    logger.debug("Find Element {}", by.toString());
    List<WebElement> webElements = null;
    try {
      webElements = webDriver.findElements(by);
    } catch (Exception e) {
      Assert.fail("WebElements Not Found");
    }
    Assert.assertNotNull("WebElements Not Found", webElements);
    return webElements;
  }

  public WebElement find(By by) {
    logger.debug("Find Element {}", by.toString());
    WebElement webElement = null;
    try {
      webElement = webDriver.findElement(by);
    } catch (Exception e) {
      Assert.fail("WebElement Not Found");
    }
    Assert.assertNotNull("WebElement Not Found", webElement);
    return webElement;
  }


  public WebElement findByClickable(By by) {
    logger.debug("Find Element Clickable {}", by.toString());
    WebElement webElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(by));
    Assert.assertNotNull("WebElement Not Clickable", webElement);
    return webElement;
  }

  public List<WebElement> findsByClickable(By by) {
    logger.debug("Find Elements Clickable {}", by.toString());

    List<WebElement> webElements = webDriverWait.until(new ExpectedCondition<List<WebElement>>() {
      @Nullable
      @Override
      public List<WebElement> apply(@Nullable WebDriver webDriver) {
        List<WebElement> elements = ExpectedConditions.visibilityOfAllElementsLocatedBy(by)
            .apply(webDriver);
        try {
          for (WebElement element : elements) {
            if (element == null || !element.isEnabled()) {
              return null;
            }
          }
          return elements;
        } catch (StaleElementReferenceException e) {
          return null;
        }
      }
    });

    Assert.assertNotNull("WebElements Not Clickable", webElements);
    return webElements;
  }

  public WebElement findByVisible(By by) {
    logger.debug("Find Element Visible {}", by.toString());
    WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
    Assert.assertNotNull("WebElement Not Visible", webElement);
    return webElement;
  }

  public List<WebElement> findsByVisible(By by) {
    logger.debug("Find Elements Visible {}", by.toString());
    List<WebElement> webElements = webDriverWait
        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    Assert.assertNotNull("WebElement Not Visible", webElements);
    return webElements;
  }

  public WebElement findByPresence(By by) {
    logger.debug("Find Element Presence {}", by.toString());
    WebElement webElement = webDriverWait
        .until(ExpectedConditions.presenceOfElementLocated(by));
    Assert.assertNotNull("WebElement Not Presence", webElement);
    return webElement;
  }

  public List<WebElement> findsByPresence(By by) {
    logger.debug("Find Elements Presence {}", by.toString());
    List<WebElement> webElements = webDriverWait
        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    Assert.assertNotNull("WebElement Not Presence", webElements);
    return webElements;
  }


  public WebElement findByCss(String css) {
    return find(By.cssSelector(css));
  }

  public List<WebElement> findsByCss(String css) {
    return finds(By.cssSelector(css));
  }

  public WebElement findById(String id) {
    return find(By.id(id));
  }

  public List<WebElement> findsById(String id) {
    return finds(By.id(id));
  }

  public WebElement findByXpath(String xpath) {
    return find(By.xpath(xpath));
  }

  public List<WebElement> findsByXpath(String xpath) {
    return finds(By.xpath(xpath));
  }

  public BasePage checkElementVisible(WebElement webElement) {
    Assert.assertTrue("WebElement Not Visible", webElement.isDisplayed());
    return this;
  }

  public BasePage checkElementVisible(List<WebElement> webElements, int index) {
    return checkElementVisible(webElements.get(index));
  }

  public BasePage checkElementVisible(By by) {
    return checkElementVisible(find(by));
  }

  public BasePage checkElementVisible(By by, int index) {
    return checkElementVisible(finds(by).get(index));
  }

  public BasePage checkElementEnable(WebElement webElement) {
    Assert.assertTrue("WebElement Not Enable", webElement.isEnabled());
    return this;
  }

  public BasePage checkElementEnable(List<WebElement> webElements, int index) {
    return checkElementEnable(webElements.get(index));
  }

  public BasePage checkElementEnable(By by) {
    return checkElementEnable(find(by));
  }

  public BasePage checkElementEnable(By by, int index) {
    return checkElementEnable(finds(by).get(index));
  }

  public void click(WebElement webElement) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    webElement.click();
  }

  public void click(List<WebElement> webElements, int index) {
    click(webElements.get(index));
  }

  public void click(By by) {
    click(find(by));
  }

  public void click(By by, int index) {
    click(finds(by), index);
  }

  public void sendKeys(WebElement webElement, String text) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    webElement.sendKeys(text);
  }

  public void sendKeys(WebElement webElement, Keys keys) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    webElement.sendKeys(keys);
  }

  public void sendKeys(List<WebElement> webElements, int index, String text) {
    sendKeys(webElements.get(index), text);
  }

  public void sendKeys(By by, String text) {
    sendKeys(find(by), text);
  }

  public void sendKeys(By by, Keys keys) {
    sendKeys(find(by), keys);
  }

  public void sendKeys(By by, int index, String text) {
    sendKeys(finds(by), index, text);
  }

  public void sendKeyCode(WebElement webElement, Keys keyCode) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    webElement.sendKeys(keyCode);
  }

  public void sendKeyCode(By by, Keys keyCode) {
    sendKeyCode(find(by), keyCode);
  }

  public Select createSelect(WebElement webElement) {
    return new Select(webElement);
  }

  public Select createSelect(By by) {
    return new Select(find(by));
  }

  public void selectOptionsByVisibleText(WebElement webElement, String visibleText) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    createSelect(webElement)
        .selectByVisibleText(visibleText);
  }

  public void selectOptionsByVisibleText(List<WebElement> webElements, int index,
      String visibleText) {
    selectOptionsByVisibleText(webElements.get(index), visibleText);
  }

  public void selectOptionsByVisibleText(By by, String visibleText) {
    selectOptionsByVisibleText(find(by), visibleText);
  }

  public void selectOptionsByVisibleText(By by, int index, String visibleText) {
    selectOptionsByVisibleText(finds(by), index, visibleText);
  }

  public void selectOptionsByValue(WebElement webElement, String visibleText) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    createSelect(webElement)
        .selectByValue(visibleText);
  }

  public void selectOptionsByValue(By by, String visibleText) {
    selectOptionsByValue(find(by), visibleText);
  }

  public void selectOptionsByIndex(WebElement webElement, int index) {
    checkElementEnable(webElement)
        .checkElementVisible(webElement);
    createSelect(webElement)
        .selectByIndex(index);
  }

  public void selectOptionsByIndex(By by, int index) {
    selectOptionsByIndex(find(by), index);
  }


  public void scrollTo(WebElement webElement) {
    new Actions(webDriver)
        .moveToElement(webElement)
        .build()
        .perform();
  }

  public void scrollTo(By by) {
    scrollTo(find(by));
  }

  public String getText(WebElement webElement) {
    return webElement.getText();
  }

  public String getText(By by) {
    return find(by).getText();
  }

  public String getAttribute(WebElement webElement, String attributeName) {
    return webElement.getAttribute(attributeName);
  }

  public String getAttribute(By by, String attributeName) {
    return find(by).getAttribute(attributeName);
  }

  public String getCssValue(WebElement webElement, String cssName) {
    return webElement.getCssValue(cssName);
  }

  public String getCssValue(By by, String cssName) {
    return find(by).getCssValue(cssName);
  }

  public String getTagName(WebElement webElement) {
    return webElement.getTagName();
  }

  public String getTagName(By by) {
    return find(by).getTagName();
  }

  public String getUrl() {
    return webDriver.getCurrentUrl();
  }

  public String getPageSource() {
    return webDriver.getPageSource();
  }

  public String getPageTitle() {
    return webDriver.getTitle();
  }

  public Navigation getNavigation() {
    return webDriver.navigate();
  }

  public void refresh() {
    getNavigation().refresh();
  }

  public void forward() {
    getNavigation().forward();
  }

  public void back() {
    getNavigation().back();
  }

  public void goToUrl(String url) {
    getNavigation().to(url);
  }

  public void goToUrl(URL url) {
    getNavigation().to(url);
  }

  public void quit() {
    webDriver.quit();
  }

  public void close() {
    webDriver.close();
  }


  public void switchToMainContent(String frameId) {
    webDriver.switchTo().defaultContent();
  }

  public void switchToParentFrame() {
    webDriver.switchTo().parentFrame();
  }

  public void switchToFrame(By by) {
    webDriver.switchTo().frame(find(by));
  }

  public void switchToFrameByIndex(int index) {
    webDriver.switchTo().frame(index);
  }

  public void switchToFrameById(String frameId) {
    webDriver.switchTo().frame(frameId);
  }

  public void switchToFirstWindow(String windowName) {
    webDriver.switchTo().window(webDriver.getWindowHandles().stream().findFirst().orElse(""));
  }

  public void switchToWindow(String windowName) {
    webDriver.switchTo().window(windowName);
  }

  public int getCurrentWindowIndex() {
    Set<String> windowHandles = webDriver.getWindowHandles();
    int currentIndex = 0;
    for (String windowHandle : windowHandles) {
      if (windowHandle.equals(webDriver.getWindowHandle())) {
        break;
      }
      currentIndex++;
    }
    return currentIndex;
  }

  public void switchToNextTab() {
    switchToWindow(
        webDriver.getWindowHandles().stream().skip(getCurrentWindowIndex()).findFirst().orElse(""));
  }

  public void switchToPrevTab() {
    switchToWindow(
        webDriver.getWindowHandles().stream().skip(getCurrentWindowIndex() - 1).findFirst()
            .orElse(""));
  }

  public Alert switchToAlert() {
    Alert alert = webDriver.switchTo().alert();
    Assert.assertNotNull("Switchable Alert Not Found", alert);
    return alert;
  }

  public void acceptAlert() {
    switchToAlert().accept();
  }

  public void dismissAlert() {
    switchToAlert().dismiss();
  }

  public void sendTextToAlert(String keys) {
    switchToAlert().sendKeys(keys);
  }

  public String getTextAlert(String keys) {
    return switchToAlert().getText();
  }

  public void authenticateAlert(Credentials credentials) {
    switchToAlert().authenticateUsing(credentials);
  }

  public void authenticateAlert(String username, String password) {
    switchToAlert().authenticateUsing(new UserAndPassword(username, password));
  }

  public Options getOptions() {
    return webDriver.manage();
  }

  public Set<Cookie> getAllCookies() {
    return getOptions().getCookies();
  }

  public void getCookie(String name) {
    getOptions().getCookieNamed(name);
  }

  public void addCookie(Cookie cookie) {
    getOptions().addCookie(cookie);
  }

  public void deleteCookie(Cookie cookie) {
    getOptions().deleteCookie(cookie);
  }

  public void deleteCookie(String cookieName) {
    getOptions().deleteCookieNamed(cookieName);
  }

  public void deleteAllCookies() {
    getOptions().deleteAllCookies();
  }

  public JavascriptExecutor getJavaScriptExecutor() {
    return ((JavascriptExecutor) webDriver);
  }

  public void waitPageLoadComplete() {
    try {
      webDriverWait.until(
          driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
              .toString()
              .equals("complete"));
    } catch (Throwable error) {
      error.printStackTrace();
    }
  }

  public void waitForAngularLoad() {
    Boolean existAngular = (Boolean) getJavaScriptExecutor()
        .executeScript("return (typeof(angular) != 'undefined')");
    if (existAngular) {
      try {
        webDriverWait.until(driver -> ((Boolean) ((JavascriptExecutor) driver).executeScript(
            "return angular.element(document).injector().get('$http').pendingRequests.length === 0")));
      } catch (Throwable error) {
        error.printStackTrace();
      }
    }
  }

  public void waitJQueryComplete() {
    Boolean existJquery = (Boolean) getJavaScriptExecutor()
        .executeScript("return (typeof(jQuery) != 'undefined')");
    if (existJquery) {
      try {
        webDriverWait.until(driver -> (Boolean) ((JavascriptExecutor) driver)
            .executeScript("return jQuery.active == 0"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void waitAll() {
    waitPageLoadComplete();
    waitForAngularLoad();
    waitJQueryComplete();
  }

  public void waitSeconds(int seconds) {
    waitMillis(seconds * 1000);
  }

  public void waitMillis(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
