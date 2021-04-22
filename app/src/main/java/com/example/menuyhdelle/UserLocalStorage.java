package com.example.menuyhdelle;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User backend implementation and helper classes. Todo check if needs to be splitted
 */
public class UserLocalStorage {

    private ArrayList<User> userList = new ArrayList<>(); // todo replace with seeking the data from test.json

    // Test functions
    public boolean writeJson(Context c){
        // Test JSONing
        String testUserString = loadJSONFromAsset(c.getApplicationContext());
        System.out.println(testUserString);

        ArrayList<User> allUsers =  JsonToUserList(testUserString);
        System.out.println("User list size = " + allUsers.size());

        return true;
    }

    // Test functions
    public boolean readJson(Context c){
        return true;
    }

    /** todo:
     *  - password verification
     *  - check if user is already created, problems in the login phase otherwise
     */
    public boolean createUser(String name, String pass, double co2obj){
        if (isValidName(name) == false){
            System.out.println("Invalid user name.");
            return false;
        }

        if (isValidPassword(pass) == false){
            System.out.println("Invalid user pass.");
            return false;
        }

        if (isValidObjective(co2obj) == false){
            System.out.println("Invalid co2 obj");
            return false;
        }

        User newUser = new User(name, pass, co2obj);
        userList.add(newUser);
        System.out.println("User list size in LoginActivity = " + userList.size());

        return true;
    }

    public User loginUser(String loginName, String loginPass) {
        User user;
        boolean passOk = false;
        System.out.println("Try to login: " + loginName + ", login pass = " + loginPass);

        if (userList == null) {
            System.out.println("Empty user list, add more users.");
            return null;
        }

        if (userList.size() <= 0) {
            System.out.println("Empty user list, add more users.");
            return null;
        }

        for (int i = 0; i < userList.size(); i++) {
            String userName = userList.get(i).getUserName();
            if (userName == loginName) {
                System.out.println("Login user name found from index " + i);
                passOk = verifyPassword(userList.get(i).getPassword(), loginPass);
                if (passOk) {
                    user = userList.get(i);
                    return user;
                } else {
                    System.out.println("Wrong password!");
                }
                break;
            }
        }
        return null;
    }

    private boolean verifyPassword(String pass, String loginPass){
        if (pass.equals(loginPass)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isValidObjective(Double obj){
        if (obj >= 0.0) {
            return true;
        } else{
            return false;
        }
    }

    private boolean isValidName(String name){
        if (name.length() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Atleast 1 number, 1 alpha, 1 special, len > 4
     * @param pass
     * @return true if valid
     */
    private boolean isValidPassword(String pass){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[@_.]).*$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pass);

        if (pass.length() > 4){ //&& matcher.matches()) { // todo shit doesnt work
            return true;
        }
        return false;
    }

    /**
     * Serialize string Json to ArrayList of Users
     * @param jsonTxt that contains instances of User in form of ArrayList
     * @return null if unsuccesfull, ArrayList of users if ok
     */
    public ArrayList<User> JsonToUserList(String jsonTxt){
        // todo do some kind of validation?
        ArrayList<User> userList = null;
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType(); // Java cannot handle the array list for fromJson class type so need to change
        try {
            Gson gson = new Gson();
            userList = gson.fromJson(jsonTxt, userListType);
        } catch (Exception e){
            System.out.println("error when converting json to user object");
            System.out.println(e.toString());
        }
        return userList;
    }

    /**
     * Deserialize ArrayList of Users to json string.
     * @param a ArrayList of Users
     * @return null if unsucessfull, json string
     */
    public String userListToJson(ArrayList<User> a){
        String json = null;
        try {
            Gson gson = new Gson();
            String jsonTxt = gson.toJson(a);
            System.out.println(jsonTxt);
        } catch (Exception e) {
            System.out.println("error when converting user object to json ");
            System.out.println(e.toString());
        }
        return json;
    }


    /**
     * todo not needed?
     * @param jsonTxt with parameters in format of user
     * @return null if unsuccesfull, User if ok
     */
    public User JsonToUser(String jsonTxt){
        // todo do some kind of validation?
        User user = null;
        try {
            Gson gson = new Gson();
            user = gson.fromJson(jsonTxt, User.class);
            System.out.println("User: " + user.getUserName());
            System.out.println("Obj: " + user.getCo2AnnualOjbective());
        } catch (Exception e){
            System.out.println("error when converting json to user object");
            System.out.println(e.toString());
        }
        return user;
    }

    /**
     * todo not needed?
     * @param user instance of User
     * @return null if unsuccesfull, json array list in form of a string
     */
    public String userToJson(User user){
        String json = null;
        try {
            Gson gson = new Gson();
            String jsonTxt = gson.toJson(user);
            System.out.println(jsonTxt);
        } catch (Exception e) {
            System.out.println("error when converting user object to json ");
            System.out.println(e.toString());
        }
        return json;
    }

    /**
     *
     * @param context
     * @return
     */
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("test.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
