package se2203b.ipayroll;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Abdelkader Ouda
 */
public interface DataStore {
    // Add new data record
    public void addNewRecord(Object data) throws SQLException;
    // Update the existing record
    public void updateRecord(Object data) throws SQLException;
    // Get one specific record
    public Object findOneRecord(String key) throws SQLException;
    // Get one specific record by referenced table data
    public Object findOneRecord(Object referencedObject) throws SQLException;
    // Delete one specific record
    public void deleteOneRecord(String key) throws SQLException;
    // Delete a group of specific records by referenced table data
    public void deleteRecords(Object referencedObject) throws SQLException;
    // Get list of all data keys
    public List<String> getKeys() throws SQLException;
    // Get a list of all data records
    public List<Object> getAllRecords() throws SQLException;
    // Get a list of specific records by referenced table data
    public List<Object> getAllRecords(Object referencedObject) throws SQLException;
}