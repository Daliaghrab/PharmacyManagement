package com.example.pharmacymanagement;

public class Employee {
    private String employeeId;
    private String email;
    private String password;
    private double sales;

    public Employee() {
        // Default constructor required for Firebase database
    }

    public Employee(String employeeId, String email, String password, double sales) {
        this.employeeId = employeeId;
        this.email = email;
        this.password = password;
        this.sales = sales;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }
}