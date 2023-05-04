package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class below creates the User database methods.
 *
 * @author Alexander Cowan
 */
public class DAOUserLayer {
    /**
     * Below is the method to validate that a specific user exists in the SQL "Users" table with the provided username and provided password. Essentially searching to confirm a match ins the database. A SQL query is executed to select al users in with BOTH a matching username and a password that matches the inputted username and password. Then returns TRUE if any users are found or FALSE if no users are found.
     * @param User_Name the username to validate for
     * @param Password the password to validate for
     * @return true if user is found matching username and password, false if no user is found
     * @throws SQLException
     */
    public static boolean validateLogIn(String User_Name, String Password) throws SQLException {
        try (PreparedStatement pstt = JDBC.connection.prepareStatement("select * FROM Users WHERE User_Name = ? AND Password = ?")) {
            pstt.setString(1, User_Name);
            pstt.setString(2, Password);
            ResultSet resultSet = pstt.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
         e.printStackTrace();
        }
        return false;
    }

    /**
     * In the method below, it validates that a specific username exists in the "Users" table. A SQL method is executed to select all users with a username matching the inputted username then returns TRUE if any users are found or FALSE if no users are found.
     * @param User_Name the username to validate that it exists
     * @return true if user is found matching username, false if no user is found
     * @throws SQLException
     */
    public static boolean validateUserName(String User_Name) throws SQLException {
        try (PreparedStatement pstt = JDBC.connection.prepareStatement("SELECT * FROM users WHERE User_Name = ?")) {
            pstt.setString(1, User_Name);
            ResultSet resultSet = pstt.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * In the method below, it validates if the specific password exists in the "Users" table within the SQL database. A SQL query is executed to select all users to see if the password matches the password inputted/provided then returns TRUE if any users are found or returns FALSE is no users are found.
     * @param Password the password to validate if it exists
     * @return true is user is found matching password, false if no user is found
     * @throws SQLException
     */
    public static boolean validatePassword(String Password) throws SQLException {
        try (PreparedStatement pstt = JDBC.connection.prepareStatement("SELECT * FROM users WHERE Password = ?")) {
            pstt.setString(1, Password);
            ResultSet resultSet = pstt.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * In the method below, we get a "User" from the provided "userName". A SQL query us executed to select the user from the "Users" table with a matching "userName".
     * @param userName the username to get the User object for
     * @return the User userResult matching the selected userName
     * @throws SQLException
     * @throws Exception
     */
    public static User getUser(String userName) throws SQLException, Exception{
        String sqlCommand="select * FROM Users WHERE User_Name  = '" + userName+ "'";
        SQLQueryLayer.makeQuery(sqlCommand);
           User userResult;
           ResultSet qresult= SQLQueryLayer.getResult();
           while(qresult.next()){
               int userid=qresult.getInt("User_ID");
               String userNameG=qresult.getString("User_Name");
               String password=qresult.getString("Password");
               Timestamp createDate=qresult.getTimestamp("Create_Date");
               LocalDateTime createDateCalendar=createDate.toLocalDateTime();
               String createdBy = qresult.getString("Created_By");
               Timestamp lastUpdate=qresult.getTimestamp("Last_Update");
               LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
               String lastUpdateby=qresult.getString("Last_Updated_By");

               userResult= new User(userid, userNameG, password,  createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
               return userResult;
           }
        return null;
    }

    /**
     * In the method below, we get ALL users. A SQL query is executed which selects all users from the "Users" table and adds the users to the ObservableList allUsers.
     * @return the ObservableList allUsers containing all users
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers=FXCollections.observableArrayList();
            String sqlCommand="select * from Users";
            SQLQueryLayer.makeQuery(sqlCommand);
            ResultSet qresult= SQLQueryLayer.getResult();
             while(qresult.next()){
                int userid=qresult.getInt("User_ID");
                String userNameG=qresult.getString("User_Name");
                String password=qresult.getString("Password");
                Timestamp createDate=qresult.getTimestamp("Create_Date");
                LocalDateTime createDateCalendar=createDate.toLocalDateTime();
                String createdBy = qresult.getString("Created_By");
                Timestamp lastUpdate=qresult.getTimestamp("Last_Update");
                LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
                String lastUpdateby=qresult.getString("Last_Updated_By");

                User userResult= new User(userid, userNameG, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allUsers.add(userResult);
            }
        return allUsers;
    }

    /**
     * In the method below, it returns the "User" matching the specific "UserID". A SQL query is executed to select a "User" from the Users table with a matching "userID".
     * @param userID the userID to get the User object for
     * @return the User getUserFromUserIDResult matching the selected userID
     * @throws SQLException
     */
    public static User getUserFromUserID(int userID) throws SQLException {
        String sqlCommand = "select * from users where User_ID = " + userID;
        SQLQueryLayer.makeQuery(sqlCommand);
        User getUserFromUserIDResult;
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            int User_ID = qresult.getInt("User_ID");
            String User_Name = qresult.getString("User_Name");
            String Password = qresult.getString("Password");
            Timestamp createDate=qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String Created_By = qresult.getString("Created_By");
            Timestamp lastUpdate=qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String Last_Updated_By = qresult.getString("Last_Updated_By");

            getUserFromUserIDResult = new User(User_ID, User_Name, Password, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
            return getUserFromUserIDResult;
        }
        return null;
    }

    /**
     * In the method below, it returns the "User ID" of a "userName". A SQL query is executed to select the "User ID" matching the selected "userName".
     * @param userName the username to get the "userID" for
     * @return the User ID matching the selected userName
     * @throws SQLException
     */
    public static int getUserIDFromUserName(String userName) throws SQLException {
        int userID = 0;
        String sqlCommand = "select User_ID from users where User_Name = '" + userName+ "'";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            userID = qresult.getInt("User_ID");

            int getUserIDFromUserNameResult = userID;
            return getUserIDFromUserNameResult;
        }
        return userID;
    }
}