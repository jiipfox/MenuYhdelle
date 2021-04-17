package com.example.menuyhdelle;

import java.util.ArrayList;

public class MainClass {
    static private MainClass main = null;
    private ArrayList<Menu> menuList = new ArrayList<>();
    // private ArrayList<Class> shoppingLists = new ArrayList<>();
    // private Class currentUser;

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

    // *** Methods ***
    public void createMenu() {
        menuList.add(new Menu("Gourmet menu"));
    }
    public void createUser() {

    }
    public void loginUser() {

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
}
