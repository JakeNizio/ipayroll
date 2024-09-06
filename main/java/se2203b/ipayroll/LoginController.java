package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controls the login screen
 */
public class LoginController implements Initializable {
    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label errorMsg;

    IPayrollController iPayrollController;

    private DataStore userAccountTable;
    private DataStore employeeTable;

    public void setDataStore(DataStore accountAdapter, DataStore profile) {
        userAccountTable = accountAdapter;
        employeeTable = profile;
    }

    /**
     * Check authorization credentials.
     */
    public void authorize() {
        errorMsg.setText("");
        try {
            // Get the user account information from database
            UserAccount account = (UserAccount) userAccountTable.findOneRecord(user.getText());
            if (account.getUserAccountName() == null) {
                // Account not found
                errorMsg.setText("Incorrect username");
            } else {
                // Account exist, now check the password
                String encryptedPassword = iPayrollController.encrypt(password.getText());
                String retrievedEncryptedPassword = account.getEncryptedPassword();
                if (encryptedPassword.equals(retrievedEncryptedPassword)) {
                    // enable controls based on the account type
                    authenticated(account, account.getAccountType());
                } else {
                    // wrong password
                    errorMsg.setText("Wrong password");
                }
            }
        } catch (SQLException ex) {
            iPayrollController.displayAlert("ERROR: " + ex.getMessage());
        }


    }

    /**
     * Will show the main application screen.
     */
    public void authenticated(UserAccount userAccount, String privilege) {
        iPayrollController.setUserName(userAccount.getUserAccountName());
        if (privilege.equals("admin")) {
            // show admin menu options
            iPayrollController.setUserFullname("Admin");
            iPayrollController.enableAdminControls();
        } else {
            // get employee's full name
            try {
                Employee employee = (Employee) employeeTable.findOneRecord(userAccount);
                iPayrollController.setUserFullname(employee.getFullName());
                // show non admin menu options
                iPayrollController.enableEmployeeControls();
            } catch (SQLException e) {
                iPayrollController.displayAlert("ERROR-Login: " + e.getMessage());
            }
        }

        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    public void clearErrorMsg() {
        errorMsg.setText("");
    }

    public void setIPayrollController(IPayrollController controller) {
        iPayrollController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMsg.setText("");
    }
}