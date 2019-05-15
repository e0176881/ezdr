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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class History extends Activity {
	static final int DATE_DIALOG_ID = 999;
	Button b;
	EditText nric, fullname, address, allergies, medicalhistory, medication ;
	TextView tv;
	HttpPost httppost;
	StringBuffer buffer;
	
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	public int currentimageindex=0;
    Timer timer;
    TimerTask task;
    ImageView slidingimage;
    
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pUserName = "username"; 
    public static final String pPassword = "password"; 
    
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
        this.setContentView(R.layout.history); 
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.history);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView Pagename = (TextView)findViewById(R.id.pagename);
        TextView lb1 = (TextView)findViewById(R.id.lbl1);
        TextView lb2 = (TextView)findViewById(R.id.lbl2);
        TextView lb3 = (TextView)findViewById(R.id.lbl3);
        TextView lbmsg2 = (TextView)findViewById(R.id.lblmessage2);
        TextView lbmsg3 = (TextView)findViewById(R.id.lblmessage3);
        TextView lbmsg4 = (TextView)findViewById(R.id.lblmessage4);
        lb1.setTypeface(type);
        lb2.setTypeface(type);
        lb3.setTypeface(type);
        lbmsg2.setTypeface(type);
        lbmsg3.setTypeface(type);
        lbmsg4.setTypeface(type);
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
 
        int delay = 0; // delay for 1 sec.
 
        int period = 2000; // repeat every 4 sec.
 
        Timer timer = new Timer();
 
        timer.scheduleAtFixedRate(new TimerTask() {
 
        public void run() {
 
             mHandler.post(mUpdateResults);
 
        }
 
        }, delay, period);*/
 
     /*   TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        }); */
        
        
        b = (Button)findViewById(R.id.btnBook);  
        
        nric = (EditText)findViewById(R.id.his_NRIC);
        fullname= (EditText)findViewById(R.id.his_fullname);
        address = (EditText)findViewById(R.id.his_address);
        allergies = (EditText)findViewById(R.id.allergies);
        medication = (EditText)findViewById(R.id.medication);
        medicalhistory = (EditText)findViewById(R.id.medicalhistory);
        Button updatee = (Button)findViewById(R.id.btnUpdate);  
        tv = (TextView)findViewById(R.id.TextViewaaa);
       
        b.setVisibility(View.GONE);
			b.setTypeface(type);
        updatee.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(History.this, "", 
                        "Updating...", true);
				 new Thread(new Runnable() {
					    public void run() {
					    	Register2();					      
					    }
					  }).start();				
			}
		});
    
					  
					    				      
					  			
			
		

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
               
                  if(isNetworkConnected()!=false)
                  {
                     
                       Register();
                  } else
                  {
                	  noDB();
                  }
              
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
    void noDB()
    {
    	sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_APPEND);
    	nric.setText(sharedpreferences.getString("NRIC", null));
    	fullname.setText(sharedpreferences.getString("fullname", null));
    	address.setText(sharedpreferences.getString("address", null));
    	allergies.setText(sharedpreferences.getString("allergies", null));
  	medication.setText(sharedpreferences.getString("medication", null));
    	medicalhistory.setText(sharedpreferences.getString("medicalhistory", null));
        
		
    }
    void Register2(){
  		try{			
  			 
  			httpclient=new DefaultHttpClient();
  			httppost= new HttpPost("http://ezdr.com.sg/appsettings/myhistory.php"); // make sure the url is correct.
  			nameValuePairs = new ArrayList<NameValuePair>(7);
  			sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_APPEND);
  	        

  			nameValuePairs.add(new BasicNameValuePair("NRIC",nric.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
  			nameValuePairs.add(new BasicNameValuePair("fullname",fullname.getText().toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("address",address.getText().toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("allergies",allergies.getText().toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("medication",medication.getText().toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("medicalhistory",medicalhistory.getText().toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("username",sharedpreferences.getString(pUserName, "").trim()));
  			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			
  			ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			final String response = httpclient.execute(httppost, responseHandler);
  			System.out.println("Response : " + response); 
  			runOnUiThread(new Runnable() {
  			    public void run() {
  			    	tv.setText("Response from PHP : " + response);
  					dialog.dismiss();
  			    }
  			});
  		
  			
  			if(response.contains("success")){
  				runOnUiThread(new Runnable() {
  				    public void run() {
  				    	Editor editor = sharedpreferences.edit();
  						editor.putString("NRIC", nric.getText().toString().trim()); // Storing string
  						editor.putString("fullname", fullname.getText().toString().trim());
  						editor.putString("address",address.getText().toString().trim());
  						editor.putString("allergies",allergies.getText().toString().trim());
  						editor.putString("medication",medication.getText().toString().trim());
  						editor.putString("medicalhistory",medicalhistory.getText().toString().trim());
  						
  						editor.commit();


  				    	Toast.makeText(History.this,"History Updated!", Toast.LENGTH_SHORT).show();
  				    	
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
				    	nric.setText(values[0],TextView.BufferType.EDITABLE);
				    	fullname.setText(values[1],TextView.BufferType.EDITABLE);
				    	address.setText(values[2],TextView.BufferType.EDITABLE);
				    	allergies.setText(values[3],TextView.BufferType.EDITABLE);
				  	medication.setText(values[4],TextView.BufferType.EDITABLE);
				    	medicalhistory.setText(values[5],TextView.BufferType.EDITABLE);
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
  					nric.setText("",TextView.BufferType.EDITABLE);
			    	fullname.setText("",TextView.BufferType.EDITABLE);
			    	address.setText("",TextView.BufferType.EDITABLE);
			    	allergies.setText("",TextView.BufferType.EDITABLE);
			  	medication.setText("",TextView.BufferType.EDITABLE);
			    	medicalhistory.setText("",TextView.BufferType.EDITABLE);	
  				}
  				
  			}else{
  				showAlert();				
  			}
  			
  		}catch(Exception e){
  			dialog.dismiss();
  			System.out.println("Exception : " + e.getMessage());
  		}
  	}
  	public void showAlert(){
  		History.this.runOnUiThread(new Runnable() {
  		    public void run() {
  		    	AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
  		    	builder.setTitle("Error");
  		    	builder.setMessage("Registration Error")  
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
  	private boolean isNetworkConnected() {
  	  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  	  NetworkInfo ni = cm.getActiveNetworkInfo();
  	  if (ni == null) {
  	   // There are no active networks.
  	   return false;
  	  } else
  	   return true;
  	 }
}