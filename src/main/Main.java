package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * <h1>Acowan - a GUI-based scheduling desktop application in JavaFX with filtering options to manage a SQL database of Appointments, Customers, and Reports</h1>
 * The class below creates an app that manages a database of customers and their appointments.
 * <p><b>
 * Lambdas are found on line 69 of LogIn.java and on line 259 of DAOAppointmentLayer.java
 * </b></p>
 * After unzipping acowan195final.zip, the Javadocs files can be found at the /javadoc file
 *
 * To run properly, unzip the acowan195final.zip then ONLY open "C195 Project AC" folder in Intellij. The essential Libraries are properly added under "libraries" folder.
 *
 * @author Alexander Cowan
 */

public class Main extends Application {
    /**
     * Below loads MainScreen.fxml to start the GUI and display the LogIn screen for user.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start (Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setTitle(myBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    ResourceBundle myBundle = ResourceBundle.getBundle("bundle/lang");

    /** Below is the main method, which launches the database program for the user.
     * @param args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
