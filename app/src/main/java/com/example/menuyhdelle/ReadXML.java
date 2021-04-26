package com.example.menuyhdelle;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadXML {

    private static ArrayList theatreArrayList;
    private String referenceCo2Target2 = "";
    private static DecimalFormat df = new DecimalFormat("0.00");
    //MainClass main = MainClass.getMain();
    //String getCo2Prefer = main.getCo2Prefer;

    /**
     * Read url rest json using from defined url using volley api. Put request on the queue and after response pick the Total CO2 and return. Handle exceptions todo.
     *
     * @param c context for the RequestQueue, v for updating the Snackbar text
     */

    /**
     * Read url rest json using from defined url using volley api. Put request on the queue and
     * after response pick the Total CO2 and return. Handle exceptions todo.
     * @param c context
     * @param v view
     * @param preferLowCo user prefered selection of low co2 food production
     */
    public static void readAverageCo2(Context c, View v, String preferLowCo) {
        final String[] referenceCo2Target = {""};
        RequestQueue queue = Volley.newRequestQueue(c);
        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.lowCarbonPreference="+preferLowCo+"&query.beefLevel=100&query.fishLevel=100&query.porkPoultryLevel=100&query.dairyLevel=100&query.cheeseLevel=100&query.riceLevel=100&query.eggLevel=100&query.winterSaladLevel=100&query.restaurantSpending=100";
        FloatingActionButton fab = v.findViewById(R.id.fab);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    int failure = obj.optInt("failure");
                    referenceCo2Target[0] = obj.getString("Total");
                    double refTarget = Double.parseDouble(referenceCo2Target[0]);
                    Snackbar.make(v, "Ruoasta aiheutunut keskimääräinen hiilijalanjälki valintojesi pohjalta suomessa on " + df.format(refTarget) + "kg CO2 vuodessa?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(v, "Tapahtui virhe, tietoa ei pystytty hakemaan.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERROR");
                    }

                });
        queue.add(stringRequest);
    }

    public String getco2(){
        return this.referenceCo2Target2;
    }

    private void setCo2(String co2){
        this.referenceCo2Target2 = co2;
    }

}
