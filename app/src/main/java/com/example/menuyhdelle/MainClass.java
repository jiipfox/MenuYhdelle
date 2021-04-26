package com.example.menuyhdelle;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MainClass {
    static private MainClass main = null;
    private ArrayList<Menu> menuList = new ArrayList<>();
        // private ArrayList<Class> shoppingLists = new ArrayList<>();
    private User currentUser;
    private File path;
    private Context appContext;
    UserLocalStorage userBackEnd = new UserLocalStorage();
    StuffLocalStorage stuffBackEnd = new StuffLocalStorage();

    // *** Constructors ***
    private MainClass() {
        System.out.println("Main class created");
    }

    // Use context to access file saving location
    static public MainClass getMain() {
        if (main == null) {
            main = new MainClass(); // Context on application level never changes so this should be risk free
        }
        return main;
    }


    // *** Methods ***
    public void createMenu() {
        menuList.add(new Menu("Gourmet menu", null, new Date())); // nonsense just to test
    }

    /**
     * Read user json file
     * todo temporary solution
     * @return always true
     */
    public boolean loadDb(File path){
        System.out.println("load db");
        userBackEnd.readJson(path);
        return true;
    }

    /**
     * Write user json file
     * todo temporary solution
     * @return always true
     */
    public boolean saveDb(File path){
        System.out.println("save db");
        userBackEnd.writeJson(path);
        return true;
    }

    /**
     * If user exists and pass matches, return true
     * @param loginName
     * @param loginPass
     * @return true if found & password matches, false if not
     */
    public boolean loginUser(String loginName, String loginPass) {
        User loggedInUser = userBackEnd.loginUser(loginName, loginPass);

        if (loggedInUser == null){
            return false;
        } else {
            currentUser = loggedInUser;
            return true;
        }
    }

    /**
     * Abstraction of user backend, create new function
     * @return true if succesfull
     */
    public boolean createNewUser(String name, String pass, double co2obj)
    {
        if (userBackEnd.createUser(name, pass, co2obj)){
            return true;
        } else {
            System.out.println("Cannot create user");
            return false;
        }
    }

    /**
     * Wrapper/abstraction for backend features
     * @return created ingredient instance
     */
    public Ingredient createIngredient(String name, double co2, String uom, double price){
        Ingredient ingre = new Ingredient(name, co2, uom, price);
        stuffBackEnd.addStuff(ingre);
        return ingre;
    }

    public boolean storeIngredients(File path, Boolean firstWrite){
        stuffBackEnd.storeIngredients(path, firstWrite);
        return true;
    }

    public ArrayList<Ingredient> loadIngredients(File path){
        return stuffBackEnd.loadIngredients(path);
    }

    /**
     * Wrapper/abstraction for backend features
     * @return created ingredient instance
     */
    public Dish createDish(String n, ArrayList<Ingredient> a, String diet, String type, String recipe){
        Dish d = new Dish(n,a,diet,type,recipe);
        stuffBackEnd.addStuff(d);
        return d;
    }

    public boolean storeDishes(File path, Boolean append){
        stuffBackEnd.storeDishes(path, append);
        return true;
    }

    /**
     * Assign Menu m to logged in User
     * @return true if successful
     */
    public boolean assignMenuToCurrentUser(Menu m){
        if (m != null && currentUser != null) {
            System.out.println("Add, " + currentUser.getUserName() + ", " + m.getName());
            currentUser.setMenus(m);
            return true;
        } else {
            System.out.println("Menu or user is null!");
            return false;
        }
    }

    public ArrayList<Menu> retrieveMenuOfCurrentUser(){
        return currentUser.getMenus();
    }

    public ArrayList<Dish> loadDishes(File path){
        return stuffBackEnd.loadDishes(path);
    }


    public String getUserName(){
        return currentUser.getUserName();
    }

    public void logoutUser() {

    }
    public void createShoppingList() {

    }
    public void saveUserData() {

    }

    public ArrayList<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<Menu> menuList) {
        this.menuList = menuList;
    }

    public Double getCurrentUserTergetCo2Value(){
        if (currentUser == null){
            System.out.println("No user defined!");
        }
        return currentUser.getCo2AnnualOjbective();
    }
}
