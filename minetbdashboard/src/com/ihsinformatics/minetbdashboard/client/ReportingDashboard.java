/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * Main Menu Composite for TB CONTROL client
 */

package com.ihsinformatics.minetbdashboard.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.minetbdashboard.shared.LocationDimension;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;

public class ReportingDashboard extends Composite implements ChangeHandler, ClickHandler
{
	
	private static LoadingWidget loading = new LoadingWidget();
	
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	private HTML reportingOptionsLabel = new HTML("<font size=\"6\"> Reporting Options </font> <br> <br> ");
	
	private FlexTable optionsTable = new FlexTable();
	private FlexTable dateFilterTable = new FlexTable();
	
	private RadioButton simpleRadioButton = new RadioButton("radioGroup", "Simple Reports");
    private RadioButton combinedRadioButton = new RadioButton("radioGroup", "Combination Reports");
    
    private HorizontalPanel radioButtonPanel = new HorizontalPanel();
	
	private ListBox reportsList = new ListBox();
	private ListBox locationDimensionList = new ListBox();
	private ListBox timeDimensionList = new ListBox();
	
	private ListBox yearFrom = new ListBox();
	private ListBox yearTo = new ListBox();
	private ListBox quarterFrom = new ListBox();
	private ListBox quarterTo = new ListBox();
	private ListBox monthFrom = new ListBox();
	private ListBox monthTo = new ListBox();
	private ListBox weekFrom = new ListBox();
	private ListBox weekTo = new ListBox();

	private Button showButton = new Button("Generate Reports");
	
	public ReportingDashboard ()
	{	
		
		mainVerticalPanel.setSize("100%", "100%");
	
		reportingOptionsLabel.setStyleName("MineTBHeader");
		mainVerticalPanel.add(reportingOptionsLabel);
		
		radioButtonPanel.add(simpleRadioButton);
		radioButtonPanel.add(combinedRadioButton);
		
		simpleRadioButton.setValue(true);
		
		optionsTable.getElement().setAttribute("align", "center");
		optionsTable.setWidget(0, 1, radioButtonPanel);
		optionsTable.setWidget(1, 0, new Label("Report:"));
		optionsTable.setWidget(1, 1, reportsList);
		optionsTable.setWidget(2, 0, new Label("Reporting Level:"));
		optionsTable.setWidget(2, 1, locationDimensionList);
		optionsTable.setWidget(3, 0, new Label("Grouping:"));
		optionsTable.setWidget(3, 1, timeDimensionList);
		optionsTable.setWidget(4, 0, new Label("Select Date Range:"));
		optionsTable.setWidget(4, 1, dateFilterTable);
		reportsList.setWidth("300px");
		
		fillLists();
		createDateFilterWidgets(TimeDimenstion.YEAR);
		timeDimensionList.addChangeHandler(this);
		
		mainVerticalPanel.add(optionsTable);
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		buttonsPanel.add(showButton);
		buttonsPanel.setSpacing(5);
		buttonsPanel.getElement().setAttribute("align", "center");
		
		mainVerticalPanel.add(buttonsPanel);
		
		showButton.addClickHandler(this);
		simpleRadioButton.addClickHandler(this);
		combinedRadioButton.addClickHandler(this);
		
	}
	
	/**
	 * 
	 * Filter Date Widgets according to Time dimension
	 * @param time
	 */
	public void createDateFilterWidgets(TimeDimenstion time) {
		dateFilterTable.clear();
		switch (time) {
		case YEAR:
			dateFilterTable.setWidget(0, 0, yearFrom);
			dateFilterTable.setWidget(0, 1, yearTo);
			break;
		case MONTH:
			dateFilterTable.setWidget(0, 0, yearFrom);
			dateFilterTable.setWidget(1, 0, monthFrom);
			dateFilterTable.setWidget(1, 1, monthTo);
			break;
		case QUARTER:
			dateFilterTable.setWidget(0, 0, yearFrom);
			dateFilterTable.setWidget(1, 0, quarterFrom);
			dateFilterTable.setWidget(1, 1, quarterTo);
			break;
		case WEEK:
			dateFilterTable.setWidget(0, 0, yearFrom);
			dateFilterTable.setWidget(1, 0, weekFrom);
			dateFilterTable.setWidget(1, 1, weekTo);
			break;
		default:
		}

	}
	
