package com.example.menuyhdelle;


/*
*
* */
public class User {
    private String userName = "";
    private String password = "";
    private double co2AnnualOjbective = 0.0;
    private double co2Cumulative = 0.0;
    private static int counter;

    public User(String name, String pass, Double objective){
        userName = name;
        password = pass;
        co2AnnualOjbective = objective;

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
     * Increase co2 cumulative value with addedCo2
     * @param addedCO2
     * @return cumulative co2 value after addition
     */
    public double increaseCo2(double addedCO2){
        co2Cumulative = co2Cumulative + addedCO2;
        return  co2Cumulative;
    }
}
