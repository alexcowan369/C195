package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.appointmentType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class below creates the "Appointment" database methods.
 *
 * @author Alexander Cowan
 */
public class DAOAppointmentLayer {
    /**
     * In the method below, it creates an "ObservablesList" of appointments containing said User ID as a unique identifier. An SQL query is executed to find all appointments containing the select User ID. Once the "User ID" is found it adds the appointments to the "ObservablesList userIDAppointments"
     * @param userID the "userID" to get the appointment of
     * @return the "ObservableList userIDAppointments" containing appointments with the User ID
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getAppointmentUserID(int userID) throws SQLException, Exception{
        ObservableList<Appointment> userIDAppointments=FXCollections.observableArrayList();
        String sqlStatement="select * FROM Appointments WHERE User_ID  = '" + userID + "'";
        SQLQueryLayer.makeQuery(sqlStatement);
        Appointment appointment_Result;
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Appointment_ID=result.getInt("Appointment_ID");
            String Title=result.getString("Title");
            String Description=result.getString("Description");
            String Location=result.getString("Location");
            String Type=result.getString("Type");
            Timestamp Start=result.getTimestamp("Start");
            LocalDateTime StartCalendar=Start.toLocalDateTime();
            Timestamp End=result.getTimestamp("End");
            LocalDateTime EndCalendar=End.toLocalDateTime();
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime Create_DateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime Last_UpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
            int Customer_ID=result.getInt("Customer_ID");
            int User_ID=result.getInt("User_ID");
            int Contact_ID=result.getInt("Contact_ID");

            appointment_Result= new Appointment(Appointment_ID, Title, Description, Location, Type, StartCalendar, EndCalendar, Create_DateCalendar, Created_By, Last_UpdateCalendar, Last_Updated_By, Customer_ID, User_ID, Contact_ID);
            userIDAppointments.add(appointment_Result);
        }
        return userIDAppointments;
    }

    /**
     * In the method below, we create an "ObservableList" to get all appointments. SQL query is executed to fina all appointments then add the appointment to the "ObservableList allAppointments"
     * @return the "ObservableList allAppointments" with all appointments
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointment> allSysAppointments=FXCollections.observableArrayList();
        String sqlStatement="select * from Appointments";
        SQLQueryLayer.makeQuery(sqlStatement);
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Appointment_ID=result.getInt("Appointment_ID");
            String Title=result.getString("Title");
            String Description=result.getString("Description");
            String Location=result.getString("Location");
            String Type=result.getString("Type");
            Timestamp Start=result.getTimestamp("Start");
            LocalDateTime StartCalendar=Start.toLocalDateTime();
            Timestamp End=result.getTimestamp("End");
            LocalDateTime EndCalendar=End.toLocalDateTime();
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime Create_DateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime Last_UpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
            int Customer_ID=result.getInt("Customer_ID");
            int User_ID=result.getInt("User_ID");
            int Contact_ID=result.getInt("Contact_ID");

            Appointment appointment_Result= new Appointment(Appointment_ID, Title, Description, Location, Type, StartCalendar, EndCalendar, Create_DateCalendar, Created_By, Last_UpdateCalendar, Last_Updated_By, Customer_ID, User_ID, Contact_ID);
            allSysAppointments.add(appointment_Result);
        }
        return allSysAppointments;
    }

    /**
     * The method below adds an appointment. A SQL query executes to insert a new "Appointment" into the "Appointments" table of the database using the inputted data on the "AddAppointments" screen.
     * @param appointmentTitle the title of the appointment to add
     * @param appointmentDescription the description of the appointment to add
     * @param appointmentLocation the location of the appointment to add
     * @param appointmentType the type of the appointment to add
     * @param appointmentStart the start of the appointment to add
     * @param appointmentEnd the end of the appointment to add
     * @param createDate the create date of the appointment to add
     * @param createdBy the user creating the appointment to add
     * @param lastUpdate the last updated time of the appointment to add
     * @param lastUpdateBy the last user to update the appointment to add
     * @param customerID the customer ID of the appointment to add
     * @param userID the user ID of the appointment to add
     * @param contactID the contact ID of the appointment to add
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        try{
            String sqlstring = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psti = JDBC.connection.prepareStatement(sqlstring);

            psti.setString(1, appointmentTitle);
            psti.setString(2, appointmentDescription);
            psti.setString(3, appointmentLocation);
            psti.setString(4, appointmentType);
            psti.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            psti.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            psti. setTimestamp(7, createDate);
            psti.setString(8, createdBy);
            psti.setTimestamp(9, lastUpdate);
            psti.setString(10, lastUpdateBy);
            psti.setInt(11, customerID);
            psti.setInt(12, userID);
            psti.setInt(13, contactID);

            psti.execute();
        } catch(SQLException ex){
            ex.printStackTrace();
            }
        }

    /**
     * Below is the method to modify an appointment. A SQL query is executed to update existing "Appointment" in the "Appointments" table of the SQL database using inputted data on the "ModifyAppointments" screen.
     * @param appointmentID the ID of the appointment to modify
     * @param appointmentTitle the title of the appointment to modify
     * @param appointmentDescription the description of the appointment to modify
     * @param appointmentLocation the location of the appointment to modify
     * @param appointmentType the type of the appointment to modify
     * @param appointmentStart the start time of the appointment to modify
     * @param appointmentEnd the end time of the appointment to modify
     * @param lastUpdate the last updated time of the appointment to modify
     * @param lastUpdateBy the last user to update the appointment to modify
     * @param customerID the customer ID of the appointment to modify
     * @param userID the user ID of the appointment to modify
     * @param contactID the contact ID of the appointment to modify
     */
    public static void modifyAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, Timestamp lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        try{
            String sqlstring = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement psti = JDBC.connection.prepareStatement(sqlstring);

            psti.setString(1, appointmentTitle);
            psti.setString(2, appointmentDescription);
            psti.setString(3, appointmentLocation);
            psti. setString(4, appointmentType);
            psti.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            psti.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            psti.setTimestamp(7, lastUpdate);
            psti.setString(8, lastUpdateBy);
            psti.setInt(9, customerID);
            psti.setInt(10, userID);
            psti.setInt(11, contactID);
            psti.setInt(12, appointmentID);

            psti.execute();
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * The method below deletes an appointment. A SQL query is executed to delete and existing "Appointment" in the "Appointments" table of the database with the selected & unique "Appointment ID"
     * @param appointmentID the unique ID of the appointment to delete
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sqlstring = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement psti = JDBC.connection.prepareStatement(sqlstring);

            psti.setInt(1, appointmentID);

            psti.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * The method below deletes an appointment when a customer is deleted. Similar to a cascade delete. A SQL query is executed to delete an existing "Appointment" in the "Appointments" table of the database with the selected & unique "Customer ID".
     * @param customerID the ID of the customer being deleted
     * @param appointmentID the ID of the appointment to delete
     */
    public static void deleteApptCustID(int customerID, int appointmentID) {
        try {
            String sqlstring = "DELETE FROM appointments WHERE Customer_ID = ? AND Appointment_ID = ?";
            PreparedStatement psti = JDBC.connection.prepareStatement(sqlstring);

            psti.setInt(1, customerID);
            psti.setInt(2, appointmentID);

            psti.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * In the method below, we create the "ObservableList typeAppt" with only the distinct appointment types. A SQl query is executed to select only the distinct Type strings from the "Appointments" table.
     * @return the "ObservableList typeAppt" with only the distinct appointment types
     * @throws SQLException
     */
    public static ObservableList<appointmentType> typeAppt() throws SQLException {
        ObservableList<appointmentType> typeAppt=FXCollections.observableArrayList();
        String sqlCommand = "select distinct Type from appointments";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            String Type=result.getString("Type");

            appointmentType appointmentTypeResult= new appointmentType(Type);
            typeAppt.add(appointmentTypeResult);
        }
    return typeAppt;
    }

    /**
     * Below is the method to count the number of appointments with both the selected type and selected month. A SQL query is executed to count from the "Appointments" table where the selected type and selected month are in an appointment.
     * @param selectedType the type of appointment to count
     * @param month the month for which to count
     * @return the number of appointments with both the selected type and the selected month
     * @throws SQLException
     */
    public static int countMonthType(appointmentType selectedType, String month) throws SQLException {
        String sqlCommand = "SELECT COUNT(*) AS monthType FROM appointments WHERE Type  = '" + selectedType + "' AND MONTHNAME(Start) = '" + month + "'";
        SQLQueryLayer.makeQuery(sqlCommand);
        int countMonthTypeResult = 0;
        ResultSet result = SQLQueryLayer.getResult();
        while(result.next()) {
            countMonthTypeResult = result.getInt("monthType");

            return countMonthTypeResult;
        }
        return countMonthTypeResult;
    }

    /**
     * Below is the Lambda that filters appoints using unique "Contact ID". The lambda acts a microprogram within the overarching java program to filter the list below
     * @param selectedContactID the "contact ID" by which to filter appointments
     * @return the ObservableList contactList of appointments containing the "Contact ID"
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getAppointmentsContactID(int selectedContactID) throws SQLException, Exception{
        ObservableList<Appointment> allAppointments = getAllAppointments();
        ObservableList<Appointment> allContactList = allAppointments.filtered(a -> {
            if (a.getContact_ID() == selectedContactID){
                return true;
            }
            return false;
        });
        return allContactList;
    }

    /**
     * In the method below, it creates the ObservableList "currentMonthAppointments" with only appointments with start timestamps within the current month. A SQl query is executed to select only appointments in the "Appointments" table where the month of the start attribute matches the current month.
     * @return the ObservableList "currentMonthAppointments" of appointments this month
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getCurrentMonthAppointments() throws SQLException, Exception{
        ObservableList<Appointment> currentMonthAppointments=FXCollections.observableArrayList();
        String sqlCommand="select * from Appointments where MONTH(Start) = MONTH(CURDATE())";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Appointment_ID=result.getInt("Appointment_ID");
            String Title=result.getString("Title");
            String Description=result.getString("Description");
            String Location=result.getString("Location");
            String Type=result.getString("Type");
            Timestamp Start=result.getTimestamp("Start");
            LocalDateTime StartCalendar=Start.toLocalDateTime();
            Timestamp End=result.getTimestamp("End");
            LocalDateTime EndCalendar=End.toLocalDateTime();
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime Create_DateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime Last_UpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
            int Customer_ID=result.getInt("Customer_ID");
            int User_ID=result.getInt("User_ID");
            int Contact_ID=result.getInt("Contact_ID");

            Appointment appointmentResult= new Appointment(Appointment_ID, Title, Description, Location, Type, StartCalendar, EndCalendar, Create_DateCalendar, Created_By, Last_UpdateCalendar, Last_Updated_By, Customer_ID, User_ID, Contact_ID);
            currentMonthAppointments.add(appointmentResult);
        }
        return currentMonthAppointments;
    }

    /**
     * The below method creates the ObservableList "currentWeekAppointments" with only appointments with start timestamps within the current week. A SQL query is executed to select only appointments in the "Appointments" table where the week of the start attribute matches the current week.
     * @return the ObservableList "currentWeekAppointments" of appointments this week
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getCurrentWeekAppointments() throws SQLException, Exception{
        ObservableList<Appointment> currentWeekAppointments=FXCollections.observableArrayList();
        String sqlCommand="select * from Appointments where WEEK(Start) = WEEK(CURDATE())";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Appointment_ID=result.getInt("Appointment_ID");
            String Title=result.getString("Title");
            String Description=result.getString("Description");
            String Location=result.getString("Location");
            String Type=result.getString("Type");
            Timestamp Start=result.getTimestamp("Start");
            LocalDateTime StartCalendar=Start.toLocalDateTime();
            Timestamp End=result.getTimestamp("End");
            LocalDateTime EndCalendar=End.toLocalDateTime();
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime Create_DateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime Last_UpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");
            int Customer_ID=result.getInt("Customer_ID");
            int User_ID=result.getInt("User_ID");
            int Contact_ID=result.getInt("Contact_ID");

            Appointment appointmentResult= new Appointment(Appointment_ID, Title, Description, Location, Type, StartCalendar, EndCalendar, Create_DateCalendar, Created_By, Last_UpdateCalendar, Last_Updated_By, Customer_ID, User_ID, Contact_ID);
            currentWeekAppointments.add(appointmentResult);
        }
        return currentWeekAppointments;
    }

    /**
     * In the method below, it counts the number of appointments with the selected & unique "Customer ID". A SQl query is executed to count from the "Appointments" table where the selected & "Customer ID" is in an appointment.
     * @param customerID the "customer ID" to count appointments for
     * @return the number of appointments containing the "Customer ID"
     * @throws SQLException
     */
    public static int countCustAppt(int customerID) throws SQLException {
        String sqlCommand = "SELECT COUNT(*) AS custAppt FROM appointments WHERE Customer_ID  = '" + customerID + "'";
        SQLQueryLayer.makeQuery(sqlCommand);
        int countCustApptResult = 0;
        ResultSet result = SQLQueryLayer.getResult();
        while(result.next()) {
            countCustApptResult = result.getInt("custAppt");

            return countCustApptResult;
        }
        return countCustApptResult;
    }
}