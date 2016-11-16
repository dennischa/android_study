package com.example.hong.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    static Database dbOpen;
    static SQLiteDatabase db;

    double x=0.0, y= 0.0;
    private MyLocation gps;
    String address;
    int cnt =0;


    ArrayList<MyData> arrayList;
    ArrayList<Double> latList;
    ArrayList<Double> lonList;
    ArrayList<String> strList;
    private GoogleMap gmap;
    static final LatLng SEOUL = new LatLng(37.56, 126.97);

    MarkerOptions marker = new MarkerOptions();

    EditText edittext;
    Button update;
    Button list;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dbOpen = new Database(this);
        db = dbOpen.getWritableDatabase();

        update = (Button) findViewById(R.id.updates);
        list = (Button) findViewById(R.id.lists);
        edittext = (EditText) findViewById(R.id.editText);

        registerForContextMenu(list);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOpen.onUpgrade(db, 0, 1);
                Toast.makeText(getApplicationContext(), "DB삭제", Toast.LENGTH_SHORT).show();
                gmap.clear();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new MyLocation(MainActivity.this);
                if (gps.isGetLocation()) {
                    Add add = new Add();
                    String date = add.getDate();
                    String time = add.gettime();
                    x = gps.getLatitude();
                    y = gps.getLongitude();

                    address = getAddress(x, y);
                    String inPutText = edittext.getText().toString();
                    String msg = "위도: " + x + " 경도: " + y + " " + address;
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


                    //arrayList.add(new MyData(date, time, address, x, y));

                    LatLng myLocation = new LatLng(x, y);
                    gmap.addMarker(new MarkerOptions().position(myLocation).title(inPutText)).showInfoWindow();

                    insertData(date, time, address, x, y, inPutText);

                    //Toast.makeText(getApplicationContext(), arrayList.get(0).getAddress(), Toast.LENGTH_SHORT).show();
                    //cnt++;
                    //moveMap(x, y);
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });
    }

    public void onMapReady(GoogleMap map) {
        gmap = map;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(true);



        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
        gmap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(getApplicationContext(),
                        marker.getTitle()
                        , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    private void moveMap(double lat, double lon) {
        //String to display current latitude and longitude
        String msg = lat + ", "+lon;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng( lat , lon);

        //Adding marker to map

        gmap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title





        //Moving the camera
        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        gmap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }







    public String getAddress(double latitude, double longitude){
        String str = "주소를 찾는 중입니다.";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list;
        try {
            if (geocoder != null) {
                list = geocoder.getFromLocation(latitude, longitude, 1);
                if (list != null && list.size() > 0) {
                    str = list.get(0).getAddressLine(0).toString();
                }
                return str;
            }
        } catch (IOException e) {
            return str;
        }
        return str;

    }
    public void insertData(String date, String time,
                           String address, double latitude, double longitude, String story) {
        db.execSQL("INSERT INTO db_table "
                + "VALUES(NULL, '" + date
                + "', '" + time
                + "', '" + address
                + "', '" + latitude
                + "', '" + longitude
                + "', '" + story
                + "');");
    }

    public void readTable(){
        arrayList = new ArrayList<MyData>();
        latList = new ArrayList<Double>();
        lonList = new ArrayList<Double>();
        String sql = "select * from db_table";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String date = results.getString(1);
            String time = results.getString(2);
            String address = results.getString(3);

            double latitude = results.getDouble(4);
            double longitude = results.getDouble(5);
            String story = results.getString(6);
            arrayList.add(new MyData(date, time, address, latitude, longitude, story));
            latList.add(latitude);
            lonList.add(longitude);
            results.moveToNext();
        }
        results.close();

    }


}
