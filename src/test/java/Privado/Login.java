package Privado;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {

	
	private WebDriver driver;
	
	By dniLocator = By.id("_BusplusLoginPortlet_WAR_Alsaportlet_INSTANCE_utilityMiscLogin_busplusDocumentNumber");
	By password = By.id("_BusplusLoginPortlet_WAR_Alsaportlet_INSTANCE_utilityMiscLogin_busplusPassword");
	

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	@BeforeMethod
	public void cargarPaginaInicio() {
		driver.get("https://www.alsa.es/");
		driver.findElement(By.id("didomi-notice-agree-button")).click();
	}
	@Test
	public void LogIn() throws InterruptedException {
		driver.findElement(By.id("loginItem")).click();
		driver.findElement(dniLocator).sendKeys("74719327D");
		driver.findElement(password).sendKeys("alcazar1978");
		driver.findElement(By.xpath("//button[contains(.,'Entrar')]")).click();
}
	@Test
	public void logOut() throws InterruptedException {
		driver.findElement(By.id("loginItem")).click();
		driver.findElement(dniLocator).sendKeys("74719327D");
		driver.findElement(password).sendKeys("alcazar1978");
		driver.findElement(By.xpath("//button[contains(.,'Entrar')]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//strong[contains(.,'DANIEL')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Cerrar sesión')]")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//img[@alt='Logo Alsa, ir a inicio']")).isDisplayed());
		
	}
}
