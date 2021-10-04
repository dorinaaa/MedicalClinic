package models.beans;

import java.io.Serializable;

public class User implements Serializable {
    /**
     *
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String password;
    private int role;
    public String getFirstName() {
        return name;
    }
    public void setFirstName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getRole() {
        return this.role;
    }
    public void setRole(int role) {
        this.role = role;
    }
}
