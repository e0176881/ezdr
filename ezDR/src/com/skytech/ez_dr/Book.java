package com.skytech.ez_dr;
//i think u better fix ur filename first leh 

import java.util.ArrayList;




import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class Book extends Activity {
	Button bookbutton;
	  public int currentimageindex=0;
	    Timer timer;
	    TimerTask task;
	    ImageView slidingimage;
	    CheckBox cough,flu,bodypain,fever, diarrhea, vomiting, headache,others;
	    String text,mcz;
	    EditText symptoms;
	    RadioButton mc1,mc2,mc3;
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
        this.setContentView(R.layout.abc); 
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.abc);
        slidingimage = (ImageView)findViewById(R.id.ImageView3_Left);
        slidingimage.setImageResource(R.drawable.staticimage);
 
        /* final Handler mHandler = new Handler();
        
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
        bookbutton = (Button) findViewById(R.id.imgbtnBook);
        cough = (CheckBox)findViewById(R.id.checkBox1);
        flu=(CheckBox)findViewById(R.id.checkBox3);
        bodypain=(CheckBox)findViewById(R.id.checkBox4);
        fever=(CheckBox)findViewById(R.id.checkBox2);
        others=(CheckBox)findViewById(R.id.checkBox5);
        diarrhea=(CheckBox)findViewById(R.id.cbDiarrhea);
        vomiting=(CheckBox)findViewById(R.id.cbVomiting);
        headache=(CheckBox)findViewById(R.id.cbHeadache);
        symptoms=(EditText)findViewById(R.id.editText1);
        mc1=(RadioButton)findViewById(R.id.rbMC);
        mc2=(RadioButton)findViewById(R.id.rbMC2);
        mc3=(RadioButton)findViewById(R.id.rbMC3);
        TextView lbmc = (TextView)findViewById(R.id.lbmc);
        TextView lbsyn = (TextView)findViewById(R.id.lblSymptoms);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        
        bookbutton.setTypeface(type);
        cough.setTypeface(type);
        flu.setTypeface(type);
        bodypain.setTypeface(type);
        fever.setTypeface(type);
        others.setTypeface(type);
        diarrhea.setTypeface(type);
        vomiting.setTypeface(type);
        headache.setTypeface(type);
        symptoms.setTypeface(type);
        mc1.setTypeface(type);
        mc2.setTypeface(type);
        mc3.setTypeface(type);
       lbmc.setTypeface(type);
        lbsyn.setTypeface(type);
        
        // Listening to Login Screen link
        bookbutton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // --- find the text view --
        		
        		text = null;
            	  if(cough.isChecked())
                  {
                  text = "Cough"; 
                  }
                  if(flu.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Flu"; 
                	  }
                	  else
                	  {
                		  text = "Flu"; 
                	  }
                  }
                  if(bodypain.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Body Pain"; 
                	  }
                	  else
                	  {
                		  text = "Body Pain"; 
                	  }
                  }
                  if(fever.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Fever"; 
                	  }
                	  else
                	  {
                		  text = "Fever"; 
                	  }
                  }
                  if(vomiting.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Vomiting"; 
                	  }
                	  else
                	  {
                		  text = "Vomiting"; 
                	  }
                  }
                  if(diarrhea.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Diarrhea"; 
                	  }
                	  else
                	  {
                		  text = "Diarrhea"; 
                	  }
                  }
                  if(headache.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Headache"; 
                	  }
                	  else
                	  {
                		  text = "Headache"; 
                	  }
                  }
                  
                  String sym = symptoms.getText().toString();
                  if(others.isChecked())
                  {
                	  if(text != null)
                	  {
                		  text = text + "," + " Others : "; 
                	  }
                	  else
                	  {
                		  text = "Others : "; 
                	  }
                	  
                	  
                      if(sym.length() != 0){
                    	  
                    	  text = text + sym;
                      }
                  }
                  
                  
                  
                  if(mc1.isChecked())
                  {
                  mcz ="1"; 
                  }
                  if(mc2.isChecked())
                  {
                  mcz ="2"; 
                  }
                  if(mc3.isChecked())
                  {
                  mcz ="More"; 
                  }
                 
                  if(text == null && sym.length() == 0)
                  {
                	  //Please select or enter Symptoms
                	  sAlert();
                  }
                  else if(mcz == null)
                  {
                	  //Please select MC
                	  sAlert2();
                  }else
                  {
            	Intent launchmain= new Intent(getApplicationContext(),selectmap.class);
            	launchmain.putExtra("Sym", text);
            	launchmain.putExtra("desc", symptoms.getText().toString());
            	launchmain.putExtra("mc", mcz);
                startActivity(launchmain);
                  }
            }
         });
ImageButton back = (ImageButton) findViewById(R.id.imageButton);
        
        back.setOnClickListener(new View.OnClickListener() {
        	 @Override
        	    public void onClick(View v) {
        	        finish();
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
    void sAlert()
    {
    	try{
    	showAlert();
    	}catch(Exception e){
			System.out.println("Exception : " + e.getMessage());
		}
    }
    void sAlert2()
    {
    	
    	showAlert2();
    }
    public void showAlert(){
		this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(Book.this);
		    	builder.setTitle("Error.");
		    	builder.setMessage("Please select symptoms or enter your symtoms!")  
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
		this.runOnUiThread(new Runnable() {
		    public void run() {
		    	AlertDialog.Builder builder = new AlertDialog.Builder(Book.this);
		    	builder.setTitle("Error.");
		    	builder.setMessage("Please select number of days MC.")  
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