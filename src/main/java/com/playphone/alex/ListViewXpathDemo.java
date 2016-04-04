/**
 * Copyright 2016 alewang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.playphone.alex;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <p>This class demonstrates whether or not WebElement objects become "invalidated" in a List
 * View if they are scrolled off screen.</p>
 */
public class ListViewXpathDemo {
	private AndroidDriver<AndroidElement> driver;

	public static void main(String[] args) throws MalformedURLException {
		ListViewXpathDemo demo = new ListViewXpathDemo();
		demo.runDemo();
	}

	public void runDemo() throws MalformedURLException {
		setupAppium();
		WebDriverWait localWait = new WebDriverWait(driver, 5);
		AndroidElement myListView = (AndroidElement) localWait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("list view")));

		List<MobileElement> listChildren = myListView.findElementsByClassName("android.widget.TextView");
		AndroidElement firstChild = (AndroidElement) listChildren.get(0);
		System.out.println("Scrolling first element off screen");
		Point upperLeft = firstChild.getLocation();
		Dimension childSize = firstChild.getSize();
		System.out.println("First child dimensions: Point(" + upperLeft.toString() + "), Dim(" + childSize.toString() + ")");
		System.out.println("First child text: " + firstChild.getText());
		int x = childSize.getWidth() / 2;

		Dimension screenSize = driver.manage().window().getSize();

		driver.swipe(x, screenSize.getHeight() - 1, x, upperLeft.getY(), 3000);

		System.out.println("Now printing the values of the first child again to see what the values are:");
		upperLeft = firstChild.getLocation();
		childSize = firstChild.getSize();
		System.out.println("First child dimensions: Point(" + upperLeft.toString() + "), Dim(" + childSize.toString() + ")");
		System.out.println("First child text: " + firstChild.getText());

		cleanupAppium();
	}

	public void setupAppium() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		File app = new File("app-list-view.apk");
		caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, Platform.ANDROID);
		driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
	}

	public void cleanupAppium() {
		if(driver != null) {
			driver.quit();
		}
	}
}
