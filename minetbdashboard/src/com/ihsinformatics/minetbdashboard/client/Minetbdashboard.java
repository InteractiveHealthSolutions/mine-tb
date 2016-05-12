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


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage; 
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.InfoType;
import com.ihsinformatics.minetbdashboard.shared.MineTB;

/**
 * Entry point of Project...
 * -> Display Login Page
 * -> Redirects to Main Dashboard if session is active. 
 * -> Redirects to Main Dashboard after verifying the credentials.
 * 
 * @author Rabbia
 */

public class Minetbdashboard implements EntryPoint, ClickHandler,KeyDownHandler {
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	private static LoadingWidget loading = new LoadingWidget();
	
	private static RootPanel rootPanel;
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	// Widgets for login Page -> Login Panel
	private VerticalPanel loginPanel = new VerticalPanel();
	private FlexTable loginHeaderFlexTable = new FlexTable();
	private FlexTable loginFlexTable = new FlexTable();
	private HTML formHeadingLabel = new HTML("<font size=\"4\"> Welcome to Reporting Dashboard. Please login to proceed. </font> ");
	private Label userNameLabel = new Label("User ID:");
	private Label passwordLabel = new Label("Password:");
	private TextBox userTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private Button loginButton = new Button("Login");
	
	// Widgets for login Page -> footer Panel
	private VerticalPanel footerPanel = new VerticalPanel();
	private FlexTable footerFlexTable = new FlexTable();
	private Image irdLogoImage = new Image("images\\irdSaLogo.png");
	private Image aurumLogoImage = new Image("images\\aurumLogo.png");
	private Image openmrsLogoImage = new Image("images\\openmrsLogo.png");
	private Image androidLogoImage = new Image("images\\androidLogo.png");
		
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		rootPanel = RootPanel.get();
		rootPanel.setSize ("100%", "100%");
		rootPanel.setStyleName("rootPanel");
		rootPanel.add (mainVerticalPanel);
		mainVerticalPanel.setSize ("100%", "100%");
		
		loginButton.addClickHandler(this);
		passwordTextBox.addKeyPressHandler(new KeyPressHandler() {   // try login on Enter Key Pressed...
			public void onKeyPress(KeyPressEvent event) {
				boolean enterPressed = event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER;
				if (enterPressed) {
					loginButton.click();
				}
			}
		});
		
