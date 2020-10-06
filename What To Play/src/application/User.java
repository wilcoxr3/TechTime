package application;

import java.util.HashMap;

public class User {
	
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String age;
	private HashMap<String, Game> favorites = new HashMap<>();
	
	public User(String username, String firstName, String lastName, String passWord, String email, String age) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = passWord;
		this.email = email;
		this.age = age;
		
	}
	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HashMap<String, Game> getFavorites() {
		return favorites;
	}

	public void setFavorites(HashMap<String, Game> favorites) {
		this.favorites = favorites;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	

}
