package com.wizbox.wizadssettings;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;










import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends ListActivity implements OnClickListener {
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;

	LinearLayout ll;
	ImageView iv;
	EditText et1, et2;
	StringEntity se;
	SharedPreferences sp,response_sp;;
	String json = "";
	String macAddress;
	String passWord,Appname="";
	Intent in;
	ListView lv;
	int appno=0;
	Button btn_activate;
	Intent intent;
	Context mContext=MainActivity.this;
	SharedPreferences appPreferences;
	boolean isAppInstalled = false;
	boolean isSuccess = false;
	TextView activation;
//	HttpClient httpclient;HttpPost httpPost; HttpResponse httpResponse;
 int listid;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	private ProgressDialog mProgressDialog;

	String[] values = new String[] {

	"Bludo Wizboxads App","Topsun App", "WizboxAds App","Network Test App","Team Viewer App" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(android.os.Build.VERSION.SDK_INT>9 ){
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		
		response_sp = PreferenceManager.getDefaultSharedPreferences(this);
		isSuccess = response_sp.getBoolean("isSuccess",false);
		
		
		
		
		appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		isAppInstalled = appPreferences.getBoolean("isAppInstalled",false);
		if(isAppInstalled==false){

		           // add shortcutIcon code here
			 addShortcutIcon(); 
		}
		// finally isAppInstalled should be true.
		SharedPreferences.Editor editor1 = appPreferences.edit();
		editor1.putBoolean("isAppInstalled", true);
		editor1.commit();
		
		
       
		 //httpclient = new DefaultHttpClient();
			
		 // 2. make POST request to the given URL
		//httpPost = new HttpPost("http://nebula.wizbox.in/mobileapi/address.php");
		mProgressDialog = new ProgressDialog(MainActivity.this);
		tv1 = (TextView) findViewById(R.id.network);
		tv2 = (TextView) findViewById(R.id.disp);
		tv3 = (TextView) findViewById(R.id.apps);
		tv4 = (TextView) findViewById(R.id.wacc);
		tv5 = (TextView) findViewById(R.id.gacc);
		tv6 = (TextView) findViewById(R.id.net_test);
		tv7 = (TextView) findViewById(R.id.vid);
		tv8 = (TextView) findViewById(R.id.ss);
		tv9 = (TextView) findViewById(R.id.remote_access);
		
		ll = (LinearLayout) findViewById(R.id.myll);
		in = new Intent(android.content.Intent.ACTION_VIEW);
		iv = (ImageView) findViewById(R.id.image_network);

		ll.removeAllViews();
		ll.setVisibility(View.VISIBLE);

		LayoutInflater inflatermain = getLayoutInflater();
		View mainview = inflatermain.inflate(R.layout.hometext, null);
		ll.addView(mainview);

		boolean conStatus = false;
		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cn.getActiveNetworkInfo();
		if (nf != null && nf.isConnected() == true) {
			iv.setImageResource(R.drawable.ok);
			// Toast.makeText(this, "Network Available",
			// Toast.LENGTH_LONG).show();

			conStatus = true;
		} else {
			iv.setImageResource(R.drawable.no);
			// Toast.makeText(this, "Network not Available",
			// Toast.LENGTH_LONG).show();
		}

		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		

		

	}

	

	// Checking internet connection here
//	@Override
//	protected void onRestart() {
//		boolean conStatus = false;
//	
//		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo nf = cn.getActiveNetworkInfo();
//		if (nf != null && nf.isConnected() == true) {
//			iv.setImageResource(R.drawable.ok);
//			// Toast.makeText(this, "Network Available",
//			// Toast.LENGTH_LONG).show();
//
//			conStatus = true;
//		} else {
//			iv.setImageResource(R.drawable.no);
//			// Toast.makeText(this, "Network not Available",
//			// Toast.LENGTH_LONG).show();
//		}
//
//		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
//		lv.setAdapter(adapter);
//		
//
//	}

	private void addShortcutIcon() {
		// TODO Auto-generated method stub
	
		  Intent shortcutIntent = new Intent(getApplicationContext(),
	                MainActivity.class);
	        
	        shortcutIntent.setAction(Intent.ACTION_MAIN);
	        //shortcutIntent is added with addIntent
	        Intent addIntent = new Intent();
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "WizBox AdsSetup");
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
	                        R.drawable.ic_launcher));

	        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	        // finally broadcast the new Intent
	        getApplicationContext().sendBroadcast(addIntent);
	    
		
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.network: // Choosing Connection Wifi or Ethernet
			
			listid=1;
			resetcolor(); // Reseting Color Of All the Button
			tv1.setTextColor(Color.parseColor("#3BB9FF"));

			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain = getLayoutInflater();
			View mainview = inflatermain.inflate(R.layout.hometext, null);
			ll.addView(mainview);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);

			// Set title
			alertDialogBuilder.setTitle("Set Your Network Setting");

			// Set dialog message
			alertDialogBuilder
					.setMessage("Choose one Configuration")
					.setCancelable(true)
					.setPositiveButton("Wifi",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									// MainActivity.this.finish();
									Intent wi = new Intent(Intent.ACTION_VIEW);
									wi.addCategory(Intent.CATEGORY_LAUNCHER);
									wi.setClassName("com.android.settings",
											"com.android.settings.wifi.WifiSettings");
									wi.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									try {
										startActivity(wi);
									} catch (ActivityNotFoundException e) {

										// ShowAlert("Music");
									}
								}
							})
					.setNegativeButton("Ethernet",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									Intent et = new Intent(Intent.ACTION_VIEW);
									et.addCategory(Intent.CATEGORY_LAUNCHER);
									et.setClassName("com.android.settings",
											"com.android.settings.Settings");
									et.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									try {
										startActivity(et);
									} catch (ActivityNotFoundException e) {

										// ShowAlert("Music");
									}
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			break;

		case R.id.disp: // Setting Display Setting
			listid=2;
			resetcolor();
			tv2.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain1 = getLayoutInflater();
			View mainview1 = inflatermain1.inflate(R.layout.hometext, null);
			ll.addView(mainview1);
			Intent dis = new Intent(Intent.ACTION_VIEW);
			dis.addCategory(Intent.CATEGORY_LAUNCHER);
			dis.setClassName("com.android.settings",
					"com.android.settings.ScreenSettings");
			dis.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(dis);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}
			break;

		case R.id.apps: // Getting List Of Required Applicaton
			listid=3;
			resetcolor();
			tv3.setTextColor(Color.parseColor("#3BB9FF"));
			Account();
			if(isSuccess==false){
				try{
				startActivity(new Intent(MainActivity.this,Customdialog.class));
//				Toast.makeText(getApplicationContext(), "Please Register Your Account First", Toast.LENGTH_LONG).show();
//			Account();
			}catch(Exception e){
				Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
			}
			}
			else
			{
			// ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);
			ll.removeAllViews();

			LayoutInflater inflater1 = getLayoutInflater();
			View myview2 = inflater1.inflate(R.layout.appinflate, null);
			ll.addView(myview2);
			lv = (ListView) myview2.findViewById(R.id.list);

			MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,
					values);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v,
						int position, long arg3) {
					// TODO Auto-generated method stub
					//ImageView imageView = (ImageView) v.findViewById(R.id.icon);
					//imageView.setImageResource(R.drawable.ok);

					Intent i = new Intent(android.content.Intent.ACTION_VIEW);

					boolean installed = false;

					switch (position) {
					case 0:
						
						appno=1;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.bludo.sismatik.wizboxadv");
																					
																					// Bludo Wizads App
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                       String link="http://appstore.wizbox.in/ads/BludoWizBoxAdv.apk";
									 startDownload(link);
			                    
			                   
							
						} else {
							Toast.makeText(getApplicationContext(),
									"Bludo Wizads App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
                       case 1:
						
						appno=2;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.sismatik.wizboxadv");
																					
																					// Bludo Wizads App
																					// is
																					// installed
																					// or
																					// not

						if (!installed) {
							
			                      
			                       String link="http://appstore.wizbox.in/ads/WizBoxAdv.apk";
									 startDownload(link);
			                    
			                   
							
						} else {
							Toast.makeText(getApplicationContext(),
									"Topsun App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					case 2:
						appno=3;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.sismatik.wizboxads");// Checking
																						// TeamViewer
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/ads/WizboxAds.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"WizboxAds App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					

					case 3:
						appno=4;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("org.zwanoo.android.speedtest");// Checking
																						// Radio
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/ads/Speedtest.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Network Test App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;

					case 4:
						appno=5;
						mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
						installed = isPackageInstalled("com.teamviewer.quicksupport.market");// Checking
																						// TeamViewer
																						// app
																						// is
																						// installed
																						// or
																						// not

						if (!installed) {
							
			                      
			                	   String link="http://appstore.wizbox.in/ads/TeamViewer.apk";
									 startDownload(link);
			                     
						} else {
							Toast.makeText(getApplicationContext(),
									"Team Viewer App is already installed",
									Toast.LENGTH_LONG).show();
						}
						break;
					
					}
				}

				private void startDownload(String myurl) {
					
        String url = myurl;
        new DownloadFileAsync().execute(url);
    }
	

				private boolean isPackageInstalled(String packagename) {
					PackageManager pm = MainActivity.this.getPackageManager();
					try {
						pm.getPackageInfo(packagename,
								PackageManager.GET_ACTIVITIES);
						return true;
					} catch (NameNotFoundException e) {
						return false;
					}
				}

			});
			}
			break;

		case R.id.wacc: // Wizbox Account
			Account();
