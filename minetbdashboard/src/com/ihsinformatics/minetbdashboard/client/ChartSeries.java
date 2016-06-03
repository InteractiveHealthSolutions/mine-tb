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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Rabbia
 *
 */
public class ChartSeries extends Composite{
	
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	private FlexTable seriesFlexTable = new FlexTable();
	
	private ArrayList<SeriesPanel> seriesPanelArray = new ArrayList<SeriesPanel>();
	private ArrayList<HTML> htmlArray = new ArrayList<HTML>();
	private ArrayList<Label> labelArray = new ArrayList<Label>();
	
	public ChartSeries (){
		
		mainVerticalPanel.add(seriesFlexTable);
		
		// Series 1
		addSeries();
		// Series 2
		addSeries();
		
	}
		
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}
	
	public ArrayList<SeriesPanel> getSeriesPanelArray() {
		return seriesPanelArray;
	}


	public void setSeriesPanelArray(ArrayList<SeriesPanel> seriesPanelArray) {
		this.seriesPanelArray = seriesPanelArray;
	}
	
	public void addSeries(){
		
		VerticalPanel seriesVerticalPanel = new VerticalPanel();
		// Series Heading and remove button...
		int row = seriesPanelArray.size()+1;
		HTML seriesLabel = new HTML("<font size=\"3\"> <b> Series #"+row+" </b></font>");
		htmlArray.add(seriesLabel);   // add to array
		seriesVerticalPanel.add(seriesLabel);
		// series option Panel
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("Option-Panel");
		SeriesPanel sp = new SeriesPanel();
		final Label removeLabel = new Label("- Remove");
		removeLabel.setStyleName("logout-button");
		removeLabel.getElement().setAttribute("align", "right");
		labelArray.add(removeLabel); // add to array
		vp.add(removeLabel);
		vp.add(sp.getComposite());
		seriesPanelArray.add(sp);  // add to array
		seriesVerticalPanel.add(vp);
		vp.setWidth("410px");
		
		// add to flextable...
		seriesFlexTable.setWidget(seriesFlexTable.getRowCount(), 0, seriesVerticalPanel);
		
		removeLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				int index = labelArray.indexOf(removeLabel);
				labelArray.remove(index);
				seriesPanelArray.remove(index);
				htmlArray.remove(index);
				
				seriesFlexTable.removeRow(index);
				
				for(int i = 0; i <htmlArray.size(); i++){
					
					HTML html = htmlArray.get(i);
					int row = i+1;
					
					html.setHTML("<font size=\"3\"> <b> Series #"+row+" </b></font>");
					
				}
				
			}
	       });
		
	}
	

}
