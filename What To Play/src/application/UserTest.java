package application;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	User testUser = new User("UsernameRyan","Ryan","Bulcher","1234","rbulcher2@gmail.com","21");

	@Test
	public void testName() {
		
		assertTrue(testUser.getFirstName() == "Ryan");
		assertFalse(testUser.getFirstName() == null);
		
		
	}
	@Test
	public void testAge() {
		
		assertTrue(testUser.getAge() == "21");
		assertFalse(testUser.getFirstName() == null);
		
		
	}
	@Test
	public void testEmail() {
		
		assertTrue(testUser.getEmail() == "rbulcher2@gmail.com");
		assertFalse(!(testUser.getEmail().contains("@") || testUser.getEmail().contains(".")));
		assertFalse(testUser.getFirstName() == null);
		
		
	}
	@Test
	public void testPassword() {
		
		assertTrue(testUser.getPassword() == "1234");
		assertFalse(testUser.getFirstName() == null);
		
		
	}

}
