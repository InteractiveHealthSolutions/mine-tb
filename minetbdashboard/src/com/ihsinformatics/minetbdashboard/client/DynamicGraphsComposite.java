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
import java.util.Date;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.GraphData;
import com.ihsinformatics.minetbdashboard.shared.LocationDimension;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;

/**
 * @author Rabbia
 *
 */
public class DynamicGraphsComposite implements ChangeHandler, ClickHandler  {
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	
	private String chartType = "";
	
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	// Title Widgets...
	private FlexTable titleOptionFlexTable = new FlexTable();
	private HTML titleLabel = new HTML("<font size=\"3\"> <b> Title: </b> </font>");
	private TextBox titleTextBox = new TextBox();
	private HTML subtitleLabel = new HTML("<font size=\"3\"> <b> Subtitle: </b> </font>");
	private TextBox subtitleTextBox = new TextBox();
	
	// Chart Options Widgets...
	private VerticalPanel chartOptionPanel = new VerticalPanel();
	private Grid bodyGrid = new Grid(1,3);
	
	// Data Widgets
	private VerticalPanel dataPanel = new VerticalPanel();
	private HTML dataLabel = new HTML("<font size=\"3\"> <b> Data Dimensions </b> </font>");
	private VerticalPanel dataOptionPanel  = new VerticalPanel();
	
	private Label formLabel = new Label("Select Form:");
	private ListBox formListBox = new ListBox();
	private Label formDataLabel = new Label("Select Data:");
	private CheckBox selectAllDataTables = new CheckBox("Select All");
	private ListBox formDataListBox = new ListBox();
	private Label axisLabel = new Label("y-Axis Label: ");
	private TextBox axisTextBox = new TextBox();
	private CheckBox stackedCheckBox = new CheckBox("Stacked");
	private RadioButton stackedOnDataRadioButton = new RadioButton("stack button", "on Data");
	private RadioButton stackedOnTimeRadioButton = new RadioButton("stack button", "on Time");
	
	// Group By Widgets
	private VerticalPanel groupByPanel = new VerticalPanel();
	private HTML groupByLabel = new HTML("<font size=\"3\"> <b> Group by </b> </font>");
	private VerticalPanel groupByOptionPanel  = new VerticalPanel();
	private RadioButton timeGroupByRadioButton = new RadioButton("group-by","Time");
	private RadioButton locationGroupByRadioButton = new RadioButton("group-by","Location");
	private VerticalPanel groupByOptionDetailPanel = new VerticalPanel();
	   // Time ... 
	private Label groupByTimeDimensionLabel = new Label("Select Time Dimension:");
	private ListBox groupByTimeListBox = new ListBox();
	private Label groupByRangeLabel = new Label("Select Time Range:");
    private ListBox groupByYearFromListBox = new ListBox();
    private ListBox groupByYearToListBox = new ListBox();
    private ListBox groupByFromRange = new ListBox();
    private ListBox groupByToRange = new ListBox();
    	// Location ...
	private Label groupByLocationDimensionLabel = new Label("Select Location Dimension:");
	private ListBox groupByLocationsDimensionListBox = new ListBox();
	private Label groupByLocationLabel = new Label();
	private CheckBox groupBySelectAllLocationCheckBox = new CheckBox("Select All");
	private ListBox groupByLocationListBox = new ListBox();
	
	// Reporting Level Widgets
	private VerticalPanel reportingLevelPanel = new VerticalPanel();
	private HTML reportingLevelLabel = new HTML("<font size=\"3\"> <b> Reporting Level </b> </font>");
	private VerticalPanel reportingLevelOptionPanel  = new VerticalPanel();
	private RadioButton timeReportingLevelRadioButton = new RadioButton("report-level","Time");
	private RadioButton locationReportingLevelRadioButton = new RadioButton("report-level","Location");
	private VerticalPanel reportingLevelOptionDetailPanel  = new VerticalPanel();
		// Location ...
	private Label reportingLevelSelectLocationLabel = new Label("Select Location:");
	private ListBox reportingLevelLocationDimensionListBox = new ListBox();
	private Label reportingLevelOptionsLabel = new Label();
	private ListBox reportingLevelOptionListBox = new ListBox();
		// Time ...
	private Label reportingLevelTimeDimensionLabel = new Label("Select Time:");
	private ListBox reportingLevelTimeListBox = new ListBox();
	private Label reportingLevelTimeRangeLabel = new Label("Select Time Range:");
	private Label reportingLevelYearLabel = new Label("Year: ");
	private ListBox reportingLevelYearListBox = new ListBox();
	private Label reportingLevelRangeLabel = new Label();
	private ListBox reportingLevelRangeListBox = new ListBox();
	
	// Series Widgets
	private FlexTable seriesFlexTable = new FlexTable();
	private Label addSeriesLabel = new Label("+ More Series");
	private ChartSeries chartSeries;
	private VerticalPanel seriesVerticalPanel = new VerticalPanel();
	
	//Bottom Panel
	private Button drawChart = new Button("Draw Chart");
	private HTML statement1 = new HTML("<b><font color=\"red\">* </font>Use Control Key(Ctrl) for multiple value selection.</b>");
	private HTML statement2 = new HTML("<b><font color=\"red\">* </font>Same Dimensions can't be selected for Group By and Report Level.</b>");
	
