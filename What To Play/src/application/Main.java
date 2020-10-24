package application;

import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			StartUpLocation temp = new StartUpLocation(1920,1080);
			double xPos = temp.getXPos();
	        double yPos = temp.getYPos();
			if (xPos != 0 && yPos != 0) {
	            primaryStage.setX(xPos);
	            primaryStage.setY(yPos);
	            System.out.println(xPos + " " + yPos);
	        } else {
	            primaryStage.centerOnScreen();
	        }
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	static User currentUser = new User();
	static Game currentGame = new Game();
	
	public static void main(String[] args) {
		launch(args);
		
	}
	public class StartUpLocation {

	    private double xPos = 0;
	    private double yPos = 0;

	    /**
	     * Get Top Left X and Y Positions for a Window to center it on the
	     * currently active screen at application startup
	     * @param windowWidth - Window Width
	     * @param windowHeight - Window Height
	     */
	    public StartUpLocation(double windowWidth, double windowHeight) {
	        // Get X Y of start-up location on Active Screen
	        // simple_JavaFX_App
	        try {
	            // Get current mouse location, could return null if mouse is moving Super-Man fast
	        	Point p = MouseInfo.getPointerInfo().getLocation();
	            // Get list of available screens
	            List<Screen> screens = Screen.getScreens();
	            if (p != null && screens != null && screens.size() > 1) {
	                // Screen bounds as rectangle
	                Rectangle2D screenBounds;
	                // Go through each screen to see if the mouse is currently on that screen
	                for (Screen screen : screens) {
	                    screenBounds = screen.getVisualBounds();
	                    // Trying to compute Left Top X-Y position for the Application Window
	                    // If the Point p is in the Bounds
	                    if (screenBounds.contains(p.x, p.y)) {
	                        // Fixed Size Window Width and Height
	                        xPos = screenBounds.getMinX() + ((screenBounds.getMaxX() - screenBounds.getMinX() - windowWidth) / 2);
	                        yPos = screenBounds.getMinY() + ((screenBounds.getMaxY() - screenBounds.getMinY() - windowHeight) / 2);
	                        return;
	                    }
	                }
	            }
	        } catch (HeadlessException headlessException) {
	            // Catch and report exceptions
	            headlessException.printStackTrace();
	        }
	    }

	    /**
	     * @return the top left X Position of Window on Active Screen
	     */
	    public double getXPos() {
	        return xPos;
	    }

	    /**
	     * @return the top left Y Position of Window on Active Screen
	     */
	    public double getYPos() {
	        return yPos;
	    }
	}
}
