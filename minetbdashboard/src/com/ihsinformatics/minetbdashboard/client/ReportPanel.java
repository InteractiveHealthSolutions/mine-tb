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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.minetbdashboard.shared.GraphData;

/**
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
		
		for(GraphData gd: dataList){
			
			GraphData gdata = new GraphData(gd.getTitle(), gd.getData());
			list.add(gdata);
		}
		
		this.arrayT = arrayT;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.title = title;
		this.subTitle = subTitle;
		
		if(reportType.equalsIgnoreCase("Simple reports")){
			
			i++;
			columnRadioButton = new RadioButton("radioGroup"+i, "Column");
	        barRadioButton = new RadioButton("radioGroup"+i, "Bar");
	        pieRadioButton = new RadioButton("radioGroup"+i, "Pie");
	        lineRadioButton = new RadioButton("radioGroup"+i, "Line");
	    	
	        horizontalPanel.add(columnRadioButton);
	        horizontalPanel.add(barRadioButton);
	        horizontalPanel.add(pieRadioButton);
	        horizontalPanel.add(lineRadioButton);
	        
	        panel.add(horizontalPanel);
	        
			Chart chart = null;
			
			// if y-axis has more than 10 variable - Go for Stack Bar Chart
			if(dataList.size() > 10){
				
				chart = MoxieChartBuilder.createHorizontalStackBarChart(arrayT,xLabel, yLabel, dataList, title, subTitle);
				barRadioButton.setValue(true);
			}
			
			// If there's only one variable on x-axis - Go for Column Chart
			else if(arrayT.length == 1){
				
				chart = MoxieChartBuilder.createColumnChart(arrayT,xLabel, yLabel, dataList, title, subTitle);
				columnRadioButton.setValue(true);
			}
			
			else {
			
				chart = MoxieChartBuilder.createLineChart(arrayT, xLabel, yLabel, dataList, title, xLabel + "ly" + subTitle);
				lineRadioButton.setValue(true);
			}
			
			columnRadioButton.addClickHandler(this);
			lineRadioButton.addClickHandler(this);
			barRadioButton.addClickHandler(this);
			
			panel.add(chart);
		}
	}

	public VerticalPanel getComposite(){	
		return panel;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
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
