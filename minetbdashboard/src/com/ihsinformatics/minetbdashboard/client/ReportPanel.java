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

import org.moxieapps.gwt.highcharts.client.Chart;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.table.Table;
import com.ihsinformatics.minetbdashboard.shared.GraphData;

/**
 * Single Report Panel for Report Dialog Box
 * @author Rabbia
 *
 */
public class ReportPanel implements ClickHandler {
	
	static int i = 0;
	
	VerticalPanel panel = new VerticalPanel();
	HorizontalPanel horizontalPanel = new HorizontalPanel();
	
	String reportType;
	ArrayList<GraphData> list = new ArrayList<GraphData>();
	String[] arrayT;
	String xLabel;
	String yLabel;
	String title;
	String subTitle;
	String legendType;
	Boolean autoAdjust = false;
	
	// Simple Report Radio Buttons
	RadioButton columnSimpleRadioButton;
    RadioButton barSimpleRadioButton;
    RadioButton pieSimpleRadioButton;
    RadioButton lineSimpleRadioButton;
    RadioButton tableSimpleRadioButton;
    
    // Combination Report Radio Buttons
    RadioButton	tableDataRadioButton;
    RadioButton	graphRadioButton;
    
    // Custom Report Radio Buttons
    RadioButton columnCustomRadioButton;
    RadioButton barCustomRadioButton;
    RadioButton pieCustomRadioButton;
    RadioButton lineCustomRadioButton;
    RadioButton tableCustomRadioButton;
	
