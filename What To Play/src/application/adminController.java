package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class adminController {

	@FXML
	private TextField image3 = new TextField();

	@FXML
	private TextField price;

	@FXML
	private TextField gameTitle = new TextField();

	@FXML
	private TextArea gameDescription;

	@FXML
	private Button adminToAdmin2;

	@FXML
	private Label label;

	@FXML
	private TextField image1;

	@FXML
	private TextField image2;

	@FXML
	private RadioButton gameEasyT;

	@FXML
	private RadioButton gameEasyF;

	private String title = "";
	private String description;
	private String imageLink1;
	private String imageLink2;
	private String imageLink3;
	private String gamePrice;
	private Boolean a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12; // game atributes

	@FXML
	private ToggleGroup raceGame;

	@FXML
	private ToggleGroup challengeGame;

	@FXML
	private ToggleGroup graphicGame;

	@FXML
	private ToggleGroup multGame;

	@FXML
	private ToggleGroup fpsGame;

	@FXML
	private ToggleGroup scaryGame;

	@FXML
	private ToggleGroup gameEasy;

	@FXML
	private ToggleGroup survGame;

	@FXML
	private ToggleGroup simGame;

	@FXML
	private ToggleGroup freeGame;

	@FXML
	private ToggleGroup timeGame;

	@FXML
	private ToggleGroup partyGame;
	
	

	@FXML
	void displayLogin(ActionEvent event) throws IOException {
		Parent createAccScene = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene createAccount = new Scene(createAccScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(createAccount);
		window.show();

	}

	@FXML
	void nextPageAdmin(ActionEvent event) throws IOException {

		// set the variables to later load into game database
		title = gameTitle.getText();
		description = gameDescription.getText();
		imageLink1 = image1.getText();
		imageLink2 = image2.getText();
		imageLink3 = image3.getText();
		gamePrice = price.getText();
		
		File gameData = new File("gameDataBase.csv");
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("gameDataBase.csv"), true));
		writer.newLine();
		writer.write(title + "," + description + "," + imageLink1 + "," + imageLink2 + "," + imageLink3 + ","
				+ gamePrice);
		
		writer.close();
		Parent CreateScene = FXMLLoader.load(getClass().getResource("Admin2.fxml"));
		Scene scene = new Scene(CreateScene);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	private Boolean getBool(ToggleGroup group) {
		String text = String.valueOf(group.getSelectedToggle());

		if (text.contains("Yes"))
			return true;

		return false;

	}

	@FXML
	void saveGame(ActionEvent event) throws IOException {

		a1 = getBool(gameEasy);
		a2 = getBool(fpsGame);
		a3 = getBool(simGame);
		a4 = getBool(scaryGame);
		a5 = getBool(raceGame);
		a6 = getBool(survGame);
		a7 = getBool(partyGame);
		a8 = getBool(multGame);
		a9 = getBool(timeGame);
		a10 = getBool(challengeGame);
		a11 = getBool(graphicGame);
		a12 = getBool(freeGame);

		System.out.println("uploading game to file");
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("gameDataBase.csv"), true));
		writer.write("," + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + "," + a8 + "," + a9 + ","
				+ a10 + "," + a11 + "," + a12);
		writer.close();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Upload Complete");
		alert.setHeaderText(null);
		alert.setContentText("Game added to Database");

		alert.showAndWait();
		
		Parent loginParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
		Scene home = new Scene(loginParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(home);
		window.show();
	}

	@FXML
	void displayAdmin(ActionEvent event) throws IOException {
		Parent loginParent = FXMLLoader.load(getClass().getResource("Admin.fxml"));
		Scene home = new Scene(loginParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(home);
		window.show();
	}

}