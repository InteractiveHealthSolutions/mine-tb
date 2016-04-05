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

import org.apache.taglibs.response.SetHeaderTag;
import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*; 

import com.google.gwt.user.client.Window;
import com.ihsinformatics.minetbdashboard.shared.GraphData;


/**
 * Chart Builder Class using Moxie Group - High Charts...
 * 
 * @author Rabbia
 *
 */
public class MoxieChartBuilder {
	
	public static Chart createLineChart(String[] timeArray, final String xLabel, String yLabel, ArrayList<GraphData> dataList, String title){
		final Chart chart = new Chart()  
        .setType(Series.Type.LINE)  
        .setMarginRight(130)  
        .setMarginBottom(25)  
        .setChartTitle(new ChartTitle()  
            .setText(title)  
            .setX(-20)  // center  
        )
        .setLegend(new Legend()  
            .setLayout(Legend.Layout.VERTICAL)  
            .setAlign(Legend.Align.RIGHT)  
            .setVerticalAlign(Legend.VerticalAlign.TOP)  
            .setX(-10)  
            .setY(100)  
            .setBorderWidth(0)  
        )  
        .setToolTip(new ToolTip()  
            .setFormatter(new ToolTipFormatter() {  
                public String format(ToolTipData toolTipData) {  
                    return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +  
                        toolTipData.getXAsString() + "(" + xLabel + "): " + toolTipData.getYAsDouble();  
                }  
            })  
        );    

    chart.getXAxis()  
    .setCategories(  
    		timeArray 
    ).setAxisTitle(new AxisTitle()  
    .setText(xLabel).setStyle(new Style().setFontSize("16px"))  
    );  


	chart.getYAxis().setMin(0)
	    .setAxisTitle(new AxisTitle()  
	        .setText(yLabel).setStyle(new Style().setFontSize("16px"))  
	);
    
    for (int i=0; i<dataList.size(); i++) {
    	
    	chart.addSeries(chart.createSeries()  
    	        .setName(dataList.get(i).getTitle())  
    	        .setPoints(dataList.get(i).getData() 
    	        )  
    	    );
    } 

    return chart;  
	}
	
	
	public static Chart createStackChartWithLine(String[] timeData, Number[] primaryData , Number[] secondaryData, Number[] lineData, String title, String loc, final String time, String yLabel, final String lineLabel, String primaryTitle, String secondaryTitle) {  
		  
        final Chart chart = new Chart()  
            .setType(Series.Type.COLUMN)  
            .setChartTitleText(Character.toUpperCase(time.charAt(0)) + time.substring(1) + "ly " +title) 
            .setChartSubtitleText(loc)
            .setColumnPlotOptions(new ColumnPlotOptions()  
                .setStacking(PlotOptions.Stacking.NORMAL)  
            )  
            .setLegend(new Legend()  
                .setAlign(Legend.Align.RIGHT)  
                .setVerticalAlign(Legend.VerticalAlign.BOTTOM)     
                .setFloating(true)  
                .setBackgroundColor("#FFFFFF")  
                .setBorderColor("#CCC")  
                .setBorderWidth(1)  
                .setShadow(false)  
            )  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getXAsString() + " ("+time+")" + "</b><br/>" +  
                            toolTipData.getSeriesName() + " : " + toolTipData.getYAsLong() + "<br/>" +
                            (lineLabel.equals(toolTipData.getSeriesName()) ? "" : "Total: " + toolTipData.getTotal());  
                    }  
                })  
            );  
  
        chart.getXAxis()  
            .setCategories(timeData)
            .setAxisTitle(new AxisTitle()  
            .setText(time).setStyle(new Style().setFontSize("16px"))); 
  
        chart.getYAxis(1).setMax(getMaxValue(primaryData).intValue()+getMaxValue(secondaryData).intValue())  
            .setMin(0)
            .setAxisTitle(new AxisTitle()  
            .setText(yLabel).setStyle(new Style().setFontSize("16px")) 
            ); 
        
     // Secondary yAxis  
        chart.getYAxis(0).setMax(getMaxValue(lineData))
        	.setMin(0)
            .setAxisTitle(new AxisTitle()  
                .setText(lineLabel).setStyle(new Style().setFontSize("16px"))) 
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor("#a74572")  
                )  
            ).setOpposite(true); 
  
        chart.addSeries(chart.createSeries()
        	.setYAxis(1)	
            .setName(primaryTitle)  
            .setPoints(primaryData) 
            .setPlotOptions(new ColumnPlotOptions()  
    				.setColor("#89A54E")
            	)	
        );  
        chart.addSeries(chart.createSeries()  
        	.setYAxis(1)	
            .setName(secondaryTitle)  
            .setPoints(secondaryData)  
            .setPlotOptions(new ColumnPlotOptions()  
            		.setColor("#A74945")
            	) 
        );
        chart.addSeries(chart.createSeries()  
                .setName(lineLabel)  
                .setType(Series.Type.LINE)  
                .setPlotOptions(new LinePlotOptions()  
                    .setColor("#a74572")
                )
                .setPoints(lineData)
                
            );
        
        //chart.setExporting(new Exporting().setEnabled(true));
  
        return chart;  
    }  
	
	
	 public static Chart createStackChart(String[] timeData, Number[] primaryData , Number[] secondaryData, String title, String loc, final String time, String yLabel, String primaryTitle, String secondaryTitle) {  
		  
	        final Chart chart = new Chart()
	            .setType(Series.Type.COLUMN)  
	            .setChartTitleText(Character.toUpperCase(time.charAt(0)) + time.substring(1) + "ly " +title) 
	            .setChartSubtitleText(loc)
	            .setColumnPlotOptions(new ColumnPlotOptions()  
	                .setStacking(PlotOptions.Stacking.NORMAL)  
	            )  
	            .setLegend(new Legend()  
		            .setAlign(Legend.Align.RIGHT)  
	                .setVerticalAlign(Legend.VerticalAlign.BOTTOM)   
	                .setFloating(true)  
	                .setBackgroundColor("#FFFFFF")  
	                .setBorderColor("#CCC")  
	                .setBorderWidth(1)  
	                .setShadow(false)   
	            )  
	            .setToolTip(new ToolTip()  
	                .setFormatter(new ToolTipFormatter() {  
	                    public String format(ToolTipData toolTipData) {  
	                        return "<b>" + toolTipData.getXAsString() + "("+time+"): " + "</b><br/>" +  
	                            toolTipData.getSeriesName() + " : " + toolTipData.getYAsLong() + "<br/>" +  
	                            "Total: " + toolTipData.getTotal();  
	                    }  
	                })  
	            );  
	  
	        chart.getXAxis()  
	            .setCategories(timeData).setAxisTitle(new AxisTitle()  
	            .setText(time).setStyle(new Style().setFontSize("16px"))  
	                    ); 
	  
	        chart.getYAxis()  
	            .setMin(0)  
	            .setAxisTitle(new AxisTitle()  
	            .setText(yLabel).setStyle(new Style().setFontSize("16px"))  
                );  
	  
	        chart.addSeries(chart.createSeries()  
	            .setName(primaryTitle)  
	            .setPoints(primaryData) 
	            .setPlotOptions(new ColumnPlotOptions()  
        				.setColor("#89A54E")
	            	) 
	        );  
	        chart.addSeries(chart.createSeries()  
	            .setName(secondaryTitle)  
	            .setPoints(secondaryData)  
	            .setPlotOptions(new ColumnPlotOptions()  
                		.setColor("#A74945")
	            	) 
	        );
	  
	        return chart;  
	    }  
		
	public static Chart createCombinationChart(String[] xAxisData, final String xLabel, String yLabel, ArrayList<GraphData> dataList, String title, String loc) {  
		 
		final String primaryTitle = dataList.get(0).getTitle();
		final Number[] primaryData = dataList.get(0).getData();
		
        final Chart chart = new Chart()
            .setChartTitleText(xLabel + "ly " +title) 
            .setChartSubtitleText(loc)
            .setZoomType(BaseChart.ZoomType.X_AND_Y)  
            .setToolTip(new ToolTip()  
                .setFormatter(new ToolTipFormatter() {  
                    public String format(ToolTipData toolTipData) {  
                        return "<b>" + toolTipData.getXAsString() + " ("+xLabel+")" + "</b><br/>" +
                        		  toolTipData.getSeriesName() + " : " + toolTipData.getYAsDouble() +  
                                  (primaryTitle.equals(toolTipData.getSeriesName()) ? "" : " %");  
                    }  
                })  
            )  
            .setLegend(new Legend()  
	            .setAlign(Legend.Align.RIGHT)  
	            .setVerticalAlign(Legend.VerticalAlign.BOTTOM) 
	            .setFloating(true)  
	            .setBackgroundColor("#FFFFFF")  
	            .setBorderColor("#CCC")  
	            .setBorderWidth(1)  
	            .setShadow(false)  
            );  
  
        chart.getXAxis()  
            .setCategories(  
            		xAxisData 
            ).setAxisTitle(new AxisTitle()  
            .setText(xLabel).setStyle(new Style().setFontSize("16px"))  
        );  
  
        // Primary yAxis  
        chart.getYAxis(1).setMax(getMaxValue(primaryData))  
            .setAxisTitle(new AxisTitle()  
                .setText(primaryTitle).setStyle(new Style().setFontSize("16px"))  
            )
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor("#A74945")  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + "";  
                    }  
                })  
            );
        
        chart.addSeries(chart.createSeries()  
                .setName(primaryTitle)  
                .setType(Series.Type.COLUMN)  
                .setPlotOptions(new ColumnPlotOptions()  
                    .setColor("#A74945")
                ) 
                .setYAxis(1) 
                .setPoints(primaryData) 
            ); 
        
        // Secondary yAxis  
        chart.getYAxis(0).setMin(0).setMax(getMaxValueFromSecondaryData(dataList))
            .setAxisTitle(new AxisTitle()  
            .setText(yLabel).setStyle(new Style().setFontSize("16px"))  
            )  
            .setOpposite(true)  
            .setLabels(new YAxisLabels()  
                .setStyle(new Style()  
                    .setColor("#89A54E")  
                )  
                .setFormatter(new AxisLabelsFormatter() {  
                    public String format(AxisLabelsData axisLabelsData) {  
                        return axisLabelsData.getValueAsLong() + " %";  
                    }  
                })  
            );  
  

        for (int i=1; i<dataList.size(); i++) {
        	
        	 chart.addSeries(chart.createSeries()  
        	            .setName(dataList.get(i).getTitle())  
        	            .setType(Series.Type.LINE)  
        	            .setPoints(dataList.get(i).getData())
        	        ); 
        	
        }
        
        return chart;  
    }  
	
	public static Number getMaxValue(Number[] data){
		
		Number no = data[0];
		for(Number n: data){
			if(n.intValue() > no.intValue())
				no = n;
		}
		
		return no;
	}
	
	
	public static Number getMaxValueFromSecondaryData(ArrayList<GraphData> dataList){
		
		Number n = 0;
		for(int i=1; i<dataList.size(); i++){
			
			Number temp = getMaxValue(dataList.get(i).getData());
			if(temp.intValue() > n.intValue())
				n = temp;
		}
		
		return n;
	}
	

}
