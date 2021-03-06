package com.example.menuyhdelle;

import java.util.ArrayList;

public class Dish {
    private String name;
    //private ArrayList<DishIngredient> ingredients = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>(); // save some time just to demonstrate functionality
    // private Class created;
    private String diet;
    private String type;
    private String recipe;
    private Double co2;

    // *** Constructors ***
    public Dish(String n) {
        this.name = n; // just to support the testing
    }

    public Dish(String n, ArrayList<Ingredient> a, String diet, String type, String recipe) {
        System.out.println("Added dish: " + n);
        this.name = n;
        this.ingredients = a; // how nulls affect, do we allow?
        this.diet = diet;
        this.type = type;
        this.recipe = recipe;
    }

    // To display correct name in spinner

    @Override
    public String toString() {
        return getName();
    }

    // *** Methods ***
    public double getCO2() {
        Double ingredientsCo2 = 0.0;
        if (this.ingredients != null){
            for (int i = 0; i < this.ingredients.size(); i++){
                System.out.println("ing = " + this.ingredients.get(i).getName());
                System.out.println("co2 = " + this.ingredients.get(i).getCo2());
                ingredientsCo2 = ingredientsCo2 + this.ingredients.get(i).getCo2();
            }
        }
        return ingredientsCo2;
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
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
