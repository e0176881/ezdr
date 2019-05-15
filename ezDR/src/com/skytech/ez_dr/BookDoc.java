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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.app.Fragment;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BookDoc extends Fragment {
	GoogleMap map ;
	ListView l;
	MapView mapView;
	String oaddress, odays, ohoursmorning, ohoursafternoon, ohoursevening, ocontactno, ofaxnumber;
	String symb,desc,mc,mapname,clickedname;
	List<NameValuePair> nameValuePairs;
	
	Double lat,lng;
	
	
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		 StrictMode.ThreadPolicy policy = new StrictMode.
                 ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
	    View v = inflater.inflate(R.layout.activity_main, container, false); // here got this
	 //idk sia fkkkk 99% left sia
	    String readgmapz = readMaps2();
		 try {
	            JSONArray jsonArray = new JSONArray(readgmapz);

	   	   
	           
	            ArrayList <String> list = new ArrayList <String>();
   	            for (int i = 0; i < jsonArray.length(); i++) {
   	            	 JSONObject jsonObject = jsonArray.getJSONObject(i);
      	              mapname =  jsonObject.getString("name");

      	              oaddress =  jsonObject.getString("address");
      	           list.add(new String(mapname));
      	           l=(ListView) v.findViewById(R.id.listView1); //gg is it time to pas u back ? :P the action bar menu also there le?tat one easy de u comeh ere
      		   	  ArrayAdapter<String> adapterzz=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
      		   	  l.setAdapter(adapterzz);
   	            }
	   	  
	}catch (Exception e) {
       e.printStackTrace();
   }
          String readgmap = readMaps();
		    try {
		   
		    	
		        JSONArray jsonArray = new JSONArray(readgmap);
		    //    Log.i(ParseJSON.class.getName(),
		      //      "Number of entries " + jsonArray.length());
		        for (int i = 0; i < jsonArray.length(); i++) {
		          JSONObject jsonObject = jsonArray.getJSONObject(i);
		          mapname =  jsonObject.getString("name");
		          oaddress =  jsonObject.getString("address");
		          lat=jsonObject.getDouble("lat");
		          lng=jsonObject.getDouble("lng");
		      
		       // which working u say?i google le like no have clear fragment leh nvm this one i can fix soon but json got prob
		          map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		  	    ViewGroup.LayoutParams params = ((MapFragment)  getFragmentManager().findFragmentById(R.id.map)).getView().getLayoutParams();
		          params.height = 600;
		          ((MapFragment)  getFragmentManager().findFragmentById(R.id.map)).getView().setLayoutParams(params);
		          map.addMarker(new MarkerOptions()
		          .position(new LatLng(lat,lng))
		          .title(mapname)
		          .snippet( oaddress )
		          
		          );
		          LatLng toCenter = new LatLng(1.424275, 103.838379);


		          map.moveCamera(CameraUpdateFactory.newLatLngZoom(toCenter, 10));
		          map.setMyLocationEnabled(true);
		        }
		      
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		    Button viewall = (Button) v.findViewById(R.id.textViewff);
			viewall.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					 String readgmapla = readMaps();
					 ArrayList <String> list = new ArrayList <String>();
					 try {
						 
				            JSONArray jsonArray = new JSONArray(readgmapla);
				        	ArrayAdapter<String> adapterzz=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
				        	adapterzz.clear();
				   	   //just now it will clear the listitem readd de. now become error exception
				            
			    	            for (int i = 0; i < jsonArray.length(); i++) {
			    	            	 JSONObject jsonObject = jsonArray.getJSONObject(i);
			       	              mapname =  jsonObject.getString("name");
			       	            
			       	              oaddress =  jsonObject.getString("address");

			       	           list.add(new String(mapname));
			       	           System.out.println("List: " + list);
			       	   	 l=(ListView) getActivity().findViewById(R.id.listView1);
							
						
							l.setAdapter(adapterzz);  
			       	           
			    	            }
			    	           
				}catch (Exception e) {
			        e.printStackTrace();
			    }
// is kor kor found out :P gg but why top can beow cannot sia
				
				}
			});
       
return v;
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
	  public String readMaps2() {
	    	
	        StringBuilder builder = new StringBuilder();
	        HttpClient client = new DefaultHttpClient();
	        HttpPost httppost= new HttpPost("http://ezdr.com.sg/appsettings/getnearest.php"); // make sure the url is correct.
			
	        try {
	        	LatLng latl= getCurrentLocation(this.getActivity());
				nameValuePairs = new ArrayList<NameValuePair>(2);
				// Getting latitude of the current location
		      
				// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
				nameValuePairs.add(new BasicNameValuePair("currentlat",String.valueOf(latl.latitude)));  // $Edittext_value = $_POST['Edittext_value'];
				nameValuePairs.add(new BasicNameValuePair("currentlng",String.valueOf(latl.longitude))); 
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
	        public LatLng getCurrentLocation(Context context)
	        {
	            try
	            {
	                LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	                Criteria criteria = new Criteria();
	                String locProvider = locMgr.getBestProvider(criteria, false);
	                Location location = locMgr.getLastKnownLocation(locProvider);
	 
	                // getting GPS status
	                boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
	                // getting network status
	                boolean isNWEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	 
	                if (!isGPSEnabled && !isNWEnabled)
	                {
	                    // no network provider is enabled
	                    return null;
	                }
	                else
	                {
	                    // First get location from Network Provider
	                    if (isNWEnabled)
	                        if (locMgr != null)
	                            location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	 
	                    // if GPS Enabled get lat/long using GPS Services
	                    if (isGPSEnabled)
	                        if (location == null)
	                            if (locMgr != null)
	                                location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                }
	 
	                return new LatLng(location.getLatitude(), location.getLongitude());
	            }
	            catch (NullPointerException ne)
	            {
	                Log.e("Current Location", "Current Lat Lng is Null");
	                return new LatLng(0, 0);
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	                return new LatLng(0, 0);
	            }
	        }
}
