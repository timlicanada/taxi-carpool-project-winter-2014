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

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class LocalProfilePage extends Activity {

    // currentID is the userID of the currently viewed profile.
    // localID is the userID of the local user.
    // localUser is the profile of the localUser

    private String localID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_profile_page);

        //Parse.initialize(this, "gBMLlJhlzzddlcHmeKWiOZcgJiguYEjue3zAEFTD", "WHB7biu1ignurDgHTiW3J7P3wxFM9esn0To5SDeA");

        // if activity started from a different class, get the localID of user. Otherwise report error and ask user to login again.

        try {
            Bundle bundle = getIntent().getExtras();
            this.localID = bundle.getString("localID");
            String name = bundle.getString("name");
            String password = bundle.getString("password");

            ((TextView) findViewById(R.id.textViewName)).setText(name);
            ((EditText) findViewById(R.id.editTextBio)).setText("Enter bio here.");
            findViewById(R.id.editTextBio).setEnabled(false);

            ((EditText) findViewById(R.id.editTextPassword1)).setText(password);
            findViewById(R.id.editTextPassword1).setEnabled(false);

            ((EditText) findViewById(R.id.editTextPassword2)).setText(password);
            findViewById(R.id.editTextPassword2).setEnabled(false);

            ((RatingBar) findViewById(R.id.ratingBar)).setRating(5f);
            findViewById(R.id.ratingBar).setEnabled(false);

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_profile_page, menu);
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


    // Edit button function: Makes bio & password editable and changes button text to SAVE. Clicking again
    // makes bio and password not editable and saves new text to database

    public void buttonEditClick(View view) {
        // checking button state

        if (((Button) findViewById(R.id.buttonEdit)).getText().equals("Edit")) {
            // making password and bio field text editable

            ((EditText) findViewById(R.id.editTextBio)).setEnabled(true);
            ((EditText) findViewById(R.id.editTextPassword1)).setEnabled(true);
            ((EditText) findViewById(R.id.editTextPassword2)).setEnabled(true);

            // changing button text to SAVE

            ((Button) findViewById(R.id.buttonEdit)).setText("Save");

        } else if (((Button) findViewById(R.id.buttonEdit)).getText().equals("Save")) {
            if ((((EditText) findViewById(R.id.editTextPassword1)).getText().toString())
                    .equals(((EditText) findViewById(R.id.editTextPassword2)).getText()
                            .toString())) {
                // making bio and password field text not editable

                ((EditText) findViewById(R.id.editTextBio)).setEnabled(false);
                ((EditText) findViewById(R.id.editTextPassword1)).setEnabled(false);
                ((EditText) findViewById(R.id.editTextPassword2)).setEnabled(false);

                // saving changes to database

                //RegistrationPage.setBio(this.localID,((EditText) findViewById(R.id.editTextBio)).getText()
                //.toString());
                //RegistrationPage.setPassword(this.localID,((EditText) findViewById(R.id.editTextPassword1))
                //.getText().toString());

                // changing button text back to EDIT

                ((Button) findViewById(R.id.buttonEdit)).setText("Edit");
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Password Error")
                        .setMessage("Passwords do not match.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface
                                .OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }


    }


    // opens user search activity, allowing users to search for other users

    public void buttonSearchClick(View view) {
        Intent intent = new Intent(LocalProfilePage.this, UserSearchPage.class);
        intent.putExtra("localID", localID);
        startActivity(intent);

    }


    // Close Account button function: Displays a warning screen.

    public void buttonCloseClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Please Confirm")
                .setMessage("Closing your account will no longer allow you to access your " +
                        "profile and use the oneCab app." + "\n"
                        + "Are you sure you wish to proceed?")
                .setPositiveButton(android.R.string.yes, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //RegistrationPage.setInactiveFlag(localID, true);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void buttonStartMenuClick(View view) {

        startActivity(new Intent(getApplicationContext(), StartMenu.class));
        finish();
    }

    /*
    public static String queryData;

    public static String getName(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (database == null) {
                    //failure
                } else {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String queryField = database.getString("name");
                    String [] querySplit = queryField.split(",");
                    queryData = querySplit[index];
                }
            }
        });

        return SecurityClass.privateDecrypt(queryData);

    }
    public static String getPassword(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (database == null) {
                    //failure
                } else {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String queryField = database.getString("password");
                    String [] querySplit = queryField.split(",");
                    queryData = querySplit[index];
                }
            }
        });

        return SecurityClass.privateDecrypt(queryData);

    }
    public static String getBio(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String bioField = database.getString("bio");
                    String [] bioSplit = bioField.split(",");
                    queryData = bioSplit[index];
                }
            }
        });

        return SecurityClass.privateDecrypt(queryData);

    }
    public static Float getRating(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String queryField = database.getString("rating");
                    String [] querySplit = queryField.split(",");
                    queryData = querySplit[index];
                }
            }
        });

        return Float.parseFloat(SecurityClass.privateDecrypt(queryData));

    }
    public static Float getRatingCount(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String queryField = database.getString("ratingCount");
                    String [] querySplit = queryField.split(",");
                    queryData = querySplit[index];
                }
            }
        });

        return Float.parseFloat(SecurityClass.privateDecrypt(queryData));

    }
    public static boolean getInactiveFlag(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String flagField = database.getString("inactiveFlag");
                    String [] flagSplit = flagField.split(",");
                    queryData = flagSplit[index];
                }
            }
        });

        return Boolean.parseBoolean(SecurityClass.privateDecrypt(queryData));
    }

    public static boolean getDriverFlag(String ID)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String flagField = database.getString("driverFlag");
                    String [] flagSplit = flagField.split(",");
                    queryData = flagSplit[index];
                }
            }
        });

        return Boolean.parseBoolean(SecurityClass.privateDecrypt(queryData));

    }
    public static boolean checkLoginInfo(String ID, String password)
    {
        final String IDEncrypted = SecurityClass.privateEncrypt(ID);
        final String passwordEncrypted = SecurityClass.privateEncrypt(password);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground("KJsai6n9z8", new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String queryField = database.getString("password");
                    String [] querySplit = queryField.split(",");
                    queryData = querySplit[index];
                }
            }
        });

        return queryData == passwordEncrypted;

    }
    */
}
