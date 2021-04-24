package com.example.menuyhdelle;

import android.content.Context;

public class Ingredient {
    private String name = "";
    private double co2 = 0.0;
    private String unitOfMeasure = "";
    private double price = 0.0;

    // *** Constructors ***
    public Ingredient() {

    }

    public Ingredient(String n, double co2, String unitOfMeas, double price) {
        this.name = n;
        this.co2 = co2;
        this.unitOfMeasure = unitOfMeas;
        this.price = price;

        System.out.println("Added Ingredient: " + this.getName());
    }

    // *** Methods ***
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
