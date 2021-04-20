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

// Mixed sorts of helper functions to store the data
// Note, separate user data and login credentials?
public class UserLocalStorage {

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
