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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * Dialog Box for  Dynamic Reports 
 * @author Rabbia
 *
 */


class DynamicReportDialogBox extends WindowBox {

	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel headerPanel = new HorizontalPanel();
	VerticalPanel chartPanel = new VerticalPanel();
	TabPanel tabPanel = new TabPanel();
	
	Label subheadingLabel = new Label();

	
    public DynamicReportDialogBox(String title, String timeline, VerticalPanel composite) {
    	
       setText(title); // Title
       
       int width = Window.getClientWidth() - 20;
       setWidth(width+"px");
       mainPanel.setSize("100%", "100%");
       chartPanel.setSize("100%", "100%");
       
       setModal(false);				//parent screen remain active
       setAnimationEnabled(true);
       setGlassEnabled(false);
       
       setPopupPosition(0, 350);

       mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
       
       Button ok = new Button("X");
       ok.setStyleName("closeButton");
       ok.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 DynamicReportDialogBox.this.hide();    //close
		}
       });
       
       Button min = new Button("-");
       min.setStyleName("closeButton");
       min.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 DynamicReportDialogBox.this.setPopupPosition(0, 700);   //minimize
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
       
       subheadingLabel.setText(timeline);       // subheading
       subheadingLabel.setStyleName("bold-green-text");
       subheadingLabel.getElement().setAttribute("align", "left");
       
       headerPanel.add(subheadingLabel);
       headerPanel.add(buttonPanel);
       headerPanel.setSize("100%", "100%");
       
       mainPanel.add(headerPanel);
       
       ScrollPanel scrollPanel = new ScrollPanel();
       scrollPanel.setSize("100", "100");
       scrollPanel.add(chartPanel);
       
       chartPanel.add(composite);
       
       mainPanel.add(scrollPanel);
       
       setWidget(mainPanel);
       
    }
    
    
    public VerticalPanel getComposite(VerticalPanel composite){
    
    	
    	return mainPanel;
    }
   
    
 }
