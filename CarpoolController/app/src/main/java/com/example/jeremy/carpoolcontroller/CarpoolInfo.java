package com.example.jeremy.carpoolcontroller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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

public class CarpoolInfo extends FragmentActivity {
    GoogleMap mMap;
    ArrayList<LatLng> Marker;
    @Override

    protected void onCreate(Bundle savedInstanceState) { //On create, make maps, create parse
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        super.onCreate(savedInstanceState);
        Parse.initialize(this, "NaM2ayNUKBdNpDKDabrnLNFYOASDCDYYrVYkBjVo", "OupLbeS6UVIJchkGOpGu8qCSLIia9hS0W4weBmOl");
        Marker = new ArrayList<LatLng>();               //Initial array of latlng
        Marker.add(new LatLng(43.26, -79.92));
        setContentView(R.layout.activity_carpool_info);
        setUpMapIfNeeded();                             //Android Studios createmap code
        mMap.setMyLocationEnabled(true);
        // Testing Parse
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
        // Testing Parse
        LatLng McMaster = new LatLng(43.26, -79.92);
        CameraUpdate McMasterLoc = CameraUpdateFactory.newLatLngZoom(McMaster, 15);
        mMap.animateCamera(McMasterLoc);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Marker.clear();
                mMap.clear();
                mMap.getMyLocation();
                Marker.add(new LatLng(43.26, -79.92));
                setUpMap();
                return true;
            }
        });


        mMap.setOnMapClickListener(new OnMapClickListener() { //Set markers and route on map
            @Override
            public void onMapClick(LatLng point) { //Abstract Class, This plots markers on map
                if (Marker.size() ==0){
                    mMap.addMarker(new MarkerOptions().position(point).title("Start"));
                    Marker.add(point);
                }
                if (Marker.size() ==1) {
                    mMap.addMarker(new MarkerOptions().position(point).title("Destination"));
                    Marker.add(point);
                }
                if(Marker.size() == 2){

                    LatLng origin = Marker.get(0);
                    LatLng Final = Marker.get(1);
                    String url = getGoogleUrl(origin, Final);// Get google maps URL
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
                    builder.setTitle("No Carpool");
                    Double Dist = GetDistance(origin, Final);
                    builder.setMessage("You Cost is " + Dist + "m in distance, Long Press to Carpool");
                    builder.show();
                }
            }
            });


        mMap.setOnMapLongClickListener(new OnMapLongClickListener() { //This Gets Carpools
            @Override
            public void onMapLongClick(LatLng point) { // carpool getter
                if (Marker.size() ==1) {
                    mMap.addMarker(new MarkerOptions().position(point).title("Destination"));
                    Marker.add(point);
                }
                if(Marker.size() == 2){
                    final LatLng origin = Marker.get(0);
                    final LatLng Final = Marker.get(1);
                    //Parse
                    final ParseObject location = new ParseObject("Location");
                    location.put("LatO",origin.latitude);
                    location.put("LngO",origin.longitude);

                    location.put("LatD", Final.latitude);
                    location.put("LngD", Final.longitude);

                    location.saveInBackground();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Location");
                    query.getInBackground("ekHup0XH48", new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                Double LatO = object.getDouble("LatO");
                                Double LatD = object.getDouble("LatD");
                                Double LngO = object.getDouble("LngO");
                                Double LngD = object.getDouble("LngD");
                                LatLng Inter1 = new LatLng(LatO,LngO);
                                LatLng Inter2 = new LatLng(LatD,LngD);
                                //recproical put
                                object.put("LatO",origin.latitude);
                                object.put("LngO",origin.longitude);
                                object.put("LatD", Final.latitude);
                                object.put("LngD", Final.longitude);
                                //recproical put
                                object.saveInBackground();

                                mMap.addMarker(new MarkerOptions().position(Inter1).title("PickUp"));
                                mMap.addMarker(new MarkerOptions().position(Inter2).title("Destination"));

                                String url = getGoogleUrl(origin, Inter1);// Get google maps URL
                                DownloadTask downloadTask = new DownloadTask();
                                downloadTask.execute(url);

                                String url3 = getGoogleUrl(Inter1, Inter2);// Get google maps URL
                                DownloadTask downloadTask3 = new DownloadTask();
                                downloadTask3.execute(url3);

                                String url2 = getGoogleUrl(Inter2, Final);// Get google maps URL
                                DownloadTask downloadTask2 = new DownloadTask();
                                downloadTask2.execute(url2);
                                builder.setTitle("Carpooling");
                                Double Dist = GetDistance(origin, Inter1);
                                Double Dist2 = GetDistance(Inter1, Inter2);
                                Double Dist3 = GetDistance(Inter2, Final);
                                Double dist4 = Dist + Dist2*0.5;
                                Double Dist5 = Dist2*0.35;
                                builder.setMessage("You Cost is " + dist4 + "m in distance, You Saved:"+ Dist5 +" In Meters");
                                builder.show();
                            } else {
                                // something went wrong
                            }

                        }
                    });
                }
            }
        });
    }

    private String downloadUrl(String strUrl) throws IOException{ //This gets google info and converts a JSON section, the structure of this code was largely sourced from http://wptrafficanalyzer.in
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
private  double GetDistance(LatLng origin, LatLng Final){
    int R = 6371;
    double x = (origin.longitude - Final.longitude) * Math.cos((Final.latitude + Final.latitude) / 2);
    double y = (Final.latitude - Final.latitude);
    double distance = Math.sqrt(x * x + y * y) * R;
    return distance;
}

    private class DownloadTask extends AsyncTask<String, Void, String>{ //Based off og code from http://wptrafficanalyzer.in
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {//Baed Off of http://wptrafficanalyzer.in
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.BLACK);
            }

            mMap.addPolyline(lineOptions);
        }
    }


    private String getGoogleUrl(LatLng origin,LatLng Final) { //Method of creating URL was sourced from http://wptrafficanalyzer.in
        String OriginLatLng = "origin=" + origin.latitude + "," + origin.longitude;
        String DestinationLatLng = "destination=" + Final.latitude + "," + Final.longitude;
        String Googleurl = "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + OriginLatLng + "&" + DestinationLatLng + "&" + "sensor=false";
        return Googleurl;
    }
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();}

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.26, -79.92)).title("McMaster"));
    }
}
