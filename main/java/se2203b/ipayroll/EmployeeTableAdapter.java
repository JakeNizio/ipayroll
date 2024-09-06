/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Abdelkader
 */
public class EmployeeTableAdapter implements DataStore {

    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public EmployeeTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Employee");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            }
        }

        try {
            // Create the table
            stmt.execute("CREATE TABLE Employee ("
                    + "id VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "fullName VARCHAR(50), "
                    + "city VARCHAR(20), "
                    + "province VARCHAR(20),"
                    + "postalCode VARCHAR(20),"
                    + "phone VARCHAR(20),"
                    + "SIN VARCHAR(20),"
                    + "martialStatus VARCHAR(20),"
                    + "jobName VARCHAR(50),"
                    + "skillCode VARCHAR(20),"
                    + "workHours DOUBLE,"
                    + "payType VARCHAR(50),"
                    + "exempt INT,"
                    + "DOB DATE,"
                    + "DOH DATE,"
                    + "DOR DATE,"
                    + "DOLR DATE,"
                    + "DOLP DATE,"
                    + "DOLPC DATE,"
                    + "DOLCC DATE,"
                    + "DOT DATE,"
                    + "vacationAccrualRate DOUBLE,"
                    + "vacationAccrued DOUBLE,"
                    + "vacationUsed DOUBLE,"
                    + "sickAccrualRate DOUBLE,"
                    + "sickAccrued DOUBLE,"
                    + "sickUsed DOUBLE,"
                    + "earningStatementType VARCHAR(20),"
                    + "userAccount VARCHAR(30) REFERENCES UserAccount(userAccountName)"
                    + ")");
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        Employee employee = (Employee) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        try {
            String command = "INSERT INTO Employee (id, fullName, city, province, postalCode, phone, SIN, martialStatus, jobName, skillCode, DOB, DOH, DOLP, payType, workHours, earningStatementType, exempt) "
                    + "VALUES ('"
                    + employee.getID() + "', '"
                    + employee.getFullName() + "', '"
                    + employee.getCity() + "', '"
                    + employee.getProvince() + "', '"
                    + employee.getPostalCode() + "', '"
                    + employee.getPhone() + "', '"
                    + employee.getSIN() + "', '"
                    + employee.getMartialStatus() + "', '"
                    + employee.getJobName() + "', '"
                    + employee.getSkillCode() + "', '"
                    + employee.getDOB() + "', '"
                    + employee.getDOH() + "', '"
                    + employee.getDOLP() + "', '"
                    + employee.getPayType() + "', "
                    + employee.getWorkHours() + ", '"
                    + employee.getEarningStatementType() + "', "
                    + (employee.getExempt()? 1 : 0)
                    +  " )";
            stmt.executeUpdate(command);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Employee employee = (Employee) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        // Get the user account name if exists
        String command = null;

        command = "UPDATE Employee "
                + "SET fullName = '" + employee.getFullName() + "', "
                + "city = '" + employee.getCity() + "', "
                + "province = '" + employee.getProvince() + "', "
                + "postalCode = '" + employee.getPostalCode() + "', "
                + "phone = '" + employee.getPhone() + "', "
                + "SIN = '" + employee.getSIN() + "', "
                + "martialStatus = '" + employee.getMartialStatus() + "', "
                + "jobName = '" + employee.getJobName() + "', "
                + "skillCode = '" + employee.getSkillCode() + "', "
                + "DOB = '" + employee.getDOB() + "', "
                + "DOH = '" + employee.getDOH() + "', "
                + "DOLP = '" + employee.getDOLP() + "', "

                + "payType ='" + employee.getPayType() + "', "
                + "workHours = " + employee.getWorkHours() + ", "
                + "earningStatementType = '" + employee.getEarningStatementType() + "', "
                + "exempt = " + (employee.getExempt()? 1 : 0) + ", "

                + "userAccount = " + (employee.getUserAccount().getUserAccountName() == null? null : "'" + employee.getUserAccount().getUserAccountName() + "' ")

                + " WHERE id = "
                + "'" + employee.getID() + "'";

        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Employee WHERE id = '" + key + "'");
        connection.close();
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
        String command = "SELECT id FROM Employee";

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
        Employee profile = new Employee();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM Employee WHERE id = '" + key + "'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            profile.setID(rs.getString("id"));
            profile.setFullName(rs.getString("fullName"));
            profile.setCity(rs.getString("city"));
            profile.setProvince(rs.getString("province"));
            profile.setPhone(rs.getString("phone"));
            profile.setPostalCode(rs.getString("postalCode"));
            profile.setSIN(rs.getString("SIN"));
            profile.setMartialStatus(rs.getString("martialStatus"));
            profile.setJobName(rs.getString("jobName"));
            profile.setSkillCode(rs.getString("skillCode"));
            profile.setDOB(rs.getDate("DOB"));
            profile.setDOH(rs.getDate("DOH"));
            profile.setDOLP(rs.getDate("DOLP"));
            profile.setExempt(rs.getBoolean("Exempt"));
            profile.setWorkHours(rs.getDouble("workHours"));
            profile.setPayType(rs.getString("payType"));
            profile.setEarningStatementType(rs.getString("earningStatementType"));
            // we need also to keep a reference to the userAccount object by saving the user account name
            if (rs.getString("userAccount") != null) {
                UserAccount account = new UserAccount(rs.getString("userAccount"), "", "", "");
                profile.setUserAccount(account);
            }
        }
        connection.close();
        return profile;
    }

    @Override
    public Object findOneRecord(Object userAccount) throws SQLException {
        Employee profile = new Employee();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM Employee WHERE userAccount = '" + ((UserAccount) userAccount).getUserAccountName() + "'";
        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            profile.setID(rs.getString("id"));
            profile.setFullName(rs.getString("fullName"));
            profile.setCity(rs.getString("city"));
            profile.setProvince(rs.getString("province"));
            profile.setPhone(rs.getString("phone"));
            profile.setPostalCode(rs.getString("postalCode"));
            profile.setSIN(rs.getString("SIN"));
            profile.setMartialStatus(rs.getString("martialStatus"));
            profile.setJobName(rs.getString("jobName"));
            profile.setSkillCode(rs.getString("skillCode"));
            profile.setDOB(rs.getDate("DOB"));
            profile.setDOH(rs.getDate("DOH"));
            profile.setDOLP(rs.getDate("DOLP"));
            profile.setExempt(rs.getBoolean("exempt"));
            profile.setWorkHours(rs.getDouble("workHours"));
        }
        connection.close();
        return profile;
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
            String command = "SELECT * FROM Employee";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                Employee employee = new Employee();
                employee.setID(result.getString("id"));
                employee.setFullName(result.getString("fullName"));
                employee.setCity(result.getString("city"));
                employee.setProvince(result.getString("province"));
                employee.setPhone(result.getString("phone"));
                employee.setPostalCode(result.getString("postalCode"));
                employee.setSIN(result.getString("SIN"));
                employee.setMartialStatus(result.getString("martialStatus"));
                employee.setJobName(result.getString("jobName"));
                employee.setSkillCode(result.getString("skillCode"));
                employee.setDOB(result.getDate("DOB"));
                employee.setDOH(result.getDate("DOH"));
                employee.setDOLP(result.getDate("DOLP"));
                employee.setExempt(result.getBoolean("exempt"));
                employee.setWorkHours(result.getDouble("workHours"));

                list.add(employee);
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
