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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
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
	
	ArrayList<GraphData> list = new ArrayList<GraphData>();
	String[] arrayT;
	String xLabel;
	String yLabel;
	String title;
	String subTitle;
	
	RadioButton columnRadioButton;
    RadioButton barRadioButton;
    RadioButton pieRadioButton;
    RadioButton lineRadioButton;
	
	public ReportPanel (String reportType, ArrayList<GraphData> dataList, String[] arrayT, String xLabel, String yLabel, String title, String subTitle)
	{	
		
		panel.setSize("100%", "100%");
		Chart chart = null;
		
		for(GraphData gd: dataList){  // Copying GraphData ArrayList
			
			GraphData gdata = new GraphData(gd.getTitle(), gd.getData());
			list.add(gdata);
		}
		
		this.arrayT = arrayT;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.title = title;
		this.subTitle = subTitle;
		
		if(reportType.equalsIgnoreCase("Simple reports")){   // if, simple reports... provide chart type radio buttons.
			
			i++;
			columnRadioButton = new RadioButton("radioGroup"+i, "Column");
	        barRadioButton = new RadioButton("radioGroup"+i, "Bar");
	        pieRadioButton = new RadioButton("radioGroup"+i, "Pie");
	        lineRadioButton = new RadioButton("radioGroup"+i, "Line");
	    	
	        Label label = new Label("Chart Type: ");
	        label.setStyleName("bold-green-text");
	        
	        horizontalPanel.add(label);
	        horizontalPanel.add(columnRadioButton);
	        horizontalPanel.add(barRadioButton);
	        horizontalPanel.add(pieRadioButton);
	        horizontalPanel.add(lineRadioButton);
	        
	        panel.add(horizontalPanel);
	        
			// if y-axis has more than 10 variable - Go for Stack Bar Chart
			if(dataList.size() > 10){
				
				chart = MoxieChartBuilder.createStackBarChart(arrayT,xLabel, yLabel, dataList, title, subTitle);
				barRadioButton.setValue(true);
			}
			
			// If there's only one variable on x-axis - Go for Column Chart
			else if(arrayT.length == 1){
				
				chart = MoxieChartBuilder.createColumnChart(arrayT,xLabel, yLabel, dataList, title, subTitle);
				columnRadioButton.setValue(true);
			}
			
			else {  // Line chart...
			
				chart = MoxieChartBuilder.createLineChart(arrayT, xLabel, yLabel, dataList, title, subTitle);
				lineRadioButton.setValue(true);
			}
			
			columnRadioButton.addClickHandler(this);
			lineRadioButton.addClickHandler(this);
			barRadioButton.addClickHandler(this);
			
		}
		else{ // if combinations charts...
			
			if(reportType.equalsIgnoreCase("Screening Summary"))
				chart = MoxieChartBuilder.createStackChartWithLine(arrayT, dataList.get(0).getData(), dataList.get(1).getData(), dataList.get(2).getData(), title, subTitle, xLabel, yLabel, dataList.get(2).getTitle(), dataList.get(0).getTitle(), dataList.get(1).getTitle());
			else if(reportType.equalsIgnoreCase("Sputum Submission Rates") || reportType.equalsIgnoreCase("Treatment Initiation Rates") || reportType.equalsIgnoreCase("Sputum Submission & Error Rates"))	
				chart = MoxieChartBuilder.createColumnChartWithLines(arrayT, xLabel, yLabel,dataList, title, subTitle);
			
			
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
		if(sender == columnRadioButton){
			
			if(columnRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createColumnChart(arrayT,xLabel, yLabel, list, title, subTitle);
				panel.add(chart);
				
			}
			
		}
		else if(sender == lineRadioButton){
			
			if(lineRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createLineChart(arrayT,xLabel, yLabel, list, title, subTitle);
				panel.add(chart);
				
			}
			
		}
		else if(sender == barRadioButton){
			
			if(barRadioButton.getValue()){
				
				Chart chart = MoxieChartBuilder.createBarChart(arrayT,xLabel, yLabel, list, title, subTitle);
				panel.add(chart);
				
			}
			
		}
		
	}
	
}
