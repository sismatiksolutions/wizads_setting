package com.wizbox.settings;



import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class Videoplay extends Activity {
	VideoView vv;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.video);
	
    vv = (VideoView) findViewById(R.id.videoView1);
    String path = "android.resource://" + "com.wizbox.settings" + "/" + R.raw.myvideo;
    vv.setVideoURI(Uri.parse(path));
    vv.start();
   vv.setOnCompletionListener(new OnCompletionListener() {
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.stop();
		 String path = "android.resource://" + "com.wizbox.settings" + "/" + R.raw.myvideo;
		    vv.setVideoURI(Uri.parse(path));
		    vv.start();
		
		
	}
});
}

	
}
