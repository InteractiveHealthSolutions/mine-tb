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
import java.util.HashSet;

import org.easymock.internal.Results;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.DataType;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.GraphData;
import com.ihsinformatics.minetbdashboard.shared.Parameter;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;

class ReportDialogBox extends WindowBox {
	
	private static ServerServiceAsync service = GWT.create(ServerService.class);


	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel headerPanel = new HorizontalPanel();
	VerticalPanel chartPanel = new VerticalPanel();
	TabPanel tabPanel = new TabPanel();
	
	Label subheadingLabel = new Label();
	
	String report;
	String time;
	String loc;
	String yearFrom;
	String yearTo;
	String to;
	String from;
	String reportType;
	
	
    public ReportDialogBox(String reportType, String report, String time, String loc, String yearFrom, String yearTo, String from, String to) {
    	
		this.report = report;
		this.time = time;
		this.loc = loc;
		this.yearFrom = yearFrom;
		this.yearTo = yearTo;
		this.to = to;
		this.from = from;
		this.reportType = reportType;
    	
       setText(report);
       
       int width = Window.getClientWidth() - 20;
       
       setWidth(width+"px");
       mainPanel.setSize("100%", "100%");
       chartPanel.setSize("100%", "100%");
       
       setModal(false);
       setAnimationEnabled(true);
       setGlassEnabled(false);
       setResizable(true);
       setCloseIconVisible(true);
       
       setPopupPosition(0, 200);

       mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
       
       Button ok = new Button("X");
       ok.setStyleName("closeButton");
       ok.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 ReportDialogBox.this.hide();
		}
       });
       
       Button min = new Button("-");
       min.setStyleName("closeButton");
       min.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			 ReportDialogBox.this.setPopupPosition(0, 700);
		}
       });

       Button max = new Button("O");
       max.setStyleName("closeButton");
       max.addClickHandler(new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			setPopupPosition(0, 200);
		}
       });
       
       
       Grid buttonPanel = new Grid(1,3);
       buttonPanel.setWidget(0, 0, min);
       buttonPanel.setWidget(0, 1, max);
       buttonPanel.setWidget(0, 2, ok);
       buttonPanel.getElement().setAttribute("align", "right");
       
       String timeline = "";
       
       if(time.equalsIgnoreCase("year")){
    	   timeline = " (" + yearFrom + " - " + yearTo +")";
       } else if (time.equals("quarter")){
    	   timeline = " (Q" + from + " -  Q" + to + " for year " + yearFrom + ")";
       } else if (time.equals("month")){
    	   timeline = " (M" + from + " -  M" + to + " for year " + yearFrom + ")";
       } else if (time.equals("week")){
    	   timeline = " (W" + from + " -  W" + to + " for year " + yearFrom + ")";
       }
       
       String labelText = " by "
				+ Character.toUpperCase(loc.charAt(0)) + loc.substring(1)
				+ " per " + Character.toUpperCase(time.charAt(0))
				+ time.substring(1);
       
       subheadingLabel.setText(labelText + timeline);
       subheadingLabel.setStyleName("bold-green-text");
       subheadingLabel.getElement().setAttribute("align", "left");
       
       headerPanel.add(subheadingLabel);
       headerPanel.add(buttonPanel);
       headerPanel.setSize("100%", "100%");
       
       mainPanel.add(headerPanel);
       
       fillChartPanel();
       
       ScrollPanel scrollPanel = new ScrollPanel();
       scrollPanel.setSize("100", "100");
       scrollPanel.add(chartPanel);
       
       mainPanel.add(scrollPanel);
       
       setWidget(mainPanel);
    }
    
    
    private void fillChartPanel(){
    
    	StringBuilder query = getQuery();
    	drawCharts(query);
    	
    }
    
    private StringBuilder getQuery(){
    	
    	StringBuilder query = new StringBuilder();
    	Parameter[] params = null;
    	
    	if(report.equalsIgnoreCase("screening")){
    		query.append("select " + time + ", ");
    		query.append(loc + ", ");
    		query.append("sum(screened) as screened, sum(suspects) as suspects, sum(non_suspects) as non_suspects from fact_screening ");
    		query.append(getFilter(params, ""));
    		query.append(" group by " + time + ", " + loc);
    		query.append(" order by " + time + ", " + loc);
    	}
    	
    	return query;
    }
    
    private void drawCharts(StringBuilder query){
    	
    	try {
			service.getTableData(query.toString(),new AsyncCallback<String[][]>() {
						@Override
						public void onSuccess(final String[][] result) {

							chartPanel.add(tabPanel);
							tabPanel.setSize("100%", "100%");
							
							String[] timeArray = getTimeArray();
							String xLabel = Character.toUpperCase(time.charAt(0)) + time.substring(1);
							String[] locations = getUniqueValues(result, 1);
							String[] arrayT = timeArray;

							if (time.equals("month")) {
								for (int i = 0; i < timeArray.length; i++) {
									String monthString;
									switch (Integer.parseInt(timeArray[i])) {
									case 1:
										monthString = "Jan";
										break;
									case 2:
										monthString = "Feb";
										break;
									case 3:
										monthString = "Mar";
										break;
									case 4:
										monthString = "Apr";
										break;
									case 5:
										monthString = "May";
										break;
									case 6:
										monthString = "Jun";
										break;
									case 7:
										monthString = "Jul";
										break;
									case 8:
										monthString = "Aug";
										break;
									case 9:
										monthString = "Sep";
										break;
									case 10:
										monthString = "Oct";
										break;
									case 11:
										monthString = "Nov";
										break;
									case 12:
										monthString = "Dec";
										break;
									default:
										monthString = "Invalid month";
										break;
									}
									arrayT[i] = monthString;
								}
							}
							
							String yearString = "";

							if (time.equalsIgnoreCase("year"))
									yearString = " (" + yearFrom + " - " + yearTo + ")";
							else
								yearString = " (" + yearFrom + ")";
							
							timeArray = getTimeArray();
							
							if(report.equalsIgnoreCase("screening"))
								drawScreening(result, locations, timeArray, arrayT, xLabel, yearString);
								
							tabPanel.selectTab(0);

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
    
    public void drawScreening(String[][] result, String[] locations, String[] timeArray, final String[] arrayT, final String xLabel, final String yearString){
    	
    	// Add Total Screened Chart
    	ArrayList<GraphData> dataList = getColumnDataAsGraphData(result, locations, timeArray,2);
    	String yLabel = "Screened";
    	ReportPanel screenedPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Screened", yearString);
    	tabPanel.add(screenedPanel.getComposite(), yLabel);
    	
		dataList.clear();
		
		// Add Presumptive and High Risk Chart
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,3);
    	yLabel = "Presumptive and High Risk";
    	ReportPanel suspectPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Presumptives and High Risks",yearString);
    	tabPanel.add(suspectPanel.getComposite(), yLabel);    	
    	
    	dataList.clear();
    	
    	// Add Non-Suspects
    	dataList = getColumnDataAsGraphData(result, locations, timeArray,4);
    	yLabel = "Non-Suspects";
    	ReportPanel nonSuspectPanel = new ReportPanel(reportType, dataList, arrayT, xLabel, yLabel, "Total Non-Suspects",yearString);
    	tabPanel.add(nonSuspectPanel.getComposite(), yLabel);   	

    }
    
    public ArrayList<GraphData> getColumnDataAsGraphData(String[][] result, String[] locations, String[] timeArray, int index){
    	
    	ArrayList<GraphData> dataList = new ArrayList<GraphData>();
    	
    	for (String loc : locations) {

			Number[] data = getColumnData(result,timeArray, loc, index);
			GraphData yAxisData = new GraphData(loc, data);
			
			dataList.add(yAxisData);

		}
    	
    	return dataList;
    	
    }
    
    public Double[] getColumnData(String[][] data, String[] timeArray,
			String loc, int index) {
		ArrayList<Double> doubleArray = new ArrayList<Double>();
		for (int i = 0; i < timeArray.length; i++) {
			double value = findValueInData(data, loc, timeArray[i], index);
			doubleArray.add(value);
		}
		Double[] doubleArr = new Double[doubleArray.size()];
		doubleArr = doubleArray.toArray(doubleArr);
		return doubleArr;
	}
    
    
    public String[] getTimeArray() {

		ArrayList<String> timeArray = new ArrayList<String>();
		int from = 0;
		int to = 0;

		// Append Date filter
		String yFrom = this.yearFrom;
		String yTo = this.yearTo;
		String qFrom = this.from;
		String qTo = this.to;
		String mFrom = this.from;;
		String mTo = this.to;
		String wFrom = this.from;;
		String wTo = this.to;
		TimeDimenstion time = TimeDimenstion.valueOf(this.time.toUpperCase());
		switch (time) {
		case YEAR:
			from = Integer.valueOf(yFrom);
			to = Integer.valueOf(yTo);
			break;
		case QUARTER:
			from = Integer.valueOf(qFrom);
			to = Integer.valueOf(qTo);
			break;
		case MONTH:
			from = Integer.valueOf(mFrom);
			to = Integer.valueOf(mTo);
			break;
		case WEEK:
			from = Integer.valueOf(wFrom);
			to = Integer.valueOf(wTo);
			break;
		default:
			break;
		}

		for (int i = from; i <= to; i++) {

			timeArray.add(String.valueOf(i));
		}

		String[] timeArr = new String[timeArray.size()];
		timeArr = timeArray.toArray(timeArr);

		return timeArr;
	}
    
    /**
	 * Applies filter picked from date fields w.r.t. selected time dimension
	 * 
	 * @param params
	 * @return
	 */
	private String getFilter(Parameter[] params, String alias) {
		
		if (!alias.equals(""))
			alias = alias + ".";

		StringBuilder where = new StringBuilder(" where 1 = 1 ");
		// Append Date filter
		String yFrom = this.yearFrom;
		String yTo = this.yearTo;
		String qFrom = this.from;
		String qTo = this.to;
		String mFrom = this.from;;
		String mTo = this.to;
		String wFrom = this.from;;
		String wTo = this.to;
		
		TimeDimenstion time = TimeDimenstion.valueOf(this.time.toUpperCase());
		
		switch (time) {
		case YEAR:
			where.append(" and " + alias + "year between " + yFrom + " and "
					+ yTo);
			break;
		case QUARTER:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "quarter between " + qFrom + " and "
					+ qTo);
			break;
		case MONTH:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "month between " + mFrom + " and "
					+ mTo);
			break;
		case WEEK:
			where.append(" and " + alias + "year = " + yFrom);
			where.append(" and " + alias + "week between " + wFrom + " and "
					+ wTo);
			break;
		default:
			break;
		}
		if (params != null) {
			for (Parameter param : params) {
				DataType type = param.getType();
				switch (type) {
				case CHAR:
				case STRING:
					where.append(" and " + param.getName());
					where.append(" = '" + param.getValue() + "'");
					break;
				default:
					where.append(" and " + param.getName());
					where.append(" = " + param.getValue());
				}
			}
		}
		return where.toString();
	}
	
	/**
	 * Return set of unique values from given data array
	 * 
	 * @param data
	 * @param columnIndex
	 * @return
	 */
	private String[] getUniqueValues(String[][] data, int columnIndex) {
		HashSet<String> values = new HashSet<String>();
		for (String[] record : data) {
			values.add(record[columnIndex]);
		}
		String[] array = values.toArray(new String[] {});
		return array;
	}

	private double findValueInData(String[][] data, String columnValue,
			String rowValue, int valueIndex) {
		double value = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][1].equals(columnValue) && data[i][0].equals(rowValue)) {
				value = Double.parseDouble(data[i][valueIndex]);
			}
		}
		return value;
	}
    
 }
