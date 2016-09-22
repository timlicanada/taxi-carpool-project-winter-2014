package com.example.jeremy.carpoolcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }
    /*
        public void buttonClick(View v){//button click test
            Button button = (Button) v;
            ((Button) v).setText("clicked");
        }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public static boolean checkLogin (String ID, String PW){
        boolean checker = false;
        //access database
        return checker;//true or false based on login check
    }

    public void logOut (){
        startActivity(new Intent(getApplicationContext(),LogoutActivity.class));//switch activity
    }
    public void profileMode(){
        startActivity(new Intent(getApplicationContext(),LocalProfilePage.class));
    }
    public void driverMode(){
        //startActivity(new Intent(getApplicationContext(),LogoutActivity.class)); //cut from final version
    }
    public void carpoolMode(){
        startActivity(new Intent(getApplicationContext(),CarMenu2.class));
    }

    public static String decrypt(String cipherText){
        String keyword = "ALK3R893afjaidnf9qnqdfnq09a093JAF0094aldfemalkdmfDFNNN203";
        String clearText1 = "";
        String clearText2 = "";
        int count = 0;

        for(int j = 0; j<cipherText.length(); j++) {
            char ch = cipherText.charAt(j);
            int index2 = (int)ch;
            index2 -= 399;
            if(index2 < 0){index2 = index2 + 65535;}
            clearText1 += (char)index2;
        }

        for(int i = 0; i<clearText1.length(); i++) {
            char c = clearText1.charAt(i);
            int index = (int)c;
            index -= (int)keyword.charAt(count);
            if (index < 0) {
                index = index + 65535;
            }
            clearText2 += (char) index;
            count++;
            if (count == keyword.length()) {
                count = 0;
            }
        }

        return clearText2;
    }
}
