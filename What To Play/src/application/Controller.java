package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller {

	@FXML
	private PasswordField password;

	@FXML
	private Button loginBtn;

	@FXML
	private TextField username;

	@FXML
	private Button createAccButton;

	@FXML
	private Button backToHome;

	@FXML
	private Button buttonToQPage1;

	@FXML
	private TextField newUserFirstName;

	@FXML
	private TextField newUserEmail;

	@FXML
	private TextField newUserAge;

	@FXML
	private TextField newUserLastName;

	@FXML
	private PasswordField newUserPassword;

	@FXML
	private Button backToQPage0;

	@FXML
	private ToggleGroup sim;

	@FXML
	private ToggleGroup fps;

	@FXML
	private ToggleGroup scary;

	@FXML
	private ToggleGroup experience;

	@FXML
	private TextField newUserUsername;

	@FXML
	private ToggleGroup racing;

	@FXML
	private ToggleGroup survival;

	@FXML
	private ToggleGroup party;

	@FXML
	private ToggleGroup multiplayer;

	@FXML
	private ToggleGroup challenge;

	@FXML
	private ToggleGroup pay;

	@FXML
	private ToggleGroup time;

	@FXML
	private ToggleGroup graphics;

	@FXML
	private TableView<Game> tableView = new TableView<>();
	@FXML
	private TableView<Game> tableViewFavorites = new TableView<>();
	@FXML
	private TableColumn<String, String> gameTitle = new TableColumn<>();

	@FXML
	private TableColumn<String, String> gameTitleFavorites = new TableColumn<>();

	@FXML
	private TableColumn<String, String> gamePrice = new TableColumn<>();

	@FXML
	private TableColumn<String, String> gamePriceFavorites = new TableColumn<>();

	private final ObservableList<Game> dataList = FXCollections.observableArrayList();

	private final ObservableList<Game> dataListFavorites = FXCollections.observableArrayList();

	private ArrayList<String> titles = new ArrayList<>();

	@FXML
	private TextField filterField = new TextField();

	private Boolean a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12; // player atributes

	private HashMap<String, User> users = new HashMap<>();
	private HashMap<String, String> userAndPass = new HashMap<>();
	private HashMap<String, Game> loadedGames = new HashMap<>();

	@FXML
	void displayHome(ActionEvent event) throws IOException {
		if (!username.getText().isEmpty()) {
			if (!password.getText().isEmpty()) {
				// check user and password
				if (userCleared(username.getText(), password.getText())) {
					// set the current user to get data for later on

					Main.currentUser = users.get(username.getText());

					if (username.getText().equals("admin")) {
						Parent loginParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
						Scene home = new Scene(loginParent);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(home);
						window.show();

					} else {

						Parent loginParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
						Scene home = new Scene(loginParent);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(home);
						window.show();
					}

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Username or Password does not match");
					alert.setContentText("Please try again.");

					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Invalid Password");

				alert.showAndWait();
			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Invalid Username");

			alert.showAndWait();
		}

		// reset text fields
		username.setText("");
		password.setText("");

	}

	@FXML
	void displayHomeByPass(ActionEvent event) throws IOException {
		Parent loginParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
		Scene home = new Scene(loginParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(home);
		window.show();

	}

	private void loadUsers() {
		try {
			File myObj = new File("userDatabase.txt");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] userData = data.split(",");
				User temp = new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5]);
				users.put(userData[0], temp);
				userAndPass.put(userData[0], userData[3]);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error with userDataBase");
			e.printStackTrace();
		}
		loadUsersFavorites();
	}

	private void loadUsersFavorites() {
		try {
			File myObj = new File("userFavoriteDatabase.txt");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] userData = data.split(",");
				for (int i = 1; i < userData.length; i++)
					users.get(userData[0]).getFavorites().put(userData[i], loadedGames.get(userData[i]));
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error with userFavoriteDataBase");
			e.printStackTrace();
		}
	}

	private boolean userCleared(String username, String password) {
		loadUsers();
		// verify matching user and password
		if (userAndPass.containsKey(username) && userAndPass.get(username).equals(password))
			return true;

		return false;
	}

	@FXML
	void displayQPage0(ActionEvent event) throws IOException {

		Parent createAccScene = FXMLLoader.load(getClass().getResource("QPage0.fxml"));
		Scene createAccount = new Scene(createAccScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(createAccount);
		window.show();

	}

	@FXML
	void displayLogin(ActionEvent event) throws IOException {
		Parent createAccScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene createAccount = new Scene(createAccScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(createAccount);
		window.show();

	}

	@FXML
	void backToQPage1(ActionEvent event) throws IOException {

		Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage1.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	void displayQPage1(ActionEvent event) throws IOException {
		if (!newUserFirstName.getText().isEmpty()) {
			if (!newUserLastName.getText().isEmpty()) {
				if (!newUserEmail.getText().isEmpty() && newUserEmail.getText().contains("@")
						&& newUserEmail.getText().contains(".")) {
					if (newUserAge.getText().length() >= 2 && !newUserAge.getText().contains("[a-zA-Z+")) {
						User newUser = new User();
						newUser.setFirstName(newUserFirstName.getText());
						newUser.setLastName(newUserLastName.getText());
						newUser.setEmail(newUserEmail.getText());
						newUser.setAge(newUserAge.getText());
						Main.currentUser = newUser;

						Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage1.fxml"));
						Scene scene = new Scene(CreateScene);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(scene);
						window.show();

					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Enter valid Age");
						alert.setContentText("Please try again.");

						alert.showAndWait();

					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid Email");
					alert.setContentText("Please try again.");

					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Must fill in Last Name");
				alert.setContentText("Please try again.");

				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Must fill in First Name");
			alert.setContentText("Please try again.");

			alert.showAndWait();
		}

	}

	@FXML
	private Button buttonToQPage2;

	@FXML
	void displayQPage2(ActionEvent event) throws IOException {
		loadUsers();

		if (!users.containsKey(newUserUsername.getText())) {
			if (!newUserPassword.getText().isEmpty()) {

				Main.currentUser.setUsername(newUserUsername.getText());
				Main.currentUser.setPassword(newUserPassword.getText());

				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("userDataBase.txt"), true));
				writer.newLine();
				writer.write(Main.currentUser.getUsername() + "," + Main.currentUser.getFirstName() + ","
						+ Main.currentUser.getLastName() + "," + Main.currentUser.getPassword() + ","
						+ Main.currentUser.getEmail() + "," + Main.currentUser.getAge());
				writer.close();

				Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage2.fxml"));
				Scene scene = new Scene(CreateScene);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(scene);
				window.show();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Must create a password");
				alert.setContentText("Please try again.");

				alert.showAndWait();

			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Username already taken!");
			alert.setContentText("Please try again.");

			alert.showAndWait();
		}

	}

	@FXML
	void backToQPage2(ActionEvent event) throws IOException {

		Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage2.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	private Button buttonToQPage3;

	@FXML
	void displayQPage3(ActionEvent event) throws IOException {
		a1 = getBool(experience);
		a2 = getBool(fps);
		a3 = getBool(sim);
		a4 = getBool(scary);

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("userDataBase.txt"), true));
		writer.write("," + a1 + "," + a2 + "," + a3 + "," + a4);
		writer.close();
		Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage3.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	private Button buttonToQPage4;

	@FXML
	void displayQPage4(ActionEvent event) throws IOException {
		a5 = getBool(racing);
		a6 = getBool(survival);
		a7 = getBool(party);
		a8 = getBool(multiplayer);

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("userDataBase.txt"), true));
		writer.write("," + a5 + "," + a6 + "," + a7 + "," + a8);
		writer.close();
		Parent CreateScene = FXMLLoader.load(getClass().getResource("QPage4.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

		// Finished creating profile : select current user

	}

	@FXML
	private Button buttonToProfile;

	@FXML
	void displayProfile(ActionEvent event) throws IOException {
		a9 = getBool(time);
		a10 = getBool(challenge);
		a11 = getBool(graphics);
		a12 = getBool(pay);
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("userDataBase.txt"), true));
		writer.write("," + a9 + "," + a10 + "," + a11 + "," + a12);
		writer.close();

		Parent CreateScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	private MenuItem homeToProfileButton;

	@FXML
	private MenuItem homeToLoginButton;

	@FXML
	private MenuBar homeMenuBar;

	@FXML
	private TextField profileUsername = new TextField();

	@FXML
	private Label label = new Label();

	@FXML
	private TextField profileAge = new TextField();

	@FXML
	private TextField profileName = new TextField();

	@FXML
	private TextField profileEmail = new TextField();

	@FXML
	private PasswordField profilePassword = new PasswordField();

	@FXML
	private ImageView wheel = new ImageView();

	// CONTROL table view home page clicks here
	@FXML
	public void clickItem(MouseEvent event) throws IOException {
		if (event.getClickCount() == 2) // Checking double click
		{
			if (tableView.getId() != null) {
				if (tableView.getSelectionModel().getSelectedItem() != null) {
					Main.currentGame = loadedGames.get(tableView.getSelectionModel().getSelectedItem().getName());
					Parent CreateScene = FXMLLoader.load(getClass().getResource("genericGame.fxml"));
					Scene scene = new Scene(CreateScene);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(scene);
					window.show();

				} else {
					System.out.println("No game2");
				}
			} else {
				if (tableViewFavorites.getSelectionModel().getSelectedItem() != null) {
					Main.currentGame = loadedGames
							.get(tableViewFavorites.getSelectionModel().getSelectedItem().getName());
					Parent CreateScene = FXMLLoader.load(getClass().getResource("genericGameFavorite.fxml"));
					Scene scene = new Scene(CreateScene);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(scene);
					window.show();
				} else {
					System.out.println("No game1");
				}
			}
			
		}
	}

	@FXML
	private ImageView imageOneLink = new ImageView();

	@FXML
	private ImageView imageTwoLink = new ImageView();
	@FXML
	private ImageView imageThreeLink = new ImageView();

	@FXML
	private Label genericTitle = new Label();
	@FXML
	private Label genericPrice = new Label();

	@FXML
	private TextArea descriptionText = new TextArea();

	@FXML
	private void initialize() {
		label.textProperty().bind(Bindings.format("Welcome, " + Main.currentUser.getFirstName()));

		profileUsername.promptTextProperty().set(Main.currentUser.getUsername());

		profileAge.promptTextProperty().set(Main.currentUser.getAge());

		profileEmail.promptTextProperty().set(Main.currentUser.getEmail());

		profilePassword.setPromptText("**********");

		profileName.setPromptText(Main.currentUser.getFirstName() + " " + Main.currentUser.getLastName());

		gameTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
		gamePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

		gameTitleFavorites.setCellValueFactory(new PropertyValueFactory<>("name"));
		gamePriceFavorites.setCellValueFactory(new PropertyValueFactory<>("price"));

		ArrayList<Game> games = loadGames();
		for (Game g : games) {
			dataList.add(g);
			loadedGames.put(g.getName(), g);
			titles.add(g.getName());
		}

		for (Game g : Main.currentUser.getFavorites().values()) {
			dataListFavorites.add(g);
		}

		FilteredList<Game> filteredData = new FilteredList<>(dataList, b -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(game -> {
				// If filter text is empty, display all persons.

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (game.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (game.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else
					return false; // Does not match.
			});
		});

		FilteredList<Game> filteredDataFavorites = new FilteredList<>(dataListFavorites, b -> true);

		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataFavorites.setPredicate(game -> {
				// If filter text is empty, display all persons.

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (game.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (game.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else
					return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Game> sortedData = new SortedList<>(filteredData);

		SortedList<Game> sortedDataFavorites = new SortedList<>(filteredDataFavorites);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		sortedDataFavorites.comparatorProperty().bind(tableViewFavorites.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tableView.setItems(sortedData);
		tableViewFavorites.setItems(sortedDataFavorites);

		genericTitle.textProperty().bind(Bindings.format("" + Main.currentGame.getName()));
		genericPrice.textProperty().bind(Bindings.format("" + Main.currentGame.getPrice()));
		String url1 = Main.currentGame.getImage1();
		String url2 = Main.currentGame.getImage2();
		String url3 = Main.currentGame.getImage3();
		if (url1 != null || url2 != null || url3 != null) {
			if (!url1.equals("Image 1")) {
				Image image1 = new Image(url1, true);
				imageOneLink.setImage(image1);
			} else {
				Image image1 = new Image("https://picsum.photos/350/200", true);
				imageOneLink.setImage(image1);
			}
			if (!url2.equals("Image 2")) {
				Image image2 = new Image(url2, true);
				imageTwoLink.setImage(image2);
			} else {
				Image image2 = new Image("https://picsum.photos/350/200", true);
				imageTwoLink.setImage(image2);
			}
			if (!url3.equals("Image 3")) {
				Image image3 = new Image(url3, true);
				imageThreeLink.setImage(image3);
			} else {
				Image image3 = new Image("https://picsum.photos/350/200", true);
				imageThreeLink.setImage(image3);
			}
		}

		if (Main.currentGame.getDescription() != null) {

			descriptionText.setText(Main.currentGame.getDescription());
		}

		if (claimButton != null) {
			claimText.setOpacity(0);
			claimBanner.setOpacity(0);
		}

	}

	@FXML
	private Button claimButton = new Button();

	@FXML
	private ImageView claimText = new ImageView();

	@FXML
	private ImageView claimBanner = new ImageView();

	@FXML
	void spinWheel(ActionEvent event) {
		if (claimButton != null) {
			claimText.setOpacity(0);
			claimBanner.setOpacity(0);
		}

		// wheel.setRotate(wheel.getRotate() + 90);
		int time = (int) (Math.random() * (4000 - 2000 + 1) + 2000);
		RotateTransition rt = new RotateTransition(Duration.millis(3000), wheel);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.setRate(3);
		rt.play();
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				rt.stop();
				if (claimButton != null) {
					claimText.setOpacity(1);
					claimBanner.setOpacity(1);
				}

			}
		}, time);

	}

	@FXML
	void displayRandomGame(ActionEvent event) throws IOException {

		Parent CreateScene = FXMLLoader.load(getClass().getResource("Random.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	private ArrayList<Game> loadGames() {
		ArrayList<Game> allGames = new ArrayList<>();
		try {
			File myObj = new File("gameDataBase.csv");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] vals = data.split(",");
				Game temp = new Game(vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7], vals[8],
						vals[9], vals[10], vals[11], vals[12], vals[13], vals[14], vals[15], vals[16], vals[17]);
				allGames.add(temp);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error with userDataBase");
			e.printStackTrace();
		}

		return allGames;
	}

	@FXML
	private Button updateInfoButton;

	@FXML
	void updateInfo(ActionEvent event) {

		boolean changedUser = false, changedPass = false, changedEmail = false, changedAge = false, change = false;
		loadUsers();

		if (!(profileUsername.getText().isEmpty() && profileAge.getText().isEmpty() && profileEmail.getText().isEmpty()
				&& profilePassword.getText().isEmpty() && profileName.getText().isEmpty())) {

			if (!profileUsername.getText().isEmpty())
				if (!users.containsKey(profileUsername.getText())) {
					Main.currentUser.setUsername(profileUsername.getText());
					changedUser = true;
					change = true;
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Username already taken!");
					alert.setContentText("Please try again.");

					alert.showAndWait();
				}

			if (!profilePassword.getText().isEmpty()) {
				Main.currentUser.setPassword(profilePassword.getText());
				changedPass = true;
				change = true;
			}

			if (!profileEmail.getText().isEmpty() && profileEmail.getText().contains("@")
					&& profileEmail.getText().contains(".")) {
				Main.currentUser.setEmail(profileEmail.getText());
				changedEmail = true;
				change = true;
			} else if (!profileEmail.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Email Address");
				alert.setContentText("Please try again.");

				alert.showAndWait();
			}

			if (!profileAge.getText().isEmpty() && profileAge.getText().length() >= 2
					&& !profileAge.getText().contains("[a-zA-Z+")) {
				Main.currentUser.setAge(profileAge.getText());
				changedAge = true;
				change = true;
			} else if (!profileAge.getText().isBlank()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid Age");
				alert.setContentText("Please try again.");

				alert.showAndWait();
			}

			String allUpdates = "";

			if (changedUser)
				allUpdates += "Username: " + profileUsername.getText() + "\n";
			if (changedPass)
				allUpdates += "Password: " + "********" + "\n";
			if (changedEmail)
				allUpdates += "Email: " + profileEmail.getText() + "\n";
			if (changedAge)
				allUpdates += "Age: " + profileAge.getText() + "\n";

			if (!change) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("UPDATE");
				alert.setHeaderText("Profile Information Updated:");
				alert.setContentText("No updates made");

				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("UPDATE");
				alert.setHeaderText("Profile Information Updated:");
				alert.setContentText(allUpdates);

				alert.showAndWait();

				profileUsername.setText("");
				profilePassword.setText("");
				;
				profileEmail.setText("");
				;
				profileName.setText("");
				;
				profileAge.setText("");

				this.initialize();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setHeaderText("Nothing Changed");
			alert.setContentText("Please try again.");

			alert.showAndWait();
		}

	}

	@FXML
	void homeToProfile(ActionEvent event) throws IOException {
		Parent CreateScene = FXMLLoader.load(getClass().getResource("Profile.fxml"));

		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) homeMenuBar.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	void randomToHome(ActionEvent event) throws IOException {
		Parent CreateScene = FXMLLoader.load(getClass().getResource("Home.fxml"));

		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) homeMenuBar.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	@FXML
	void homeToLogin(ActionEvent event) throws IOException {
		// logout option in home

		Parent CreateScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) homeMenuBar.getScene().getWindow();
		window.setScene(scene);
		window.show();

		// sets current user to fresh empty user
		Main.currentUser = new User();
	}

	private Boolean getBool(ToggleGroup group) {
		String text = String.valueOf(group.getSelectedToggle());

		if (text.contains("Yes"))
			return true;

		return false;

	}

	@FXML
	void claimRandomGame(ActionEvent event) throws IOException {
		// select random current game
		Random r = new Random();
		String randomTitle = titles.get(r.nextInt(titles.size()));

		Main.currentGame = loadedGames.get(randomTitle);

		Parent CreateScene = FXMLLoader.load(getClass().getResource("genericGame.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	void addGameToFavorite(ActionEvent event) throws IOException {
		boolean added = false;
		if (Main.currentUser.getFavorites().containsKey(Main.currentGame.getName())) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Game Added");
			alert.setHeaderText("Game already a favorite!");
			alert.setContentText("Title:   " + Main.currentGame.getName());
			alert.showAndWait();
		} else {

			File myObj = new File("userFavoriteDatabase.txt");
			Scanner myReader = new Scanner(myObj);
			myReader.nextLine();
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] userData = data.split(",");
				System.out.println(userData[0]);
				if (userData[0].equals(Main.currentUser.getUsername())) {
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(new File("userFavoriteDatabase.txt"), true));

					writer.write("," + Main.currentGame.getName());
					writer.close();
					added = true;
					
					Main.currentUser.getFavorites().put(Main.currentGame.getName(), Main.currentGame);
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Added");
					alert.setHeaderText("Added game to profile favorites!");
					alert.setContentText("Title:   " + Main.currentGame.getName());
					alert.showAndWait();
					break;
				}
			}
			if(!added) {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File("userFavoriteDatabase.txt"), true));
			writer.newLine();
			writer.write(Main.currentUser.getUsername() + "," + Main.currentGame.getName());
			writer.close();
			myReader.close();
			Main.currentUser.getFavorites().put(Main.currentGame.getName(), Main.currentGame);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Added");
			alert.setHeaderText("Added game to profile favorites!");
			alert.setContentText("Title:   " + Main.currentGame.getName());
			alert.showAndWait();
			}

			
		}
	}

	@FXML
	void showUserFavorites(ActionEvent event) throws IOException {

		Parent CreateScene = FXMLLoader.load(getClass().getResource("Favorites.fxml"));

		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) homeMenuBar.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

}
