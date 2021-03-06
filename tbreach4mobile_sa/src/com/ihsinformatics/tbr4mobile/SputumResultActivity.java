/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * 
 */

package com.ihsinformatics.tbr4mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import com.ihsinformatics.tbr4mobile.custom.MyButton;
import com.ihsinformatics.tbr4mobile.custom.MyEditText;
import com.ihsinformatics.tbr4mobile.custom.MyRadioButton;
import com.ihsinformatics.tbr4mobile.custom.MyRadioGroup;
import com.ihsinformatics.tbr4mobile.custom.MySpinner;
import com.ihsinformatics.tbr4mobile.custom.MyTextView;
import com.ihsinformatics.tbr4mobile.shared.AlertType;
import com.ihsinformatics.tbr4mobile.shared.FormType;
import com.ihsinformatics.tbr4mobile.util.RegexUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.INotificationSideChannel;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class SputumResultActivity extends AbstractFragmentActivity
{
	static final int	GET_PATIENT_ID	= 1;
	// Views displayed in pages, sorted w.r.t. appearance on pager
	
	
	MyTextView          submitByTextView;
	MyRadioGroup		submitOption;
	MyRadioButton		patientIdRadioButton;
	MyRadioButton		nhlsIdRadioButton;
	
	MyTextView			patientIdMyTextView;
	MyEditText			patientId;
	MyTextView			testIdMyTextView;
	MyEditText			testId;
	MyButton			scanBarcode;
	
	MyTextView		    firstNameTextView;
	MyTextView		    firstName;
	MyButton			searchPatientButton;
	
	MyTextView			formDateTextView;
	MyButton			formDateButton;
	
	MyTextView			genexpertResultTextView;
	
	MyRadioGroup 		genexpertResultGroup;
	MyRadioButton 		negativeGenexpertResult;
	MyRadioButton 		positiveGenexpertResult;
	MyRadioButton 		leakedGenexpertResult;
	MyRadioButton 		insufficientGenexpertResult;
	MyRadioButton 		unsuccessfulGenexpertResult;
	MyRadioButton 		rejectedGenexpertResult;
	MyRadioButton 		incorrectPaperWorkGenexpertResult;
	
	MyTextView			rifResultTextView;
	
	MyRadioGroup 		rifResultGroup;
	MyRadioButton 		yesDetectedRifResult;
	MyRadioButton 		noDetectedRifResult;
	MyRadioButton 		unknownRifResult;
	
	MyTextView 			sputumResultSpace;
	
	MyButton 			saveButton;
	
	String firstNameValue = "";
	String lastNameValue = "";
	
	private static Toast toast;
	
	View[] invisibleViews;
	View[] visibleViews; 

	/**
	 * Subclass representing Fragment for customer info form
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	@SuppressLint("ValidFragment")
	class CustomerInfoFragment extends Fragment
	{
		int	currentPage;

		@Override
		public void onCreate (Bundle savedInstanceState)
		{
			super.onCreate (savedInstanceState);
			Bundle data = getArguments ();
			currentPage = data.getInt ("current_page", 0);
		}

		@Override
		public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			// Return a layout of views from pre-filled ArrayList of groups
			if (currentPage != 0 && groups.size () != 0)
				return groups.get (currentPage - 1);
			return null;
		}
	}

	/**
	 * Subclass for Pager Adapter. Uses FeedbackFragment subclass as items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class CustomerInfoFragmentPagerAdapter extends FragmentPagerAdapter
	{
		/** Constructor of the class */
		public CustomerInfoFragmentPagerAdapter (FragmentManager fragmentManager)
		{
			super (fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem (int arg0)
		{
			CustomerInfoFragment fragment = new CustomerInfoFragment ();
			Bundle data = new Bundle ();
			data.putInt ("current_page", arg0 + 1);
			fragment.setArguments (data);
			return fragment;
		}

		/** Returns the number of pages */
		@Override
		public int getCount ()
		{
			return PAGE_COUNT;
		}
	}

	@Override
	public void createViews (final Context context)
	{
		
		toast = new Toast(context);
		
		FORM_NAME = "Sputum Result Form";
		TAG = "SputumResultActivity";
		PAGE_COUNT = 3;
		pager = (ViewPager) findViewById (R.template_id.pager);
		navigationSeekbar.setMax (PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById (R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2)
		{
			navigatorLayout.setVisibility (View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager ();
		CustomerInfoFragmentPagerAdapter pagerAdapter = new CustomerInfoFragmentPagerAdapter (fragmentManager);
		pager.setAdapter (pagerAdapter);
		pager.setOffscreenPageLimit (PAGE_COUNT);
		// Create views for pages
		
		submitByTextView = new MyTextView (context, R.style.text, R.string.search_option);
		patientIdRadioButton = new MyRadioButton (context, R.string.patient_id_radio, R.style.radio, R.string.patient_id_radio);
		nhlsIdRadioButton = new MyRadioButton (context, R.string.nhls_id_radio, R.style.radio, R.string.nhls_id_radio);
		submitOption = new MyRadioGroup (context, new MyRadioButton[] {patientIdRadioButton, nhlsIdRadioButton}, R.string.search_option, R.style.radio, App.isLanguageRTL (),0);
	
		patientIdMyTextView = new MyTextView (context, R.style.text, R.string.patient_id);
		patientId = new MyEditText (context, R.string.patient_id, R.string.patient_id_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.idLength, false);
		scanBarcode = new MyButton (context, R.style.button, R.drawable.custom_button_beige, R.string.scan_qr_code, R.string.scan_code);
		
		testIdMyTextView = new MyTextView (context, R.style.text, R.string.test_id);
		testId = new MyEditText (context, R.string.test_id, R.string.lab_test_id_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.labTestIdLength, false);
		
		firstNameTextView = new MyTextView (context, R.style.text, R.string.name);
		firstName = new MyTextView (context, R.style.text, R.string.empty_string);
		firstName.setTextColor(getResources ().getColor (R.color.IRDTitle));
		
		searchPatientButton = new MyButton (context, R.style.button, R.drawable.custom_button_beige, R.string.search_patient, R.string.fetch_name);
		
		formDateTextView = new MyTextView (context, R.style.text, R.string.date_result_recieved);
		formDateButton = new MyButton (context, R.style.button, R.drawable.custom_button_beige, R.string.date_result_recieved, R.string.date_result_recieved);
		
		genexpertResultTextView = new MyTextView (context, R.style.text, R.string.gxp_result);
		
		negativeGenexpertResult = new MyRadioButton(context, R.string.mtb_neg, R.style.radio,
				R.string.mtb_neg);
		positiveGenexpertResult = new MyRadioButton(context, R.string.mtb_pos, R.style.radio,
				R.string.mtb_pos);
		leakedGenexpertResult = new MyRadioButton(context, R.string.leaked, R.style.radio,
				R.string.leaked);
		insufficientGenexpertResult = new MyRadioButton(context, R.string.insufficient_quantity, R.style.radio,
				R.string.insufficient_quantity);
		unsuccessfulGenexpertResult = new MyRadioButton(context, R.string.unsuccessful, R.style.radio,
				R.string.unsuccessful);
		rejectedGenexpertResult = new MyRadioButton(context, R.string.rejected, R.style.radio,
				R.string.rejected); 
		incorrectPaperWorkGenexpertResult = new MyRadioButton(context, R.string.incorrect_paperwork, R.style.radio, R.string.incorrect_paperwork);
		
		genexpertResultGroup = new MyRadioGroup(context,
				new MyRadioButton[] { negativeGenexpertResult, positiveGenexpertResult, leakedGenexpertResult, insufficientGenexpertResult, unsuccessfulGenexpertResult, rejectedGenexpertResult, incorrectPaperWorkGenexpertResult }, R.string.gxp_result,
				R.style.radio, App.isLanguageRTL(),1);
		
		rifResultTextView = new MyTextView (context, R.style.text, R.string.rif_result);
		
		yesDetectedRifResult = new MyRadioButton(context, R.string.yes, R.style.radio,
				R.string.yes);
		
		noDetectedRifResult = new MyRadioButton(context, R.string.no, R.style.radio,
				R.string.no);
		unknownRifResult = new MyRadioButton(context, R.string.unknown, R.style.radio,
				R.string.unknown);
		
		rifResultGroup = new MyRadioGroup(context,
				new MyRadioButton[] { yesDetectedRifResult, noDetectedRifResult, unknownRifResult}, R.string.rif_result,
				R.style.radio, App.isLanguageRTL(),1);
		
		sputumResultSpace = new MyTextView(context, R.style.text,
				R.string.sputumResult_space);
		
		saveButton = new MyButton(context, R.style.button,
				R.drawable.custom_button_beige, R.string.submit_form,
				R.string.submit_form);
		
		View[][] viewGroups = { 
				    { submitByTextView, submitOption, scanBarcode, patientIdMyTextView, patientId, testIdMyTextView, testId,firstNameTextView, firstName, searchPatientButton},
				    {formDateTextView, formDateButton, genexpertResultTextView, genexpertResultGroup},
					{rifResultTextView, rifResultGroup, sputumResultSpace,saveButton },
					};
		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup> ();
		for (int i = 0; i < PAGE_COUNT; i++)
		{
			LinearLayout layout = new LinearLayout (context);
			layout.setOrientation (LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++)
			{
				layout.addView (viewGroups[i][j]);
			}
			ScrollView scrollView = new ScrollView (context);
			scrollView.setLayoutParams (new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			scrollView.addView (layout);
			groups.add (scrollView);
		}
		
		// Set event listeners
		View[] setListener = new View[]{
				formDateButton,firstButton,lastButton,clearButton,saveButton,scanBarcode,searchPatientButton,
				negativeGenexpertResult,positiveGenexpertResult,leakedGenexpertResult,insufficientGenexpertResult,unsuccessfulGenexpertResult,rejectedGenexpertResult,
				patientIdRadioButton,nhlsIdRadioButton,incorrectPaperWorkGenexpertResult};
		
		for (View v : setListener) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			}  else if (v instanceof Button) {
				((Button) v).setOnClickListener(this);
			}  else if (v instanceof RadioButton) {
				((RadioButton) v).setOnClickListener(this);
			}
		}
		
		navigationSeekbar.setOnSeekBarChangeListener (this);
		
		views = new View[] {testId, patientId, patientIdRadioButton,negativeGenexpertResult,noDetectedRifResult};
		
		pager.setOnPageChangeListener (this);
		// Detect RTL language
		if (App.isLanguageRTL ())
		{
			Collections.reverse (groups);
			for (ViewGroup g : groups)
			{
				LinearLayout linearLayout = (LinearLayout) g.getChildAt (0);
				linearLayout.setGravity (Gravity.RIGHT);
			}
			for (View v : views)
			{
				if (v instanceof EditText)
				{
					((EditText) v).setGravity (Gravity.RIGHT);
				}
			}
		}
		
		visibleViews = new View[]{genexpertResultTextView,genexpertResultGroup};
		invisibleViews = new View[]{rifResultTextView,rifResultGroup};
	}

	@Override
	public void initView (View[] views)
	{
		super.initView (views);
		formDate = Calendar.getInstance ();
		updateDisplay ();
		
		testIdMyTextView.setEnabled(false);
		testId.setEnabled(false);
		
		// Hide Views
		for (View v : invisibleViews) {
			v.setVisibility(View.INVISIBLE);
		}
				
		// Show Views
		for (View v : visibleViews) {
			v.setVisibility(View.VISIBLE);
		}
		
		firstNameValue = "";
        lastNameValue = "";
        firstName.setText(firstNameValue+" "+lastNameValue);
		
	}

	@Override
	public void updateDisplay ()
	{
		formDateButton.setText (DateFormat.format ("dd-MMM-yyyy", formDate));	
		
		if(formDate.getTime().after(new Date())){
			
			if (toast != null)
			    toast.cancel();
			
			formDateButton.setTextColor(getResources().getColor(R.color.Red));
			toast = Toast.makeText(SputumResultActivity.this,"Form Date: "+getResources().getString(R.string.invalid_date_or_time), Toast.LENGTH_SHORT);
			toast.show();
		}
		else
			formDateButton.setTextColor(getResources().getColor(R.color.IRDTitle));
	}

	@Override
	public boolean validate ()
	{
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		View[] mandatory = {testId, patientId};
		for (View v : mandatory)
		{
			if(v.isEnabled())
			{	
				if (App.get (v).equals (""))
				{
					valid = false;
					message.append (v.getTag () + ". ");
					((EditText) v).setHintTextColor (getResources ().getColor (R.color.Red));
				}
			}
		}
		
			
		if (!valid)
		{
			message.append (getResources ().getString (R.string.empty_data) + "\n");
		}
		
		if(valid){
			// Validate range
			if (formDate.getTime ().after (new Date ()))
			{
				valid = false;
				message.append (formDateButton.getTag () + ": " + getResources ().getString (R.string.invalid_date_or_time) + "\n");
			}
			
			if(patientId.isEnabled()){
				if (!RegexUtil.isValidId (App.get (patientId)))
				{
					valid = false;
					message.append (patientId.getTag ().toString () + ": " + getResources ().getString (R.string.invalid_data) + "\n");
					patientId.setTextColor (getResources ().getColor (R.color.Red));
				}
				if (!valid)
				{
					App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();
				}
			}
		}
		return valid;
	}
	

	public boolean submit ()
	{
		if (validate ())
		{
		
				final ContentValues values = new ContentValues ();
				values.put ("formDate", App.getSqlDate (formDate));
				values.put ("LabTestId", App.get (testId));
				values.put ("PatientId", App.get (patientId));
				
				final ArrayList<String[]> observations = new ArrayList<String[]> ();
			    observations.add (new String[] {"Date Of Test Report", App.getSqlDate (formDate)});
				if (genexpertResultGroup.getVisibility() == View.VISIBLE)
					observations.add (new String[] {"GeneXpert Result", negativeGenexpertResult.isChecked() ? "MTB Negative" :(positiveGenexpertResult.isChecked() ? "MTB Positive" : (leakedGenexpertResult.isChecked() ? "Leaked" : (insufficientGenexpertResult.isChecked() ? "Insufficient Quantity" : (unsuccessfulGenexpertResult.isChecked() ? "Unsuccessful" : (rejectedGenexpertResult.isChecked() ? "Rejected" : "Incorrect Paperwork")))))});
				if (rifResultGroup.getVisibility() == View.VISIBLE)
					observations.add (new String[] {"RIF Result", yesDetectedRifResult.isChecked() ? "Yes" : (noDetectedRifResult.isChecked() ? "No" : "Unknown")});
				if (testId.isEnabled())
					observations.add (new String[] {"Lab Test Id", App.get (testId)});
				
				observations.add (new String[] {"District", App.getDistrict ()});
				
				if(App.getScreeningType ().equals("Community"))
				{
					String screeningStrategy = App.getScreeningStrategy().substring(6,App.getScreeningStrategy().length());
					observations.add (new String[] {"Screening Strategy", screeningStrategy});
				}	
				else
					observations.add (new String[] {"Facility name", App.getFacility()});
				
				AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String> ()
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
								loading.setMessage (getResources ().getString (R.string.loading_message));
								loading.show ();
							}
						});
						
						String result = serverService.saveSputumResult (FormType.SPUTUM_RESULT, values, observations.toArray (new String[][] {}));
						return result;
					}
	
					@Override
					protected void onProgressUpdate (String... values)
					{
					};
	
					@Override
					protected void onPostExecute (String result)
					{
						super.onPostExecute (result);
						loading.dismiss ();
						if (result.equals ("SUCCESS"))
						{
							App.getAlertDialog (SputumResultActivity.this, AlertType.INFO, getResources ().getString (R.string.inserted)).show ();
							initView (views);
						}
						else
						{
							App.getAlertDialog (SputumResultActivity.this, AlertType.ERROR, result).show ();
						}
					}
				};
				updateTask.execute ("");
			}
	    
		return true;
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult (requestCode, resultCode, data);
		// Retrieve barcode scan results or Search for ID
		if (requestCode == Barcode.BARCODE_RESULT || requestCode == GET_PATIENT_ID)
		{
			if (resultCode == RESULT_OK)
			{
				String str = "";
				if (requestCode == Barcode.BARCODE_RESULT)
					str = data.getStringExtra (Barcode.SCAN_RESULT);
				else if (requestCode == GET_PATIENT_ID)
					str = data.getStringExtra (PatientSearchActivity.SEARCH_RESULT);
				// Check for valid Id
				if(patientIdRadioButton.isChecked())
				{	
					if (RegexUtil.isValidId (str) && !RegexUtil.isNumeric (str, false))
					{
						patientId.setText (str);
					}
					else
					{
						App.getAlertDialog (this, AlertType.ERROR, patientId.getTag ().toString () + ": " + getResources ().getString (R.string.invalid_data)).show ();
					}
				}
				else
				{
					if (str.length() <= 11)
					{
						testId.setText (str);
					}
					else
					{
						App.getAlertDialog (this, AlertType.ERROR, patientId.getTag ().toString () + ": " + getResources ().getString (R.string.invalid_data)).show ();
					}
				}
			}
			else if (resultCode == RESULT_CANCELED)
			{
				// Handle cancel
				App.getAlertDialog (this, AlertType.ERROR, getResources ().getString (R.string.operation_cancelled)).show ();
			}
			// Set the locale again, since the Barcode app restores system's
			// locale because of orientation
			Locale.setDefault (App.getCurrentLocale ());
			Configuration config = new Configuration ();
			config.locale = App.getCurrentLocale ();
			getApplicationContext ().getResources ().updateConfiguration (config, null);
		}
		
	}

	@Override
	public void onClick (View view)
	{
		if (view == formDateButton)
		{
			showDialog (DATE_DIALOG_ID);
		}
		else if (view == searchPatientButton)
		{
			String id = "";
			String type = "";
			StringBuffer message = new StringBuffer ();
			
			if(patientIdRadioButton.isChecked())
			{	
				type = "pid";
			    id  = App.get(patientId);
			    
			    if(id.equals ("")){
			    
						message.append (patientId.getTag () + ": " + getResources ().getString (R.string.empty_data) + "\n");
						App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();	
						return;
			    }
			    else{
			    	if(!RegexUtil.isValidId (id))
			    	{
			    		message.append (patientId.getTag () + ": " + getResources ().getString (R.string.invalid_data) + "\n");
						App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();
						return;
			    	}
			    }
			    
			}
			else
			{	
				type = "tid";
				id = App.get(testId);
				
				if(id.equals ("")){
				    
					message.append (testId.getTag () + ": " + getResources ().getString (R.string.empty_data) + "\n");
					App.getAlertDialog (this, AlertType.ERROR, message.toString ()).show ();	
					return;
				}
			}
			
			searchPatient (id,type);
		}	
		else if (view == firstButton)
		{
			gotoFirstPage ();
		}
		else if (view == lastButton)
		{
			gotoLastPage ();
		}
		else if (view == clearButton)
		{
			AlertDialog confirmationDialog = new AlertDialog.Builder (this).create ();
			confirmationDialog.setTitle (getResources ().getString (R.string.clear_form));
			confirmationDialog.setMessage (getResources ().getString (R.string.clear_close));
			confirmationDialog.setButton (AlertDialog.BUTTON_POSITIVE, getResources ().getString (R.string.yes), new AlertDialog.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
					initView(views);
				}
			});
			confirmationDialog.setButton (AlertDialog.BUTTON_NEGATIVE, getResources ().getString (R.string.cancel), new AlertDialog.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
				}
			});
			confirmationDialog.show ();
		}
		else if (view == saveButton)
		{
			AlertDialog confirmationDialog = new AlertDialog.Builder (this).create ();
			confirmationDialog.setTitle (getResources ().getString (R.string.save_form));
			confirmationDialog.setMessage (getResources ().getString (R.string.save_close));
			confirmationDialog.setButton (AlertDialog.BUTTON_POSITIVE, getResources ().getString (R.string.yes), new AlertDialog.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
					submit();
				}
			});
			confirmationDialog.setButton (AlertDialog.BUTTON_NEGATIVE, getResources ().getString (R.string.cancel), new AlertDialog.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
				}
			});
			confirmationDialog.show ();
		}
		else if (view == patientIdRadioButton)
		{
			patientId.setEnabled(true);
			patientIdMyTextView.setEnabled(true);
			testId.setEnabled(false);
			testIdMyTextView.setEnabled(false);
			patientId.requestFocus();
			testId.clearFocus();
			testId.setText("");
			firstName.setText("");
		}
		else if (view == nhlsIdRadioButton)
		{
			patientId.setEnabled(false);
			patientIdMyTextView.setEnabled(false);
			testId.setEnabled(true);
			testIdMyTextView.setEnabled(true);
			testId.requestFocus();
			patientId.clearFocus();
			patientId.setText("");
			firstName.setText("");
		}
		else if (view == scanBarcode)
		{
			Intent intent = new Intent (Barcode.BARCODE_INTENT);
			intent.putExtra (Barcode.SCAN_MODE, Barcode.QR_MODE);
			startActivityForResult (intent, Barcode.BARCODE_RESULT);
		}
		else if (view == negativeGenexpertResult || view == positiveGenexpertResult || view == leakedGenexpertResult || view == insufficientGenexpertResult || view == unsuccessfulGenexpertResult || view == rejectedGenexpertResult || view == incorrectPaperWorkGenexpertResult ){
			
			boolean check = positiveGenexpertResult.isChecked();
			
			if (check){
				rifResultTextView.setVisibility(View.VISIBLE);
				rifResultGroup.setVisibility(View.VISIBLE);
			}
			else{
				rifResultTextView.setVisibility(View.INVISIBLE);
				rifResultGroup.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onCheckedChanged (CompoundButton button, boolean state)
	{
		
	}

	@Override
	public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
	{
		MySpinner spinner = (MySpinner) parent;
	
	}
	
	public boolean searchPatient (String id, String type)
	{
		final String ids = id;
		final String types = type;
		
	
				AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String> ()
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
									loading.setMessage (getResources ().getString (R.string.loading_message));
									loading.show ();
								}
							});
							//TODO: Uncomment when live
							String[][] result = serverService.getPatientName (ids,types);
							firstNameValue = "";
							lastNameValue = "";
							if(result == null)
							   return "FAIL";
							
							firstNameValue = result[0][1];
							lastNameValue = result[1][1];
							
							return "SUCCESS";
						}
	
						@Override
						protected void onProgressUpdate (String... values)
						{
						};
	
						@Override
						protected void onPostExecute (String result)
						{
							firstName.setText(firstNameValue+" "+lastNameValue);
							
							super.onPostExecute (result);
							loading.dismiss ();
							if (!result.equals ("SUCCESS"))
							{
								Toast toast = Toast.makeText (SputumResultActivity.this, "", App.getDelay ());
								if(patientIdRadioButton.isChecked())
									toast.setText (R.string.patient_id_missing);
								else
									toast.setText (R.string.test_id_missing);
								toast.setGravity (Gravity.CENTER, 0, 0);
								toast.show ();
								return;
							}
						}
					};
				updateTask.execute ("");
				
			
			
		
		return true;
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
