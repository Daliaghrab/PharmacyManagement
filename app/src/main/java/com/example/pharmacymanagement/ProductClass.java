package com.example.pharmacymanagement;

public class ProductClass {
    private String prodName;
    private String prodImage;
    private String prodDesc;
    private String prodCategory;
    private String prodPrice;
    private String prodQuantity;

    private String key;
    public String getKey() {
        return key;
    }
    public ProductClass() {
        // Required empty constructor for Firebase deserialization
    }
    public ProductClass(String prodName, String prodImage, String prodDesc, String prodCategory, String prodPrice, String prodQuantity) {
        this.prodName = prodName;
        this.prodImage = prodImage;
        this.prodDesc = prodDesc;
        this.prodCategory = prodCategory;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
    }

    public String getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(String prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
