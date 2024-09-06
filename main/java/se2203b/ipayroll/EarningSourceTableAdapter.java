package se2203b.ipayroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EarningSourceTableAdapter implements DataStore {

    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public EarningSourceTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE EarningSource");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            }
        }

        try {
            // Create the table
            // code, name
            stmt.execute("CREATE TABLE EarningSource ("
                    + "code VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "name VARCHAR(20)"
                    + ")");
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        EarningSource earningSource = (EarningSource) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        try {
            String command = "INSERT INTO EarningSource (code, name) "
                    + "VALUES ('"
                    + earningSource.getCode() + "', '"
                    + earningSource.getName() + "'"
                    +  ")";
            stmt.executeUpdate(command);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT code FROM EarningSource";

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
        EarningSource earningSource = new EarningSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM EarningSource WHERE code = '" + key + "'";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            earningSource.setCode(rs.getString("code"));
            earningSource.setName(rs.getString("name"));
            return earningSource;
        }
        connection.close();
        return null;
    }

    @Override
    public Object findOneRecord(Object data) throws SQLException {
        EarningSource earningSource = new EarningSource();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM EarningSource WHERE code = '" + ((EarningSource) data).getCode() + "'";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            earningSource.setCode(rs.getString("code"));
            earningSource.setName(rs.getString("name"));
            return earningSource;
        }
        connection.close();
        return null;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet result;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM EarningSource";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                EarningSource earningSource = new EarningSource();
                earningSource.setCode(result.getString("code"));
                earningSource.setName(result.getString("name"));

                list.add(earningSource);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
        return null;
    }
}
