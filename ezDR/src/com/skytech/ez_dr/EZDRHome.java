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
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;


public class EZDRHome extends Activity {
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
        setContentView(R.layout.ezdrmain);
        this.setContentView(R.layout.ezdrmain); 
        super.onCreate(savedInstanceState);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setTypeface(type);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        tv2.setTypeface(type);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        tv3.setTypeface(type);
        
        Button nbtnBook = (Button)findViewById(R.id.btnBook);
        nbtnBook.setTypeface(type);
        TextView nbtnLogin = (TextView)findViewById(R.id.btnLogin);
        nbtnLogin.setTypeface(type);
        nbtnLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
			}
		});
      
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
    
   
}