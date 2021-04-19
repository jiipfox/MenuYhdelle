package com.example.menuyhdelle;

import java.util.ArrayList;

public class Dish {
    private String name;
    private ArrayList<DishIngredient> ingredients = new ArrayList<>();
    // private Class created;
    private String diet;
    private String type;
    private String recipe;

    // *** Constructors ***
    public Dish() {

    }
    public Dish(String n) {
        this.name = n;
        System.out.println("Added dish: " + this.name);
        this.ingredients.add(new DishIngredient());
    }

    // *** Methods ***
    public void getCO2() {

    }
    public void getNutrition() {

    }
    public void getEnergy() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DishIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<DishIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
