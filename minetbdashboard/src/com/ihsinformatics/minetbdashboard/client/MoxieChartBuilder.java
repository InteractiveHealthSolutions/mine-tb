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

import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.Legend.Layout;
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;

import com.google.gwt.user.client.Window;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;
import com.ihsinformatics.minetbdashboard.shared.GraphData;


/**
 * Chart Builder Class using Moxie Group - High Charts...
 * 
 * @author Rabbia
 *
 */
public class MoxieChartBuilder {
	
	/**
	 * Returns Line Chart 
	 * 
	 * @param timeArray - x-axis Data Array
	 * @param xLabel
	 * @param yLabel
	 * @param dataList  - GraphData Array List (YAxis Data)
	 * @param title
	 * @param subTitle
	 * 
	 * @return Chart
	 */
	public static Chart createLineChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subTitle, String legendType){
		
		// Define LINE Chart
		final Chart chart = new Chart();  
        chart.setType(Series.Type.LINE);  
        chart.setMarginBottom(60);
        chart.setMarginRight(80);
        
        // Chart Headings
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20)  // center 
        					)
        	 .setChartSubtitle(new ChartSubtitle()  
            						.setText(subTitle)  
            						.setX(-20)  // center
        			 		   );
        
        // Set Legends position and style 
        chart.setLegend(new Legend()    
                				.setAlign(Legend.Align.RIGHT)  
                				.setVerticalAlign(Legend.VerticalAlign.MIDDLE)     
                				.setFloating(true) 
                				.setShadow(false) 
                				.setLayout(Layout.VERTICAL)
                				.setTitleText(legendType)
        				); 
        
        // Set Tool Tip
        chart.setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                    	 return "<b>" + toolTipData.getXAsString() + " ("+xLabel+")" + "</b><br/>" +
                       		  toolTipData.getSeriesName() + " : " + toolTipData.getYAsDouble(); 
                    }  
                })  
            );
		
        // Allow Whole Number only
		chart.getXAxis().setAllowDecimals(false);
		chart.getYAxis().setAllowDecimals(false);
		
		// Define X-Axis
	    chart.getXAxis()  
	    	.setCategories(timeArray)    // set x-Axis Data - Number[]
	    	.setAxisTitle(new AxisTitle()     // x-Axis Title & Value
	    					.setText(xLabel)
	    					.setStyle(new Style().setFontSize("16px"))  
	    				 );  
	
	    // Define Y-Axis
		chart.getYAxis()
			 .setMin(0)  
		     .setAxisTitle(new AxisTitle()    // y-Axis Title & Value
		        			.setText(yLabel)
		        			.setStyle(new Style().setFontSize("16px"))  
		    		 	);
		
		// Set Y-Values
	    for (int i=0; i<dataList.size(); i++) {
	    	
	    	chart.addSeries(chart.createSeries()  
	    	        				.setName(dataList.get(i).getTitle())    //  Series Title
	    	        				.setPoints(dataList.get(i).getData())   //  Series Data in Number Array
	    					);
	    } 
	
	    // move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
	    
	    return chart;  
	}
	
	/**
	 * Returns Pie Chart 
	 * 
	 * @param timeArray - x-axis Data Array
	 * @param xLabel
	 * @param yLabel
	 * @param dataList  - GraphData Array List (YAxis Data)
	 * @param title
	 * @param subTitle
	 * 
	 * @return Chart
	 */
	public static Chart createPieChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subTitle){
		
		// Define PIE Chart
		final Chart chart = new Chart(); 
        chart.setType(Series.Type.PIE);
        
        int locNumber = dataList.size();
        
        if(locNumber > 2){
        	
        	title = "Pie Chart NOT APPLICABLE for selected Data Set.";
        	chart.setChartTitle(new ChartTitle()  
			                      .setText(title)
			                      .setVerticalAlign(org.moxieapps.gwt.highcharts.client.ChartTitle.VerticalAlign.MIDDLE)
	                           );
        	return chart;
        }
        
        String sub = "";
        if(locNumber == 1)
        	sub = dataList.get(0).getTitle();
        else
        	sub = "Inner Circle: " + dataList.get(0).getTitle() + " - Outer Circle: " + dataList.get(1).getTitle();
       
        
        // Chart Headings
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20)  // center 
        					)
        	 .setChartSubtitle(new ChartSubtitle()  
            						.setText(subTitle + "<br><br>" + sub)
            						.setX(-20)  // center
        			 		   ); 
        
        // Set Plot Options... Legends and Label
        chart.setPiePlotOptions(new PiePlotOptions()  
            .setAllowPointSelect(true)  
            .setCursor(PlotOptions.Cursor.POINTER)  
            .setPieDataLabels(new PieDataLabels()  
                .setEnabled(true)  
            )
        );
        
        // Set Tool Tips
        chart.setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                    return toolTipData.getSeriesName() + " <b>(" + toolTipData.getPointName() + ")</b>: " + toolTipData.getYAsDouble();  
                }  
            })  
        );  

      // Set Legend Position and Style
      chart.setLegend(new Legend()  
            					.setLayout(Legend.Layout.HORIZONTAL)  
            					.setAlign(Legend.Align.RIGHT)  
            					.setVerticalAlign(Legend.VerticalAlign.BOTTOM)  
            					.setBorderWidth(0)  
            					.setTitleText(xLabel)
        				); 
        
      
      double size = 0.25;
      for(int i = 0; (i<locNumber && i<2); i++){
    	  
    	Boolean flag = false;  
    	if(i == locNumber - 1)
    		flag = true;
    	
    	Point[] points = new Point[timeArray.length];
    	for(int j=0; j<timeArray.length; j++){
    		Point point = new Point(timeArray[j], dataList.get(i).getData()[j]);
    		points[j] = point;
    	}
    	
		chart.addSeries(chart.createSeries()  
		        .setName(dataList.get(i).getTitle())
	            .setPlotOptions(new PiePlotOptions()  
	                .setCenter(.5, .5)  
	                .setInnerSize(size) 
	                .setShowInLegend(flag)
        			.setPieDataLabels(new PieDataLabels()  
	                    .setConnectorColor("#000000")  
	                    .setEnabled(true)  
	                    .setColor("#000000")  
	                    .setFormatter(new DataLabelsFormatter() {  
	                        public String format(DataLabelsData dataLabelsData) {  
	                            return " " + dataLabelsData.getYAsDouble();  
	                        }  
	                    })  
	                 )  			
	            ) 
		        .setPoints(points)
		    );  
		
    	size = size + 0.25;
      }  
      
	  return chart; 
	}
	
	/**
	 * 
	 * Returns Column Chart
	 * 
	 * @param timeArray
	 * @param xLabel
	 * @param yLabel
	 * @param dataList
	 * @param title
	 * @param subtitle
	 * 
	 * @return Chart
	 */
	public static Chart createColumnChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subtitle, String legendType, Boolean stacked, Boolean autoAdjusted){
		
		// if y-axis has more than 10 variable - Go for Stack Bar - on Time
		if(dataList.size() > 10 && autoAdjusted){
			
			Chart chart = createStackColumnOnTimeChart(timeArray,xLabel, yLabel, dataList, title, subtitle, legendType);
			return chart;
			
		}
		
		// Define Column Chart
		final Chart chart = new Chart(); 
        chart.setType(Series.Type.COLUMN);  
        chart.setMarginRight(80);  
        chart.setMarginBottom(60);
        if(stacked){    // Stacking option! - on Data 
        chart.setSeriesPlotOptions(new SeriesPlotOptions()  
										.setStacking(PlotOptions.Stacking.NORMAL)  
        							);  
        }
        
        // Chart Headings
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20)  // center  
        					)
        	.setChartSubtitle(new ChartSubtitle()  
            						.setText(subtitle)  
            						.setX(-20)  // center  
        					);
        
        // Set Legend Position and Style
        chart.setLegend(new Legend()  
					        .setAlign(Legend.Align.RIGHT)  
							.setVerticalAlign(Legend.VerticalAlign.MIDDLE)     
							.setFloating(true) 
							.setShadow(false) 
							.setLayout(Layout.VERTICAL)
							.setTitleText(legendType)
        				);
        
        // Set ToolTip
        chart.setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                    	 return "<b>" + toolTipData.getXAsString() + " ("+xLabel+")" + "</b><br/>" +
                       		  toolTipData.getSeriesName() + " : " + toolTipData.getYAsDouble(); 
                    }  
                })  
            );
		
        // Allow only whole number
		chart.getXAxis().setAllowDecimals(false);
		chart.getYAxis().setAllowDecimals(false);
		
		// Define X-Axis
		chart.getXAxis()  
	    	 .setCategories(timeArray)    // set x-Axis Data - Number[]
	    	 .setAxisTitle(new AxisTitle()    // x-Axis Title & Style
	    	 						.setText(xLabel)
	    	 						.setStyle(new Style().setFontSize("16px"))  
	    			 	   );  

		// Define Y-Axis
		chart.getYAxis()
			.setMin(0)
		    .setAxisTitle(new AxisTitle()      // y-Axis Title & Style
		        					.setText(yLabel)
		        					.setStyle(new Style().setFontSize("16px"))  
		    				);
		
		// set y-axis Data
	    for (int i=0; i<dataList.size(); i++) {
	    	
	    	chart.addSeries(chart.createSeries()  
	    	        .setName(dataList.get(i).getTitle())    // Set series name 
	    	        .setPoints(dataList.get(i).getData())    // Set series data - Number[]    
	    	    );
	    } 
	    
		// move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);

	    return chart; 
	}
	
	/**
	 * 
	 * Returns Bar Chart
	 * 
	 * @param timeArray
	 * @param xLabel
	 * @param yLabel
	 * @param dataList
	 * @param title
	 * @return
	 */
	public static Chart createBarChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subtitle, String legendType, Boolean stacked, Boolean autoAdjust){
		
		// if y-axis has more than 10 variable - Go for Stack Bar on Time
		if(dataList.size() > 10 && autoAdjust){
			
			Chart chart = createStackBarOnTimeChart(timeArray,xLabel, yLabel, dataList, title, subtitle, legendType);
			return chart;
			
		}
		
		// Define Bar Chart
		final Chart chart = new Chart() ; 
        chart.setType(Series.Type.BAR);  
        chart.setMarginRight(80);  
        chart.setMarginBottom(60);
        if(stacked){  // Stacking option on Data
        chart.setSeriesPlotOptions(new SeriesPlotOptions()  
				.setStacking(PlotOptions.Stacking.NORMAL)  
        	 );  
        }
        
        // Chart Headings
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20)  // center  
        					)
        	.setChartSubtitle(new ChartSubtitle()  
            						.setText(subtitle)  
            						.setX(-20)  // center  
        					);
        
        // Set Legend Position and Style
        chart.setLegend(new Legend()  
            					.setLayout(Legend.Layout.VERTICAL)  
            					.setAlign(Legend.Align.RIGHT)  
            					.setVerticalAlign(Legend.VerticalAlign.TOP)  
            					.setX(-10)  
            					.setY(100)
            					.setFloating(true) 
            					.setShadow(false) 
            					.setBorderWidth(0)
            					.setTitleText(legendType)
        					);
        
        // Set Tool Tip
        chart.setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                    	 return "<b>" + toolTipData.getXAsString() + " ("+xLabel+")" + "</b><br/>" +
                       		  toolTipData.getSeriesName() + " : " + toolTipData.getYAsDouble(); 
                    }  
                })  
            );
		
        // Allow whole numbers only
		chart.getXAxis().setAllowDecimals(false);
		chart.getYAxis().setAllowDecimals(false);
		
		// Define X-Axis
		chart.getXAxis()  
	    	 .setCategories(timeArray)     // set x-axis data - Number[]
	    	 .setAxisTitle(new AxisTitle()  // set x-axis title & style
	    	 						.setText(xLabel)
	    	 						.setStyle(new Style().setFontSize("16px"))  
	    			 		);  

        // Define Y-Axis
		chart.getYAxis()
			 .setMin(0)
		     .setAxisTitle(new AxisTitle()  // set y-axis title & style
		        					.setText(yLabel)
		        					.setStyle(new Style().setFontSize("16px"))  
		    		 		);
		
		// set y-axis data 
	    for (int i=0; i<dataList.size(); i++) {
	    	
	    	chart.addSeries(chart.createSeries()  
	    	        .setName(dataList.get(i).getTitle())   // set series name
	    	        .setPoints(dataList.get(i).getData() 	// set series data - Number[]
	    	        )  
	    	    );
	    } 

	    // move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
	    
	    return chart; 
	}
	
	/**
	 * 
	 * Returns Stack Bar Chart on Time Dimension
	 * 
	 * @param timeArray
	 * @param xLabel
	 * @param yLabel
	 * @param dataList
	 * @param title
	 * @param subtitle
	 * 
	 * @return chart
	 */
	public static Chart createStackColumnOnTimeChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subtitle, String legendType){
		
		// Create stacked Bar Chart
		final Chart chart = new Chart();  
		chart.setType(Series.Type.COLUMN); 
		chart.setMarginBottom(60);
        chart.setWidth("100%");
        chart.setSeriesPlotOptions(new SeriesPlotOptions()  
        										.setStacking(PlotOptions.Stacking.NORMAL)  
        						  );  
        
        // Chart Heading
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20))  // center 
            .setChartSubtitle(new ChartSubtitle()  
            						.setText(subtitle)  
            						.setX(-20)	// center
            			   );
        
        // Set Legend Position & Style
        chart.setLegend(new Legend()  
            					.setReversed(true) 
            					.setTitleText(xLabel)
            					.setAlign(Legend.Align.RIGHT)  
            					.setVerticalAlign(Legend.VerticalAlign.MIDDLE) 
            					.setLayout(Layout.VERTICAL)
            					.setFloating(true)
            					.setShadow(false) 
        				);
        
        // set Tool Tip
        chart.setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                	 return "<b>" + toolTipData.getXAsString() + "</b><br/>" +
                   		  toolTipData.getSeriesName() +  " ("+xLabel+") : " + toolTipData.getYAsDouble(); 
                }  
            })  
        );  

        // Extract Graph Data Title from ArrayList
		String[] yData = new String[dataList.size()];
		for(int i=0; i < dataList.size(); i++){
			
			GraphData data = dataList.get(i);
			yData[i] = data.getTitle();
			
		}
		
		// Define x-Axis
	    chart.getXAxis()  
	         .setCategories(yData);
	
	    // Define y-Axis
	   chart.getYAxis() 
	        .setAxisTitle(new AxisTitle()  // set y-Axis title & styles
	        						.setText(yLabel)
	        						.setStyle(new Style().setFontSize("16px"))
	        				)  ;
	   
	   // Allow while number only
	   chart.getXAxis().setAllowDecimals(false);
	   chart.getYAxis().setAllowDecimals(false);
	   
	   ArrayList<Number[]> numberList = new ArrayList<Number[]>();
	   
	   for(int i=0; i<timeArray.length; i++){   // for every time dimension 
		   
		   Number[] numberData = new Number[dataList.size()];   
		   
		   for(int j=0; j<dataList.size(); j++){  // extract all number[j] for graphData list
			   
			   Number[] data = dataList.get(j).getData();
			   numberData[j] = data[i];
			   
		   }
		   
		   numberList.add(numberData);  // add to list
	   }
	    
	   // create y series for time dimension
	   for (int i=timeArray.length-1; i>=0; i--) {
        	
        	 chart.addSeries(chart.createSeries()  
        	            .setName(timeArray[i])			
        	            .setPoints(numberList.get(i))   // extract data Number[]
        	        ); 
        	
        }
		
		// move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setOption("/buttons/contextButton/align", "left")
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
	      
	    return chart;  
	}
	
	/**
	 * 
	 * Returns Stack Bar Chart on Time Dimension
	 * 
	 * @param timeArray
	 * @param xLabel
	 * @param yLabel
	 * @param dataList
	 * @param title
	 * @param subtitle
	 * 
	 * @return chart
	 */
	public static Chart createStackBarOnTimeChart(String[] timeArray, final String xLabel,final String yLabel, ArrayList<GraphData> dataList, String title, String subtitle, String legendType){
		
		// Create stacked Bar Chart
		final Chart chart = new Chart();  
		chart.setType(Series.Type.BAR); 
		chart.setMarginBottom(60);
		if(dataList.size() > 10)
			chart.setHeight(750);
        chart.setSeriesPlotOptions(new SeriesPlotOptions()  
        										.setStacking(PlotOptions.Stacking.NORMAL)  
        						  );  
        
        // Chart Heading
        chart.setChartTitle(new ChartTitle()  
            						.setText(title)  
            						.setX(-20))  // center 
            .setChartSubtitle(new ChartSubtitle()  
            						.setText(subtitle)  
            						.setX(-20)	// center
            			   );
        
        // Set Legend Position & Style
        chart.setLegend(new Legend()  
            					.setReversed(true) 
            					.setShadow(false)
            					.setTitleText(xLabel)
            					.setAlign(Legend.Align.RIGHT)  
            					.setVerticalAlign(Legend.VerticalAlign.MIDDLE) 
            					.setLayout(Layout.VERTICAL)
            					.setFloating(true)
        				);
        
        // set Tool Tip
        chart.setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                	 return "<b>" + toolTipData.getXAsString() + "</b><br/>" +
                   		  toolTipData.getSeriesName() +  " ("+xLabel+") : " + toolTipData.getYAsDouble(); 
                }  
            })  
        );  

        // Extract Graph Data Title from ArrayList
		String[] yData = new String[dataList.size()];
		for(int i=0; i < dataList.size(); i++){
			
			GraphData data = dataList.get(i);
			yData[i] = data.getTitle();
			
		}
		
		// Define x-Axis
	    chart.getXAxis()  
	         .setCategories(yData);
	
	    // Define y-Axis
	   chart.getYAxis() 
	        .setAxisTitle(new AxisTitle()  // set y-Axis title & styles
	        						.setText(yLabel)
	        						.setStyle(new Style().setFontSize("16px"))
	        				)  ;
	   
	   // Allow while number only
	   chart.getXAxis().setAllowDecimals(false);
	   chart.getYAxis().setAllowDecimals(false);
	   
	   ArrayList<Number[]> numberList = new ArrayList<Number[]>();
	   
	   for(int i=0; i<timeArray.length; i++){   // for every time dimension 
		   
		   Number[] numberData = new Number[dataList.size()];   
		   
		   for(int j=0; j<dataList.size(); j++){  // extract all number[j] for graphData list
			   
			   Number[] data = dataList.get(j).getData();
			   numberData[j] = data[i];
			   
		   }
		   
		   numberList.add(numberData);  // add to list
	   }
	    
	   // create y series for time dimension
	   for (int i=timeArray.length-1; i>=0; i--) {
        	
        	 chart.addSeries(chart.createSeries()  
        	            .setName(timeArray[i])			
        	            .setPoints(numberList.get(i))   // extract data Number[]
        	        ); 
        	
        }
		
		// move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setOption("/buttons/contextButton/align", "left")
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
	      
	    return chart;  
	}
	
	/**
	 * returns combination chart (stack chart + line chart)
	 * 
	 * @param timeData
	 * @param primaryData
	 * @param secondaryData
	 * @param lineData
	 * @param title
	 * @param loc
	 * @param time
	 * @param yLabel
	 * @param lineLabel
	 * @param primaryTitle
	 * @param secondaryTitle
	 * 
	 * @return chart
	 */
	public static Chart createStackChartWithLine(String[] timeData, Number[] primaryData , Number[] secondaryData, Number[] lineData, String title, String loc, final String time, String yLabel, final String lineLabel, String primaryTitle, String secondaryTitle) {  
		
		// Define Stack Column Chart
        final Chart chart = new Chart();  
        chart.setType(Series.Type.COLUMN);
        chart.setColumnPlotOptions(new ColumnPlotOptions()  
        									.setStacking(PlotOptions.Stacking.NORMAL)  
        						  ); 
        
        // Chart Headings
        chart.setChartTitle(new ChartTitle()  
            						.setText(Character.toUpperCase(time.charAt(0)) + time.substring(1) + "ly " +title)  
            						.setX(-20)  // center  
        					)
        	  .setChartSubtitle(new ChartSubtitle()  
            							.setText(loc)  
            							.setX(-20)  // center  
        						);
       
        // Define Legend Position & Style    
        chart.setLegend(new Legend()  
            					.setAlign(Legend.Align.RIGHT)  
            					.setVerticalAlign(Legend.VerticalAlign.BOTTOM)     
            					.setFloating(true)  
            					.setShadow(false)  
        		   		 );
           
         // Define Tool Tip   
         chart.setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getXAsString() + " ("+time+")" + "</b><br/>" +  
                            toolTipData.getSeriesName() + " : " + toolTipData.getYAsLong() + "<br/>" +
                            (lineLabel.equals(toolTipData.getSeriesName()) ? "" : "Total: " + toolTipData.getTotal());  
                    }  
                })  
            ); 
        
        // Allow Whole Numbers Only
        chart.getXAxis().setAllowDecimals(false);
 		chart.getYAxis().setAllowDecimals(false);
        
		// Define x-Axis
        chart.getXAxis()  
             .setCategories(timeData)    // set x-Axis Data - Number[]
             .setAxisTitle(new AxisTitle()  // set x-Axis Title & Style
             						.setText(Character.toUpperCase(time.charAt(0)) + time.substring(1))
             						.setStyle(new Style().setFontSize("16px"))
             			  ); 
  
        // Define L.H.S y-Axis    
        chart.getYAxis(1)
        	 .setMax(getMaxValue(primaryData).intValue()+getMaxValue(secondaryData).intValue())  
             .setMin(0)
             .setAxisTitle(new AxisTitle()  // set Title & Style
             						.setText(yLabel)
             						.setStyle(new Style().setFontSize("16px")) 
            		 	  ); 
        
        // Define R.H.S y-Axis     
        chart.getYAxis(0)
        	 .setMax(getMaxValue(lineData))
        	 .setMin(0)
             .setAxisTitle(new AxisTitle()   // set Title & Style
                					.setText(lineLabel)
                					.setStyle(new Style().setFontSize("16px"))
                		   ) 
             .setLabels(new YAxisLabels()    // set Label & Style
                                    .setStyle(new Style()  
                                    .setColor("#a74572")  
                           )  
                       )         
             .setOpposite(true);  // set on R.H.S
  
        // set y-Axis Primary Data - First Stack
        chart.addSeries(chart.createSeries()
        							.setYAxis(1)	
        							.setName(primaryTitle)   
        							.setPoints(primaryData)  // set Data - Number []
        							.setPlotOptions(new ColumnPlotOptions()  
        													.setColor("#89A54E")
        									)	
        				);
        
        // set y-Axis Secondary Data - Second Stack
        chart.addSeries(chart.createSeries()  
        							.setYAxis(1)	
        							.setName(secondaryTitle)  
        							.setPoints(secondaryData)    // set Data - Number []
        							.setPlotOptions(new ColumnPlotOptions()  
        														.setColor("#A74945")
        									) 
        				);
        
        // set line series 
        chart.addSeries(chart.createSeries()  
                					.setName(lineLabel)  
                					.setType(Series.Type.LINE)  // define type...
                					.setPlotOptions(new LinePlotOptions()  
                											.setColor("#a74572")
                								   )
                					.setPoints(lineData)       // set Data - Number []
                		);
        
        // move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
        
        return chart;  
    } 
	
	/**
	 * 
	 * Returns Combination Chart (line charts + column chart)
	 * 
	 * @param xAxisData
	 * @param xLabel
	 * @param yLabel
	 * @param dataList
	 * @param title
	 * @param loc
	 * 
	 * @return chart
	 */
	public static Chart createColumnChartWithLines(String[] xAxisData, final String xLabel, String yLabel, ArrayList<GraphData> dataList, String title, String loc) {  
		
		// Get Primary Data for column chart
		final String primaryTitle = dataList.get(0).getTitle();
		final Number[] primaryData = dataList.get(0).getData();
		
		// Define Chart
        final Chart chart = new Chart();
        chart.setZoomType(BaseChart.ZoomType.X_AND_Y); 
        
        // Define Headings
        chart.setChartTitleText(xLabel.charAt(0) + xLabel.substring(1) + "ly " +title); 
        chart.setChartSubtitleText(loc);
        
        // Define Tool Tip
        chart.setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                    return "<b>" + toolTipData.getXAsString() + " ("+xLabel+")" + "</b><br/>" +
                    		  toolTipData.getSeriesName() + " : " + toolTipData.getYAsDouble() +  
                              (primaryTitle.equals(toolTipData.getSeriesName()) ? "" : " %");  
                }  
            })  
        );  
            
        // Define Legend Position & styl
        chart.setLegend(new Legend()  
	            				.setAlign(Legend.Align.RIGHT)  
	            				.setVerticalAlign(Legend.VerticalAlign.BOTTOM) 
	            				.setFloating(true)
	            				.setShadow(false)  
        				); 
        
        //Define x-Axis
        chart.getXAxis()  
             .setCategories(xAxisData)
             				.setAxisTitle(new AxisTitle()  
             									.setText(xLabel.charAt(0) + xLabel.substring(1))
             									.setStyle(new Style().setFontSize("16px")
             							)  
             			   );  
        
        // Allow Whole Numbers Only
        chart.getXAxis().setAllowDecimals(false);
		chart.getYAxis().setAllowDecimals(false);
  
        // Primary yAxis  
        chart.getYAxis(1)
        	 .setMax(getMaxValue(primaryData))  
             .setAxisTitle(new AxisTitle()   // Define YAxis L.H.S Heading and Title 
             						.setText(primaryTitle)
             						.setStyle(new Style().setFontSize("16px"))  
            )
            .setLabels(new YAxisLabels()    // Define YAxis L.H.S Label and Style 
                				.setStyle(new Style().setColor("#A74945") )  
                                .setFormatter(new AxisLabelsFormatter() {  
                                				public String format(AxisLabelsData axisLabelsData) {  
                                					 return axisLabelsData.getValueAsLong() + "";  
                                				}  
                                })  
            		);
        
        // Define y-Axis L.H.S Series
        chart.addSeries(chart.createSeries()  
                					.setName(primaryTitle)  
                					.setType(Series.Type.COLUMN)    // Column Series
                					.setPlotOptions(new ColumnPlotOptions()  
                												.setColor("#A74945")
                									) 
                					.setYAxis(1) 
                					.setPoints(primaryData)      // set data - Number[]
        				); 
        
        // Secondary yAxis  
        chart.getYAxis(0)
        	 .setMin(0)
        	 .setMax(getMaxValueFromDataList(dataList))
             .setAxisTitle(new AxisTitle()  // Define YAxis R.H.S Heading and Title 
             						.setText(yLabel)
             						.setStyle(new Style().setFontSize("16px"))  
            		 	  )  
            .setOpposite(true)  
            .setLabels(new YAxisLabels()  // Define YAxis r.H.S Label and Style 
                				.setStyle(new Style()  
                				.setColor("#89A54E")  
                	  )  
                	  			.setFormatter(new AxisLabelsFormatter() {  
                	  									public String format(AxisLabelsData axisLabelsData) {  
                	  													return axisLabelsData.getValueAsLong() + " ";  
                	  										}  
                	  			})  
            );  
  
       // Define y-Axis R.H.S Series
       for (int i=1; i<dataList.size(); i++) {
        	
        	 chart.addSeries(chart.createSeries()  
        	            .setName(dataList.get(i).getTitle())  
        	            .setType(Series.Type.LINE)  // Line Series
        	            .setPoints(dataList.get(i).getData())  // set data - Number[]
        	        ); 
        	
        }
        
       // move exporting button & move to top button
	    chart.setExporting(new Exporting()
	       .setSourceWidth(1500)
	       .setSourceHeight(1500)
	    		);
       
        return chart;  
    } 
	
	
	public static void createTable(String reportType, Table table, ArrayList<GraphData> dataList, String[] timeArray, String xLabel, String yLabel, String title, String subtitle, String legendType) {
		
		// Prepare the data
		DataTable dataTable = DataTable.create();
		
		if(reportType.equalsIgnoreCase("Simple Reports")){
			dataTable.addColumn(ColumnType.STRING, xLabel + subtitle);
			dataTable.addColumn(ColumnType.STRING, legendType);
			dataTable.addColumn(ColumnType.NUMBER, yLabel);
			
			dataTable.addRows(timeArray.length * dataList.size());
			
			int index = 0;
			for(int i=0; i<dataList.size(); i++){
			
				GraphData gd = dataList.get(i);
				
				for(int j=0; j<timeArray.length; j++){
					dataTable.setCell(index, 0, timeArray[j]);
					dataTable.setCell(index, 1, gd.getTitle());
					dataTable.setCell(index, 2, gd.getData()[j].intValue());
					index++;
				}
			}
		}
		
		else if(reportType.equalsIgnoreCase("Screening Summary") || reportType.equalsIgnoreCase("Sputum Submission Rates") || reportType.equalsIgnoreCase("Treatment Initiation Rates") || reportType.equalsIgnoreCase("Sputum Submission & Error Rates") 
				|| reportType.equalsIgnoreCase("Line") || reportType.equalsIgnoreCase("Column") || reportType.equalsIgnoreCase("Bar") || reportType.equalsIgnoreCase("Pie")){
			
			dataTable.addColumn(ColumnType.STRING, xLabel);
			for(int i=0; i<dataList.size(); i++)
				dataTable.addColumn(ColumnType.NUMBER, dataList.get(i).getTitle());
			
			dataTable.addRows(timeArray.length);
			
			for(int j=0; j<timeArray.length; j++)
				dataTable.setCell(j, 0, timeArray[j]);
			
			for(int i=0; i<dataList.size(); i++){
				
				GraphData gd = dataList.get(i);
				
				for(int j = 0; j<timeArray.length; j++){
					
					dataTable.setCell(j, i+1, gd.getData()[j].intValue());
					
				}
				
			}
			
		}
		
		// Set options
		TableOptions options = TableOptions.create();
		options.setAlternatingRowStyle(true);
		options.setShowRowNumber(true);
		options.setPageSize(20);

		// Draw the chart
		table.draw(dataTable, options);

	}
	
	/**
	 * Returns Maximum values from Number Array
	 * @param data
	 * @return
	 */
	public static Number getMaxValue(Number[] data){
		
		Number no = data[0];
		for(Number n: data){
			if(n.intValue() > no.intValue())
				no = n;
		}
		
		return no;
	}
	
	/**
	 * Returns Maximum values from DataList
	 * @param dataList
	 * @return
	 */
	public static Number getMaxValueFromDataList(ArrayList<GraphData> dataList){
		
		Number n = 0;
		for(int i=1; i<dataList.size(); i++){
			
			Number temp = getMaxValue(dataList.get(i).getData());
			if(temp.intValue() > n.intValue())
				n = temp;
		}
		
		return n;
	}
	

}