	public ReportPanel (String reportType, ArrayList<GraphData> dataList, String[] arrayT, String xLabel, String yLabel, String title, String subTitle, String legendType)
	{	
		
		panel.setSize("100%", "100%");
		Chart chart = null;
		
		this.reportType = reportType;
		
		for(GraphData gd: dataList){  // Copying GraphData ArrayList
			
			GraphData gdata = new GraphData(gd.getTitle(), gd.getData());
			list.add(gdata);
		}
		
		this.arrayT = arrayT;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.title = title;
		this.subTitle = subTitle;
		legendType = Character.toUpperCase(legendType.charAt(0)) + legendType.substring(1);
		this.legendType = legendType;
		
		if(reportType.equalsIgnoreCase("Simple reports")){   // if, simple reports... provide chart type radio buttons.
			
			i++;
			columnSimpleRadioButton = new RadioButton("radioGroup"+i, "Column");
	        barSimpleRadioButton = new RadioButton("radioGroup"+i, "Bar");
	        pieSimpleRadioButton = new RadioButton("radioGroup"+i, "Pie");
	        lineSimpleRadioButton = new RadioButton("radioGroup"+i, "Line");
	        tableSimpleRadioButton = new RadioButton("radioGroup"+i, "Table");
	    	
	        Label label = new Label("Chart Type: ");
	        label.setStyleName("bold-green-text");
	        
	        horizontalPanel.add(label);
	        horizontalPanel.add(columnSimpleRadioButton);
	        horizontalPanel.add(barSimpleRadioButton);
	        horizontalPanel.add(pieSimpleRadioButton);
	        horizontalPanel.add(lineSimpleRadioButton);
	        horizontalPanel.add(tableSimpleRadioButton);
	        
	        panel.add(horizontalPanel);
	        
			// if y-axis has more than 10 variable - Go for Stack Bar Chart
			if(dataList.size() > 10){
				
				chart = MoxieChartBuilder.createStackBarOnTimeChart(arrayT,xLabel, yLabel, dataList, title, subTitle, legendType);
				barSimpleRadioButton.setValue(true);
				autoAdjust = true;
			}
			
			// If there's only one variable on x-axis - Go for Column Chart
			else if(arrayT.length == 1){
				chart = MoxieChartBuilder.createColumnChart(arrayT,xLabel, yLabel, dataList, title, subTitle, legendType, false, autoAdjust);
				columnSimpleRadioButton.setValue(true);
				
			}
			
			else {  // Line chart...
			
				chart = MoxieChartBuilder.createLineChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType);
				lineSimpleRadioButton.setValue(true);
			}
			
			columnSimpleRadioButton.addClickHandler(this);
			lineSimpleRadioButton.addClickHandler(this);
			barSimpleRadioButton.addClickHandler(this);
			pieSimpleRadioButton.addClickHandler(this);
			tableSimpleRadioButton.addClickHandler(this);
			
		}
		else if(reportType.equalsIgnoreCase("Screening Summary") || reportType.equalsIgnoreCase("Sputum Submission Rates") || reportType.equalsIgnoreCase("Treatment Initiation Rates") || reportType.equalsIgnoreCase("Sputum Submission & Error Rates")){ // if other charts...
			
			i++;
	        graphRadioButton = new RadioButton("radioGroup"+i, "Graph");
	        tableDataRadioButton = new RadioButton("radioGroup"+i, "Table");
	    	
	        Label label = new Label("Chart Type: ");
	        label.setStyleName("bold-green-text");
	        
	        horizontalPanel.add(label);
	        horizontalPanel.add(graphRadioButton);
	        horizontalPanel.add(tableDataRadioButton);
	        
	        panel.add(horizontalPanel);
			
			if(reportType.equalsIgnoreCase("Screening Summary"))
				chart = MoxieChartBuilder.createStackChartWithLine(arrayT, dataList.get(0).getData(), dataList.get(1).getData(), dataList.get(2).getData(), title, subTitle, xLabel, yLabel, dataList.get(2).getTitle(), dataList.get(0).getTitle(), dataList.get(1).getTitle());
			else if(reportType.equalsIgnoreCase("Sputum Submission Rates") || reportType.equalsIgnoreCase("Treatment Initiation Rates") || reportType.equalsIgnoreCase("Sputum Submission & Error Rates"))	
				chart = MoxieChartBuilder.createColumnChartWithLines(arrayT, xLabel, yLabel,dataList, title, subTitle);
			
			graphRadioButton.setValue(true);
			
			graphRadioButton.addClickHandler(this);
			tableDataRadioButton.addClickHandler(this);
		}
		else {
			
			i++;
	        graphRadioButton = new RadioButton("radioGroup"+i, "Graph");
	        tableDataRadioButton = new RadioButton("radioGroup"+i, "Table");
	    	
	        Label label = new Label("Chart Type: ");
	        label.setStyleName("bold-green-text");
	        
	        horizontalPanel.add(label);
	        horizontalPanel.add(graphRadioButton);
	        horizontalPanel.add(tableDataRadioButton);
	        
	        panel.add(horizontalPanel);
			
			if(reportType.equalsIgnoreCase("Line"))
				chart = MoxieChartBuilder.createLineChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType);
			else if(reportType.equalsIgnoreCase("Column"))
				chart = MoxieChartBuilder.createColumnChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType, false, autoAdjust);
			else if(reportType.equalsIgnoreCase("Bar"))
				chart = MoxieChartBuilder.createBarChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType, false, autoAdjust);
			else if(reportType.equalsIgnoreCase("Stacked Column"))
				chart = MoxieChartBuilder.createColumnChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType, true, autoAdjust);
			else if(reportType.equalsIgnoreCase("Stacked Bar"))
				chart = MoxieChartBuilder.createBarChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType, true, autoAdjust);
			else if(reportType.equalsIgnoreCase("Stacked on Time Column"))
				chart = MoxieChartBuilder.createStackColumnOnTimeChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType);
			else if(reportType.equalsIgnoreCase("Stacked on Time Bar"))
				chart = MoxieChartBuilder.createStackBarOnTimeChart(arrayT, xLabel, yLabel, dataList, title, subTitle, legendType);
			else if(reportType.equalsIgnoreCase("Pie"))
				chart = MoxieChartBuilder.createPieChart(arrayT, xLabel, yLabel, dataList, title, subTitle);
			
			graphRadioButton.setValue(true);
			
			graphRadioButton.addClickHandler(this);
			tableDataRadioButton.addClickHandler(this);
			
		}
		
		panel.add(chart);
	}

	public VerticalPanel getComposite(){	
		return panel;
	}

	@Override
	public void onClick(ClickEvent event) {
		
		panel.clear();
		panel.add(horizontalPanel);
		
		Widget sender = (Widget) event.getSource();
		if(sender == columnSimpleRadioButton){
			
			if(columnSimpleRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createColumnChart(arrayT,xLabel, yLabel, list, title, subTitle, legendType, false, autoAdjust);
				panel.add(chart);
				
			}
			
		}
		else if(sender == lineSimpleRadioButton){
			
			if(lineSimpleRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createLineChart(arrayT,xLabel, yLabel, list, title, subTitle, legendType);
				panel.add(chart);
				
			}
			
		}
		else if(sender == barSimpleRadioButton){
			
			if(barSimpleRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createBarChart(arrayT,xLabel, yLabel, list, title, subTitle, legendType, false, autoAdjust);
				panel.add(chart);
				
			}
			
		}
		else if(sender == pieSimpleRadioButton){
			
			if(pieSimpleRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createPieChart(arrayT,xLabel, yLabel, list, title, subTitle);
				panel.add(chart);
				
			}
			
		}
		else if(sender == tableSimpleRadioButton){
			
			if(tableSimpleRadioButton.getValue()){
					
				ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
				chartLoader.loadApi(new Runnable() {

					@Override
					public void run() {
						// Create and attach the chart
						Table table = new Table();
						Label label = new Label(title + " - " + subTitle);
						panel.add(label);
						panel.add(table);
						label.setStyleName("ChartHeader");
						table.getElement().setAttribute("align", "center");
						MoxieChartBuilder.createTable("Simple Reports",table,list,arrayT, xLabel, yLabel, title, subTitle,legendType);
					}
				});
				
			}
			
		}
		else if(sender == graphRadioButton){
			
			if(graphRadioButton.getValue()){
				panel.add(horizontalPanel);
					
				if(reportType.equalsIgnoreCase("Screening Summary")){
					Chart chart = MoxieChartBuilder.createStackChartWithLine(arrayT, list.get(0).getData(), list.get(1).getData(), list.get(2).getData(), title, subTitle, xLabel, yLabel, list.get(2).getTitle(), list.get(0).getTitle(), list.get(1).getTitle());
					panel.add(chart);
				}
				else if(reportType.equalsIgnoreCase("Sputum Submission Rates") || reportType.equalsIgnoreCase("Treatment Initiation Rates") || reportType.equalsIgnoreCase("Sputum Submission & Error Rates")){	
					Chart chart = MoxieChartBuilder.createColumnChartWithLines(arrayT, xLabel, yLabel,list, title, subTitle);
					panel.add(chart);
				}
				else if(reportType.equalsIgnoreCase("Line")){
					Chart chart = MoxieChartBuilder.createLineChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Column")){
					Chart chart = MoxieChartBuilder.createColumnChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType, false, autoAdjust);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Bar")){
					Chart chart = MoxieChartBuilder.createBarChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType, false, autoAdjust);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Stacked Column")){
					Chart chart = MoxieChartBuilder.createColumnChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType, true, autoAdjust);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Stacked Bar")){
					Chart chart = MoxieChartBuilder.createBarChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType, true, autoAdjust);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Stacked on Time Column")){
					Chart chart = MoxieChartBuilder.createStackColumnOnTimeChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Stacked on Time Bar")){
					Chart chart = MoxieChartBuilder.createStackBarOnTimeChart(arrayT, xLabel, yLabel, list, title, subTitle, legendType);
					panel.add(chart);
				}else if(reportType.equalsIgnoreCase("Pie")){
					Chart chart = MoxieChartBuilder.createPieChart(arrayT, xLabel, yLabel, list, title, subTitle);
					panel.add(chart);
				}	
			}
			
		}
		else if(sender == tableDataRadioButton){
			
			if(tableDataRadioButton.getValue()){
				
				ChartLoader chartLoader = new ChartLoader(ChartPackage.TABLE);
				chartLoader.loadApi(new Runnable() {

					@Override
					public void run() {
						// Create and attach the chart
						Table table = new Table();
						Label label = new Label(title + " - " + subTitle);
						panel.add(label);
						panel.add(table);
						label.setStyleName("ChartHeader");
						table.getElement().setAttribute("align", "center");
						MoxieChartBuilder.createTable(reportType,table,list,arrayT, xLabel, yLabel, title, subTitle,legendType);
					}
				});
				
			}
			
		}
		
	}
	
}
