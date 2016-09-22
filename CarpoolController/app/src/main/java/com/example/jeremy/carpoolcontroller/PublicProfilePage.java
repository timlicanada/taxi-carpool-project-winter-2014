package com.example.jeremy.carpoolcontroller;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class PublicProfilePage extends Activity {

    // currentID is the userID of the currently viewed profile.
    // localID is the userID of the local user.
    // localUser is the profile of the localUser

    private String publicID, localID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_profile_page);

        //Parse.initialize(this, "gBMLlJhlzzddlcHmeKWiOZcgJiguYEjue3zAEFTD", "WHB7biu1ignurDgHTiW3J7P3wxFM9esn0To5SDeA");

        try {
            Bundle bundle = getIntent().getExtras();
            this.localID = "arangojd";
            this.publicID = "test";

        } catch (NullPointerException ex)
        {

            new AlertDialog.Builder(this)
                    .setTitle("Oops Something went wrong!")
                    .setMessage("Please login again.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

        ((TextView) findViewById(R.id.textViewName)).setText("test");

        ((EditText) findViewById(R.id.editTextBio)).setText("Enter bio here.");
        findViewById(R.id.editTextBio).setEnabled(false);

        ((RatingBar) findViewById(R.id.ratingBar)).setRating(5f);
        findViewById(R.id.ratingBar).setEnabled(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_profile_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Rate button function: Saves whatever rating is selected in the rating bar. Changes colour of
    // stars to blue.

    public void buttonRateClick(View view) {
        // checking that local user is not current user

        if (!(this.publicID.equals(this.localID)))
        {
            // checking button state

            if (((Button) findViewById(R.id.buttonRate)).getText().equals("Rate")) {
                // setting rating bar to editable & button text to confirm

                (findViewById(R.id.ratingBar)).setEnabled(true);
                ((Button) findViewById(R.id.buttonRate)).setText("Confirm");

            } else if (((Button) findViewById(R.id.buttonRate)).getText().equals("Confirm")) {
                // getting rating from user and setting the rating bar to not editable

                float rating = ((RatingBar) findViewById(R.id.ratingBar)).getRating();
                ((RatingBar) findViewById(R.id.ratingBar)).setEnabled(false);

                // calculating new rating and updating rating bar

                rating = (5f * 0f + rating)
                        / (0f + 1f);
                ((RatingBar) findViewById(R.id.ratingBar)).setRating(rating);

                // updating database

                //RegistrationPage.setRating(publicID, rating, RegistrationPage.getRatingCount(publicID) + 1f);

                // changing button text back to RATE

                ((Button) findViewById(R.id.buttonRate)).setText("Rate");
            }
        }
    }

    public void buttonBackClick(View view) {
        finish();
    }
}