package com.wizbox.wizadssettings;







import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Customdialog extends Activity {
	 
//	String msg;
//	ImageView im;
 
    public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        
 
       
      
 
	 final Dialog dialog = new Dialog(Customdialog.this);
     // Include dialog.xml file
     dialog.setContentView(R.layout.customdialog);

     dialog.setTitle(Html.fromHtml("<font color='#FFFFFF' ><big><big>Sorry.....</big></big> </font>"));
     // set values for custom dialog components - text, image and button
     TextView text = (TextView) dialog.findViewById(R.id.textDialog);
     text.setText("Please Register Your Account First");
     dialog.setCancelable(false);
     dialog.show();
     ImageButton declineButton = (ImageButton) dialog.findViewById(R.id.okButton);
     // if decline button is clicked, close the custom dialog
     declineButton.setOnClickListener(new OnClickListener() {
       

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 // Close dialog

          dialog.dismiss();
          Customdialog.this.finish();
		}
     });
 }
// else if(msg.equalsIgnoreCase("Your Account is Already Activated")){
//	// Create custom dialog object
//     final Dialog dialog = new Dialog(Customdialog.this);
//     // Include dialog.xml file
//     dialog.setContentView(R.layout.customdialog);
//     // Set dialog title
//  //   dialog.setTitle("Congratulation!!!");
//     im=(ImageView) dialog.findViewById(R.id.imageDialog);
//     im.setImageResource(R.drawable.already);
//     dialog.setTitle(Html.fromHtml("<font color='#FFFFFF' ><big><big>Already Registered </big></big> </font>"));
//     // set values for custom dialog components - text, image and button
//     TextView text = (TextView) dialog.findViewById(R.id.textDialog);
//     text.setText("Your Account Is Already Activated");
//     dialog.show();  
//     ImageButton declineButton = (ImageButton) dialog.findViewById(R.id.okButton);
//     // if decline button is clicked, close the custom dialog
//     declineButton.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             // Close dialog
//             dialog.dismiss();
//         }
//     });
// }
// else if(msg.equalsIgnoreCase("Password Mismatch. Sorry Try Again")){
	// Create custom dialog object
    // final Dialog dialog = new Dialog(Customdialog.this);
     // Include dialog.xml file
    // dialog.setContentView(R.layout.customdialog);
     // Set dialog title
  //   dialog.setTitle("Congratulation!!!");
//     im=(ImageView) dialog.findViewById(R.id.imageDialog);
//     im.setImageResource(R.drawable.sadface);
//     dialog.setTitle(Html.fromHtml("<font color='#FFFFFF' ><big><big>OOOPS...!!!! </big></big> </font>"));
//     // set values for custom dialog components - text, image and button
//     TextView text = (TextView) dialog.findViewById(R.id.textDialog);
//     text.setText("Password Mismatch. Sorry Try Again");
//     dialog.show();
//     ImageButton declineButton = (ImageButton) dialog.findViewById(R.id.okButton);
//     // if decline button is clicked, close the custom dialog
//     declineButton.setOnClickListener(new OnClickListener() {
//         @Override
//         public void onClick(View v) {
//             // Close dialog
//             dialog.dismiss();
//         }
//     });
                 
            
 
       
 
    }
 
