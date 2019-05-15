package com.skytech.ez_dr;

import java.util.ArrayList;
import java.util.List;



import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsActivity extends Activity {
	public class codeLeanChapter {
		String chapterName;
		String chapterDescription;
		String queryz;
	}
	public class fuckintent {
		
		String queryz;
	}
	CodeLearnAdapter chapterListAdapter;
	private TextView txtQuery;
	fuckintent iaa = new fuckintent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
chapterListAdapter = new CodeLearnAdapter();
        
        ListView codeLearnLessons = (ListView)findViewById(R.id.searchListView);
        codeLearnLessons.setAdapter(chapterListAdapter);
        
        codeLearnLessons.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				codeLeanChapter chapter = chapterListAdapter.getCodeLearnChapter(arg2);
				
				Toast.makeText(SearchResultsActivity.this, chapter.chapterName,Toast.LENGTH_LONG).show();
				
			}
		});
		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		txtQuery = (TextView) findViewById(R.id.txtQuery);

		handleIntent(getIntent());
	}
	public class CodeLearnAdapter extends BaseAdapter {

    	List<codeLeanChapter> codeLeanChapterList = getDataForListView();
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return codeLeanChapterList.size();
		}

		@Override
		public codeLeanChapter getItem(int arg0) {
			// TODO Auto-generated method stub
			return codeLeanChapterList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			if(arg1==null)
			{
				LayoutInflater inflater = (LayoutInflater) SearchResultsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				arg1 = inflater.inflate(R.layout.listitem, arg2,false);
			}
			
			TextView chapterName = (TextView)arg1.findViewById(R.id.textView1);
			TextView chapterDesc = (TextView)arg1.findViewById(R.id.textView2);
			
			codeLeanChapter chapter = codeLeanChapterList.get(arg0);
			
			chapterName.setText(chapter.chapterName);
			chapterDesc.setText(chapter.chapterDescription);
			
			return arg1;
		}
		
		public codeLeanChapter getCodeLearnChapter(int position)
		{
			return codeLeanChapterList.get(position);
		}

    }
private void handleIntent(Intent intent) {
		
		
}
	 public List<codeLeanChapter> getDataForListView()
	    {
	    	List<codeLeanChapter> codeLeanChaptersList = new ArrayList<codeLeanChapter>();
	    	
	    	Intent intent = new Intent();
	    	
	    	if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
				 String query = intent.getStringExtra(SearchManager.QUERY);
				 
				/**
				 * Use this query to display search results like 
				 * 1. Getting the data from SQLite and showing in listview 
				 * 2. Making webrequest and displaying the data 
				 * For now we just display the query only
				 */
				txtQuery.setText("Search Query: " + query);

			}
	    	
	    	for(int i=0;i<10;i++)
	    	{
	    		
	    		codeLeanChapter chapter = new codeLeanChapter();
	    		chapter.chapterName = intent.getStringExtra(SearchManager.QUERY);
	    		chapter.chapterDescription =intent.getStringExtra(SearchManager.QUERY);
	    		codeLeanChaptersList.add(chapter);
	    	}
	    	
	    	return codeLeanChaptersList;
	    	
	    }
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.activity_main_actions, menu);
			

			return super.onCreateOptionsMenu(menu);
		}
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data
	 */
	String fuckla;
	private void setfucklaz(String sd)
	{
	fuckla = sd;
	}
	private String getfkla()
	{
	return fuckla;
	}


	}

