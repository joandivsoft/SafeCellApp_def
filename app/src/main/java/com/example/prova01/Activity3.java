package com.example.prova01;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.prova01.Utilities.lang1;
import static java.lang.String.valueOf;

public class Activity3 extends AppCompatActivity implements LocationListener {

    //PEND
    //Comentaris, classes, codi
    //logo >> transparència

    //PEND
    //Revisar LOC enviament  >> ERR (lectura + enviament) ??

    //REGEX >> url well formed >>   ^http(s)?://([\w-]+.)+[\w-]+(/[\w- ./?%&=])?$
    //Boton mostrar/ocultar Fields hidden >> Falta pwd
    //Bloc screen - https://stackoverflow.com/questions/14352648/how-to-lock-unlock-screen-programmatically
    //Idiomes en Toast
    //Logo


    //No tancar app
    //BlockInternet - - Blocar pantalla - Filtre
    //xifrat de les claus

    //3. Filter web, Block screen, don't close

    //FET - dj
    //Show (admin) amb login
    //Contrassenya para mostrar

    //FET - dc
    //Show/hidden nom >>> // (admin/usuari)
    //Mostrar bloc connexions (sense IMEI)
    //parametros segundos >= 2
    //stop/ start readings


    //FET (dl-dt)
    //Interval >> Block WIFI - onChangeLoc
    //1. Geoloc
    //3 execute methods on reading (WIFI)
    //2. Readings
    //1. Lectura WIFI + position
    //2. Block WIFI


    String fcmToken = "";
    static String numIMEI;
    static String numIMEI_R;
    static String numIMEI_def;

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
    //Button getLocationBtn;
    //Button blockWifi;
    //Button enabledWifi;
    //Button blockInternet;
    Button readings;
    Button btn_show;
    Button btn_hidden;
    Button btn_stopReading;

    TextView et_geoloc;
    TextView et_connect;
    TextView et_mobile;
    TextView et_reads;
    TextView et_read_wifi;
    TextView text_read_wifi;
    TextView et_read_field2;
    TextView text_read_field2;
    TextView et_titulo_IMEI;
    TextView et_connect_wifi;
    TextView et_connect_internet;
    TextView et_connect_net;
    TextView et_alt;
    TextView et_lon;
    TextView et_lat;
    TextView et_Interval;
    EditText textInterval;

    public Handler handler;
    public Runnable runnable;
    public StringRequest postRequest;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 900;

    static LocationManager locationManager;
    static Location location;
    String lat2="PEND";
    String lon2="PEND";
    String alt2="PEND";
    String estatLoc = "Estat 0";
    String estat2 = "Pendent...";
    JSONObject jsonObject;
    RequestQueue queue;
    private String m_Text = "";
    public String intervalSegonsStr;
    public long intervalSegonsLn;

    static ConnectivityManager cm;
    static NetworkInfo activeNetwork;

    //supr
    public URLConnection yc;
    public BufferedReader in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //Imei ================================
        mostrar_imei = (TextView) findViewById(R.id.mostrar_imei);
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

        et_read_wifi=(TextView) findViewById(R.id.et_read_wifi);

        //buttons
        //getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        //blockWifi = (Button) findViewById(R.id.blockWifi);
        //enabledWifi = (Button) findViewById(R.id.enabledWifi);
        //blockInternet = (Button) findViewById(R.id.blockInternet);
        //blockInternet.setVisibility(View.GONE); //Ocultar
        readings= (Button) findViewById(R.id.readings);
        btn_hidden= (Button) findViewById(R.id.btn_hidden);
        btn_show= (Button) findViewById(R.id.btn_show);
        btn_stopReading= (Button) findViewById(R.id.stopReadings);


        locationText1_lat = (TextView) findViewById(R.id.loc_lat);
        locationText2_lon = (TextView) findViewById(R.id.loc_lon);
        locationText3_alt = (TextView) findViewById(R.id.loc_alt);
        text4_net = (TextView) findViewById(R.id.connect_net);
        text5_wfi = (TextView) findViewById(R.id.connect_wifi);
        //text5_wfi.setVisibility(View.GONE); //Ocultar
        text6_mobile = (TextView) findViewById(R.id.connect_mobile);
        text_read_wifi = (TextView) findViewById(R.id.read_wifi);
        et_read_field2 = (TextView) findViewById(R.id.et_read_field2);
        text_read_field2= (TextView) findViewById(R.id.read_field2);
        et_titulo_IMEI= (TextView) findViewById(R.id.titulo_imei);
        et_connect_wifi= (TextView) findViewById(R.id.et_connect_wifi);
        et_connect_internet= (TextView) findViewById(R.id.et_connect_internet);
        et_connect_net= (TextView) findViewById(R.id.et_connect_net);
        et_alt=(TextView) findViewById(R.id.et_alt);
        et_lon=(TextView) findViewById(R.id.et_lon);
        et_lat=(TextView) findViewById(R.id.et_lat);
        et_Interval=(TextView) findViewById(R.id.et_interval);
        textInterval=(EditText) findViewById(R.id.text_interval);

