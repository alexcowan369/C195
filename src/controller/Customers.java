package controller;

import DAO.DAOAppointmentLayer;
import DAO.DAOCustomerLayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Below class creates the Customers controller.
 *
 * @author Alexander Cowan
 */
public class Customers implements Initializable {

    Stage stage;
    Parent scene;

    ObservableList<Customer> CustomerList = FXCollections.observableArrayList();

    @FXML private TableView<Customer> CustomerTable;
    @FXML private TableColumn<Customer, Integer> Customer_ID;
    @FXML private TableColumn<Customer, String> Customer_Name;
    @FXML private TableColumn<Customer, String> Address;
    @FXML private TableColumn<Customer, String> Postal_Code;
    @FXML private TableColumn<Customer, String> Phone_Number;
    @FXML private TableColumn<Customer, Calendar> Create_Date;
    @FXML private TableColumn<Customer, String> Create_By;
    @FXML private TableColumn<Customer, Calendar> Last_Update;
    @FXML private TableColumn<Customer, String> Last_Updated_By;
    @FXML private TableColumn<Customer, Integer> Division_ID;

    /**
     * In the method below, the "AddCustomers" screen is loaded when the user clicks the "Add" button from the "Customers" screen
     * @param event user clicks the "Add" button from the "Customers" screen
     * @throws IOException
     */
    @FXML void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, when the "Delete" button on the "Customers" screen is selected a customer is deleted.
     * @param event user clicks the "Delete" button from the "Customers" screen
     * @throws Exception
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws Exception {
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle("Warning Message");
            alertm.setContentText("ERROR [No customer selected]");
            alertm.showAndWait();
        } else {
            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?");
            Optional<ButtonType> result = alertm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Appointment> aList = DAOAppointmentLayer.getAllAppointments();
                for (Appointment a : aList) {
                    if (a.getCustomer_ID() == CustomerTable.getSelectionModel().getSelectedItem().getCustomerId()) {
                        Alert alertAppt = new Alert(Alert.AlertType.CONFIRMATION, "WARNING [Must delete all appointments first] \n \n Are you sure you want to delete Appointment ID " + a.getAppointment_ID() + "?");
                        Optional<ButtonType> resultAppt = alertAppt.showAndWait();
                        if (resultAppt.isPresent() && resultAppt.get() == ButtonType.OK) {
                            DAOAppointmentLayer.deleteApptCustID(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId(), a.getAppointment_ID());
                        }
                    }
                }
                if (DAOAppointmentLayer.countCustAppt(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId()) > 0){
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning Message");
                    alert2.setContentText("ERROR [Must delete all appointments first before deleting customer]");
                    alert2.showAndWait();
                } else {
                    String deletedName = CustomerTable.getSelectionModel().getSelectedItem().getCustomerName();
                    DAOCustomerLayer.deleteCustomer(CustomerTable.getSelectionModel().getSelectedItem().getCustomerId());

                    Alert alert3 = new Alert(Alert.AlertType.WARNING);
                    alert3.setTitle("Warning Message");
                    alert3.setContentText(deletedName + " successfully deleted.");
                    alert3.showAndWait();

                    CustomerList = DAOCustomerLayer.getAllCustomers();
                    CustomerTable.setItems(CustomerList);
                    CustomerTable.refresh();
                }
            } else {
                CustomerList = DAOCustomerLayer.getAllCustomers();
                CustomerTable.setItems(CustomerList);
                CustomerTable.refresh();
            }
        }
    }

    /**
     * In the method below, the "Main Menu" screen is loaded when the user clicks "Main Menu" button on the 'Customers" screen.
     * @param event the user clicks the "Main Menu" button on the "Customers" screen
     * @throws IOException
     */
    @FXML void onActionMainMenu(ActionEvent event) throws IOException {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**
     * In the method below, when the "Modify" button is selected on the "Customers" screen the "ModifyCustomers" screen is loaded.
     * @param event the user clicks the "Modify" button on the "Customers" screen
     * @throws IOException
     */
    @FXML void onActionModifyCustomer(ActionEvent event) throws IOException {
        if (CustomerTable.getSelectionModel().getSelectedItem() == null) {
            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle("Warning Message");
            alertm.setContentText("ERROR [No customer selected]");
            alertm.showAndWait();
        } else {
            ModifyCustomers.receiveSelectedCustomer(CustomerTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersModify.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * In the method below, the "CustomerTable" is set.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        Phone_Number.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        Create_Date.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        Create_By.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        Last_Update.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        Last_Updated_By.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));

        try {
            CustomerList.addAll(DAOCustomerLayer.getAllCustomers());
        } catch (Exception ex) {
            Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
        }
        CustomerTable.setItems(CustomerList);
    }
}