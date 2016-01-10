package com.example.mirko.proxermeforfiretv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Mirko on 07.01.2016.
 */
public class MyAppWebViewClient extends WebViewClient {
    public String urlLove;
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        return false;
    }
    @Override
    public void onPageStarted(WebView view, String url,
                              Bitmap favicon) {
    }

    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
    }
}
