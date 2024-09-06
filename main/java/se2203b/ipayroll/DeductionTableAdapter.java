package se2203b.ipayroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeductionTableAdapter implements DataStore {

    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public DeductionTableAdapter(Boolean reset) throws SQLException { // !!! code not configured for deduction
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Deduction");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            }
        }

        try {
            // Create the table
            // earningId, amount, ratePerHour, startDate, endDate, earningSource, employee
            stmt.execute("CREATE TABLE Deduction ("
                    + "deductionId VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "amount DOUBLE, "
                    + "percentageRemaining DOUBLE, "
                    + "upperLimit DOUBLE, "
                    + "startDate DATE,"
                    + "endDate DATE,"
                    + "deductionSource VARCHAR(9) REFERENCES DeductionSource(code),"
                    + "employee VARCHAR(9) REFERENCES Employee(id)"
                    + ")");
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        try {
            String command = "INSERT INTO DEDUCTION (deductionId, amount, percentageRemaining, upperLimit, startDate, endDate, deductionSource, employee) "
                    + "VALUES ('"
                    + deduction.getDeductionId() + "', "
                    + deduction.getAmount() + ", "
                    + deduction.getPercentageRemaining() + ", "
                    + deduction.getUpperLimit() + ", "
                    + ((deduction.getStartDate() != null) ? "'" + deduction.getStartDate() + "'" : null) + ", "
                    + ((deduction.getEndDate() != null) ? "'" + deduction.getEndDate() + "'" : null) + ", '"
                    + deduction.getDeductionSource().getCode() + "', '"
                    + deduction.getEmployee().getID() + "'"
                    +  ")";
            stmt.executeUpdate(command);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        // Get the user account name if exists
        String command = null;

        command = "UPDATE Deduction "
                + "SET amount = " + deduction.getAmount() + ", "
                + "percentageRemaining = " + deduction.getPercentageRemaining() + ", "
                + "upperLimit = " + deduction.getUpperLimit() + ", "
                + "startDate = " + ((deduction.getStartDate() != null) ? "'" + deduction.getStartDate() + "'" : null) + ", "
                + "endDate = " + ((deduction.getEndDate() != null) ? "'" + deduction.getEndDate() + "'" : null) + ", "
                + "deductionSource = '" + deduction.getDeductionSource().getCode() + "' "
                + "WHERE deductionId = '" + deduction.getDeductionId() + "'";

        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Deduction WHERE deductionId = '" + key + "'");
        connection.close();
    }

    @Override
    public void deleteRecords(Object data) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        // Create a Statement object
        Statement stmt = connection.createStatement();
        // Create a string with a SELECT statement
        String command = "DELETE FROM Deduction WHERE employee = '" + ((Employee) data).getID() + "'";
        // Execute the statement and return the result
        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT deductionId FROM Deduction";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            list.add(rs.getString(1));
        }
        connection.close();
        return list;
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        return null;
    }

    @Override
    public Object findOneRecord(Object userAccount) throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords(Object data) throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet result;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM Deduction WHERE EMPLOYEE = '" + ((Employee) data).getID() + "'";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                Deduction deduction = new Deduction();
                deduction.setDeductionId(result.getString("deductionId"));
                deduction.setAmount(result.getDouble("amount"));
                deduction.setPercentageRemaining(result.getDouble("percentageRemaining"));
                deduction.setUpperLimit(result.getDouble("upperLimit"));
                deduction.setStartDate(result.getDate("startDate"));
                deduction.setEndDate(result.getDate("endDate"));
                DeductionSource ds = new DeductionSource();
                ds.setCode(result.getString("deductionSource"));
                deduction.setDeductionSource(ds);
                deduction.setEmployee((Employee) data);

                list.add(deduction);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
