package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The class below creates the Country database methods.
 *
 * @author Alexander Cowan
 */
public class DAOCountryLayer {
    /**
     * In the method below, the ObservableList is created to get all countries. A SQL query is executed to find all countries and then adds the countries to the ObservableList "allCountries".
     * @return the ObservableList "allCountries"
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
        ObservableList<Country> allCountries=FXCollections.observableArrayList();
        String sqlCommand="select * from countries";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Country_ID=result.getInt("Country_ID");
            String Country=result.getString("Country");
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");

            Country countryResult= new Country(Country_ID, Country, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
            allCountries.add(countryResult);
        }
        return allCountries;
    }

    /**
     * In the method below, it gets the Country object from a "Country ID". A SQL query is executed to select the country from the "Countries" table with the selected & unique "Country ID".
     * @param CountryID the "country ID" to get the country object for
     * @return the Country "countryResult"
     * @throws SQLException
     * @throws Exception
     */
    public static Country getCountryFromCountryID(int CountryID) throws SQLException, Exception{
        String sqlCommand="select * FROM countries WHERE Country_ID  = '" + CountryID + "'";
        SQLQueryLayer.makeQuery(sqlCommand);
        Country countryResult;
        ResultSet result= SQLQueryLayer.getResult();
        while(result.next()){
            int Country_ID=result.getInt("Country_ID");
            String Country=result.getString("Country");
            Timestamp Create_Date=result.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=Create_Date.toLocalDateTime();
            String Created_By=result.getString("Created_By");
            Timestamp Last_Update=result.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=Last_Update.toLocalDateTime();
            String Last_Updated_By=result.getString("Last_Updated_By");

            countryResult= new Country(Country_ID, Country, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By);
            return countryResult;
        }
        return null;
    }
}
