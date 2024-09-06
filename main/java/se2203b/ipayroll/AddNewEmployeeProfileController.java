/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class AddNewEmployeeProfileController implements Initializable {
    @FXML
    private TabPane tb;
    @FXML
    private TextField id;
    @FXML
    private TextField fullName;
    @FXML
    private TextField city;
    @FXML
    private TextField province;
    @FXML
    private TextField phone;
    @FXML
    private TextField SIN;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField martialStatus;
    @FXML
    private TextField jobName;
    @FXML
    private TextField skillCode;
    @FXML
    private DatePicker DOB;
    @FXML
    private DatePicker DOH;
    @FXML
    private DatePicker DOLP;
    @FXML
    Button cancelBtn;
    @FXML
    Button saveBtn;

    // Earnings Data
    @FXML
    ComboBox payTypeCombo, statementTypeCombo, earningSourceCombo;
    @FXML
    TextField workingHoursField, valueField1, ratePerHourField;
    @FXML
    CheckBox exemptCheck;
    @FXML
    DatePicker startDatePicker1, endDatePicker1;
    @FXML
    Button addEarningButton;
    @FXML
    TableView<Object> tableView1;
    @FXML
    TableColumn<Earning, String> earningSourceCol;
    @FXML
    TableColumn<Earning, Number> valueCol1, ratePerHourCol;
    @FXML
    TableColumn<Earning, Date> startDateCol1, endDateCol1;

    // Deductions Data
    @FXML
    ComboBox deductionSourceCombo;
    @FXML
    TextField valueField2, percentRemainingField, upperLimitField;
    @FXML
    DatePicker startDatePicker2, endDatePicker2;
    @FXML
    Button addDeductionButton;
    @FXML
    TableView<Object> tableView2;
    @FXML
    TableColumn<Deduction, String> deductionSourceCol;
    @FXML
    TableColumn<Deduction, Number> valueCol2, percentRemainingCol, upperLimitCol;
    @FXML
    TableColumn<Deduction, Date> startDateCol2, endDateCol2;

    ///////////////////
    private ObservableList<Object> earningData = FXCollections.observableArrayList();
    private ObservableList<Object> earningSourceData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionSourceData = FXCollections.observableArrayList();

    private DataStore userAccountAdapter;
    private DataStore employeeTableAdapter;
    private DataStore earningTableAdapter;
    private DataStore earningSourceTableAdapter;
    private DataStore deductionTableAdapter;
    private DataStore deductionSourceTableAdapter;
    private IPayrollController iPAYROLLController;


    public void setDataStore(DataStore account, DataStore profile, DataStore earning, DataStore earningSource, DataStore deduction, DataStore deductionSource) {
        userAccountAdapter = account;
        employeeTableAdapter = profile;
        earningTableAdapter = earning;
        earningSourceTableAdapter = earningSource;
        deductionTableAdapter = deduction;
        deductionSourceTableAdapter = deductionSource;
        buildData();
    }

    public void setIPayrollController(IPayrollController controller) {
        iPAYROLLController = controller;
    }

    public void buildData() {
        try {
            // gets earning sources from the database, extracts their names, and adds them to an observable list
            List<Object> eList = earningSourceTableAdapter.getAllRecords();
            List<String> eNameList = eList.stream().map(object -> ((EarningSource) object).getName()).collect(Collectors.toList());
            ObservableList<Object> eObservableArrayList = FXCollections.observableArrayList(eNameList);
            earningSourceData.addAll(eObservableArrayList);

            // gets deduction sources from the database, extracts their names, and adds them to an observable list
            List<Object> dList = deductionSourceTableAdapter.getAllRecords();
            List<String> dNameList = dList.stream().map(object -> ((DeductionSource) object).getName()).collect(Collectors.toList());
            ObservableList<Object> dObservableArrayList = FXCollections.observableArrayList(dNameList);
            earningSourceData.addAll(dObservableArrayList);
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    // adds an earning to the table
    public void addEarning() {
        EarningSource earningSource = new EarningSource();
        Boolean earningSourceExists = false;

        // checks if the earning source already exists and sets the earningSource object to an already existing code if it does
        for (int i = 0; i < earningSourceData.size(); i++) {
            if (earningSourceData.get(i).equals(earningSourceCombo.getValue().toString())) {
                earningSource.setName((String) earningSourceData.get(i));
                earningSource.setCode(String.valueOf(i+1));
                earningSourceExists = true;
            }
        }

        // sets the earning source with a new code if it didnt exist
        if (!earningSourceExists) {
            earningSource.setCode(String.valueOf(earningSourceData.size()+1));
            earningSource.setName(earningSourceCombo.getValue().toString());
            earningSourceData.add(earningSource.getName());
        }

        // builds the earning object
        Earning earning = new Earning();
        earning.setEarningSource(earningSource);

        // replaces blanks with zeros
        if (valueField1.getText().isBlank()) {valueField1.setText("0");}
        earning.setAmount(Double.parseDouble(valueField1.getText()));
        if (ratePerHourField.getText().isBlank()) {ratePerHourField.setText("0");}
        earning.setRatePerHour(Double.parseDouble(ratePerHourField.getText()));

        // sets the start and end dates
        if (startDatePicker1.getValue() != null) {
            earning.setStartDate(java.sql.Date.valueOf(startDatePicker1.getValue()));
        }
        if (endDatePicker1.getValue() != null) {
            earning.setEndDate(java.sql.Date.valueOf(endDatePicker1.getValue()));
        }

        // adds the new object to the earning data list
        earningData.add(earning);

        // resets the form inputs
        earningSourceCombo.valueProperty().set(null);
        valueField1.setText("");
        ratePerHourField.setText("");
        startDatePicker1.setValue(null);
        endDatePicker1.setValue(null);

        // populates the table
        tableView1.setItems(earningData);
    }

    public void addDeduction() {
        DeductionSource deductionSource = new DeductionSource();
        Boolean deductionSourceExists = false;

        // checks if the deduction source already exists and sets the deduction source object to an already existing code if it does
        for (int i = 0; i < deductionSourceData.size(); i++) {
            if (deductionSourceData.get(i).equals(deductionSourceCombo.getValue().toString())) {
                deductionSource.setName((String) deductionSourceData.get(i));
                deductionSource.setCode(String.valueOf(i+1));
                deductionSourceExists = true;
            }
        }

        // sets the deduction source with a new code if it didnt exist
        if (!deductionSourceExists) {
            deductionSource.setCode(String.valueOf(deductionSourceData.size()+1));
            deductionSource.setName(deductionSourceCombo.getValue().toString());
            deductionSourceData.add(deductionSource.getName());
        }

        // builds the deduction object
        Deduction deduction = new Deduction();
        deduction.setDeductionSource(deductionSource);

        // replaces blanks with zeros
        if (valueField2.getText().isBlank()) {valueField2.setText("0");}
        deduction.setAmount(Double.parseDouble(valueField2.getText()));
        if (percentRemainingField.getText().isBlank()) {percentRemainingField.setText("0");}
        deduction.setPercentageRemaining(Double.parseDouble(percentRemainingField.getText()));
        if (upperLimitField.getText().isBlank()) {upperLimitField.setText("0");}
        deduction.setUpperLimit(Double.parseDouble(upperLimitField.getText()));

        // sets the dates
        if (startDatePicker2.getValue() != null) {
            deduction.setStartDate(java.sql.Date.valueOf(startDatePicker2.getValue()));
        }
        if (endDatePicker2.getValue() != null) {
            deduction.setEndDate(java.sql.Date.valueOf(endDatePicker2.getValue()));
        }

        // adds the new deduction object to the deduction list
        deductionData.add(deduction);

        // resets the form inputs
        deductionSourceCombo.valueProperty().set(null);
        valueField2.setText("");
        percentRemainingField.setText("");
        upperLimitField.setText("");
        startDatePicker2.setValue(null);
        endDatePicker2.setValue(null);

        // populates the table
        tableView2.setItems(deductionData);
    }

    @FXML
    public void save() {
        try {
            UserAccount account = new UserAccount();
            Employee employee = new Employee();
            employee.setID(this.id.getText());
            employee.setFullName(this.fullName.getText());
            employee.setCity(this.city.getText());
            employee.setProvince(this.province.getText());
            employee.setPostalCode(this.postalCode.getText());
            employee.setPhone(this.phone.getText());
            employee.setSIN(this.SIN.getText());
            employee.setMartialStatus(this.martialStatus.getText());
            employee.setJobName(this.jobName.getText());
            employee.setSkillCode(this.skillCode.getText());
            employee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            employee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            employee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            employee.setUserAccount(account);

            employee.setPayType(this.payTypeCombo.getValue().toString());
            employee.setWorkHours(Double.parseDouble(this.workingHoursField.getText()));
            employee.setEarningStatementType(this.statementTypeCombo.getValue().toString());
            employee.setExempt(this.exemptCheck.isSelected());

            employeeTableAdapter.addNewRecord(employee);


            // loops through each earning in the earning data list, first adding the earning source to the db if it doesnt exist and then adding the earning
            Random random = new Random();
            for (int i = 0; i < earningData.size(); i++) {
                // adds the earning source if it doesnt already exist
                EarningSource es = (EarningSource) earningSourceTableAdapter.findOneRecord(((Earning) earningData.get(i)).getEarningSource());
                if (es == null) {
                    earningSourceTableAdapter.addNewRecord(((Earning) earningData.get(i)).getEarningSource());
                }

                // adds the earning to the db
                ((Earning) earningData.get(i)).setEmployee(employee);
                ((Earning) earningData.get(i)).setEarningId(String.format("%09d", random.nextInt(1000000000)));
                earningTableAdapter.addNewRecord(earningData.get(i));
            }

            // loops through each earning in the earning data list, first adding the earning source to the db if it doesnt exist and then adding the earning
            for (int i = 0; i < deductionData.size(); i++) {
                // adds the deduction source if it doesnt already exist
                DeductionSource es = (DeductionSource) deductionSourceTableAdapter.findOneRecord(((Deduction) deductionData.get(i)).getDeductionSource());
                if (es == null) {
                    deductionSourceTableAdapter.addNewRecord(((Deduction) deductionData.get(i)).getDeductionSource());
                }

                // adds the deduction to the db
                ((Deduction) deductionData.get(i)).setEmployee(employee);
                ((Deduction) deductionData.get(i)).setDeductionId(String.format("%09d", random.nextInt(1000000000)));
                deductionTableAdapter.addNewRecord(deductionData.get(i));
            }

        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
 
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets up the earnings table
        earningSourceCol.setCellValueFactory(cellData -> cellData.getValue().getEarningSource().nameProperty());
        valueCol1.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        ratePerHourCol.setCellValueFactory(cellData -> cellData.getValue().ratePerHourProperty());
        startDateCol1.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateCol1.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());

        // sets up the deductions table
        deductionSourceCol.setCellValueFactory(cellData -> cellData.getValue().getDeductionSource().nameProperty());
        valueCol2.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        percentRemainingCol.setCellValueFactory(cellData -> cellData.getValue().percentageRemainingProperty());
        upperLimitCol.setCellValueFactory(cellData -> cellData.getValue().upperLimitProperty());
        startDateCol2.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateCol2.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());

        // fills the combo boxes
        earningSourceCombo.setItems(earningSourceData);
        deductionSourceCombo.setItems(deductionSourceData);

        List<String> payTypeList = Arrays.asList("Hourly with card", "Hourly without card", "Salaried");
        ObservableList<Object> observablePayTypeList = FXCollections.observableArrayList(payTypeList);
        payTypeCombo.setItems(observablePayTypeList);

        List<String> statementTypeList = Arrays.asList("Weekly", "Bi-Weekly", "Semi-monthly", "Monthly", "Special");
        ObservableList<Object> observableStatementTypeList = FXCollections.observableArrayList(statementTypeList);
        statementTypeCombo.setItems(observableStatementTypeList);
    }

}
