package com.example.jeremy.carpoolcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import com.parse.ParseException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//Parse
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.GetCallback;
import com.parse.ParseException;
//Parse
import java.util.ArrayList;
import android.app.AlertDialog;

public class CarMenu2 extends Activity {
String Names;
String Start;
String InputStart;
String InputName;
String cipher;
    String BeforeStart;
String clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu2);

        Parse.initialize(this, "NaM2ayNUKBdNpDKDabrnLNFYOASDCDYYrVYkBjVo", "OupLbeS6UVIJchkGOpGu8qCSLIia9hS0W4weBmOl");
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        EditText edit = (EditText) findViewById(R.id.editText);
        EditText edit2 = (EditText) findViewById(R.id.editText2);

        InputStart = (edit.getText()).toString();
        InputName = (edit2.getText()).toString();
        //cipher = SecurityClass.encrypt(", ,.123.45");
        //clear = SecurityClass.decrypt(cipher);


        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LatLng test = new LatLng(43.2633,-79.9189);
                LatLng test1 = new LatLng (43.3633,-79.8189);
                String test2 = getGoogleUrl(test,test1);
                TextView view3 = (TextView) findViewById(R.id.textView3);
                view3.setText(cipher +"  "+ clear);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Lobby");
                ParseObject lobby = new ParseObject("Lobby");
                query.getInBackground("OqFtjnZEZy", new GetCallback<ParseObject>() {
                    public void done(ParseObject obj, ParseException e) {
                        if (e == null) {
                            obj.put("StartLocation", "a");//Makes Lobby usable again, regardless of current status
                            obj.put("People","");
                            obj.saveInBackground();
                            startActivity(new Intent(getApplicationContext(), CarpoolInfo.class));
                        }
                    }
                });
                //Transition
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Lobby");
                ParseObject lobby = new ParseObject("Lobby");
                query.getInBackground("OqFtjnZEZy", new GetCallback<ParseObject>() {
                    public void done(ParseObject obj, ParseException e) {
                        if (e == null) {
                            obj.put("StartLocation", "a");//Makes Lobby usable again, regardless of current status
                            obj.put("People","");
                            obj.saveInBackground();
                        }
                    }
                });
                //Transition to Main
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView view = (TextView) findViewById(R.id.textView);
                TextView view2 = (TextView) findViewById(R.id.textView2);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Lobby");
                ParseObject lobby = new ParseObject("Lobby");
                query.getInBackground("OqFtjnZEZy", new GetCallback<ParseObject>() {
                    public void done(ParseObject obj, ParseException e) {
                        if (e == null) {
                            BeforeStart = obj.getString("People");
                            if (BeforeStart !="a"){
                                Names = SecurityClass.decrypt(obj.getString("People"));
                                Start = SecurityClass.decrypt(obj.getString("StartLocation"));
                                String puut = Names + InputName;
                                if (Start == "a"){
                                    Start = InputStart;
                            }else{
                                    InputName = "Lobby In Use!";
                                }

                            }
                            obj.saveInBackground();
                        }
                    }
                });
                //cipher = SecurityClass.encrypt(", ,.123.45");
                //clear = SecurityClass.decrypt(cipher);
                if (BeforeStart == "a"){
                    view2.setText("No Lobby Yet!, Create Your Own!");
                }else{
                    view2.setText("Start Location: "+Start);
                }
                view.setText("Names:"+InputName);
                view2.setText("Start Location:"+InputStart);
            }
        });
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView view = (TextView) findViewById(R.id.textView);
                TextView view2 = (TextView) findViewById(R.id.textView2);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Lobby");
                ParseObject lobby = new ParseObject("Lobby");
                query.getInBackground("OqFtjnZEZy", new GetCallback<ParseObject>() {
                    public void done(ParseObject obj, ParseException e) {
                        if (e == null) {
                            BeforeStart = obj.getString("People");
                            if (!BeforeStart.contains(InputName)){
                                Names = SecurityClass.decrypt(obj.getString("People"));
                                Start = SecurityClass.decrypt(obj.getString("StartLocation"));
                                String NewMem = Names + InputName;
                                obj.put("People", SecurityClass.encrypt(NewMem));// adds name to lobby
                                obj.saveInBackground();
                            }

                        }
                    }
                });
                view.setText("Names:"+Names + " "+ InputName);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_menu2, menu);
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


    private String getGoogleUrl(LatLng origin,LatLng Final) {
        String OriginLatLng = "origin=" + origin.latitude + "," + origin.longitude;
        String DestinationLatLng = "destination=" + Final.latitude + "," + Final.longitude;
        String Googleurl = "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + OriginLatLng + "&" + DestinationLatLng + "&" + "sensor=false";
    String data = "";
    try{
        data = downloadUrl(Googleurl);
    }catch(Exception e){
        Log.d("Background Task",e.toString());
    }
    return data;}

    private String downloadUrl(String strUrl) throws IOException { //This gets google info and converts a JSON section, the structure of this code was largely sourced from http://wptrafficanalyzer.in
        InputStream Input = null;
        HttpURLConnection GoogleConnect = null;
        String JSONOut = "";
        URL url = new URL(strUrl);
        GoogleConnect = (HttpURLConnection) url.openConnection();
        StringBuffer Buffer = new StringBuffer();
        GoogleConnect.connect();
        Input = GoogleConnect.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(Input));
        String line = "";
        while( ( line = br.readLine()) != null){
            Buffer.append(line);}
        JSONOut = Buffer.toString();
        br.close();
        Input.close();
        GoogleConnect.disconnect();
        return JSONOut;
    }


}
