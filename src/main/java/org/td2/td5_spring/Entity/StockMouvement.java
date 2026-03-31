package org.td2.td5_spring.Entity;

public class StockMouvement {
    private String unit;
    private Double value;

    public StockMouvement(String unit, Double value) {
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

    public void setValue(Double value) {
        this.value = value;
    }
}
