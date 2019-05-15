package com.skytech.ez_dr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;














import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class clinic extends FragmentActivity {
	  GoogleMap map ;
	  String mapname, oaddress, odays, ohoursmorning, ohoursafternoon, ohoursevening, ocontactno, ofaxnumber;
	  Double lat,lng;
	  TextView lb1, lb2, label,label2,info,address,lbloperating,operatingdays,lbloperatinghours,lbloperatingm,operatinghoursmorning,lbloperatinga,operatinghoursafternoon,lbloperatinge,operatinghoursevening,lblcontact,contactnumber,lblfax,fax;
	  List<NameValuePair> nameValuePairs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
   	 //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //set content view AFTER ABOVE sequence (to avoid crash)
        //this.setContentView(R.layout.clinics); 
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
        	    ThreadPolicy.Builder().permitAll().build();
        	    StrictMode.setThreadPolicy(policy); 
        // Set View to register.xml
        setContentView(R.layout.clinics);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/verdana.ttf"); 
        TextView Pagename = (TextView)findViewById(R.id.pagename);
        Pagename.setTypeface(type);
        
        
        TextView info1 = (TextView) findViewById(R.id.infogg);
        TextView info2 = (TextView) findViewById(R.id.infozzz);
        info2.setVisibility(View.GONE);
        info1.setVisibility(View.GONE);
        info= (TextView) findViewById(R.id.info);
      	address = (TextView) findViewById(R.id.address);
    	lbloperating = (TextView) findViewById(R.id.lbloperating);
    	operatingdays = (TextView) findViewById(R.id.operatingdays);
    	lbloperatinghours = (TextView) findViewById(R.id.lbloperatinghours);
    	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
    	operatinghoursmorning = (TextView) findViewById(R.id.operatinghoursmorning);
    	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
    	lbloperatinga = (TextView) findViewById(R.id.lbloperatinga);
    	operatinghoursafternoon = (TextView) findViewById(R.id.operatinghoursafternoon);
    	lbloperatinge = (TextView) findViewById(R.id.lbloperatinge);
    	operatinghoursevening = (TextView) findViewById(R.id.operatinghoursevening);
    	lblcontact = (TextView) findViewById(R.id.lblcontact);
    	contactnumber = (TextView) findViewById(R.id.contactnumber);
    	lblfax = (TextView) findViewById(R.id.lblfax);
    	fax = (TextView) findViewById(R.id.fax);
    	label = (TextView) findViewById(R.id.lblnearbyclinics);
        label2 = (TextView) findViewById(R.id.nearbyclinics);
        lb1 = (TextView) findViewById(R.id.lblsearchresult);
        lb2 = (TextView) findViewById(R.id.searchresult);
        lb1.setVisibility(View.GONE);
        lb2.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);
    	info.setVisibility(View.GONE);
    	address.setVisibility(View.GONE);
    	lbloperating.setVisibility(View.GONE);
    	operatingdays.setVisibility(View.GONE);
    	lbloperatinghours.setVisibility(View.GONE);
    	lbloperatingm.setVisibility(View.GONE);
    	operatinghoursmorning.setVisibility(View.GONE);
    	lbloperatingm.setVisibility(View.GONE);
    	lbloperatinga.setVisibility(View.GONE);
    	operatinghoursafternoon.setVisibility(View.GONE);
    	lbloperatinge.setVisibility(View.GONE);
    	operatinghoursevening.setVisibility(View.GONE);
    	lblcontact.setVisibility(View.GONE);
    	contactnumber.setVisibility(View.GONE);
    	lblfax.setVisibility(View.GONE);
    	fax.setVisibility(View.GONE);

    	TextView lbs = (TextView) findViewById(R.id.lblselected);
    	lbs.setTypeface(type);
    	lb1.setTypeface(type);
        lb2.setTypeface(type);
        label.setTypeface(type);
        label2.setTypeface(type);
    	info.setTypeface(type);
    	info2.setTypeface(type);
    	info1.setTypeface(type);
    	address.setTypeface(type);
    	lbloperating.setTypeface(type);
    	operatingdays.setTypeface(type);
    	lbloperatinghours.setTypeface(type);
    	lbloperatingm.setTypeface(type);
    	operatinghoursmorning.setTypeface(type);
    	lbloperatingm.setTypeface(type);
    	lbloperatinga.setTypeface(type);
    	operatinghoursafternoon.setTypeface(type);
    	lbloperatinge.setTypeface(type);
    	operatinghoursevening.setTypeface(type);
    	lblcontact.setTypeface(type);
    	contactnumber.setTypeface(type);
    	lblfax.setTypeface(type);
    	fax.setTypeface(type);
    	
    	String readgmap = readMaps();
        try {
        	
        	//info2.setVisibility(View.VISIBLE);
        	//info1.setVisibility(View.VISIBLE);
        	 info2.setText("");
            JSONArray jsonArray = new JSONArray(readgmap);
        //    Log.i(ParseJSON.class.getName(),
          //      "Number of entries " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              mapname =  jsonObject.getString("name");
              oaddress =  jsonObject.getString("address");
              odays =  jsonObject.getString("operatingdays");
              ohoursmorning =  jsonObject.getString("operatinghoursmorning");
              ohoursafternoon =  jsonObject.getString("operatinghoursafternoon");
              ohoursevening =  jsonObject.getString("operatinghoursevening");
              ocontactno =  jsonObject.getString("contactno");
              ofaxnumber =  jsonObject.getString("faxnumber");
              
              
              
              lat=jsonObject.getDouble("lat");
              lng=jsonObject.getDouble("lng");
              map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                      .getMap();
              
              ViewGroup.LayoutParams params = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().getLayoutParams();
              params.height = 600;
              ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().setLayoutParams(params);
              map.addMarker(new MarkerOptions()
              .position(new LatLng(lat,lng))
              .title(mapname)
              .snippet(oaddress )
              );

        	
        	
         	 
         	 info2.setText(info2.getText() +  "\n" + mapname);
            }
            map.setOnMarkerClickListener(new OnMarkerClickListener() {

              	@Override
              	public boolean onMarkerClick(Marker marker) {
              		 TextView info = (TextView) findViewById(R.id.info);
              	 
              		 info.setText(marker.getTitle());
                 	info.setVisibility(View.VISIBLE);
                	address.setVisibility(View.VISIBLE);
                	lbloperatinghours.setVisibility(View.VISIBLE);
                	operatinghoursmorning.setVisibility(View.VISIBLE);
                	operatinghoursafternoon.setVisibility(View.VISIBLE);
                	operatinghoursevening.setVisibility(View.VISIBLE);

                	String readgmap = readMaps();
                	try {
                        JSONArray jsonArray = new JSONArray(readgmap);
                    //    Log.i(ParseJSON.class.getName(),
                      //      "Number of entries " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          Log.e("Name",jsonObject.getString("name"));
                          Log.e("test",marker.getTitle());
                          if( jsonObject.getString("name").equals(marker.getTitle())) {
                        	  address.setText(jsonObject.getString("address"));
                        	  operatingdays.setText(jsonObject.getString("operatingdays"));
                        	  operatinghoursmorning.setText(jsonObject.getString("operatinghoursmorning"));
                        	  operatinghoursafternoon.setText(jsonObject.getString("operatinghoursafternoon"));
                        	  operatinghoursevening.setText(jsonObject.getString("operatinghoursevening"));
                        	  contactnumber.setText(jsonObject.getString("contactno"));
                        	  fax.setText(jsonObject.getString("faxnumber"));
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  break;
                          }
                        }
                	}catch (Exception e) {
                        e.printStackTrace();
                    }
              		 
              		 
              		return false;
              	}
              	
              });
            LatLng toCenter = new LatLng(1.424275, 103.838379);


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(toCenter, 10));
            map.setMyLocationEnabled(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
       	 
    	
        Button showall = (Button) findViewById(R.id.btnshowall);
        showall.setTypeface(type);
        showall.setOnClickListener(new View.OnClickListener() {
       	 @Override
       	    public void onClick(View v) {
       		String readgmap = readMaps();
        try {
        	TextView info2 = (TextView) findViewById(R.id.infozzz);
        	TextView info1 = (TextView) findViewById(R.id.infogg);
        	info2.setVisibility(View.VISIBLE);
        	info1.setVisibility(View.VISIBLE);
        	 info2.setText("");
            JSONArray jsonArray = new JSONArray(readgmap);
        //    Log.i(ParseJSON.class.getName(),
          //      "Number of entries " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              mapname =  jsonObject.getString("name");
              oaddress =  jsonObject.getString("address");
              odays =  jsonObject.getString("operatingdays");
              ohoursmorning =  jsonObject.getString("operatinghoursmorning");
              ohoursafternoon =  jsonObject.getString("operatinghoursafternoon");
              ohoursevening =  jsonObject.getString("operatinghoursevening");
              ocontactno =  jsonObject.getString("contactno");
              ofaxnumber =  jsonObject.getString("faxnumber");
              
              
              
              lat=jsonObject.getDouble("lat");
              lng=jsonObject.getDouble("lng");
              map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                      .getMap();
              
              ViewGroup.LayoutParams params = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().getLayoutParams();
              params.height = 600;
              ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().setLayoutParams(params);
              map.addMarker(new MarkerOptions()
              .position(new LatLng(lat,lng))
              .title(mapname)
              .snippet(oaddress )
              );

        	
        	
         	 
         	 info2.setText(info2.getText() +  "\n" + mapname);
            }
            map.setOnMarkerClickListener(new OnMarkerClickListener() {

              	@Override
              	public boolean onMarkerClick(Marker marker) {
              		 TextView info = (TextView) findViewById(R.id.info);
              	 
              		 info.setText(marker.getTitle());
                 	info.setVisibility(View.VISIBLE);
                	address.setVisibility(View.VISIBLE);
                	lbloperatinghours.setVisibility(View.VISIBLE);
                	operatinghoursmorning.setVisibility(View.VISIBLE);
                	operatinghoursafternoon.setVisibility(View.VISIBLE);
                	operatinghoursevening.setVisibility(View.VISIBLE);

                	String readgmap = readMaps();
                	try {
                        JSONArray jsonArray = new JSONArray(readgmap);
                    //    Log.i(ParseJSON.class.getName(),
                      //      "Number of entries " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          Log.e("Name",jsonObject.getString("name"));
                          Log.e("test",marker.getTitle());
                          if( jsonObject.getString("name").equals(marker.getTitle())) {
                        	  address.setText(jsonObject.getString("address"));
                        	  operatingdays.setText(jsonObject.getString("operatingdays"));
                        	  operatinghoursmorning.setText(jsonObject.getString("operatinghoursmorning"));
                        	  operatinghoursafternoon.setText(jsonObject.getString("operatinghoursafternoon"));
                        	  operatinghoursevening.setText(jsonObject.getString("operatinghoursevening"));
                        	  contactnumber.setText(jsonObject.getString("contactno"));
                        	  fax.setText(jsonObject.getString("faxnumber"));
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  
                        	  break;
                          }
                        }
                	}catch (Exception e) {
                        e.printStackTrace();
                    }
              		 
              		 
              		return false;
              	}
              	
              });
            LatLng toCenter = new LatLng(1.424275, 103.838379);


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(toCenter, 10));
            map.setMyLocationEnabled(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
       	 }
        });
        
