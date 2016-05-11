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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Rabbia
 *
 */
public class VerticalTabPanel {

	VerticalPanel mainVerticalPanel = new VerticalPanel();
	VerticalPanel bodyPanel = new VerticalPanel();
	int index;
	
	public VerticalTabPanel() {
		mainVerticalPanel.setSize("100%", "100%");
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSize("100%", "100%");
		bodyPanel.setSize("100%", "100%");
		mainVerticalPanel.add(verticalPanel);
		mainVerticalPanel.add(bodyPanel);
		index = 0;
	}

	public void addTab(final Widget w, String Title){
		VerticalPanel panel = new VerticalPanel();
		panel.setSize("100%", "100%");
		Label label = new Label(Title);
		label.setSize("100%", "100%");
		HTML line = new HTML("<hr  style=\"width:100%;\" />");
		panel.add(label);
		panel.add(line);
		panel.add(w);
		bodyPanel.add(panel);
		w.setSize("100%", "100%");

		index++;

		if(!(index == 1))
			w.setVisible(false);
		
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(w.isVisible())
					w.setVisible(false);
				else
					w.setVisible(true);
				w.setSize("100%", "100%");
			}
	    });
		
	}
	
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}
	
}


