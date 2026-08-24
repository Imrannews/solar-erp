package com.mhsolar.dailyrates;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {
  private WebView web;
  private AdView banner;

  @SuppressLint("SetJavaScriptEnabled")
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);

    MobileAds.initialize(this, initializationStatus -> {});

    LinearLayout root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);

    web = new WebView(this);
    WebSettings settings = web.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setAllowFileAccess(true);
    settings.setLoadsImagesAutomatically(true);
    web.setWebViewClient(new WebViewClient());
    web.setWebChromeClient(new WebChromeClient());
    web.loadUrl("https://raw.githubusercontent.com/Imrannews/solar-erp/main/index.html");

    root.addView(web, new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

    banner = new AdView(this);
    banner.setAdUnitId(getString(com.mhsolar.dailyrates.R.string.admob_banner_id));
    banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, getResources().getDisplayMetrics().widthPixels));
    LinearLayout.LayoutParams adParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    adParams.gravity = Gravity.CENTER;
    root.addView(banner, adParams);
    banner.loadAd(new AdRequest.Builder().build());

    setContentView(root);
  }

  @Override public void onBackPressed() {
    if (web.canGoBack()) web.goBack(); else super.onBackPressed();
  }

  @Override protected void onDestroy() {
    if (banner != null) banner.destroy();
    if (web != null) web.destroy();
    super.onDestroy();
  }
}