        //Language manage
        et_lat.setText(lang1[a3_getParam1Int][8]);
        et_lon.setText(lang1[a3_getParam1Int][9]);
        et_alt.setText(lang1[a3_getParam1Int][10]);
        et_connect_net.setText(lang1[a3_getParam1Int][11]);
        et_mobile.setText(lang1[a3_getParam1Int][12]);
        et_read_wifi.setText(lang1[a3_getParam1Int][13]);
        et_read_field2.setText(lang1[a3_getParam1Int][14]);
        et_Interval.setText(lang1[a3_getParam1Int][15]);

        //Languages
        btn_stopReading.setText(lang1[a3_getParam1Int][16]);
        readings.setText(lang1[a3_getParam1Int][17]);
        btn_show.setText(lang1[a3_getParam1Int][18]);
        btn_hidden.setText(lang1[a3_getParam1Int][19]);

        //Visibles per defecte
        et_connect_net.setVisibility(View.VISIBLE);
        text4_net.setVisibility(View.VISIBLE);
        et_connect_internet.setVisibility(View.VISIBLE);
        text4_internet.setVisibility(View.VISIBLE);
        et_connect_wifi.setVisibility(View.VISIBLE);
        text5_wfi.setVisibility(View.VISIBLE);
        et_mobile.setVisibility(View.VISIBLE);
        text6_mobile.setVisibility(View.VISIBLE);

