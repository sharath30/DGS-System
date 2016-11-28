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
import org.json.JSONException;
import org.json.JSONObject;

import com.example.dgsystem.ShareLocation.ExecuteTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewNotification extends Activity {

	ListView lv;
	
	String selectedmsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notification);
		
		new ExecuteTask().execute();
		
	    
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
        	
        //	Toast.makeText(getApplicationContext(), res, 3000).show();
        	
       try {
        	
        	if(res.length()>0){
        		
        		
        		JSONArray jsonArray1 = new JSONArray(res);
				
//        		final ArrayList<message> mesg=new ArrayList<message>();
    			//	Toast.makeText(getApplicationContext(), "else"+jsonArray1.length(), Toast.LENGTH_SHORT).show();
    					
    			           ArrayList<String> msgs = new ArrayList<String>();
    			           final  ArrayList<message> msgs123 = new ArrayList<message>();
    					
    			            int temp=1;
    			            
    						for (int i = 0; i < jsonArray1.length(); i++) {
    							JSONObject obj = jsonArray1.getJSONObject(i);
    							message obj1=new message();
    							obj1.setMsg(obj.getString("msg"));
   				
    							msgs.add(temp+ " message");	
    							
    							msgs123.add(obj1);
    							
    							temp++;
    						
    					}	

    						ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_fac_msgs,msgs);
    							
    							lv=(ListView)findViewById(R.id.listcmp); 
    						   	lv.setAdapter(adapter);
    						   	
    						   	
    						   	lv.setOnItemClickListener(new OnItemClickListener() {
    						        public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
    						          String selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
    						          
    						          
    						        
    						          String str123 = msgs123.get(myItemInt).getMsg();
    						          
    						             						          
    						        //  Toast.makeText(getApplicationContext(), str123, 3000).show();
    						          
    						          Intent i = new Intent(ViewNotification.this, LocNotification.class);  
    						         
    						          i.putExtra("str123", str123);
    						          
    						          startActivity(i);
    						          
    						        }                 
    						  });
        	} else{
       		 Toast.makeText(getApplicationContext(), "Failed or No results found", 3000).show();
       	}
      // Toast.makeText(getApplicationContext(), res, 3000).show();  
       
       
        } catch (Exception e) {
    		// TODO: handle exception
    	}
          
    }
    
    
    
    public String PostData(String[] valuse) {  
        String s="";  
        try  
        {  
        HttpClient httpClient=new DefaultHttpClient();  
        HttpPost httpPost=new HttpPost("http://192.168.0.30:8083/Dynamic_Grid_System/Viewmsgs");  
          
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
        
        list.add(new BasicNameValuePair("id",UserProperty.uid));
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
		getMenuInflater().inflate(R.menu.view_notification, menu);
		return true;
	}

}
