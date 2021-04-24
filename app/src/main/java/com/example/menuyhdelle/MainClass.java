package com.example.menuyhdelle;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

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
        menuList.add(new Menu("Gourmet menu"));
    }

    /**
     * Initialize database loading things
     * todo change so that only inits are done and no database is read
     * @return
     */
    public boolean loadDb(File path){
        System.out.println("load db");
        userBackEnd.readJson(path);
        return true;
    }

    /**
     * Initialize database writing
     * todo testing only atm
     * @return
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

    // TODO store the user to db in here or in createUser function
    public boolean createNewUser(String name, String pass, double co2obj)
    {
        if (userBackEnd.createUser(name, pass, co2obj)){
            System.out.println("Succesfully created user");
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

    public boolean storeIngredients(File path){
        stuffBackEnd.storeIngredients(path);
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

    public boolean storeDishes(File path){
        stuffBackEnd.storeDishes(path);
        return true;
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
