package com.example.jeremy.carpoolcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class LoginActivity extends Activity {
    String userID, userPW;
    boolean checker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void loginAttempt(View v){
        String userIn, pwIn;

        userIn = ((EditText) findViewById(R.id.editText)).getText().toString();
        pwIn = ((EditText) findViewById(R.id.editText2)).getText().toString();
        //checker = MainActivity.checkLogin(userIn.toString(), pwIn.toString());
        checker = true;//switch this with loginchecker
        if (checker){
            Intent intent = new Intent(getApplicationContext(),StartMenu.class);
            intent.putExtra("localID",userIn);
            intent.putExtra("name","David");
            intent.putExtra("password",pwIn);
            startActivity(intent);
        }
        else{
            startActivity(new Intent(getApplicationContext(), LoginError.class));
        }
    }
    public void registerButton (View v){
        startActivity(new Intent(getApplicationContext(),RegistrationPage.class));
    }
}
