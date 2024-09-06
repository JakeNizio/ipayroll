/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Abdelkader
 */
public class ModifyEmployeeProfileController implements Initializable {

    @FXML
    private TabPane tb;
    @FXML
    private ComboBox id;
    @FXML
    private TextField fullName, city, province, phone, SIN, postalCode,
            martialStatus, jobName, skillCode;
    @FXML
    private DatePicker DOB, DOH, DOLP;
    @FXML
    Button cancelBtn, saveBtn;

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
    Button addEarningButton, removeEarningButton;
    @FXML
    TableView<Object> tableView1;
    @FXML
    TableColumn<Earning, String> earningSourceCol;
    @FXML
    TableColumn<Earning, Number> valueCol1, ratePerHourCol;
    @FXML
    TableColumn<Earning, java.sql.Date> startDateCol1, endDateCol1;


    // Deductions Data
    @FXML
    ComboBox deductionSourceCombo;
    @FXML
    TextField valueField2, percentRemainingField, upperLimitField;
    @FXML
    DatePicker startDatePicker2, endDatePicker2;
    @FXML
    Button addDeductionButton, removeDeductionButton;
    @FXML
    TableView<Object> tableView2;
    @FXML
    TableColumn<Deduction, String> deductionSourceCol;
    @FXML
    TableColumn<Deduction, Number> valueCol2, percentRemainingCol, upperLimitCol;
    @FXML
    TableColumn<Deduction, java.sql.Date> startDateCol2, endDateCol2;

    /******************/

    private ObservableList<Object> earningData = FXCollections.observableArrayList();
    private ObservableList<Object> earningSourceData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionData = FXCollections.observableArrayList();
    private ObservableList<Object> deductionSourceData = FXCollections.observableArrayList();

    private DataStore userAccountTable;
    private UserAccount userAccount;
    private DataStore employeeTable;
    private DataStore earningTableAdapter;
    private DataStore earningSourceTableAdapter;
    private DataStore deductionTableAdapter;
    private DataStore deductionSourceTableAdapter;
    private Employee employee = null;
    private IPayrollController iPAYROLLController;

    final ObservableList<String> data = FXCollections.observableArrayList();

    public void setDataStore(DataStore account, DataStore profile, DataStore earning, DataStore earningSource, DataStore deduction, DataStore deductionSource) {
        userAccountTable = account;
        employeeTable = profile;
        earningTableAdapter = earning;
        earningSourceTableAdapter = earningSource;
        deductionTableAdapter = deduction;
        deductionSourceTableAdapter = deductionSource;
        buildData();
    }

    public void setIPayrollController(IPayrollController controller) {
        iPAYROLLController = controller;
    }


