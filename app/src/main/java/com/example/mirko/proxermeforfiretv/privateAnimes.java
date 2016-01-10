package com.example.mirko.proxermeforfiretv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mirko.proxermeforfiretv.ProxerME;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class privateAnimes extends AppCompatActivity {
    private static ArrayList<ArrayList<ArrayList<String>>> privateAnimesAll = new ArrayList<ArrayList<ArrayList<String>>>();
    ProxerME proxerME = new ProxerME();
    private static AsyncHttpClient client = new AsyncHttpClient();
    private boolean isCreated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_animes);
        loadViewAll();
    }

    private void loadViewAll(){
        try {
            LinearLayout ll = (LinearLayout) findViewById(R.id.listView2);
            int i = 0;
            int index = 0;
            privateAnimesAll = proxerME.testPrivateAnimes(getIntent().getStringExtra("html"));
            for (ArrayList<ArrayList<String>> privateAnimesState : privateAnimesAll) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView textViewState = new TextView(this);
                switch (i) {
                    case 0:
                        textViewState.setText("Geschaut:");
                        break;
                    case 1:
                        textViewState.setText("Am Schauen:");
                        break;
                    case 2:
                        textViewState.setText("Wird noch geschaut:");
                        break;
                }
                ll.addView(textViewState, params);
                for (final ArrayList<String> privateAnimeSingleAnimeList : privateAnimesState) {
                    if (privateAnimeSingleAnimeList.size() > 2) {
                        Button btn = new Button(this);
                        btn.setId(index + 0);
                        final int id_ = btn.getId();
                        btn.setText("Status:" + privateAnimeSingleAnimeList.get(0)+ "|||||Name:" + privateAnimeSingleAnimeList.get(3));
                        //btn.setText("Button");
                        btn.setBackgroundColor(Color.rgb(70, 80, 90));
                        ll.addView(btn, params);
                        Button btn1 = ((Button) findViewById(id_));
                        btn1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(),
                                        "Button clicked index = " + privateAnimeSingleAnimeList.get(2), Toast.LENGTH_SHORT)
                                        .show();
                                client.get("http://proxer.me" + privateAnimeSingleAnimeList.get(2)+"?format=json", new TextHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, String res) {
                                                // called when response HTTP status is "200 OK"
                                                intentCall(privateAnimeSingleAnimeList.get(2), privateAnimeSingleAnimeList.get(3),res);
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                                            }
                                        }
                                );

                            }
                        });
                        index++;
                    }
                }
                i++;
            }
        }catch (Exception e){


        }
    }
    public void intentCall(String url, String animeName,String html){
        Intent intent = new Intent(this, anime_episodes.class);
        intent.putExtra("link",url);
        intent.putExtra("name",animeName);
        intent.putExtra("html",html);
        startActivityForResult(intent, 0);
    }


}
