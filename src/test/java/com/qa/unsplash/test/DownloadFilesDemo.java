package com.qa.unsplash.test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DownloadFilesDemo {

	WebDriver driver;
	File folder;

	@BeforeMethod
	public void setUp() throws IOException {

		folder = new File("/Users/alikozan/Documents/Picture");
		folder.mkdir();
		folder.createNewFile();

		System.setProperty("webdriver.chrome.driver", "/Users/alikozan/Documents/Selenium/libs/chromedriver");

		String downloadFilepath = folder.getAbsolutePath().toString();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", "/Users/alikozan/Documents/Picture");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(cap);

	}

	@Test
	public void downloadFilesDemo() throws InterruptedException {
		driver.get("https://unsplash.com/");
		driver.findElement(By.xpath("//a[text()='Nature']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		for (int i = 1; i < 10; i++) {
			WebElement img = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='_1ZjfQ _2T3hc Xl8Lr'][1]//figure[" + i + "]//div[@class='IEpfq']/img")));
			img.click();
			WebElement infoButton = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Info']")));

			infoButton.click();
			WebElement dimentions = driver.findElement(
					By.xpath("//div[@class='_1XYV- _3aBA9 Rs1Ko']//dt[text()='Dimensions']//parent::div//dd"));

			String dimentionsText = dimentions.getText();

			String[] widthAndLength = dimentionsText.split(" × ");
			int width = Integer.parseInt(widthAndLength[0]);
			int length = Integer.parseInt(widthAndLength[1]);
			if (width > length) {
				WebElement closebuttonInfo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ReactModalPortal'][2]//button[@class='_1NHYN DWLeW Ddtb4']")));
				closebuttonInfo.click();
				WebElement dowloadButton = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Download free']")));
				dowloadButton.click();
				Thread.sleep(5000);
				WebElement closebuttonDownload = driver.findElement(By.xpath("//button[@class='_1NHYN DWLeW Ddtb4']"));
				closebuttonDownload.click();

			} else {
				for (int j = 2; j > 0; j--) {
					WebElement closebuttonInfo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ReactModalPortal'][" + j + "]//button[@class='_1NHYN DWLeW Ddtb4']")));
					closebuttonInfo.click();
				}
			}

		}

	}

	@AfterMethod
	public void tearUp() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();

	}

}
