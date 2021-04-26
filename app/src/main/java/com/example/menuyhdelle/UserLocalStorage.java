package com.example.menuyhdelle;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User backend implementation and helper classes. Todo check if needs to be splitted
 */
public class UserLocalStorage {
    private static String filename = "user.json";
    private ArrayList<User> userList = new ArrayList<>();
    FileWriterReader io = new FileWriterReader();
    Krypto krypt1x;

    // Create passphrase for encoding and decoding
    public UserLocalStorage(){
        try {
            krypt1x = new Krypto("salasana123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Get path from context, create json string from the users list and store it to file
     *
     * @return true if successfull
     */
    public boolean writeJson(File definedPath) {
        System.out.println("PAAAAATH = " + definedPath);
        File file = new File(definedPath, filename);
        String jsonString = userListToJson(this.userList);

        System.out.println("Write json to: " + file.toPath());
        System.out.println("Data: " + jsonString);

        if (io.write(file, jsonString, false)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get path from context, create user list from json and store it locally
     *
     * @param definedPath , app context files directory
     * @return
     */
    public boolean readJson(File definedPath) {
        File file = new File(definedPath, filename);
        System.out.println("File path = " + file.toPath());

        String userString = io.read(file);
        this.userList = JsonToUserList(userString);

        if (userList == null){
            System.out.println("User list is null!"); // todo test prints
            return false;
        }

        System.out.println("User names:"); // todo test prints
        for (int i = 0; i < userList.size(); i++) {
            System.out.println("Name (" + i + ") = " + userList.get(i).getUserName());
            System.out.println("Name (" + i + ") = " + userList.get(i).getMenus());
        }

        return true;
    }

    /**
     * todo:
     * - password verification
     * - check if user is already created, problems in the login phase otherwise
     */
    public boolean createUser(String name, String pass, double co2obj, String fullName, boolean preferLowCo2) {
        String encodedPass = "";

        if (isValidName(name) == false) {
            System.out.println("Invalid user name.");
            return false;
        }

        if (isValidPassword(pass) == false) {
            System.out.println("Invalid user pass.");
            return false;
        }

        if (isValidObjective(co2obj) == false) {
            System.out.println("Invalid co2 obj");
            return false;
        }

        // Encode password using given passphrase
        try {
            encodedPass = krypt1x.encrypt(pass);
            System.out.println("encoded pass = " + encodedPass);
        } catch (Exception e) {
            System.out.println("Can't encode user password! Do what?");
            e.printStackTrace();
            return false;
        }

        // For some reason if userlist is null, make some dummy user.
        if (userList == null){
            String dummy = "[{\"co2AnnualOjbective\":0.0, \"co2Cumulative\":0.0, \"password\":\"1\", \"userMenus\":[{\"date\":\"Jan 1, 1970 6:56:48 PM\", \"dishes\":[{\"diet\":\"\", \"ingredients\":[{\"co2\":0.0,\"name\":\"1\",\"price\":1,\"unitOfMeasure\":\"g\"}], \"name\":\"K\",\"recipe\":\"K\",\"type\":\"R\"}], \"name\":\"Erikois\"}], \"userName\":\"Teppo\"}]";
            userList = JsonToUserList(dummy);
            System.out.println("Name und drang " + userList.get(0).getUserName());
        }

        try{
            User newUser = new User(name, encodedPass, co2obj, fullName, preferLowCo2);
            userList.add(newUser);
            System.out.println("User list size in LoginActivity = " + userList.size());
        } catch (NullPointerException ei){
            ei.printStackTrace();
            return false;
        }

        return true;
    }

    public User loginUser(String loginName, String loginPass) {
        User user;
        boolean passOk = false;
        String encodedPass;
        try {
            if (userList == null) {
                System.out.println("Empty user list, add more users.");
                return null;
            }

            if (userList.size() <= 0) {
                System.out.println("Empty user list, add more users.");
                return null;
            }

            // Do not check the pass if not even valid
            if (loginPass.length() <= 1){
                return null;
            }

            System.out.println("Trying to login with =" + loginPass);

            for (int i = 0; i < userList.size(); i++) {
                String userName = userList.get(i).getUserName();
                if (userName.equals(loginName)) {
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
        } catch (Exception e){
            System.out.println("Error while creating user!");
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Decrypt
     * @param pass
     * @param loginPass
     * @return
     */
    private boolean verifyPassword(String pass, String loginPass) {
        String decoded;

        try {
            decoded = krypt1x.decrypt(pass);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (decoded.equals(loginPass)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidObjective(Double obj) {
        if (obj >= 0.0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidName(String name) {
        if (name.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Atleast 1 number, 1 alpha, 1 special, len > 4
     *
     * @param pass
     * @return true if valid
     */
    private boolean isValidPassword(String pass) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[@_.]).*$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pass);

        if (pass.length() > 1) { //&& matcher.matches()) { // todo shit doesnt work
            return true;
        }
        return false;
    }

    /**
     * Serialize string Json to ArrayList of Users
     *
     * @param jsonTxt that contains instances of User in form of ArrayList
     * @return null if unsuccesfull, ArrayList of users if ok
     */
    public ArrayList<User> JsonToUserList(String jsonTxt) {
        // todo do some kind of validation?
        ArrayList<User> userList = null;
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType(); // Java cannot handle the array list for fromJson class type so need to change
        try {
            Gson gson = new Gson();
            userList = gson.fromJson(jsonTxt, userListType);
        } catch (Exception e) {
            System.out.println("error when converting json to user object");
            System.out.println(e.toString());
        }
        return userList;
    }

    /**
     * Deserialize ArrayList of Users to json string.
     * todo could this be generic
     * @param a ArrayList of Users
     * @return null if unsucessfull, json string
     */
    public String userListToJson(ArrayList<User> a) {
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



    /**
     * Todo redundant, not needed as asset folder cannot be used
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

    /**
     * todo not needed?
     *
     * @param user instance of User
     * @return null if unsuccesfull, json array list in form of a string
     */
    public String userToJson(User user) {
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
     * todo not needed?
     *
     * @param jsonTxt with parameters in format of user
     * @return null if unsuccesfull, User if ok
     */
    public User JsonToUser(String jsonTxt) {
        // todo do some kind of validation?
        User user = null;
        try {
            Gson gson = new Gson();
            user = gson.fromJson(jsonTxt, User.class);
            System.out.println("User: " + user.getUserName());
            System.out.println("Obj: " + user.getCo2AnnualOjbective());
        } catch (Exception e) {
            System.out.println("error when converting json to user object");
            System.out.println(e.toString());
        }
        return user;
    }
}
