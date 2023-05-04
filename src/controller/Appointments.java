package controller;

import DAO.DAOAppointmentLayer;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class below creates the "Appointments" controller.
 *
 * @author Alexander Cowan
 */
public class Appointments implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Appointment> Appointments = FXCollections.observableArrayList();

    @FXML private RadioButton weekSpecRadioButton;
    @FXML private RadioButton monthSpecRadioButton;
    @FXML private RadioButton allRadioButton;
    @FXML private TableView<Appointment> AppointmentTable;
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
     * Below is the method to check for a specific appointment overlap. If a condition is met below the method will return TRUE after checking the various combinations and overlaps. If no overlaps are found the method below returns FALSE.
     * @param customer_ID specific customer ID to check overlaps for
     * @param appointment_ID specific appointment ID being created
     * @param appointment_Start is start time of the appointment being created
     * @param appointment_End is end time of the appointment being created
     * @return true = appointment overlap found, false = no overlap
     * @throws Exception
     */
    public static boolean checkOverlap(int customer_ID, int appointment_ID, LocalDateTime appointment_Start, LocalDateTime appointment_End) throws Exception {
        ObservableList<Appointment> aList = DAOAppointmentLayer.getAllAppointments();
        LocalDateTime apptBeginCheck;
        LocalDateTime apptEndCheck;

        for (Appointment a : aList) {
            apptBeginCheck = a.getStart();
            apptEndCheck = a.getEnd();
            if (customer_ID != a.getCustomer_ID()) {
               continue;
            }
            if (appointment_ID == a.getAppointment_ID()) {
                continue;
            } else if (apptBeginCheck.isEqual(appointment_Start) || apptBeginCheck.isEqual(appointment_End) || apptEndCheck.isEqual(appointment_Start) || apptEndCheck.isEqual(appointment_End)){
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [Appointments must not start or end at same time as existing customer appointments]");
                alertm.showAndWait();
                return true;
            } else if (appointment_Start.isAfter(apptBeginCheck) && (appointment_Start.isBefore(apptEndCheck))){
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [Appointment start must not be during existing customer appointments]");
                alertm.showAndWait();
                return true;
            } else if (appointment_End.isAfter(apptBeginCheck) && appointment_End.isBefore(apptEndCheck)){
                Alert alertm = new Alert(Alert.AlertType.WARNING);
                alertm.setTitle("Warning Message");
                alertm.setContentText("ERROR [Appointment end must not be during existing customer appointments]");
                alertm.showAndWait();
                return true;
            }
        }
        return false;
    }

    /**
     * In the method below, the "AddAppointments" screen loads when the Add Button is clicked from the "Appointments" screen.
     * @param event user clicks the "Add" button from the "Appointments" screen
     * @throws IOException
     */
    @FXML void onActionAddAppointment(ActionEvent event) throws IOException {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAdd.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**
     * In the method below, an appointment is deleted when the "Delete" button in the "Appointments" screen is clicked by the user.
     * @param event user clicks the "Delete" button from the "Appointments" screen
     * @throws Exception
     */
    @FXML void onActionDeleteAppointment(ActionEvent event) throws Exception {
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle("Warning Message");
            alertm.setContentText("ERROR [No appointment selected]");
            alertm.showAndWait();
        } else {
            Alert alertm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alertm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int deletedAppt_ID = AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID();
                String deletedAppt_Type = AppointmentTable.getSelectionModel().getSelectedItem().getType();
                DAOAppointmentLayer.deleteAppointment(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());

                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning Message");
                alert2.setContentText("Appointment ID # " + deletedAppt_ID + " of type " + deletedAppt_Type + " successfully deleted.");
                alert2.showAndWait();

                Appointments = DAOAppointmentLayer.getAllAppointments();
                AppointmentTable.setItems(Appointments);
                AppointmentTable.refresh();
            } else {
                Appointments = DAOAppointmentLayer.getAllAppointments();
                AppointmentTable.setItems(Appointments);
                AppointmentTable.refresh();
            }
        }
    }

    /**
     * In the method below, the "MainMenu" screen is loaded when the user clicks the "Main Menu" button in the "Appointments" screen.
     * @param event the user clicks the "Main Menu" button on the "Appointments" screen
     * @throws IOException
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * In the method below, when the user clicks the "Modify" button from the "Appointments" screen the "ModifyAppointments" screen is then loaded.
     * @param event the user clicks the "Modify" button on the "Appointments" screen
     * @throws IOException
     */
    @FXML void onActionModifyAppointment(ActionEvent event) throws IOException {
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle("Warning Message");
            alertm.setContentText("ERROR [No specific appointment selected]");
            alertm.showAndWait();
        } else {
            ModifyAppointments.receiveSelectedAppointment(AppointmentTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsModify.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * In the method below, the "AppointmentTable" view is changed to see all appointments when the "All" radio button is selected on the "Appointments" screen.
     * @param event the user clicks the "All" radio button on the "Appointments" screen
     * @throws Exception
     */
    @FXML void onActionAll(ActionEvent event) throws Exception {
        weekSpecRadioButton.setSelected(false);
        monthSpecRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(DAOAppointmentLayer.getAllAppointments());
    }

    /**
     * In the method below, the "AppointmentTable" changes the view to the current month when the "Month" radio button on the "Appointments" screen is selected by said user.
     * @param event the user clicks the "Month" radio button on the "Appointments" screen
     * @throws Exception
     */
    @FXML void onActionMonth(ActionEvent event) throws Exception {
        allRadioButton.setSelected(false);
        weekSpecRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(DAOAppointmentLayer.getCurrentMonthAppointments());
    }

    /**
     * In the method below, the "AppointmentTable" changes the view to the current week when the "Week" radio button on the "Appointments" screen is selected by said user.
     * @param event the user clicks the "Week" radio button on the "Appointments" screen
     * @throws Exception
     */
    @FXML void onActionWeek(ActionEvent event) throws Exception {
        allRadioButton.setSelected(false);
        monthSpecRadioButton.setSelected(false);
        Appointments.clear();
        Appointments.addAll(DAOAppointmentLayer.getCurrentWeekAppointments());
    }

    /**
     * In the method below, the "AppointmentsTable" is set and the "All" radio button selected by default.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        try {
            Appointments.addAll(DAOAppointmentLayer.getAllAppointments());

        } catch (Exception ex) {
            Logger.getLogger(Appointments.class.getName()).log(Level.SEVERE, null, ex);
        }

        AppointmentTable.setItems(Appointments);
        allRadioButton.setSelected(true);
    }
}
