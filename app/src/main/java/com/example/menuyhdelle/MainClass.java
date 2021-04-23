package com.example.menuyhdelle;

import android.content.Context;

import java.util.ArrayList;

public class MainClass {
    static private MainClass main = null;
    private ArrayList<Menu> menuList = new ArrayList<>();
        // private ArrayList<Class> shoppingLists = new ArrayList<>();
    private User currentUser;

    // *** Constructors ***
    private MainClass() {
        System.out.println("Main class created");
    }

    static public MainClass getMain() {
        if (main == null) {
            main = new MainClass();
        }
        return main;
    }

    UserLocalStorage userBackEnd = new UserLocalStorage(); // todo work or no work?

    // *** Methods ***
    public void createMenu() {
        menuList.add(new Menu("Gourmet menu"));
    }

    /**
     * Initialize database loading things
     * todo change so that only inits are done and no database is read
     * @param c
     * @return
     */
    public boolean loadDb(Context c){
        System.out.println("load db");
        userBackEnd.readJson(c);
        return true;
    }

    /**
     * Initialize database writing
     * todo testing only atm
     * @param c
     * @return
     */
    public boolean saveDb(Context c){
        userBackEnd.writeJson(c);
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
