package com.example.menuyhdelle;

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

    /**
     * Store ingredients to given path location. Stores the local arraylist.
     * @param givenPath path with context info
     * @param append true if append, false if overwrite whole file
     * @return true if successfull
     */
    public boolean storeIngredients(File givenPath, Boolean append){
        File file = new File(givenPath, ingredientFilename);
        String jsonString = stuffToJson(this.ingredients);
        System.out.println("Write json to: " + file.toPath());
        System.out.println("Data: " + jsonString);
        if (io.write(file, jsonString, append)){
            return true;
        } else {
            System.out.println("File write not ok for some reason");
            return false;
        }
    }

    /**
     * Transform string of ingredients in json format to ArrayList of ingredients
     * @param jsonTxt
     * @return ArrayList of ingredients
     */
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

    /**
     * Load ingredients file from device and store it to local private arraylist with first time exception
     * @param definedPath path with context info
     * @return ArrayList of ingredients copy to be used ie. in ui
     */
    public ArrayList<Ingredient> loadIngredients(File definedPath) {
        File file = new File(definedPath, ingredientFilename);
        System.out.println("Ingredients file path = " + file.toPath());
        String userString = io.read(file);

        // If file doesn't exist, create dummy ingredient json and store it locally, also create file
        if (userString == null){
            System.out.println("File " + ingredientFilename + " doesnt exist, create dummy file");
            String dummyIngredients = "[{\"co2\":0.0,\"name\":\"Raaka-aine\",\"price\":1,\"unitOfMeasure\":\"g\"}]";
            this.ingredients = jsonToIngredientList(dummyIngredients);
            storeIngredients(definedPath, true);
        } else {
            this.ingredients = jsonToIngredientList(userString); // note difference with userString and dummyIngredients
        }

        if (this.ingredients == null){
            System.out.println("Ingredients list is null!"); // todo test prints
            return null;
        }

        userString = io.read(file);
        System.out.println("Ingredient names:"); // todo test prints
        System.out.println(userString);
        for (int i = 0; i < this.ingredients.size(); i++) {
            System.out.println("Name (" + i + ") = " + this.ingredients.get(i).getName());
        }

        return this.ingredients;
    }


    /**
     * Store dishes to given path location. Stores the local arraylist
     * @param givenPath path with context info
     * @return true if successfull
     */
    public boolean storeDishes(File givenPath, Boolean firstTime){
        System.out.println("Store dishes.");

        File file = new File(givenPath, dishFilename);
        String jsonString = stuffToJson(this.dishes);
        System.out.println("Write json to: " + file.toPath());
        System.out.println("Data: " + jsonString);
        if (io.write(file, jsonString, firstTime)) {
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
        // If file doesn't exist, create dummy json and store it locally, also create file
        if (userString == null){
            System.out.println("File " + dishFilename + " doesnt exist, create dummy file");
            String dummyDishes = "[{\"diet\":Test-diet\"\", \"ingredients\":[{\"co2\":0.0,\"name\":\"Ingredient1\",\"price\":1.0,\"unitOfMeasure\":\"g\"}], \"name\":\"Dish1\",\"recipe\":\"Recipe1\",\"type\":\"Type1\"}]";
            this.dishes = jsonToDishList(dummyDishes);
            storeDishes(definedPath, true);
        } else {
            this.dishes = jsonToDishList(userString); // note difference with userString and dummyIngredients
        }

        if (this.dishes == null){
            System.out.println("Dish list is null!"); // todo test prints
            return null;
        }

        userString = io.read(file);
        System.out.println("Dish names:"); // todo test prints
        System.out.println(userString);
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
