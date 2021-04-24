package com.example.menuyhdelle;

public class DishIngredient {
    private Ingredient ingredient;
    private float quantity;


    // *** Constructors ***
    public DishIngredient() {
        System.out.println("Added dish DishIngredient");
        this.ingredient = new Ingredient("Pottu", 0.1, "kpl", 0.0 );
        this.quantity = (float) 1.0;
    }

    // *** Methods ***
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
