package com.example.menuyhdelle;

public class Ingredient {
    private String name;
    private float co2;
    private String unitOfMeasure;
    private float price;

    // *** Constructors ***
    public Ingredient() {

    }

    public Ingredient(String n) {
        this.name = n;
        System.out.println("Added Ingredient: " + this.getName());
    }

    // *** Methods ***
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCo2() {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
