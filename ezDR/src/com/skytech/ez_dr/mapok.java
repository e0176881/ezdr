package com.skytech.ez_dr;

import java.math.BigDecimal;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.paypal.android.sdk.payments.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class mapok extends Activity {
	
	HttpPost httppost;
	StringBuffer buffer;
	String pay_key;
	HttpClient httpclient;
	List<NameValuePair> nameValuePairs;
	ProgressDialog dialog = null;
	public int currentimageindex=0;
 double price = 0.0;
    Timer timer;
    TimerTask task;
    ImageView slidingimage;
    String symb,desc,mc,mapname,receiptnumber;
    TextView symptoms, description, mcla, pricing,mapnamez;
    private int[] IMAGE_IDS = {
            R.drawable.pic2, R.drawable.pic3,
            R.drawable.pic4, R.drawable.pic5
        };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Intent in = getIntent();
    	symb = in.getStringExtra("sym");
    	desc = in.getStringExtra("desc");
    	mc = in.getStringExtra("mc");
    	mapname=in.getStringExtra("mapname");
    	 //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //set content view AFTER ABOVE sequence (to avoid crash)
        //this.setContentView(R.layout.payment); 
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.payment);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        
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
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf");
        TextView a = (TextView)findViewById(R.id.lblmessage);
        TextView b = (TextView)findViewById(R.id.lblbookclinic);
        TextView c = (TextView)findViewById(R.id.lblsyndrome);
        TextView d = (TextView)findViewById(R.id.lbldesc);
        TextView e = (TextView)findViewById(R.id.lblmc);
        TextView f = (TextView)findViewById(R.id.textView1);
        a.setTypeface(type);
        b.setTypeface(type);
        c.setTypeface(type);
        d.setTypeface(type);
        e.setTypeface(type);
        f.setTypeface(type);
        pricing = (TextView)findViewById(R.id.textView2);
        pricing.setTypeface(type);
        
