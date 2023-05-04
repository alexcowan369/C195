package controller;

import DAO.DAOAppointmentLayer;
import DAO.DAOContactLayer;
import DAO.DAOCustomerLayer;
import DAO.DAOUserLayer;
import helper.Globals;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Class below creates the "AddAppointments" controller.
 * @author Alexander Cowan
 */
public class AddAppointments implements Initializable {

    Stage stage;
    Parent scene;
    public ComboBox<User> userComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<LocalTime> beginComboBox;
    public ComboBox<LocalTime> endComboBox;

    /**
     * Remember appoint = appointment and Lbl = label, shortens the repetitive jargon
     */

    @FXML private TextField appointAddLocationLbl;
    @FXML private TextField appointAddTitleLbl;
    @FXML private TextField appointAddDescriptionLbl;
    @FXML private TextField appointAddTypeLbl;
    @FXML private DatePicker appointAddDatePicker;

    /**
     * In the method below, when the "Cancel" button is clicked by the user they are returned to hte "Appointments" screen. Information on said screen is not saved.
     * @param event the user clicks the "Cancel" button on the "AddAppointments" screen
     * @throws IOException
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, then the "Save" button is selected by said user a new appointment is saved. Information inputted on the "AddAppointments" screen is used to create a new appointment. The new appointment is inserted into the "Appointments" table of the SQL database.
     * @param event the user clicks the "Save" button on the "AddAppointments" screen
     * @throws IOException
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try{
            if (appointAddDatePicker.getValue() == null) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The date must not be empty]");
                alertm.showAndWait();
            } else if (appointAddDatePicker.getValue() != null) {
                String appointment_Title = appointAddTitleLbl.getText();
                String appointment_Description = appointAddDescriptionLbl.getText();
                String appointment_Location = appointAddLocationLbl.getText();
                String appointment_Type = appointAddTypeLbl.getText();
                LocalDateTime appointment_Start = LocalDateTime.of(appointAddDatePicker.getValue(), beginComboBox.getSelectionModel().getSelectedItem());
                LocalDateTime appointment_End = LocalDateTime.of(appointAddDatePicker.getValue(), endComboBox.getSelectionModel().getSelectedItem());
                Timestamp create_Date = Timestamp.valueOf(LocalDateTime.now());
                String created_By = Globals.userName;
                Timestamp last_Update = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdateBy = Globals.userName;
                int customer_ID = customerComboBox.getValue().getCustomerId();
                int user_ID = userComboBox.getValue().getUserId();
                int contact_ID = contactComboBox.getValue().getContact_ID();
                ZonedDateTime startTimeConvert = appointment_Start.atZone(ZoneId.systemDefault());
                ZonedDateTime startTimeEST = startTimeConvert.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime LDTstartEST = startTimeEST.toLocalDateTime();
                LocalDateTime locStart = LocalDateTime.of(appointAddDatePicker.getValue(), LocalTime.of(8, 0));
                ZonedDateTime endTimeConvert = appointment_End.atZone(ZoneId.systemDefault());
                ZonedDateTime endTimeEST = endTimeConvert.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime LDTendEST = endTimeEST.toLocalDateTime();
                LocalDateTime locEnd = LocalDateTime.of(appointAddDatePicker.getValue(), LocalTime.of(22, 0));

                if (appointment_Title.isEmpty()) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The title must not be empty]");
                    alertm.showAndWait();
                } else if (appointment_Description.isEmpty()) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The description must not be empty]");
                    alertm.showAndWait();
                } else if (appointment_Location.isEmpty()) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The location must not be empty]");
                    alertm.showAndWait();
                } else if (contactComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The contact must not be empty]");
                    alertm.showAndWait();
                } else if (appointment_Type.isEmpty()) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The type must not be empty]");
                    alertm.showAndWait();
                } else if (beginComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The start time must not be empty]");
                    alertm.showAndWait();
                } else if (endComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The end time must not be empty]");
                    alertm.showAndWait();
                } else if (customerComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The customer must not be empty]");
                    alertm.showAndWait();
                } else if (userComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The user must not be empty]");
                    alertm.showAndWait();
                } else if (LDTstartEST.isAfter(LDTendEST)) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [Appointment start time is after end time!]");
                    alertm.showAndWait();
                } else if (LDTendEST.isBefore(LDTstartEST)) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [Appointment end time is before start time!]");
                    alertm.showAndWait();
                } else if (LDTstartEST.isEqual(LDTendEST)) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [Appointment start time is same time as end time!]");
                    alertm.showAndWait();
                } else if (LDTstartEST.isBefore(locStart)) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [Appointment start time is too early!]");
                    alertm.showAndWait();
                } else if (LDTendEST.isAfter(locEnd)) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [Appointment end time is too late!]");
                    alertm.showAndWait();
                } else if (Appointments.checkOverlap(customer_ID, 0, appointment_Start, appointment_End) == true) {
                    return;
                } else {
                    DAOAppointmentLayer.addAppointment(appointment_Title, appointment_Description, appointment_Location, appointment_Type, appointment_Start, appointment_End, create_Date, created_By, last_Update, lastUpdateBy, customer_ID, user_ID, contact_ID);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * In the method below, it sets the combo boxes with all the appropriate values.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userComboBox.setItems(DAOUserLayer.getAllUsers());
            customerComboBox.setItems(DAOCustomerLayer.getAllCustomers());
            contactComboBox.setItems(DAOContactLayer.getAllContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalTime startBegin = LocalTime.of(5,0);
        LocalTime startEnd = LocalTime.of(21,45);
        LocalTime endStart = LocalTime.of(5,15);
        LocalTime endEnd = LocalTime.of(22,0);

        while(startBegin.isBefore(startEnd.plusSeconds(1))){
            beginComboBox.getItems().add(startBegin);
            startBegin = startBegin.plusMinutes(15);

            while(endStart.isBefore(endEnd.plusSeconds(1))){
                endComboBox.getItems().add(endStart);
                endStart = endStart.plusMinutes(15);
            }
        }
    }
}
