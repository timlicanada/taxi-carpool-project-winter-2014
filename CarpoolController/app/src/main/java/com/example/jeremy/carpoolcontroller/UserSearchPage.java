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

import com.parse.CountCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class UserSearchPage extends Activity {

    private String localID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_page);

        try
        {
            this.localID = "test";
        }
        catch (NullPointerException ex)
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
        getMenuInflater().inflate(R.menu.menu_user_search_page, menu);
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

    public void buttonSearchClick(View view)
    {
        if((((EditText) findViewById(R.id.editTextName)).getText().toString()).equals(""))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Field Empty")
                    .setMessage("Please enter a username.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
        {
            //if(RegistrationPage.existsUser(((EditText) findViewById(R.id.editTextName)).getText().toString()))
            if((((EditText) findViewById(R.id.editTextName)).getText().toString()).equals("test"))
            {
                Intent intent = new Intent(UserSearchPage.this, PublicProfilePage.class);
                intent.putExtra("localID", this.localID);
                intent.putExtra("publicID", ((EditText) findViewById(R.id.editTextName)).getText().toString());
                startActivity(intent);
                finish();
            }
            else
            {
                new AlertDialog.Builder(this)
                        .setTitle("User not found.")
                        .setMessage("Username does not exist. Please check spelling. (Search is case sensitive)")
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

    public void buttonBackClick (View view){
        finish();
    }

}
