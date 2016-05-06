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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.MineTB;

public class MainMenuComposite extends Composite implements ClickHandler
{
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	private Grid					headerGrid						= new Grid(1,3);

	private static 	VerticalPanel	mainVerticalPanel				= new VerticalPanel();
	VerticalPanel 					topVerticalPanel 				= new VerticalPanel ();
	VerticalPanel 					footerPanel 					= new VerticalPanel ();
	VerticalPanel 					midPanel 						= new VerticalPanel ();

	private static LoadingWidget 	loading 						= new LoadingWidget();
		
	private Label 					loggedInLabel 					= new Label("Logged in as: ");
	private Label 					userLoggedInLabel 				= new Label();
	private Label					summaryReportLabel				= new Label("Summary Report");
	private Label					reportingDashboardLabel			= new Label("Reporting Dashboard");
	private Label					separatorLabel					= new Label("|");
	private Label 					logoutLabel 					= new Label("Logout");
	private Label 					copyrightLabel 					= new Label("Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.");
	
	@SuppressWarnings("deprecation")
	public MainMenuComposite ()
	{	
		initWidget (topVerticalPanel);
		
		topVerticalPanel.setHorizontalAlignment (HasHorizontalAlignment.ALIGN_CENTER);
		topVerticalPanel.setSize ("100%", "100%");
		
		headerGrid.setSize ("100%", "100%");
		
		topVerticalPanel.add(headerGrid);
		
		Grid grid = new Grid(1,2);
		grid.setWidget(0, 0, loggedInLabel);
		loggedInLabel.setStyleName("orange-text");
		grid.setWidget(0, 1, userLoggedInLabel);
		String userName = Cookies.getCookie("UserName");
		userLoggedInLabel.setText(userName);
		userLoggedInLabel.setStyleName("bold-green-text");
		
		headerGrid.setWidget(0, 0, grid);
		
		logoutLabel.setStyleName("logout-button");
		headerGrid.setWidget(0, 2, logoutLabel);
		
		grid.getCellFormatter().setWidth(0, 0, "80px");
		grid.getCellFormatter().setWidth(0, 1, "50px");
		
		HTML line = new HTML("<hr  style=\"width:100%;\" />");
		topVerticalPanel.add(line);
		
		Grid grid1 = new Grid(1,3);
		grid1.setWidget(0, 0, summaryReportLabel);
		summaryReportLabel.setStyleName("current-page");
		grid1.setWidget(0, 1, separatorLabel);
		separatorLabel.setStyleName("orange-text");
		grid1.setWidget(0, 2, reportingDashboardLabel);
		reportingDashboardLabel.setStyleName("different-page");
		grid1.getElement().setAttribute("align", "center");
		
		headerGrid.setWidget(0, 1, grid1);
		
		headerGrid.getCellFormatter().setWidth(0, 2, "280px");
		
		topVerticalPanel.add(mainVerticalPanel);
		mainVerticalPanel.setSize("100%", "100%");
		
		SummaryReport summaryReport = new SummaryReport();
		mainVerticalPanel.add(summaryReport.getComposite());
		
		topVerticalPanel.add(footerPanel);
		footerPanel.setSize("100%", "100%");
		
		createFooter();
		
		reportingDashboardLabel.addClickHandler(this);
		summaryReportLabel.addClickHandler(this);
		logoutLabel.addClickHandler(this);
	
		
	}
	
	public void createFooter(){
		
		HTML line1 = new HTML("<br> <hr  style=\"width:100%;\" />");
		footerPanel.add(line1);
		
		Grid mainGrid = new Grid(1,2);
		footerPanel.add(mainGrid);
		
		mainGrid.setSize("100%", "100%");
		
		mainGrid.setWidget(0,0, copyrightLabel);
		copyrightLabel.setStyleName("green-text");
		
		Grid grids = new Grid(1, 2);
		mainGrid.setWidget(0,1,grids);

		Label lastUpdatedLabel = new Label("Last updated on: ");
		grids.setWidget(0, 0, lastUpdatedLabel);
		lastUpdatedLabel.setStyleName("orange-text");
		final Label dateLabel = new Label();
		grids.setWidget(0, 1, dateLabel);
		dateLabel.setStyleName("bold-green-text");

		grids.getElement().setAttribute("align", "right");

		HTML line2 = new HTML("<hr  style=\"width:100%;\" /> <br> ");
		footerPanel.add(line2);
		
		String query = "SELECT * FROM minetb_dw.meta_data where tag = 'last_updated';";
		try {
			service.getTableData(query.toString(),
					new AsyncCallback<String[][]>() {
						@Override
						public void onSuccess(
								final String[][] result) {

							dateLabel.setText(result[0][1]);

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
	
	public VerticalPanel getComposite(){
		return topVerticalPanel;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		load(true);
		if (sender == logoutLabel) {
			
			logout();
		}
		else if (sender == summaryReportLabel){
			
			mainVerticalPanel.clear();
			
			SummaryReport summaryReport = new SummaryReport();
			mainVerticalPanel.add(summaryReport.getComposite());
			
			summaryReportLabel.setStyleName("current-page");
			reportingDashboardLabel.setStyleName("different-page");
			
		}
		else if (sender == reportingDashboardLabel){
			
			mainVerticalPanel.clear();
			
			ReportingDashboard reportingDashboard = new ReportingDashboard();
			mainVerticalPanel.add(reportingDashboard.getComposite());
			
			summaryReportLabel.setStyleName("different-page");
			reportingDashboardLabel.setStyleName("current-page");
			
		}
		
		load(false);
		Window.scrollTo(0, 220);
	}
	
	/**
	 * Display/Hide main panel and loading widget
	 * 
	 * @param status
	 */
	public void load(boolean status) {
		topVerticalPanel.setVisible(!status);
		if (status)
			loading.show();
		else
			loading.hide();
	}
	
	/**
	 * Log out the application
	 */
	public static void logout() {
		try {
			setCookies("", "", "");
			Window.Location.reload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set browser cookies
	 */
	public static void setCookies(String userName, String passCode,
			String password) {
		Cookies.removeCookie("UserName");
		Cookies.removeCookie("Pass");
		Cookies.removeCookie("LoginTime");
		Cookies.removeCookie("SessionLimit");

		MineTB.setCurrentUser(userName);
		MineTB.setPassCode(passCode);

		if (!userName.equals(""))
			Cookies.setCookie("UserName", MineTB.getCurrentUser());
		if (!password.equals(""))
			Cookies.setCookie("Password", MineTB.getPassCode());
		if (!passCode.equals("")) {
			Cookies.setCookie("Pass", MineTB.getPassCode());
			Cookies.setCookie("LoginTime", String.valueOf(new Date().getTime()));
			Cookies.setCookie("SessionLimit",
					String.valueOf(new Date().getTime() + MineTB.sessionLimit));
		}
	}
	
}