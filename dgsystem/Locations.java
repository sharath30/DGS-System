package com.example.dgsystem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Locations extends Activity {

	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations);
		
		
		Intent intent = getIntent();
		String lat= intent.getStringExtra("lat");
		String lang= intent.getStringExtra("lang");
		
		Toast.makeText(getApplicationContext(), "Lat: " + lat + "\n Long : " + lang+"\n Adress :", Toast.LENGTH_LONG).show();
		
		//Bundle bundle = getIntent().getExtras();
		//String message = bundle.getString("message");
		
//	     	String lang=Property.lang;
//	     	String lat=Property.lat;
		
		 webView = (WebView) findViewById(R.id.webView);
		 WebSettings webSettings = webView.getSettings();
		 webSettings.setBuiltInZoomControls(true);
		 webView.getSettings().setJavaScriptEnabled(true);
         webView.setWebViewClient(new WebViewClient());
		 String url = "http://192.168.0.30:8083/Dynamic_Grid_System/Viewmapbywebview.jsp?lat="+lat+"&lang="+lang+"";	
                
		 
		 webView.loadUrl(url);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.locations, menu);
		return true;
	}

}
