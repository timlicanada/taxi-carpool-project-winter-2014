package com.example.jeremy.carpoolcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartMenu extends Activity {
    String userIn, pwIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_menu, menu);
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
    public void selectProfile(View v){
        Bundle bundle = getIntent().getExtras();
        this.userIn = bundle.getString("localID");
        String name = bundle.getString("name");
        String password = bundle.getString("password");

        Intent intent = new Intent(getApplicationContext(),LocalProfilePage.class);
        intent.putExtra("localID",userIn);
        intent.putExtra("name","David");
        intent.putExtra("password",pwIn);
        startActivity(intent);
    }
    public void selectCarpool(View v){
        startActivity(new Intent(getApplicationContext(),CarMenu2.class));
    }
    public void selectLogout(View v){
        startActivity(new Intent(getApplicationContext(),LogoutActivity.class));
    }
}
