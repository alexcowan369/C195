package controller;

import DAO.DAOAppointmentLayer;
import DAO.DAOContactLayer;
import DAO.DAOCountryLayer;
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
import model.Contact;
import model.Country;
import model.appointmentType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Below class creates the "Reports" controller.
 *
 * @author Alexander Cowan
 */
public class Reports implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<model.Appointment> Appointment = FXCollections.observableArrayList();
    ObservableList<String> Months = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    @FXML private ComboBox<String> monthComboBox;
    @FXML private ComboBox<appointmentType> typeComboBox;
    @FXML private Label appointTotalLbl;
    @FXML private ComboBox<Contact> contactComboBox;
    @FXML private ComboBox<Country> countryComboBox;
    @FXML private Label custTotalLbl;
    @FXML private TableView<model.Appointment> AppointmentTable;
    @FXML private TableColumn<Appointment, Integer> Appointment_ID;
    @FXML private TableColumn<Appointment, String> Title;
    @FXML private TableColumn<Appointment, String> Description;
    @FXML private TableColumn<Appointment, String> Location;
    @FXML private TableColumn<Appointment, Integer> Contact;
    @FXML private TableColumn<Appointment, String> Type;
    @FXML private TableColumn<Appointment, Calendar> Start;
    @FXML private TableColumn<Appointment, Calendar> End;
    @FXML private TableColumn<Appointment, Integer> Customer_ID;
    @FXML private TableColumn<Appointment, Integer> User_ID;

    /**
     * In the method below, the number of customers per country is displayed when a country from the "countryComboBox" box is selected by the user.
     * @param event the user selects a country from the "countryComboBox" combo box
     * @throws SQLException
     */
    @FXML void onActionCountryCombo(ActionEvent event) throws SQLException {
        Country selectedCountry = countryComboBox.getValue();
        int selectedCountryID = selectedCountry.getCountry_ID();
        custTotalLbl.setText(String.valueOf(DAOCustomerLayer.countCustomers(selectedCountryID)));
    }

    /**
     * In the method below, the "Main Menu" screen is loaded once the user selects the "Main Menu" button on the "Reports" screen/interface.
     * @param event the user clicks the "Main Menu" button
     * @throws IOException
     */
    @FXML void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, when the user selects the "appointmentType" from the "typeComboBox" combo box it displays the count of appointments in the specific/selected month.
     * @param event the user selects a type from the "typeComboBox" combo box
     * @throws SQLException
     */
    @FXML void onActionTypeCombo(ActionEvent event) throws SQLException {
        appointmentType selectedType = typeComboBox.getValue();
        String selectedMonth = monthComboBox.getValue();
        appointTotalLbl.setText(String.valueOf(DAOAppointmentLayer.countMonthType(selectedType, selectedMonth)));
    }

    /**
     * In the method below, when you select the month from the "monthComboBox" combo box it displays the count of already selected type of appointments in the specified month.
     * @param event the user selects a month from the "monthComboBox" combo box
     * @throws SQLException
     */
    @FXML void onActonMonthCombo(ActionEvent event) throws SQLException {
        appointmentType selectedType = typeComboBox.getValue();
        String selectedMonth = monthComboBox.getValue();
        appointTotalLbl.setText(String.valueOf(DAOAppointmentLayer.countMonthType(selectedType, selectedMonth)));
    }

    /**
     * In the method below, we see the appointments for a selected contact displayed when the user selected a contact from the "contactComboBox" combo box.
     * @param event the user selects a contact from the "contactComboBox" combo box
     */
    @FXML void onActionContactCombo(ActionEvent event) {
        Appointment.clear();
        Contact selectedContact = contactComboBox.getValue();
        int selectedContactID = selectedContact.getContact_ID();

        try {
                Appointment.addAll(DAOAppointmentLayer.getAppointmentsContactID(selectedContactID));
        } catch (Exception ex) {
                Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * In the method below, the combo boxes are set for the "AppointmentTable" from the "Appointments" table in the sql database.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactComboBox.setItems(DAOContactLayer.getAllContacts());
            countryComboBox.setItems(DAOCountryLayer.getAllCountries());
            typeComboBox.setItems(DAOAppointmentLayer.typeAppt());
            monthComboBox.setItems(Months);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        End.setCellValueFactory(new PropertyValueFactory<>("End"));
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        AppointmentTable.setItems(Appointment);
    }
}