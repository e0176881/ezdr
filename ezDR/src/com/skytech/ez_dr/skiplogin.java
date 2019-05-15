package com.skytech.ez_dr;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class skiplogin extends Activity {
	HttpPost httppost;
	StringBuffer buffer;
	
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	public int currentimageindex=0;
    Timer timer;
    TimerTask task;
    ImageView slidingimage;
    TextView tv;
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
        this.setContentView(R.layout.mainscreen); 
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.mainscreen);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView Pagename = (TextView)findViewById(R.id.pagename);
        Pagename.setTypeface(type);
        
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_APPEND); // 0 - for private mode
        tv = (TextView)findViewById(R.id.TextView01);
        tv.setTypeface(type);
       	tv.setText("Welcome " + pref.getString("fullname", null));
        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(R.drawable.staticimage);
       	/*
       	
final Handler mHandler = new Handler();
        
        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
 
                AnimateandSlideShow();
 
            }
        };
 
        int delay = 3; // delay for 1 sec.
 
        int period = 6000; // repeat every 4 sec.
 
        Timer timer = new Timer();
 
        timer.scheduleAtFixedRate(new TimerTask() {
 
        public void run() {
 
             mHandler.post(mUpdateResults);
 
        }
 
        }, delay, period);
        */
       	
        
     
        ImageButton aboutus = (ImageButton) findViewById(R.id.imgbtnAbout);
        ImageButton book = (ImageButton) findViewById(R.id.imgbtnBook);
        ImageButton history = (ImageButton) findViewById(R.id.imgbtnHistory);
        ImageButton clinic = (ImageButton) findViewById(R.id.imgbtnClinics);
        ImageButton logout = (ImageButton) findViewById(R.id.imglogout);
        // Listening to Login Screen link
        aboutus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // --- find the text view --
            	Intent launchmain= new Intent(getApplicationContext(),MainOperation.class);
                startActivity(launchmain);
            }
         });
        // Listening to register new account link
        book.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), Book.class);
				startActivity(i);
			}
		});
  history.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), History.class);
				startActivity(i);
			}
		});
  clinic.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// Switching to Register screen
			Intent i = new Intent(getApplicationContext(), clinic.class);
			startActivity(i);
		}
	});
  logout.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// Switching to Register screen
			SharedPreferences settings = getSharedPreferences("MyPrefs", 0);
			//settings.edit().clear().commit();
			settings.edit().remove("username").commit();
			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
		}
	});
  
  Thread thread = new Thread()
  {
      @Override
      public void run() {
         
           Register();
        
      }
  };
  thread.start();
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
    
    private void AnimateandSlideShow() {
      	 
        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
 
        currentimageindex++;
 
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.layout.custom_anim);
 slidingimage.setScaleType(ScaleType.FIT_XY);
          slidingimage.startAnimation(rotateimage);
 
    }
    
    void Register(){
  		try{			
  			 
  			httpclient=new DefaultHttpClient();
  			httppost= new HttpPost("http://ezdr.com.sg/appsettings/gethistory.php"); // make sure the url is correct.
  			nameValuePairs = new ArrayList<NameValuePair>(7);
  			sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_APPEND);
  	        


  			nameValuePairs.add(new BasicNameValuePair("username",sharedpreferences.getString(pUserName, "").trim()));
  			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			
  			ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			final String response = httpclient.execute(httppost, responseHandler);
  			System.out.println("Response : " + response); 
  		
  			
  			if(response != null){
  				if( response != "" & response != "empty")
  				{
  				runOnUiThread(new Runnable() {
				    public void run() {
				    	String[] values = response.split(",");
				    	//allergies.setText(response);
				    	Editor editor = sharedpreferences.edit();
						editor.putString("NRIC", values[0].toString().trim()); // Storing string
						editor.putString("fullname", values[1].toString().trim());
						editor.putString("address",values[2].toString().trim());
						editor.putString("allergies",values[3].toString().trim());
						editor.putString("medication",values[4].toString().trim());
						editor.putString("medicalhistory",values[5].toString().trim());
						
						editor.commit();
				    }
				});
  				} else
  				{

  				}
  				
  			}else{				
  			}
  			
  		}catch(Exception e){
  			dialog.dismiss();
  			System.out.println("Exception : " + e.getMessage());
  		}
  	}
}