    @FXML
    public void getProfile() {
        try {
            employee = (Employee) employeeTable.findOneRecord(this.id.getValue().toString());
            this.fullName.setText(employee.getFullName());
            this.city.setText(employee.getCity());
            this.province.setText(employee.getProvince());
            this.phone.setText(employee.getPhone());
            this.postalCode.setText(employee.getPhone());
            this.SIN.setText(employee.getSIN());
            this.martialStatus.setText(employee.getMartialStatus());
            this.jobName.setText(employee.getJobName());
            this.skillCode.setText(employee.getSkillCode());
            Date utilDOB = new Date(employee.getDOB().getTime());
            this.DOB.setValue(utilDOB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOH = new Date(employee.getDOH().getTime());
            this.DOH.setValue(utilDOH.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            Date utilDOLP = new Date(employee.getDOLP().getTime());
            this.DOLP.setValue(utilDOLP.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.exemptCheck.setSelected(employee.getExempt());
            this.payTypeCombo.valueProperty().set(employee.getPayType());
            this.workingHoursField.setText(employee.getWorkHours().toString());
            this.statementTypeCombo.valueProperty().set(employee.getEarningStatementType());


            userAccount = (UserAccount) userAccountTable.findOneRecord(employee.getUserAccount().getUserAccountName());

            // get and load employee specific earnings
            List<Object> eList = earningTableAdapter.getAllRecords(employee);
            List<Object> eCompleteList = eList.stream().map(earning -> completeEarning(((Earning) earning))).collect(Collectors.toList());;
            ObservableList<Object> eObservableArrayList = FXCollections.observableArrayList(eCompleteList);
            earningData.clear();
            earningData.addAll(eObservableArrayList);

            // get and load employee specific deductions
            List<Object> dList = deductionTableAdapter.getAllRecords(employee);
            List<Object> dCompleteList = dList.stream().map(deduction -> completeDeduction(((Deduction) deduction))).collect(Collectors.toList());;
            ObservableList<Object> dObservableArrayList = FXCollections.observableArrayList(dCompleteList);
            deductionData.clear();
            deductionData.addAll(dObservableArrayList);

            tableView1.setItems(earningData);
            tableView2.setItems(deductionData);

        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    // completes earnings grabbed from the database by fetching additonal earningSource data
    public Earning completeEarning(Earning earning) {
        try {
            EarningSource e = (EarningSource) earningSourceTableAdapter.findOneRecord(earning.getEarningSource().getCode());
            earning.setEarningSource(e);
            return earning;
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
        return null;
    }

    // completes deductions grabbed from the database by fetching additonal deductionSource data
    public Deduction completeDeduction(Deduction deduction) {
        try {
            DeductionSource d = (DeductionSource) deductionSourceTableAdapter.findOneRecord(deduction.getDeductionSource().getCode());
            deduction.setDeductionSource(d);
            return deduction;
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
        return null;
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

        // initialize the list of earning sources first, then pull from them or add a new one
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

    // removes earning from db then the list
    public void removeEarning() {
        int index = tableView1.getSelectionModel().getSelectedIndex();
        String key = ((Earning) earningData.get(index)).getEarningId();
        try {
            if (key != null) {
                earningTableAdapter.deleteOneRecord(key);
            }
            earningData.remove(index);
            tableView1.refresh();
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    // removes deduction from db then the list
    public void removeDeduction() {
        //delete from db, then delete from list
        int index = tableView2.getSelectionModel().getSelectedIndex();
        String key = ((Deduction) deductionData.get(index)).getDeductionId();
        try {
            if (key != null) {
               deductionTableAdapter.deleteOneRecord(key);
            }
            deductionData.remove(index);
            tableView2.refresh();
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }

    @FXML
    public void update() {
        try {
            Employee oneEmployee = new Employee();
            oneEmployee.setID(employee.getID());
            oneEmployee.setFullName(this.fullName.getText());
            oneEmployee.setCity(this.city.getText());
            oneEmployee.setProvince(this.province.getText());
            oneEmployee.setPostalCode(this.postalCode.getText());
            oneEmployee.setPhone(this.phone.getText());
            oneEmployee.setSIN(this.SIN.getText());
            oneEmployee.setMartialStatus(this.martialStatus.getText());
            oneEmployee.setJobName(this.jobName.getText());
            oneEmployee.setSkillCode(this.skillCode.getText());
            oneEmployee.setDOB(java.sql.Date.valueOf(this.DOB.getValue()));
            oneEmployee.setDOH(java.sql.Date.valueOf(this.DOH.getValue()));
            oneEmployee.setDOLP(java.sql.Date.valueOf(this.DOLP.getValue()));
            oneEmployee.setExempt(this.exemptCheck.isSelected());
            oneEmployee.setWorkHours(Double.parseDouble(this.workingHoursField.getText()));
            oneEmployee.setEarningStatementType(this.statementTypeCombo.getValue().toString());
            oneEmployee.setPayType(this.payTypeCombo.getValue().toString());

            oneEmployee.setUserAccount(userAccount);

            employeeTable.updateRecord(oneEmployee);

            Random random = new Random();

            // loops through each earning, adding the earning source first if it doesnt exist, then updates old earnings and adds new ones
            for (int i = 0; i < earningData.size(); i++) {
                EarningSource es = (EarningSource) earningSourceTableAdapter.findOneRecord(((Earning) earningData.get(i)).getEarningSource());
                if (es == null) {
                    earningSourceTableAdapter.addNewRecord(((Earning) earningData.get(i)).getEarningSource());
                }
                if (((Earning) earningData.get(i)).getEarningId() == null) {
                    ((Earning) earningData.get(i)).setEmployee(employee);
                    ((Earning) earningData.get(i)).setEarningId(String.format("%09d", random.nextInt(1000000000)));
                    earningTableAdapter.addNewRecord(earningData.get(i));
                } else {
                    earningTableAdapter.updateRecord(earningData.get(i));
                }
            }

            // loop through each deduction, adding the deduction source first if it doesnt exist, then updates old deductions and adds new ones
            for (int i = 0; i < deductionData.size(); i++) {
                DeductionSource es = (DeductionSource) deductionSourceTableAdapter.findOneRecord(((Deduction) deductionData.get(i)).getDeductionSource());
                if (es == null) {
                    deductionSourceTableAdapter.addNewRecord(((Deduction) deductionData.get(i)).getDeductionSource());
                }
                if(((Deduction) deductionData.get(i)).getDeductionId() == null) {
                    ((Deduction) deductionData.get(i)).setEmployee(employee);
                    ((Deduction) deductionData.get(i)).setDeductionId(String.format("%09d", random.nextInt(1000000000)));
                    deductionTableAdapter.addNewRecord(deductionData.get(i));
                } else {
                    deductionTableAdapter.updateRecord(deductionData.get(i));
                }
            }

        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void delete() {
        // Check if there is a related user account
        if (employee.getUserAccount().getUserAccountName() == null) {
            try {
                earningTableAdapter.deleteRecords(employee);
                deductionTableAdapter.deleteRecords(employee);
                employeeTable.deleteOneRecord(employee.getID());
            } catch (SQLException ex) {
                iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
            }
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        } else {
            iPAYROLLController.displayAlert("Please delete the associated user account first");
        }
    }

    public void buildData() {
        try {
            data.addAll(employeeTable.getKeys());

            // gets earning sources from the database, extracts their names, and adds them to an observable list
            List<Object> esList = earningSourceTableAdapter.getAllRecords();
            List<String> esNameList = esList.stream().map(object -> ((EarningSource) object).getName()).collect(Collectors.toList());
            ObservableList<Object> esObservableArrayList = FXCollections.observableArrayList(esNameList);
            earningSourceData.addAll(esObservableArrayList);

            // gets deduction sources from the database, extracts their names, and adds them to an observable list
            List<Object> dsList = deductionSourceTableAdapter.getAllRecords();
            List<String> dsNameList = dsList.stream().map(object -> ((DeductionSource) object).getName()).collect(Collectors.toList());
            ObservableList<Object> dsObservableArrayList = FXCollections.observableArrayList(dsNameList);
            deductionSourceData.addAll(dsObservableArrayList);
        } catch (SQLException ex) {
            iPAYROLLController.displayAlert("ERROR: " + ex.getMessage());
        }
    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setItems(data);

        //editable tables
        tableView1.setEditable(true);
        tableView2.setEditable(true);

        // sets up the earnings table
        earningSourceCol.setCellValueFactory(cellData -> cellData.getValue().getEarningSource().nameProperty());
        valueCol1.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        valueCol1.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        ratePerHourCol.setCellValueFactory(cellData -> cellData.getValue().ratePerHourProperty());
        ratePerHourCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        startDateCol1.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endDateCol1.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());


        // sets up the deductions table
        deductionSourceCol.setCellValueFactory(cellData -> cellData.getValue().getDeductionSource().nameProperty());
        valueCol2.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        valueCol2.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        percentRemainingCol.setCellValueFactory(cellData -> cellData.getValue().percentageRemainingProperty());
        percentRemainingCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        upperLimitCol.setCellValueFactory(cellData -> cellData.getValue().upperLimitProperty());
        upperLimitCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
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
