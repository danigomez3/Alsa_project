package Inicio;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlsaViajes {
	
	private WebDriver driver;


	@BeforeClass
	public void SetUp() {
	System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver/chromedriver.exe");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get("https://www.alsa.es/");
	driver.findElement(By.id("didomi-notice-agree-button")).click();
	
	}
	
	@Test
	public void viajes_alsa() {
		driver.findElement(By.xpath("//span[contains(.,'Alsa Viajes')]")).click();
		String handleVentana1 = driver.getWindowHandle();
        Set<String> listadoHanleVentanas = driver.getWindowHandles();
        listadoHanleVentanas.remove(handleVentana1);
        String handleVentana2 = listadoHanleVentanas.iterator().next();
        driver.switchTo().window(handleVentana2);
        Assert.assertEquals(handleVentana2, driver.getWindowHandle(), "No estás en la pestaña adecuada");
        Assert.assertTrue(driver.findElement(By.cssSelector(".brand__logo")).isDisplayed());
        driver.close();
        driver.switchTo().window(handleVentana1);
        
		
	}	
	
}
