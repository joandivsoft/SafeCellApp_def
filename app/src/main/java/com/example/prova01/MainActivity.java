package com.example.prova01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String fcmToken="";
    String name1;
    String password1;
    JSONObject jsonObject;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final EditText eName=findViewById(R.id.fieldName);
        final EditText ePassword=findViewById(R.id.fieldPassword);
      
        Button fab = findViewById(R.id.fab);


        //Session========
        final SharedPreferences pref;
        final SharedPreferences.Editor[] editor = new SharedPreferences.Editor[0];
        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        //Session========

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Iniciando sesión...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();


                //NOU
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://safe-cell.herokuapp.com/api/auth/login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
                    String hhh="";
                            @Override
                            public void onResponse(String response) {
                                // response
                                fcmToken=response;
                                //access_token

                                //==================

                                try {

                                    jsonObject = new JSONObject(response);
                                    Utilities.getToken = jsonObject.getString("access_token");

                                    //Toast.makeText(getApplicationContext(), "Get token: "+Utilities.getToken, Toast.LENGTH_LONG).show();
                                    //==============

                                    intent = new Intent(getApplicationContext(), Activity2.class);
                                    intent.putExtra("param1", "sesión iniciada OKKKKKKK");
                                    startActivity(intent);

                                    Log.d("Response", response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //==================


                            }

                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Snackbar.make(getApplicationContext(), "Error de sesión", Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                                Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        name1=String.valueOf(eName.getText());
                        password1=String.valueOf(ePassword.getText());

                        //Toast.makeText(getApplicationContext(), "editor: ".concat(name1).concat("-").concat(password1), Toast.LENGTH_LONG).show();
                        params.put("email", name1);  //ara per defecte: iris3@test.com...
                        params.put("password", password1); //ara per defecte: 123456
                        params.put("remember_me", String.valueOf(true));
                        Utilities.pwdScreen=password1;

                        //Session=============
                        //editor[0] = pref.edit();
                        //editor.putString("username", name1);
                        //editor.putString("password",password1);
                        //editor.commit();
                        //Session=============


                        return params;
                    }
                };
                queue.add(postRequest);

        }
        });
    }

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
}
