package com.rm.kismet_cooking;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;


public class HistoryActivity extends Activity {
	private VideosListView2 historyListView;

    @SuppressLint("NewApi") 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);        
        historyListView = (VideosListView2) findViewById(R.id.historyVideosListView);
       
        DatabaseHandler db = new DatabaseHandler(this);
 
        // Inserting Contacts
        // Log.d("Insert: "); 
        // db.addLink(new WebLinks("pSDoWvzsRPY"));         
        // Reading all contacts
        // Log.d("Reading: "); 
        String id= "1";
        WebLinks Links = db.getLinkByID(id);   
          
        List<WebLinks> allLinks = db.getAllLinks();   
        for (WebLinks cn : allLinks) {
	        String log = "Id: "+cn.get_id()+" ,VideoId: " + cn.get_videoid();
	        //Writing Contacts to log
	        Log.d(log);
        }
        String str = String.valueOf(Links.get_videoid());
        new GetYouTubeHistoryVideosTask(responseRelatedHandler, str).run();
        
                
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    Handler responseRelatedHandler = new Handler() {
		public void handleMessage(Message relmsg) {
			populateListWithRelatedVideos(relmsg);
		};
	};
	
    private void populateListWithRelatedVideos(Message relmsg) {
		// Retrieve the videos are task found from the data bundle sent back
		Library relatedlib = (Library) relmsg.getData().get(GetYouTubeHistoryVideosTask.RELATEDLIBRARY);		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		historyListView.setVideos(relatedlib.getVideos());
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