ImageButton back = (ImageButton) findViewById(R.id.imageButton);
        
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
            	Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf");
            	symptoms = (TextView)findViewById(R.id.lblsyndromes);
            	symptoms.setText(symb);
            	symptoms.setTypeface(type);
            	description=(TextView)findViewById(R.id.lbldescz);
            	description.setText(desc);
            	description.setTypeface(type);
            	mcla=(TextView)findViewById(R.id.lblmcz);
            	mcla.setText(mc + " (day)s ");
            	mcla.setTypeface(type);
            	mapnamez=(TextView)findViewById(R.id.lblbookclinic);
            	mapnamez.setText("Booking Clinic - " + mapname);
            	mapnamez.setTypeface(type);
            	Register();
            }
        };
        thread.start();
      /*  Button aboutus = (Button) findViewById(R.id.btnAbout);
     
        // Listening to Login Screen link
        aboutus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // --- find the text view --
            	Intent launchmain= new Intent(getApplicationContext(),MainOperation.class);
                startActivity(launchmain);
            }
         }); */
  
      
    }
    void Register(){
  		try{			
  			 
  			httpclient=new DefaultHttpClient();
  			httppost= new HttpPost("http://ezdr.com.sg/appsettings/getpricing.php"); // make sure the url is correct.
  			ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			final String response = httpclient.execute(httppost, responseHandler);
  			System.out.println("Response : " + response); 
  		
  			
  			if(response != null){
  				
  				runOnUiThread(new Runnable() {
				    public void run() {
				    	price = Double.parseDouble(response);
				    	pricing.setText("$ " +price);
		  				
				    
				    }
				});
  			

  			}else{
  								
  			}
  			
  		}catch(Exception e){
  			dialog.dismiss();
  			System.out.println("Exception : " + e.getMessage());
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
    private static final String TAG = "paymentExample";
    /**
     * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
     * 
     * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     * 
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AbwJ4RBrD9ZFl4vWieQlWbd7tqdoXx0XRxGsv6g3HtLHUn9RRzZS4Yx43r-u";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Ez_DR")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    public void onBuyPressed(View pressed) {
        /* 
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to PAYMENT_INTENT_AUTHORIZE to only authorize payment and 
         * capture funds later.
         * 
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
    	
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(mapok.this, PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        
    }
    
    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(price), "SGD", "Consultation at " + mapname,
                paymentIntent);
    }
    
    /* 
     * This method shows use of optional payment details and item list.
     */
    private PayPalPayment getStuffToBuy(String paymentIntent) {
        PayPalItem[] items =
            {
                    new PayPalItem("old jeans with holes", 2, new BigDecimal("87.50"), "USD",
                            "sku-12345678"),
                    new PayPalItem("free rainbow patch", 1, new BigDecimal("0.00"),
                            "USD", "sku-zero-price"),
                    new PayPalItem("long sleeve plaid shirt (no mustache included)", 6, new BigDecimal("37.99"),
                            "USD", "sku-33333") 
            };
    BigDecimal subtotal = PayPalItem.getItemTotal(items);
    BigDecimal shipping = new BigDecimal("7.21");
    BigDecimal tax = new BigDecimal("4.67");
    PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
    BigDecimal amount = subtotal.add(shipping).add(tax);
        PayPalPayment payment = new PayPalPayment(amount, "USD", "hipster jeans", paymentIntent);
        return payment.items(items).paymentDetails(paymentDetails);
    }
    boolean paymentok = false;
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                //    Log.i("paymentExample", confirm.toJSONObject().getJSONObject("proof_of_payment").toString(4));
                	String result = confirm.toJSONObject().toString(4);
                	String state =confirm.toJSONObject().getJSONObject("response").getString("state");
                	pay_key=confirm.toJSONObject().getJSONObject("response").getString("id");
                //	  String payment_exec_status = jsonResult2.getString("payment_exec_status");
                	 // String app_id = aaa.getString("app_id");
                	 // String pay_key = aaa.getString("pay_key");
                	
                	
             confirmdb(pay_key);
           
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.
                    
         	Toast.makeText(mapok.this,"Payment Success", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
            paymentok = true;
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
        //super.onActivityResult(requestCode, resultCode, data);
        
    }
 
    void confirmdb(String paykey){
  		try{			
  		   SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_APPEND);
  			httpclient=new DefaultHttpClient();
  			httppost= new HttpPost("http://ezdr.com.sg/appsettings/book.php"); 
  			nameValuePairs = new ArrayList<NameValuePair>(5);
  			nameValuePairs.add(new BasicNameValuePair("username",pref.getString("username", null).toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
  			nameValuePairs.add(new BasicNameValuePair("symptoms",symb.toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("clinicname",mapname.toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("noofmcs",mc.toString().trim())); 
  			nameValuePairs.add(new BasicNameValuePair("paymentid",paykey.toString().trim())); 
  			
  			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  			ResponseHandler<String> responseHandler = new BasicResponseHandler();
  			final String response = httpclient.execute(httppost, responseHandler);
  			System.out.println("Response : " + response); 
  		
  			
  			if(response !=""){
  				
  			
  				/* runOnUiThread(new Runnable() {
				    public void run() {
				    	Intent launchmain= new Intent(getApplicationContext(),summary.class);
		            	launchmain.putExtra("sym", symb);
		            	launchmain.putExtra("desc", desc);
		            	launchmain.putExtra("mc", mc);
		            	launchmain.putExtra("mapname",mapname);
		            	launchmain.putExtra("price", price);
		                startActivity(launchmain);
				    	//Toast.makeText(mapok.this,"Payment Success", Toast.LENGTH_LONG).show();
		  			
				    	
				    }
				});*/
  				receiptnumber = response;

  			}else{
  				
  			}
  			
  		}catch(Exception e){
  			dialog.dismiss();
  			System.out.println("Exception : " + e.getMessage());
  		}
  		
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
      
       if (paymentok == true)
       {
    		
    	   Intent launchmain= new Intent(getApplicationContext(),summary.class);
    	   launchmain.putExtra("rn", receiptnumber);
    	   launchmain.putExtra("sym", symb);
       	launchmain.putExtra("desc", desc);
       	launchmain.putExtra("mc", mc);
       	launchmain.putExtra("mapname",mapname);
       	launchmain.putExtra("price", price);
       	launchmain.putExtra("paymentid", pay_key);
           startActivity(launchmain);
	    
       
       }
       super.onResume();
    }
    @Override
    public void onBackPressed() {
       return ; // Do Here what ever you want do on back press;
    }
}
