package se2203b.ipayroll;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdministratorTableAdapter implements DataStore {
    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public AdministratorTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Administrator");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE UserAccount");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {
            String command = "CREATE TABLE UserAccount ("
                    + "userAccountName VARCHAR(30) NOT NULL PRIMARY KEY,"
                    + "encryptedPassword VARCHAR(100) NOT NULL,"
                    + "emailAddress VARCHAR(50) NOT NULL,"
                    + "accountType VARCHAR(10) NOT NULL"
                    + ")";
            // Create the table
            stmt.execute(command);
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        try {
            String command = "CREATE TABLE Administrator ("
                    + "id VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "fullName VARCHAR(60) NOT NULL, "
                    + "dateCreated DATE, "
                    + "userAccount VARCHAR(30) NOT NULL REFERENCES UserAccount(userAccountName)"
                    + ")";
            // Create the table
            stmt.execute(command);
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }

        try {
            addAmin();
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }

        connection.close();
    }

    // Add the default admin account
    private void addAmin() throws SQLException {
        // Create admin user account first
        String defaultAdminPassword = "admin";
        UserAccount account = new UserAccount("admin", encrypt(defaultAdminPassword), "admin@ipayroll.com", "admin");
        DataStore userAccount = new UserAccountTableAdapter(false);
        userAccount.addNewRecord(account);
        // get current date to be burned in with admin user data
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        // Create Administrator
        Administrator administrator = new Administrator("Admin-1", "Admin Full-Name",
                java.sql.Date.valueOf(dateFormat.format(date).toString()),
                account);
        addNewRecord(administrator);
    }

    // adds new record, reads from Administrator object
    @Override
    public void addNewRecord(Object data) throws SQLException {
        Administrator administrator = (Administrator) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();

        // insert the administrator profile
        String command = "INSERT INTO Administrator ( id,  fullName, dateCreated, userAccount) "
                + "VALUES ( '"
                + administrator.getID() + "', '"
                + administrator.getFullName() + "', '"
                + administrator.getDateCreated() + "', '"
                + administrator.getUserAccount().getUserAccountName() + "')";
        stmt.executeUpdate(command);
        connection.close();
    }

    // Just hash the password to encrypt it. It is more secure if we add random salt before hashing.
    private String encrypt(String password) {
        MessageDigest crypto = null;
        try {
            crypto = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
            byte[] passHash = crypto.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return generatedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateRecord(Object data) throws SQLException {

    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        return null;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        return null;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {

    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {

    }

    @Override
    public List<String> getKeys() throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
        return null;
    }
}
