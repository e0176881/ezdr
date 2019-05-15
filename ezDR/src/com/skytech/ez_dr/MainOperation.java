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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;


public class MainOperation extends Activity {
	HttpPost httppost;
	StringBuffer buffer;
	TextView about;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.aboutus); 
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.aboutus);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView Pagename = (TextView)findViewById(R.id.pagename);
        Pagename.setTypeface(type);
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
     //   Button backButton = (Button)this.findViewById(R.id.back);
     /*   TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        }); */
        about = (TextView)findViewById(R.id.lblmessage);
        
        
        about.setTypeface(type);
ImageButton back = (ImageButton) findViewById(R.id.imageButton1);
        
        back.setOnClickListener(new View.OnClickListener() {
        	 @Override
        	    public void onClick(View v) {
        	        finish();
        	    }
         });
        Thread thread = new Thread()
        {
            @Override
            public void run() {
               
                  
                     
                       RetrieveAboutus();
                    
              
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
    void RetrieveAboutus(){
  		try{			
  			 
  			httpclient=new DefaultHttpClient();
  			httppost= new HttpPost("http://ezdr.com.sg/appsettings/getaboutus.php"); // make sure the url is correct.
  			nameValuePairs = new ArrayList<NameValuePair>(7);
  			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			
  			ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			final String response = httpclient.execute(httppost, responseHandler);
  			System.out.println("Response : " + response); 
  		
  			
  			if(response != null){
  				
  				runOnUiThread(new Runnable() {
				    public void run() {
				    	
				    	//allergies.setText(response);
				    	about.setText(response);
				    
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
  		MainOperation.this.runOnUiThread(new Runnable() {
  		    public void run() {
  		    	AlertDialog.Builder builder = new AlertDialog.Builder(MainOperation.this);
  		    	builder.setTitle("Error");
  		    	builder.setMessage("Error")  
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