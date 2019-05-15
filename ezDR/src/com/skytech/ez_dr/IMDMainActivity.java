package com.skytech.ez_dr;
//i think u better fix ur filename first leh 

import java.util.ArrayList;




import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class IMDMainActivity extends Activity {
	
	public static Handler exitHandler;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	/*
	 * Code ommited
	 */
	 
	exitHandler = new Handler() {
	    public void handleMessage(Message msg) {
		    super.handleMessage(msg);
		    switch (msg.what) {
		    case 0:
	                // clear any informations you like here
			IMDMainActivity.this.finish();
			break;
		    }
		}
	};   
}
}