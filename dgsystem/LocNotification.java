package com.example.dgsystem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.dgsystem.ShareLocation.shareLoctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LocNotification extends Activity {

	EditText lang, lat, smsg, skey;
	
	String str1="";
	
	Button bView;
	String s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loc_notification);
		
		lang=(EditText) findViewById(R.id.editlong);
		lat=(EditText) findViewById(R.id.editlat);
		smsg=(EditText) findViewById(R.id.editcontent);
		
		
		skey=(EditText) findViewById(R.id.skey);
		
		String data = getIntent().getStringExtra("str123").toString();

		String str[]=data.split("@");
		
		str1=str[0];
		
	//	Toast.makeText(getApplicationContext(), "s1= "+str[0]+" s2= "+str[1]+" s3= "+str[2]+" s4= "+str[3], Toast.LENGTH_LONG).show();
		
	 s=str[0];
		
	//	Toast.makeText(getApplicationContext(), data, 3000).show();
		
		
		lat.setText(str[1]);
		lang.setText(str[2]);
		smsg.setText(str[3]);
		
	}
	//System.out.print(""+s);
	
	public void callDecrypt(View v){
		new ExecuteTask().execute();
		
	}
	
	
	
	public void callView(View V){	
		
	
	//	Toast.makeText(getApplicationContext(), lat.getText().toString()+" "+lang.getText().toString(), 3000).show();
		
		Intent intent = new Intent(LocNotification.this, Locations.class);
		
		intent.putExtra("lat", lat.getText().toString());
		intent.putExtra("lang", lang.getText().toString());
			
		startActivity(intent);
	}
	
	
	class ExecuteTask extends AsyncTask<String	, Integer, String>  
    {  
    	String res="";

        @Override  
        protected String doInBackground(String... params) {  
              
           res=PostData(params);  
              
            return res;  
        }  
          
        @Override  
        protected void onPostExecute(String result) {  
        	
        	//Toast.makeText(getApplicationContext(), res, 3000).show();
        	
       try {
        	
        	if(res.length()>0){
        		String str[]=res.split("@");
        		
        		//Toast.makeText(getApplicationContext(), str[0]+" "+str[1]+" "+str[2], 3000).show();
        		Toast.makeText(getApplicationContext(), " lat="+str[1]+" "+str[2], 3000).show();
//        		Toast.makeText(getApplicationContext(), data, 3000).show();
        		
        		lat.setText(str[0]);
        		lang.setText(str[1]);
        		smsg.setText(str[2]);	   	
    						   	
        	} else{
       		 Toast.makeText(getApplicationContext(), "Failed", 3000).show();
       	}
     //  Toast.makeText(getApplicationContext(), res, 3000).show();  
       
       
        } catch (Exception e) {
    		// TODO: handle exception
    	}
          
    }
    
    
    
    public String PostData(String[] valuse) {  
        String s="";  
        try  
        {  
        HttpClient httpClient=new DefaultHttpClient();  
        HttpPost httpPost=new HttpPost("http://192.168.0.30:8083/Dynamic_Grid_System/Recvdata");  
          
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
        
        list.add(new BasicNameValuePair("id",UserProperty.uid));
       
		list.add(new BasicNameValuePair("nid",str1));
        list.add(new BasicNameValuePair("skey",skey.getText().toString()));
        
        httpPost.setEntity(new UrlEncodedFormEntity(list));  
        HttpResponse httpResponse=  httpClient.execute(httpPost);  
      
        HttpEntity httpEntity=httpResponse.getEntity();  
//        s= readResponse(httpResponse); 
        
        
        InputStream is=null;   
        String return_text="";  
        try {  
            is=httpResponse.getEntity().getContent();  
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));  
            String line="";  
            StringBuffer sb=new StringBuffer();  
            while ((line=bufferedReader.readLine())!=null)  
            {  
            sb.append(line);  
            }  
            s=sb.toString();  
        } catch (Exception e)  
        {  
              
        } 
        
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    
        }  
        catch(Exception exception)  {}  
//        return s;
        return s;
      
          
    }
    
	
    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loc_notification, menu);
		return true;
	}

}
