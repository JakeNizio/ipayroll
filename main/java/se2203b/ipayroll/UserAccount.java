package se2203b.ipayroll;

import javafx.beans.property.*;

public class UserAccount {
    private StringProperty userAccountName;
    private StringProperty encryptedPassword;
    private StringProperty emailAddress;
    private StringProperty accountType;  // "admin" or "employee"

    public UserAccount() {
        this.userAccountName = new SimpleStringProperty();
        this.encryptedPassword = new SimpleStringProperty();
        this.emailAddress = new SimpleStringProperty();
        this.accountType = new SimpleStringProperty();
    }

    public UserAccount(String userAccountName, String encryptedPassword, String emailAddress, String accountType) {
        this.userAccountName = new SimpleStringProperty(userAccountName);
        this.encryptedPassword = new SimpleStringProperty(encryptedPassword);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.accountType = new SimpleStringProperty(accountType);
    }

    //set and get methods
    // userName property
    public void setUserAccountName(String userAccountName) {
        this.userAccountName.set(userAccountName);
    }
    public StringProperty userAccountNameProperty() {
        return this.userAccountName;
    }
    public String getUserAccountName() {
        return this.userAccountName.get();
    }

    // encryptedPassword property
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword.set(encryptedPassword);
    }
    public StringProperty encryptedPasswordProperty() {
        return this.encryptedPassword;
    }
    public String getEncryptedPassword() {
        return this.encryptedPassword.get();
    }

    // email address property
    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }
    public StringProperty emailAddressProperty() {
        return this.emailAddress;
    }
    public String getEmailAddress() {
        return this.emailAddress.get();
    }

    // account type property
    public void setAccountType(String accountType) {
        this.accountType.set(accountType);
    }
    public StringProperty accountTypeProperty() {
        return this.accountType;
    }
    public String getAccountType() {
        return this.accountType.get();
    }

}
