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
package com.ihsinformatics.minetbdashboard.shared;

/**
 * @author Rabbia
 *
 */
public class GraphData {
	
	private String title;
	private int size;
	private Number[] data;
	private String seriesType;
	private int id;
	
	public String getSeriesType() {
		return seriesType;
	}
	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Number[] getData() {
		return data;
	}
	public void setData(Number[] data) {
		this.data = data;
		this.size = data.length;
	}
	
	public int getSize() {
		return size;
	}
	
	
	/**
	 * @param title
	 * @param data
	 */
	public GraphData(String title, Number[] data) {
		super();
		this.title = title;
		this.data = data;
		this.size = data.length;
	}
	
	public GraphData(String title, Number[] data, String seriesType, int id) {
		super();
		this.title = title;
		this.data = data;
		this.size = data.length;
		this.seriesType = seriesType;
		this.id = id;
	}

}
