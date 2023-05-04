package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class below creates the MainMenu controller.
 * @author Alexander Cowan
 */
public class MainMenu implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * The method below loads the "Appointments" screen when the "Appointments" button is clicked by said user on the main menu.
     * @param event the user clicks the "Appointments" button
     * @throws IOException
     */
    @FXML void onActionAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Method below is similar to above but this time with the "Customers" screen loaded when the "Customers" button is selected.
     * @param event the user clicks the "Customers" button
     * @throws IOException
     */
    @FXML void onActionCustomers(ActionEvent event) throws IOException {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**
     * In the method below, when the "Reports" button is clicked the "Reports" screen is then loaded.
     * @param event The user clicks the "Reports" button
     * @throws IOException
     */
    @FXML void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Below method loads the "Log In" screen when "Log Out" button is selected by the user. Helps maintain the integrity and design of a login system
     * @param event The user clicks the "Log Out" button
     * @throws IOException
     */
    @FXML void onActionLogOut(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * method below initializes the "MainMenu" controller.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}