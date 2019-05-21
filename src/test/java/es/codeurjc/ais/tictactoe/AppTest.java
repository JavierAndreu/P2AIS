package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.*;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.junit.*;
import es.codeurjc.ais.tictactoe.WebApp;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class AppTest {
	
	WebDriver d0,d1;
	WebElement [] tablero0,tablero1;
	

	
	@BeforeClass
	public static void setUpBeforeClass() {
		ChromeDriverManager.chromedriver().setup();
		WebApp.start();
	}
	

	@AfterClass
	public static void tearDownAfterClass() {
		WebApp.stop();
	}
	
	
	@Before
	public void setUp() throws InterruptedException {
		d0 = new ChromeDriver();
		d1 = new ChromeDriver();
		d0.get("http://localhost:8080/");
		d1.get("http://localhost:8080/");
		d0.findElement(By.id("nickname")).sendKeys("Javier");
		d0.findElement(By.id("startBtn")).click();
		d1.findElement(By.id("nickname")).sendKeys("Diego");
		d1.findElement(By.id("startBtn")).click();
		tablero0 = new WebElement[9];
		tablero1 = new WebElement[9];
		Thread.sleep(500);
		for (int i=0; i<9; i++) {
			tablero0[i] = d0.findElement(By.id("cell-"+i));
			tablero1[i] = d1.findElement(By.id("cell-"+i));
		}
		
	}
	
	
	@After
	public void tearDown() {
		d0.quit();
		d1.quit();
	}
	

	@Test
	public void testJugador0Win() throws InterruptedException {
		tablero0[0].click();
		tablero1[1].click();
		tablero0[4].click();
		tablero1[8].click();
		tablero0[6].click();
		tablero1[2].click();
		tablero0[3].click();
		
		Thread.sleep(500);
		assertEquals( d0.switchTo().alert().getText(),"Javier wins! Diego looses.");
		assertEquals( d1.switchTo().alert().getText(),"Javier wins! Diego looses.");
	}
	
	
	@Test
	public void testJugador0Lose() throws InterruptedException {
		tablero0[0].click();
		tablero1[2].click();
		tablero0[3].click();
		tablero1[6].click();
		tablero0[8].click();
		tablero1[4].click();
		
		Thread.sleep(500);
		assertEquals( d0.switchTo().alert().getText(),"Diego wins! Javier looses.");
		assertEquals( d1.switchTo().alert().getText(),"Diego wins! Javier looses.");
	}
	
	@Test
	public void testDraw() throws InterruptedException {
		tablero0[0].click();
		tablero1[1].click();
		tablero0[3].click();
		tablero1[6].click();
		tablero0[2].click();
		tablero1[4].click();
		tablero0[7].click();
		tablero1[5].click();
		tablero0[8].click();
		
		Thread.sleep(500);
		assertEquals( d0.switchTo().alert().getText(),"Draw!");
		assertEquals( d1.switchTo().alert().getText(),"Draw!");
	}

}
