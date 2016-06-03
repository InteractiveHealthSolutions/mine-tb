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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Dynamic Graphs Composite
 * 
 * @author Rabbia
 *
 */
public class DynamicGraphs implements ChangeHandler{
	
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	private HTML dynamicGraphLabel = new HTML("<font size=\"6\"> Custom Charts </font>");
	private HTML dynamicGraphSubheading = new HTML("Select your options to build Chart Report <br> <br>");
	
	private VerticalPanel bodyVerticalPanel = new VerticalPanel();
	private ListBox chartType = new ListBox();
	private VerticalPanel optionVerticalPanel = new VerticalPanel();
	
	public DynamicGraphs(){
		
		mainVerticalPanel.setSize("100%", "100%");
		
		dynamicGraphLabel.setStyleName("MineTBHeader");
		mainVerticalPanel.add(dynamicGraphLabel);
		dynamicGraphSubheading.setStyleName("MineTBHeader");
		mainVerticalPanel.add(dynamicGraphSubheading);
		
		mainVerticalPanel.add(bodyVerticalPanel);
		bodyVerticalPanel.setSize("100%", "100%");
		bodyVerticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		bodyVerticalPanel.add(chartType);
		chartType.setWidth("300px");
		chartType.clear();
		chartType.addItem("-- Select Chart Type ---");
		chartType.addItem("Column Chart", "Column");
		chartType.addItem("Bar Chart", "Bar");
		chartType.addItem("Pie Chart", "Pie");
		chartType.addItem("Line Chart", "Line");
		chartType.addItem("Combination Chart", "Combination");
		
		bodyVerticalPanel.add(optionVerticalPanel);
		bodyVerticalPanel.setCellVerticalAlignment(optionVerticalPanel, HasVerticalAlignment.ALIGN_TOP);
		
		chartType.addChangeHandler(this);
		
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
		if (source == chartType) {
			optionVerticalPanel.clear();
			String chartT = MineTBClient.get(chartType);
			
			DynamicGraphsComposite lineComposite = new DynamicGraphsComposite(chartT);
			optionVerticalPanel.add(lineComposite.getComposite());
			Window.scrollTo(0, 320);
				
		}
	}
	


}
