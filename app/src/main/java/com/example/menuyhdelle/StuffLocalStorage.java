package com.example.menuyhdelle;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Ingredient, Dish, Dishincredient and Menu storing and helpers
 */
public class StuffLocalStorage {
    private static String ingredientFilename = "ingredients.json";
    private static String dishFilename = "dishes.json";
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Dish> dishes = new ArrayList<>();
    FileWriterReader io = new FileWriterReader();


    public boolean addStuff(Ingredient ing){
        ingredients.add(ing);
        return true;
    }

    public boolean addStuff(Dish dish){
        dishes.add(dish);
        return true;
    }


    public boolean storeIngredients(File givenPath){
        File file = new File(givenPath, ingredientFilename);
        String jsonString = stuffToJson(this.ingredients);
        System.out.println("Write json to: " + file.toPath());
        System.out.println("Data: " + jsonString);
        if (io.write(file, jsonString, false)){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Ingredient> jsonToIngredientList(String jsonTxt){
        ArrayList<Ingredient> ingList = null;
        // Java cannot handle the array list for fromJson class type so need to change
        Type ingListType = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        try {
            Gson gson = new Gson();
            ingList = gson.fromJson(jsonTxt, ingListType);
        } catch (Exception e) {
            System.out.println("error when converting json to user object");
            System.out.println(e.toString());
        }
        return ingList;
        }


    public ArrayList<Ingredient> loadIngredients(File definedPath) {
        File file = new File(definedPath, ingredientFilename);
        System.out.println("Ingredients file path = " + file.toPath());

        String userString = io.read(file);
        this.ingredients = jsonToIngredientList(userString);

        if (this.ingredients == null){
            System.out.println("User list is null!"); // todo test prints
            return null;
        }

        System.out.println("Ingredient names:"); // todo test prints
        for (int i = 0; i < this.ingredients.size(); i++) {
            System.out.println("Name (" + i + ") = " + this.ingredients.get(i).getName());
        }

        return this.ingredients;
    }


    public boolean storeDishes(File givenPath){
        System.out.println("Store dishes.");

        File file = new File(givenPath, dishFilename);
        String jsonString = stuffToJson(this.dishes);
        System.out.println("Write json to: " + file.toPath());
        System.out.println("Data: " + jsonString);
        if (io.write(file, jsonString, false)){
            System.out.println("ok");
            return true;
        } else {
            System.out.println("NOT NOT NOT ok");
            return false;
        }
    }

    public ArrayList<Dish> loadDishes(File definedPath) {
        File file = new File(definedPath, dishFilename);
        System.out.println("Dishes file path = " + file.toPath());

        String userString = io.read(file);
        this.dishes = jsonToDishList(userString);

        if (this.dishes == null){
            System.out.println("Dish list is null!"); // todo test prints
            return null;
        }

        System.out.println("Dish names:"); // todo test prints
        for (int i = 0; i < this.dishes.size(); i++) {
            System.out.println("Name (" + i + ") = " + this.dishes.get(i).getName());
        }

        return this.dishes;
    }

    public ArrayList<Dish> jsonToDishList(String jsonTxt){
        ArrayList<Dish> dList = null;
        // Java cannot handle the array list for fromJson class type so need to change
        Type dListType = new TypeToken<ArrayList<Dish>>() {}.getType();
        try {
            Gson gson = new Gson();
            dList = gson.fromJson(jsonTxt, dListType);
        } catch (Exception e) {
            System.out.println("error when converting json to dish objects");
            System.out.println(e.toString());
        }
        return dList;
    }

    public String stuffToJson(ArrayList a) {
        String json = null;
        try {
            Gson gson = new Gson();
            json = gson.toJson(a);
        } catch (Exception e) {
            System.out.println("error when converting user object to json ");
            System.out.println(e.toString());
        }
        return json;
    }
}
