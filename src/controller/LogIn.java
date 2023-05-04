package controller;

import DAO.DAOAppointmentLayer;
import DAO.DAOUserLayer;
import Interfaces.GeneralInterface;
import helper.Globals;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Class below creates the "Login" controller.
 * @author Alexander Cowan
 */
public class LogIn implements Initializable {
    Stage stage;
    ResourceBundle myBundle = ResourceBundle.getBundle("bundle/lang");
    TimeZone tz = TimeZone.getDefault();
    String tz1 = tz.getID();

    @FXML private TextField logInUsernameLbl;
    @FXML private PasswordField logInPasswordLbl;
    @FXML private Label userNameLbl;
    @FXML private Label passwordLbl;
    @FXML private Label locationText;
    @FXML private Label locationLbl;
    @FXML private Button logInButton;
    @FXML private Button exitButton;

    /**
     * In the method below, wen the user clicks the "Exit" button the program is exited/terminated. Note an alert is given for confirmation.
     * @param event the user clicks the "Exit" button
     */
    @FXML void onActionExit(ActionEvent event) {
        Alert alertm = new Alert(Alert.AlertType.CONFIRMATION, myBundle.getString("Exit"));
        Optional<ButtonType> result = alertm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }

    /**
     * Below is the lambda that indicates NO username was provided at the specific timestamp.
     */
    public GeneralInterface messageNoUser = s -> {
        return "No username provided at " + s;
    };

    /**
     * In the method below, it receives the inputted user & pass when the "Log In" button is selected by the user.
     * The method first checks that the username and password are not empty. After this is checked for it references/validates the specific password combination with the records in the "Users" table of SQl database.
     * There has to be an exact match with the database for the login to proceed.
     * @param event the user clicks the "Log In" button
     * @throws Exception
     * @throws SQLException
     * @throws IOException
     */
    @FXML void onActionLogIn(ActionEvent event) throws Exception, SQLException, IOException {
        String User_Name = logInUsernameLbl.getText();
        String Password = logInPasswordLbl.getText();
        User user_Result = DAOUserLayer.getUser(User_Name);
        String filelabel = "login_activity.txt", item;
        File file = new File(filelabel);
        FileWriter filewriter = new FileWriter(file, true);
        PrintWriter output_File = new PrintWriter(filewriter);
        LocalDateTime time = LocalDateTime.now();
        ZonedDateTime LDTConvert = time.atZone(ZoneId.systemDefault());
        ZonedDateTime LDTUTC = LDTConvert.withZoneSameInstant(ZoneId.of("Etc/UTC"));

        if (User_Name.isEmpty() || User_Name.isBlank()) {
            output_File.println(messageNoUser.getMessage(LDTUTC.toString()));
            output_File.close();

            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle(myBundle.getString("WarningDialog"));
            alertm.setContentText(myBundle.getString("ERROR"));
            alertm.showAndWait();
        } else if (Password.isEmpty() || Password.isBlank()) {
            item = User_Name;
            output_File.println("User " + item + " failed login at " + LDTUTC);
            output_File.close();

            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle(myBundle.getString("WarningDialog"));
            alertm.setContentText(myBundle.getString("ERROR"));
            alertm.showAndWait();
        } else if (DAOUserLayer.validateUserName(User_Name) == false) {
            item = User_Name;
            output_File.println("User " + item + " failed login at " + LDTUTC);
            output_File.close();

            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle(myBundle.getString("WarningDialog"));
            alertm.setContentText(myBundle.getString("ERROR"));
            alertm.showAndWait();
        } else if (DAOUserLayer.validatePassword(Password) == false) {
            item = User_Name;
            output_File.println("User " + item + " failed login at " + LDTUTC);
            output_File.close();

            Alert alertm = new Alert(Alert.AlertType.WARNING);
            alertm.setTitle(myBundle.getString("WarningDialog"));
            alertm.setContentText(myBundle.getString("ERROR"));
            alertm.showAndWait();
        } else if (DAOUserLayer.validateLogIn(User_Name, Password) == true) {
            Globals.userName = user_Result.getUserName();
            int userID = DAOUserLayer.getUserIDFromUserName(Globals.userName);
            LocalDateTime nowLDT = LocalDateTime.now();
            LocalDateTime plus15minLDT = nowLDT.plusMinutes(15);
            ObservableList<Appointment> scheduledAppts = FXCollections.observableArrayList();
            ObservableList<Appointment> appointments = DAOAppointmentLayer.getAppointmentUserID(userID);

            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    if ((appointment.getStart().isAfter(nowLDT) || appointment.getStart().isEqual(nowLDT)) && (appointment.getStart().isBefore(plus15minLDT) || appointment.getStart().isEqual(plus15minLDT))) {
                        scheduledAppts.add(appointment);
                        if (scheduledAppts.size() > 0) {
                            Alert alertm = new Alert(Alert.AlertType.WARNING);
                            alertm.setTitle(myBundle.getString("WarningDialog"));
                            alertm.setContentText(myBundle.getString("NoticeAppt") + appointment.getAppointment_ID() + " " + myBundle.getString("At") + appointment.getStart() + " " + myBundle.getString("StartsSoon"));
                            alertm.showAndWait();
                        }
                    }
                }
                if (scheduledAppts.size() < 1) {
                    Alert alertm = new Alert(Alert.AlertType.WARNING);
                    alertm.setTitle(myBundle.getString("WarningDialog"));
                    alertm.setContentText(myBundle.getString("NoAppointments"));
                    alertm.showAndWait();
                }
            }

            item = User_Name;
            output_File.println("User " + item + " successfully logged in at " + LDTUTC);
            output_File.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
            loader.load();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
    } else {
        item = User_Name;
        output_File.println("User " + item + " failed login at " + LDTUTC);
        output_File.close();

        Alert alertm = new Alert(Alert.AlertType.WARNING);
        alertm.setTitle(myBundle.getString("WarningDialog"));
        alertm.setContentText(myBundle.getString("ERROR"));
        alertm.showAndWait();
    }
}

    /**
     * The below method determines the user location and text as either English or French via the resource bundle files. It is dependent on the user location at time of login.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationLbl.setText(tz1);
        userNameLbl.setText(myBundle.getString("Username"));
        passwordLbl.setText(myBundle.getString("Password"));
        locationText.setText(myBundle.getString("Location"));
        logInButton.setText(myBundle.getString("Login"));
        exitButton.setText(myBundle.getString("ExitButton"));
    }
}