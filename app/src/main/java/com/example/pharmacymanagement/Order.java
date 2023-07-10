package com.example.pharmacymanagement;

import java.util.List;

public class Order {
    private String email;
    private String addr;
    private String city;
    private String province;
    private String contactNumber;
    private List<ProductClass> products;
    private String orderstatus;
    private String totprice;
    private String Productnamelist;
    private String Productpricelist;

    private User recipient;
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String orderId;

    public Order() {
        // Default constructor required for Firebase database operations
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getTotprice() {
        return totprice;
    }

    public void setTotprice(String totprice) {
        this.totprice = totprice;
    }

    public String getProductnamelist() {
        return Productnamelist;
    }

    public void setProductnamelist(String productnamelist) {
        Productnamelist = productnamelist;
    }

    public String getProductpricelist() {
        return Productpricelist;
    }

    public void setProductpricelist(String productpricelist) {
        Productpricelist = productpricelist;
    }

    public Order(String email, String addr, String city, String province, String contactNumber, List<ProductClass> products, User recipient) {
        this.email = email;
        this.addr = addr;
        this.city = city;
        this.province = province;
        this.contactNumber = contactNumber;
        this.products = products;
        this.orderstatus = "In progress";

        double totalPrice = 0.0;
        StringBuilder productNamesBuilder = new StringBuilder();
        StringBuilder productPricesBuilder = new StringBuilder();

        for (ProductClass product : products) {
            totalPrice += Double.parseDouble(product.getProdPrice());
            productNamesBuilder.append(product.getProdName()).append("\n");
            productPricesBuilder.append("$ ").append(product.getProdPrice()).append("\n");
        }

        this.Productnamelist = productNamesBuilder.toString().trim();
        this.Productpricelist = productPricesBuilder.toString().trim();
        this.totprice = String.format("$ %.2f", totalPrice);

    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<ProductClass> getProducts() {
        return products;
    }

    public void setProducts(List<ProductClass> products) {
        this.products = products;
    }
}

