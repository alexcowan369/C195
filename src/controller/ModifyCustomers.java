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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Below class creates "ModifyCustomers" controller.
 *
 * @author Alexander Cowan
 */
public class ModifyCustomers implements Initializable {

    Stage stage;
    Parent scene;
    private static Customer selectedCustomer;
    private static int selectedCountryID;
    private static Country selectedCountry;

    @FXML private ComboBox<FirstLevelDivision> divisionComboBox;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private Label custModifyIDLbl;
    @FXML private TextField custModifyNameLbl;
    @FXML private TextField custModifyPhoneLbl;
    @FXML private TextField custModifyAddressLbl;
    @FXML private TextField custModifyPostalLbl;

    /**
     * In the method below, when the "Cancel" button is clicked it returns said user back to the "Customers" screen. Like previous classes, inputted info is cleared and NOT saved.
     * @param event the user clicks the "Cancel" button on the "ModifyCustomers" screen
     * @throws IOException
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, an existing customer is modified when the "Save" button is clicked by the user. Information inputted o "ModifyCustomers" screen is used to update the existing customer. Information is also updated in the "Customers" table of the SQl database. Likewise, the "customer ID" is used to update the customer being modified in hte "Customers" table of SQL database.
     *
     * @param event the user clicks the "Save" button on the "ModifyCustomers" screen
     * @throws IOException
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try {
            int customerID = Integer.parseInt(custModifyIDLbl.getText());
            String customer_Name = custModifyNameLbl.getText();
            String customer_Address = custModifyAddressLbl.getText();
            String postal_Code = custModifyPostalLbl.getText();
            String customer_Phone = custModifyPhoneLbl.getText();
            Timestamp last_Update = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdateBy = Globals.userName;
            FirstLevelDivision D = divisionComboBox.getValue();;

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
            } else {

                int Division_ID = D.getDivision_ID();
                DAOCustomerLayer.modifyCustomer(customerID, customer_Name, customer_Address, postal_Code, customer_Phone, last_Update, lastUpdateBy, Division_ID);

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
     * This is the method to set the "divisionComboBox" combo box with only the divisions for only the specific country when the user selects a country from the "countryComboBox" combo box.
     * @param event the user selects a country from the "countryComboBox" combo box
     * @throws SQLException
     */
    @FXML void onActionCountryCombo(ActionEvent event) throws SQLException {
        divisionComboBox.setValue(null);
        Country C = countryComboBox.getValue();
        divisionComboBox.setItems(DAOFirstLevelDivisionLayer.getDiv(C.getCountry_ID()));
    }

    /**
     * In the method below, it sends the customer data to the fields on the "ModifyCustomers" screen. This is in conjunction with the customer selected on the "Customers" screen to be modified/edited.
     * @param customer the selected existing customer being received to modify
     */
    public static void receiveSelectedCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    /**
     * In the method below, the combo boxes are set with the appropriate values. It also sets the combo boxes to select the data from the "modified" customer and populates all fields with the data from the modified customer.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            divisionComboBox.setValue(DAOFirstLevelDivisionLayer.getDivFromDivID(selectedCustomer.getDivision_ID()));
            FirstLevelDivision selectedDivision = DAOFirstLevelDivisionLayer.getDivision(divisionComboBox.getValue());
            selectedCountryID = selectedDivision.getCOUNTRY_ID();
            selectedCountry = DAOCountryLayer.getCountryFromCountryID(selectedCountryID);
            countryComboBox.setValue(selectedCountry);
            Country C = countryComboBox.getValue();
            divisionComboBox.setItems(DAOFirstLevelDivisionLayer.getDiv(C.getCountry_ID()));
            countryComboBox.setItems(DAOCountryLayer.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }

        custModifyIDLbl.setText(Integer.toString(selectedCustomer.getCustomerId()));
        custModifyNameLbl.setText(selectedCustomer.getCustomerName());
        custModifyAddressLbl.setText(selectedCustomer.getCustomerAddress());
        custModifyPhoneLbl.setText(selectedCustomer.getCustomerPhone());
        custModifyPostalLbl.setText(selectedCustomer.getPostalCode());
    }
}