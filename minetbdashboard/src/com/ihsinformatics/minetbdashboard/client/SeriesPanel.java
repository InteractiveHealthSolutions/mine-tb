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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;

/**
 * @author Rabbia
 *
 */
public class SeriesPanel extends Composite implements ChangeHandler{
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	
	static int i = 0;
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	private VerticalPanel seriesTypePanel = new VerticalPanel();
	
	private Grid seriesTypeGrid = new Grid(1,2);
	private Label seriesTypeLabel = new Label("Series Type:");
	private ListBox seriesTypeListBox = new ListBox();
	
	private Label formLabel = new Label("Select Form:");
	private ListBox formListBox = new ListBox();
	
	private Label dataTableLabel = new Label("Select Data:");
	private CheckBox selectAllDataTables = new CheckBox("Select All");
	private ListBox dataTableListBox = new ListBox();
	
	private RadioButton primaryRadioButton;
	private RadioButton secondaryRadioButton;
	
	private Label axisYLabel = new Label("Axis Label: ");
	private TextBox axislabelTextBox = new TextBox();
	
	public SeriesPanel (){
		i++;
		id = i;
		
		mainVerticalPanel.add(seriesTypeGrid);
		
		seriesTypeGrid.setWidget(0, 0, seriesTypeLabel);
		
		seriesTypeListBox.addItem("Column");
		seriesTypeListBox.addItem("Bar");
		seriesTypeListBox.addItem("Line");
		seriesTypeListBox.addItem("Pie");
		
		seriesTypeGrid.setWidget(0, 1, seriesTypeListBox);
		
		fillSeriesOptions();
		
		mainVerticalPanel.add(seriesTypePanel);
		
		seriesTypeListBox.setWidth("250px");
		seriesTypeListBox.addChangeHandler(this);
		formListBox.addChangeHandler(this);
		
	}
	
	public String getSeriesType(){
		
		return MineTBClient.get(seriesTypeListBox);
	}
	
	public String getFormName(){
		
		return MineTBClient.get(formListBox);
	}
	
	public String getTableColumn(){
		
		String value = "";
		
		if(MineTBClient.get(seriesTypeListBox).equalsIgnoreCase("Column") || MineTBClient.get(seriesTypeListBox).equalsIgnoreCase("Bar")){
			if(selectAllDataTables.getValue()){
				for(int i=0; i<dataTableListBox.getItemCount(); i++){
					value = value + dataTableListBox.getValue(i) + " , ";
				}
			}
			else{
				
				for(int i=0; i<dataTableListBox.getItemCount(); i++){
					if(dataTableListBox.isItemSelected(i))
						value = value + dataTableListBox.getValue(i) + " , ";
				}
			}
			
			value = value.substring(0,value.length()-3);
		}
		else
			value = MineTBClient.get(dataTableListBox);
		
		return value;
	}
	
	public String getColumnTitles(){
		
		String value = "";
		
		if(MineTBClient.get(seriesTypeListBox).equalsIgnoreCase("Column") || MineTBClient.get(seriesTypeListBox).equalsIgnoreCase("Bar")){
			if(selectAllDataTables.getValue()){
				for(int i=0; i<dataTableListBox.getItemCount(); i++){
					value = value + dataTableListBox.getItemText(i) + " , ";
				}
			}
			else{
				
				for(int i=0; i<dataTableListBox.getItemCount(); i++){
					if(dataTableListBox.isItemSelected(i))
						value = value + dataTableListBox.getItemText(i) + " , ";
				}
			}
			
			value = value.substring(0,value.length()-3);
		}
		else
			value = MineTBClient.get(dataTableListBox);
		
		return value;
	}
	
	
	public void fillSeriesOptions(){
		
		String seriesType = MineTBClient.get(seriesTypeListBox);
		
		seriesTypePanel.clear();
		
		if(seriesType.equalsIgnoreCase("Pie")){
			
		}
		else if(seriesType.equalsIgnoreCase("Column") || seriesType.equalsIgnoreCase("Bar") || seriesType.equalsIgnoreCase("Line")){
			seriesTypePanel.add(new HTML("<b> Data Dimension </b>"));
			
			FlexTable flexTable = new FlexTable();
			seriesTypePanel.add(flexTable);
			
			flexTable.setWidget(0, 0, formLabel);
			flexTable.setWidget(0, 1, formListBox);
			
			flexTable.setWidget(1, 0, dataTableLabel);
			VerticalPanel vp = new VerticalPanel();
			vp.add(selectAllDataTables);
			vp.add(dataTableListBox);
			
			selectAllDataTables.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					
				   if(selectAllDataTables.getValue())
					   dataTableListBox.setEnabled(false);
				   else
					   dataTableListBox.setEnabled(true); 
				   
				}
		       });
			
			formListBox.clear();
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
							formListBox.addItem(result[i][1], result[i][2]);
						}
						fillYAxisDataTables();
					}
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			formListBox.setWidth("250px");
			dataTableListBox.setWidth("250px");
			selectAllDataTables.setValue(true);
			dataTableListBox.setEnabled(false);
			dataTableListBox.setHeight("100px");
			dataTableListBox.setMultipleSelect(true);
			
			HorizontalPanel hp = new HorizontalPanel();
			
			primaryRadioButton = new RadioButton("axis button -"+i, "Primary Axis");
			secondaryRadioButton = new RadioButton("axis button -"+i, "Secondary Axis");
			
			Label label = new Label("Label Axis: ");
			label.setStyleName("");
			hp.add(label);
			hp.add(primaryRadioButton);
			hp.add(secondaryRadioButton);
			
			primaryRadioButton.setValue(true);
				
			vp.add(hp);
			
			flexTable.setWidget(1, 1, vp);
			
			flexTable.setWidget(2, 0, axisYLabel);
			flexTable.setWidget(2, 1, axislabelTextBox);
		}
	}
	
	public void fillYAxisDataTables(){
		dataTableListBox.clear();
		String query = "select d2.name, d2.data_table from data_forms d1, data_forms_tables d2 where d1.id = d2.data_form_id and d1.table_name = '"+MineTBClient.get(formListBox).toLowerCase()+"';";
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
						dataTableListBox.addItem(result[i][0], result[i][1]);
					}
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}
	
	@Override
	public void onChange(ChangeEvent event) {
		Object source = event.getSource();
		 if (source == seriesTypeListBox){
			 fillSeriesOptions();
		 }
		 else if (source == formListBox){
			 fillYAxisDataTables();
		 }
		
	}
	

}
