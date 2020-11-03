package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void testName() {
		Game randomGame = new Game();
		assertTrue(randomGame.getName() != null);

		assertFalse(randomGame.getName() == null);
	}
	@Test
	public void testPrice() {
		Game randomGame = new Game();
		assertTrue(randomGame.getPrice() == "");

		assertFalse(randomGame.getPrice() == null);
		randomGame.setPrice("0.00");
		assertTrue(randomGame.getPrice() != null);
	}
	@Test
	public void testA1() {
		Game randomGame = new Game();
		assertTrue(randomGame.getA1() == "");

		assertFalse(randomGame.getA1() == null);
		randomGame.setA1("false");
	
		assertTrue(randomGame.getA1().equals("true") || randomGame.getA1().equals("false"));
	}
	@Test
	public void testA12() {
		Game randomGame = new Game();
		assertTrue(randomGame.getA12() == "");

		assertFalse(randomGame.getA12() == null);
		randomGame.setA12("true");
	
		assertTrue(randomGame.getA12().equals("true") || randomGame.getA12().equals("false"));
		
			
	}
	
	@Test
	public void testDescription() {
		Game randomGame = new Game();
		assertTrue(randomGame.getDescription() == "");

		assertFalse(randomGame.getDescription() == null);
		randomGame.setDescription("Description");
	
		assertTrue(randomGame.getDescription().equals("Description"));
		
			
	}
	
	
	

}
