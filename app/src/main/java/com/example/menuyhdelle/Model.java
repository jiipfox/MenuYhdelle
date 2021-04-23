package com.example.menuyhdelle;

public class Model {

    private String sNo;
    private String product;
    private String category;

    public Model(String sNo, String product, String category) {
        this.sNo = sNo;
        this.product = product;
        this.category = category;
    }

    public String getsNo() {
        return sNo;
    }

    public String getProduct() {
        return product;
    }

    public String getCategory() {
        return category;
    }

}