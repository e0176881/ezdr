package com.skytech.ez_dr;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;


public class ForgetPassword extends Activity {
	Button b;
	EditText et,emai;
	TextView tv;
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	  public int currentimageindex=0;
	    Timer timer;
	    TimerTask task;
	    ImageView slidingimage;
	    
	    private int[] IMAGE_IDS = {
	            R.drawable.pic2, R.drawable.pic3,
	            R.drawable.pic4, R.drawable.pic5
	        };
	    SharedPreferences sharedpreferences;
	    public static final String MyPREFERENCES = "MyPrefs" ;
	    public static final String pUserName = "username"; 
	    public static final String pPassword = "password"; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //set content view AFTER ABOVE sequence (to avoid crash)
        //this.setContentView(R.layout.login); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView Pagename = (TextView)findViewById(R.id.pagename);
        TextView lbu = (TextView)findViewById(R.id.lbusername);
       
        TextView lbe = (TextView)findViewById(R.id.lbemail);
        lbu.setTypeface(type);

        lbe.setTypeface(type);
        Pagename.setTypeface(type);
        b = (Button)findViewById(R.id.btnLogin); 
        b.setTypeface(type);
        et = (EditText)findViewById(R.id.username);
        emai = (EditText)findViewById(R.id.email);
        tv = (TextView)findViewById(R.id.tv);
       
        
        b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String uname = et.getText().toString();
				String upw = emai.getText().toString();
				
				if(uname.length() == 0)
				{
					showAlert2();
					
				}
				else if(upw.length() == 0)
				{
					
					showAlert3();
				}
				else
				{
				dialog = ProgressDialog.show(ForgetPassword.this, "", 
                        "Validating user...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	login();					      
					    }
					  }).start();
				}
			} 
		});
       

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.exit_title:
        	this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        return true;

        default:
        return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
       sharedpreferences=getSharedPreferences(MyPREFERENCES, 
    		   MODE_APPEND);
       if (sharedpreferences.getString("username", null) != null)
       {
     
          Intent i = new Intent(getApplicationContext(),skiplogin.class);
          startActivity(i);
       
       }
       super.onResume();
      
    }
    
    void login(){
		try{			
			
			httpclient=new DefaultHttpClient();
			httppost= new HttpPost("http://ezdr.com.sg/appsettings/forgetpassword.php"); // make sure the url is correct.
			//add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("username",et.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("email",emai.getText().toString().trim())); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//response=httpclient.execute(httppost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost, responseHandler);
			System.out.println("Response : " + response); 
			runOnUiThread(new Runnable() {
			    public void run() {
			    //	tv.setText("Response from PHP : " + response);
					dialog.dismiss();
			    }
			});
			
			if(response.contains("success")){
				runOnUiThread(new Runnable() {
				    public void run() {
				    	Toast.makeText(ForgetPassword.this,"Request Success", Toast.LENGTH_SHORT).show();
				    	
				    }
				});
				
				
			}else{
				showAlert();				
			}
			
		}catch(Exception e){
			dialog.dismiss();
			System.out.println("Exception : " + e.getMessage());
		}
	}
    
   
	public void showAlert(){
		ForgetPassword.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
		    	builder.setTitle("Login Error.");
		    	builder.setMessage("Invalid username/email!")  
		    	       .setCancelable(false)
		    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface dialog, int id) {
		    	           }
		    	       });		    	       
		    	AlertDialog alert = builder.create();
		    	alert.show();		    	
		    }
		});
	}
	public void showAlert2(){
		ForgetPassword.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
		    	builder.setTitle("Login Error.");
		    	builder.setMessage("Please enter username (HandPhone)!")  
		    	       .setCancelable(false)
		    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface dialog, int id) {
		    	           }
		    	       });		    	       
		    	AlertDialog alert = builder.create();
		    	alert.show();		    	
		    }
		});
	}
	public void showAlert3(){
		ForgetPassword.this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
		    	builder.setTitle("Login Error.");
		    	builder.setMessage("Please enter password!")  
		    	       .setCancelable(false)
		    	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	           public void onClick(DialogInterface dialog, int id) {
		    	           }
		    	       });		    	       
		    	AlertDialog alert = builder.create();
		    	alert.show();		    	
		    }
		});
	}
}