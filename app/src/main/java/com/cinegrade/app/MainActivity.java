package com.cinegrade.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.*;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {
  private WebView web;
  private ValueCallback<Uri[]> upload;
  private static final int PICK_IMAGE=41;
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    web=new WebView(this);
    web.getSettings().setJavaScriptEnabled(true);
    web.getSettings().setDomStorageEnabled(true);
    web.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> cb, FileChooserParams p) {
        upload=cb;
        Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*"); i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(i,"फोटो चुनें"),PICK_IMAGE);
        return true;
      }
    });
    setContentView(web);
    web.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int req,int res,Intent data) {
    super.onActivityResult(req,res,data);
    if(req==PICK_IMAGE && upload!=null) {
      Uri[] result=(res==RESULT_OK && data!=null && data.getData()!=null)?new Uri[]{data.getData()}:null;
      upload.onReceiveValue(result); upload=null;
    }
  }
  @Override public void onBackPressed() {
    if(web!=null && web.canGoBack()) web.goBack(); else super.onBackPressed();
  }
}
