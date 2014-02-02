package com.rm.kismet_cooking;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This is a representation of a users video off YouTube
 * @author ravi_manasa
 */
public class Video implements Serializable {
	// The title of the video
	private String title;
	// A link to the video on youtube
	private String url;
	// A link to a still image of the youtube video
	private String thumbUrl;
	private String id;
	public Context context;
	private int currentImage = 0;
	private String prevvalue;
	OnClickListener listener1 = new OnClickListener(){ // the book's action
        @Override
        public void onClick(View v) {
                            
        	currentImage++;
            currentImage = currentImage % 2;

        	Button b1 = (Button)v.findViewById(R.id.fav);
        	 DatabaseHandler db = new DatabaseHandler(context);	
            //Set the image depending on the counter.
            switch (currentImage) {
           
            case 0: b1.setBackgroundResource(R.drawable.watch1); 
            String value2 = id;
    		
    		
    			db.deleteFav(id);   
    		
                    break;
            case 1: b1.setBackgroundResource(R.drawable.watch3);
		            String value = id;
		    		//DatabaseHandler db = new DatabaseHandler(context);	
		    		if(value!=prevvalue)
		    			db.addFav(new WebLinks(value));   
		    		 prevvalue = value;
            		break;
            default:b1.setBackgroundResource(R.drawable.watch1);     
            }
            
        }
    };
    
    OnClickListener listener2 = new OnClickListener(){ // the book's action
        @Override
        public void onClick(View v) {
                            
        	Intent intent = new Intent(context, MainActivity2.class);
			Bundle b = new Bundle();
			b.putString("key", id);
			intent.putExtras(b);
			context.startActivity(intent);            
        }
    };
	
	public Video(String title, String url, String thumbUrl, String id) {
		super();
		this.title = title;
		this.url = url;
		this.thumbUrl = thumbUrl;
		this.id = id;
	}
	public Video(String title, String url, String thumbUrl, String id, Context context)
    {
		super();
		this.title = title;
		this.url = url;
		this.thumbUrl = thumbUrl;
		this.id = id;
        this.context = context;
    }

	/**
	 * @return the title of the video
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * @return the url to this video on youtube
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the thumbUrl of a still image representation of this video
	 */
	public String getThumbUrl() {
		return thumbUrl;
	}
	public String getid(){
		return id;
	}
}