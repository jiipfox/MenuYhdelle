package com.example.menuyhdelle;

import java.util.ArrayList;
import java.util.Date;

public class Menu {
    private ArrayList<Dish> dishes = new ArrayList<>();
    // private Class user;
    private Date date;
    private String name;

    // *** Constructors ***
    public Menu() {

    }
    public Menu(String n) {
        this.name = n;
        System.out.println("Added menu: " + this.name);
        this.addDish(); 
    }

    // *** Methods ***
    public void getMenuCO2() {

    }
    public void addDish() {
        this.dishes.add(new Dish("Pyttipannu"));
    }
    public void removeDish() {

    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