ImageButton back = (ImageButton) findViewById(R.id.imageButton1);
        
        back.setOnClickListener(new View.OnClickListener() {
        	 @Override
        	    public void onClick(View v) {
        	        finish();
        	    }
         });
 Button search = (Button) findViewById(R.id.btnsearch);
        search.setTypeface(type);
        search.setOnClickListener(new View.OnClickListener() {
                	 @Override
                	    public void onClick(View v) {
                		 lb2.setText("");
                		 map.clear();
                		 String readgmap = readMaps3();
                	        try {
                	        	TextView info2 = (TextView) findViewById(R.id.infozzz);
                	        	 //info2.setText("");
                	            JSONArray jsonArray = new JSONArray(readgmap);
                	        //    Log.i(ParseJSON.class.getName(),
                	          //      "Number of entries " + jsonArray.length());
                	            for (int i = 0; i < jsonArray.length(); i++) {
                	              JSONObject jsonObject = jsonArray.getJSONObject(i);
                	              mapname =  jsonObject.getString("name");
                	              oaddress =  jsonObject.getString("address");
                	              odays =  jsonObject.getString("operatingdays");
                	              ohoursmorning =  jsonObject.getString("operatinghoursmorning");
                	              ohoursafternoon =  jsonObject.getString("operatinghoursafternoon");
                	              ohoursevening =  jsonObject.getString("operatinghoursevening");
                	              ocontactno =  jsonObject.getString("contactno");
                	              ofaxnumber =  jsonObject.getString("faxnumber");
                	              
                	              
                	              
                	              lat=jsonObject.getDouble("lat");
                	              lng=jsonObject.getDouble("lng");
                	              map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                	                      .getMap();
                	              
                	              ViewGroup.LayoutParams params = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().getLayoutParams();
                	              params.height = 600;
                	              ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().setLayoutParams(params);
                	              map.addMarker(new MarkerOptions()
                	              .position(new LatLng(lat,lng))
                	              .title(mapname)
                	              .snippet(oaddress)
                	              );
                	            info= (TextView) findViewById(R.id.info);
                	          	address = (TextView) findViewById(R.id.address);
                	        	lbloperating = (TextView) findViewById(R.id.lbloperating);
                	        	operatingdays = (TextView) findViewById(R.id.operatingdays);
                	        	lbloperatinghours = (TextView) findViewById(R.id.lbloperatinghours);
                	        	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
                	        	operatinghoursmorning = (TextView) findViewById(R.id.operatinghoursmorning);
                	        	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
                	        	lbloperatinga = (TextView) findViewById(R.id.lbloperatinga);
                	        	operatinghoursafternoon = (TextView) findViewById(R.id.operatinghoursafternoon);
                	        	lbloperatinge = (TextView) findViewById(R.id.lbloperatinge);
                	        	operatinghoursevening = (TextView) findViewById(R.id.operatinghoursevening);
                	        	lblcontact = (TextView) findViewById(R.id.lblcontact);
                	        	contactnumber = (TextView) findViewById(R.id.contactnumber);
                	        	lblfax = (TextView) findViewById(R.id.lblfax);
                	        	fax = (TextView) findViewById(R.id.fax);
                	        	label = (TextView) findViewById(R.id.lblnearbyclinics);
                	            label2 = (TextView) findViewById(R.id.nearbyclinics);
                	            lb1 = (TextView) findViewById(R.id.lblsearchresult);
                	            lb2 = (TextView) findViewById(R.id.searchresult);
                	            lb1.setVisibility(View.VISIBLE);
                	            lb2.setVisibility(View.VISIBLE);
                	            label.setVisibility(View.GONE);
                	            label2.setVisibility(View.GONE);
                	        	info.setVisibility(View.GONE);
                	        	address.setVisibility(View.GONE);
                	        	lbloperating.setVisibility(View.GONE);
                	        	operatingdays.setVisibility(View.GONE);
                	        	lbloperatinghours.setVisibility(View.GONE);
                	        	lbloperatingm.setVisibility(View.GONE);
                	        	operatinghoursmorning.setVisibility(View.GONE);
                	        	lbloperatingm.setVisibility(View.GONE);
                	        	lbloperatinga.setVisibility(View.GONE);
                	        	operatinghoursafternoon.setVisibility(View.GONE);
                	        	lbloperatinge.setVisibility(View.GONE);
                	        	operatinghoursevening.setVisibility(View.GONE);
                	        	lblcontact.setVisibility(View.GONE);
                	        	contactnumber.setVisibility(View.GONE);
                	        	lblfax.setVisibility(View.GONE);
                	        	fax.setVisibility(View.GONE);
                	        	
                	        	
                	         	 
                	        	lb2.setText(lb2.getText() +  "\n" + mapname);
                	            }
                	            map.setOnMarkerClickListener(new OnMarkerClickListener() {

                	              	@Override
                	              	public boolean onMarkerClick(Marker marker) {
                	              		 TextView info = (TextView) findViewById(R.id.info);
                	              	 
                	              		 info.setText(marker.getTitle());
                	                 	info.setVisibility(View.VISIBLE);
                	                	address.setVisibility(View.VISIBLE);
                	               
                	                	lbloperatinghours.setVisibility(View.VISIBLE);
                	                	operatinghoursmorning.setVisibility(View.VISIBLE);
                	                	operatinghoursafternoon.setVisibility(View.VISIBLE);
                	                	operatinghoursevening.setVisibility(View.VISIBLE);

                	                	String readgmap = readMaps();
                	                	try {
                	                        JSONArray jsonArray = new JSONArray(readgmap);
                	                    //    Log.i(ParseJSON.class.getName(),
                	                      //      "Number of entries " + jsonArray.length());
                	                        for (int i = 0; i < jsonArray.length(); i++) {
                	                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                	                          Log.e("Name",jsonObject.getString("name"));
                	                          Log.e("test",marker.getTitle());
                	                          if( jsonObject.getString("name").equals(marker.getTitle())) {
                	                        	  address.setText(jsonObject.getString("address"));
                	                        	  operatingdays.setText(jsonObject.getString("operatingdays"));
                	                        	  operatinghoursmorning.setText(jsonObject.getString("operatinghoursmorning"));
                	                        	  operatinghoursafternoon.setText(jsonObject.getString("operatinghoursafternoon"));
                	                        	  operatinghoursevening.setText(jsonObject.getString("operatinghoursevening"));
                	                        	  contactnumber.setText(jsonObject.getString("contactno"));
                	                        	  fax.setText(jsonObject.getString("faxnumber"));
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  break;
                	                          }
                	                        }
                	                	}catch (Exception e) {
                	                        e.printStackTrace();
                	                    }
                	              		 
                	              		 
                	              		return false;
                	              	}
                	              	
                	              });
                	            LatLng toCenter = new LatLng(1.424275, 103.838379);


                	            map.moveCamera(CameraUpdateFactory.newLatLngZoom(toCenter, 10));
                	            map.setMyLocationEnabled(true);
                	          } catch (Exception e) {
                	            e.printStackTrace();
                	          }
                	 }
                 });
        Button neareset = (Button) findViewById(R.id.btnshownearest);
        neareset.setTypeface(type);
        neareset.setOnClickListener(new View.OnClickListener() {
                	 @Override
                	    public void onClick(View v) {

                         if(map.getMyLocation()!=null){
                             
                         
                		 label2.setText("");
                		 map.clear();
                		  String readgmap = readMaps2();
                	        try {
                	        	TextView info2 = (TextView) findViewById(R.id.infozzz);
                	        	 //info2.setText("");
                	            JSONArray jsonArray = new JSONArray(readgmap);
                	        //    Log.i(ParseJSON.class.getName(),
                	          //      "Number of entries " + jsonArray.length());
                	            for (int i = 0; i < jsonArray.length(); i++) {
                	              JSONObject jsonObject = jsonArray.getJSONObject(i);
                	              mapname =  jsonObject.getString("name");
                	              oaddress =  jsonObject.getString("address");
                	              odays =  jsonObject.getString("operatingdays");
                	              ohoursmorning =  jsonObject.getString("operatinghoursmorning");
                	              ohoursafternoon =  jsonObject.getString("operatinghoursafternoon");
                	              ohoursevening =  jsonObject.getString("operatinghoursevening");
                	              ocontactno =  jsonObject.getString("contactno");
                	              ofaxnumber =  jsonObject.getString("faxnumber");
                	              
                	              
                	              
                	              lat=jsonObject.getDouble("lat");
                	              lng=jsonObject.getDouble("lng");
                	              map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map))
                	                      .getMap();
                	              
                	              ViewGroup.LayoutParams params = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().getLayoutParams();
                	              params.height = 600;
                	              ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getView().setLayoutParams(params);
                	              map.addMarker(new MarkerOptions()
                	              .position(new LatLng(lat,lng))
                	              .title(mapname)
                	              .snippet(oaddress)
                	              );
                	            info= (TextView) findViewById(R.id.info);
                	          	address = (TextView) findViewById(R.id.address);
                	        	lbloperating = (TextView) findViewById(R.id.lbloperating);
                	        	operatingdays = (TextView) findViewById(R.id.operatingdays);
                	        	lbloperatinghours = (TextView) findViewById(R.id.lbloperatinghours);
                	        	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
                	        	operatinghoursmorning = (TextView) findViewById(R.id.operatinghoursmorning);
                	        	lbloperatingm = (TextView) findViewById(R.id.lbloperatingm);
                	        	lbloperatinga = (TextView) findViewById(R.id.lbloperatinga);
                	        	operatinghoursafternoon = (TextView) findViewById(R.id.operatinghoursafternoon);
                	        	lbloperatinge = (TextView) findViewById(R.id.lbloperatinge);
                	        	operatinghoursevening = (TextView) findViewById(R.id.operatinghoursevening);
                	        	lblcontact = (TextView) findViewById(R.id.lblcontact);
                	        	contactnumber = (TextView) findViewById(R.id.contactnumber);
                	        	lblfax = (TextView) findViewById(R.id.lblfax);
                	        	fax = (TextView) findViewById(R.id.fax);
                	        	label = (TextView) findViewById(R.id.lblnearbyclinics);
                	            label2 = (TextView) findViewById(R.id.nearbyclinics);
                	            lb1 = (TextView) findViewById(R.id.lblsearchresult);
                	            lb2 = (TextView) findViewById(R.id.searchresult);
                	            lb1.setVisibility(View.GONE);
                	            lb2.setVisibility(View.GONE);
                	            label.setVisibility(View.VISIBLE);
                	            label2.setVisibility(View.VISIBLE);
                	        	info.setVisibility(View.GONE);
                	        	address.setVisibility(View.GONE);
                	        	lbloperating.setVisibility(View.GONE);
                	        	operatingdays.setVisibility(View.GONE);
                	        	lbloperatinghours.setVisibility(View.GONE);
                	        	lbloperatingm.setVisibility(View.GONE);
                	        	operatinghoursmorning.setVisibility(View.GONE);
                	        	lbloperatingm.setVisibility(View.GONE);
                	        	lbloperatinga.setVisibility(View.GONE);
                	        	operatinghoursafternoon.setVisibility(View.GONE);
                	        	lbloperatinge.setVisibility(View.GONE);
                	        	operatinghoursevening.setVisibility(View.GONE);
                	        	lblcontact.setVisibility(View.GONE);
                	        	contactnumber.setVisibility(View.GONE);
                	        	lblfax.setVisibility(View.GONE);
                	        	fax.setVisibility(View.GONE);
                	        	
                	        	
                	         	 
                	        	label2.setText(label2.getText() +  "\n" + mapname);
                	            }
                	            map.setOnMarkerClickListener(new OnMarkerClickListener() {

                	              	@Override
                	              	public boolean onMarkerClick(Marker marker) {
                	              		 TextView info = (TextView) findViewById(R.id.info);
                	              	 
                	              		 info.setText(marker.getTitle());
                	                 	info.setVisibility(View.VISIBLE);
                	                	address.setVisibility(View.VISIBLE);
                	                
                	                	lbloperatinghours.setVisibility(View.VISIBLE);
                	                
                	                	operatinghoursmorning.setVisibility(View.VISIBLE);
                	                
                	                	operatinghoursafternoon.setVisibility(View.VISIBLE);
                	                
                	                	operatinghoursevening.setVisibility(View.VISIBLE);

                	                	String readgmap = readMaps();
                	                	try {
                	                        JSONArray jsonArray = new JSONArray(readgmap);
                	                    //    Log.i(ParseJSON.class.getName(),
                	                      //      "Number of entries " + jsonArray.length());
                	                        for (int i = 0; i < jsonArray.length(); i++) {
                	                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                	                          Log.e("Name",jsonObject.getString("name"));
                	                          Log.e("test",marker.getTitle());
                	                          if( jsonObject.getString("name").equals(marker.getTitle())) {
                	                        	  address.setText(jsonObject.getString("address"));
                	                        	  operatingdays.setText(jsonObject.getString("operatingdays"));
                	                        	  operatinghoursmorning.setText(jsonObject.getString("operatinghoursmorning"));
                	                        	  operatinghoursafternoon.setText(jsonObject.getString("operatinghoursafternoon"));
                	                        	  operatinghoursevening.setText(jsonObject.getString("operatinghoursevening"));
                	                        	  contactnumber.setText(jsonObject.getString("contactno"));
                	                        	  fax.setText(jsonObject.getString("faxnumber"));
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  
                	                        	  break;
                	                          }
                	                        }
                	                	}catch (Exception e) {
                	                        e.printStackTrace();
                	                    }
                	              		 
                	              		 
                	              		return false;
                	              	}
                	              	
                	              });
                	            LatLng toCenter = new LatLng(1.424275, 103.838379);


                	            map.moveCamera(CameraUpdateFactory.newLatLngZoom(toCenter, 10));
                	            map.setMyLocationEnabled(true);
                	          } catch (Exception e) {
                	            e.printStackTrace();
                	          }
                		 
                	 }
                         else {
                        	 showAlert();
                        	 
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
    public void showAlert(){
  		clinic.this.runOnUiThread(new Runnable() {
  		    public void run() {
  		    	AlertDialog.Builder builder = new AlertDialog.Builder(clinic.this);
  		    	builder.setTitle("Error.");
  		    	builder.setMessage("Please turn on GPS and enable current location! Or wait for GPS to get your current location!")  
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
  public String readMaps3() {
    	
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httppost= new HttpPost("http://ezdr.com.sg/appsettings/search.php"); // make sure the url is correct.
		
        try {
        	//add your data
			nameValuePairs = new ArrayList<NameValuePair>(1);
			// Getting latitude of the current location
			EditText et = (EditText) findViewById(R.id.searchs);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("search",et.getText().toString())); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response= client.execute(httppost);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line);
            }
          } else {
            Log.e("ERROR", "Failed to load map");
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return builder.toString();
      }
  public String readMaps2() {
    	
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httppost= new HttpPost("http://ezdr.com.sg/appsettings/getnearest.php"); // make sure the url is correct.
		
        try {
        	//add your data
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Getting latitude of the current location
	      
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("currentlat",String.valueOf(map.getMyLocation().getLatitude())));  // $Edittext_value = $_POST['Edittext_value'];
			nameValuePairs.add(new BasicNameValuePair("currentlng",String.valueOf(map.getMyLocation().getLongitude()))); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response= client.execute(httppost);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line);
            }
          } else {
            Log.e("ERROR", "Failed to load map");
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return builder.toString();
      }
    public String readMaps() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://ezdr.com.sg/appsettings/clinics.php");
        try {
          HttpResponse response = client.execute(httpGet);
          StatusLine statusLine = response.getStatusLine();
          int statusCode = statusLine.getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
              builder.append(line);
            }
          } else {
            Log.e("ERROR", "Failed to load map");
          }
        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return builder.toString();
      }




    
    
}

