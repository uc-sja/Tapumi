package com.utility.tapumi.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.utility.tapumi.R;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity {
    private WebView browserView;
    private AdvancedWebView webView;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private LottieAnimationView loadingView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));



        //Creation of the Webview found in the XML Layout file
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressbar);
        loadingView = findViewById(R.id.loading_view);

        //Enable Jgit avascripts


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(view.getProgress());
                if (view.getProgress() == 100) {
                    Log.d(TAG, "onProgressChanged: "+progress);
                    progressBar.setVisibility(View.GONE);
                    loadingView.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.VISIBLE);

                }
            }
        });

        webView.setWebViewClient(new MyWebViewClient());

        webView.loadUrl("http://tapumi.com/");


    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}
