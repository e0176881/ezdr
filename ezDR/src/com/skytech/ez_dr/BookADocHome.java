package com.skytech.ez_dr;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.skytech.ez_dr.adapter.TitleNavigationAdapter;
import com.skytech.ez_dr.model.SpinnerNavItem;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
public class BookADocHome extends FragmentActivity{

	// action bar
	private ActionBar actionBar;
	 ListView l;
	// Title navigation Spinner data
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter adapter;
	List<Fragment> fragList = new ArrayList<Fragment>();
	// Refresh menu item
	private MenuItem refreshMenuItem;
	GoogleMap map ;
	String oaddress, odays, ohoursmorning, ohoursafternoon, ohoursevening, ocontactno, ofaxnumber;
	String symb,desc,mc,mapname,clickedname;
	public static Context appContext;
	Double lat,lng;
	ArrayList <String> list = new ArrayList <String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 StrictMode.ThreadPolicy policy = new StrictMode.
	        	    ThreadPolicy.Builder().permitAll().build();
	        	    StrictMode.setThreadPolicy(policy); 
		setContentView(R.layout.fragment);
		appContext = getApplicationContext();
		//ActionBar
        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab PlayerTab = actionbar.newTab().setText("Fragment A");
        ActionBar.Tab StationsTab = actionbar.newTab().setText("Fragment B");

        Fragment PlayerFragment = new BookDoc();
        Fragment StationsFragment = new HealthBook();

        PlayerTab.setTabListener(new MyTabsListener(PlayerFragment));
        StationsTab.setTabListener(new MyTabsListener(StationsFragment));

        actionbar.addTab(PlayerTab);
        actionbar.addTab(StationsTab);

	}
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        return true;
	    }

	    
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
				case R.id.menuitem_search:
					Toast.makeText(appContext, "search", Toast.LENGTH_SHORT).show();
					return true;
				case R.id.menuitem_add:
					Toast.makeText(appContext, "add", Toast.LENGTH_SHORT).show();
					return true;
				case R.id.menuitem_share:
					Toast.makeText(appContext, "share", Toast.LENGTH_SHORT).show();  
					return true;
				case R.id.menuitem_feedback:
					Toast.makeText(appContext, "feedback", Toast.LENGTH_SHORT).show();
					return true;
				case R.id.menuitem_about:
					Toast.makeText(appContext, "about", Toast.LENGTH_SHORT).show();
					return true;
				case R.id.menuitem_quit:
					Toast.makeText(appContext, "quit", Toast.LENGTH_SHORT).show();
					return true;
			}
			return false;
		}
		
	  
	    
	}

class MyTabsListener implements ActionBar.TabListener {
	public Fragment fragment;
	
	public MyTabsListener(Fragment fragment) {
		this.fragment = fragment;
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(BookADocHome.appContext, "Reselected!", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.fragment_container, fragment);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(fragment);
	}
	
}