	public DynamicGraphsComposite(String chartType){
		
		this.chartType = chartType;
		
		mainVerticalPanel.setSize("100%", "100%");
		
		// Adding title Widgets
		mainVerticalPanel.add(titleOptionFlexTable);
		titleOptionFlexTable.setWidget(0, 0, titleLabel);
		titleOptionFlexTable.setWidget(0, 1, titleTextBox);
		titleOptionFlexTable.setWidget(0, 2, subtitleLabel);
		titleOptionFlexTable.setWidget(0, 3, subtitleTextBox);
		mainVerticalPanel.setCellHorizontalAlignment(titleOptionFlexTable, HasHorizontalAlignment.ALIGN_CENTER);
		
		mainVerticalPanel.add(chartOptionPanel);
		chartOptionPanel.setSize("100%", "100%");
		
		fillChartOptions(chartType);  // Add Option Widgets According to chart type selected
		
		// Add Footer...
		Grid  grid = new Grid(1,2);
		grid.setSize("100%", "100%");
		VerticalPanel panel = new VerticalPanel();
				
		panel.add(statement1);
		panel.add(statement2);
			
		grid.setWidget(0, 0, panel);
		grid.setWidget(0, 1, drawChart);
				
		mainVerticalPanel.add(grid);
		grid.getCellFormatter().getElement(0, 1).setAttribute("align", "right");
		
		// Handlers... 
		formListBox.addChangeHandler(this);
		groupByTimeListBox.addChangeHandler(this);
		groupByLocationsDimensionListBox.addChangeHandler(this);
		reportingLevelTimeListBox.addChangeHandler(this);
		reportingLevelLocationDimensionListBox.addChangeHandler(this);
		
		selectAllDataTables.addClickHandler(this);
		stackedCheckBox.addClickHandler(this);
		timeGroupByRadioButton.addClickHandler(this);
		locationGroupByRadioButton.addClickHandler(this);
		groupBySelectAllLocationCheckBox.addClickHandler(this);
		timeReportingLevelRadioButton.addClickHandler(this);
		locationReportingLevelRadioButton.addClickHandler(this);
		drawChart.addClickHandler(this);
		addSeriesLabel.addClickHandler(this);
	}
	
	public void fillChartOptions(String chartType){
		
		chartOptionPanel.clear();
		chartOptionPanel.add(bodyGrid);
		bodyGrid.setCellSpacing(20);
		
		if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar") || chartType.equalsIgnoreCase("Pie")){
			
			// Add Data Widgets
			bodyGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
			bodyGrid.setWidget(0, 0, dataPanel);
			dataPanel.add(dataLabel);
			dataOptionPanel.setStyleName("Option-Panel");
			dataPanel.add(dataOptionPanel);
			dataOptionPanel.add(formLabel);
			dataOptionPanel.add(formListBox);
			dataOptionPanel.add(formDataLabel);
			dataOptionPanel.add(formDataListBox);
			
			if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")){
				
				dataOptionPanel.add(selectAllDataTables);
				dataOptionPanel.add(formDataListBox);
				selectAllDataTables.setValue(true);
				formDataListBox.setEnabled(false);
				formDataListBox.setHeight("100px");
				formDataListBox.setMultipleSelect(true);
				
				HorizontalPanel hp = new HorizontalPanel();
				hp.add(stackedCheckBox);
				hp.add(stackedOnDataRadioButton);
				hp.add(stackedOnTimeRadioButton);
				
				stackedCheckBox.setValue(false);
				stackedOnDataRadioButton.setValue(true);
				stackedOnDataRadioButton.setVisible(false);
				stackedOnTimeRadioButton.setVisible(false);
				
				dataOptionPanel.add(hp);
				
				HorizontalPanel hp1 = new HorizontalPanel();
				hp1.add(axisLabel);
				hp1.add(axisTextBox);
				
				dataOptionPanel.add(hp1);
			}
			else{
				dataOptionPanel.add(formDataListBox);
				selectAllDataTables.setValue(false);
				stackedOnDataRadioButton.setValue(false);
				formDataListBox.setEnabled(true);
				formDataListBox.setHeight("30px");
				formDataListBox.setMultipleSelect(false);
				
				stackedCheckBox.setValue(true);
				stackedOnDataRadioButton.setVisible(false);
				stackedOnTimeRadioButton.setVisible(false);
			}
			
			formListBox.clear();
			String query = "select * from data_forms;";
			try {
				service.getTableData(query.toString(),new AsyncCallback<String[][]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(CustomMessage
								.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
						
					}

					@Override
					public void onSuccess(String[][] result) {
						
						for (int i = 0; i < result.length; i++) {
							formListBox.addItem(result[i][1], result[i][2]);
						}
						fillDataTables();
					}
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			formListBox.setWidth("250px");
			formDataListBox.setWidth("250px");
			dataOptionPanel.setWidth("340px");	
		
		}
		
		// Add GroupBy Widgets
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 1, groupByPanel);
		groupByPanel.add(groupByLabel);
		groupByOptionPanel.setStyleName("Option-Panel");
		groupByPanel.add(groupByOptionPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(timeGroupByRadioButton);
		hp.add(locationGroupByRadioButton);
		groupByOptionPanel.add(hp);
		timeGroupByRadioButton.setValue(true);
		fillGroupByPanel();
		groupByOptionPanel.add(groupByOptionDetailPanel);
		groupByOptionPanel.setWidth("340px");
		
		// Reporting Level
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 2, reportingLevelPanel);
		reportingLevelPanel.add(reportingLevelLabel);
		reportingLevelOptionPanel.setStyleName("Option-Panel");
		reportingLevelPanel.add(reportingLevelOptionPanel);
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.add(timeReportingLevelRadioButton);
		hp1.add(locationReportingLevelRadioButton);
		reportingLevelOptionPanel.add(hp1);
		locationReportingLevelRadioButton.setValue(true);
		fillReportingLevel();
		reportingLevelOptionPanel.add(reportingLevelOptionDetailPanel);
		reportingLevelOptionPanel.setWidth("340px");
		
