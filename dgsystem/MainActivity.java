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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText email,pass;
	
	String displayText;
	String editTextUsername;
	
	Button login;
	    
	String editTextPassword;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        email=(EditText) findViewById(R.id.temail);
        pass=(EditText) findViewById(R.id.tpass);
        
        login=(Button) findViewById(R.id.blogin);
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 String s1=email.getText().toString();  
	                String s2=pass.getText().toString();
	                
	                new ExecuteTask().execute(s1,s2); 
			}
		});
    }
    
//    public void loginservice(View v){
//    	name=(EditText) findViewById(R.id.temail);
//    	password=(EditText) findViewById(R.id.tpass);
//    	
//    	editTextUsername=name.getText().toString();
//    	editTextPassword=password.getText().toString();
//    	
//    	if (name.getText().length() != 0 && password.getText().length() != 0) {
//
//			AsyncCallWS task = new AsyncCallWS();
//		
//			task.execute();
//		}
//    }
    
    
    
    
    
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
//        progressBar.setVisibility(View.GONE);  
        //progess_msz.setVisibility(View.GONE);  
        	
        	Toast.makeText(getApplicationContext(), res, 3000).show();
        	
        	if(!res.equalsIgnoreCase("0")){
        		
        		
        		UserProperty up=new UserProperty();
        		up.uid=res;
        		
        	Intent intent = new Intent(MainActivity.this,
        			ChooseAction.class); 
			
		    startActivity(intent);   	
        	} else{
        		 Toast.makeText(getApplicationContext(), "Failed", 3000).show();
        	}
        Toast.makeText(getApplicationContext(), res, 3000).show();  
        }  
          
    }
    
    
    
    public String PostData(String[] valuse) {  
        String s="";  
        try  
        {  
        HttpClient httpClient=new DefaultHttpClient();  
        HttpPost httpPost=new HttpPost("http://192.168.0.30:8083/Dynamic_Grid_System/Loginws");  
          
        List<NameValuePair> list=new ArrayList<NameValuePair>();  
        list.add(new BasicNameValuePair("email", valuse[0]));  
        list.add(new BasicNameValuePair("pswd",valuse[1]));  
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
    
    
//    public String readResponse(HttpResponse res) {  
//        InputStream is=null;   
//        String return_text="";  
//        try {  
//            is=res.getEntity().getContent();  
//            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));  
//            String line="";  
//            StringBuffer sb=new StringBuffer();  
//            while ((line=bufferedReader.readLine())!=null)  
//            {  
//            sb.append(line);  
//            }  
//            return_text=sb.toString();  
//        } catch (Exception e)  
//        {  
//              
//        }  
//        return return_text;  
//          
//    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
