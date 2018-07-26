import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class PageLoadSuspension
{
	public static void main(String[] args) throws MalformedURLException
	{
		URL url = getSauceURL();
		DesiredCapabilities capabilities = useIPadPro12Inch();
		AppiumDriver<WebElement> driver = new IOSDriver<>(url, capabilities);
		WebDriverWait wait = new WebDriverWait(driver, 60);

		driver.get("https://www-stest.allstate.com/anon/bumpertobumper/default.aspx");

		printContextInfo(driver);

		wait.until(ExpectedConditions.titleIs("Allstate Bumper-to-Bumper Basics | Auto Insurance Guide"));

		waitForPageToLoad(driver);
		printContextInfo(driver);

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privacy Statement"))).click();

		waitForPageToLoad(driver);
		printContextInfo(driver);
	}

	public static void waitForPageToLoad(AppiumDriver<WebElement> driver)
	{
		for(int i=0; i<10; i++)
		{
			Object state = driver.executeScript("return document.readyState;");
			System.out.println("state: " + state);

			if (state.equals("complete")) { return; }
			sleep(5);
		}

		driver.executeScript("return window.stop()");
		sleep(5);
	}

	///// HELPERS /////

	public static void printContextInfo(AppiumDriver<WebElement> driver)
	{
		System.out.println("------");
		System.out.println("CONTEXT HANDLES: " + driver.getContextHandles());
		System.out.println("CURRENT CONTEXT: " + driver.getContext());
		System.out.println("WINDOW HANDLES: " + driver.getWindowHandle());
		System.out.println("CURRENT WINDOW: " + driver.getWindowHandle());
		System.out.println("TITLE: " + driver.getTitle());
		System.out.println("URL: " + driver.getCurrentUrl());
		System.out.println("------");
	}

	public static URL getSauceURL() throws MalformedURLException
	{
		String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
		String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
		String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com/wd/hub"
				.replace("SAUCE_USERNAME", SAUCE_USERNAME)
				.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);

		return new URL(SAUCE_URL);
	}

	public static DesiredCapabilities useIPadPro9Inch()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPad Pro (9.7 inch) Simulator");
		return capabilities;
	}

	public static DesiredCapabilities useIPadPro12Inch()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPad Pro (12.9 inch) (2nd generation) Simulator");
		return capabilities;
	}

	public static DesiredCapabilities useIphone7()
	{
		DesiredCapabilities capabilities = getIOSSimulator();
		capabilities.setCapability("deviceName", "iPhone 7 Simulator");
		return capabilities;
	}

	public static DesiredCapabilities getIOSSimulator()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "11.3");
		capabilities.setCapability("deviceName", "iPhone Simulator");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("name", getName());
		return capabilities;
	}

	public static String getName()
	{
		StackTraceElement stack = Thread.currentThread().getStackTrace()[4];
		return stack.getClassName() + " " + stack.getMethodName();
	}

	public static void sleep(int seconds)
	{
		try
		{
			Thread.sleep(1000 * seconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}