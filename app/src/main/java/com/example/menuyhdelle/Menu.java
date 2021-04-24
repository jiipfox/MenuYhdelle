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

    public Menu(String n, ArrayList<Dish> dish, Date date) {
        System.out.println("Add menu: " + n);
        this.name = n;
        this.dishes = dish;
        this.date = date;
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

    public String getName() {
        return this.name;
    }
}
