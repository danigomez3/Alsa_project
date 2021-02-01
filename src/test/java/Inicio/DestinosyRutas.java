package Inicio;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Test
public class DestinosyRutas {

	private WebDriver driver;

	By cookiesLocator = By.id("didomi-notice-agree-button");
	By destinoLocator = By.xpath("//span[contains(.,'Destinos y rutas')]");
	By origen = By.id("_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_originStationNameId");
	By destino = By.id("_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_destinationStationNameId");
	//Para poder escribir dentro de un casillero es fundamenta localizar un input "text", de lo contrario no te dejara escribir ahi
	By fechaSal = By.xpath("//button[@id='_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_departureDate_bt']/span");
	By fechaLleg = By.xpath("//button[@id='_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853__returnDate_bt']/span");

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
		driver.findElement(cookiesLocator).click();
	}

	@Test
	public void destinosyRutas() throws InterruptedException {
		driver.findElement(destinoLocator).click();
		driver.findElement(By.xpath("//a[contains(text(),'Aeropuertos a los que llegamos')]")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@id='_originStationNameId_']/span")).click();
		driver.findElement(origen).sendKeys("Madrid Interc. Moncloa");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(origen).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(origen).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(origen).sendKeys(Keys.ENTER);

		driver.findElement(By.id("destinationId")).click();
		driver.findElement(destino).sendKeys("Aeropuerto Valladolid");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(destino).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(destino).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(destino).sendKeys(Keys.ENTER);

		driver.findElement(fechaSal).click();
		driver.findElement(By.xpath("//a[contains(text(),'10')]")).click();
		//cuidado con este desplegable, si señalas 10 como dia y estamos a 12, no te dejara selleccionar ese dia por haber pasado ya
		driver.findElement(fechaLleg).click();
		driver.findElement(By.xpath("//a[contains(text(),'12')]")).click();

		driver.findElement(By.id("journeySearchFormButtonjs")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//img[@alt='Logo Alsa, ir a inicio']")).isDisplayed());
		driver.quit();

	}
}
