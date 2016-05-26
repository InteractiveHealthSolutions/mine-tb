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

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
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
import com.ihsinformatics.minetbdashboard.shared.MineTB;
import com.ihsinformatics.minetbdashboard.shared.Parameter;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;

/**
 * @author Rabbia
 *
 */
public class DynamicGraphsComposite implements ChangeHandler, ClickHandler  {
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	
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
	
	/**
	 *  For Line/Column/Pie Chart  (2D)
	 */
	
	// x-Axis Widgets
	private VerticalPanel xAxisPanel = new VerticalPanel();
	private HTML xAxisLabel = new HTML("<font size=\"3\"> <b> x-Axis </b> </font>");
	private VerticalPanel xAxisOptionPanel = new VerticalPanel();
	private Label xAxisTimeDimensionLabel = new Label("Select Time Dimension:");
	private ListBox xAxisTimeListBox = new ListBox();
	private Label xAxisTimeRangeLabel = new Label("Select Time Range:");
	private VerticalPanel xAxisTimeRangePanel = new VerticalPanel();
    private ListBox xAxisYearFromListBox = new ListBox();
    private ListBox xAxisYearToListBox = new ListBox();
    private ListBox xAxisFromRange = new ListBox();
    private ListBox xAxisToRange = new ListBox();
    private Label xAxisYLabel = new Label("x-Axis Label: ");
	private TextBox xAxisXlabelTextBox = new TextBox();
	
	// yAxis Widgets
	private VerticalPanel yAxisPanel = new VerticalPanel();
	private HTML yAxisLabel = new HTML("<font size=\"3\"> <b> y-Axis </b> </font>");
	private VerticalPanel yAxisOptionPanel = new VerticalPanel();
	private Label yAxisDataDimensionLabel = new Label("Select Form:");
	private ListBox yAxisDataListBox = new ListBox();
	private Label yAxisDataTableLabel = new Label("Select Data:");
	private CheckBox yAxisSelectAllDataTables = new CheckBox("Select All");
	private ListBox yAxisDataTableListBox = new ListBox();
	private Label yAxisYLabel = new Label("y-Axis Label: ");
	private TextBox yAxisYlabelTextBox = new TextBox();
	private CheckBox yAxisStackedCheckBox = new CheckBox("Stacked");
	private RadioButton yAxisOnDataRadioButton = new RadioButton("stack button", "on Data");
	private RadioButton yAxisOnTimeRadioButton = new RadioButton("stack button", "on Time");
	
	// Report Level Widgets
	private VerticalPanel reportingLevelPanel = new VerticalPanel();
	private HTML reportingLevelLabel = new HTML("<font size=\"3\"> <b> Reporting Level </b> </font>");
	private VerticalPanel reportingLevelOptionPanel  = new VerticalPanel();
	private Label selectLevelLabel = new Label("Select Level:");
	private ListBox reportingLevelListBox = new ListBox();
	private Label levelOptionsLabel = new Label();
	private CheckBox selectAllLocationCheckBox = new CheckBox("Select All");
	private ListBox levelOptionListBox = new ListBox();
	
	//Bottom Panel
	private Button drawChart = new Button("Draw Chart");
	private HTML statement1 = new HTML("<b><font color=\"red\">* </font>Use Control Key(Ctrl) for multiple value selection.</b>");
	private HTML statement2 = new HTML("<b><font color=\"red\">* </font>You can only select multiple values (or All), either for y-Axis or Reporting Level.</b>");
	private HTML statement3 = new HTML("<b><font color=\"red\">* </font>Same Dimensions can't be selected for Group By and Report Level.</b>");
	
	/**
	 * For Pie Chart 1D 
	 */
	
	// Group By Widgets
	private VerticalPanel groupByPanel = new VerticalPanel();
	private HTML groupByLabel = new HTML("<font size=\"3\"> <b> Group by </b> </font>");
	private VerticalPanel groupByOptionPanel  = new VerticalPanel();
	private RadioButton timeGroupByRadioButton = new RadioButton("group-by","Time");
	private RadioButton locationGroupByRadioButton = new RadioButton("group-by","Location");
	private VerticalPanel groupByOptionDetailPanel = new VerticalPanel();
	
	// Data Widgets
	private VerticalPanel dataPanel = new VerticalPanel();
	private HTML dataLabel = new HTML("<font size=\"3\"> <b> Data </b> </font>");
	private VerticalPanel dataOptionPanel  = new VerticalPanel();
	
	// Reporting Level Widgets
	private VerticalPanel reportingLevelIDPanel = new VerticalPanel();
	private HTML reportingLevelIDLabel = new HTML("<font size=\"3\"> <b> Reporting Level </b> </font>");
	private VerticalPanel reportingLevelIDOptionPanel  = new VerticalPanel();
	private RadioButton timeReportingLevelRadioButton = new RadioButton("report-level","Time");
	private RadioButton locationReportingLevelRadioButton = new RadioButton("report-level","Location");
	private VerticalPanel reportingLevelIDOptionDetailPanel  = new VerticalPanel();
	private Label selectReportingLevelLabel = new Label("Select Level:");
	private ListBox reportingLevel1DListBox = new ListBox();
	private Label levelOptions1DLabel = new Label();
	private ListBox levelOption1DListBox = new ListBox();
	private Label reportingLevelTimeDimensionLabel = new Label("Select Time Dimension:");
	private ListBox reportingLevelTimeListBox = new ListBox();
	private Label reportingLevelTimeRangeLabel = new Label("Select Time Range:");
	private Label reportingLevelYearLabel = new Label("Year: ");
	private ListBox reportingLevelYearListBox = new ListBox();
	private Label reportingLevelRangeLabel = new Label();
	private ListBox reportingLevelRangeListBox = new ListBox();
	
