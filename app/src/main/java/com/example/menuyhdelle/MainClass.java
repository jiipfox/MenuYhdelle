package com.example.menuyhdelle;

import java.util.ArrayList;

public class MainClass {
    static private MainClass main = null;
    private ArrayList<Menu> menuList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    // private ArrayList<Class> shoppingLists = new ArrayList<>();
    private User currentUser; // we might not need to pass this via set / get functions if use always this in the methods

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

    /** todo:
     *  - password verification
     *  - check if user is already created, problems in the login phase otherwise
     */
    public void createUser(String name, String pass, double co2obj) {
        userList.add(new User(name, pass, co2obj));
    }

    /**
     * If user exists and pass matches, set as currentUser and return it also
     * @param loginName
     * @param loginPass
     * @return true if found, false if not
     */
    public boolean loginUser(String loginName, String loginPass) {
        boolean passOk = false;
        System.out.println("Try to login: " + loginName + ", login pass = " + loginPass);

        if (userList == null){
            System.out.println("Empty user list, add more users.");
            return false;
        }

        if (userList.size() <= 0){
            System.out.println("Empty user list, add more users.");
            return false;
        }

        for (int i = 0; i < userList.size(); i++){
            String userName = userList.get(i).getUserName();
            if (userName == loginName){
                System.out.println("Login user name found from index " + i);
                passOk = verifyPassword(userList.get(i).getPassword(), loginPass);
                if (passOk){
                    currentUser = userList.get(i);
                    return true;
                }
                else{
                    System.out.println("Wrong password!");
                }
                break;
            }
        }

        return false;
    }

    private boolean verifyPassword(String pass, String loginPass){
        if (pass.equals(loginPass)){
            return true;
        }
        else{
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
}
