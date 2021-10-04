package models.services;

import java.util.Arrays;
import java.util.List;

import models.beans.Employee;

public class EmployeeService {

    public List < Employee > getEmployees() {
        return Arrays.asList(new Employee(), new Employee(),
                new Employee());
    }
}