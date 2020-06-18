package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich new_sandwich = new Sandwich();

        JSONObject sandwich_details = new JSONObject(json);
        JSONObject name = sandwich_details.getJSONObject("name");

        String mainName = name.getString("mainName");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

        List<String> differentNames = new ArrayList<>();

        for(int i=0;i<alsoKnownAs.length();i++){
            differentNames.add(i,alsoKnownAs.getString(i));
        }

        String placeOfOrigin = sandwich_details.getString("placeOfOrigin");

        String description = sandwich_details.getString("description");

        String image = sandwich_details.getString("image");

        JSONArray ingredients = sandwich_details.getJSONArray("ingredients");

        List<String> ingredientsList = new ArrayList<>();

        for(int i=0;i<ingredients.length();i++){
            ingredientsList.add(i,ingredients.getString(i));
        }

        new_sandwich.setMainName(mainName);
        new_sandwich.setAlsoKnownAs(differentNames);
        new_sandwich.setDescription(description);
        new_sandwich.setImage(image);
        new_sandwich.setPlaceOfOrigin(placeOfOrigin);
        new_sandwich.setIngredients(ingredientsList);
        return new_sandwich;
    }
}
