package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView langitude;
    TextView latitude;
    TextView speed;
    TextView direction;
    private static final int sh=1000;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    Sensor accelerometer;
    SensorManager sm;

    TextView Vitess = findViewById(R.id.Vitess);
    TextView xAcc,yAcc,zAcc;
    double x,y,z,vilocity;
    double xAccel,yAccel,zAccel;
    double xPreviousAccel,yPreviousAccel,zPreviousAccel;
    boolean firstUpdate = true;

    //MyDatabase db ;




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case sh:
            {
                if(grantResults.length>0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    }
                    else if (grantResults[0] == PackageManager.PERMISSION_DENIED){

                    }
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        langitude=findViewById(R.id.langitude);
        latitude=findViewById(R.id.latitude);
        speed=findViewById(R.id.speed);
        direction=findViewById(R.id.direction);
        //db= Room.databaseBuilder(getApplicationContext(), MyDatabase.class, MyDatabase.DB_Racolt).build();

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);



        xAcc = findViewById(R.id.xac);
        yAcc = findViewById(R.id.yac);
        zAcc = findViewById(R.id.zac);


        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},sh);
        }
        else
            {
                buildLocationRequest();
                buildLocationCallBack();
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());

        }
    }

    private String ConvertLatitude(double latitude) {
        StringBuilder builder = new StringBuilder();

        if (latitude < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }

        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        builder.append(" ");

        return builder.toString();
    }




    private String ConvertLongitude( double longitude){
        StringBuilder builder = new StringBuilder();
        if (longitude < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");

        return builder.toString();
    }







    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationResult(LocationResult locationResult) {
                 for(Location location:locationResult.getLocations()){

                     langitude.setText("longitude = "+ConvertLatitude(location.getLatitude()));
                     latitude.setText("laltittude = "+ConvertLongitude(location.getLongitude()));
                     speed.setText("speed = "+location.getSpeed());
                     direction.setText("Direction = "+location.getBearingAccuracyDegrees());

                    /* Recolt donnees = new Recolt();
                     donnees.setLaltitude(location.getAltitude());
                     donnees.setLongitude(location.getLongitude());
                     donnees.setSpeed(location.getAccSpeed());
                     donnees.setDirection(location.getBearing());
                     donnees.setX(x);
                     donnees.setY(y);
                     donnees.setZ(z);
                     donnees.setVilocity(vilocity);
                     db.daoAccess().insertRacolt(donnees);
                     xAcc.setText(""+donnees);*/
                 }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10);
        locationRequest.setFastestInterval(9);
        locationRequest.setSmallestDisplacement(10);
    }







    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];


        xAcc.setText("x =  "+x);
        yAcc.setText("y =  "+y);
        zAcc.setText("z =  "+z);

        updateAccel(x,y,z);

        double deltaX = abs(xPreviousAccel - xAccel);
        double deltaY = abs(yPreviousAccel - yAccel);
        double deltaZ = abs(yPreviousAccel - yAccel);
        vilocity = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2)+Math.pow(deltaZ,2)-SensorManager.GRAVITY_EARTH);

        Vitess.setText("vilocity =  "+vilocity);

    }
    private void updateAccel(double xNew, double yNew, double zNew) {
        if(firstUpdate){
            xPreviousAccel = xNew;
            yPreviousAccel = yNew;
            zPreviousAccel = zNew;
            firstUpdate = false ;
        }
        else {
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
        }

        xAccel = xNew;
        yAccel = yNew;
        zAccel = zNew;
    }
}
