package com.ywcheng1985.blogreader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainListActivity extends ListActivity {

	protected String[] mBlogPostTitles;
	public static final int Number_of_Posts = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);

		try {
			URL blogFeedUrl = new URL("http://rss.cnn.com/rss/edition.rss" + Number_of_Posts);
			HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
			connection.connect();
		} 
		catch (MalformedURLException e) {
			Log.e(TAG,"Exception caught",e);
		} catch (IOException e) {
			Log.e(TAG,"Exception caught",e);
		}

		Resources resources = getResources();
		mBlogPostTitles = resources.getStringArray(R.array.android_names);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mBlogPostTitles);
		setListAdapter(adapter);

		//String message = getString(R.string.no_items);
		//Toast.makeText(this, message, Toast.LENGTH_LONG).show();

		Button t = (Button) findViewById(R.id.useLogo);
		t.setText(R.string.no_items);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
