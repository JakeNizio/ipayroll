/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se2203b.ipayroll;

import java.sql.Date;
import javafx.beans.property.*;

/**
 *
 * @author Abdelkader
 */
public class Employee extends IPayrollUser{
    private final StringProperty city;
    private final StringProperty province;
    private final StringProperty postalCode;
    private final StringProperty phone;
    private final StringProperty SIN;
    private final StringProperty martialStatus;
    private final StringProperty jobName;
    private final StringProperty skillCode;
    private final DoubleProperty workHours;
    private final StringProperty payType;
    private final BooleanProperty exempt;
    private final DoubleProperty ratePerHour;
    private final ObjectProperty<Date> DOB;
    private final ObjectProperty<Date> DOH;
    private final ObjectProperty<Date> DOR;
    private final ObjectProperty<Date> DOLR;
    private final ObjectProperty<Date> DOLP;
    private final ObjectProperty<Date> DOLPC;
    private final ObjectProperty<Date> DOLCC;
    private final ObjectProperty<Date> DOT;
    private final DoubleProperty vacationAccrualRate;
    private final DoubleProperty vacationAccrued;
    private final DoubleProperty vacationUsed;
    private final DoubleProperty sickAccrualRate;
    private final DoubleProperty sickAccrued;
    private final DoubleProperty sickUsed;
    private final StringProperty earningStatementType;


    public Employee() {
        setID(null);
        setFullName(null);
        setUserAccount(new UserAccount());

        this.city = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.SIN = new SimpleStringProperty();
        this.martialStatus = new SimpleStringProperty();
        this.jobName = new SimpleStringProperty();
        this.skillCode = new SimpleStringProperty();
        this.workHours = new SimpleDoubleProperty();
        this.payType = new SimpleStringProperty();
        this.exempt = new SimpleBooleanProperty();
        this.ratePerHour = new SimpleDoubleProperty();
        this.DOB = new SimpleObjectProperty<>();
        this.DOH = new SimpleObjectProperty<>();
        this.DOR = new SimpleObjectProperty<>();
        this.DOLR = new SimpleObjectProperty<>();
        this.DOLP = new SimpleObjectProperty<>();
        this.DOLPC = new SimpleObjectProperty<>();
        this.DOLCC = new SimpleObjectProperty<>();
        this.DOT = new SimpleObjectProperty<>();
        this.vacationAccrualRate = new SimpleDoubleProperty();
        this.vacationAccrued = new SimpleDoubleProperty();
        this.vacationUsed = new SimpleDoubleProperty();
        this.sickAccrualRate = new SimpleDoubleProperty();
        this.sickAccrued = new SimpleDoubleProperty();
        this.sickUsed = new SimpleDoubleProperty();
        this.earningStatementType = new SimpleStringProperty();


    }
    public void setCity(String _code) {
        city.set(_code);
    }
    public void setProvince(String _code) {
        province.set(_code);
    }
    public void setPostalCode(String _code) {
        postalCode.set(_code);
    }
    public void setPhone(String _code) {
        phone.set(_code);
    }
    public void setSIN(String _code) {
        SIN.set(_code);
    }
    public void setMartialStatus(String _code) {
        martialStatus.set(_code);
    }
    public void setJobName(String _code) {
        jobName.set(_code);
    }
    public void setSkillCode(String _code) {
        skillCode.set(_code);
    }
    public void setWorkHours(double amount) {
        workHours.set(amount);
    }
    public void setPayType(String _code) {
        payType.set(_code);
    }
    public void setExempt(boolean value) {
        exempt.set(value);
    }
    public void setRatePerHour(double amount) {
        ratePerHour.set(amount);
    }
    public void setDOB(Date _date) {
        DOB.set(_date);
    }
    public void setDOH(Date _date) {
        DOH.set(_date);
    }
    public void setDOR(Date _date) {
        DOR.set(_date);
    }
    public void setDOLR(Date _date) {
        DOLR.set(_date);
    }
    public void setDOLP(Date _date) {
        DOLP.set(_date);
    }
    public void setDOLPC(Date _date) {
        DOLPC.set(_date);
    }
    public void setDOLCC(Date _date) {
        DOLCC.set(_date);
    }
    public void setDOT(Date _date) {
        DOT.set(_date);
    }
    public void setVacationAccrualRate(double amount) {
        vacationAccrualRate.set(amount);
    }
    public void setVacationAccrued(double amount) {
        vacationAccrued.set(amount);
    }
    public void setVacationUsed(double amount) {
        vacationUsed.set(amount);
    }
    public void setSickAccrualRate(double amount) {
        sickAccrualRate.set(amount);
    }
    public void setSickAccrued(double amount) {
        sickAccrued.set(amount);
    }
    public void setSickUsed(double amount) {
        sickUsed.set(amount);
    }
    public void setEarningStatementType(String _code) {
        earningStatementType.set(_code);
    }


