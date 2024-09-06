package se2203b.ipayroll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteUserAccountController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField email;
    @FXML
    private TextField fullName;
    @FXML
    private Button deleteBtn;
    @FXML
    private ComboBox userName;

    IPayrollController iPayrollController;

    private DataStore userAccountTable;
    private DataStore employeeTable;
    private Employee employee = null;
    private UserAccount userAccount;

    private final ObservableList<String> usernamesList = FXCollections.observableArrayList();
    private final ObservableList<UserAccount> userAccountList = FXCollections.observableArrayList();

    public void setDataStore(DataStore account, DataStore profile) {
        userAccountTable = account;
        employeeTable = profile;
        buildData();
    }

    public void setIPayrollController(IPayrollController controller) {
        iPayrollController = controller;
    }

    public void buildData() {
        try {
            usernamesList.addAll(userAccountTable.getKeys());
        } catch (SQLException ex) {
            iPayrollController.displayAlert("User Accounts List: " + ex.getMessage());
        }
    }

    public void deleteAccount() {
        try {
            // Delete the link to the employee table first
            employee.setUserAccount(null);
            employeeTable.updateRecord(employee);
            // Then delete user account
            userAccountTable.deleteOneRecord(userAccount.getUserAccountName());

            // Get current stage reference
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            // Close stage
            stage.close();
        } catch (SQLException ex) {
            iPayrollController.displayAlert("Find User Account: " + ex.getMessage());
        }


    }

    // retrieve the selected profile and update the screen
    public void getAccount() {
        try {
            userAccount = (UserAccount) userAccountTable.findOneRecord(userName.getValue().toString());
            employee = (Employee) employeeTable.findOneRecord(userAccount);
            deleteBtn.setDisable(false);
            id.setText(String.valueOf(employee.getID()));
            fullName.setText(employee.getFullName());

        } catch (SQLException ex) {
            iPayrollController.displayAlert("Find Profile: " + ex.getMessage());
        }
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setItems(usernamesList);

    }
}
