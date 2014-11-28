package com.wizbox.wizadssettings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;



import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class FirstTimeInstaller extends Activity {

Button install,update;
	static int progress = 0;

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;

	private static final String[] apkNameList = {

	"Online Picture", "Picasa", "Local Photos", "Pixlr", "Flicker",
			"Indian Movies", "International Movies", "International TV",
			"Movietube", "Filmon", "Local Videos", "Big Flix", "Box Tv",
			"Spuul", "Youtube Tv", "Local Music", "English Songs",
			"Hindi Songs", "Radio", "Apps Organizer", "Mx Player", "My Media",
			"Network Test", "WizBox" };
	private static final String apkName[] = {

	"OnlinePictures.apk", "PicasaTool.apk", "F-Stop.apk", "PixlrExpress.apk",
			"Flickr.apk", "IndianMovies.apk", "InternationalMovies.apk",
			"InternationalTv.apk", "MovieTube.apk", "WorldLiveTVChannels.apk",
			"Videos.apk", "BIGFLIXforTablet.apk", "BoxTV.apk", "Spuul.apk",
			"YouTube.apk", "GooglePlayMusic.apk", "MúsicaPop.apk",
			"Hungama.apk", "RadioFM.apk", "WixBoxAppsOrganizer.apk",
			"MXPlayer.apk", "AirShare.apk", "Speedtest.apk", "WizBox.apk"

	};

	private static final String apkPkgNameList[] = {

	"com.wizbox.onlinepictures", "larry.zou.colorfullife", "com.fstop.photo",
			"com.pixlr.express", "com.yahoo.mobile.client.android.flickr",
			"com.wizbox.indianmovies", "com.wizbox.internationalmovies",
			"com.wizbox.internationaltv", "topandroidapp.movietube",
			"com.filmon.android.aff21136Yf",
			"com.androidcodemonkey.videos.free", "com.bigflix.bigflixtablet",
			"com.til.boxtv.mobile", "com.spuul.android",
			"com.google.android.youtube.googletv", "com.google.android.music",
			"com.musicnetwork.vivepop", "com.hungama.myplay.activity",
			"com.radio.fmradio", "com.wizbox.appsorganizer",
			"com.mxtech.videoplayer.ad", "com.softmedia.airshare",
			"org.zwanoo.android.speedtest", "org.xbmc.xbmc"

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fti);
		
		install =(Button) findViewById(R.id.btn_install);
		update =(Button) findViewById(R.id.btn_checkupdate);

		
		
		install.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startDownload();
			}
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	private void startDownload() {

		String[] url = new String[apkName.length];

		for (int i = 0; i < apkName.length; i++) {
			url[i] = "http://appstore.wizbox.in/installer/" + apkName[i];
		}

		new DownloadFileAsync().execute(url);

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Downloading files..." + apkNameList[0]);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(true);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				for (int i = 0; i < aurl.length; i++) {

					progress = i;
					URL url = new URL(aurl[i]);
					URLConnection conexion = url.openConnection();
					conexion.connect();

					int lenghtOfFile = conexion.getContentLength();
					Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

					File wizboxapps = new File("/sdcard/wizboxapps/");
					// have the object build the directory structure, if needed.
					wizboxapps.mkdirs();

					InputStream input = new BufferedInputStream(
							url.openStream());
					OutputStream output = new FileOutputStream(
							"/sdcard/wizboxapps/" + apkName[progress]);

					byte data[] = new byte[1024];

					long total = 0;

					// publishProgress(""+(int)((progress*100)/apkName.length));

					while ((count = input.read(data)) != -1) {
						total += count;
						publishProgress(""
								+ (int) ((total * 100) / lenghtOfFile));

						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				}
			} catch (Exception e) {
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			String pg = ""
					+ (int) ((FirstTimeInstaller.progress * 100) / apkName.length);
			mProgressDialog.setProgress(Integer.parseInt(pg));
			mProgressDialog.setMessage("Downloading files..."
					+ apkNameList[FirstTimeInstaller.progress] + "("
					+ Integer.parseInt(progress[0]) + "%)");
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}

}