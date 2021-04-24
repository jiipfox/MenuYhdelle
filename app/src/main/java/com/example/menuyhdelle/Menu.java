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
    private Double co2;

    // *** Constructors ***
    public Menu() {

    }

    public Menu(String n, ArrayList<Dish> dish, Date date) {
        System.out.println("Add menu: " + n);
        this.name = n;
        this.dishes = dish;
        this.date = date;
    }

    // *** Methods ***

    /**
     * We need to do this everytime becaue menu might be created without adding dishes using addDish
     * @return co2 foot print of dishes
     */
    public Double getCO2() {
        Double calculated = 0.0;

        if (this.dishes != null){
            for (int i = 0; i < this.dishes.size(); i++){
                calculated = calculated + this.dishes.get(i).getCO2();
            }
        }

        return calculated;
    }

    public void addDish(Dish dish) {
        if (dish == null){ // fail safer?
            return;
        }
        this.co2 = this.co2 + dish.getCO2();
        this.dishes.add(dish);
    }

    public void removeDish() {   }

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
