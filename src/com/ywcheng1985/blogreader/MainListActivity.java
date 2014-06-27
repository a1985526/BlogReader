package com.ywcheng1985.blogreader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainListActivity extends ListActivity {

	protected String[] mBlogPostTitles;
	public static final int Number_of_Posts = 20;
	public static final String TAG = MainListActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);		

		if(isNetworkAvailable()){
			GetBlogPostsTask getBlogPostTask = new GetBlogPostsTask();
			getBlogPostTask.execute();
			
		}else {
			Toast.makeText(this, "Network is unavailabl!", Toast.LENGTH_LONG).show();
		}
		//Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		boolean isAvailable = false;
		if(networkInfo != null && networkInfo.isConnected()){
			isAvailable = true;
		}
		return isAvailable;
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

	private class GetBlogPostsTask extends AsyncTask<Object, Void, String>{

		@Override
		protected String doInBackground(Object... params) {
			int responseCode = -1;

			try {
				URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/" + Number_of_Posts);
				HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
				connection.connect();

				responseCode = connection.getResponseCode();
				Log.i(TAG, "Response Code:" + responseCode);
			} 
			catch (MalformedURLException e) {
				Log.e(TAG,"Exception caught",e);
			} 
			catch (IOException e) {
				Log.e(TAG,"Exception caught",e);
			}
			catch(Exception e){
				Log.e(TAG,"Exception caught",e);
			}


			return "Code: " + responseCode;
		}


	}
}
