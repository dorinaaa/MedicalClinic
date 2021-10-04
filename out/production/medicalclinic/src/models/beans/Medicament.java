package models.beans;

import java.sql.Date;

public class Medicament {
    private  int id ;
    private String name;
    private int availability;
    private float price;
    private Date best_before;

    public Date getBest_before() {
        return best_before;
    }

    public void setBest_before(Date best_before) {
        this.best_before = best_before;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
