package se2203b.ipayroll;

import javafx.beans.property.*;

import java.sql.Date;

public class Deduction {
    private final StringProperty deductionId;
    private final DoubleProperty amount;
    private final DoubleProperty percentageRemaining;
    private final DoubleProperty upperLimit;
    private final ObjectProperty<Date> startDate;
    private final ObjectProperty<Date> endDate;
    private ObjectProperty<DeductionSource> deductionSource = new SimpleObjectProperty(new DeductionSource());
    private ObjectProperty<Employee> employee = new SimpleObjectProperty(new Employee());

    public Deduction() {
        this.deductionId = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty();
        this.percentageRemaining = new SimpleDoubleProperty();
        this.upperLimit = new SimpleDoubleProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
    }

    // setters
    public void setDeductionId(String deductionId) {this.deductionId.set(deductionId);}
    public void setAmount(Double amount) {this.amount.set(amount);}
    public void setPercentageRemaining(Double percentageRemaining) {this.percentageRemaining.set(percentageRemaining);}
    public void setUpperLimit(Double upperLimit) {this.upperLimit.set(upperLimit);}
    public void setStartDate(Date startDate) {this.startDate.set(startDate);}
    public void setEndDate(Date endDate) {this.endDate.set(endDate);}
    public void setDeductionSource(DeductionSource deductionSource) {this.deductionSource.set(deductionSource);}
    public void setEmployee(Employee employee) {this.employee.set(employee);}

    // getters
    public String getDeductionId() {return this.deductionId.get();}
    public Double getAmount() {return this.amount.get();}
    public Double getPercentageRemaining() {return this.percentageRemaining.get();}
    public Double getUpperLimit() {return this.upperLimit.get();}
    public Date getStartDate() {return this.startDate.get();}
    public Date getEndDate() {return this.endDate.get();}
    public DeductionSource getDeductionSource() {return this.deductionSource.get();}
    public Employee getEmployee() {return this.employee.get();}

    // properties
    public StringProperty deductionIdProperty() {return deductionId;}
    public DoubleProperty amountProperty() {return amount;}
    public DoubleProperty percentageRemainingProperty() {return percentageRemaining;}
    public DoubleProperty upperLimitProperty() {return upperLimit;}
    public ObjectProperty<Date> startDateProperty() {return startDate;}
    public ObjectProperty<Date> endDateProperty() {return endDate;}
    public ObjectProperty<DeductionSource> deductionSourceProperty() {return deductionSource;}
    public ObjectProperty<Employee> employeeProperty() {return employee;}
}
