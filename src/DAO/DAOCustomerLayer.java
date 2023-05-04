package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The class below creates the Customer database methods.
 * @author Alexander Cowan
 */
public class DAOCustomerLayer {
    /**
     * In the class below, an ObservableList to get all customers is created. A SQL query is executed to final all the customers. Once the customers are found they are then added to the ObservableList "allCustomers".
     * @return the ObservableList "allCustomers"
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers=FXCollections.observableArrayList();
        String sqlCommand="select * from Customers";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet qresult= SQLQueryLayer.getResult();
        while(qresult.next()){
            int customerid=qresult.getInt("Customer_ID");
            String customerNameG=qresult.getString("Customer_Name");
            String address=qresult.getString("Address");
            String postalCode=qresult.getString("Postal_Code");
            String phone=qresult.getString("Phone");
            Timestamp createDate=qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String createdBy=qresult.getString("Created_By");
            Timestamp lastUpdate=qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String lastUpdateby=qresult.getString("Last_Updated_By");
            int Division_ID=qresult.getInt("Division_ID");
            Customer customerResult= new Customer(customerid, customerNameG, address, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, Division_ID);
            allCustomers.add(customerResult);
        }
        return allCustomers;
    }

    /**
     * The method below is the instructions to add a customer. A SQL query is executed to insert a NEW customer into the "Customers" table within the SQL database. The data inputted on the user is used/inputted on the "AddCustomers" screen.
     * @param customerName the name of the customer to add
     * @param customerAddress the address of the customer to add
     * @param postalCode the postal code of the customer to add
     * @param customerPhone the phone number of the customer to add
     * @param createDate the create date of the customer to add
     * @param createdBy the user creating the customer to add
     * @param lastUpdate the last updated time of the customer to add
     * @param lastUpdateBy the last user to update the customer to add
     * @param Division_ID the division ID of the customer to add
     */
    public static void addCustomer(String customerName, String customerAddress, String postalCode, String customerPhone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy, int Division_ID) {
        try {
            String sqlstring = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psti = JDBC.connection.prepareStatement(sqlstring);

            psti.setString(1, customerName);
            psti.setString(2, customerAddress);
            psti.setString(3, postalCode);
            psti.setString(4, customerPhone);
            psti.setTimestamp(5, createDate);
            psti.setString(6, createdBy);
            psti.setTimestamp(7, lastUpdate);
            psti.setString(8, lastUpdateBy);
            psti.setInt(9, Division_ID);

            psti.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Below is the method to modify an existing "Customer" in the database. A SQL query is executed to update an existing "Customer" in the "Customers" table of tje SQL database. Data is pulled/used from the inputted information by the user on the "ModifyCustomers" screen.
     * @param customerID the ID of the customer to modify
     * @param customerName the name of the customer to modify
     * @param customerAddress the address of the customer to modify
     * @param postalCode the postal code of the customer to modify
     * @param customerPhone the phone number of the customer to modify
     * @param lastUpdate the last updated time of the customer to modify
     * @param lastUpdateBy the last user to update the customer to modify
     * @param Division_ID the division ID of the customer to modify
     */
    public static void modifyCustomer(int customerID, String customerName, String customerAddress, String postalCode, String customerPhone, Timestamp lastUpdate, String lastUpdateBy, int Division_ID) {
        try {
            String sqlstring = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement psmod = JDBC.connection.prepareStatement(sqlstring);

            psmod.setString(1, customerName);
            psmod.setString(2, customerAddress);
            psmod.setString(3, postalCode);
            psmod.setString(4, customerPhone);
            psmod.setTimestamp(5, lastUpdate);
            psmod.setString(6, lastUpdateBy);
            psmod.setInt(7, Division_ID);
            psmod.setInt(8, customerID);

            psmod.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method below for deleting a customer. A SQL query is executed to delete an existing "Customer" in the "Customers" table of the SQL database. The unique "Customer ID" is utilized in order to select the correct customer, this maintains accuracy/integrity within the database.
     * @param customerID the ID of the selected customer to delete
     */
    public static void deleteCustomer(int customerID) {
        try {
            String sqlcd = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement psmod = JDBC.connection.prepareStatement(sqlcd);

            psmod.setInt(1, customerID);

            psmod.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method below is used to get a "Customer" object from a "Customer ID". A SQL query is executed to select the customer from the "Customers" table using the unique "Customer ID".
     * @param customerID the customer ID to get the customer object for
     * @return the Customer getCustomerFromCustomerIDResult
     * @throws SQLException
     */
    public static Customer getCustomerFromCustomerID(int customerID) throws SQLException {
        String sqlCommand = "select * from customers where Customer_ID = " + customerID;
        SQLQueryLayer.makeQuery(sqlCommand);
        Customer getCustomerFromCustomerIDResult;
        ResultSet qresult = SQLQueryLayer.getResult();
        while (qresult.next()) {
            int Customer_ID = qresult.getInt("Customer_ID");
            String Customer_Name = qresult.getString("Customer_Name");
            String Address = qresult.getString("Address");
            String Postal_Code = qresult.getString("Postal_Code");
            String Phone = qresult.getString("Phone");
            Timestamp createDate=qresult.getTimestamp("Create_Date");
            LocalDateTime createDateCalendar=createDate.toLocalDateTime();
            String Created_By = qresult.getString("Created_By");
            Timestamp lastUpdate=qresult.getTimestamp("Last_Update");
            LocalDateTime lastUpdateCalendar=lastUpdate.toLocalDateTime();
            String Last_Updated_By = qresult.getString("Last_Updated_By");
            int Division_ID = qresult.getInt("Division_ID");

            getCustomerFromCustomerIDResult = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, createDateCalendar, Created_By, lastUpdateCalendar, Last_Updated_By, Division_ID);
            return getCustomerFromCustomerIDResult;
        }
        return null;
    }

    /**
     * Method below is used to count the number of customers with a specific "Country ID". Similar to sorting a list or checking the database for a match in relation to the unique PK. A SQL query is executed to count the number of customers where the "Division ID" is found using the "Country ID". The "Country ID" is matched to the range of "FirstLevelDivision IDs".
     * @param Country_ID the country ID of which to count customers for
     * @return the number of customers with the selected "Country ID"
     * @throws SQLException
     */
    public static int countCustomers(int Country_ID) throws SQLException {
        String sqlCommand = "SELECT COUNT(*) AS customerCountry FROM customers WHERE Division_ID IN (SELECT Division_ID FROM First_Level_Divisions WHERE Country_ID = " + Country_ID + ")";
        SQLQueryLayer.makeQuery(sqlCommand);
        int countCustomersResult = 0;
        ResultSet qresult = SQLQueryLayer.getResult();
        while(qresult.next()) {
            countCustomersResult = qresult.getInt("customerCountry");

            return countCustomersResult;
        }
        return countCustomersResult;
    }
}
