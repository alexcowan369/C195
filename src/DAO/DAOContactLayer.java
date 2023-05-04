package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class below creates the Contact database methods.
 *
 * @author Alexander Cowan
 */
public class DAOContactLayer {
    /**
     * In the method below, it creates an ObservableList to get all contacts. A SQL query is executed to fina all contacts and then adds the contacts to the ObservableList "allContacts".
     * @return the ObservableList "allContacts"
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sqlCommand = "select * from Contacts";
        SQLQueryLayer.makeQuery(sqlCommand);
        ResultSet result = SQLQueryLayer.getResult();
        while (result.next()) {
            int Contact_ID = result.getInt("Contact_ID");
            String Contact_Name = result.getString("Contact_Name");
            String Email = result.getString("Email");

            Contact contactResult = new Contact(Contact_ID, Contact_Name, Email);
            allContacts.add(contactResult);
        }
        return allContacts;
    }

    /**
     * In the method below, it gets a Contact object from the "Contact ID". A SQl query is executed to select the contact from the Contracts table with the selected "Contact ID".
     * @param contactID the "contact ID" to find the contact object for
     * @return the Contact getContactFromContactIDResult
     * @throws SQLException
     *
     */
    public static Contact getContactFromContactID(int contactID) throws SQLException {
        String sqlCommand = "select * from contacts where Contact_ID = " + contactID;
        SQLQueryLayer.makeQuery(sqlCommand);
        Contact getContactFromContactIDResult;
        ResultSet result = SQLQueryLayer.getResult();
        while (result.next()) {
            int Contact_ID = result.getInt("Contact_ID");
            String Contact_Name = result.getString("Contact_Name");
            String Email = result.getString("Email");

            getContactFromContactIDResult = new Contact(Contact_ID, Contact_Name, Email);
            return getContactFromContactIDResult;
        }
        return null;
    }
}
