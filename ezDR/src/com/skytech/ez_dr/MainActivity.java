package com.skytech.ez_dr;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends Activity {
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
        this.setContentView(R.layout.mainscreen); 
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(R.drawable.staticimage);
        
        /*final Handler mHandler = new Handler();
        
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
				Intent i = new Intent(getApplicationContext(), Book.class);
				startActivity(i);
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
    
    private void AnimateandSlideShow() {
   	 
        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
 
        currentimageindex++;
 
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.layout.custom_anim);
 slidingimage.setScaleType(ScaleType.FIT_XY);
          slidingimage.startAnimation(rotateimage);
 
    }
}