	private String chartType = "";
	
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
		
		// Adding Option Panels a/c to Chart Type
		if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar"))
			fill2DChartOptions(chartType);
		else if(chartType.equalsIgnoreCase("Pie"))
			fill1DChartOptions(chartType);
		
		// Add Footer...
		Grid  grid = new Grid(1,2);
		grid.setSize("100%", "100%");
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(statement1);
		
		if(chartType.equalsIgnoreCase("Pie"))
			panel.add(statement3);
		else if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar"))
			panel.add(statement2);
		
		grid.setWidget(0, 0, panel);
		grid.setWidget(0, 1, drawChart);
		
		mainVerticalPanel.add(grid);
		grid.getCellFormatter().getElement(0, 1).setAttribute("align", "right");
		
		
		xAxisTimeListBox.addChangeHandler(this);
		yAxisDataListBox.addChangeHandler(this);
		reportingLevelListBox.addChangeHandler(this);
		reportingLevel1DListBox.addChangeHandler(this);
		reportingLevelTimeListBox.addChangeHandler(this);
		
		yAxisSelectAllDataTables.addClickHandler(this);
		selectAllLocationCheckBox.addClickHandler(this);
		yAxisStackedCheckBox.addClickHandler(this);
		drawChart.addClickHandler(this);
		timeGroupByRadioButton.addClickHandler(this);
		locationGroupByRadioButton.addClickHandler(this);
		timeReportingLevelRadioButton.addClickHandler(this);
		locationReportingLevelRadioButton.addClickHandler(this);
		
	}
	
	/**
	 * 
	 * @return mainVerticalPanel
	 */
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}
	
	/**
	 * 
	 * Options for Pie Chart...
	 */
	public void fill1DChartOptions(String chartType){
		
		chartOptionPanel.clear();
		chartOptionPanel.add(bodyGrid);
		bodyGrid.setCellSpacing(20);
		
		// Add Data Widgets
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 0, dataPanel);
		dataPanel.add(dataLabel);
		dataOptionPanel.setStyleName("Option-Panel");
		dataPanel.add(dataOptionPanel);
		dataOptionPanel.add(yAxisDataDimensionLabel);
		dataOptionPanel.add(yAxisDataListBox);
		dataOptionPanel.add(yAxisDataTableLabel);
		dataOptionPanel.add(yAxisDataTableListBox);
        yAxisSelectAllDataTables.setValue(false);
		yAxisDataListBox.clear();
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
						yAxisDataListBox.addItem(result[i][1], result[i][2]);
					}
					fillYAxisDataTables();
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		yAxisDataListBox.setWidth("250px");
		yAxisDataTableListBox.setWidth("250px");
		
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
		
		// Reporting Level
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 2, reportingLevelIDPanel);
		reportingLevelIDPanel.add(reportingLevelIDLabel);
		reportingLevelIDOptionPanel.setStyleName("Option-Panel");
		reportingLevelIDPanel.add(reportingLevelIDOptionPanel);
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.add(timeReportingLevelRadioButton);
		hp1.add(locationReportingLevelRadioButton);
		reportingLevelIDOptionPanel.add(hp1);
		locationReportingLevelRadioButton.setValue(true);
		fillReportingLevel();
		reportingLevelIDOptionPanel.add(reportingLevelIDOptionDetailPanel);
	}
	
	public void fillGroupByPanel(){
		groupByOptionDetailPanel.clear();
		
		if(timeGroupByRadioButton.getValue()){
			groupByOptionDetailPanel.add(xAxisTimeDimensionLabel);
			groupByOptionDetailPanel.add(xAxisTimeListBox);
			xAxisTimeListBox.clear();
			for (TimeDimenstion dim : TimeDimenstion.values()) {
				xAxisTimeListBox.addItem(dim.toString());
			}
			groupByOptionDetailPanel.add(xAxisTimeRangeLabel);
			FlexTable dateRangeXAxisFlexTable = new FlexTable();
			dateRangeXAxisFlexTable.setWidget(0, 0, xAxisYearFromListBox);
			dateRangeXAxisFlexTable.setWidget(0, 1, xAxisYearToListBox);
			dateRangeXAxisFlexTable.setWidget(1, 0, xAxisFromRange);
			dateRangeXAxisFlexTable.setWidget(1, 1, xAxisToRange);
			groupByOptionDetailPanel.add(dateRangeXAxisFlexTable);
			xAxisYearFromListBox.clear();
			xAxisYearToListBox.clear();
			for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
				xAxisYearFromListBox.addItem(String.valueOf(year));
				xAxisYearToListBox.addItem(String.valueOf(year));
			}
			fillXAxisTimeRange();
		}
		else{
			groupByOptionDetailPanel.add(selectLevelLabel);
			reportingLevelListBox.clear();
			for (LocationDimension dim : LocationDimension.values()) {
				reportingLevelListBox.addItem(dim.toString());
			}
			groupByOptionDetailPanel.add(reportingLevelListBox);
			
			String HeadingLabel = "Select " + MineTBClient.get(reportingLevelListBox) + "(s).";
			
			levelOptionsLabel.setText(HeadingLabel);
			groupByOptionDetailPanel.add(levelOptionsLabel);
			groupByOptionDetailPanel.add(selectAllLocationCheckBox);
			selectAllLocationCheckBox.setValue(true);
			levelOptionListBox.setEnabled(false);
			groupByOptionDetailPanel.add(levelOptionListBox);
			fillLocationOptions();
			levelOptionListBox.setHeight("100px");
			levelOptionListBox.setWidth("250px");
			levelOptionListBox.setMultipleSelect(true);
		}
	}
	
	
	/**
	 * 
	 * Reporting Level Option for Pie (1D) chart
	 */
	public void fillReportingLevel(){
		reportingLevelIDOptionDetailPanel.clear();
		
		if(locationReportingLevelRadioButton.getValue()){
			
			reportingLevelIDOptionDetailPanel.add(selectReportingLevelLabel);
			reportingLevel1DListBox.clear();
			for (LocationDimension dim : LocationDimension.values()) {
				reportingLevel1DListBox.addItem(dim.toString());
			}
			reportingLevelIDOptionDetailPanel.add(reportingLevel1DListBox);
			String HeadingLabel = "Select " + MineTBClient.get(reportingLevel1DListBox);
			levelOptions1DLabel.setText(HeadingLabel);
			reportingLevelIDOptionDetailPanel.add(levelOptions1DLabel);
			reportingLevelIDOptionDetailPanel.add(levelOption1DListBox);
			fillLocation1DOptions();
			levelOption1DListBox.setWidth("250px");
			
		}
		else{
			reportingLevelIDOptionDetailPanel.add(reportingLevelTimeDimensionLabel);
			reportingLevelTimeListBox.clear();
			for (TimeDimenstion dim : TimeDimenstion.values()) {
				reportingLevelTimeListBox.addItem(dim.toString());
			}
			reportingLevelIDOptionDetailPanel.add(reportingLevelTimeListBox);
			reportingLevelIDOptionDetailPanel.add(reportingLevelTimeRangeLabel);
			FlexTable flexTable = new FlexTable();
			flexTable.setWidget(0,0,reportingLevelYearLabel);
			flexTable.setWidget(0,1,reportingLevelYearListBox);
			flexTable.setWidget(1,0,reportingLevelRangeLabel);
			flexTable.setWidget(1,1,reportingLevelRangeListBox);
			reportingLevelIDOptionDetailPanel.add(flexTable);
			reportingLevelYearListBox.clear();
			for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
				reportingLevelYearListBox.addItem(String.valueOf(year));
			}
			fill1DTimeWidgets();
		}
		
	}
	
	/**
	 * 
	 * Options For Line/Column/Bar Chart...
	 */
	public void fill2DChartOptions(String chartType){
		
		chartOptionPanel.clear();
		chartOptionPanel.add(bodyGrid);
		bodyGrid.setCellSpacing(20);
		
		// Add X-Axis Widgets
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 0, xAxisPanel);
		xAxisPanel.add(xAxisLabel);
		xAxisOptionPanel.setStyleName("Option-Panel");
		xAxisPanel.add(xAxisOptionPanel);
		xAxisOptionPanel.add(xAxisTimeDimensionLabel);
		xAxisOptionPanel.add(xAxisTimeListBox);
		xAxisTimeListBox.clear();
		for (TimeDimenstion dim : TimeDimenstion.values()) {
			xAxisTimeListBox.addItem(dim.toString());
		}
		xAxisOptionPanel.add(xAxisTimeRangeLabel);
		FlexTable dateRangeXAxisFlexTable = new FlexTable();
		xAxisTimeRangePanel.add(dateRangeXAxisFlexTable);
		dateRangeXAxisFlexTable.setWidget(0, 0, xAxisYearFromListBox);
		dateRangeXAxisFlexTable.setWidget(0, 1, xAxisYearToListBox);
		dateRangeXAxisFlexTable.setWidget(1, 0, xAxisFromRange);
		dateRangeXAxisFlexTable.setWidget(1, 1, xAxisToRange);
		xAxisOptionPanel.add(xAxisTimeRangePanel);
		xAxisYearFromListBox.clear();
		xAxisYearToListBox.clear();
		for (int year = 2014; year <= new Date().getYear() + 1900; year++) {
			xAxisYearFromListBox.addItem(String.valueOf(year));
			xAxisYearToListBox.addItem(String.valueOf(year));
		}
		fillXAxisTimeRange();
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(xAxisYLabel);
		hp.add(xAxisXlabelTextBox);
		xAxisOptionPanel.add(hp);
	
		// Add y-Axis Widgets
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 1, yAxisPanel);
		yAxisPanel.add(yAxisLabel);
		yAxisOptionPanel.setStyleName("Option-Panel");
		yAxisPanel.add(yAxisOptionPanel);
		yAxisOptionPanel.add(yAxisDataDimensionLabel);
        yAxisOptionPanel.add(yAxisDataListBox);
        yAxisOptionPanel.add(yAxisDataTableLabel);
        yAxisOptionPanel.add(yAxisSelectAllDataTables);
        yAxisOptionPanel.add(yAxisDataTableListBox);
        yAxisSelectAllDataTables.setValue(true);
        yAxisDataTableListBox.setEnabled(false);
		yAxisDataListBox.clear();
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
						yAxisDataListBox.addItem(result[i][1], result[i][2]);
					}
					fillYAxisDataTables();
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(chartType.equals("Column") || chartType.equals("Bar")){  // Add Stacked Option For Bar and Column
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			yAxisOptionPanel.add(horizontalPanel);
			horizontalPanel.add(yAxisStackedCheckBox);
			
			Grid grid = new Grid(1,2);
			grid.setWidget(0, 0, yAxisOnDataRadioButton);
			grid.setWidget(0, 1, yAxisOnTimeRadioButton);
			yAxisOnDataRadioButton.setValue(true);
			
			horizontalPanel.add(grid);
			
			yAxisOnDataRadioButton.setVisible(false);
			yAxisOnTimeRadioButton.setVisible(false);			
		}
		
		yAxisDataTableListBox.setHeight("100px");
		yAxisDataTableListBox.setMultipleSelect(true);
		yAxisDataListBox.setWidth("250px");
		yAxisDataTableListBox.setWidth("250px");
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.add(yAxisYLabel);
		hp1.add(yAxisYlabelTextBox);
		yAxisOptionPanel.add(hp1);
		
		// Add Report Level Widgets
		bodyGrid.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
		bodyGrid.setWidget(0, 2, reportingLevelPanel);
		reportingLevelPanel.add(reportingLevelLabel);
		reportingLevelOptionPanel.setStyleName("Option-Panel");
		reportingLevelPanel.add(reportingLevelOptionPanel);
		reportingLevelOptionPanel.add(selectLevelLabel);
		reportingLevelListBox.clear();
		for (LocationDimension dim : LocationDimension.values()) {
			reportingLevelListBox.addItem(dim.toString());
		}
		reportingLevelOptionPanel.add(reportingLevelListBox);
		
		String HeadingLabel = "Select " + MineTBClient.get(reportingLevelListBox) + "(s).";
		
		levelOptionsLabel.setText(HeadingLabel);
		reportingLevelOptionPanel.add(levelOptionsLabel);
		reportingLevelOptionPanel.add(selectAllLocationCheckBox);
		selectAllLocationCheckBox.setValue(true);
		levelOptionListBox.setEnabled(false);
		reportingLevelOptionPanel.add(levelOptionListBox);
		fillLocationOptions();
		levelOptionListBox.setHeight("100px");
		levelOptionListBox.setWidth("250px");
		levelOptionListBox.setMultipleSelect(true);
		
	}
	
	/**
	 * Fills X-Axis Time Range
	 * 
	 */
	public void fillXAxisTimeRange(){
		
		xAxisYearFromListBox.setVisible(false);
		xAxisYearToListBox.setVisible(false);
		xAxisFromRange.setVisible(false);
		xAxisToRange.setVisible(false);
		
		String time = MineTBClient.get(xAxisTimeListBox).toLowerCase();
		
		if(time.equalsIgnoreCase("Year")){
			xAxisYearFromListBox.setVisible(true);
			xAxisYearToListBox.setVisible(true);
		}
		else if(time.equalsIgnoreCase("Quarter")){
			xAxisYearFromListBox.setVisible(true);
			xAxisFromRange.setVisible(true);
			xAxisToRange.setVisible(true);
			
			xAxisFromRange.clear();
			xAxisToRange.clear();
			
			for (int quarter = 1; quarter <= 4; quarter++) {
				xAxisFromRange.addItem(String.valueOf(quarter));
				xAxisToRange.addItem(String.valueOf(quarter));
			}
		}
		else if(time.equalsIgnoreCase("Month")){
			xAxisYearFromListBox.setVisible(true);
			xAxisFromRange.setVisible(true);
			xAxisToRange.setVisible(true);
			
			xAxisFromRange.clear();
			xAxisToRange.clear();
			
			for (int month = 1; month <= 12; month++) {
				xAxisFromRange.addItem(String.valueOf(month));
				xAxisToRange.addItem(String.valueOf(month));
			}
		}
		else if(time.equalsIgnoreCase("Week")){
			xAxisYearFromListBox.setVisible(true);
			xAxisFromRange.setVisible(true);
			xAxisToRange.setVisible(true);
			
			xAxisFromRange.clear();
			xAxisToRange.clear();
			
			for (int week = 1; week <= 52; week++) {
				xAxisFromRange.addItem(String.valueOf(week));
				xAxisToRange.addItem(String.valueOf(week));
			}
		}
		
		xAxisXlabelTextBox.setText(Character.toUpperCase(time.charAt(0)) + time.substring(1));
	}
	
	/**
	 * Fills Y-Axis Data
	 * 
	 */
	public void fillYAxisDataTables(){
		yAxisDataTableListBox.clear();
		yAxisYlabelTextBox.setText("Number");
		String query = "select d2.name, d2.data_table from data_forms d1, data_forms_tables d2 where d1.id = d2.data_form_id and d1.table_name = '"+MineTBClient.get(yAxisDataListBox)+"';";
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
						yAxisDataTableListBox.addItem(result[i][0], result[i][1]);
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
	public void fill1DTimeWidgets(){
		
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
	 * Fills Location Options for Pie Reporting Level
	 * 
	 */
	public void fillLocation1DOptions(){
		
		String HeadingLabel = "Select " + MineTBClient.get(reportingLevel1DListBox) + "(s).";
		levelOptions1DLabel.setText(HeadingLabel);
		
		String query = "select state_province, city_village, location_name from dim_location";
		
		levelOption1DListBox.clear();
		
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
					
					
					if(MineTBClient.get(reportingLevel1DListBox).equalsIgnoreCase("Province")){
						for(int i = 0; i < locationsProvince.length; i++){
							if(!(locationsProvince[i] == null || locationsProvince[i].equals("")))
								levelOption1DListBox.addItem(locationsProvince[i]);
						}
					}
					else if(MineTBClient.get(reportingLevel1DListBox).equalsIgnoreCase("District")){
						for(int i = 0; i < locationsDistict.length; i++){
							if(!(locationsDistict[i] == null || locationsDistict[i].equals("")))
								levelOption1DListBox.addItem(locationsDistict[i]);
						}
					}
					else if(MineTBClient.get(reportingLevel1DListBox).equalsIgnoreCase("Facility")){
						for(int i = 0; i < locationsFacility.length; i++){
							if(!(locationsFacility[i] == null || locationsFacility[i].equals("")))
								levelOption1DListBox.addItem(locationsFacility[i]);
						}
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Fills Location Options for Reporting Level
	 * 
	 */
	public void fillLocationOptions(){
		
		String HeadingLabel = "Select " + MineTBClient.get(reportingLevelListBox) + "(s).";
		levelOptionsLabel.setText(HeadingLabel);
		
		String query = "select state_province, city_village, location_name from dim_location";
		
		levelOptionListBox.clear();
		
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
					
					
					if(MineTBClient.get(reportingLevelListBox).equalsIgnoreCase("Province")){
						for(int i = 0; i < locationsProvince.length; i++){
							if(!(locationsProvince[i] == null || locationsProvince[i].equals("")))
								levelOptionListBox.addItem(locationsProvince[i]);
						}
					}
					else if(MineTBClient.get(reportingLevelListBox).equalsIgnoreCase("District")){
						for(int i = 0; i < locationsDistict.length; i++){
							if(!(locationsDistict[i] == null || locationsDistict[i].equals("")))
								levelOptionListBox.addItem(locationsDistict[i]);
						}
					}
					else if(MineTBClient.get(reportingLevelListBox).equalsIgnoreCase("Facility")){
						for(int i = 0; i < locationsFacility.length; i++){
							if(!(locationsFacility[i] == null || locationsFacility[i].equals("")))
								levelOptionListBox.addItem(locationsFacility[i]);
						}
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
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
	
	@Override
	public void onChange(ChangeEvent event) {
		Object source = event.getSource();
		 if (source == xAxisTimeListBox){
			fillXAxisTimeRange();
		 }
		 else if (source == yAxisDataListBox){
				fillYAxisDataTables();
		 }
		 else if (source == reportingLevelListBox){
				fillLocationOptions();
		 }
		 else if (source == reportingLevel1DListBox){
			 fillLocation1DOptions();
		 }
		 else if(source == reportingLevelTimeListBox){
			 fill1DTimeWidgets();
		 }
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if(sender == yAxisSelectAllDataTables){
			if(yAxisSelectAllDataTables.getValue())
				yAxisDataTableListBox.setEnabled(false);
			else
				yAxisDataTableListBox.setEnabled(true);
		}
		else if(sender == selectAllLocationCheckBox){
			if(selectAllLocationCheckBox.getValue())
				levelOptionListBox.setEnabled(false);
			else
				levelOptionListBox.setEnabled(true);
		}
		else if(sender == yAxisStackedCheckBox){
			if(yAxisStackedCheckBox.getValue()){
				yAxisOnDataRadioButton.setVisible(true);
				yAxisOnTimeRadioButton.setVisible(true);
			}else {
				yAxisOnDataRadioButton.setVisible(false);
				yAxisOnTimeRadioButton.setVisible(false);
			}
				
		}
		else if(sender == drawChart){
			if(validate()){
				if(chartType.equals("Pie"))
					drawPieChart();
				else if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar"))
					draw2DChart();	
			}
		}
		else if(sender == timeGroupByRadioButton || sender == locationGroupByRadioButton){
			fillGroupByPanel();
		}
		else if(sender == timeReportingLevelRadioButton || sender == locationReportingLevelRadioButton){
			fillReportingLevel();
		}
	}
	
	
	public void drawPieChart(){
		
		StringBuilder query = new StringBuilder();
		
		String time = "";
		if(timeGroupByRadioButton.getValue())
			time = MineTBClient.get(xAxisTimeListBox).toLowerCase();
		else
			time = MineTBClient.get(reportingLevelTimeListBox).toLowerCase();
		String loc = "";
		if(locationGroupByRadioButton.getValue())
			loc = MineTBClient.get(reportingLevelListBox).toLowerCase();
		else
			loc = MineTBClient.get(reportingLevel1DListBox).toLowerCase();
		
		query.append(getSelectString(time, loc));
		query.append(getFromString());
		query.append(getWhereString());
    	query.append(" group by " + time + ", " + loc);
    	query.append(" order by " + time + ", " + loc);
    	
    	try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(CustomMessage
							.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
				}

				@Override
				public void onSuccess(String[][] result) {
					String[] timeArray = getTimeArray();    // Get all time values
					String[] locations = getUniqueValues(result, 1);    // Get all unique locations involved
					
					if(timeGroupByRadioButton.getValue()){
						
						Number[]  numberArray = new Number[timeArray.length]; 
						
						for(int i = 0; i < timeArray.length; i++){
							
							numberArray[i] = findValueInData(result, locations[0], timeArray[i], 2);
							
						}
						
						ArrayList<GraphData> dataList = new ArrayList<GraphData>();
						GraphData gd = new GraphData(locations[0],numberArray);
						dataList.add(gd);
						
						String xLabel = MineTBClient.get(xAxisTimeListBox);
						
						if(!xLabel.equalsIgnoreCase("YEAR"))
							xLabel = xLabel + " (" + MineTBClient.get(xAxisYearFromListBox) + ")";
						
						String yLabel = locations[0];
						
						ReportPanel screenedPanel = new ReportPanel(chartType, dataList, timeArray, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),"");
						DynamicReportDialogBox dialogBox = new DynamicReportDialogBox("", "", screenedPanel.getComposite());
						dialogBox.show();
						
					}
					else{
						
						Number[]  numberArray = new Number[locations.length];
						
						String time = "";
						String label = "";
						if(MineTBClient.get(reportingLevelTimeListBox).equalsIgnoreCase("YEAR")){
							time = MineTBClient.get(reportingLevelYearListBox);
							label = MineTBClient.get(reportingLevelYearListBox);
						}	
						else{ 
							time = MineTBClient.get(reportingLevelRangeListBox);
							label = MineTBClient.get(reportingLevelYearListBox) +" ("+ MineTBClient.get(reportingLevelTimeListBox).substring(0,1) + " - " + MineTBClient.get(reportingLevelRangeListBox) + ")";
						}	
						
						for(int i = 0; i < locations.length; i++){
							
							numberArray[i] = findValueInData(result, locations[i], time, 2);
							
						}
						
						ArrayList<GraphData> dataList = new ArrayList<GraphData>();
						GraphData gd = new GraphData(label, numberArray);
						dataList.add(gd);
						
						String xLabel = MineTBClient.get(reportingLevelListBox);
						String yLabel = "";
						if(MineTBClient.get(reportingLevelTimeListBox).equalsIgnoreCase("YEAR"))
							yLabel = MineTBClient.get(reportingLevelYearListBox);
						else 
							yLabel = MineTBClient.get(reportingLevelYearListBox) + "("  + MineTBClient.get(reportingLevelTimeListBox).toLowerCase() + " - "  + MineTBClient.get(reportingLevelRangeListBox) + ")";
						
						ReportPanel screenedPanel = new ReportPanel(chartType, dataList, locations, xLabel, yLabel, MineTBClient.get(titleTextBox), MineTBClient.get(subtitleTextBox),"");
						DynamicReportDialogBox dialogBox = new DynamicReportDialogBox("", "", screenedPanel.getComposite());
						dialogBox.show();
						
					}
					
				}
    	
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    			
	}
	
	/**
	 * Draw Charts a/c to selection
	 * 
	 */
	public void draw2DChart(){
		
		StringBuilder query = new StringBuilder();
		String loc = MineTBClient.get(reportingLevelListBox).toLowerCase();
		String time = MineTBClient.get(xAxisTimeListBox).toLowerCase();
		
    	query.append(getSelectString(time, loc));
    	query.append(getFromString());
    	query.append(getWhereString());
    	query.append(" group by " + time + ", " + loc);
    	query.append(" order by " + time + ", " + loc);
		
    	try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
							
					String time = MineTBClient.get(xAxisTimeListBox).toLowerCase();
							
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
					
					String yearFrom = MineTBClient.get(xAxisYearFromListBox);
					String yearTo = MineTBClient.get(xAxisYearToListBox);
					String from = "";
					String to = "";
					if(!time.equalsIgnoreCase("year")){
						from = MineTBClient.get(xAxisFromRange);
						to = MineTBClient.get(xAxisToRange);
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
							
					timeArray = getTimeArray();
							
					ArrayList<GraphData> dataList = null;
					String yLabel = MineTBClient.get(yAxisYlabelTextBox);
					xLabel = MineTBClient.get(xAxisXlabelTextBox);
								
					if(yAxisSelectAllDataTables.getValue()){   // if All is selected for data...
						String select = "";
						for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
				            select = select + yAxisDataTableListBox.getItemText(i) + " , ";   
						}
						String splitSelect[] = select.split(" , ");
						dataList = getColumnDataAsGraphDataBySelection(result,splitSelect,timeArray);
						
					}// if one values is selected on data
					else if(!yAxisSelectAllDataTables.getValue() && MineTBClient.get(yAxisDataTableListBox).split(" , ").length == 1){
						dataList = getColumnDataAsGraphDataByLoc(result, locations, timeArray,2);
						
					}
					else{ // if more than one selected
						String select = "";
						for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
							if(yAxisDataTableListBox.isItemSelected(i))
								select = select + yAxisDataTableListBox.getItemText(i) + " , ";   
						}
						String splitSelect[] = select.split(" , ");
						dataList = getColumnDataAsGraphDataBySelection(result,splitSelect,timeArray);
						
					}
					
					String title = MineTBClient.get(titleTextBox) + " - " + MineTBClient.get(subtitleTextBox);
					
					String legendType = "";
					if(selectAllLocationCheckBox.getValue() || MineTBClient.get(levelOptionListBox).split(" , ").length > 1){
						legendType = MineTBClient.get(reportingLevelListBox).toLowerCase();
						legendType = Character.toUpperCase(legendType.charAt(0)) + legendType.substring(1);
					}
					
					// Stacked Bar or Column Chart selected
					if((chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")) && yAxisStackedCheckBox.getValue()){  
						if(yAxisOnDataRadioButton.getValue()){
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
	 * creates and returns select statement from  data selected;
	 * @return
	 */
	public String getSelectString(String time, String loc){
		
		String select = "";
		
		select = "select " + time + ", " + loc + ", ";
		
		if(yAxisSelectAllDataTables.getValue()){
			for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
		            select = select + "sum(" + yAxisDataTableListBox.getValue(i) + ") , ";   
		    }
			select = select.substring(0, select.length()-2);
		}
		else{
			for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
				if(yAxisDataTableListBox.isItemSelected(i))
					select = select + "sum(" + yAxisDataTableListBox.getValue(i) + ") , ";   
			}
			select = select.substring(0, select.length()-2);
		}
		
		return select;
	}
	
	/**
	 * creates and return from statement from selected data
	 * @return
	 */
	public String getFromString(){
		
		String from = " from ";
		
		from = from + MineTBClient.get(yAxisDataListBox);
		
		return from;
	}
	
	/**
	 * creates and return where statement from selected data
	 * @return
	 */
	public String getWhereString(){
		
		String where = "";
		if(chartType.equalsIgnoreCase("Pie")){   // For Pie Chart Options
			
			// Time is Grouped By
			if(timeGroupByRadioButton.getValue()){
				// Grouped By...
				String t = MineTBClient.get(xAxisTimeListBox).toLowerCase();
				TimeDimenstion time = TimeDimenstion.valueOf(t.toUpperCase());
				
				if(where.equals(""))
					where = " where 1=1";
				
				switch (time) {
				case YEAR:
					where = where + " and year between " + MineTBClient.get(xAxisYearFromListBox).toLowerCase() + " and " + MineTBClient.get(xAxisYearToListBox).toLowerCase();
					break;
				case QUARTER:
				case MONTH:
				case WEEK:
					where = where + " and year = " + MineTBClient.get(xAxisYearFromListBox).toLowerCase();
					where = where + " and " + t + " between " + MineTBClient.get(xAxisFromRange).toLowerCase() + " and " + MineTBClient.get(xAxisToRange).toLowerCase();
					break;
				default:
					break;
				}
				// Reporting Level..
				where = where + " and " + MineTBClient.get(reportingLevel1DListBox).toLowerCase() + " = '" + MineTBClient.get(levelOption1DListBox) +"'";
			}
			else{  //Location is Grouped By
				// some value selected
				if(!selectAllLocationCheckBox.getValue()){
					String selected = MineTBClient.get(levelOptionListBox);
					String stringArray[] = selected.split(" , ");
					if(stringArray.length > 0){
						for(int i = 0; i < stringArray.length; i++){
							
							if(!stringArray[i].equals("")){
								
								if(where.equals(""))
									where = " where ( " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + stringArray[i] + "'";
								else
									where =  where + " or " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + stringArray[i] + "'";
									
							}
						}
					}		
					where = where + " )";
				}
				else{  // all value selected
					for (int i = 0; i < levelOptionListBox.getItemCount(); i++) {
							if(where.equals(""))
								where = " where ( " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + levelOptionListBox.getValue(i) + "'";
							else
								where =  where + " or " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + levelOptionListBox.getValue(i) + "'";							
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
		}
		else if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")){  // For Column, Bar, Line Chart Options
			// Reporting Level ...
			if(!selectAllLocationCheckBox.getValue()){   // if some value selected
				String selected = MineTBClient.get(levelOptionListBox);
				String stringArray[] = selected.split(" , ");
				if(stringArray.length > 0){
					for(int i = 0; i < stringArray.length; i++){
						
						if(!stringArray[i].equals("")){
							
							if(where.equals(""))
								where = " where ( " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + stringArray[i] + "'";
							else
								where =  where + " or " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + stringArray[i] + "'";
								
						}
					}
					
					where = where + " )";
				}
			}
			else{   // if all selected
				for (int i = 0; i < levelOptionListBox.getItemCount(); i++) {
						if(where.equals(""))
							where = " where ( " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + levelOptionListBox.getValue(i) + "'";
						else
							where =  where + " or " + MineTBClient.get(reportingLevelListBox).toLowerCase() + " = '" + levelOptionListBox.getValue(i) + "'";		
				}
				where = where + " )";
			}
			
			// Time ...
			String t = MineTBClient.get(xAxisTimeListBox).toLowerCase();
			TimeDimenstion time = TimeDimenstion.valueOf(t.toUpperCase());
			
			if(where.equals(""))
				where = " where 1=1";
			
			switch (time) {
			case YEAR:
				where = where + " and year between " + MineTBClient.get(xAxisYearFromListBox).toLowerCase() + " and " + MineTBClient.get(xAxisYearToListBox).toLowerCase();
				break;
			case QUARTER:
			case MONTH:
			case WEEK:
				where = where + " and year = " + MineTBClient.get(xAxisYearFromListBox).toLowerCase();
				where = where + " and "+ t +" between " + MineTBClient.get(xAxisFromRange).toLowerCase() + " and " + MineTBClient.get(xAxisToRange).toLowerCase();
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
		
		if(chartType.equals("Pie")){
			
			if(timeGroupByRadioButton.getValue() == timeReportingLevelRadioButton.getValue()){
				flag = false;
				alertMessage = alertMessage + "Select different dimension for Group by and Reporting Level.\n";
			}
			else{
				if(locationGroupByRadioButton.getValue() && !selectAllLocationCheckBox.getValue() && MineTBClient.get(levelOptionListBox).equals("")){
					flag = false;
					alertMessage = alertMessage + "Select atleast one data item for Group by location. \n";
				}
			}
			
		}
		else if(chartType.equalsIgnoreCase("Line") || chartType.equalsIgnoreCase("Column") || chartType.equalsIgnoreCase("Bar")){
			if(!selectAllLocationCheckBox.getValue() && MineTBClient.get(levelOptionListBox).equals("")){
				flag = false;
				alertMessage = alertMessage + "Select atleast one group by item. \n";
			}
			
			if(!yAxisSelectAllDataTables.getValue() && MineTBClient.get(yAxisDataTableListBox).equals("")){
				flag = false;
				alertMessage = alertMessage + "Select atleast one data item for y-Axis. \n";
			}
			else{
				boolean f = false;
				if(yAxisSelectAllDataTables.getValue() || MineTBClient.get(yAxisDataTableListBox).split(" , ").length != 1)
					f = true;
				
				if(f){
					
					if(selectAllLocationCheckBox.getValue() || MineTBClient.get(levelOptionListBox).split(" , ").length != 1){
						flag = false;
						alertMessage = alertMessage + "You can only select multiple values (or All), either for data or group by. \n";
					}
				}
			}
		}
		
		if(!flag){
			Window.alert("Fix following error(s) to continue. \n\n" + alertMessage);
		}
		
		return flag;
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
		
		time = TimeDimenstion.valueOf(MineTBClient.get(xAxisTimeListBox));
		if(time.toString().equalsIgnoreCase("Year")){
			yFrom = MineTBClient.get(xAxisYearFromListBox);
			yTo = MineTBClient.get(xAxisYearToListBox);
		}
		else{
			yFrom = MineTBClient.get(xAxisYearFromListBox);
			tFrom = MineTBClient.get(xAxisFromRange);
			tTo = MineTBClient.get(xAxisToRange);
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
     * Retrieve Data A/c to selection as ArrayList of GraphData   
     * 
     * (used when multiple data columns are selected )
     * 
     * @param result
     * @param splitSelect
     * @param timeArray
     * @return
     */
    public ArrayList<GraphData> getColumnDataAsGraphDataBySelection(String[][] result, String[] splitSelect, String[] timeArray){
    	ArrayList<GraphData> dataList = new ArrayList<GraphData>();
    	
    	for(String select : splitSelect){
    	    int index = 0;
    	    String selectArray[] = null;
    	    
    	    if(!yAxisSelectAllDataTables.getValue()){
    	    	String s = "";
    	    	for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
    	    		if(yAxisDataTableListBox.isItemSelected(i))
    	    			s = s + yAxisDataTableListBox.getItemText(i) + " , ";   
    	    	}
    	    	selectArray = s.split(" , ");
    	    }
    	    else{
    	    	String s = "";
    	    	for (int i = 0; i < yAxisDataTableListBox.getItemCount(); i++) {
		            s = s + yAxisDataTableListBox.getItemText(i) + " , ";   
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
    		
    		String loc[] = MineTBClient.get(levelOptionListBox).split(" , ");
    		
    		Number[] data = getColumnData(result,timeArray,loc[0],index);
			GraphData yAxisData = new GraphData(select, data);
			
			dataList.add(yAxisData);
    		
    	}
    	
    	return dataList;
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
    public ArrayList<GraphData> getColumnDataAsGraphDataByLoc(String[][] result, String[] locations, String[] timeArray, int index){
    	
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
    public Double[] getColumnData(String[][] data, String[] timeArray, String loc, int index) {
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

}
