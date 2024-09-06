package se2203b.ipayroll;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author Abdelkader
 */
public class IPayrollController implements Initializable {
    @FXML
    private Menu aboutMenu;
    @FXML
    private MenuItem aboutusMenuItem;
    @FXML
    private MenuItem addProfileMenuItem;
    @FXML
    private MenuItem changePasswordMenuItem;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem deleteProfileMenuItem;
    @FXML
    private MenuItem federalTaxWothholdingsMenuItem;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu generateReportsMenu;
    @FXML
    private MenuItem localPayrollExpensesMenuItem;
    @FXML
    private MenuItem loginMenuItem;
    @FXML
    private MenuItem logoutMenuItem;
    @FXML
    private MenuBar mainMenu;
    @FXML
    private Menu manageEmployeeProfileMenu;
    @FXML
    private Menu manageLeavesMenu;
    @FXML
    private MenuItem modifyProfileMenuItem;
    @FXML
    private Menu printChecksMenu;
    @FXML
    private Menu userMenuItem;
    @FXML
    private Menu manageUserAccountsMenu;
    @FXML
    private Menu viewCompensationMenu;

    private Connection conn;
    private DataStore account;
    private String userAccountName;

    public void showAbout() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about-view.fxml"));
        Parent About = (Parent) fxmlLoader.load();
        Scene scene = new Scene(About);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("About Us");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }

    public void login() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("login-View.fxml"));

        // create the root node
        Parent Login = fxmlLoader.load();
        LoginController loginController = (LoginController) fxmlLoader.getController();
        loginController.setIPayrollController(this);
        loginController.setDataStore(new UserAccountTableAdapter(false), new EmployeeTableAdapter(false) );

        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(Login));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Login to iPAYROLL");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void changePassword() throws Exception {
        account = new UserAccountTableAdapter(false);
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("ChangePassword-view.fxml"));

        // create the root node
        Parent changePassword = fxmlLoader.load();
        ChangePasswordController changePasswordController = (ChangePasswordController) fxmlLoader.getController();
        changePasswordController.setIPayrollController(this);
        changePasswordController.setDataStore(account);

        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(changePassword));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Change Password");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void createUserAccount() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("createUserAccount-view.fxml"));
        // create the root node
        Parent newUser = fxmlLoader.load();
        CreateUserAccountController createUserAccountController = (CreateUserAccountController) fxmlLoader.getController();
        createUserAccountController.setIPayrollController(this);
        createUserAccountController.setDataStore(new UserAccountTableAdapter(false), new EmployeeTableAdapter(false) );
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(newUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Create User Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void addNewUserProfile() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("addNewEmployeeProfile-view.fxml"));
        // create the root node
        Parent aUser = fxmlLoader.load();
        AddNewEmployeeProfileController addNewEmployeeProfileController = (AddNewEmployeeProfileController) fxmlLoader.getController();
        addNewEmployeeProfileController.setIPayrollController(this);
        addNewEmployeeProfileController.setDataStore(new UserAccountTableAdapter(false), new EmployeeTableAdapter(false), new EarningTableAdapter(false), new EarningSourceTableAdapter(false), new DeductionTableAdapter(false), new DeductionSourceTableAdapter(false));
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(aUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Add New Employee Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void modifyEmployeeProfile() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("modifyEmployeeProfile-view.fxml"));
        // create the root node
        Parent aUser = fxmlLoader.load();
        ModifyEmployeeProfileController modifyEmployeeProfileController = (ModifyEmployeeProfileController) fxmlLoader.getController();
        modifyEmployeeProfileController.setIPayrollController(this);
        modifyEmployeeProfileController.setDataStore(new UserAccountTableAdapter(false), new EmployeeTableAdapter(false), new EarningTableAdapter(false), new EarningSourceTableAdapter(false), new DeductionTableAdapter(false), new DeductionSourceTableAdapter(false));
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(aUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Modify User Profile");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void deleteUserAccount() throws Exception {
        // load the fxml file (the UI elements)
        FXMLLoader fxmlLoader = new FXMLLoader(IPayrollController.class.getResource("deleteUserAccount-view.fxml"));
        // create the root node
        Parent aUser = fxmlLoader.load();
        DeleteUserAccountController deleteUserAccountController = (DeleteUserAccountController) fxmlLoader.getController();
        deleteUserAccountController.setIPayrollController(this);
        deleteUserAccountController.setDataStore(new UserAccountTableAdapter(false), new EmployeeTableAdapter(false));
        // create new stage
        Stage stage = new Stage();
        stage.setScene(new Scene(aUser));
        // add icon to the About window
        stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
        stage.setTitle("Delete User Account");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void logout() {
        disableMenuItems();
    }
    public void enableAdminControls() {
        // Off
        viewCompensationMenu.setDisable(true);
        manageLeavesMenu.setDisable(true);
        loginMenuItem.setDisable(true);

        // On
        fileMenu.setDisable(false);
        logoutMenuItem.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        manageEmployeeProfileMenu.setDisable(false);
        generateReportsMenu.setDisable(false);
        printChecksMenu.setDisable(false);
        manageUserAccountsMenu.setDisable(false);
        userMenuItem.setVisible(true);
    }
    public void enableEmployeeControls() {
        // Off
        manageEmployeeProfileMenu.setDisable(true);
        loginMenuItem.setDisable(true);
        generateReportsMenu.setDisable(true);
        manageUserAccountsMenu.setDisable(true);

        // On
        fileMenu.setDisable(false);
        logoutMenuItem.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        viewCompensationMenu.setDisable(false);
        manageLeavesMenu.setDisable(false);
        userMenuItem.setVisible(true);

    }
    public void disableMenuItems() {
        // Off
        manageEmployeeProfileMenu.setDisable(true);
        generateReportsMenu.setDisable(true);
        viewCompensationMenu.setDisable(true);
        printChecksMenu.setDisable(true);
        manageLeavesMenu.setDisable(true);
        logoutMenuItem.setDisable(true);
        userMenuItem.setVisible(false);
        manageUserAccountsMenu.setDisable(true);

        // On
        fileMenu.setDisable(false);
        mainMenu.setDisable(false);
        closeMenuItem.setDisable(false);
        loginMenuItem.setDisable(false);
    }
    // set the logged-in username into the menu item
    public void setUserFullname(String name) {
        userMenuItem.setText(name);
    }
    // save user account name
    public void setUserName(String userAccountName) {
        this.userAccountName = userAccountName;
    }
    public String getUserFullname() {
        return userMenuItem.getText();
    }
    public String getUserAccountName() {
        return userAccountName;
    }
    public void displayAlert(String msg) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IPayrollApplication.class.getResource("alert-View.fxml"));
            // create the root node
            Parent alertWindow = fxmlLoader.load();
            AlertController alertController = (AlertController) fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(alertWindow));
            stage.getIcons().add(new Image("file:src/main/resources/se2203b/ipayroll/WesternLogo.png"));
            alertController.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex1) {
            System.out.println("Error in Display Alert " + ex1);
        }
    }
    // Just hash the password to encrypt it. It is more secure if we add random salt before hashing.
    public String encrypt(String password) {
        MessageDigest crypto = null;
        try {
            crypto = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
            byte[] passHash = crypto.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< passHash.length ;i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return generatedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView face = new ImageView(new Image("file:src/main/resources/se2203b/ipayroll/UserIcon.png",20,20,true,true));
        userMenuItem.setGraphic(face);
        disableMenuItems();

        try {
            // Create a named constant for the URL
            // NOTE: This value is specific for Java DB
            String DB_URL = "jdbc:derby:iPAYROLLDB";
            // Create a connection to the database
            conn = DriverManager.getConnection(DB_URL);

            // Create the administrator data if it is not already in the database
            new AdministratorTableAdapter(false);

        } catch (SQLException ex) {
            displayAlert(ex.getMessage());
        }
    }

}
