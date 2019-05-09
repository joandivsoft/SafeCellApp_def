package com.example.prova01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //PEND lan_properties
        /*
        final String[] msg_es={getString(R.string.warn_ES)};
        final String[] msg_en={getString(R.string.warn_EN)};
        final String[] msg_ca={getString(R.string.warn_CA)};
        */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button b_en = findViewById(R.id.b_en);
        Button b_es = findViewById(R.id.b_es);
        Button b_ca = findViewById(R.id.b_ca);

        /*
        String getParam1 = getIntent().getStringExtra("param1");
        tv2b.setText(getParam1);
        */

        b_ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLang=0;
                Snackbar.make(view, com.example.prova01.Utilities.lang1[idLang][1], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                //Intent intent = new Intent(getBaseContext(), SignoutActivity.class);
                intent.putExtra("a2_param_lang", idLang+"");
                startActivity(intent);

            }
        });

        b_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLang=1;
                Snackbar.make(view, com.example.prova01.Utilities.lang1[idLang][1], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                //Intent intent = new Intent(getBaseContext(), SignoutActivity.class);
                intent.putExtra("a2_param_lang", idLang+"");
                startActivity(intent);

            }
        });

        b_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLang=2;
                Snackbar.make(view, com.example.prova01.Utilities.lang1[idLang][1], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                //Intent intent = new Intent(getBaseContext(), SignoutActivity.class);
                intent.putExtra("a2_param_lang", idLang+"");
                startActivity(intent);

            }
        });


        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
