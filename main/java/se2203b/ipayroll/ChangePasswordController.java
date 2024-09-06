package se2203b.ipayroll;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField newPassword1;

    @FXML
    private TextField newPassword2;

    @FXML
    private TextField oldPassword;

    @FXML
    private Button saveBtn;

    @FXML
    private Label username;

    @FXML
    private Label errorMsg;

    private String loggedInUser, userAccountName;
    IPayrollController iPayrollController;

    private DataStore userAccountTable;

    // To get the data store names from the caller
    public void setDataStore(DataStore accountAdapter) {
        userAccountTable = accountAdapter;
    }


    public void setIPayrollController(IPayrollController controller) {
        iPayrollController = controller;
        loggedInUser = controller.getUserFullname();
        userAccountName = controller.getUserAccountName();
        username.setText("Change password for " + loggedInUser);
    }

    public void changePassword() {
        errorMsg.setText("");
        try {
            // Get the user account information from database
            UserAccount account = (UserAccount) userAccountTable.findOneRecord(userAccountName);
            // check the old password
            String encryptedPassword = iPayrollController.encrypt(oldPassword.getText());
            String retrievedEncryptedPassword = account.getEncryptedPassword();
            if (encryptedPassword.equals(retrievedEncryptedPassword)) {
                // check if the two new password are identical
                if (newPassword1.getText().equals(newPassword2.getText())) {
                    // Encrypt the new password
                    String encryptedNewPassword = iPayrollController.encrypt(newPassword1.getText());

                    // save the new password then exit and logout
                    account.setEncryptedPassword(encryptedNewPassword);
                    try {
                        userAccountTable.updateRecord(account);

                        // Get current stage reference
                        Stage stage = (Stage) cancelBtn.getScene().getWindow();
                        // Close stage
                        stage.close();
                        iPayrollController.logout();
                    } catch (SQLException e) {
                        iPayrollController.displayAlert("Update User Account: " + e.getMessage());
                    }
                } else {
                    // wrong new password
                    errorMsg.setText("The new passwords do not match");
                }
            } else {
                // wrong password
                errorMsg.setText("Wrong old password");
            }
        } catch (SQLException ex) {
            iPayrollController.displayAlert("Find User Account: " + ex.getMessage());
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
