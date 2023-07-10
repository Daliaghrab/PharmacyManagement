package com.example.pharmacymanagement;

public class User {

    private String email;
    private String addr;
    private String city;
    private String province;
    private String Contactnumber;
    private String userId;

    public User(String email, String addr, String city, String province, String contactnumber) {
        this.email = email;
        this.addr = addr;
        this.city = city;
        this.province = province;
        Contactnumber = contactnumber;
    }

    public User(String userId, String email, String addr, String city, String province, String contactnumber) {
        this.userId = userId;
        this.email = email;
        this.addr = addr;
        this.city = city;
        this.province = province;
        Contactnumber = contactnumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getContactnumber() {
        return Contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        Contactnumber = contactnumber;
    }
}