		if(chartType.equalsIgnoreCase("Combination")){
			// Adding Series
			chartOptionPanel.add(seriesFlexTable);
			seriesFlexTable.getElement().setAttribute("align", "center");
			
			chartSeries = new ChartSeries();
			seriesVerticalPanel = chartSeries.getComposite();
			chartOptionPanel.add(seriesVerticalPanel);
			seriesVerticalPanel.getElement().setAttribute("align", "center");
			
			addSeriesLabel.setStyleName("logout-button");
			chartOptionPanel.add(addSeriesLabel);
		}
		
	}
	
	/**
	 * Fills Data Panel
	 * 
	 */
	public void fillDataTables(){
		formDataListBox.clear();
		axisTextBox.setText("Number");
		String query = "select d2.name, d2.data_table from data_forms d1, data_forms_tables d2 where d1.id = d2.data_form_id and d1.table_name = '"+MineTBClient.get(formListBox)+"';";
		try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
					
				}

				@Override
				public void onSuccess(String[][] result) {
					
					for (int i = 0; i < result.length; i++) {
						formDataListBox.addItem(result[i][0], result[i][1]);
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Fills Group By Panel
	 * 
	 */
	public void fillGroupByPanel(){
		groupByOptionDetailPanel.clear();
		
		if(timeGroupByRadioButton.getValue()){
			groupByOptionDetailPanel.add(groupByTimeDimensionLabel);
			groupByOptionDetailPanel.add(groupByTimeListBox);
			groupByTimeListBox.clear();
			for (TimeDimenstion dim : TimeDimenstion.values()) {
				groupByTimeListBox.addItem(dim.toString());
			}
			groupByOptionDetailPanel.add(groupByRangeLabel);
			FlexTable dateRangeXAxisFlexTable = new FlexTable();
			dateRangeXAxisFlexTable.setWidget(0, 0, groupByYearFromListBox);
			dateRangeXAxisFlexTable.setWidget(0, 1, groupByYearToListBox);
			dateRangeXAxisFlexTable.setWidget(1, 0, groupByFromRange);
			dateRangeXAxisFlexTable.setWidget(1, 1, groupByToRange);
			groupByOptionDetailPanel.add(dateRangeXAxisFlexTable);
			groupByYearFromListBox.clear();
			groupByYearToListBox.clear();
			for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
				groupByYearFromListBox.addItem(String.valueOf(year));
				groupByYearToListBox.addItem(String.valueOf(year));
			}
			fillGroupByTimeRange();
		}
		else{
			groupByOptionDetailPanel.add(groupByLocationDimensionLabel);
			groupByLocationsDimensionListBox.clear();
			for (LocationDimension dim : LocationDimension.values()) {
				groupByLocationsDimensionListBox.addItem(dim.toString());
			}
			groupByOptionDetailPanel.add(groupByLocationsDimensionListBox);
			
			String HeadingLabel = "Select " + MineTBClient.get(groupByLocationsDimensionListBox) + "(s).";
			
			groupByLocationLabel.setText(HeadingLabel);
			groupByOptionDetailPanel.add(groupByLocationLabel);
			groupByOptionDetailPanel.add(groupBySelectAllLocationCheckBox);
			groupBySelectAllLocationCheckBox.setValue(true);
			groupByLocationListBox.setEnabled(false);
			groupByOptionDetailPanel.add(groupByLocationListBox);
			fillGroupByLocationOptions();
			groupByLocationListBox.setHeight("100px");
			groupByLocationListBox.setWidth("250px");
			groupByLocationListBox.setMultipleSelect(true);
		}
	}
	
	/**
	 * Fills X-Axis Time Range
	 * 
	 */
	public void fillGroupByTimeRange(){
		
		groupByYearFromListBox.setVisible(false);
		groupByYearToListBox.setVisible(false);
		groupByFromRange.setVisible(false);
		groupByToRange.setVisible(false);
		
		String time = MineTBClient.get(groupByTimeListBox).toLowerCase();
		
		if(time.equalsIgnoreCase("Year")){
			groupByYearFromListBox.setVisible(true);
			groupByYearToListBox.setVisible(true);
		}
		else if(time.equalsIgnoreCase("Quarter")){
			groupByYearFromListBox.setVisible(true);
			groupByFromRange.setVisible(true);
			groupByToRange.setVisible(true);
			
			groupByFromRange.clear();
			groupByToRange.clear();
			
			for (int quarter = 1; quarter <= 4; quarter++) {
				groupByFromRange.addItem(String.valueOf(quarter));
				groupByToRange.addItem(String.valueOf(quarter));
			}
		}
		else if(time.equalsIgnoreCase("Month")){
			groupByYearFromListBox.setVisible(true);
			groupByFromRange.setVisible(true);
			groupByToRange.setVisible(true);
			
			groupByFromRange.clear();
			groupByToRange.clear();
			
			for (int month = 1; month <= 12; month++) {
				groupByFromRange.addItem(String.valueOf(month));
				groupByToRange.addItem(String.valueOf(month));
			}
		}
		else if(time.equalsIgnoreCase("Week")){
			groupByYearFromListBox.setVisible(true);
			groupByFromRange.setVisible(true);
			groupByToRange.setVisible(true);
			
			groupByFromRange.clear();
			groupByToRange.clear();
			
			for (int week = 1; week <= 52; week++) {
				groupByFromRange.addItem(String.valueOf(week));
				groupByToRange.addItem(String.valueOf(week));
			}
		}
		
	}
	
	/**
	 * Fills Location Options for Group 
	 * 
	 */
	public void fillGroupByLocationOptions(){
		
		String HeadingLabel = "Select " + MineTBClient.get(groupByLocationsDimensionListBox) + "(s).";
		groupByLocationLabel.setText(HeadingLabel);
		
		String query = "select state_province, city_village, location_name from dim_location";
		
		groupByLocationListBox.clear();
		
		try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
					
				}

				@Override
				public void onSuccess(String[][] result) {
					
					String[] locationsProvince = getUniqueValues(result, 0);
					String[] locationsDistict = getUniqueValues(result, 1);
					String[] locationsFacility = getUniqueValues(result, 2);
					
					
					if(MineTBClient.get(groupByLocationsDimensionListBox).equalsIgnoreCase("Province")){
						for(int i = 0; i < locationsProvince.length; i++){
							if(!(locationsProvince[i] == null || locationsProvince[i].equals("")))
								groupByLocationListBox.addItem(locationsProvince[i]);
						}
					}
					else if(MineTBClient.get(groupByLocationsDimensionListBox).equalsIgnoreCase("District")){
						for(int i = 0; i < locationsDistict.length; i++){
							if(!(locationsDistict[i] == null || locationsDistict[i].equals("")))
								groupByLocationListBox.addItem(locationsDistict[i]);
						}
					}
					else if(MineTBClient.get(groupByLocationsDimensionListBox).equalsIgnoreCase("Facility")){
						for(int i = 0; i < locationsFacility.length; i++){
							if(!(locationsFacility[i] == null || locationsFacility[i].equals("")))
								groupByLocationListBox.addItem(locationsFacility[i]);
						}
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Reporting Level Option for Pie (1D) chart
	 */
	public void fillReportingLevel(){
		reportingLevelOptionDetailPanel.clear();
		
		if(locationReportingLevelRadioButton.getValue()){
			
			reportingLevelOptionDetailPanel.add(reportingLevelSelectLocationLabel);
			reportingLevelLocationDimensionListBox.clear();
			for (LocationDimension dim : LocationDimension.values()) {
				reportingLevelLocationDimensionListBox.addItem(dim.toString());
			}
			reportingLevelOptionDetailPanel.add(reportingLevelLocationDimensionListBox);
			String HeadingLabel = "Select " + MineTBClient.get(reportingLevelLocationDimensionListBox);
			reportingLevelOptionsLabel.setText(HeadingLabel);
			reportingLevelOptionDetailPanel.add(reportingLevelOptionsLabel);
			reportingLevelOptionDetailPanel.add(reportingLevelOptionListBox);
			fillReportingLevelLocationOptions();
			reportingLevelOptionListBox.setWidth("250px");
			
		}
		else{
			reportingLevelOptionDetailPanel.add(reportingLevelTimeDimensionLabel);
			reportingLevelTimeListBox.clear();
			for (TimeDimenstion dim : TimeDimenstion.values()) {
				reportingLevelTimeListBox.addItem(dim.toString());
			}
			reportingLevelOptionDetailPanel.add(reportingLevelTimeListBox);
			reportingLevelOptionDetailPanel.add(reportingLevelTimeRangeLabel);
			FlexTable flexTable = new FlexTable();
			flexTable.setWidget(0,0,reportingLevelYearLabel);
			flexTable.setWidget(0,1,reportingLevelYearListBox);
			flexTable.setWidget(1,0,reportingLevelRangeLabel);
			flexTable.setWidget(1,1,reportingLevelRangeListBox);
			reportingLevelOptionDetailPanel.add(flexTable);
			reportingLevelYearListBox.clear();
			for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
				reportingLevelYearListBox.addItem(String.valueOf(year));
			}
			fillReportingLevelTimeWidgets();
		}
		
	}
	
	/**
	 * Fills Location Options for Pie Reporting Level
	 * 
	 */
	public void fillReportingLevelLocationOptions(){
		
		String HeadingLabel = "Select " + MineTBClient.get(reportingLevelLocationDimensionListBox) + "(s).";
		reportingLevelOptionsLabel.setText(HeadingLabel);
		
		String query = "select state_province, city_village, location_name from dim_location";
		
		reportingLevelOptionListBox.clear();
		
		try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
					
				}

				@Override
				public void onSuccess(String[][] result) {
					
					String[] locationsProvince = getUniqueValues(result, 0);
					String[] locationsDistict = getUniqueValues(result, 1);
					String[] locationsFacility = getUniqueValues(result, 2);
					
					
					if(MineTBClient.get(reportingLevelLocationDimensionListBox).equalsIgnoreCase("Province")){
						for(int i = 0; i < locationsProvince.length; i++){
							if(!(locationsProvince[i] == null || locationsProvince[i].equals("")))
								reportingLevelOptionListBox.addItem(locationsProvince[i]);
						}
					}
					else if(MineTBClient.get(reportingLevelLocationDimensionListBox).equalsIgnoreCase("District")){
						for(int i = 0; i < locationsDistict.length; i++){
							if(!(locationsDistict[i] == null || locationsDistict[i].equals("")))
								reportingLevelOptionListBox.addItem(locationsDistict[i]);
						}
					}
					else if(MineTBClient.get(reportingLevelLocationDimensionListBox).equalsIgnoreCase("Facility")){
						for(int i = 0; i < locationsFacility.length; i++){
							if(!(locationsFacility[i] == null || locationsFacility[i].equals("")))
								reportingLevelOptionListBox.addItem(locationsFacility[i]);
						}
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reporting Level Time Dimension for Pie (1D) charts
	 * 
	 */
	public void fillReportingLevelTimeWidgets(){
		
		String time = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
		if(time.equalsIgnoreCase("Year")){
			reportingLevelRangeLabel.setVisible(false);
			reportingLevelRangeListBox.setVisible(false);
			reportingLevelYearLabel.setVisible(true);
			reportingLevelYearListBox.setVisible(true);
		}
		else{
			reportingLevelRangeLabel.setVisible(true);
			reportingLevelRangeListBox.setVisible(true);
			reportingLevelYearLabel.setVisible(true);
			reportingLevelYearListBox.setVisible(true);
			
			reportingLevelRangeListBox.clear();
			
			if(time.equalsIgnoreCase("Quarter")){
				
				for (int quarter = 1; quarter <= 4; quarter++) {
					reportingLevelRangeListBox.addItem(String.valueOf(quarter));
				}
			}
			else if(time.equalsIgnoreCase("Month")){
				
				for (int month = 1; month <= 12; month++) {
					reportingLevelRangeListBox.addItem(String.valueOf(month));
				}
			}
			else if(time.equalsIgnoreCase("Week")){
				
				for (int week = 1; week <= 52; week++) {
					reportingLevelRangeListBox.addItem(String.valueOf(week));
				}
			}
			
			reportingLevelRangeLabel.setText(Character.toUpperCase(time.charAt(0)) + time.substring(1) + ": ");
			
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
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(sender == selectAllDataTables){
			if(selectAllDataTables.getValue())
				formDataListBox.setEnabled(false);
			else
				formDataListBox.setEnabled(true);
		}
		else if(sender == stackedCheckBox){
			if(stackedCheckBox.getValue()){
				stackedOnDataRadioButton.setVisible(true);
				stackedOnTimeRadioButton.setVisible(true);
			}else {
				stackedOnDataRadioButton.setVisible(false);
				stackedOnTimeRadioButton.setVisible(false);
			}
				
		}
		else if(sender == timeGroupByRadioButton || sender == locationGroupByRadioButton){
			fillGroupByPanel();
		}
		else if(sender == groupBySelectAllLocationCheckBox){
			if(groupBySelectAllLocationCheckBox.getValue())
				groupByLocationListBox.setEnabled(false);
			else
				groupByLocationListBox.setEnabled(true);
		}
		else if(sender == timeReportingLevelRadioButton || sender == locationReportingLevelRadioButton){
			fillReportingLevel();
		}
		else if(sender == drawChart){
			if(validate()){
				
				if(chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar") || chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Pie"))
					drawChart();
				else if(chartType.equalsIgnoreCase("Combination"))
					drawCombinationChart();
				
			}
		}
		else if(sender == addSeriesLabel){
			chartSeries.addSeries();
		}
	}
	
	public void drawCombinationChart(){
		
		final ArrayList<SeriesPanel> seriesPanelArray = chartSeries.getSeriesPanelArray();
		ArrayList<String> queryArray = new ArrayList<String>();
		
		String where = "";
		String time = "";
		String loc = "";
		
		String locName = "";
    	String timeName = "";
    	String[] timeArray = null;
    	String[] locArray = null;
    	
    	if(timeGroupByRadioButton.getValue()){
			time = MineTBClient.get(groupByTimeListBox).toLowerCase();
			loc = MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase();
			
			timeArray = getTimeArray();
			locName = MineTBClient.get(reportingLevelOptionListBox);
		}	
		else{
			time = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
			loc = MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase();
			
			if(groupBySelectAllLocationCheckBox.getValue()){
	    		
    			locArray = new String[groupByLocationListBox.getItemCount()];
    			
    			for(int i=0; i<groupByLocationListBox.getItemCount(); i++)
    				locArray[i] = groupByLocationListBox.getValue(i);
    			
    		}
    		else
    			locArray = MineTBClient.get(groupByLocationListBox).split(" , ");
			
			if(time.equalsIgnoreCase("Year"))
    			timeName = MineTBClient.get(reportingLevelYearListBox);
    		else
    			timeName = MineTBClient.get(reportingLevelRangeListBox);
		}
			
		where = getWhereString() + " group by " + time + ", " + loc + " order by " + time + ", " + loc;
		
    	for(SeriesPanel sp : seriesPanelArray){
    		
    		String data = "";
    		String[] array = sp.getTableColumn().split(" , ");
    		for(String a : array){
    			
    			data = data + "sum(" + a + ") , ";
    		}
    		
    		data = data.substring(0, data.length()-3);
    		
    		String query = "select " + time + ", " + loc + ", " + data + " from " + sp.getFormName() + " " + where;
    		queryArray.add(query);
    		
    	}
    	
    	final String location = locName;
    	final String timeDimension = timeName;
    	final String[] timeDimensionsArray = timeArray;
    	final String[] locationArray = locArray;
    	
    	try {
			service.executeQueries(queryArray,new AsyncCallback<ArrayList<String[][]>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
				}

				@Override
				public void onSuccess(ArrayList<String[][]> resultArray) {
					
					ArrayList<GraphData> dataList = new ArrayList<GraphData>();
					
					String xLabel = "";
					if(timeGroupByRadioButton.getValue())
						xLabel = MineTBClient.get(groupByTimeListBox).toLowerCase();
					else
						xLabel = MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase();
					
					for(int i = 0; i < resultArray.size(); i++){   // for every result
						
						String[][] result = resultArray.get(i);
						SeriesPanel sp = seriesPanelArray.get(i); 
						
						String[] columnNames = sp.getTableColumn().split(" , ");  
						String[] column = sp.getColumnTitles().split(" , ");  
						int index = 2;
						
						if(timeGroupByRadioButton.getValue()){  // if grouped by time dimension
							
							for(int k = 0; k < columnNames.length; k++){
								
								Number[]  numberArray = new Number[timeDimensionsArray.length]; // for all time
								
								for(int j = 0; j < timeDimensionsArray.length; j++){
								
									numberArray[j] = findValueInData(result, location, timeDimensionsArray[j], index);
									
								}
								
								GraphData gd = new GraphData(column[k], numberArray, sp.getSeriesType(), sp.getId());
								dataList.add(gd);
								index++;
							}
								
						}
						else{
							
							for(int k = 0; k < columnNames.length; k++){
								
								Number[]  numberArray = new Number[locationArray.length]; // for all time
								
								for(int j = 0; j < locationArray.length; j++){
								
									numberArray[j] = findValueInData(result, locationArray[j], timeDimension, index);
									
								}
								
								GraphData gd = new GraphData(column[k], numberArray, sp.getSeriesType(), sp.getId());
								dataList.add(gd);
								index++;
							}
						}
						
					} 
					
					String title = MineTBClient.get(titleTextBox) + " - " + MineTBClient.get(subtitleTextBox);
					String timeline = "";
					if(timeGroupByRadioButton.getValue()){
						
						String yearFrom = MineTBClient.get(groupByYearFromListBox);
						String yearTo = MineTBClient.get(groupByYearToListBox);
						String from = "";
						String to = "";
						String time = MineTBClient.get(groupByTimeListBox).toLowerCase();
						if(!time.equalsIgnoreCase("year")){
							from = MineTBClient.get(groupByFromRange);
							to = MineTBClient.get(groupByToRange);
						}
						
						timeline = Character.toUpperCase(xLabel.charAt(0)) + xLabel.substring(1) + " wise";
					       
				       if(time.equalsIgnoreCase("year")){
				    	   timeline = timeline + " (" + yearFrom + " - " + yearTo +")";
				       } else if (time.equals("quarter")){
				    	   timeline = timeline + " (Q" + from + " -  Q" + to + " for year " + yearFrom + ")";
				       } else if (time.equals("month")){
				    	   timeline = timeline + " (M" + from + " -  M" + to + " for year " + yearFrom + ")";
				       } else if (time.equals("week")){
				    	   timeline = timeline + " (W" + from + " -  W" + to + " for year " + yearFrom + ")";
				       }
				       
				       if(!xLabel.equalsIgnoreCase("Year"))
							xLabel = xLabel + " (" + MineTBClient.get(groupByYearFromListBox) + ")";
				       
				       String location = MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase();
				       timeline = timeline + " for " + Character.toUpperCase(location.charAt(0)) + location.substring(1) + " - " + MineTBClient.get(reportingLevelOptionListBox);
					
				       ReportPanel screenedPanel = new ReportPanel(chartType, dataList, timeDimensionsArray, Character.toUpperCase(xLabel.charAt(0)) + xLabel.substring(1), "", MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),"");
					   DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
					   dialogBox.show();
				       
					}
					else{
						timeline = Character.toUpperCase(xLabel.charAt(0)) + xLabel.substring(1) + " wise for Year " + MineTBClient.get(reportingLevelYearListBox);
						if (timeDimension.equals("quarter")){
				    	   timeline = timeline + " (Q" +  MineTBClient.get(reportingLevelYearListBox) + ")";
						} else if (timeDimension.equals("month")){
				    	   timeline = timeline + " (M" + MineTBClient.get(reportingLevelYearListBox) + ")";
						} else if (timeDimension.equals("week")){
				    	   timeline = timeline + " (W" + MineTBClient.get(reportingLevelYearListBox) + ")";
						}
						
						ReportPanel screenedPanel = new ReportPanel(chartType, dataList, locationArray, Character.toUpperCase(xLabel.charAt(0)) + xLabel.substring(1), "", MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),"");
						DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
						dialogBox.show();
					}
					
					Window.scrollTo(0, 300);
				}
    	
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
	
	
	/**
	 * Draw Chart a/c to Option selected...
	 */
	public void drawChart(){
		
		StringBuilder query = new StringBuilder();
		
		String time = "";
		String loc = "";
		
		if(timeGroupByRadioButton.getValue()){
			time = MineTBClient.get(groupByTimeListBox).toLowerCase();
			loc = MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase();
		}	
		else{
			time = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
			loc = MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase();
		}
		
		query.append(getSelectString(time, loc));
		query.append(getFromString());
		query.append(getWhereString());
		query.append(" group by " + time + ", " + loc);
    	query.append(" order by " + time + ", " + loc);
    	
		try{

			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					
					if(timeGroupByRadioButton.getValue()){
						
						String time = MineTBClient.get(groupByTimeListBox).toLowerCase();
						
						String[] timeArray = getTimeArray();    // Get all time values			
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
						
						
						String xLabel = Character.toUpperCase(time.charAt(0)) + time.substring(1);
						String yearFrom = MineTBClient.get(groupByYearFromListBox);
						String yearTo = MineTBClient.get(groupByYearToListBox);
						String from = "";
						String to = "";
						if(!time.equalsIgnoreCase("year")){
							from = MineTBClient.get(groupByFromRange);
							to = MineTBClient.get(groupByToRange);
						}
						
						String timeline = xLabel + " wise";
					       
				       if(time.equalsIgnoreCase("year")){
				    	   timeline = timeline + " (" + yearFrom + " - " + yearTo +")";
				       } else if (time.equals("quarter")){
				    	   timeline = timeline + " (Q" + from + " -  Q" + to + " for year " + yearFrom + ")";
				       } else if (time.equals("month")){
				    	   timeline = timeline + " (M" + from + " -  M" + to + " for year " + yearFrom + ")";
				       } else if (time.equals("week")){
				    	   timeline = timeline + " (W" + from + " -  W" + to + " for year " + yearFrom + ")";
				       }
				       
				       String location = MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase();
				       timeline = timeline + " for " + Character.toUpperCase(location.charAt(0)) + location.substring(1) + " - " + MineTBClient.get(reportingLevelOptionListBox);
								
						timeArray = getTimeArray();
								
						ArrayList<GraphData> dataList = null;
						String yLabel = MineTBClient.get(axisTextBox);
						if(!xLabel.equalsIgnoreCase("Year"))
							xLabel = xLabel + " (" + MineTBClient.get(groupByYearFromListBox) + ")";
									
						if(selectAllDataTables.getValue()){   // if All is selected for data...
							String select = "";
							for (int i = 0; i < formDataListBox.getItemCount(); i++) {
					            select = select + formDataListBox.getItemText(i) + " , ";   
							}
							String splitSelect[] = select.split(" , ");
							dataList = getColumnDataAsGraphData(result,splitSelect,timeArray,"Time");
							
						}
						else{ // if more than one selected
							String select = MineTBClient.get(formDataListBox);
							String splitSelect[] = select.split(" , ");
							dataList = getColumnDataAsGraphData(result,splitSelect,timeArray,"Time");
							
						}
						
						String title = MineTBClient.get(titleTextBox) + " - " + MineTBClient.get(subtitleTextBox);
						
						String legendType = "";
						
						// Stacked Bar or Column Chart selected
						if((chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")) && stackedCheckBox.getValue()){  
							if(stackedOnDataRadioButton.getValue()){
								ReportPanel screenedPanel = new ReportPanel("Stacked " + chartType, dataList, arrayT, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
								DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
								dialogBox.show();
							}
							else{
								ReportPanel screenedPanel = new ReportPanel("Stacked on Time " + chartType, dataList, arrayT, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
								DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
								dialogBox.show();
							}
						}
						else{  // else
							ReportPanel screenedPanel = new ReportPanel(chartType, dataList, arrayT, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
							DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
							dialogBox.show();
						}
						
					}
					else{
						
						String location = MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase();
						String xLabel = Character.toUpperCase(location.charAt(0)) + location.substring(1);
						
						String title = MineTBClient.get(titleTextBox) + " - " + MineTBClient.get(subtitleTextBox);
						
						String legendType = "";
						
						String yLabel = MineTBClient.get(axisTextBox);
						
						String time = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
						time = Character.toUpperCase(time.charAt(0)) + time.substring(1);
						String timeline = xLabel + " wise for Year " + MineTBClient.get(reportingLevelYearListBox);
						if (time.equals("quarter")){
				    	   timeline = timeline + " (Q" +  MineTBClient.get(reportingLevelYearListBox) + ")";
						} else if (time.equals("month")){
				    	   timeline = timeline + " (M" + MineTBClient.get(reportingLevelYearListBox) + ")";
						} else if (time.equals("week")){
				    	   timeline = timeline + " (W" + MineTBClient.get(reportingLevelYearListBox) + ")";
						}
						
						
						String locations = "";
						if(groupBySelectAllLocationCheckBox.getValue()){
							for(int i = 0; i < groupByLocationListBox.getItemCount(); i++)
								locations = locations + groupByLocationListBox.getItemText(i) + " , ";
						}
						else
							locations = MineTBClient.get(groupByLocationListBox);
						
						locations = locations.substring(0,locations.length()-3);
						String[] locationsArray = locations.split(" , ");
						
						ArrayList<GraphData> dataList = null;
						if(selectAllDataTables.getValue()){   // if All is selected for data...
							String select = "";
							for (int i = 0; i < formDataListBox.getItemCount(); i++) {
					            select = select + formDataListBox.getItemText(i) + " , ";   
							}
							String splitSelect[] = select.split(" , ");
							dataList = getColumnDataAsGraphData(result,splitSelect,locationsArray,"Location");
							
						}
						else{ // if more than one selected
							String select = MineTBClient.get(formDataListBox);
							String splitSelect[] = select.split(" , ");
							dataList = getColumnDataAsGraphData(result,splitSelect,locationsArray,"Location");
							
						}	
						
						// Stacked Bar or Column Chart selected
						if((chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")) && stackedCheckBox.getValue()){  
							if(stackedOnDataRadioButton.getValue()){
								ReportPanel screenedPanel = new ReportPanel("Stacked " + chartType, dataList, locationsArray, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
								DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
								dialogBox.show();
							}
							else{
								ReportPanel screenedPanel = new ReportPanel("Stacked on Time " + chartType, dataList, locationsArray, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
								DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
								dialogBox.show();
							}
						}
						else{  // else
							ReportPanel screenedPanel = new ReportPanel(chartType, dataList, locationsArray, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),legendType);
							DynamicReportDialogBox dialogBox = new DynamicReportDialogBox(title, timeline, screenedPanel.getComposite());
							dialogBox.show();
						}
					}
				}
						
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
				}
		   });
		}	catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * Retrieve Data A/c to selection as ArrayList of GraphData   
     * 
     * (used when multiple data columns are selected )
     * 
     * @param result
     * @param splitSelect
     * @param array
     * @return
     */
    public ArrayList<GraphData> getColumnDataAsGraphData(String[][] result, String[] splitSelect, String[] array, String groupedBy){
    	ArrayList<GraphData> dataList = new ArrayList<GraphData>();
    	
    	for(String select : splitSelect){
    	    int index = 0;
    	    String selectArray[] = null;
    	    
    	    if(!selectAllDataTables.getValue()){
    	    	String s = MineTBClient.get(formDataListBox);
    	    	selectArray = s.split(" , ");
    	    }
    	    else{
    	    	String s = "";
    	    	for (int i = 0; i < formDataListBox.getItemCount(); i++) {
		            s = s + formDataListBox.getItemText(i) + " , ";   
    	    	}
    	    	selectArray = s.split(" , ");
    	    }
    	    
    		for(int i = 0; i < selectArray.length; i++){
    			if(select.equals(selectArray[i])){
    				index = i;
    				break;
    			}
    		}
    		
    		index = index+2;
    		
    		if(groupedBy.equalsIgnoreCase("Time")){
	    		String loc[] = MineTBClient.get(reportingLevelOptionListBox).split(" , ");
	    		
	    		Number[] data = getColumnData(result,array,loc[0],index,groupedBy);
				GraphData yAxisData = new GraphData(select, data);
				
				dataList.add(yAxisData);
    		}
    		else{
    			String time = "";
    			if(MineTBClient.get(reportingLevelTimeListBox).equalsIgnoreCase("year"))
    				time = MineTBClient.get(reportingLevelYearListBox);
    			else
    				time = MineTBClient.get(reportingLevelRangeListBox);
	    		
	    		Number[] data = getColumnData(result,array,time,index,groupedBy);
				GraphData yAxisData = new GraphData(select, data);
				
				dataList.add(yAxisData);
    		}
    	}
    	
    	
    	return dataList;
    }
    
    /**
     *  Get Column(#index) data from result[][] data.
     * 
     * @param data
     * @param array
     * @param group
     * @param index
     * @return
     */
    public Double[] getColumnData(String[][] data, String[] array, String group, int index, String groupedBy) {
		ArrayList<Double> doubleArray = new ArrayList<Double>();
		if(groupedBy.equalsIgnoreCase("time")){
			for (int i = 0; i < array.length; i++) {
				double value = findValueInData(data, group, array[i], index);
				doubleArray.add(value);
			}
		}
		else{
			for (int i = 0; i < array.length; i++) {
				double value = findValueInData(data, array[i], group, index);
				doubleArray.add(value);
			}
		}
		Double[] doubleArr = new Double[doubleArray.size()];
		doubleArr = doubleArray.toArray(doubleArr);
		return doubleArr;
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
     * Get Time data in Array a.c to Time Dimension
     * @return String[]
     */
    public String[] getTimeArray() {

		ArrayList<String> timeArray = new ArrayList<String>();
		int from = 0;
		int to = 0;
		
		// Append Date filter
		String yFrom = "0";
		String yTo = "0";
		String tFrom = "0";
		String tTo = "0";
		TimeDimenstion time = null;
		
		time = TimeDimenstion.valueOf(MineTBClient.get(groupByTimeListBox));
		if(time.toString().equalsIgnoreCase("Year")){
			yFrom = MineTBClient.get(groupByYearFromListBox);
			yTo = MineTBClient.get(groupByYearToListBox);
		}
		else{
			yFrom = MineTBClient.get(groupByYearFromListBox);
			tFrom = MineTBClient.get(groupByFromRange);
			tTo = MineTBClient.get(groupByToRange);
		}
		
		switch (time) {
		case YEAR:
			from = Integer.valueOf(yFrom);
			to = Integer.valueOf(yTo);
			break;
		case QUARTER:
		case MONTH:
		case WEEK:
			from = Integer.valueOf(tFrom);
			to = Integer.valueOf(tTo);
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
	 * creates and returns select statement from  data selected;
	 * @return
	 */
	public String getSelectString(String time, String loc){
		
		String select = "";
		select = "select " + time + ", " + loc + ", ";
		
		if(selectAllDataTables.getValue()){
			for (int i = 0; i < formDataListBox.getItemCount(); i++) {
		            select = select + "sum(" + formDataListBox.getValue(i) + ") , ";   
		    }
			select = select.substring(0, select.length()-3);
		}
		else{
			String[] arrayData = MineTBClient.get(formDataListBox).split(" , ");
			for(String data : arrayData){
				select = select + "sum(" + data + ") , ";
			}
			select = select.substring(0, select.length()-3);
		}
		
		return select;
	}
	
	/**
	 * creates and return from statement from selected data
	 * @return
	 */
	public String getFromString(){
		
		String from = " from ";
		
		from = from + MineTBClient.get(formListBox);
		
		return from;
	}
	
	/**
	 * creates and return where statement from selected data
	 * @return
	 */
	public String getWhereString(){
		
		String where = "";
			
		// Time is Grouped By and Location Reporting Level
		if(timeGroupByRadioButton.getValue()){
			
			// Grouped By...
			String t = MineTBClient.get(groupByTimeListBox).toLowerCase();
			TimeDimenstion time = TimeDimenstion.valueOf(t.toUpperCase());
			
			if(where.equals(""))
				where = " where 1=1";
			
			switch (time) {
			case YEAR:
				where = where + " and year between " + MineTBClient.get(groupByYearFromListBox).toLowerCase() + " and " + MineTBClient.get(groupByYearToListBox).toLowerCase();
				break;
			case QUARTER:
			case MONTH:
			case WEEK:
				where = where + " and year = " + MineTBClient.get(groupByYearFromListBox).toLowerCase();
				where = where + " and " + t + " between " + MineTBClient.get(groupByFromRange).toLowerCase() + " and " + MineTBClient.get(groupByToRange).toLowerCase();
				break;
			default:
				break;
			}
			
			// Reporting Level..
			where = where + " and " + MineTBClient.get(reportingLevelLocationDimensionListBox).toLowerCase() + " = '" + MineTBClient.get(reportingLevelOptionListBox) +"'";

		}
		else{  //Location is Grouped By  and Reporting Level is Time
			
			// Grouped by
			if(!groupBySelectAllLocationCheckBox.getValue()){  // some values are selected
				String selected = MineTBClient.get(groupByLocationListBox);
				String stringArray[] = selected.split(" , ");
				for(int i = 0; i < stringArray.length; i++){
					
					if(!stringArray[i].equals("")){
						
						if(where.equals(""))
							where = " where ( " + MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase() + " = '" + stringArray[i] + "'";
						else
							where =  where + " or " + MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase() + " = '" + stringArray[i] + "'";
							
					}
				}
						
				where = where + " )";
			}
			else{  // all value selected
				for (int i = 0; i < groupByLocationListBox.getItemCount(); i++) {
						if(where.equals(""))
							where = " where ( " + MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase() + " = '" + groupByLocationListBox.getValue(i) + "'";
						else
							where =  where + " or " + MineTBClient.get(groupByLocationsDimensionListBox).toLowerCase() + " = '" + groupByLocationListBox.getValue(i) + "'";							
				}
				where = where + " )";
			}
			
			// Reporting Level
			String t = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
			TimeDimenstion time = TimeDimenstion.valueOf(t.toUpperCase());
			
			switch (time) {
			case YEAR:
				where = where + " and year = " + MineTBClient.get(reportingLevelYearListBox);
				break;
			case QUARTER:
			case MONTH:
			case WEEK:
				where = where + " and year = " + MineTBClient.get(reportingLevelYearListBox);
				where = where + " and " + t + " = " + MineTBClient.get(reportingLevelRangeListBox);
				break;
			default:
				break;
			}
			
		}
		
		
		return where;
	}
		
	/**
	 * 
	 * Validates the selection...
	 * @return
	 */
	public boolean validate(){
		
		boolean flag = true;
		String alertMessage = "";
		
		if(chartType.equalsIgnoreCase("Column") ||  chartType.equalsIgnoreCase("Bar") || chartType.equalsIgnoreCase("Line")){
			if(!selectAllDataTables.getValue() && MineTBClient.get(formDataListBox).equals("")){
				flag = false;
				alertMessage = alertMessage + "Select atleast one data item for Group by location. \n";
			}
		}
		
		if(timeGroupByRadioButton.getValue() == timeReportingLevelRadioButton.getValue()){
			flag = false;
			alertMessage = alertMessage + "Select different dimension for Group by and Reporting Level.\n";
		}
		
		if(!flag){
			Window.alert("Fix following error(s) to continue. \n\n" + alertMessage);
		}
		
		return flag;
	}	

	@Override
	public void onChange(ChangeEvent event) {
		Object source = event.getSource();
		 if (source == formListBox){
				fillDataTables();
		 }
		 else if (source == groupByTimeListBox){
				fillGroupByTimeRange();
		 }
		 else if (source == groupByLocationsDimensionListBox){
			 fillGroupByLocationOptions();
		 }
		 else if (source == reportingLevelLocationDimensionListBox){
			 fillReportingLevelLocationOptions();
		 }
		 else if(source == reportingLevelTimeListBox){
			 fillReportingLevelTimeWidgets();
		 }
		
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

}
