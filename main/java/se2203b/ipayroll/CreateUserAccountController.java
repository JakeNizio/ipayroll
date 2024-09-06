package se2203b.ipayroll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserAccountController implements Initializable {
    @FXML
    private Button cancelBtn;

    @FXML
    private TextField email;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField fullName;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField userName;
    @FXML
    private ComboBox<String> id;

    private String loggedInUser;
    private IPayrollController iPayrollController;
    private DataStore userAccountTable;
    private DataStore employeeTable;
    private Employee employee;
    final ObservableList<String> data = FXCollections.observableArrayList();

    public void setDataStore(DataStore account, DataStore profile) {
        userAccountTable = account;
        employeeTable = profile;

        buildData();
    }

    public void setIPayrollController(IPayrollController controller) {
        iPayrollController = controller;
    }

    @FXML
    public void getProfile() {
        try {
            employee = (Employee) employeeTable.findOneRecord(this.id.getValue().toString());
        } catch (SQLException ex) {
            displayAlert("ERROR - employeeAdapter: " + ex.getMessage());
        }
        this.fullName.setText(employee.getFullName());
    }

    public void createAccount() {
        if (validateForm()) {
            // Get current stage reference
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            // Close stage
            stage.close();
        }

    }

    private boolean validateForm() {
        errorMsg.setText("");
        if (!fullName.getText().isEmpty()) {
            if (!userName.getText().isEmpty()) {
                try {
                    UserAccount userAccount = (UserAccount) userAccountTable.findOneRecord(userName.getText());
                    if (!(userAccount.getUserAccountName() == null)) {
                        // username is already exist for other account
                        errorMsg.setText("This username is not available");
                    } else {
                        if (!password1.getText().isEmpty() && (password1.getText().equals(password2.getText()))) {
                            // Encrypt the password
                            String encryptedPassword = iPayrollController.encrypt(password1.getText());
                            // save the new account and the user profile and then exit
                            UserAccount account = new UserAccount(userName.getText(), encryptedPassword,
                                    email.getText(), "employee");
                            try {
                                // create new account
                                userAccountTable.addNewRecord(account);
                                // assign the new account to the employee
                                employee.setUserAccount(account);
                                // update the employee profile
                                employeeTable.updateRecord(employee);
                                return true;
                            } catch (SQLException ex) {
                                iPayrollController.displayAlert("Insert User Account: " + ex.getMessage());
                            }
                        } else {
                            // wrong new password
                            errorMsg.setText("The new passwords do not match");
                        }
                    }
                } catch (SQLException ex) {
                    iPayrollController.displayAlert("Find User Account: " + ex.getMessage());
                }
            } else {
                // empty username
                errorMsg.setText("Username can not be empty");
            }
        } else {
            // empty user full name
            errorMsg.setText("User Full name can not be empty");
        }
        return false;
    }

    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        // Close stage
        stage.close();
    }

    private void displayAlert(String msg) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("alert-View.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/iPAYROLL/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    public void clearErrorMsg() {
        errorMsg.setText("");
    }

    public void buildData() {
        try {
            data.addAll(employeeTable.getKeys());
        } catch (SQLException ex) {
            displayAlert("ERROR - employeeAdapter: " + ex.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setItems(data);
    }
}
