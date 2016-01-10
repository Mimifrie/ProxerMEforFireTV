package com.example.mirko.proxermeforfiretv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

public class anime_episodes extends AppCompatActivity {
    private static ArrayList<ArrayList<String>> animeEpisodes;
    private static AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_episodes);
        String url = "https://proxer.me/login?format=json&action=login";
        client.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
        client.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.addHeader("Accept-Encoding", "gzip, deflate, sdch");
        client.addHeader("Accept-Language", "de-DE,de;q=0.8,en-US;q=0.6,en;q=0.4,ja;q=0.2");
        client.addHeader("Cache-Control", "max-age=0");
        client.addHeader("Connection", "keep-alive");
        client.addHeader("Upgrade-Insecure-Requests", "1");
        final ProxerME proxerME = new ProxerME();
        LinearLayout ll = (LinearLayout) findViewById(R.id.listView3);
        int index = 0;
        animeEpisodes = proxerME.testAnimeEpisodes(getIntent().getStringExtra("html"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textViewState = new TextView(this);
        //textViewState.setText("blub");
        ll.addView(textViewState, params);
        String splitUrl[] = getIntent().getStringExtra("link").split("info/");
        String splitUrl2[] = splitUrl[1].split("/");
        final String animeNumber = splitUrl2[0];
        for(int i = 0; i< animeEpisodes.size(); i++){
                final Button btn = new Button(this);
                btn.setId(i + 0);
                final int id_ = btn.getId();
                final int iFinal = i;
                btn.setText(getIntent().getStringExtra("name") + " " + animeEpisodes.get(i).get(0) + " " + animeEpisodes.get(i).get(2));
                //btn.setText("Button");
                btn.setBackgroundColor(Color.rgb(70, 80, 90));
                ll.addView(btn, params);
                Button btn1 = ((Button) findViewById(id_));
                final LinearLayout llFinal = ll;
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(final View view) {
                        client.get("http://proxer.me/watch/"+ animeNumber + "/" +  animeEpisodes.get(iFinal).get(0) + "/"+animeEpisodes.get(iFinal).get(2)+"?format=raw", new TextHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String res) {
                                        // called when response HTTP status is "200 OK"
                                        String getHost = proxerME.getHost(animeEpisodes.get(iFinal).get(0), res);
                                        if(getHost.equals("notAvaible") || getHost.isEmpty() || getHost.equals("parseFail") || getHost.equals("noStreams"))
                                            Toast.makeText(view.getContext(),
                                                    "Kein unterstützter Stream verfügbar: " + getHost, Toast.LENGTH_SHORT)
                                                    .show();
                                        else {
                                            /*Toast.makeText(view.getContext(),
                                                    "Kein unterstützter Stream verfügbar: " + getHost, Toast.LENGTH_SHORT)
                                                    .show();*/
                                            intentCall(getHost);
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                                    }
                                }
                        );
                    }
                });
        }
    }
    public void intentCall(String url){
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
