package controller;

import DAO.DAOCountryLayer;
import DAO.DAOCustomerLayer;
import DAO.DAOFirstLevelDivisionLayer;
import helper.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Class below creates the "CustomerAdd" controller.
 *
 * @author Alexander Cowan
 */
public class AddCustomers implements Initializable {
    Stage stage;
    Parent scene;
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> divisionComboBox;

    /**
     * Remeber Customer = cust and Label = lbl
     */
    @FXML private TextField custAddNameLbl;
    @FXML private TextField custAddPhoneLbl;
    @FXML private TextField custAddAddressLbl;
    @FXML private TextField custAddPostalLbl;

    /**
     * The below method saves a new customer only when the said user clicks the "Save" Button. A new customer is created using the inputted information. Information inputtd on the "AddCustomers" screen is utilized and inserts the new data/customer into the "Customers" table of the SQL database.
     * @param event the user clicks the "Save" button on the "AddCustomers" screen
     * @throws IOException
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try {
            String customer_Name = custAddNameLbl.getText();
            String customer_Address = custAddAddressLbl.getText();
            String postal_Code = custAddPostalLbl.getText();
            String customer_Phone = custAddPhoneLbl.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = Globals.userName;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = Globals.userName;
            FirstLevelDivision D = divisionComboBox.getValue();

            if (customer_Name.isEmpty()) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The name must not be empty]");
                alertm.showAndWait();
            } else if (customer_Address.isEmpty()){
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The address must not be empty]");
                alertm.showAndWait();
            } else if (postal_Code.isEmpty()) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The postal code must not be empty]");
                alertm.showAndWait();
            } else if (customer_Phone.isEmpty()) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The phone must not be empty]");
                alertm.showAndWait();
            } else if (divisionComboBox.getSelectionModel().getSelectedItem() == null) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The division must not be empty]");
                alertm.showAndWait();
            } else if (countryComboBox.getSelectionModel().getSelectedItem() == null) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The country must not be empty]");
                alertm.showAndWait();
            } else{
                int Division_ID = D.getDivision_ID();
                DAOCustomerLayer.addCustomer(customer_Name, customer_Address, postal_Code, customer_Phone, createDate, createdBy, lastUpdate, lastUpdateBy, Division_ID);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * In the method below, when the user clicks the "Cancel" button it returns the user back to the "Customers" screen. Note: inputted information is cleared and NOT saved if the cancel button is clicked
     * @param event the user clicks the "Cancel" button on the "AddCustomers" screen
     * @throws IOException
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * The method below sets the "divisionComboBox" combo box with only the divisions for the specific country when the user selects a country from the "countryComboBox" combo box.
     *
     * @param event the user selects a country from the countryCombo combo box
     * @throws SQLException
     */
    @FXML void OnActionCountryCombo(ActionEvent event) throws SQLException {
        Country C = countryComboBox.getValue();
        divisionComboBox.setItems(DAOFirstLevelDivisionLayer.getDiv(C.getCountry_ID()));
    }

    @FXML
    void onActionDivisionCombo(ActionEvent event) {

    }

    /**
     * Method below sets the "countryComboBox" combo box
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryComboBox.setItems(DAOCountryLayer.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}