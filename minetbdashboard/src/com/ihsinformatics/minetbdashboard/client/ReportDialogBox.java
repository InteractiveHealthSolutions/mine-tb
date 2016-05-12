/**
 * Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
 * You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html
 * Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 * Contributors: Rabbia
 */

package com.ihsinformatics.minetbdashboard.client;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.DataType;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.GraphData;
import com.ihsinformatics.minetbdashboard.shared.Parameter;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;

/**
 * 
 * Dialog Box for Reports 
 * @author Rabbia
 *
 */


class ReportDialogBox extends WindowBox {
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);

	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel headerPanel = new HorizontalPanel();
	VerticalPanel chartPanel = new VerticalPanel();
	TabPanel tabPanel = new TabPanel();
	
	Label subheadingLabel = new Label();
	
	String report;
	String time;
	String loc;
	String yearFrom;
	String yearTo;
	String to;
	String from;
	String reportType;
	
    public ReportDialogBox(String reportType, String report, String time, String loc, String yearFrom, String yearTo, String from, String to) {
    	
		this.report = report;
		this.time = time;
		this.loc = loc;
		this.yearFrom = yearFrom;
		this.yearTo = yearTo;
		this.to = to;
		this.from = from;
		this.reportType = reportType;
    	
       setText(report); // Title
       
       int width = Window.getClientWidth() - 20;
       setWidth(width+"px");
       mainPanel.setSize("100%", "100%");
       chartPanel.setSize("100%", "100%");
       
       setModal(false);				//parent screen remain active
       setAnimationEnabled(true);
       setGlassEnabled(false);
       
       setPopupPosition(0, 150);

       mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
       
       Button ok = new Button("X");
       ok.setStyleName("closeButton");
       ok.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 ReportDialogBox.this.hide();    //close
		}
       });
       
       Button min = new Button("-");
       min.setStyleName("closeButton");
       min.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 ReportDialogBox.this.setPopupPosition(0, 700);   //minimize
		}
       });

       Button max = new Button("O");
       max.setStyleName("closeButton");
       max.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			setPopupPosition(0, 150);       //maximize
		}
       });
       
       
       Grid buttonPanel = new Grid(1,3);
       buttonPanel.setWidget(0, 0, min);
       buttonPanel.setWidget(0, 1, max);
       buttonPanel.setWidget(0, 2, ok);
       buttonPanel.getElement().setAttribute("align", "right");
       
       String timeline = "";
       
       if(time.equalsIgnoreCase("year")){
    	   timeline = " (" + yearFrom + " - " + yearTo +")";
       } else if (time.equals("quarter")){
    	   timeline = " (Q" + from + " -  Q" + to + " for year " + yearFrom + ")";
       } else if (time.equals("month")){
    	   timeline = " (M" + from + " -  M" + to + " for year " + yearFrom + ")";
       } else if (time.equals("week")){
    	   timeline = " (W" + from + " -  W" + to + " for year " + yearFrom + ")";
       }
       
       String labelText = " by "
				+ Character.toUpperCase(loc.charAt(0)) + loc.substring(1)
				+ " per " + Character.toUpperCase(time.charAt(0))
				+ time.substring(1);
       
       subheadingLabel.setText(labelText + timeline);       // subheading
       subheadingLabel.setStyleName("bold-green-text");
       subheadingLabel.getElement().setAttribute("align", "left");
       
       headerPanel.add(subheadingLabel);
       headerPanel.add(buttonPanel);
       headerPanel.setSize("100%", "100%");
       
       mainPanel.add(headerPanel);
       
       fillChartPanel();
       
       ScrollPanel scrollPanel = new ScrollPanel();
       scrollPanel.setSize("100", "100");
       scrollPanel.add(chartPanel);
       
       mainPanel.add(scrollPanel);
       
       setWidget(mainPanel);
       
    }
    
    /**
     * Creates and Display Charts 
     * a/c to selections
     */
    private void fillChartPanel(){
    
    	StringBuilder query = getQuery();
    	drawCharts(query);
    	
    }
    
    /**
     * Returns query a/c to selections
     * @return Query -> String Builder
     */
    private StringBuilder getQuery(){
    	
    	StringBuilder query = new StringBuilder();
    	Parameter[] params = null;
    	
    	if(report.equalsIgnoreCase("screening")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(screened) as screened, sum(suspects) as suspects, sum(non_suspects) as non_suspects from fact_screening ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("sputum submission")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(total_submissions) as total, sum(accepted_submissions) as accepted, sum(rejected_submissions) as rejected from fact_sputumresults ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("GeneXpert: MTB Positive and Rif Resistants")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(total_results) as total, sum(mtb_positives) as positive, sum(rif_resistants) as rif from fact_sputumresults ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("GeneXpert: Other Results")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(total_results) as total, sum(mtb_positives) as positive, sum(mtb_negatives) as negative, sum(unsuccessful) as successful, sum(leaked) as leaked, sum(insufficient_quantity) as insufficient_quantity, sum(incorrect_paperwork) as incorrect_paperwork , sum(rejected) as rejected, sum(errors) as error, sum(invalid) as invalid, sum(no_results) as no_result, sum(others) as other  from fact_sputumresults ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("Treatment Initiated")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(tx_initiated) as tx_initiated, sum(tx_initiated_at_clinic) as initiated_at_clinic, sum(tx_initiated_tranferred) as initiated_transferred_out, sum(tx_not_initiated) as tx_not_initiated, sum(patient_refused_tx) as patient_refused, sum(patient_not_found) as not_found, sum(contact_info_missing) as info_missing, sum(patient_died) as died, sum(already_on_tx) as already_on_tx  from fact_treatment ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("Treatment Outcome Results")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(patient_cured) as cure, sum(tx_completed) as completed, sum(tx_default) as default_outcome, sum(tx_failure) as failure, sum(patient_death) as death, sum(patient_transferred_out) as transferred_out from fact_treatment ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("Screening Summary")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(screened) as screened, sum(suspects) as suspects, sum(non_suspects) as non_suspects from fact_screening ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	else if(report.equalsIgnoreCase("Sputum Submission Rates")){
    		query.append("select screening_data." + time + ", ");
    		query.append(" screening_data." + loc + ", ");
    		query.append(" screening_data.suspects, coalesce(((sputum_data.total_submissions/screening_data.suspects)*100),0)  from");
    		query.append(" (select s.year as y, s." + time + ", s." + loc + ", sum(suspects) as suspects from fact_screening s ");
    		query.append(getFilter(params, "s"));
    		query.append(" group by s." + time + ", s." + loc);
    		query.append(" order by s." + time + ", s." + loc + ") screening_data");
    		query.append(" join");
    		query.append(" (select s.year as y, s." + time + ", s." + loc + ", sum(total_submissions) as total_submissions from fact_sputumresults s ");
    		query.append(getFilter(params, "s"));
    		query.append(" group by s." + time + ", s." + loc);
    		query.append(" order by s." + time + ", s." + loc + ") sputum_data");
    		query.append(" on");
    		query.append(" screening_data." + time + " = sputum_data." + time + " and screening_data.y = sputum_data.y and screening_data." + loc + " = sputum_data." + loc + ";");
    	}
    	else if(report.equalsIgnoreCase("Treatment Initiation Rates")){
    		query.append("select sputum_data." + time + ", ");
    		query.append(" sputum_data." + loc + ", ");
    		query.append(" sputum_data.mtb_positives, coalesce(((treatment_data.tx_initiated/sputum_data.mtb_positives)*100),0)  from");
    		query.append(" (select s.year as y, s." + time + ", s." + loc + ", sum(mtb_positives) as mtb_positives from fact_sputumresults s ");
    		query.append(getFilter(params, "s"));
    		query.append(" group by s." + time + ", s." + loc);
    		query.append(" order by s." + time + ", s." + loc + ") sputum_data");
    		query.append(" join");
    		query.append(" (select s.year as y, s." + time + ", s." + loc + ", sum(tx_initiated) as tx_initiated from fact_treatment s ");
    		query.append(getFilter(params, "s"));
    		query.append(" group by s." + time + ", s." + loc);
    		query.append(" order by s." + time + ", s." + loc + ") treatment_data");
    		query.append(" on");
    		query.append(" treatment_data." + time + " = sputum_data." + time + " and treatment_data.y = sputum_data.y and treatment_data." + loc + " = sputum_data." + loc + ";");
    	}
    	else if(report.equalsIgnoreCase("Sputum Submission & Error Rates")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(total_submissions) as total_submissions, sum(leaked) as leaked, sum(no_results) as no_results, (sum(errors)+sum(invalid)+sum(unsuccessful)+sum(insufficient_quantity)+sum(others)+sum(incorrect_paperwork)+sum(rejected)) from fact_sputumresults ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	
    	return query;
    }
    
    /**
     * Get and manage data from Query Result
     * @param query
     */
    private void drawCharts(StringBuilder query){
    	
    	try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {
						@Override
						public void onSuccess(final String[][] result) {
							
							String[] timeArray = getTimeArray();    // Get all time values
							String xLabel = Character.toUpperCase(time.charAt(0)) + time.substring(1);
							String[] locations = getUniqueValues(result, 1);    // Get all unique locations involved 
							String[] arrayT = timeArray;

							// Converts month# to month name
							if (time.equals("month")) {
								for (int i = 0; i < timeArray.length; i++) {
									String monthString;
									switch (Integer.parseInt(timeArray[i])) {
									case 1:
										monthString = "Jan";
										break;
									case 2:
										monthString = "Feb";
										break;
									case 3:
										monthString = "Mar";
										break;
									case 4:
										monthString = "Apr";
										break;
									case 5:
										monthString = "May";
										break;
									case 6:
										monthString = "Jun";
										break;
									case 7:
										monthString = "Jul";
										break;
									case 8:
										monthString = "Aug";
										break;
									case 9:
										monthString = "Sep";
										break;
									case 10:
										monthString = "Oct";
										break;
									case 11:
										monthString = "Nov";
										break;
									case 12:
										monthString = "Dec";
										break;
									default:
										monthString = "Invalid month";
										break;
									}
									arrayT[i] = monthString;
								}
							}
							
							String yearString = "";

							// Year String according to time dimension selected
							if (time.equalsIgnoreCase("year"))
									yearString = " (" + yearFrom + " - " + yearTo + ")";
							else
								yearString = " (" + yearFrom + ")";
							
							timeArray = getTimeArray();

							// Draw specific chart
							if(report.equalsIgnoreCase("screening"))
								drawScreening(result, locations, timeArray, arrayT, xLabel, yearString);
							else if (report.equalsIgnoreCase("Sputum Submission"))
								drawSputumSubmission(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("GeneXpert: MTB Positive and Rif Resistants"))
								drawPositiveResults(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("GeneXpert: Other Results"))
								drawOtherResults(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Treatment Initiated"))
								drawTreatmentInitiation(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Treatment Outcome Results"))
								drawTreatmentOutcome(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Screening Summary"))
								drawScreeningSummary(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Sputum Submission Rates"))
								drawSputumSubmissionRate(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Treatment Initiation Rates"))
								drawTreatmentInitiationRate(result, locations, timeArray, arrayT, xLabel, yearString);
							else if(report.equalsIgnoreCase("Sputum Submission & Error Rates"))
								drawSputumSubmissionAndErrorRate(result, locations, timeArray, arrayT, xLabel, yearString);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(CustomMessage
									.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Draw Screening Charts (#Screened, #Suspects, #Non-Suspects) 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawScreening(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	chartPanel.add(tabPanel);
		tabPanel.setSize("100%", "100%");
    	
    	// Add Total Screened Chart
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "Screened";
    	ReportPanel screenedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Screened", yearString);
    	tabPanel.add(screenedPanel.getComposite(),yLabel);
    	
		dataList.clear();
		
		// Add Presumptive and High Risk Chart
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,3);
    	yLabel = "Presumptive and High Risk";
    	ReportPanel suspectPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Presumptives and High Risks",yearString);
    	tabPanel.add(suspectPanel.getComposite(),yLabel); 	
    	
    	dataList.clear();
    	
    	// Add Non-Suspects
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "Non-Suspects";
    	ReportPanel nonSuspectPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Non-Suspects",yearString);
    	tabPanel.add(nonSuspectPanel.getComposite(),yLabel); 
    	
    	tabPanel.selectTab(0);

    }
    
    /**
     * Draw Sputum Submission (#Sputum Submissions) 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawSputumSubmission(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	chartPanel.add(tabPanel);
		tabPanel.setSize("100%", "100%");
    	
    	// Add Total Screened Chart
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "Sputum Submissions";
    	ReportPanel sputumSubmissionPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Sputum Submissions", yearString);
    	tabPanel.add(sputumSubmissionPanel.getComposite(), yLabel);
    	
    	tabPanel.selectTab(0);
    	
    }
    
    /**
     * Draw MTB Positive Results (#Total Results, #All Cases Detected, #All Rif Resistant) 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawPositiveResults(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	chartPanel.add(tabPanel);
		tabPanel.setSize("100%", "100%");
    	
    	// Add Total Result Received
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "All GeneXpert Results";
    	ReportPanel allResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Number of Results Received", yearString);
    	tabPanel.add(allResultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add All Cases Detected
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,3);
    	yLabel = "Cases Detected";
    	ReportPanel positiveResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "All Cases Detected", yearString);  	
    	tabPanel.add(positiveResultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Rif Resistant Result 
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "RIF Resistant Cases";
    	ReportPanel rifResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "RIF Resistant Cases Detected", yearString);
    	tabPanel.add(rifResultPanel.getComposite(), yLabel);
    	
    	tabPanel.selectTab(0);
    	
    }
    
    /**
     * Draw Other Results (#Total Results, #All Cases Detected, #MTB Negative, #Unsuccessful Results, #Leaked Results,
     *                     #Insufficient Quantity, #Rejected Results, #Error Results, #Invalid Result, #No Result, #Other Result) 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
   public void drawOtherResults(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    
	   	chartPanel.add(tabPanel);
	   	tabPanel.setSize("100%", "100%");
	   	
    	// Add Total Result Received
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "All GeneXpert Results";
    	ReportPanel allResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Number of Results Received", yearString);
    	tabPanel.add(allResultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add All Cases Detected
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,3);
    	yLabel = "Cases Detected";
    	ReportPanel positiveResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "All Cases Detected", yearString);
    	tabPanel.add(positiveResultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add MTB Negative
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "MTB Negative";
    	ReportPanel mtbNegativePanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "MTB Negative", yearString);
    	tabPanel.add(mtbNegativePanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Unsuccessful Results
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,5);
    	yLabel = "Unsuccessful";
    	ReportPanel unsuccessfulPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Unsuccessful", yearString);
    	tabPanel.add(unsuccessfulPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Leaked Results
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,6);
    	yLabel = "Leaked";
    	ReportPanel LeakedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Leaked", yearString);
    	tabPanel.add(LeakedPanel.getComposite(), yLabel);
    	
    	// Add Insufficient Quantity
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,7);
    	yLabel = "Insufficient Quantity";
    	ReportPanel insufficientQuantityPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Insufficient Quantity", yearString);
    	tabPanel.add(insufficientQuantityPanel.getComposite(), yLabel);
    	
    	tabPanel.selectTab(0);
    	
    	TabPanel secondTabPanel = new TabPanel();
    	chartPanel.add(secondTabPanel);
    	secondTabPanel.setSize("100%", "100%");
    	
    	// Add Insufficient Quantity
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,8);
    	yLabel = "Incorrect Paperwork";
    	ReportPanel incorrectPaperworkPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Incorrect Paperwork", yearString);
    	secondTabPanel.add(incorrectPaperworkPanel.getComposite(), yLabel);
    	
    	// Add Rejected Results
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,9);
    	yLabel = "Rejected";
    	ReportPanel rejectedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Rejected", yearString);
    	secondTabPanel.add(rejectedPanel.getComposite(), yLabel);
    	
    	// Add Errored Results
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,10);
    	yLabel = "Error";
    	ReportPanel errorPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Error", yearString);
    	secondTabPanel.add(errorPanel.getComposite(), yLabel);
    	
    	// Add Invalid Results
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,11);
    	yLabel = "Invalid";
    	ReportPanel invalidPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Invalid", yearString);
    	secondTabPanel.add(invalidPanel.getComposite(), yLabel);
    	
    	// Add No Result
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,12);
    	yLabel = "Invalid";
    	ReportPanel noResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Invalid", yearString);
    	secondTabPanel.add(noResultPanel.getComposite(), yLabel);
    	
    	// Add Other Result
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,13);
    	yLabel = "Others";
    	ReportPanel otherResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "GeneXpert Results", "Others", yearString);
    	secondTabPanel.add(otherResultPanel.getComposite(), yLabel);
    	
    	secondTabPanel.selectTab(0);
    }
    
   /**
    * Draw Other Results (#Total Treatment Initiated, #Treatment Transferred Out, #Treatment Not Initiated, #Patient Refused,
    * 						#Patients Not Found, #Missing Contact Info, #Died, #Already on Treatment) 
    * 
    * @param result
    * @param locations
    * @param timeArray
    * @param arrayT
    * @param xLabel
    * @param yearString
    */
    public void drawTreatmentInitiation(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	chartPanel.add(tabPanel);
    	tabPanel.setSize("100%", "100%");
    	
    	// Add Total Treatment Initiated
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "Treatments Initiated";
    	ReportPanel treatmentInitiatedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Total Treatments Initiated", yearString);
    	tabPanel.add(treatmentInitiatedPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Treatment Transferred Out
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "Transferred Out";
    	ReportPanel transferredOutPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Treatment Transferred Out", yearString);
    	tabPanel.add(transferredOutPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Treatment Not Initiated
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,5);
    	yLabel = "Not Initiated";
    	ReportPanel notInitiatedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Total Treatment Not Initiated", yearString);
    	tabPanel.add(notInitiatedPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Patient Refused Treatment
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,6);
    	yLabel = "Treatment Refused";
    	ReportPanel rifResultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Patient Refused Treatment", yearString);
    	tabPanel.add(rifResultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Couldn't Found Patient from Home Visit
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,7);
    	yLabel = "Patient Not Found";
    	ReportPanel patientNotFoundPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Couldn't Found Patient from Home Visit", yearString);
    	tabPanel.add(patientNotFoundPanel.getComposite(), yLabel);
    	
    	// Add Missing Contact Info
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,8);
    	yLabel = "Missing Information";
    	ReportPanel missingContactInfoPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Clinic Didn't Have Address or Phone Number", yearString);
    	tabPanel.add(missingContactInfoPanel.getComposite(), yLabel);
    	
    	// Add Died
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,9);
    	yLabel = "Patient Died";
    	ReportPanel patientDiedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Patient Died", yearString);
    	tabPanel.add(patientDiedPanel.getComposite(), yLabel);
    	
    	// Add Already on Treatment
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,10);
    	yLabel = "Already on Treatment";
    	ReportPanel alreadyOnTreatmentPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Patient Already on Treatment", yearString);
    	tabPanel.add(alreadyOnTreatmentPanel.getComposite(), yLabel);
    	
    	tabPanel.selectTab(0);
    	
    }
    
    /**
     * Draw Other Results (#Total Cured, #Treatment Completed, #Default, #Treatment Failure, #Patient Died, #Transferred Out) 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawTreatmentOutcome(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	chartPanel.add(tabPanel);
		tabPanel.setSize("100%", "100%");
    	
    	// Add Cured
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "Cured";
    	ReportPanel curedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Cured", yearString);
    	tabPanel.add(curedPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Treatment Completed
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,3);
    	yLabel = "Treatment Completed";
    	ReportPanel treatmentCompletedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Treatment Completed", yearString);
    	tabPanel.add(treatmentCompletedPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Default
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "Default";
    	ReportPanel defaultPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Default", yearString);
    	tabPanel.add(defaultPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Treatment Failure
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,5);
    	yLabel = "Treatment Failure";
    	ReportPanel treatmentFailurePanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Treatment Failure", yearString);
    	tabPanel.add(treatmentFailurePanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Patient Died
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,6);
    	yLabel = "Patient Death";
    	ReportPanel patientDeathPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Patient Death", yearString);
    	tabPanel.add(patientDeathPanel.getComposite(), yLabel);
    	
    	dataList.clear();
    	
    	// Add Transferred Out
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,7);
    	yLabel = "Transferred Out";
    	ReportPanel transferredOutPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, "Number of Patients", "Transferred Out", yearString);
    	tabPanel.add(transferredOutPanel.getComposite(), yLabel);
    	
    	tabPanel.selectTab(0);
    	
    }
    
    /**
     * Draw Screening Summary for every unique location
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawScreeningSummary(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	for (int i = 0; i < locations.length; i++) {
    
    		TabPanel tabPanel = new TabPanel();
    		chartPanel.add(tabPanel);
    		tabPanel.setSize("100%", "100%");
    		
    		for(int j = 0; j < 5 && i < locations.length; j++){
    			String loc = locations[i];
    			i++;
				if(!loc.equals("")){
					Double[] primaryData = getColumnData(result,timeArray, loc, 3);
					Double[] secondaryData = getColumnData(result,timeArray, loc, 4);
					Double[] cumalativeData = getCumulativeData(result, timeArray, loc, 2);
					
					GraphData primaryGraphData = new GraphData("Suspect", primaryData);
					GraphData secondaryGraphData = new GraphData("Non-Suspect", secondaryData);
					GraphData cumalativeGraphData = new GraphData("Cumlative Screened", cumalativeData);
					ArrayList<GraphData> dataList = new ArrayList<GraphData>();
					dataList.add(primaryGraphData);
					dataList.add(secondaryGraphData);
					dataList.add(cumalativeGraphData);
					
					String title = "Screening Summary";
					
			    	ReportPanel screeningSummaryPanel = new ReportPanel(report, dataList, arrayT, time, "Number Screened", title + " " +yearString, loc);
					
					tabPanel.add(screeningSummaryPanel.getComposite(), loc);
					
				}
    		}
    		i--;
    		tabPanel.selectTab(0);	
    	}
    	
    }
    
    /**
     * Draw Sputum Submission Rate for every unique location
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawSputumSubmissionRate(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
        
    	for (int i = 0; i < locations.length; i++) {
    	    
    		TabPanel tabPanel = new TabPanel();
    		chartPanel.add(tabPanel);
    		tabPanel.setSize("100%", "100%");
    		
    		for(int j = 0; j < 5 && i < locations.length; j++){
    			String loc = locations[i];
    			i++;
				if(!loc.equals("")){
					
					Number[] primaryData = getColumnData(result,timeArray, loc, 2);
					GraphData yAxisPrimaryData = new GraphData("Number of Suspects", primaryData);

					Number[] secondaryData = getColumnData(result,timeArray, loc, 3);
					GraphData yAxisSecondaryData = new GraphData("Sputum Submission Rate", secondaryData);
					
					ArrayList<GraphData> dataList = new ArrayList<GraphData>();
					dataList.add(yAxisPrimaryData);
					dataList.add(yAxisSecondaryData);
					
					String title = "Sputum Submission Rate";
					
			    	ReportPanel sputumSubmissionRatePanel = new ReportPanel(report, dataList, arrayT, time, "Sputum Submission Rate", title + " " +yearString, loc);
					
					tabPanel.add(sputumSubmissionRatePanel.getComposite(), loc);
					
				}
    		}
    		i--;
    		tabPanel.selectTab(0);	
    	}
    	
    }
    
    /**
     * Draw Treatment Initiation Rate for every unique location
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    public void drawTreatmentInitiationRate(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    
    	for (int i = 0; i < locations.length; i++) {
    	    
    		TabPanel tabPanel = new TabPanel();
    		chartPanel.add(tabPanel);
    		tabPanel.setSize("100%", "100%");
    		
    		for(int j = 0; j < 5 && i < locations.length; j++){
    			String loc = locations[i];
    			i++;
				if(!loc.equals("")){
					
					Number[] primaryData = getColumnData(result,timeArray, loc, 2);
					GraphData yAxisPrimaryData = new GraphData("Number of MTB Cases", primaryData);

					Number[] secondaryData = getColumnData(result,timeArray, loc, 3);
					GraphData yAxisSecondaryData = new GraphData("Treatment Initiation Rate", secondaryData);
					
					ArrayList<GraphData> dataList = new ArrayList<GraphData>();
					dataList.add(yAxisPrimaryData);
					dataList.add(yAxisSecondaryData);
					
					String title = "Treatment Initiation Rate";
					
			    	ReportPanel treatmentInitiationRatePanel = new ReportPanel(report, dataList, arrayT, time, "Treatment Initiation Rate", title + " " +yearString, loc);
					
					tabPanel.add(treatmentInitiationRatePanel.getComposite(), loc);
					
				}
    		}
    		i--;
    		tabPanel.selectTab(0);	
    	}
    
    }
    
    /**
     * Draw Sputum Submission And Error Rate for every unique location
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param arrayT
     * @param xLabel
     * @param yearString
     */
    private void drawSputumSubmissionAndErrorRate(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString) {
    	
    	for (int i = 0; i < locations.length; i++) {
    	    
    		TabPanel tabPanel = new TabPanel();
    		chartPanel.add(tabPanel);
    		tabPanel.setSize("100%", "100%");
    		
    		for(int j = 0; j < 5 && i < locations.length; j++){
    			String loc = locations[i];
    			i++;
				if(!loc.equals("")){
					
					Number[] primaryData = getColumnData(result,timeArray, loc, 2);
					GraphData yAxisPrimaryData = new GraphData("Sputum Submissions", primaryData);

					Number[] secondaryData = getColumnData(result,timeArray, loc, 3);
					GraphData yAxisSecondaryData = new GraphData("Leaked", secondaryData);

					Number[] tertiaryData = getColumnData(result,timeArray, loc, 4);
					GraphData yAxisTertiaryData = new GraphData("No Result Found", tertiaryData);

					Number[] otherData = getColumnData(result,timeArray, loc, 5);
					GraphData yAxisOtherData = new GraphData("All Other Error %", otherData);

					ArrayList<GraphData> dataList = new ArrayList<GraphData>();
					dataList.add(yAxisPrimaryData);
					dataList.add(yAxisSecondaryData);
					dataList.add(yAxisTertiaryData);
					dataList.add(yAxisOtherData);
					
					String title = "Sputum Submission and Error Rate";
					String yLabel = "Percentage of Result";
					
			    	ReportPanel sputumSubmissionAndErrorRatePanel = new ReportPanel(report, dataList, arrayT, time, yLabel, title + " " +yearString, loc);
					
					tabPanel.add(sputumSubmissionAndErrorRatePanel.getComposite(), loc);
					
				}
    		}
    		i--;
    		tabPanel.selectTab(0);	
    	}
    
    }
    
    /**
     * Get Column (index#) Data for specific location and time dimension from Result in ArrayList of GraphData. 
     * 
     * @param result
     * @param locations
     * @param timeArray
     * @param index
     * @return ArrayList -> GraphData
     */
    public ArrayList<GraphData> getColumnDataAsGraphData(String[][] result, String[] locations, String[] timeArray, int index){
    	
    	ArrayList<GraphData> dataList = new ArrayList<GraphData>();
    	
    	for (String loc : locations) {

			Number[] data = getColumnData(result,timeArray, loc, index);
			GraphData yAxisData = new GraphData(loc, data);
			
			dataList.add(yAxisData);

		}
    	
    	return dataList;
    	
    }
    
    /**
     *  Get Column(#index) data from result[][] data.
     * 
     * @param data
     * @param timeArray
     * @param loc
     * @param index
     * @return
     */
    public Double[] getColumnData(String[][] data, String[] timeArray,
			String loc, int index) {
		ArrayList<Double> doubleArray = new ArrayList<Double>();
		for (int i = 0; i < timeArray.length; i++) {
			double value = findValueInData(data, loc, timeArray[i], index);
			doubleArray.add(value);
		}
		Double[] doubleArr = new Double[doubleArray.size()];
		doubleArr = doubleArray.toArray(doubleArr);
		return doubleArr;
	}
    
    /**
     * Get Time data in Array a.c to Time Dimension
     * @return String[]
     */
    public String[] getTimeArray() {

		ArrayList<String> timeArray = new ArrayList<String>();
		int from = 0;
		int to = 0;

		// Append Date filter
		String yFrom = this.yearFrom;
		String yTo = this.yearTo;
		String qFrom = this.from;
		String qTo = this.to;
		String mFrom = this.from;;
		String mTo = this.to;
		String wFrom = this.from;;
		String wTo = this.to;
		TimeDimenstion time = TimeDimenstion.valueOf(this.time.toUpperCase());
		switch (time) {
		case YEAR:
			from = Integer.valueOf(yFrom);
			to = Integer.valueOf(yTo);
			break;
		case QUARTER:
			from = Integer.valueOf(qFrom);
			to = Integer.valueOf(qTo);
			break;
		case MONTH:
			from = Integer.valueOf(mFrom);
			to = Integer.valueOf(mTo);
			break;
		case WEEK:
			from = Integer.valueOf(wFrom);
			to = Integer.valueOf(wTo);
			break;
		default:
			break;
		}

		for (int i = from; i <= to; i++) {

			timeArray.add(String.valueOf(i));
		}

		String[] timeArr = new String[timeArray.size()];
		timeArr = timeArray.toArray(timeArr);

		return timeArr;
	}
    
    /**
	 * Applies filter picked from date fields w.r.t. selected time dimension
	 * 
	 * @param params
	 * @return
	 */
	private String getFilter(Parameter[] params, String alias) {
		
		if (!alias.equals(""))
			alias = alias + ".";

		StringBuilder where = new StringBuilder(" where 1 = 1 ");
		// Append Date filter
		String yFrom = this.yearFrom;
		String yTo = this.yearTo;
		String qFrom = this.from;
		String qTo = this.to;
		String mFrom = this.from;;
		String mTo = this.to;
		String wFrom = this.from;;
		String wTo = this.to;
		
		TimeDimenstion time = TimeDimenstion.valueOf(this.time.toUpperCase());
		
		switch (time) {
		case YEAR:
			where.append(" and " + alias + "year between " + yFrom + " and "
					+ yTo);
			break;
		case QUARTER:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "quarter between " + qFrom + " and "
					+ qTo);
			break;
		case MONTH:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "month between " + mFrom + " and "
					+ mTo);
			break;
		case WEEK:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "week between " + wFrom + " and "
					+ wTo);
			break;
		default:
			break;
		}
		if (params != null) {
			for (Parameter param : params) {
				DataType type = param.getType();
				switch (type) {
				case CHAR:
				case STRING:
					where.append(" and " + param.getName());
					where.append(" = '" + param.getValue() + "'");
					break;
				default:
					where.append(" and " + param.getName());
					where.append(" = " + param.getValue());
				}
			}
		}
		return where.toString();
	}
	
	/**
	 * Return set of unique values from given data array
	 * 
	 * @param data
	 * @param columnIndex
	 * @return
	 */
	private String[] getUniqueValues(String[][] data, int columnIndex) {
		HashSet<String> values = new HashSet<String>();
		for (String[] record : data) {
			values.add(record[columnIndex]);
		}
		String[] array = values.toArray(new String[] {});
		return array;
	}

	/**
	 * 
	 * @param data
	 * @param columnValue
	 * @param rowValue
	 * @param valueIndex
	 * @return
	 */
	private double findValueInData(String[][] data, String columnValue, String rowValue, int valueIndex) {
		double value = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][1].equals(columnValue) && data[i][0].equals(rowValue)) {
				value = Double.parseDouble(data[i][valueIndex]);
			}
		}
		return value;
	}
	
	/**
	 * Calculates cumulative Data for specific index for every over every unique time
	 * 
	 * @param data
	 * @param timeArray
	 * @param loc
	 * @param index
	 * @return
	 */
	public Double[] getCumulativeData(String[][] data, String[] timeArray, String loc, int index) {
		ArrayList<Double> doubleArray = new ArrayList<Double>();
		Double screeningValue = 0.0;
		for (int i = 0; i < timeArray.length; i++) {
			double value = findValueInData(data, loc, timeArray[i], index);
			screeningValue = screeningValue + value;
			doubleArray.add(screeningValue);
		}
		
		Double[] doubleArr = new Double[doubleArray.size()];
		doubleArr = doubleArray.toArray(doubleArr);
		return doubleArr;
	}
    
 }
