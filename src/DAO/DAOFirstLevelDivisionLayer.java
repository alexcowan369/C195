package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class below creates the "FirstLevelDivision" database methods.
 *
 * @author Alexander Cowan
 */
public class DAOFirstLevelDivisionLayer {
    /**
     * In the method below, it allows us to ge the "FirstLevelDivision" from the getValue of the "divisionComboBox" combo box in "ModifyCustomers". A SQL query is executed to the select the "FirstLevelDivision" containing the "Division" matching the "getValue" of the specific "divisionComboBox" combo box
     * @param FirstLevelDivision the FirstLevelDivision to get the FirstLevelDivision for
     * @return the FirstLevelDivision divisionResult
     * @throws SQLException
     * @throws Exception
     */
    public static FirstLevelDivision getDivision(FirstLevelDivision FirstLevelDivision) throws SQLException, Exception {
        String sqlCommand = "select * FROM first_level_divisions WHERE Division  = '" + FirstLevelDivision + "'";
        SQLQueryLayer.makeQuery(sqlCommand);
        FirstLevelDivision divisionResult;
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            int Division_ID = qresult.getInt("Division_ID");
            String Division = qresult.getString("Division");
            Timestamp Create_Date = qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            String Created_By = qresult.getString("Created_By");
            Timestamp Last_Update = qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            String Last_Updated_By = qresult.getString("Last_Updated_By");
            int COUNTRY_ID = qresult.getInt("COUNTRY_ID");

            divisionResult = new FirstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            return divisionResult;
        }
        return null;
    }

    /**
     * In the method below, it sets the "ObservableList" div with "FirstLevelDivisions" from a specific and unique "Country ID". A SQl query is executed to select the "FirstLevelDivisions" with a "Country ID" matching the selected "Country ID".
     * @param country_id the "country ID" to get the "FirstLevelDivisions" for
     * @return the ObservableList div
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getDiv(int country_id) throws SQLException {
        ObservableList<FirstLevelDivision> div = FXCollections.observableArrayList();
        String sqlCommand = "select * from first_level_divisions where COUNTRY_ID = " + country_id;
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            int Division_ID = qresult.getInt("Division_ID");
            String Division = qresult.getString("Division");
            Timestamp Create_Date = qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            String Created_By = qresult.getString("Created_By");
            Timestamp Last_Update = qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            String Last_Updated_By = qresult.getString("Last_Updated_By");
            int COUNTRY_ID = qresult.getInt("COUNTRY_ID");

            FirstLevelDivision flDivResult = new FirstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            div.add(flDivResult);
        }
        return div;
    }

    /**
     * In the method below, we get a "FirstLevelDivision" from a "Division ID". A SQL query is executed to select the "FirstLevelDivision" object with the unique "Division ID".
     * @param division_id the division ID to get the FirstLevelDivision object for
     * @return the "FirstLevelDivision" "getDivFromDivIDResult"
     * @throws SQLException
     */
    public static FirstLevelDivision getDivFromDivID(int division_id) throws SQLException {
        String sqlCommand = "select * from first_level_divisions where DIVISION_ID = " + division_id;
        SQLQueryLayer.makeQuery(sqlCommand);
        FirstLevelDivision getDivFromDivIDResult;
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            int Division_ID = qresult.getInt("Division_ID");
            String Division = qresult.getString("Division");
            Timestamp Create_Date = qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar = Create_Date.toLocalDateTime();
            String Created_By = qresult.getString("Created_By");
            Timestamp Last_Update = qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar = Last_Update.toLocalDateTime();
            String Last_Updated_By = qresult.getString("Last_Updated_By");
            int COUNTRY_ID = qresult.getInt("COUNTRY_ID");

            getDivFromDivIDResult = new FirstLevelDivision(Division_ID, Division, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, COUNTRY_ID);
            return getDivFromDivIDResult;
        }
        return null;
    }
}