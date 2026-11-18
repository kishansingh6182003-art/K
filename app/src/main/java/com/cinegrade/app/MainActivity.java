package com.cinegrade.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {
  private WebView web;
  private ValueCallback<Uri[]> upload;
  private static final int PICK_MEDIA=41;
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    web=new WebView(this);
    WebSettings settings=web.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setDomStorageEnabled(true);
    settings.setMediaPlaybackRequiresUserGesture(true);
    web.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> cb, FileChooserParams p) {
        if(upload!=null) upload.onReceiveValue(null);
        upload=cb;
        Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("video/*");
        startActivityForResult(Intent.createChooser(i,"वीडियो चुनें"),PICK_MEDIA);
        return true;
      }
    });
    setContentView(web);
    web.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int req,int res,Intent data) {
    super.onActivityResult(req,res,data);
    if(req==PICK_MEDIA && upload!=null) {
      Uri[] result=(res==RESULT_OK && data!=null && data.getData()!=null)?new Uri[]{data.getData()}:null;
      upload.onReceiveValue(result); upload=null;
    }
  }
  @Override public void onBackPressed() {
    if(web!=null && web.canGoBack()) web.goBack(); else super.onBackPressed();
  }
}