package com.example.menuyhdelle;

import java.util.ArrayList;
import java.util.Date;

/**
 *  todo Per week menu?
 */
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
        this.addDish(null);
    }

    // *** Methods ***

    /**
     * Todo implementation
     * @return co2 foot print in double
     */
    public Double getMenuCO2() {
        Double co2FootPrint = 100.0;

        return co2FootPrint;

    }

    public void addDish(Dish dish) {
        this.dishes.add(new Dish("Pyttipannu")); // todo remove

        if (dish == null){ // fail safer?
            return;
        }
        this.dishes.add(dish);
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
