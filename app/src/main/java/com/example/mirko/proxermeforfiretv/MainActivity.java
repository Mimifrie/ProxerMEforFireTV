package com.example.mirko.proxermeforfiretv;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.mirko.proxermeforfiretv.ProxerME;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;


import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.*;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

public class MainActivity extends ActionBarActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private Button button = (Button) findViewById(R.id.button);
    private static AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getStringExtra("link") != null){
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(getIntent().getStringExtra("link"));
        }
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);


        /*PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        client.setCookieStore(cookieStore);
        WebView webView;
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyAppWebViewClient());
        //webView.loadUrl("http://proxer.me");
        //client.setEnableRedirects(true);
        //client.setTimeout(6000);
        String cookies = CookieManager.getInstance().getCookie("http://proxer.me");
        TextView textView = (TextView)findViewById(R.id.textView);
        String[] splitCookies = cookies.split(";");
        for(String moreCookies:splitCookies){
            String[] mostCookies = moreCookies.split("=");
            BasicClientCookie newCookie = new BasicClientCookie(mostCookies[0],mostCookies[1]);
            newCookie.setVersion(1);
            newCookie.setDomain(".proxer.me");
            newCookie.setPath("/");
            cookieStore.addCookie(newCookie);
        }
        textView.setText(cookieStore.getCookies().toString());*/
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

    public void buttonPrivateAnimes(View view){
        Toast.makeText(view.getContext(),
                "Button clicked index Work", Toast.LENGTH_SHORT)
                .show();
        String url = "https://proxer.me/login?format=json&action=login";
        final View view2 = view;
        RequestParams params = new RequestParams();
        EditText editTextUsername = (EditText)findViewById(R.id.editText);
        EditText editTextPassword = (EditText)findViewById(R.id.editText2);
        params.put("username", editTextUsername.getText());
        params.put("password", editTextPassword.getText());
        client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                        try {
                            JSONObject obj = new JSONObject(res);
                            client.get("http://proxer.me/user/" + obj.getInt("uid") + "/anime?format=raw", new TextHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, String res) {
                                            // called when response HTTP status is "200 OK"
                                            startPrivateAnimes(res);
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                                        }
                                    }
                            );
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                    }
                }
        );
    }

    private void startPrivateAnimes(String html){
        Intent intent = new Intent(this, privateAnimes.class);

        intent.putExtra("html",html);
        startActivityForResult(intent, 0);
    }
}
