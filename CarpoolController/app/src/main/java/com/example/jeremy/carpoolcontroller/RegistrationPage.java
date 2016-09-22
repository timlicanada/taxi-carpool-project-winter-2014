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
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class RegistrationPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Parse.initialize(this, "gBMLlJhlzzddlcHmeKWiOZcgJiguYEjue3zAEFTD", "WHB7biu1ignurDgHTiW3J7P3wxFM9esn0To5SDeA");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration_page, menu);
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

    public void buttonRegisterClick (View view)
    {
        if (!existsUser(((EditText) findViewById(R.id.editTextUserName)).getText().toString()))
        {
            if ((((EditText) findViewById(R.id.editTextPass1)).getText().toString())
                    .equals(((EditText) findViewById(R.id.editTextPass2)).getText().toString()))
            {
                if (!(((EditText) findViewById(R.id.editTextUserName)).getText().toString()).equals("")
                        && !(((EditText) findViewById(R.id.editTextPass1)).getText().toString()).equals("")
                        && !(((EditText) findViewById(R.id.editTextPass2)).getText().toString()).equals("")
                        && !(((EditText) findViewById(R.id.editTextName1)).getText().toString()).equals("")
                        && !(((EditText) findViewById(R.id.editTextName2)).getText().toString()).equals(""))
                {
                    newUser(((EditText) findViewById(R.id.editTextUserName)).getText().toString(),
                            ((EditText) findViewById(R.id.editTextPass1)).getText().toString(),
                            ((EditText) findViewById(R.id.editTextName1)).getText().toString() + " "
                                    + ((EditText) findViewById(R.id.editTextName2)).getText().toString());

                    String localID = ((EditText) findViewById(R.id.editTextUserName)).getText().toString();

                    Intent intent = new Intent(RegistrationPage.this, LocalProfilePage.class);
                    intent.putExtra("localID", localID);
                    intent.putExtra("name", ((EditText) findViewById(R.id.editTextName1)).getText().toString() + " "
                            + ((EditText) findViewById(R.id.editTextName2)).getText().toString());
                    intent.putExtra("password",((EditText) findViewById(R.id.editTextPass1)).getText().toString());
                    startActivity(intent);

                    finish();

                }
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Input Error")
                            .setMessage("A required field is empty. Please fill in all fields.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface
                                    .OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
            else
            {
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
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Username Taken")
                    .setMessage("Username is already in use. Please enter a new one.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void buttonStartMenuClick (View view)
    {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));/////////////////////////////////////////////////
        finish();
    }

    private static String queryData;
    private static String objectID = "KJsai6n9z8";

    public static void newUser(String ID, String password, String name)
    {
        ParseObject database = new ParseObject("UserProfileDatabase");

        final String IDEncrypt = SecurityClass.encrypt(ID);
        final String passwordEncrypt = SecurityClass.encrypt(password);
        final String nameEncrypt = SecurityClass.encrypt(name);
        final String ratingEncrypt = SecurityClass.encrypt(Float.toString(5f));
        final String ratingCountEncrypt = SecurityClass.encrypt(Float.toString(0f));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String queryField = database.getString("ID");
                    database.put("ID", queryField+IDEncrypt+",");
                    queryField = database.getString("password");
                    database.put("password", queryField+passwordEncrypt+",");
                    queryField = database.getString("name");
                    database.put("name", queryField+nameEncrypt+",");
                    queryField = database.getString("rating");
                    database.put("rating", queryField+ratingEncrypt+",");
                    queryField = database.getString("ratingCount");
                    database.put("ratingCount", queryField+ratingCountEncrypt+",");
                    queryField = database.getString("bio");
                    database.put("bio", queryField+SecurityClass.encrypt("Enter bio here.")+",");
                    queryField = database.getString("inactiveFlag");
                    database.put("inactiveFlag", queryField+SecurityClass.encrypt("false")+",");
                    queryField = database.getString("driverFlag");
                    database.put("driverFlag", queryField+SecurityClass.encrypt("false")+",");
                    database.saveInBackground();
                }
            }
        });
    }
    public static boolean existsUser(final String ID)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    queryData = Boolean.toString(idField.contains(SecurityClass.encrypt(ID)));
                }
            }
        });

        return Boolean.parseBoolean(queryData);
    }
    public static void setBio(String ID, String bio)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String bioEncrypted = SecurityClass.encrypt(bio);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String bioField = database.getString("bio");
                    String [] bioSplit = bioField.split(",");
                    bioSplit[index] = bioEncrypted;

                    database.put("bio", bioSplit.toString());
                    database.saveInBackground();
                }
            }
        });

    }

    public static void setPassword(String ID, String password)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String passwordEncrypted = SecurityClass.encrypt(password);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String passwordField = database.getString("password");
                    String [] passwordSplit = passwordField.split(",");
                    passwordSplit[index] = passwordEncrypted;

                    database.put("password", passwordSplit.toString());
                    database.saveInBackground();
                }
            }
        });

    }
    public static void setRating(String ID, Float rating, Float ratingCount)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String ratingEncrypt = SecurityClass.encrypt(Float.toString(rating));
        final String ratingCountEncrypt = SecurityClass.encrypt(Float.toString(ratingCount));


        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String ratingField = database.getString("rating");
                    String [] ratingSplit = ratingField.split(",");
                    ratingSplit[index] = ratingEncrypt;

                    String ratingCountField = database.getString("ratingCount");
                    String [] ratingCountSplit = ratingCountField.split(",");
                    ratingCountSplit[index] = ratingCountEncrypt;

                    database.put("rating", ratingSplit.toString());
                    database.put("ratingCount", ratingCountSplit.toString());
                    database.saveInBackground();
                }
            }
        });

    }

    public static void setInactiveFlag(String ID, boolean flag)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String flagEncrypted = SecurityClass.encrypt(Boolean.toString(flag));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String flagField = database.getString("inactiveFlag");
                    String [] flagSplit = flagField.split(",");
                    flagSplit[index] = flagEncrypted;

                    database.put("inactiveFlag", flagSplit.toString());
                    database.saveInBackground();
                }
            }
        });

    }

    public static void setDriverFlag(String ID, boolean flag)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String flagEncrypted = SecurityClass.encrypt(Boolean.toString(flag));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfileDatabase");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject database, ParseException e) {
                if (e == null)
                {
                    String idField = database.getString("ID");
                    String [] idSplit = idField.split(",");
                    int index = java.util.Arrays.asList(idSplit).indexOf(IDEncrypted);

                    String flagField = database.getString("driverFlag");
                    String [] flagSplit = flagField.split(",");
                    flagSplit[index] = flagEncrypted;

                    database.put("driverFlag", flagSplit.toString());
                    database.saveInBackground();
                }
            }
        });

    }
    public static String getName(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return SecurityClass.decrypt(queryData);

    }
    public static String getPassword(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return SecurityClass.decrypt(queryData);

    }
    public static String getBio(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return SecurityClass.decrypt(queryData);

    }
    public static Float getRating(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return Float.parseFloat(SecurityClass.decrypt(queryData));

    }
    public static Float getRatingCount(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return Float.parseFloat(SecurityClass.decrypt(queryData));

    }
    public static boolean getInactiveFlag(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return Boolean.parseBoolean(SecurityClass.decrypt(queryData));
    }

    public static boolean getDriverFlag(String ID)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);

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

        return Boolean.parseBoolean(SecurityClass.decrypt(queryData));

    }
    public static boolean checkLoginInfo(String ID, String password)
    {
        final String IDEncrypted = SecurityClass.encrypt(ID);
        final String passwordEncrypted = SecurityClass.encrypt(password);

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



}