        //Gone, invisible (no surt), Visible
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(getApplicationContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
                });

                builder.show();
                */

                /*
                AlertDialog alertDialog = new AlertDialog.Builder(Activity3.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Alert message to be shown");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                */

                if (locationText2_lon.getVisibility() == View.INVISIBLE) {

                    final EditText txtMsg = new EditText(Activity3.this);
                    txtMsg.setHint("Password");
                    new AlertDialog.Builder(Activity3.this).setTitle("Title").setMessage("Message").setView(txtMsg)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String txt1 = txtMsg.getText().toString();
                                    Toast.makeText(getApplicationContext(), Utilities.pwdScreen+"-"+txt1, Toast.LENGTH_SHORT).show();
                                    if (txt1.equals(Utilities.pwdScreen)) {
                                        showAdmin();
                                    }
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //hiddenUser();
                                }
                            })
                            .show();
                }


                    }
                });

        btn_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hiddenUser();

            }
        });

        btn_stopReading.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
            }
        });

        readings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //getFirstReadings();
                //getPeriodicReadings2();
                loop();
            }
        });

        /*
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                //refreshTransitData();
            }
        });
        */


        /*
        blockWifi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setEnabledWifi(false);
            }
        });
        enabledWifi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setEnabledWifi(true);
            }
        });
        */

        /*
        blockInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockData();
            }
        });
        */

        /*
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Toast.makeText(getApplicationContext(), "INTERVAL....", Toast.LENGTH_SHORT).show();
            }
        },0,5000);
        */

        loop();
    }

    void hiddenUser() {

        mostrar_imei.setVisibility(View.INVISIBLE);
        text0_appWellcome.setVisibility(View.INVISIBLE);
        text0_sessionInitied.setVisibility(View.INVISIBLE);
        //getLocationBtn.setVisibility(View.INVISIBLE);
        //blockWifi.setVisibility(View.INVISIBLE);
        //enabledWifi.setVisibility(View.INVISIBLE);
        //blockInternet.setVisibility(View.INVISIBLE);
        readings.setVisibility(View.INVISIBLE);
        locationText1_lat.setVisibility(View.INVISIBLE);
        locationText2_lon.setVisibility(View.INVISIBLE);
        locationText3_alt.setVisibility(View.INVISIBLE);
        text_read_field2.setVisibility(View.INVISIBLE);

        et_connect.setVisibility(View.INVISIBLE);
        et_geoloc.setVisibility(View.INVISIBLE);
        et_reads.setVisibility(View.INVISIBLE);
        et_read_field2.setVisibility(View.INVISIBLE);
        et_titulo_IMEI.setVisibility(View.INVISIBLE);
        et_alt.setVisibility(View.INVISIBLE);
        et_lon.setVisibility(View.INVISIBLE);
        et_lat.setVisibility(View.INVISIBLE);
        text_read_wifi.setVisibility(View.INVISIBLE);
        textInterval.setVisibility(View.INVISIBLE);
        et_Interval.setVisibility(View.INVISIBLE);
        btn_stopReading.setVisibility(View.INVISIBLE);
        et_read_wifi.setVisibility(View.INVISIBLE);

    }
    void showAdmin() {
        mostrar_imei.setVisibility(View.VISIBLE);
        text0_appWellcome.setVisibility(View.VISIBLE);
        text0_sessionInitied.setVisibility(View.VISIBLE);
        //getLocationBtn.setVisibility(View.VISIBLE);
        //blockWifi.setVisibility(View.VISIBLE);
        //enabledWifi.setVisibility(View.VISIBLE);
        //blockInternet.setVisibility(View.VISIBLE);
        readings.setVisibility(View.VISIBLE);
        locationText1_lat.setVisibility(View.VISIBLE);
        locationText2_lon.setVisibility(View.VISIBLE);
        locationText3_alt.setVisibility(View.VISIBLE);
        text_read_field2.setVisibility(View.VISIBLE);

        et_connect.setVisibility(View.VISIBLE);
        et_geoloc.setVisibility(View.VISIBLE);
        et_reads.setVisibility(View.VISIBLE);
        et_read_field2.setVisibility(View.VISIBLE);
        et_titulo_IMEI.setVisibility(View.VISIBLE);
        et_alt.setVisibility(View.VISIBLE);
        et_lon.setVisibility(View.VISIBLE);
        et_lat.setVisibility(View.VISIBLE);
        text_read_wifi.setVisibility(View.VISIBLE);
        textInterval.setVisibility(View.VISIBLE);
        et_Interval.setVisibility(View.VISIBLE);
        btn_stopReading.setVisibility(View.VISIBLE);
        et_read_wifi.setVisibility(View.VISIBLE);
    }

    void loop() {

        handler = new Handler(Looper.getMainLooper());

        runnable = new Runnable() {
            @Override
            public void run() {
                // Do the task...
                if ((textInterval.getText().toString().equals("null")) || (textInterval.getText().toString().equals(""))) {
                    intervalSegonsStr="10000";
                } else {
                    intervalSegonsStr=textInterval.getText().toString();
                }

                intervalSegonsLn=(long) (Float.parseFloat(intervalSegonsStr)*1000);

                if (intervalSegonsLn<10000 ) {
                    intervalSegonsLn=10000;
                }

                handler.postDelayed(this, intervalSegonsLn); // Repetition
                getPeriodicReadings2(); //Execute loop
                // refreshTransitData();

            }
        };

        handler.postDelayed(runnable, 5000); //Once
    }
    void getFirstReadings() {


                /*
                //======== URL - Obrir url un cop filtrada la connexió ================
                try {
                    URL oracle = new URL("http://www.oracle.com/");
                    URLConnection yc = null;
                    yc = oracle.openConnection();
                    in = new BufferedReader(new InputStreamReader(
                            yc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

                //======== URL ================
                */


//Execute first time

        //readWIFI();
        //readField2();

    }

    void getPeriodicReadings2() {
        Toast.makeText(getApplicationContext(), "Reading data....", Toast.LENGTH_SHORT).show();

        if (Utilities.permisos.equals("false")) {
            getPermisosLoc1();
            getPermisosLoc2();

        }
        //Comprovació 1 - numIMEI
        if (Utilities.numIMEI_def.length()>0) {
            //numIMEI_def=numIMEI; //Pren IMEI literal (per a proves)
            Utilities.numIMEI_def=numIMEI_R; //Pren IMEI real
            mostrar_imei.setText(Utilities.numIMEI_def);
        }

        //Comprovació 2 - idDevice
        if (Utilities.idDevice>0) {
            getServiceWifi();
            if (Utilities.getWifi.equals("false")) {
                setEnabledWifi(false);
            } else {
                setEnabledWifi(true);
            }
        } else {
            getIdDevice();
        }

        if (Utilities.idDevice>0 && (Utilities.getWifi.equals("false") || Utilities.getWifi.equals("true"))) {
            getLocation();
        }
        //refreshTransitData();

        //isMobileConnected();
        //isWifi();
        //isConnected();


        text4_net.setText(String.valueOf(isConnected())); //Redundant
        text6_mobile.setText(String.valueOf(isMobileConnected())); //3G
        text5_wfi.setText(String.valueOf(isWifi())); //OK
        //onLocationChanged(location);
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

    void getPermisosLoc1() {

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

    }

    void getPermisosLoc2() {

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

    }

    void getLocation() {

        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
        estatLoc = "Estat 1 - Preinicio localización";

        Log.d("myTag", "++++++++++++++++"+String.valueOf(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)));

        getPermisosLoc1();

        getPermisosLoc2();

        //Parametres localització
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 5, this); // ms, m  //60000, 10

        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Log.d("myTag", "Estat 2 ==================================================");
        estatLoc="Estat 2 - Inicio localización";

        try {

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }

    }

    public void blockData() {
        //refreshTransitData();
    }


    public void setEnabledWifi(boolean block) {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        wifi.setWifiEnabled(block);
    }

    public boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onLocationChanged(Location location) {

        //Canvia i envia...

        //GI
        //lat2="41.983333333333";
        //lon2="2.8166666666667";
        //alt2="0.0";

        //Ciutadella
        //lat2="40.001776";
        //lon2="3.8347893";

        lat2=String.valueOf(location.getLatitude());
        lon2=String.valueOf(location.getLongitude());
        alt2=String.valueOf(location.getAltitude());

        locationText1_lat.setText(lat2);
        locationText2_lon.setText(lon2);
        locationText3_alt.setText(alt2);
        text4_net.setText(estatLoc);
        refreshTransitData();
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


    void refreshTransitData() {

        //Envia loc i rep internet
        /*
        mostrar_imei.setText(Utilities.numIMEI_def);
        text4_net.setText(String.valueOf(isConnected())); //Redundant
        text5_wfi.setText(String.valueOf(isWifi())); //OK
        text6_mobile.setText(String.valueOf(isMobileConnected())); //3G
        */

        queue = Volley.newRequestQueue(getApplicationContext());

        String url ="https://safe-cell.herokuapp.com/api/device/updateDeviceLocation";

        //	"device_id": 8,
        //	"service_name": "internet",
        //	"is_active": false
        postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("Response", response);

                try {
                    jsonObject = new JSONObject(response);
                    Utilities.getInternet = jsonObject.getString("internet");
                    Toast.makeText(getApplicationContext(), "Actualització de serveis...", Toast.LENGTH_SHORT).show();

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

                params.put("imei", Utilities.numIMEI_def);
                params.put("lon", lon2);
                params.put("lat", lat2);

                return params;
            }
        };
        queue.add(postRequest);
    }

    void getIdDevice() {


        queue = Volley.newRequestQueue(getApplicationContext());

        String url ="https://safe-cell.herokuapp.com/api/device/getDeviceIdByImei/".concat(Utilities.numIMEI_def);

        //	"device_id": 8,
        //	"service_name": "internet",
        //	"is_active": false
        //StringRequest postRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){
        postRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("Response", response);

                try {
                    jsonObject = new JSONObject(response);
                    int id2 = Integer.parseInt(jsonObject.getString("device_id"));
                    Utilities.idDevice=id2;
                    text_read_field2.setText(String.valueOf(Utilities.idDevice));

                    Toast.makeText(getApplicationContext(), "Device Id: "+Utilities.idDevice, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    text_read_field2.setText(String.valueOf("ID_device_error_try"));
                    e.printStackTrace();
                }

            }
        },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        text_read_field2.setText(String.valueOf("ID_device_error_response"));
                        Log.d("Error.Response", String.valueOf(error));

                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers  = new HashMap<>(super.getHeaders());


                //Map<String, String> headers = super.getHeaders();
                headers.put("Authorization", "Bearer ".concat(Utilities.getToken));
                return headers;

            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                //params.put("device_id", Utilities.numIMEI_def);

                return params;
            }
        };
        queue.add(postRequest);


    }

    void getServiceWifi() {

        //String url ="https://safe-cell.herokuapp.com/api/service/getStatuses/".concat(String.valueOf(Utilities.idDevice));
        queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://safe-cell.herokuapp.com/api/service/getStatuses/".concat(String.valueOf(Utilities.idDevice));

        //	"device_id": 8,
        //	"service_name": "internet",
        //	"is_active": false
        postRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d("Response", response);

                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("services");
                    Log.d("Error.Response", String.valueOf(jsonArray));
                    //ArrayList<String> Lista = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject json = jsonArray.getJSONObject(i);
                            //Aquí se obtiene el dato y es guardado en una lista
                            //Lista.add(json.getString("name"));

                            if (json.getString("name").equals("internet")) {
                                Utilities.getWifi=json.getString("status");
                                Toast.makeText(getApplicationContext(), "WIFI service running?"+Utilities.getWifi, Toast.LENGTH_SHORT).show();
                                text_read_wifi.setText(Utilities.getWifi);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


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
                headers.put("Authorization", "Bearer ".concat(Utilities.getToken));
                return headers;

            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                //params.put("device_id", Utilities.numIMEI_def);

                return params;
            }
        };
        queue.add(postRequest);


    }

}