    public String getCity() {
        return city.get();
    }
    public String getProvince() {
        return province.get();
    }
    public String getPostalCode() {
        return postalCode.get();
    }
    public String getPhone() {
        return phone.get();
    }
    public String getSIN() {
        return SIN.get();
    }
    public String getMartialStatus() {
        return martialStatus.get();
    }
    public String getJobName() {
        return jobName.get();
    }
    public String getSkillCode() {
        return skillCode.get();
    }
    public Double getWorkHours( ) {
        return workHours.get();
    }
    public String getPayType() {
        return payType.get();
    }
    public boolean getExempt( ) {
        return exempt.get();
    }
    public Double getRatePerHour( ) {
        return ratePerHour.get();
    }
    public Date getDOB( ) {
        return DOB.get();
    }
    public Date getDOH( ) {
        return DOH.get();
    }
    public Date getDOR( ) {
        return DOR.get();
    }
    public Date getDOLR( ) {
        return DOLR.get();
    }
    public Date getDOLP( ) {
        return DOLP.get();
    }
    public Date getDOLPC( ) {
        return DOLPC.get();
    }
    public Date getDOLCC( ) {
        return DOLCC.get();
    }
    public Date getDOT( ) {
        return DOT.get();
    }
    public Double getVacationAccrualRate( ) {
        return vacationAccrualRate.get();
    }
    public Double getVacationAccrued( ) {
        return vacationAccrued.get();
    }
    public Double getVacationUsed( ) {
        return vacationUsed.get();
    }
    public Double getSickAccrualRate( ) {
        return sickAccrualRate.get();
    }
    public Double getSickAccrued( ) {
        return sickAccrued.get();
    }
    public Double getSickUsed( ) {
        return sickUsed.get();
    }
    public String getEarningStatementType( ) {
        return earningStatementType.get();
    }

    public StringProperty cityProperty() {
        return city;
    }
    public StringProperty provinceProperty() {
        return province;
    }
    public StringProperty postalCodeProperty() {
        return postalCode;
    }
    public StringProperty phoneProperty() {
        return phone;
    }
    public StringProperty SINProperty() {
        return SIN;
    }
    public StringProperty martialStatusProperty() {
        return martialStatus;
    }
    public StringProperty jobNameProperty() {
        return jobName;
    }
    public StringProperty skillCodeProperty() {
        return skillCode;
    }
    public DoubleProperty workHoursProperty() {
        return workHours;
    }
    public StringProperty payTypeProperty() {
        return payType;
    }
    public BooleanProperty exemptProperty() {
        return exempt;
    }
    public DoubleProperty ratePerHourProperty() {
        return ratePerHour;
    }
    public ObjectProperty<Date> DOBProperty() {
        return DOB;
    }
    public ObjectProperty<Date> DOHProperty() {
        return DOH;
    }
    public ObjectProperty<Date> DORProperty() {
        return DOR;
    }
    public ObjectProperty<Date> DOLRProperty() {
        return DOLR;
    }
    public ObjectProperty<Date> DOLPProperty() {
        return DOLP;
    }
    public ObjectProperty<Date> DOLPCProperty() {
        return DOLPC;
    }
    public ObjectProperty<Date> DOLCCProperty() {
        return DOLCC;
    }
    public ObjectProperty<Date> DOTProperty() {
        return DOT;
    }
    public DoubleProperty vacationAccrualRateProperty() {
        return vacationAccrualRate;
    }
    public DoubleProperty vacationAccruedProperty() {
        return vacationAccrued;
    }
    public DoubleProperty vacationUsedProperty() {
        return vacationUsed;
    }
    public DoubleProperty sickAccrualRateProperty() {
        return sickAccrualRate;
    }
    public DoubleProperty sickAccruedProperty() {
        return sickAccrued;
    }
    public DoubleProperty sickUsedProperty() {
        return sickUsed;
    }
    public StringProperty earningStatementTypeProperty() {
        return earningStatementType;
    }
}