break;


			case R.id.gacc: // Google Account
			listid=5;
			resetcolor();
			tv5.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain2 = getLayoutInflater();
			View mainview2 = inflatermain2.inflate(R.layout.hometext, null);
			ll.addView(mainview2);
			Intent ga1 = new Intent(Intent.ACTION_VIEW);
			ga1.addCategory(Intent.CATEGORY_LAUNCHER);
			ga1.setClassName("com.android.settings",
					"com.android.settings.accounts.AddAccountSettings");
			ga1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(ga1);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}
			break;

		case R.id.net_test: // Checking internet Speed Using Ookla App
			listid=6;
			resetcolor();
			tv6.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain3 = getLayoutInflater();
			View mainview3 = inflatermain3.inflate(R.layout.hometext, null);
			ll.addView(mainview3);
			Intent nt;
			PackageManager manager = getPackageManager();
			try {
				nt = manager
						.getLaunchIntentForPackage("org.zwanoo.android.speedtest");
				if (nt == null)
					throw new PackageManager.NameNotFoundException();
				nt.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(nt);
			} catch (Exception e) {

				// ShowAlert("Picture");
			}

			break;

		case R.id.vid: // Testing Video Playing
			listid=7;
			resetcolor();
			tv7.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain4 = getLayoutInflater();
			View mainview4 = inflatermain4.inflate(R.layout.hometext, null);
			ll.addView(mainview4);
			Intent i = new Intent(MainActivity.this, Videoplay.class);
			startActivity(i);

			break;

		case R.id.ss: // Calling System Setting
			listid=8;
			resetcolor();
			tv8.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain5 = getLayoutInflater();
			View mainview5 = inflatermain5.inflate(R.layout.hometext, null);
			ll.addView(mainview5);
			Intent ss = new Intent(Intent.ACTION_VIEW);
			ss.addCategory(Intent.CATEGORY_LAUNCHER);
			ss.setClassName("com.android.settings",
					"com.android.settings.Settings");
			ss.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			try {
				startActivity(ss);
			} catch (ActivityNotFoundException e) {

				// ShowAlert("Music");
			}

			break;
			
		case R.id.remote_access: //  Calling Remote Access
			listid=8;
			resetcolor();
			tv9.setTextColor(Color.parseColor("#3BB9FF"));
			ll.removeAllViews();
			ll.setVisibility(View.VISIBLE);

			LayoutInflater inflatermain6 = getLayoutInflater();
			View mainview6 = inflatermain6.inflate(R.layout.hometext, null);
			ll.addView(mainview6);
			Intent ra;
			PackageManager manager1 = getPackageManager();
			try {
				ra = manager1
						.getLaunchIntentForPackage("com.teamviewer.quicksupport.market");
				if (ra == null)
					throw new PackageManager.NameNotFoundException();
				ra.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(ra);
			} catch (Exception e) {

				// ShowAlert("Picture");
			}

			break;
		
		}

	}

	private void Account() {
		listid=4;
		resetcolor();
		tv4.setTextColor(Color.parseColor("#3BB9FF"));
		ll.setVisibility(View.VISIBLE);
		ll.removeAllViews();

		LayoutInflater inflater = getLayoutInflater();
		View myview = inflater.inflate(R.layout.accountinflate, null);
		ll.addView(myview);

		

		btn_activate = (Button) myview.findViewById(R.id.btn_send);
		et1 = (EditText) myview.findViewById(R.id.et_username);
		et2 = (EditText) myview.findViewById(R.id.et_password);
		activation = (TextView) myview.findViewById(R.id.tv_result);
		WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo winfo = wifimanager.getConnectionInfo();
		macAddress = winfo.getMacAddress();

		

		if (macAddress == null) {

			Toast.makeText(getApplicationContext(), "wi-fi is disabled",
					Toast.LENGTH_LONG).show();
		} else {
			et1.setText(macAddress);
			et1.setEnabled(false);
		}

		sp = getSharedPreferences("mypref", 0);

		et1.setText(sp.getString("macid", macAddress));
		
		
        btn_activate.setOnClickListener( new OnClickListener() {

@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
	passWord = et2.getText().toString();
	InputStream is = null;
	String result = "";
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	try {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://nebula.wizbox.in/mobileapi/address.php");
		nameValuePairs.add(new BasicNameValuePair("mac",
				macAddress));
		nameValuePairs.add(new BasicNameValuePair("password",
				passWord));
		httppost.setEntity(new UrlEncodedFormEntity(
				nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		is = entity.getContent();
	}

	catch (Exception e) {
		Log.e("log_tag",
				"Error in http connection " + e.toString());
	}
	
	// convert response to string
      try 
      {
           BufferedReader reader = new BufferedReader(new InputStreamReader( is, "iso-8859-1"), 8);
           StringBuilder sb = new StringBuilder();
           String line = null;

       while ((line = reader.readLine()) != null) 
       {
           sb.append(line + "\n");
       }

        is.close();
        result = sb.toString();
        
        String response = getResponse(result);
        String result1 = getFileName(result);
     
     String zero ="0";
     String one ="1";
      try{
    	  if(result1.equalsIgnoreCase(one)){
    		  activation.setText(response);
        	  activation.setTextColor(Color.GREEN);
    		  //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
    		  SharedPreferences.Editor editor2 = response_sp.edit();
    			editor2.putBoolean("isSuccess", true);
    			editor2.commit();
        	  
          }
      else if(result1.equalsIgnoreCase(zero)){
    	  if(isSuccess==false){
    		  activation.setText(response);
    		  activation.setTextColor(Color.RED);
    		  //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    	  }
    	  else{
    		  activation.setText("Wrong Password.But You Are Already Register");
    		  activation.setTextColor(Color.YELLOW);
    		  //Toast.makeText(getApplicationContext(), "Wrong Password.But You Are Already Register", Toast.LENGTH_LONG).show();
    	  }
      }
      }catch(Exception e){
    	  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
      }
      
//      int l = result1.length();
//      String m = String.valueOf(l);
//    	  Toast.makeText(getApplicationContext(),m, Toast.LENGTH_LONG).show();
      
//        intent = new Intent(MainActivity.this,Customdialog.class);
//        intent.putExtra("response", result1);
//        startActivity(intent);
      // Log.v("log","Result :"+result);
  } 
  catch (Exception e)
  {
	  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//   Log.v("log", "Error converting result " + e.toString());
  }


    return;
//	passWord = et2.getText().toString();
//	
//	 // 3. build jsonObject
//	 JSONObject jsonObject = new JSONObject();
//	 try {
//	 jsonObject.put("username", macAddress);
//	 jsonObject.put("password",passWord);
//	 } catch (JSONException e1) {
//	 Toast.makeText(getApplicationContext(), "json object exception", Toast.LENGTH_LONG).show();
//	 e1.printStackTrace();
//	 }
//	
//	
//	 // 4. convert JSONObject to JSON to String
//	 json = jsonObject.toString();
//	
//	 try {
//	 se = new StringEntity(json);
//	 } catch (UnsupportedEncodingException e1) {
//		 Toast.makeText(getApplicationContext(), "String entity exception", Toast.LENGTH_LONG).show();
//	 e1.printStackTrace();
//	 }
//	
//	 // 6. set httpPost Entity
//	 httpPost.setEntity(se);
//	
//	 // 7. Set some headers to inform server about the type of the
//	 //content
//	 httpPost.setHeader("mac", "application/json");
//	 httpPost.setHeader("password", "application/json");
//	
//	 // 8. Execute POST request to the given URL
//	 try {
//	 httpResponse = httpclient.execute(httpPost);
//	 } catch (ClientProtocolException e1) {
//	 // TODO Auto-generated catch block
//	 e1.printStackTrace();
//	 } catch (IOException e1) {
//	 // TODO Auto-generated catch block
//	 e1.printStackTrace();
//	 }
}


	public String getResponse(String newresult) {
		String name1=null;
	       int start,end;
	       start=newresult.lastIndexOf(':');
	       end=newresult.length();;     //lastIndexOf('.');
	       name1=newresult.substring((start+1),end);
	     //  name = "images/"+name;
	       //System.out.println("Start:"+start+"\t\tEnd:"+end+"\t\tName:"+name);
	       return name1;
}


	public String getFileName(String wholePath)
	   {
	       String name2=null;
	       int start,end;
	       start=wholePath.lastIndexOf('/');
	       end=wholePath.lastIndexOf(':');;     //lastIndexOf('.');
	       name2=wholePath.substring((start+1),end);
	     //  name = "images/"+name;
	       //System.out.println("Start:"+start+"\t\tEnd:"+end+"\t\tName:"+name);
	       return name2;
	
}
});
		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		boolean conStatus = false;
		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cn.getActiveNetworkInfo();
		if (nf != null && nf.isConnected() == true) {
			iv.setImageResource(R.drawable.ok);
			// Toast.makeText(this, "Network Available",
			// Toast.LENGTH_LONG).show();

			conStatus = true;
		} else {
			iv.setImageResource(R.drawable.no);
			// Toast.makeText(this, "Network not Available",
			// Toast.LENGTH_LONG).show();
		}
		if(listid==3){
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);
		lv.setAdapter(adapter);}
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("mypref", 0);

		SharedPreferences.Editor editor = sp.edit();

		editor.putString("macid", macAddress);
		editor.commit();
		super.onStop();

	}

	private void resetcolor() {
		// TODO Auto-generated method stub
		tv1.setTextColor(Color.parseColor("#FFFFFF"));
		tv2.setTextColor(Color.parseColor("#FFFFFF"));
		tv3.setTextColor(Color.parseColor("#FFFFFF"));
		tv4.setTextColor(Color.parseColor("#FFFFFF"));
		tv5.setTextColor(Color.parseColor("#FFFFFF"));
		tv6.setTextColor(Color.parseColor("#FFFFFF"));
		tv7.setTextColor(Color.parseColor("#FFFFFF"));
		tv8.setTextColor(Color.parseColor("#FFFFFF"));
		tv9.setTextColor(Color.parseColor("#FFFFFF"));
	}

	
	@Override
	protected Dialog onCreateDialog(int id) {
        switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			
			//mProgressDialog = new ProgressDialog(MainActivity.this);
			//mProgressDialog.setMessage("Downloading file.."+values[appno-1]);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
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

	URL url = new URL(aurl[0]);
	URLConnection conexion = url.openConnection();
	conexion.connect();

	int lenghtOfFile = conexion.getContentLength();
	Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
	
	File wizboxapps = new File("/sdcard/wizboxapps/");
	// have the object build the directory structure, if needed.
	wizboxapps.mkdirs();
	
	if(appno==1){
		Appname="BludoWizBoxAdv.apk";
	}
	else if(appno==2){
		Appname="WizBoxAdv.apk";
	}
	else if(appno==3){
		Appname="WizboxAds.apk";
	}
	
	else if(appno==4){
		Appname="Speedtest.apk";
	}
	else if(appno==5){
		Appname="TeamViewer.apk";
	}
	
	InputStream input = new BufferedInputStream(url.openStream());
	OutputStream output = new FileOutputStream("/sdcard/wizboxapps/"+Appname);

	byte data[] = new byte[1024];

	long total = 0;

		while ((count = input.read(data)) != -1) {
			total += count;
			publishProgress(""+(int)((total*100)/lenghtOfFile));
			output.write(data, 0, count);
		}

		output.flush();
		output.close();
		input.close();
	} catch (Exception e) {}
	return null;

	}
	protected void onProgressUpdate(String... progress) {
		 Log.d("ANDRO_ASYNC",progress[0]);
		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		if(appno==1){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/"+Appname)), "application/vnd.android.package-archive");
	}
		else if(appno==2){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/"+Appname)), "application/vnd.android.package-archive");
		}
		else if(appno==3){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/"+Appname)), "application/vnd.android.package-archive");
		}
		else if(appno==4){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/"+Appname)), "application/vnd.android.package-archive");
		}
		else if(appno==5){
			in.setDataAndType(Uri.fromFile(new File("/sdcard/wizboxapps/"+Appname)), "application/vnd.android.package-archive");
		}
		
		dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		
		
		startActivity(in);
	}
}


}
