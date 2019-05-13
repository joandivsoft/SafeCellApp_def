package com.example.prova01;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.prova01.Utilities.lang1;
import static java.lang.String.valueOf;

public class Activity3 extends AppCompatActivity implements LocationListener {

    //api/service/getStatuses/{ID}
    //api/device/getDeviceByImei/{IMEI}

    //PEND
    //1. Geoloc
    //2. Readings
    //3 execute methods on reading (WIFI)
    //No tancar app
    //BlockInternet - - Blocar pantalla - Filtre
    //Bloc screen - https://stackoverflow.com/questions/14352648/how-to-lock-unlock-screen-programmatically
    //REGEX    ^http(s)?://([\w-]+.)+[\w-]+(/[\w- ./?%&=])?$

    //1. Lectura WIFI + position
    //2. Block WIFI
    //3. Filter web, Block screen, don't close

    //FET
    //Fields hidden
    //Interval >> Block WIFI - onChangeLoc


    String fcmToken = "";
    static String numIMEI;
    static String numIMEI_R;

    //Imei ========================================
    TextView mostrar_imei;
    public static String imei;
    static final Integer PHONESTATS = 0x1;
    private final String TAG = Activity3.class.getSimpleName();
    //Imei ========================================

    TextView text0_appWellcome;
    TextView text0_sessionInitied;
    TextView locationText1_lat;
    TextView locationText2_lon;
    TextView locationText3_alt;
    TextView text4_net;
    TextView text4_internet;
    TextView text5_wfi;
    TextView text6_mobile;
    Button getIMEI;
    Button getLocationBtn;
    Button blockWifi;
    Button blockInternet;
    Button readings;

    TextView et_geoloc;
    TextView et_connect;
    TextView et_mobile;
    TextView et_reads;
    TextView et_read_wifi;
    TextView et_read_field2;

    public Handler handler;
    public Runnable runnable;

    final int MY_PERMISSIONS_REQUEST_LOCATION = 900;

    static LocationManager locationManager;
    static Location location1;
    String lat2;
    String lon2;
    String estat = "Estat 0";
    String estat2 = "Pendent...";
    JSONObject jsonObject;

    static ConnectivityManager cm;
    static NetworkInfo activeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Imei ================================
        mostrar_imei = (TextView) findViewById(R.id.mostrar_imei);
        //mostrar_imei.setVisibility(View.GONE); //Ocultar
        numIMEI_R = consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        numIMEI = com.example.prova01.Utilities.numIMEI;

        String a3_getParam1 = getIntent().getStringExtra("a2_param_lang");
        Integer a3_getParam1Int = Integer.parseInt(a3_getParam1);

        //Insert new params
        text4_internet = findViewById(R.id.connect_internet);
        text4_internet.setText(valueOf(isInternetConnection()));

        text0_appWellcome= findViewById(R.id.app_wellcome);
        text0_appWellcome.setText(lang1[a3_getParam1Int][0]);

        text0_sessionInitied= findViewById(R.id.session_initied);
        text0_sessionInitied.setText(lang1[a3_getParam1Int][2]);

        et_geoloc=(TextView) findViewById(R.id.et_geoloc);
        et_geoloc.setText(lang1[a3_getParam1Int][4]);

        et_connect=(TextView) findViewById(R.id.et_connect);
        et_connect.setText(lang1[a3_getParam1Int][5]);

        et_mobile=(TextView) findViewById(R.id.et_connect_mobile);
        et_mobile.setText(lang1[a3_getParam1Int][6]);

        et_reads=(TextView) findViewById(R.id.et_reads);
        et_reads.setText(lang1[a3_getParam1Int][7]);

        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        blockWifi = (Button) findViewById(R.id.blockWifi);
        blockInternet = (Button) findViewById(R.id.blockInternet);
        //blockInternet.setVisibility(View.GONE); //Ocultar
        readings= (Button) findViewById(R.id.readings);


        locationText1_lat = (TextView) findViewById(R.id.loc_lat);
        locationText2_lon = (TextView) findViewById(R.id.loc_lon);
        locationText3_alt = (TextView) findViewById(R.id.loc_alt);
        text4_net = (TextView) findViewById(R.id.connect_net);
        text5_wfi = (TextView) findViewById(R.id.connect_wifi);
        //text5_wfi.setVisibility(View.GONE); //Ocultar
        text6_mobile = (TextView) findViewById(R.id.connect_mobile);
        et_read_wifi = (TextView) findViewById(R.id.read_wifi);
        et_read_field2 = (TextView) findViewById(R.id.read_field2);

