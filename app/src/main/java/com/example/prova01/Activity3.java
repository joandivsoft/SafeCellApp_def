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
import android.support.annotation.NonNull;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.prova01.Utilities.lang1;
import static java.lang.String.valueOf;

public class Activity3 extends AppCompatActivity implements LocationListener {

    String fcmToken = "";
    static String numIMEI;
    static String numIMEI_R;

    //Imei ========================================
    TextView mostrar_imei;
    public static String imei;
    static final Integer PHONESTATS = 0x1;
    private final String TAG = Activity3.class.getSimpleName();
    //Imei ========================================

    Button getIMEI;
    Button getLocationBtn;
    Button blockWifi;
    Button blockInternet;
    TextView locationText;
    TextView locationText2;
    TextView locationText3;
    TextView locationText4;
    TextView locationText5;
    TextView locationText6;
    TextView locationText7;
    TextView locationText8;
    TextView locationText9;
    TextView locationText10;

    final int MY_PERMISSIONS_REQUEST_LOCATION = 900;

    static LocationManager locationManager;
    static Location location1;
    String lat2;
    String lon2;
    String estat = "Estat 0";
    String estat2 = "Pendent...";

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
        numIMEI_R = consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        numIMEI = com.example.prova01.Utilities.numIMEI;

        String a3_getParam1 = getIntent().getStringExtra("a2_param_lang");
        Integer a3_getParam1Int = Integer.parseInt(a3_getParam1);

        //Insert new params

        TextView tv2b = findViewById(R.id.tv2);
        tv2b.setText("With Internet Connection? " + valueOf(isInternetConnection()));

        TextView tv3ab = findViewById(R.id.tv3a);
        tv3ab.setText(lang1[a3_getParam1Int][0]);

        TextView tv3bb = findViewById(R.id.tv3b);
        tv3bb.setText(lang1[a3_getParam1Int][2]);

        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        blockWifi = (Button) findViewById(R.id.blockWifi);
        blockInternet = (Button) findViewById(R.id.blockInternet);
        locationText = (TextView) findViewById(R.id.locationText);
        locationText2 = (TextView) findViewById(R.id.locationText2);
        locationText3 = (TextView) findViewById(R.id.locationText3);
        locationText4 = (TextView) findViewById(R.id.locationText4);
        locationText5 = (TextView) findViewById(R.id.tv5);
        locationText6 = (TextView) findViewById(R.id.tv6);
        locationText7 = (TextView) findViewById(R.id.tv7);
        locationText8 = (TextView) findViewById(R.id.tv8);
        locationText9 = (TextView) findViewById(R.id.tv9);
        locationText10 = (TextView) findViewById(R.id.tv10);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OK
                getLocation();
                locationText4.setText("Is connected? " + String.valueOf(isConnected())); //Redundant
                locationText5.setText("Is Wifi connected? " + String.valueOf(isWifi())); //OK
                locationText6.setText("Is Mobile connected? " + String.valueOf(isMobileConnected())); //3G
                locationText7.setText("PEND...");
                locationText8.setText("PEND...");
                locationText9.setText("PEND...");
                locationText10.setText("PEND...");

                putIMEI(numIMEI);
                mostrar_imei.setText(numIMEI);

            }
        });

        //OK
        blockWifi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
                wifi.setWifiEnabled(false);
            }
        });

        blockInternet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10, this); // ms, m

        location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Log.d("myTag", "Estat 2 ==================================================");
        estat="Estat 2";

        try {

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }


    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onLocationChanged(Location location) {

        lat2=String.valueOf(location.getLatitude());
        lon2=String.valueOf(location.getLongitude());
        locationText.setText("Latitude: " + lat2);
        locationText2.setText("Longitude: " + lon2);
        locationText3.setText("Altitude: "+location.getAltitude());
        locationText4.setText(estat);
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

void putIMEI (final String imei3) {


    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

    String url ="https://safe-cell.herokuapp.com/api/device/updateDeviceLocation";
    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    Log.d("Response", response);
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
            params.put("lon", lon2);  //41.389150, 2.144227
            params.put("lat", lat2);  //

            //params.put("lon", "2.1190648");
            //params.put("lat", "41.421518");


            return params;
        }
    };
    queue.add(postRequest);
}


}


