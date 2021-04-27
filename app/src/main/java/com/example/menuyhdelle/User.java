package com.example.menuyhdelle;


import java.util.ArrayList;

/*
*
* */
public class User {
    private String userName = "";
    private String password = "";
    private double co2AnnualOjbective = 0.0;
    private double co2Cumulative = 0.0;
    private ArrayList<Menu> userMenus;
    private String fullName = "";
    private boolean preferLowCo = false;

    public User(String name, String pass, Double objective, String allNames, Boolean lowCoSelected){
        this.userName = name;
        this.password = pass;
        this.co2AnnualOjbective = objective;
        this.userMenus = new ArrayList<>();
        this.fullName = allNames;
        this.preferLowCo = lowCoSelected;
        System.out.println("New user created: " + this.userName);
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public double getCo2AnnualOjbective(){
        return co2AnnualOjbective;
    }

    public String getFullName(){ return this.fullName;}

    public boolean getPreferLowCo() {return this.preferLowCo;}

    public double getCo2Cumulative(){
        return co2Cumulative;
    }

    public void setUserName(String name){
        userName = name;
    }

    public void setPassword(String word){
        password = word;
    }

    public void setCo2AnnualOjbective(Double objective){
        co2AnnualOjbective = objective;
    }

    /**
     * Assign menu to user and increase co2 cumulative value
     * @param m menu
     */
    public void setMenus(Menu m){
        this.userMenus.add(m);
        this.co2Cumulative = this.co2Cumulative + m.getCO2();
    }

    public ArrayList<Menu> getMenus(){
        return this.userMenus;
    }
}
