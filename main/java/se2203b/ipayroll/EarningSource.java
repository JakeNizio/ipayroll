package se2203b.ipayroll;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EarningSource {
    // code, name
    private final StringProperty code;
    private final StringProperty name;

    public EarningSource() {
        this.code = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    // setters
    public void setCode(String code) {this.code.set(code);}
    public void setName(String name) {this.name.set(name);}

    // getters
    public String getCode() {return this.code.get();}
    public String getName() {return this.name.get();}

    // properties
    public StringProperty codeProperty() {return code;}
    public StringProperty nameProperty() {return name;}
}