		if(activeSession())   // if active session is present -> redirect to dashboard
			createDashboard();
		else
			createLoginPage();   // else login Page
	}
	
	/**
	 * 
	 * 	Creates Login Page 
	 * 	-> Login Panel + Footer Panel
	 * 
	 */
	private void createLoginPage(){
		
		loginPanel.setSize("100%", "100%");
		
		loginHeaderFlexTable.setWidget(0, 1, formHeadingLabel);
		loginHeaderFlexTable.getRowFormatter().addStyleName(0, "MineTBHeader");
		
		loginPanel.add(loginHeaderFlexTable);
		loginPanel.setCellHorizontalAlignment(loginHeaderFlexTable,HasHorizontalAlignment.ALIGN_CENTER);

		loginFlexTable.setWidget(1, 0, userNameLabel);
		loginFlexTable.setWidget(1, 1, userTextBox);
		loginFlexTable.setWidget(2, 0, passwordLabel);
		loginFlexTable.setWidget(2, 1, passwordTextBox);
		loginFlexTable.setWidget(3, 1, loginButton);
		
		loginPanel.add(loginFlexTable);
		loginPanel.setCellHorizontalAlignment(loginFlexTable,HasHorizontalAlignment.ALIGN_CENTER);
		
		HTML line = new HTML("<br> <hr  style=\"width:100%;\" />");
		loginPanel.add(line);
		
		footerPanel.setSize("100%", "100%");
		footerFlexTable.setWidget(0, 0, openmrsLogoImage);
		footerFlexTable.setWidget(0, 1, irdLogoImage);
		footerFlexTable.setWidget(0, 2, aurumLogoImage);
		footerFlexTable.setWidget(0, 3, androidLogoImage);

		footerPanel.add(footerFlexTable);
		footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		footerPanel.setCellHorizontalAlignment(footerFlexTable,
				HasHorizontalAlignment.ALIGN_CENTER);
		
		loginPanel.add(footerPanel);
		
		String userName = Cookies.getCookie("UserName");
		userTextBox.setText(userName);
		
		mainVerticalPanel.clear();
		mainVerticalPanel.add(loginPanel);
		mainVerticalPanel.add(footerPanel);
		
	}
	
	@Override
	public void onKeyDown(KeyDownEvent event) {
		Object source = event.getSource();
		if (source == passwordTextBox || source == userTextBox) {
			doLogin();
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		load(true);
		if (sender == loginButton) {
			doLogin();
		} 		
	}
	
	/**
	 * Display/Hide main panel and loading widget
	 * 
	 * @param status
	 */
	public void load(boolean status) {
		mainVerticalPanel.setVisible(!status);
		if (status)
			loading.show();
		else
			loading.hide();
	}
	
	/**
	 * 
	 * Checks User Credentials and forward to next page accordingly
	 */
	private void doLogin() {
		// Check for empty fields
		if (MineTBClient.get(userTextBox).equals("") || MineTBClient.get(passwordTextBox).equals("")) {
				Window.alert(CustomMessage.getErrorMessage(ErrorType.EMPTY_DATA_ERROR));
				return;
		}
		try {
			service.authenticate(MineTBClient.get(userTextBox), MineTBClient.get(passwordTextBox),
					new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							if (result) {   // on successful login
								Window.alert(CustomMessage.getInfoMessage(InfoType.ACCESS_GRANTED));
								String username = MineTBClient.get(userTextBox);
								String passcode = String.valueOf(MineTBClient.getSimpleCode(MineTBClient.get(passwordTextBox).substring(0, 3)));
								String password = MineTBClient.get(passwordTextBox);
								setCookies(username,passcode,password);   // Create Cookie
								createDashboard();   					// Redirect from login Page
							} else {
								Window.alert(CustomMessage.getErrorMessage(ErrorType.AUTHENTICATION_ERROR));
								load(false);
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 *  Display main dasboard
	 */
	private void createDashboard() {
		
		String userName = Cookies.getCookie("UserName");
		String passCode = Cookies.getCookie("Pass");
		String password = Cookies.getCookie("Password");
		
		setCookies(userName, passCode, password);
		
		try{
			service.setCurrentUser(userName, new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						
						mainVerticalPanel.clear ();
						MainMenuComposite mainMenu = new MainMenuComposite ();
						mainVerticalPanel.add (mainMenu);
						
						load(false);
						Window.scrollTo(0, 220);
						
					}
	
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						load(false);
					}
				});
		}
		catch(Exception e){
			e.printStackTrace();
			load(false);
		}
	}
	
	
	/**
	 * Set browser cookies
	 */
	public static void setCookies(String userName, String passCode, String password) {
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
			Cookies.setCookie("SessionLimit", String.valueOf(new Date().getTime() + MineTB.sessionLimit));
		}
	}

	/**
	 * To renew browsing session
	 * 
	 * @return true if renew was successful
	 */
	public static boolean renewSession() {
		String passcode = Window.prompt(CustomMessage.getErrorMessage(ErrorType.SESSION_EXPIRED) + "\n Please enter first 4 characters of your password to renew session.","");
		if (MineTBClient.verifyClientPasscode(passcode)) {
			Window.alert(CustomMessage.getInfoMessage(InfoType.SESSION_RENEWED));
			return true;
		}
		Window.alert(CustomMessage.getErrorMessage(ErrorType.AUTHENTICATION_ERROR));
		return false;
	}

	/**
	 * Check for active Session
	 * 
	 * @return true if renew was successful
	 */
	public static boolean activeSession() {
		String userName;
		String passCode;
		String sessionLimit;
		try {
			// Try to get Cookies
			userName = Cookies.getCookie("UserName");
			passCode = Cookies.getCookie("Pass");
			sessionLimit = Cookies.getCookie("SessionLimit");
			
			if (userName == null || passCode == null || sessionLimit == null)
				throw new Exception();
			
			if (new Date().getTime() > Long.parseLong(sessionLimit))
				if (!renewSession())
					throw new Exception();
			
		  return true;	
		
		} catch (Exception e) {
			return false;
		}
	}
	
}
