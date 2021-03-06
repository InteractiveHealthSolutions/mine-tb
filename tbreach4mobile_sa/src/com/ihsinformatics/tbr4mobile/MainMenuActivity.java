/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * Main menu Activity
 */

package com.ihsinformatics.tbr4mobile;

import java.util.ArrayList;

import com.ihsinformatics.tbr4mobile.model.OpenMrsObject;
import com.ihsinformatics.tbr4mobile.shared.AlertType;
import com.ihsinformatics.tbr4mobile.util.DatabaseUtil;
import com.ihsinformatics.tbr4mobile.util.ServerService;
import com.ihsinformatics.tbr4mobile.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MainMenuActivity extends Activity implements IActivity, OnClickListener, OnItemSelectedListener
{
	private static final String		TAG					= "MainMenuActivity";
	private static final int		LOCATIONS_DIALOG	= 1;
	private static ServerService	serverService;

	static ProgressDialog			loading;
	TextView						locationTextView;
	Button							screening;
	Button							sputumCollection;
	Button							patientReport;
	Button							sputumResult;
	Button							treatment;
	Button							hivTesting;
	Button							quickScreening;
	Button							screeningReport;
	Button							feedback;
	ImageButton						locationSetup;
	Animation						alphaAnimation;

	String 							screenerLocationSetup[] = null;
	OpenMrsObject[]					locations;
	View[]							views;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		setTheme (App.getTheme ());
		super.onCreate (savedInstanceState);
		setContentView (R.layout.main_menu);
		Configuration config = new Configuration ();
		config.locale = App.getCurrentLocale ();
		getApplicationContext ().getResources ().updateConfiguration (config, null);
		serverService = new ServerService (getApplicationContext ());
		loading = new ProgressDialog (this);

		locationTextView = (TextView) findViewById (R.main_id.locationTextView);
		screening = (Button) findViewById (R.main_id.screeningButton);
		sputumCollection = (Button) findViewById (R.main_id.sputumSubmissionButton);
		patientReport = (Button) findViewById (R.main_id.patientReportButton);
		sputumResult = (Button) findViewById (R.main_id.sputumResultButton);
		treatment = (Button) findViewById (R.main_id.treatmentButton);
		hivTesting = (Button) findViewById (R.main_id.hivTestingButton);
		quickScreening = (Button) findViewById (R.main_id.quickScreeningButton);
		feedback = (Button) findViewById (R.main_id.feedbackButton);
		alphaAnimation = AnimationUtils.loadAnimation (this, R.anim.alpha_animation);
		locationSetup = (ImageButton) findViewById (R.main_id.locationSetupButton);
		screeningReport = (Button) findViewById (R.main_id.screeningReportButton);
		
		// Disable all forms that cannot be filled offline
		if (App.isOfflineMode ())
		{
			sputumCollection.setVisibility(View.GONE);
			patientReport.setVisibility(View.GONE);
			sputumResult.setVisibility(View.GONE);
			treatment.setVisibility(View.GONE);
			screeningReport.setVisibility(View.GONE);
			hivTesting.setVisibility(View.GONE);
		}
		views = new View[] {locationTextView,  screening, sputumCollection, patientReport, sputumResult, treatment, quickScreening, hivTesting,
				 feedback, screeningReport};
		for (View v : views)
		{
			if (v instanceof Spinner)
			{
				((Spinner) v).setOnItemSelectedListener (this);
			}
			else if (v instanceof Button)
			{
				((Button) v).setOnClickListener (this);
			}
		}
		
		locationSetup.setOnClickListener(this);
		initView (views);
		
		
	}

	public void initView (View[] views)
	{

			if(!App.isOfflineMode()){
				
				// Get data from intent if any...
				Intent thisIntent = getIntent();
				if (thisIntent.hasExtra("new_login")){    // main menu is appearing after login
					getScreenerLocationSetup();			// retrieve screeners location and feedback	 
				}
				
			}
		
		String location = "";
				
		String screeningType = App.getScreeningType();    // set locations attributes
		String facility = App.getFacility();
		
		
		if(!facility.equals("") && !screeningType.equals("")){
			facility = facility.substring(6);
			
			if(screeningType.equalsIgnoreCase("Community")){
				location = facility + " (Community)";   // id + screening type
	
				screening.setVisibility(View.VISIBLE);
				quickScreening.setVisibility(View.VISIBLE);
			}
			
			else if (screeningType.equalsIgnoreCase("Facility")){
				location = facility + " (Facility)";   // id + screening type
				quickScreening.setVisibility(View.GONE);
			}
			
			// app version no.  + screener's name
			this.setTitle("   "+getResources ().getString (R.string.version_no) + "                     User: "+App.getScreenerName());
			
			locationTextView.setText (location);
		}
		
		// When online, check if there are offline forms for current user
		if (!App.isOfflineMode ())
		{
			int count = serverService.countSavedForms (App.getUsername ());
			if (count > 0)
			{
				Toast.makeText (this, R.string.offline_forms, App.getDelay ()).show ();;
			}
		}
	}

	public void updateDisplay ()
	{
	}

	public boolean validate ()
	{
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		if (locationTextView.getText ().equals (""))
		{
			valid = false;
			message.append (locationTextView.getTag () + ":" + getResources ().getString (R.string.empty_selection));
		}
		if (!valid)
		{
			App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();
		}
		return valid;
	}

	public boolean submit ()
	{
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId ())
		{
		
			case R.menu_id.locationSetupService :
				Intent locationSetupIntent = new Intent (this, LocationSetupActivity.class);
				startActivity (locationSetupIntent);
				break;
			/*case R.menu_id.searchPatientActivity :
				Intent patientSearchIntent = new Intent (this, PatientSearchActivity.class);
				startActivity (patientSearchIntent);
				break;*/
			/*case R.menu_id.reportsActivity :
				Intent reportsIntent = new Intent (this, ReportsActivity.class);
				startActivity (reportsIntent);
				break;*/
			case R.menu_id.formsActivity :
				Intent formsIntent = new Intent (this, SavedFormsActivity.class);
				startActivity (formsIntent);
				break;
			case R.menu_id.updateMetadataService :
				AlertDialog confirmationDialog = new AlertDialog.Builder (this).create ();
				confirmationDialog.setTitle ("Update Primary data?");
				confirmationDialog.setMessage (getResources ().getString (R.string.update_metadata));
				confirmationDialog.setButton (AlertDialog.BUTTON_POSITIVE, "Yes", new AlertDialog.OnClickListener ()
				{
					@Override
					public void onClick (DialogInterface dialog, int which)
					{
						AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String> ()
						{
							@Override
							protected String doInBackground (String... params)
							{
								try
								{
									if (!serverService.checkInternetConnection ())
									{
										AlertDialog alertDialog = App.getAlertDialog (MainMenuActivity.this, AlertType.ERROR, getResources ().getString (R.string.data_connection_error));
										alertDialog.setTitle (getResources ().getString (R.string.error_title));
										alertDialog.setButton (AlertDialog.BUTTON_POSITIVE, "OK", new AlertDialog.OnClickListener ()
										{
											@Override
											public void onClick (DialogInterface dialog, int which)
											{
												finish ();
											}
										});
										alertDialog.show ();
									}
									else
									{
										// Operations on UI elements can be
										// performed only in UI threads. Damn!
										// WHY?
										runOnUiThread (new Runnable ()
										{
											@Override
											public void run ()
											{
												loading.setIndeterminate (true);
												loading.setCancelable (false);
												loading.show ();
											}
										});
										// Refresh database
										DatabaseUtil util = new DatabaseUtil (MainMenuActivity.this);
										publishProgress (getResources ().getString (R.string.loading_message));
										util.buildDatabase (true);
									}
								}
								catch (Exception e)
								{
									Log.e (TAG, e.getMessage ());
								}
								return "SUCCESS";
							}

							@Override
							protected void onProgressUpdate (String... values)
							{
								loading.setMessage (values[0]);
							};

							@Override
							protected void onPostExecute (String result)
							{
								super.onPostExecute (result);
								if (result.equals ("SUCCESS"))
								{
									loading.dismiss ();
									App.getAlertDialog (MainMenuActivity.this, AlertType.INFO, "Phone data reset successfully.").show ();
									App.setLocation (null);
									locationTextView.setText ("");
									initView (views);
								}
							}
						};
						updateTask.execute ("");
					}
				});
				confirmationDialog.setButton (AlertDialog.BUTTON_NEGATIVE, "Cancel", new AlertDialog.OnClickListener ()
				{
					@Override
					public void onClick (DialogInterface dialog, int which)
					{
						// Do nothing
					}
				});
				confirmationDialog.show ();
				break;
		}
		return true;
	}

	@Override
	protected Dialog onCreateDialog (int id)
	{
		Dialog dialog = super.onCreateDialog (id);
		AlertDialog.Builder builder = new AlertDialog.Builder (this);
		switch (id)
		{
		// Show a list of all locations to choose. This is to limit the
		// locations displayed on site spinner
			case LOCATIONS_DIALOG :
				builder.setTitle(getResources().getString(R.string.multi_select_hint));
				OpenMrsObject[] locationsList = serverService.getLocations ();
				final ArrayList<CharSequence> locations = new ArrayList<CharSequence> ();
				for (OpenMrsObject location : locationsList)
					locations.add (location.getName ());
				final EditText locationText = new EditText (this);
				locationText.setTag ("Location");
				locationText.setHint(R.string.location_hint);
				builder.setView(locationText);
				builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						final String selected = App.get(locationText);
						if (selected.equals("")) {
							Toast toast = Toast.makeText(MainMenuActivity.this, "", App.getDelay());
							toast.setText(R.string.empty_data);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
						// Try to fetch from local DB or Server
						AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
							@Override
							protected String doInBackground(String... params) {
								try {
									if (!serverService.checkInternetConnection()) {
										AlertDialog alertDialog = App.getAlertDialog(MainMenuActivity.this, AlertType.ERROR, getResources().getString(R.string.data_connection_error));
										alertDialog.setTitle(getResources().getString(R.string.error_title));
										alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new AlertDialog.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												finish();
											}
										});
										alertDialog.show();
									} else {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												loading.setIndeterminate(true);
												loading.setCancelable(false);
												loading.show();
											}
										});
										// Update database
										publishProgress("Searching...");
										OpenMrsObject location = serverService.getLocation(selected);
										if (location != null) {
											App.setLocation(location.getName());
											// Save location in preferences
											SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainMenuActivity.this);
											SharedPreferences.Editor editor = preferences.edit();
											editor.putString(Preferences.LOCATION, App.getLocation());
											editor.apply();
										} else {
											return "FAIL";
										}
									}
								} catch (Exception e) {
									Log.e(TAG, e.getMessage());
								}
								return "SUCCESS";
							}

							@Override
							protected void onProgressUpdate(String... values) {
								loading.setMessage(values[0]);
							}

							;

							@Override
							protected void onPostExecute(String result) {
								super.onPostExecute(result);
								if (!result.equals("SUCCESS")) {
									App.getAlertDialog(MainMenuActivity.this, AlertType.ERROR, getResources().getString(R.string.item_not_found)).show();
								}
								loading.dismiss();
								initView(views);
							}
						};
						updateTask.execute("");
					}
				});
				builder.setNegativeButton(R.string.cancel, null);
				dialog = builder.create ();
				break;
		}
		return dialog;
	}

	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed ()
	{
		AlertDialog confirmationDialog = new AlertDialog.Builder (this).create();
		confirmationDialog.setTitle (getResources ().getString (R.string.exit_application));
		confirmationDialog.setMessage (getResources ().getString (R.string.exit_operation));
		confirmationDialog.setButton (AlertDialog.BUTTON_NEGATIVE, getResources ().getString (R.string.exit), new AlertDialog.OnClickListener ()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit ();
				editor.putBoolean (Preferences.AUTO_LOGIN, true);
				editor.apply ();
				finish ();
			}
		});
		confirmationDialog.setButton (AlertDialog.BUTTON_POSITIVE, getResources ().getString (R.string.logout), new AlertDialog.OnClickListener ()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
				
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit ();
				editor.putBoolean (Preferences.AUTO_LOGIN, false);
				editor.apply ();
				App.setAutoLogin(false);
				finish ();
				Intent mainMenuIntent = new Intent (getApplicationContext (), LoginActivity.class);
				startActivity (mainMenuIntent);
			}
		});
		confirmationDialog.setButton (AlertDialog.BUTTON_NEUTRAL, getResources ().getString (R.string.cancel), new AlertDialog.OnClickListener ()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
			}
		});
		confirmationDialog.show ();
	}

	@Override
	protected void onStop ()
	{
		super.onStop();
		finish();
	}

	@Override
	public void onClick (View view)
	{
		int id = view.getId ();
		if(id == R.main_id.locationSetupButton)
		{
			Intent locationSetupIntent = new Intent (this, LocationSetupActivity.class);
			startActivity (locationSetupIntent);
		}
		else{
			// Return if trying to open any form without selecting location
			if (view.getId () != R.main_id.selectLocationsButton && !validate ())
			{
				return;
			}
			Toast toast = Toast.makeText (view.getContext (), "", App.getDelay ());
			toast.setGravity (Gravity.CENTER, 0, 0);
			view.startAnimation (alphaAnimation);
			switch (view.getId ())
			{
			case R.main_id.selectLocationsButton :
				showDialog (LOCATIONS_DIALOG);
				break;
			case R.main_id.screeningButton :
				Intent screeningIntent = new Intent (this, ScreeningActivity.class);
				startActivity (screeningIntent);
				break;
			case R.main_id.sputumSubmissionButton :
				Intent sputumSubmissionIntent = new Intent (this, SputumSubmissionActivity.class);
				startActivity (sputumSubmissionIntent);
				break;
			case R.main_id.patientReportButton :
				Intent patientReportIntent = new Intent (this, PatientReportActivity.class);
				startActivity (patientReportIntent);
				break;
			case R.main_id.sputumResultButton :
				Intent sputumResultIntent = new Intent (this, SputumResultActivity.class);
				startActivity (sputumResultIntent);
				break;	
			case R.main_id.treatmentButton :
				Intent treatmentIntent = new Intent (this, TreatmentActivity.class);
				startActivity (treatmentIntent);
				break;
			case R.main_id.quickScreeningButton :
				Intent quickScreeningIntent = new Intent (this, QuickScreeningActivity.class);
				startActivity (quickScreeningIntent);
				break;	
			case R.main_id.feedbackButton :
				Intent feedbackIntent = new Intent (this, FeedbackActivity.class);
				startActivity (feedbackIntent);
				break;
			case R.main_id.screeningReportButton :
				Intent screeningReportIntent = new Intent (this, ScreeningReportActivity.class);
				startActivity (screeningReportIntent);
				break;
			case R.main_id.hivTestingButton :
				Intent hivTestingIntent = new Intent (this, HIVTestingActivity.class);
				startActivity (hivTestingIntent);
				break;
			default :
				toast.setText (getResources ().getString (R.string.form_unavailable));
				toast.show ();
			}
		}
	}

	@Override
	public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
	{
		// Not implemented
	}

	@Override
	public void onNothingSelected (AdapterView<?> arg0)
	{
		// Not implemented
	}
	
	
	/**
	 * 
	 * Gets & display screener location and feedback from the server.
	 * 
	 */
	public void getScreenerLocationSetup(){
		// Authenticate from server
		AsyncTask<String, String, String> loadXmls = new AsyncTask<String, String, String> ()
		{
			@Override
			protected String doInBackground (String... params)
			{
				runOnUiThread (new Runnable ()
				{
					@Override
					public void run ()
					{
						loading.setIndeterminate (true);
						loading.setCancelable (false);
						loading.show ();
					}
				});
					
				publishProgress (getResources ().getString (R.string.fetching_locations));
				screenerLocationSetup = serverService.fetchScreenerLocationAndPerformanceFeedback (App.getUsername());
				
				if(screenerLocationSetup == null)
					   return "FAIL";		
				  return "SUCCESS";
			}

			@Override
			protected void onProgressUpdate (String... values)
			{
				loading.setMessage (values[0]);
			};

			@Override
			protected void onPostExecute (String result)
			{
				super.onPostExecute (result);
				loading.dismiss ();
						
				if(result.equals("SUCCESS")){
					
					if(screenerLocationSetup != null){
						if(!screenerLocationSetup[0].equals("") && !screenerLocationSetup[1].equals(""))
						{
							App.setLocation(screenerLocationSetup[0]);
							App.setFacility(screenerLocationSetup[0]);
							App.setScreeningType(screenerLocationSetup[1]);
							App.setScreeningStrategy("Community (General)");  
							
							// Save locations in preferences
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (MainMenuActivity.this);
							SharedPreferences.Editor editor = preferences.edit ();
							editor.putString (Preferences.FACILITY, App.getFacility ());
							editor.putString (Preferences.LOCATION, App.getLocation ());
							editor.putString (Preferences.SCREENING_TYPE, App.getScreeningType());
							editor.putString (Preferences.SCREENING_STRATEGY, App.getScreeningStrategy());
							editor.apply ();
							
							String location = "";
							
							String screeningType = App.getScreeningType();
							String facility = App.getFacility();
							
							// Display locations in Location Text View
							facility = facility.substring(6);
							if(screeningType.equalsIgnoreCase("Community")){
								location = facility + " (Community)";
	
								screening.setVisibility(View.VISIBLE);
								quickScreening.setVisibility(View.VISIBLE);
							}
							
							else if (screeningType.equalsIgnoreCase("Facility")){
								location = facility + " (Facility)";
								quickScreening.setVisibility(View.GONE);
							}
							
							locationTextView.setText (location);
						}
					}

					// Display the feedback if any
					if(!screenerLocationSetup[2].equals("") ){
						
						int screenedToday = Integer.parseInt(screenerLocationSetup[3]);
						int sputumSubmittedToday = Integer.parseInt(screenerLocationSetup[4]);
						int percentage = Integer.parseInt(screenerLocationSetup[2]);
						String date = screenerLocationSetup[5];
				        
						if(!(screenedToday == 0 && sputumSubmittedToday == 0)){
						
							String[][] messageArray = new String[3][3];
					        String message = "";
							boolean flag = false;
							int count = 0;
					        
							// Messages...
							
							if(screenedToday > 50){
								message = "\nWell done! You screened over 50 people yesterday!\n";
								messageArray[0][0] = message;
								messageArray[0][1] = "+";
								count++;
							}else if (screenedToday < 20){
								message = "\nYou need to screen more. You screened fewer than 20 people yesterday.\n";
								messageArray[0][0] = message;
								messageArray[0][1] = "-";
								
							}else{
								messageArray[0][0] = "";
								messageArray[0][1] = "";
							}
							
							if(sputumSubmittedToday > 10){
								message = "\nAwesome! You collected more than 10 sputum samples yesterday! :-)\n";
								messageArray[1][0] = message;
								messageArray[1][1] = "+";
								count++;
							}else if (sputumSubmittedToday < 4){
								message = "\nTry harder- you collected fewer than 4 sputum samples yesterday.\n";
								messageArray[1][0] = message;
								messageArray[1][1] = "-";
							}else{
								messageArray[1][0] = "";
								messageArray[1][1] = "";
							}
							
							if(percentage >= 80){
								message = "\nGreat job! Yesterday you got sputum samples from at least 80% of the suspects you screened! :-)\n";
								messageArray[2][0] = message;
								messageArray[2][1] = "+";
								count++;
							}else if (percentage <= 50){
								message =  "\nEish! You got sputum samples from less than 50% of suspects yesterday. Your target is 100%.\n";
								messageArray[2][0] = message;
								messageArray[2][1] = "-";
							}else{
								messageArray[2][0] = "";
								messageArray[2][1] = "";
							}
							
							message = "";
							
							for(int i = 0; i<3; i++){
								
								if(messageArray[i][1].equals("+"))
									message = message + messageArray[i][0];
									
							}
							
							for(int j = 0; j<3; j++){
								
								if(messageArray[j][1].equals("-"))
									message = message + messageArray[j][0];
									
							}
							
							if(count > 1)
								flag = true;
							
							if(!message.equals("")){
								showFeedbackMessageAlert(message, flag, date);
							}
							
						}
					}
				}
				
			}
		};
		loadXmls.execute ("");
	}

	
	/**
	 * 
	 * Displays message in alert dialog
	 * 
	 * @param messageYestarday
	 */

	public void showFeedbackMessageAlert(String message, Boolean status, String date){
			
		// Create custom dialog object
        final Dialog dialog = new Dialog(MainMenuActivity.this);
        
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));    
        dialog.show(); 
        
     // set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.textView);
		text.setText(message);
		
		TextView d = (TextView) dialog.findViewById(R.id.date);
		d.setText("Feedback for date: \n"+ date);
		
		TextView close = (TextView) dialog.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
	    animation.setDuration(500); // duration - half a second
	    animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
	    animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
	    animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
	    ImageView image = (ImageView) dialog.findViewById(R.id.imageView);
	    image.startAnimation(animation);

	    if(status)
	    	image.setVisibility(View.VISIBLE);
	    else
	    	image.setVisibility(View.GONE);

	}
}
