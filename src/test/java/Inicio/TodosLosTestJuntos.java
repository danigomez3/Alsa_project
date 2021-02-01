package Inicio;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;



public class TodosLosTestJuntos {
	 
	  public static WebDriver driverC;
	  public static WebDriver driverF;
	
	String navegador;
	private String baseURL;
	
	By cookiesLocator = By.id("didomi-notice-agree-button");
	By destinoLocator = By.xpath("//span[contains(.,'Destinos y rutas')]");
	By origen = By.id("_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_originStationNameId");
	By destino = By.id("_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_destinationStationNameId");
	//Para poder escribir dentro de un casillero es fundamenta localizar un input "text", de lo contrario no te dejara escribir ahi
	By fechaSal = By.xpath("//button[@id='_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853_departureDate_bt']/span");
	By fechaLleg = By.xpath("//button[@id='_JourneySearchPortlet_WAR_Alsaportlet_INSTANCE_JourneySearch_21656853__returnDate_bt']/span");


	@BeforeSuite
	public void cargaPropiedades() {
		iniciarSesionPreguntandoDatos();
	
	}
	
	@BeforeMethod
	public void cargaPaginaInicial() {
		if (driverF != null) {
			driverF.get(baseURL);      //este metodo es necesario para que una vez que finalice cada test, vuelva
		}                             //a la pagina de inicio y pueda volver a ejecutar el siguiente; de lo contrario no funcionara
		if (driverC != null) {
			driverC.get(baseURL);
		}
	}
	
	@Test
	public void viajes_Alsa() {
		viajes_Alsa(driverC, driverF);
        
	}	
	
	@Test
	public void destinosyRutas() {
		destinosyRutas(driverC, driverF);
	}
		
	@AfterSuite
	void tearDoown() {
		if (driverF != null) {
			driverF.quit();
		}
		if (driverC != null) {
			driverC.quit();
		}
	}
	
	private void preguntarNavegadorAUtilizar() {
		String[] navegadores = { "Chrome", "Firefox", "Ambos" };
		Object selected = JOptionPane.showInputDialog(null, "¿Qué navegador desea usar?", "Elija navegador",
				JOptionPane.DEFAULT_OPTION, null, navegadores, "Chrome");
		if (selected != null) {
			navegador = selected.toString();
		} else {
			System.out.println("Cancelado por el usuario");
			System.exit(1);
		}

		System.out.println(navegador);
	}
	
	public void iniciarSesionPreguntandoDatos() {
		preguntarNavegadorAUtilizar();

		if (navegador.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driverC = new ChromeDriver();
			driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverC.manage().window().maximize();
			driverC.get("https://www.alsa.es/");
		} else if (navegador.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driverF = new FirefoxDriver();
			driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverF.manage().window().maximize();
			driverF.get("https://www.alsa.es/");
		} else {
			System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
			driverC = new ChromeDriver();
			driverC.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverC.manage().window().maximize();
			driverC.get("https://www.alsa.es/");
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driverF = new FirefoxDriver();
			driverF.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driverF.manage().window().maximize();
			driverF.get("https://www.alsa.es/");
		}
		baseURL = "https://www.alsa.es/";
		
		if(driverF!=null) {
			driverF.findElement(By.id("didomi-notice-agree-button")).click();
		}
		
		if(driverC!=null) {
			driverC.findElement(By.id("didomi-notice-agree-button")).click();
		}
		
		
	}
	
	public void destinosyRutas(WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				//SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(destinoLocator).click();
				currentDriver.findElement(By.xpath("//a[contains(text(),'Aeropuertos a los que llegamos')]")).click();
				
				//Thread.sleep(1000);
				currentDriver.findElement(By.xpath("//button[@id='_originStationNameId_']/span")).click();
				currentDriver.findElement(origen).sendKeys("Madrid Interc. Moncloa");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentDriver.findElement(origen).sendKeys(Keys.ARROW_DOWN);
				currentDriver.findElement(origen).sendKeys(Keys.ARROW_DOWN);
				currentDriver.findElement(origen).sendKeys(Keys.ENTER);

				currentDriver.findElement(By.id("destinationId")).click();
				currentDriver.findElement(destino).sendKeys("Aeropuerto Valladolid");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentDriver.findElement(destino).sendKeys(Keys.ARROW_DOWN);
				currentDriver.findElement(destino).sendKeys(Keys.ARROW_DOWN);
				currentDriver.findElement(destino).sendKeys(Keys.ENTER);
				
				currentDriver.findElement(fechaSal).click();
				currentDriver.findElement(By.xpath("//a[contains(text(),'10')]")).click();

				currentDriver.findElement(fechaLleg).click();
				currentDriver.findElement(By.xpath("//a[contains(text(),'12')]")).click();
				
					//Mucho cuidado con los dias; en el anterior ejercicio tenia dias de partida 29 y 30 y al ejecutarlos
					//en el mes de febrero que solo tiene 28 dias daba error.
				
				currentDriver.findElement(By.id("journeySearchFormButtonjs")).click();
				Assert.assertTrue(currentDriver.findElement(By.xpath("//img[@alt='Logo Alsa, ir a inicio']")).isDisplayed());
			

			}
		}
	}
	
	public void viajes_Alsa (WebDriver... drivers) {
		for (int i = 0; i < 2; i++) {
			WebDriver currentDriver = drivers[i];
			if (currentDriver != null) {
				//SinAutenticar.saberSiEsChromeOFirefox(i);
				currentDriver.findElement(By.xpath("//span[contains(.,'Alsa Viajes')]")).click();
				String handleVentana1 = currentDriver.getWindowHandle();
		        Set<String> listadoHanleVentanas = currentDriver.getWindowHandles();
		        listadoHanleVentanas.remove(handleVentana1);
		        String handleVentana2 = listadoHanleVentanas.iterator().next();
		        currentDriver.switchTo().window(handleVentana2);
		        Assert.assertEquals(handleVentana2, currentDriver.getWindowHandle(), "No estás en la pestaña adecuada");
		        Assert.assertTrue(currentDriver.findElement(By.cssSelector(".brand__logo")).isDisplayed());
		        currentDriver.close();
		        currentDriver.switchTo().window(handleVentana1);		
		
			}

		}
	}
}
