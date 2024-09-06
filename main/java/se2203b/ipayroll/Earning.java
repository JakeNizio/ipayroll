package se2203b.ipayroll;

import java.sql.Date;
import javafx.beans.property.*;

public class Earning {
    // earningId, amount, ratePerHour, startDate, endDate, earningSource, employee
    private final StringProperty earningId;
    private final DoubleProperty amount;
    private final DoubleProperty ratePerHour;
    private final ObjectProperty<Date> startDate;
    private final ObjectProperty<Date> endDate;
    private ObjectProperty<EarningSource> earningSource = new SimpleObjectProperty(new EarningSource());
    private ObjectProperty<Employee> employee = new SimpleObjectProperty(new Employee());

    public Earning() {
        this.earningId = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty();
        this.ratePerHour = new SimpleDoubleProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
    }

    // setters
    public void setEarningId(String earningId) {this.earningId.set(earningId);}
    public void setAmount(Double amount) {this.amount.set(amount);}
    public void setRatePerHour(Double ratePerHour) {this.ratePerHour.set(ratePerHour);}
    public void setStartDate(Date startDate) {this.startDate.set(startDate);}
    public void setEndDate(Date endDate) {this.endDate.set(endDate);}
    public void setEarningSource(EarningSource earningSource) {this.earningSource.set(earningSource);}
    public void setEmployee(Employee employee) {this.employee.set(employee);}

    // getters
    public String getEarningId() {return this.earningId.get();}
    public Double getAmount() {return this.amount.get();}
    public Double getRatePerHour() {return this.ratePerHour.get();}
    public Date getStartDate() {return this.startDate.get();}
    public Date getEndDate() {return this.endDate.get();}
    public EarningSource getEarningSource() {return this.earningSource.get();}
    public Employee getEmployee() {return this.employee.get();}

    // properties
    public StringProperty earningIdProperty() {return earningId;}
    public DoubleProperty amountProperty() {return amount;}
    public DoubleProperty ratePerHourProperty() {return ratePerHour;}
    public ObjectProperty<Date> startDateProperty() {return startDate;}
    public ObjectProperty<Date> endDateProperty() {return endDate;}
    public ObjectProperty<EarningSource> earningSourceProperty() {return earningSource;}
    public ObjectProperty<Employee> employeeProperty() {return employee;}

}
