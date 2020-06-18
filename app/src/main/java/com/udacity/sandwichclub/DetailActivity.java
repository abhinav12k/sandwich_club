package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Widgets
    private TextView tv_origin;
    private TextView tv_description;
    private TextView tv_ingredients;
    private TextView tv_diffNames;

    private Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tv_origin = (TextView) findViewById(R.id.origin_tv);
        tv_diffNames = (TextView) findViewById(R.id.also_known_tv);
        tv_description = (TextView) findViewById(R.id.description_tv);
        tv_ingredients = (TextView) findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d(TAG,"Unable to get position");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        tv_description.setText(sandwich.getDescription());
        tv_origin.setText(sandwich.getPlaceOfOrigin());

        int count = 0;
        for (String name : sandwich.getAlsoKnownAs()) {
            if (count == sandwich.getAlsoKnownAs().size() - 1)
                tv_diffNames.append(name);
            else
                tv_diffNames.append(name + ", ");
            count++;
        }

        count = 0;
        for (String ingredient : sandwich.getIngredients()) {
            if (count == sandwich.getIngredients().size() - 1)
                tv_ingredients.append(ingredient);
            else
                tv_ingredients.append(ingredient + ", ");
            count++;
        }
    }
}
