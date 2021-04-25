package com.example.menuyhdelle;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ReadXML {

    private static ArrayList theatreArrayList;

    /**
     * Read url rest json using from defined url using volley api. Put request on the queue and after
     * response pick the Total CO2 and return. Handle exceptions todo.
     *
     * @param c context for the RequestQueue
     */
    static void readTheatreAreaXML(Context c){
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.lowCarbonPreference=true&query.beefLevel=100&query.fishLevel=100&query.porkPoultryLevel=100&query.dairyLevel=100&query.cheeseLevel=100&query.riceLevel=100&query.eggLevel=100&query.winterSaladLevel=100&query.restaurantSpending=100";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;
                String referenceCo2Target = "";
                try {
                    obj = new JSONObject(response);
                    int failure = obj.optInt("failure");
                    System.out.println("JSON OBJ: " + obj.toString());
                    referenceCo2Target = obj.getString("Total");
                    System.out.println("Reference CO2 target = " + referenceCo2Target);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    static void readTheatreAreaXML1(){

        try{
            theatreArrayList = new ArrayList<String>();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.lowCarbonPreference=true&query.beefLevel=100&query.fishLevel=100&query.porkPoultryLevel=100&query.dairyLevel=100&query.cheeseLevel=100&query.riceLevel=100&query.eggLevel=100&query.winterSaladLevel=100&query.restaurantSpending=100";
            String urlStrings = "https://www.finnkino.fi/xml/TheatreAreas/";
            Document doc = builder.parse(urlStrings);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nlist = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int i = 0; i < nlist.getLength(); i++) {
                Node node = nlist.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String tId = element.getElementsByTagName("ID").item(0).getTextContent();
                    System.out.println(tId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            System.out.println("###################");
        }
    }

}
