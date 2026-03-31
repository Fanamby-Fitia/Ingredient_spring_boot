package org.td2.td5_spring.Entity;

public class StockMouvement {
    private String unit;
    private double value;

    public StockMouvement(String unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public StockMouvement() {

    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