	/**
	 * 
	 * Fill Drop Downs...
	 */
	public void fillLists() {
		String[] reports = { "Screening", "Sputum Submission",
				"GeneXpert: MTB Positive and Rif Resistants",
				"GeneXpert: Other Results", "Treatment Initiated",
				"Treatment Outcome Results"};
		
		for (String str : reports) {
			reportsList.addItem(str);
		}
		for (LocationDimension dim : LocationDimension.values()) {
			locationDimensionList.addItem(dim.toString());
		}
		for (TimeDimenstion dim : TimeDimenstion.values()) {
			timeDimensionList.addItem(dim.toString());
		}
		for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
			yearFrom.addItem(String.valueOf(year));
			yearTo.addItem(String.valueOf(year));
		}
		for (int quarter = 1; quarter <= 4; quarter++) {
			quarterFrom.addItem(String.valueOf(quarter));
			quarterTo.addItem(String.valueOf(quarter));
		}
		for (int month = 1; month <= 12; month++) {
			monthFrom.addItem(String.valueOf(month));
			monthTo.addItem(String.valueOf(month));
		}
		for (int week = 1; week <= 52; week++) {
			weekFrom.addItem(String.valueOf(week));
			weekTo.addItem(String.valueOf(week));
		}
	}

	/**
	 * 
	 * @return mainVerticalPanel
	 */
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object source = event.getSource();
		if (source == timeDimensionList) {
			TimeDimenstion time = TimeDimenstion.valueOf(MineTBClient.get(timeDimensionList));
			createDateFilterWidgets(time);
		}
		Window.scrollTo(0, 220);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		
		if(sender == showButton){
			
			load(true);
			
			String report = MineTBClient.get(reportsList);
			String time = MineTBClient.get(timeDimensionList).toLowerCase();
			String loc = MineTBClient.get(locationDimensionList).toLowerCase();

			String yearFromString = "";
			String yearToString = "";
			String fromString = "";
			String toString = "";
			
			if(time.equalsIgnoreCase("year")){
	
				yearFromString = MineTBClient.get(yearFrom); 
				yearToString = MineTBClient.get(yearTo);
			}
			else if(time.equalsIgnoreCase("quarter")){
				
				yearFromString = MineTBClient.get(yearFrom); 
				fromString = MineTBClient.get(quarterFrom);
				toString = MineTBClient.get(quarterTo); 
			}
			else if(time.equalsIgnoreCase("month")){
				
				yearFromString = MineTBClient.get(yearFrom); 
				fromString = MineTBClient.get(monthFrom);
				toString = MineTBClient.get(monthTo);
			}
			else if(time.equalsIgnoreCase("week")){
				
				yearFromString = MineTBClient.get(yearFrom); 
				fromString = MineTBClient.get(weekFrom);
				toString = MineTBClient.get(weekTo);			
			}
			
			String reportType = "";
			if(simpleRadioButton.getValue())
				reportType = "Simple Reports";
			else
				reportType = "Combination Reports";
			
			// Initiate Report Dialog Box
			ReportDialogBox reportDialog = new ReportDialogBox(reportType, report, time, loc, yearFromString, yearToString, fromString, toString);
			
			load(false);
			
			reportDialog.show();
	           
			
		}else if(sender == simpleRadioButton){
			
			String[] reports = {"Screening", "Sputum Submission",
					"GeneXpert: MTB Positive and Rif Resistants",
					"GeneXpert: Other Results", "Treatment Initiated",
					"Treatment Outcome Results"};
			reportsList.clear();
			
			for (String str : reports) {
				reportsList.addItem(str);
			}
			
		}else if (sender == combinedRadioButton){
			
			String[] reports = {"Screening Summary",
					"Sputum Submission Rates", "Treatment Initiation Rates",
					"Sputum Submission & Error Rates"};
			reportsList.clear();
			
			for (String str : reports) {
				reportsList.addItem(str);
			}
			
		}
		
	}
	
	/**
	 * Display/Hide main panel and loading widget
	 * 
	 * @param status
	 */
	public void load(boolean status) {
		mainVerticalPanel.setVisible(!status);
		if (status)
			loading.show();
		else
			loading.hide();
	}
	
}