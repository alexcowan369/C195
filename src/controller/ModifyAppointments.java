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
import model.Appointment;
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
 * Below class creates the "ModifyAppointments" controller.
 * @author Alexander Cowan
 */
public class ModifyAppointments implements Initializable {

    Stage stage;
    Parent scene;
    private static Appointment selectedAppointment;

    @FXML private Label appointModifyIDLbl;
    @FXML private ComboBox<Contact> appointModifyContactComboBox;
    @FXML private TextField appointModifyLocationLbl;
    @FXML private TextField appointModifyTitleLbl;
    @FXML private TextField appointModifyDescriptionLbl;
    @FXML private TextField appointModifyTypeLbl;
    @FXML private DatePicker appointModifyDatePicker;
    @FXML private ComboBox<LocalTime> appointModifyBeginComboBox;
    @FXML private ComboBox<LocalTime> appointModifyEndComboBox;
    @FXML private ComboBox<Customer> appointModifyCustomerIDComboBox;
    @FXML private ComboBox<User> appointModifyUserIDComboBox;

    /**
     * In the method below, when the "Cancel" button is clicked the user is returned to the "Appointments" screen. Information inputted is not saved.
     * @param event the user clicks the "Cancel" button
     * @throws IOException
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, when the "Save" button is selected an existing appointment is modified. Appointments are updated with information inputted on the "ModifyAppointments" screen and within the "Appointments" table of the SQl database. The method uses the "appointment ID" to update the appointment being modified within the "Appointments" table of the SQl database.
     * @param event the user clicks the "Save" button on the "ModifyAppointments" screen
     * @throws IOException
     */
    @FXML void onActionSave(ActionEvent event) throws IOException {
        try {
            if (appointModifyDatePicker.getValue() == null) {
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [The date must not be empty]");
                alertm.showAndWait();
            } else if (appointModifyDatePicker.getValue() != null) {
                int appointment_ID = Integer.parseInt(appointModifyIDLbl.getText());
                String appointment_Title = appointModifyTitleLbl.getText();
                String appointment_Description = appointModifyDescriptionLbl.getText();
                String appointment_Location = appointModifyLocationLbl.getText();
                String appointment_Type = appointModifyTypeLbl.getText();
                LocalDateTime appointment_Start = LocalDateTime.of(appointModifyDatePicker.getValue(), appointModifyBeginComboBox.getSelectionModel().getSelectedItem());
                LocalDateTime appointment_End = LocalDateTime.of(appointModifyDatePicker.getValue(), appointModifyEndComboBox.getSelectionModel().getSelectedItem());
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                String lastUpdateBy = Globals.userName;


                int customer_ID = appointModifyCustomerIDComboBox.getValue().getCustomerId();
                int user_ID = appointModifyUserIDComboBox.getValue().getUserId();
                int contact_ID = appointModifyContactComboBox.getValue().getContact_ID();
                ZonedDateTime startTimeConvert = appointment_Start.atZone(ZoneId.systemDefault());
                ZonedDateTime startTimeEST = startTimeConvert.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime LDTstartEST = startTimeEST.toLocalDateTime();
                LocalDateTime locStart = LocalDateTime.of(appointModifyDatePicker.getValue(), LocalTime.of(8, 0));
                ZonedDateTime endTimeConvert = appointment_End.atZone(ZoneId.systemDefault());
                ZonedDateTime endTimeEST = endTimeConvert.withZoneSameInstant(ZoneId.of("America/New_York"));
                LocalDateTime LDTendEST = endTimeEST.toLocalDateTime();
                LocalDateTime locEnd = LocalDateTime.of(appointModifyDatePicker.getValue(), LocalTime.of(22, 0));

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
                } else if (appointModifyContactComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The contact must not be empty]");
                    alertm.showAndWait();
                } else if (appointment_Type.isEmpty()) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The type must not be empty]");
                    alertm.showAndWait();
                } else if (appointModifyBeginComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The start time must not be empty]");
                    alertm.showAndWait();
                } else if (appointModifyEndComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The end time must not be empty]");
                    alertm.showAndWait();
                } else if (appointModifyCustomerIDComboBox.getSelectionModel().getSelectedItem() == null) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle("Warning Message");
                    alertm.setContentText("ERROR [The customer must not be empty]");
                    alertm.showAndWait();
                } else if (appointModifyUserIDComboBox.getSelectionModel().getSelectedItem() == null) {
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
                    return;
                } else if (Appointments.checkOverlap(customer_ID, appointment_ID, appointment_Start, appointment_End) == true) {
                    return;
                } else {
                    DAOAppointmentLayer.modifyAppointment(appointment_ID, appointment_Title, appointment_Description, appointment_Location, appointment_Type, appointment_Start, appointment_End, lastUpdate, lastUpdateBy, customer_ID, user_ID, contact_ID);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * In the method below, the appointment selected on the "Appointments" screen to be modified and then the appointment data is sent to the fields on the "ModifyAppointments" screen.
     */
    public static void receiveSelectedAppointment(Appointment appointment){
        selectedAppointment = appointment;
    }

    /**
     * In the method below, the combo boxes are set with all appropriate values. Likewise, the combo boxes are set to select data from the modified appointment and populate all fields with said data from the modified appointment.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            appointModifyContactComboBox.setValue(DAOContactLayer.getContactFromContactID(selectedAppointment.getContact_ID()));
            appointModifyContactComboBox.setItems(DAOContactLayer.getAllContacts());
            appointModifyCustomerIDComboBox.setValue(DAOCustomerLayer.getCustomerFromCustomerID(selectedAppointment.getCustomer_ID()));
            appointModifyCustomerIDComboBox.setItems(DAOCustomerLayer.getAllCustomers());
            appointModifyUserIDComboBox.setValue(DAOUserLayer.getUserFromUserID(selectedAppointment.getUser_ID()));
            appointModifyUserIDComboBox.setItems(DAOUserLayer.getAllUsers());
        } catch (Exception throwables) {
        throwables.printStackTrace();
        }

        LocalTime startBegin = LocalTime.of(5,0);
        LocalTime startEnd = LocalTime.of(21,45);
        LocalTime endStart = LocalTime.of(5,15);
        LocalTime endEnd = LocalTime.of(22,0);

        while(startBegin.isBefore(startEnd.plusSeconds(1))){
            appointModifyBeginComboBox.getItems().add(startBegin);
            startBegin = startBegin.plusMinutes(15);

            while(endStart.isBefore(endEnd.plusSeconds(1))){
                appointModifyEndComboBox.getItems().add(endStart);
                endStart = endStart.plusMinutes(15);
            }
        }

        appointModifyIDLbl.setText(Integer.toString(selectedAppointment.getAppointment_ID()));
        appointModifyTitleLbl.setText(selectedAppointment.getTitle());
        appointModifyDescriptionLbl.setText(selectedAppointment.getDescription());
        appointModifyLocationLbl.setText(selectedAppointment.getLocation());
        appointModifyTypeLbl.setText(selectedAppointment.getType());
        appointModifyDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        appointModifyBeginComboBox.setValue(selectedAppointment.getStart().toLocalTime());
        appointModifyEndComboBox.setValue(selectedAppointment.getEnd().toLocalTime());
    }
}
