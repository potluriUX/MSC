package com.rs.kismet_mallu;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;






public class MainActivity2 extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

	static private final String DEVELOPER_KEY = "AIzaSyAfnzhBO-Nzj119V3gdV4LpWaTRGGSyE0A";
	static private final String VIDEO = "4SK0cUNMnMM";
	InterstitialAd interstitial;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_main);
		YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
	    youTubeView.initialize(DEVELOPER_KEY, this);
	    Bundle b = getIntent().getExtras();
		String value = b.getString("key");
		String previd = b.getString("previd");
		Boolean flag = b.getBoolean("historyflag");
		
	       AdView adView =   (AdView)this.findViewById(R.id.adView2);
	        AdRequest adRequest = new AdRequest.Builder()
	       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .build();
	     //   adView.setAdSize(AdSize.BANNER);
	        //adView.setAdUnitId("ca-app-pub-3730266544385182/1506030791");ca-app-pub-3730266544385182/1506030791
	       
	       // AdView adView = new AdView(this);
	        adView.loadAd(adRequest);
	        // Load ads into Interstitial Ads
	        //interstitial.loadAd(adRequest);
	     
		if(flag != true){
			DatabaseHandler db = new DatabaseHandler(this);	
			
			if(!value.equals(previd))
				db.addLink(new WebLinks(value));     
			Log.d(value+ previd +"is val preval" );
    		 
		
		
		}
		
	    for (int i=0; i < 1; i++)
	    {
	    	 Toast.makeText(this, "Hit settings button in player bar below to open in YouTube and " +
	    	 		"then share to Smart TV", Toast.LENGTH_LONG).show();
	    }
	    
	   
		// Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(MainActivity2.this, DEVELOPER_KEY, VIDEO, 0, true, false);

		  //  startActivityForResult(videoIntent, 1);
	
	}
	   public void displayInterstitial() {
	        // If Ads are loaded, show Interstitial else show nothing.
	        if (interstitial.isLoaded()) {
	            interstitial.show();
	        }
	    }
	@Override
	public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
		Toast.makeText(this, "Oh no! "+error.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
		Bundle b = getIntent().getExtras();
		String value = b.getString("key");
	
		
	   
	         
	    
		
	    
		player.loadVideo(value);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == 1 && resultCode != RESULT_OK) {
	        YouTubeInitializationResult errorReason = YouTubeStandalonePlayer.getReturnedInitializationResult(data);
	        if (errorReason.isUserRecoverableError()) {
	            errorReason.getErrorDialog(this, 0).show();
	        } else {
	            String errorMessage = String.format("PLAYER ERROR!!", errorReason.toString());
	            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	        }
	    }
	}

}
