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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final EditText eName=findViewById(R.id.fieldName);
        final EditText ePassword=findViewById(R.id.fieldPassword);
        Button fab = findViewById(R.id.fab);
        Intent intent;

        final SharedPreferences pref;
        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Iniciando sesión...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();


                //NOU
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://safe-cell.herokuapp.com/api/auth/login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                fcmToken=response;
                                //access_token


                                //==================

                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    Utilities.getToken = jsonObject.getString("access_token");
                                    SharedPreferences.Editor editor = pref.edit();
                                    //editor.putString("username",ename);
                                    //editor.putString("password",password);
                                    editor.commit();
                                    //Toast.makeText(getApplicationContext(), "Get token: "+Utilities.getToken, Toast.LENGTH_LONG).show();
                                    //==============

                                    Intent intent = new Intent(getApplicationContext(), Activity2.class);
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
                                // error

                                Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();

                        params.put("email", String.valueOf(eName.getText()));  //ara per defecte: iris3@test.com...
                        params.put("password", String.valueOf(ePassword.getText())); //ara per defecte: 123456
                        params.put("remember_me", String.valueOf(true));


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