        readings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readWIFI();
                readField2();
                handler.removeCallbacks(runnable);
            }
        });


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshParams();
                getLocation();
            }
        });

        blockWifi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                blockWifi();
            }
        });

        blockInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockData();
            }
        });

        /*
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Toast.makeText(getApplicationContext(), "INTERVAL....", Toast.LENGTH_SHORT).show();
            }
        },0,5000);
        */

        handler = new Handler(Looper.getMainLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                // Do the task...
                //handler.postDelayed(this, 2000); // Repetition
                Toast.makeText(getApplicationContext(), "INTERVAL....", Toast.LENGTH_SHORT).show();
            }
        };

        handler.postDelayed(runnable, 5000); //Once

    }


    boolean isMobileConnected() {
        return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    boolean isWifi() {
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    boolean isConnected() {
        ConnectivityManager connMgr =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        cm =(ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    void readWIFI() {
        et_read_wifi.setText("Prova WIFI");
    }

    void readField2() {
        et_read_field2.setText("Prova Field2");
    }

    void getLocation() {

        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);  //>> estat 3
        estat = "Estat 1";

        Log.d("myTag", "++++++++++++++++"+String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)));

        if (ContextCompat.checkSelfPermission(Activity3.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity3.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Activity3.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        if (ContextCompat.checkSelfPermission(Activity3.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity3.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Activity3.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 10, this); // ms, m  //60000, 10

        location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Log.d("myTag", "Estat 2 ==================================================");
        estat="Estat 2";

        try {

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }

    public void blockData() {
        refreshParams();
    }
    public void blockWifi() {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        wifi.setWifiEnabled(false);
        refreshParams();

    }

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onLocationChanged(Location location) {


        //lat2=String.valueOf(location.getLatitude());
        //lon2=String.valueOf(location.getLongitude());

        //ET - 2.1190648 - 41.421518
        //41.389150, 2.144227
        //GI
        //lat2="41.983333333333";
        //lon2="2.8166666666667";

        //Ciutadella
        lat2="40.001776";
        lon2="3.8347893";

        //lat2=String.valueOf(location.getLatitude());
        //lon2=String.valueOf(location.getLongitude());

        locationText1_lat.setText("Latitude: " + lat2);
        locationText2_lon.setText("Longitude: " + lon2);
        locationText3_alt.setText("Altitude: "+location.getAltitude());
        text4_net.setText(estat);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Activity3.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    //Imei ========================================
    private String consultarPermiso(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(Activity3.this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity3.this, permission)) {

                ActivityCompat.requestPermissions(Activity3.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(Activity3.this, new String[]{permission}, requestCode);
            }
        } else {

            imei = obtenerIMEI();
            Toast.makeText(this, permission + " El permiso a la aplicación esta concedido. EMEI: "+imei, Toast.LENGTH_SHORT).show();
        }
        return imei;
    }


    // Con este método consultamos al usuario si nos puede dar acceso a leer los datos internos del móvil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    imei = obtenerIMEI();

                } else {

                    Toast.makeText(Activity3.this, "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private String obtenerIMEI() {
        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return TODO;
            }
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }


    void refreshParams () {

        mostrar_imei.setText(numIMEI);
        text4_net.setText(String.valueOf(isConnected())); //Redundant
        text5_wfi.setText(String.valueOf(isWifi())); //OK
        text6_mobile.setText(String.valueOf(isMobileConnected())); //3G

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url ="https://safe-cell.herokuapp.com/api/device/updateDeviceLocation";

        //	"device_id": 8,
        //	"service_name": "internet",
        //	"is_active": false
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("Response", response);

                try {
                        /*
                        jsonObject = new JSONObject(response);
                        Utilities.getInternet = jsonObject.getString("internet");
                        */
                    Toast.makeText(getApplicationContext(), "Actualització de localització...", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                                /*
                                Snackbar.make(, "Replace with your own action", Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                                        */

                        Log.d("Error.Response", String.valueOf(error));

                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers  = new HashMap<>(super.getHeaders());


                //Map<String, String> headers = super.getHeaders();
                //headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImY5OGMxZjM0NTZjYzQ5YjRkNGI4NzZhMGIwNjJmZGE3Mjc1ZTUxMjFmYTQ1N2IxNWRhOGY2MWQ4OGY3Y2I4MzRlM2U4YzA2Nzg4MzFiY2Q5In0.eyJhdWQiOiI5IiwianRpIjoiZjk4YzFmMzQ1NmNjNDliNGQ0Yjg3NmEwYjA2MmZkYTcyNzVlNTEyMWZhNDU3YjE1ZGE4ZjYxZDg4ZjdjYjgzNGUzZThjMDY3ODgzMWJjZDkiLCJpYXQiOjE1NTcyNDI2MDksIm5iZiI6MTU1NzI0MjYwOSwiZXhwIjoxNTg4ODY1MDA5LCJzdWIiOiI1Iiwic2NvcGVzIjpbXX0.xa3MdKvqkJmvEpa-GvMMBS-8UT80leHAoAwojhHVAPMJ8fA3F9IsWkgC-oyZkWuFyK4E9ybJ5xqAX8622j2NXHIqgVYzRX3cPcLoZS2hBzJT22cpnlLg3vtoZ1qb03YpslnoeUpWFHXcVoNNscwphq5vclFVsYQSr66w2_W7xaSWCZGIo_itIj8qhm8o66QBoMUwWt_PfAYC29q-29gz7rLnx5iDUzyhdyWUTYin3LA9rHb237LCMv2ngvMDnI85vA1hd5ZzZOPzXzYrblPCVVGgGsopZ9UeFe9FVaRCh38tkooICyuq-kpbHMn-r0tT1UGEUs1PxYAREteucDn3lBCpB1jhHZwovM2C0zWa-KLdWDiGNOKINPuvCggzOIqjVDn-6ddXEs3icggyLiu2QpBnjKq7KyB4tr5hw5cvNch_ojBzcbXh0IYbuJNOpGJaGbbGyIX8H1BGyaiGLFV_DZFxb-dapAbRF4OM6J6za5lG-eUIbUONTz6W__BHgmWE3e_4eOyjhsm5rlKfMx4b8iukS7Gtf3SnbLKwNzrCw45j1yzh-fwAXpMvr6TEzrSIa1PlXSOFHZ4TtnzcNnmHpW-QpbI0VraA1NTourJ93A5A7lr2R1DjqCoLH7_o4UDIGNErNLnvPlnFfhR7civFKw0fUIe8bjCNwwumAQSnUzk");
                headers.put("Authorization", "Bearer ".concat(Utilities.getToken));
                return headers;

            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("imei", numIMEI); //numIMEI_R
                params.put("lon", lon2);
                params.put("lat", lat2);

                return params;
            }
        };
        queue.add(postRequest);
    }


}


