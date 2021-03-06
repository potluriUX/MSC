package com.rs.kismet_mallu;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import com.google.android.gms.ads.*;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;






/**
 * The Activity can retrieve Videos for a specific username from YouTube</br>
 * It then displays them into a list including the Thumbnail preview and the title</br>
 * There is a reference to each video on YouTube as well but this isn't used in this tutorial</br>
 * </br>
 * <b>Note<b/> orientation change isn't covered in this tutorial, you will want to override
 * onSaveInstanceState() and onRestoreInstanceState() when you come to this
 * </br>
 * @author ravi_manasa
 */
public class MainActivity extends Activity{
    // A reference to our list that will hold the video details
	private VideosListView2 listView;
	private VideosListView2 relatedListView;
	private int rnd;
	private HashMap<String, String> hmap = new HashMap<String, String>() ;
	private String previd;
	InterstitialAd interstitial;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_main2);
      //  setContentView(R.layout.main);
        Button bt = (Button)findViewById(R.id.roll);
        bt.performClick();         
         interstitial = new InterstitialAd(MainActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-3730266544385182/7453002796");
       AdView adView =   (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .build();
     //   adView.setAdSize(AdSize.BANNER);
        //adView.setAdUnitId("ca-app-pub-3730266544385182/1506030791");ca-app-pub-3730266544385182/1506030791
       
       // AdView adView = new AdView(this);
        adView.loadAd(adRequest);
        // Load ads into Interstitial Ads
        //interstitial.loadAd(adRequest);
        interstitial.loadAd(adRequest);
        
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
        // Prepare an Interstitial Ad Listener
    
        listView = (VideosListView2) findViewById(R.id.videosListView);
        relatedListView = (VideosListView2) findViewById(R.id.relatedVideosListView);
    	listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				 
				Video selection = (Video)parent.getItemAtPosition(position);
					 
				Intent intent = new Intent(context, MainActivity2.class);
				Bundle b = new Bundle();
					
				/*String selection = parent.getItemAtPosition(position).toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);				
				WebLinks w = db.getLink(selection);*/
				
				b.putString("key", selection.getid());
				b.putString("previd", previd);
				intent.putExtras(b);
				previd = selection.getid();
				Log.d(previd + "previd");
				startActivity(intent);
			}
			
		});
    	/*relatedListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Video selection = (Video)parent.getItemAtPosition(position);				 
				Intent intent = new Intent(context, MainActivity2.class);
				Bundle b = new Bundle();
								
				/*String selection = parent.getItemAtPosition(position).toString();
				WebLinks testLink = (WebLinks) parent.getItemAtPosition(position);
				WebLinks w = db.getLink(selection);*/
				
			/*	b.putString("key", selection.getid());
				intent.putExtras(b);
				startActivity(intent);
			}
			
		});*/
    	
    	try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }    
    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    // This is the XML onClick listener to retrieve a users video feed
    public void getUserYouTubeFeed(View v){
    	// We start a new task that does its work on its own thread
    	// We pass in a handler that will be called when the task has finished
    	// We also pass in the name of the user we are searching YouTube for
    	String packagename = this.getClass().getPackage().getName();
    	if(packagename.equals("com.rm.kismet_cooking")){
	    	String[] users = { 
	    			"LauraVitalesKitchen", "vahchef", "JamieOliver",  "foodwishes", "BBCFood", "BarbecueWeb","ShowMeTheCurry", 
	    			"EverydayFoodVideos","BBCFood", "robjnixon", "vahchef", "JamieOliver", "BBCFood", "LauraVitalesKitchen",
	    			"SimpleCookingChannel", "CookingChannel", "bettyskitchen", "foodwishes", "robjnixon", "OnePotChefShow", 
	    			"leanbodylifestyle", "wantanmien", "Maangchi", "runnyrunny999"
	    	};
	    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
	    	
	        if(rnd > (users.length-2)){
	        	rnd=0;
	        }else{
	        	rnd++;
	        }
    	}
    	else if (packagename.equals("com.ravi_manasa.kismet")) {
    		String[] users = {"sribalajimovies", "shalimarcinema", "rajshritelugu", "thesantoshvideos",  
        			"sribalajimovies", 
        			"geethaarts","idreammovies", "sribalajimovies", "thesantoshvideos",  
        			"geethaarts", "shemarootelugu", "adityacinema", "sribalajimovies",  
        			"mangoVideos", "thesantoshvideos",
        			 "newvolgavideo",  "geethaarts",  "rajshritelugu", "shalimarcinema", 
        			 "thecinecurrytelugu", "rajshritelugu", "sribalajimovies", "shalimarcinema"
        	};
	new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
	    	
	        if(rnd > (users.length-2)){
	        	rnd=0;
	        }else{
	        	rnd++;
	        }
		}
    	else if(packagename.equals("com.rm.kismet_makeup")){
    		String[] users = {"MichellePhan","beautydept","emilynoel83","LuxyHair","cutepolish","missglamorazzi",
    		"kandeejohnson",
    		"MakeupByTiffanyD","SarahVictor","DiamondsAndHeels14"
    		};
    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
    		    	
    		        if(rnd > (users.length-2)){
    		        	rnd=0;
    		        }else{
    		        	rnd++;
    		        }
    	}
    	else if(packagename.equals("com.rm.kismet_tcomedy")){
    		String[] users = {"TeluguComedyCentral", "NavvulaTV", "SriBalajiComedy", "idreamcomedy", "santosh9sri"
    		};
    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
    		    	
    		        if(rnd > (users.length-2)){
    		        	rnd=0;
    		        }else{
    		        	rnd++;
    		        }
    	}
    	else if(packagename.equals("com.rm.kismet_tamil")){
    		String[] users = {"tamilmovies", "tamilpeak", "WAMIndiaTamil", "rajshritamil", "tamilbiscoot", "RajVideoVisionTamil", "CinemaJunction",
        			"tamilmovies", "rajshritamil", "tamilpeak","CinemaJunction"
    		};
    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
    		    	
    		        if(rnd > (users.length-2)){
    		        	rnd=0;
    		        }else{
    		        	rnd++;
    		        }
    	}
    	
    	
    	
    	else if(packagename.equals("com.rs.kismet_kids")){
    		String[] users = {"hooplakidz", "BabyFirstTV", "AllBabiesChannel", "kidscamp", "NurseryRhymeStreet", "TeeHeeTown", "KidsTV123",
        			"omigrad", "DreamEnglishKids"
    		};
    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
    		    	
    		        if(rnd > (users.length-2)){
    		        	rnd=0;
    		        }else{
    		        	rnd++;
    		        }
    	}
    	else if(packagename.equals("com.rs.kismet_mallu")){
    		String[] users = {"hungamamalayalam","LehrenMalayalam","malayalambiscoot","shahmonhtnsour","apimalayalam","malayalamfullmoviez","SainaVideoVision",
    				"MillenniumTalkies","malayalachalachitram","MalayalamFullMovieHM"
    		};
    		new GetYouTubeUserVideosTask(responseHandler, responseRelatedHandler, users[rnd], hmap).run();
    		    	
    		        if(rnd > (users.length-2)){
    		        	rnd=0;
    		        }else{
    		        	rnd++;
    		        }
    	}
    	
    }
   
    // This is the handler that receives the response when the YouTube task has finished
	Handler responseHandler = new Handler() {
		public void handleMessage(Message msg) {
			populateListWithVideos(msg);
		};
	};
	Handler responseRelatedHandler = new Handler() {
		public void handleMessage(Message relmsg) {
			populateListWithRelatedVideos(relmsg);
		};
	};

	/**
	 * This method retrieves the Library of videos from the task and passes them to our ListView
	 * @param msg
	 */
	private void populateListWithVideos(Message msg) {
		// Retrieve the videos are task found from the data bundle sent back
		Library lib = (Library) msg.getData().get(GetYouTubeUserVideosTask.LIBRARY);
		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		listView.setVideos(lib.getVideos());
		
	}
	
	private void populateListWithRelatedVideos(Message relmsg) {
		// Retrieve the videos are task found from the data bundle sent back
		Library relatedlib = (Library) relmsg.getData().get(GetYouTubeUserVideosTask.RELATEDLIBRARY);
		
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		relatedListView.setVideos(relatedlib.getVideos());
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void openHistory() {			 
		Intent intent = new Intent(this, HistoryActivity.class);
Bundle b = new Bundle();
		
	
		
		b.putString("favactivity", "false");
		intent.putExtras(b);
	    startActivity(intent);
	}
	public void openFav() {			 
		Intent intent = new Intent(this, HistoryActivity.class);
		Bundle b = new Bundle();
		

		
		b.putString("favactivity", "true");
		intent.putExtras(b);
	    startActivity(intent);
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    
	        if(item.getItemId()==R.id.action_history){
	            openHistory();
	            return true;
	        }else if
	        (item.getItemId()== R.id.action_fav){
	        	openFav();
	        	return true;
	        }
	       
	            return super.onOptionsItemSelected(item);
	        
	        //return super.onOptionsItemSelected(item);
	    
	}
	
}