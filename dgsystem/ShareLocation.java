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

import com.example.dgsystem.MainActivity.ExecuteTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ShareLocation extends Activity {

	double latitude; // latitude
	double longitude; // longitude
	
	EditText getlat, getlong, smsg;
	
	Spinner spinner;
	
	String lat, lang;
	
	 String spindata;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_location);
		
		GPSTracker gps= new GPSTracker(ShareLocation.this);
		
		getlat = (EditText) findViewById(R.id.editlat);
		getlong = (EditText) findViewById(R.id.editlong);
		smsg=(EditText) findViewById(R.id.editcontent);
		
		if(gps.canGetLocation()){
  	       
  	        latitude = gps.getLatitude();
  	        longitude = gps.getLongitude();
  	        
  	        lang= String.valueOf(longitude);
  	        lat= String.valueOf(latitude);
  	        
  	     
  	        getlat.setText(lat);
  	        getlong.setText(lang);
  	     
  	       // tts.speak("Now You are at "+add+" Location", TextToSpeech.QUEUE_FLUSH, null);
  	        	
  	        	Toast.makeText(getApplicationContext(), "Your Location is - \n Lat: " + latitude + "\n Long : " + longitude+"\n Adress :", Toast.LENGTH_LONG).show();	
  	        }
		
		
		new ExecuteTask().execute();
	}
	
	public void share(View v){
		
		new shareLoctask().execute();
		
		
	}
	
	
	
		
	//share location 
	
	class shareLoctask extends AsyncTask<String	, Integer, String>  
    {  
    	String res="";

        @Override  
        protected String doInBackground(String... params) {  
              
           res=PostDataShare(params);  
              
            return res;  
        }  
          
        @Override  
        protected void onPostExecute(String result) {          	
        	
        	Toast.makeText(getApplicationContext(), res, 3000).show();
        	
//        	startActivity(new Intent(ShareLocation.this, ChooseAction.class));
        	
       try {
        
       
        } catch (Exception e) {
    		// TODO: handle exception
    	}
          
    }
    
    
    
    public String PostDataShare(String[] valuse) {  
        String s="";  
        try  
        {  
        HttpClient httpClient=new DefaultHttpClient();  
        HttpPost httpPost=new HttpPost("http://192.168.0.30:8083/Dynamic_Grid_System/test.jsp");  
          
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
        list.add(new BasicNameValuePair("lat", lat));  
        list.add(new BasicNameValuePair("lang",lang));
        
        list.add(new BasicNameValuePair("frmid",UserProperty.uid));
        list.add(new BasicNameValuePair("toid",spindata));        
        list.add(new BasicNameValuePair("smsg", smsg.getText().toString()));
        
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
	
	
	//to get the user email
	
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
        	
        	Toast.makeText(getApplicationContext(), res, 3000).show();
        	
       try {
        	
        	if(res.length()>0){
        		
        		
        		JSONArray jsonArray1 = new JSONArray(res);
				
    			//	Toast.makeText(getApplicationContext(), "else"+jsonArray1.length(), Toast.LENGTH_SHORT).show();
    					
    			            ArrayList<String> emails = new ArrayList<String>();
    					
    						for (int i = 0; i < jsonArray1.length(); i++) {
    							JSONObject obj = jsonArray1.getJSONObject(i);
    						
//    						books.add(obj.getString("b_id")+") "+ obj.getString("branch"));
    							emails.add(obj.getString("email"));	
    						
    					}		
    						
    						final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
  					      
    					      
    					      ArrayAdapter dataAdapter = new ArrayAdapter(ShareLocation.this,android.R.layout.simple_spinner_item,emails);
    					      // Drop down layout style - list view with radio button
    					      dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    					      
    					      // attaching data adapter to spinner
    					      spinner.setAdapter(dataAdapter);
    					      
    					      spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

    					            @Override
    					            public void onItemSelected(AdapterView<?> arg0, View arg1,
    					                    int arg2, long arg3) {
    					            	spindata=spinner.getSelectedItem().toString();

    					            }

    					            @Override
    					            public void onNothingSelected(AdapterView<?> arg0) {
    					                // TODO Auto-generated method stub

    					            }
    					        });
    							
        	} else{
        		 Toast.makeText(getApplicationContext(), "Failed", 3000).show();
        	}
        Toast.makeText(getApplicationContext(), res, 3000).show();  
        
       
        } catch (Exception e) {
    		// TODO: handle exception
    	}
          
    }
    
    
    
    public String PostData(String[] valuse) {  
        String s="";  
        try  
        {  
        HttpClient httpClient=new DefaultHttpClient();  
        HttpPost httpPost=new HttpPost("http://192.168.0.30:8083/Dynamic_Grid_System/GetEmail");  
          
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
//        list.add(new BasicNameValuePair("email", valuse[0]));  
//        list.add(new BasicNameValuePair("pswd",valuse[1]));  
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
		getMenuInflater().inflate(R.menu.share_location, menu);
		return true;
	}

}
