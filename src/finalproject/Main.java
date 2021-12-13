// Nicholas_Baird_FinalProject - Main.java
// Author: Nicholas Baird
// Date: 12/13/2021


package finalproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		// Opens fxml file that holds node data
		//		Uses controller to customize node data - Where all of the logic is at (MainController.java)
		try {
			URL url = new File("src/finalproject/startpage.fxml").toURI().toURL();
		  Parent root = FXMLLoader.load(url);
		  
		  // Sets primaryStage information and shows the window
	      primaryStage.setScene(new Scene(root));
	      primaryStage.setResizable(false);
	      primaryStage.setTitle("Blockchain Simulator");
	      try {
	    	  primaryStage.getIcons().add(new Image("finalproject/block5.png"));
	      } catch(Exception e) {
	    	  System.out.println("Error: Cannot Load Icon");
	      }
	      primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
