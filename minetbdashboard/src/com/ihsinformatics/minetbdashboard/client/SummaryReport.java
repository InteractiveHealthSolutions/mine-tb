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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;

/**
 * 
 * Summary Table Report Page
 * @author Rabbia
 *
 */
public class SummaryReport extends Composite 
{
	private static ServerServiceAsync service = GWT.create(ServerService.class);
	
	// Summary Reports Widgets
	private HTML summaryLabel = new HTML("<font size=\"6\"> Summary Report </font> <br> <br> ");
	
	private VerticalPanel mainVerticalPanel = new VerticalPanel();
	private FlexTable summaryFlexTable = new FlexTable();
	
	private Grid screenedUguGrid = new Grid(1, 2);
	private Grid screenedeThekwiniGrid = new Grid(1, 2);
	private Grid screenedTotalGrid = new Grid(1, 2);
	private Grid suspectUguGrid = new Grid(1, 2);
	private Grid suspecteThekwiniGrid = new Grid(1, 2);
	private Grid suspectTotalGrid = new Grid(1, 2);
	private Grid sputumSubmittedUguGrid = new Grid(1, 2);
	private Grid sputumSubmittedeThekwiniGrid = new Grid(1, 2);
	private Grid sputumSubmittedTotalGrid = new Grid(1, 2);
	private Grid sputumResultUguGrid = new Grid(1, 2);
	private Grid sputumResulteThekwiniGrid = new Grid(1, 2);
	private Grid sputumResultTotalGrid = new Grid(1, 2);
	private Grid positiveResultUguGrid = new Grid(1, 2);
	private Grid positiveResulteThekwiniGrid = new Grid(1, 2);
	private Grid positiveResultTotalGrid = new Grid(1, 2);
	private Grid negativeResultUguGrid = new Grid(1, 2);
	private Grid negativeResulteThekwiniGrid = new Grid(1, 2);
	private Grid negativeResultTotalGrid = new Grid(1, 2);
	private Grid errorResultUguGrid = new Grid(1, 2);
	private Grid errorResulteThekwiniGrid = new Grid(1, 2);
	private Grid errorResultTotalGrid = new Grid(1, 2);
	private Grid noResultUguGrid = new Grid(1, 2);
	private Grid noResulteThekwiniGrid = new Grid(1, 2);
	private Grid noResultTotalGrid = new Grid(1, 2);
	private Grid rifResistenceUguGrid = new Grid(1, 2);
	private Grid rifResistenceeThekwiniGrid = new Grid(1, 2);
	private Grid rifResistenceTotalGrid = new Grid(1, 2);
	private Grid treatmentInitiatedUguGrid = new Grid(1, 2);
	private Grid treatmentInitiatedeThekwiniGrid = new Grid(1, 2);
	private Grid treatmentInitiatedTotalGrid = new Grid(1, 2);
	private Grid treatmentInitiatedRifResistanceUguGrid = new Grid(1, 2);
	private Grid treatmentInitiatedRifResistanceeThekwiniGrid = new Grid(1, 2);
	private Grid treatmentInitiatedRifResistanceTotalGrid = new Grid(1, 2);
	
	
	public SummaryReport ()
	{	
		
		mainVerticalPanel.setSize("100%", "100%");
		mainVerticalPanel.add(summaryLabel);
		summaryLabel.setStyleName("MineTBHeader");
		
		createSummaryFlexTable();
		
		summaryFlexTable.getElement().setAttribute("align", "center");
		mainVerticalPanel.add(summaryFlexTable);
		
		getSummaryData();
		
	}
	
	/**
	 * 
	 *  Creates Flextable for Summary Report
	 */
	public void createSummaryFlexTable(){
		
		// Column Headings
		summaryFlexTable.setText(0, 1, "Ugu");
		summaryFlexTable.setText(0, 2, "eThekwini");
		summaryFlexTable.setText(0, 3, "Total");

		summaryFlexTable.getCellFormatter().addStyleName(0, 0,"columnheaderStyle");
		summaryFlexTable.getCellFormatter().addStyleName(0, 1,"columnheaderStyle");
		summaryFlexTable.getCellFormatter().addStyleName(0, 2,"columnheaderStyle");
		summaryFlexTable.getCellFormatter().addStyleName(0, 3,"columnheaderStyle");

		for (int i = 0; i <= 12; i++) {

			summaryFlexTable.getCellFormatter().setWidth(i, 1, "150px");
			summaryFlexTable.getCellFormatter().setWidth(i, 2, "150px");
			summaryFlexTable.getCellFormatter().setWidth(i, 3, "150px");

		}

		//SubHeadings for main columns
		Grid grid1 = new Grid(1, 2);
		Grid grid2 = new Grid(1, 2);
		Grid grid3 = new Grid(1, 2);

		grid1.setText(0, 0, "N");
		grid1.setText(0, 1, "%");
		grid2.setText(0, 0, "N");
		grid2.setText(0, 1, "%");
		grid3.setText(0, 0, "N");
		grid3.setText(0, 1, "%");

		summaryFlexTable.setWidget(1, 1, grid1);
		summaryFlexTable.setWidget(1, 2, grid2);
		summaryFlexTable.setWidget(1, 3, grid3);

		grid1.getCellFormatter().addStyleName(0, 0, "gridStyle");
		grid2.getCellFormatter().addStyleName(0, 0, "gridStyle");
		grid3.getCellFormatter().addStyleName(0, 0, "gridStyle");

		grid1.getCellFormatter().addStyleName(0, 1, "gridStyle");
		grid2.getCellFormatter().addStyleName(0, 1, "gridStyle");
		grid3.getCellFormatter().addStyleName(0, 1, "gridStyle");

		// Row Headings
		summaryFlexTable.setText(2, 0, "Screened");
		summaryFlexTable.setText(3, 0, "Presumptive TB + High Risk");
		summaryFlexTable.setText(4, 0, "Able to Produce Sputum");
		summaryFlexTable.setText(5, 0, "Sputum Results");
		summaryFlexTable.setText(6, 0, "Positive");
		summaryFlexTable.setText(7, 0, "Negative");
		summaryFlexTable.setText(8, 0, "Error/Rejected");
		summaryFlexTable.setText(9, 0, "No Result/Missing");
		summaryFlexTable.setText(10, 0, "RIF Resistant Cases");
		summaryFlexTable.setText(11, 0, "Initiated on Treatment");
		summaryFlexTable.setText(12, 0,	"Rif Resistant Cases Initiated on Treatment");

		// Settings Grids in Summary FlexTable
		summaryFlexTable.setWidget(2, 1, screenedUguGrid);
		summaryFlexTable.setWidget(3, 1, suspectUguGrid);
		summaryFlexTable.setWidget(4, 1, sputumSubmittedUguGrid);
		summaryFlexTable.setWidget(5, 1, sputumResultUguGrid);
		summaryFlexTable.setWidget(6, 1, positiveResultUguGrid);
		summaryFlexTable.setWidget(7, 1, negativeResultUguGrid);
		summaryFlexTable.setWidget(8, 1, errorResultUguGrid);
		summaryFlexTable.setWidget(9, 1, noResultUguGrid);
		summaryFlexTable.setWidget(10, 1, rifResistenceUguGrid);
		summaryFlexTable.setWidget(11, 1, treatmentInitiatedUguGrid);
		summaryFlexTable.setWidget(12, 1, treatmentInitiatedRifResistanceUguGrid);

		summaryFlexTable.setWidget(2, 2, screenedeThekwiniGrid);
		summaryFlexTable.setWidget(3, 2, suspecteThekwiniGrid);
		summaryFlexTable.setWidget(4, 2, sputumSubmittedeThekwiniGrid);
		summaryFlexTable.setWidget(5, 2, sputumResulteThekwiniGrid);
		summaryFlexTable.setWidget(6, 2, positiveResulteThekwiniGrid);
		summaryFlexTable.setWidget(7, 2, negativeResulteThekwiniGrid);
		summaryFlexTable.setWidget(8, 2, errorResulteThekwiniGrid);
		summaryFlexTable.setWidget(9, 2, noResulteThekwiniGrid);
		summaryFlexTable.setWidget(10, 2, rifResistenceeThekwiniGrid);
		summaryFlexTable.setWidget(11, 2, treatmentInitiatedeThekwiniGrid);
		summaryFlexTable.setWidget(12, 2, treatmentInitiatedRifResistanceeThekwiniGrid);

		summaryFlexTable.setWidget(2, 3, screenedTotalGrid);
		summaryFlexTable.setWidget(3, 3, suspectTotalGrid);
		summaryFlexTable.setWidget(4, 3, sputumSubmittedTotalGrid);
		summaryFlexTable.setWidget(5, 3, sputumResultTotalGrid);
		summaryFlexTable.setWidget(6, 3, positiveResultTotalGrid);
		summaryFlexTable.setWidget(7, 3, negativeResultTotalGrid);
		summaryFlexTable.setWidget(8, 3, errorResultTotalGrid);
		summaryFlexTable.setWidget(9, 3, noResultTotalGrid);
		summaryFlexTable.setWidget(10, 3, rifResistenceTotalGrid);
		summaryFlexTable.setWidget(11, 3, treatmentInitiatedTotalGrid);
		summaryFlexTable.setWidget(12, 3, treatmentInitiatedRifResistanceTotalGrid);

		// All Grids...
		Grid[] gridsArray = new Grid[] { grid1, grid2, grid3, screenedUguGrid,
				screenedeThekwiniGrid, screenedTotalGrid, suspectUguGrid,
				suspecteThekwiniGrid, suspectTotalGrid, sputumSubmittedUguGrid,
				sputumSubmittedeThekwiniGrid, sputumSubmittedTotalGrid,
				sputumResultUguGrid, sputumResulteThekwiniGrid,
				sputumResultTotalGrid, positiveResultUguGrid,
				positiveResulteThekwiniGrid, positiveResultTotalGrid,
				negativeResultUguGrid, negativeResulteThekwiniGrid,
				negativeResultTotalGrid, errorResultUguGrid,
				errorResulteThekwiniGrid, errorResultTotalGrid,
				noResultUguGrid, noResulteThekwiniGrid, noResultTotalGrid,
				rifResistenceUguGrid, rifResistenceeThekwiniGrid,
				rifResistenceTotalGrid, treatmentInitiatedUguGrid,
				treatmentInitiatedeThekwiniGrid, treatmentInitiatedTotalGrid,
				treatmentInitiatedRifResistanceUguGrid,
				treatmentInitiatedRifResistanceeThekwiniGrid,
				treatmentInitiatedRifResistanceTotalGrid };

		// Styling Grids..
		for (Grid grid : gridsArray) {

			grid.getCellFormatter().addStyleName(0, 0, "gridNumberStyle");
			grid.getCellFormatter().addStyleName(0, 1, "gridPercentageStyle");
		}

		/// Styling Table  rows...
		for (int i = 1; i <= 12; i++) {

			if ((i & 1) == 0) {
				summaryFlexTable.getCellFormatter().addStyleName(i, 0, "evenColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 1, "evenColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 2, "evenColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 3, "evenColumnStyle");
			} else {
				summaryFlexTable.getCellFormatter().addStyleName(i, 0, "oddColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 1, "oddColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 2, "oddColumnStyle");
				summaryFlexTable.getCellFormatter().addStyleName(i, 3, "oddColumnStyle");
			}
		}
		
		
	}
	
	/**
	 * 
	 * @return mainVerticalPanel
	 */
	public VerticalPanel getComposite(){
		return mainVerticalPanel;
	}
	
	/**
	 * 
	 * Retrieve calculate, fills the Summary Report Table.
	 */
	private void getSummaryData(){
		
		// Total Screened and Suspect Numbers (Ugu + ethekwini + total)
		String query = "Select "
				+ "IFNULL(SUM(case when ( screening.district = 'Ugu') then screening.screened else 0 end),0), "
				+ "IFNULL(SUM(case when ( screening.district = 'eThekwini') then screening.screened else 0 end),0), "
				+ "SUM(screening.screened), "
				+ "IFNULL(SUM(case when ( screening.district = 'Ugu') then screening.suspects else 0 end),0), "
				+ "IFNULL(SUM(case when ( screening.district = 'eThekwini') then screening.suspects else 0 end),0), "
				+ "SUM(screening.suspects) "
				+ "from fact_screening as screening";
		try {
			service.getTableData(query.toString(),
					new AsyncCallback<String[][]>() {
						@Override
						public void onSuccess(final String[][] result) {

							// fill grids accordingly
							int screenedUguString = getNumber(result[0][0]);
							screenedUguGrid.setText(0, 0, String.valueOf(screenedUguString));

							int screenedeThekwiniString = getNumber(result[0][1]);
							screenedeThekwiniGrid.setText(0, 0, String.valueOf(screenedeThekwiniString));

							int screenedTotalString = getNumber(result[0][2]);
							screenedTotalGrid.setText(0, 0, String.valueOf(screenedTotalString));

							int suspectUguString = getNumber(result[0][3]);
				 			suspectUguGrid.setText(0, 0, String.valueOf(suspectUguString));

							int suspecteThekwiniString = getNumber(result[0][4]);
							suspecteThekwiniGrid.setText(0, 0, String.valueOf(suspecteThekwiniString));

							int suspectTotalString = getNumber(result[0][5]);
							suspectTotalGrid.setText(0, 0, String.valueOf(suspectTotalString));

							// Calculate percentages..
							float percentage;
							int per;

							percentage = (float) (suspectUguString * 100) / screenedUguString;
							per = (int) Math.round(percentage);
							suspectUguGrid.setText(0, 1, per + "%");

							percentage = (float) (suspecteThekwiniString * 100) / screenedeThekwiniString;
							per = (int) Math.round(percentage);
							suspecteThekwiniGrid.setText(0, 1, per + "%");

							percentage = (float) (suspectTotalString * 100) / screenedTotalString;
							per = (int) Math.round(percentage);
							suspectTotalGrid.setText(0, 1, per + "%");

							// Sputum Submissions & Results (Ugu + ethekwini + total)
							String query = "select "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.accepted_submissions else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.accepted_submissions else 0 end),0), "
									+ "SUM(sputum_result.accepted_submissions), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.total_results else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.total_results else 0 end),0), "
									+ "SUM(sputum_result.total_results), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.mtb_positives else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.mtb_positives else 0 end),0), "
									+ "SUM(sputum_result.mtb_positives), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.mtb_negatives else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.mtb_negatives else 0 end),0), "
									+ "SUM(sputum_result.mtb_negatives), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then (sputum_result.errors + sputum_result.invalid + sputum_result.unsuccessful + sputum_result.leaked + sputum_result.insufficient_quantity + sputum_result.others + sputum_result.incorrect_paperwork + sputum_result.rejected) else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then (sputum_result.errors + sputum_result.invalid + sputum_result.unsuccessful + sputum_result.leaked + sputum_result.insufficient_quantity + sputum_result.others + sputum_result.incorrect_paperwork + sputum_result.rejected) else 0 end),0), "
									+ "SUM(sputum_result.errors + sputum_result.invalid + sputum_result.unsuccessful + sputum_result.leaked + sputum_result.insufficient_quantity + sputum_result.others + sputum_result.incorrect_paperwork + sputum_result.rejected), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.no_results else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.no_results else 0 end),0), "
									+ "SUM(sputum_result.no_results), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'Ugu') then sputum_result.rif_resistants else 0 end),0), "
									+ "IFNULL(SUM(case when ( sputum_result.district = 'eThekwini') then sputum_result.rif_resistants else 0 end),0), "
									+ "SUM(sputum_result.rif_resistants) "
									+ "from fact_sputumresults as sputum_result;";
							try {
								service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
											@Override
											public void onSuccess(final String[][] result) {

												// fill grids accordingly
												int sputumSubmittedUguString = getNumber(result[0][0]);
												sputumSubmittedUguGrid.setText(0, 0, String.valueOf(sputumSubmittedUguString));

												int sputumSubmittedeThekwiniString = getNumber(result[0][1]);
												sputumSubmittedeThekwiniGrid.setText(0, 0, String.valueOf(sputumSubmittedeThekwiniString));

												int sputumSubmittedTotalString = getNumber(result[0][2]);
												sputumSubmittedTotalGrid.setText(0, 0, String.valueOf(sputumSubmittedTotalString));

												int suspectUguString = getNumber(suspectUguGrid.getText(0, 0));
												int suspecteThekwiniString = getNumber(suspecteThekwiniGrid.getText(0, 0));
												int suspectTotalString = getNumber(suspectTotalGrid.getText(0, 0));

												float percentage;
												int per;

												percentage = (float) (sputumSubmittedUguString * 100) / suspectUguString;
												per = (int) Math .round(percentage);
												sputumSubmittedUguGrid.setText( 0, 1, per + "%");

												percentage = (float) (sputumSubmittedeThekwiniString * 100) / suspecteThekwiniString;
												per = (int) Math .round(percentage);
												sputumSubmittedeThekwiniGrid .setText(0, 1, per + "%");

												percentage = (float) (sputumSubmittedTotalString * 100) / suspectTotalString;
												per = (int) Math .round(percentage);
												sputumSubmittedTotalGrid.setText(0, 1, per + "%");

												int sputumResultUguString = getNumber(result[0][3]);
												sputumResultUguGrid.setText( 0, 0, String.valueOf(sputumResultUguString));

												int sputumResulteThekwiniString = getNumber(result[0][4]);
												sputumResulteThekwiniGrid.setText(0, 0, String.valueOf(sputumResulteThekwiniString));

												int sputumResultTotalString = getNumber(result[0][5]);
												sputumResultTotalGrid.setText(0, 0, String.valueOf(sputumResultTotalString));

												int positiveResultUguString = getNumber(result[0][6]);
												positiveResultUguGrid.setText(0, 0, String.valueOf(positiveResultUguString));

												int positiveResulteThekwiniString = getNumber(result[0][7]);
												positiveResulteThekwiniGrid.setText(0, 0, String.valueOf(positiveResulteThekwiniString));

												int positiveResultTotalString = getNumber(result[0][8]);
												positiveResultTotalGrid.setText(0, 0, String.valueOf(positiveResultTotalString));

												percentage = (float) (positiveResultUguString * 100) / sputumResultUguString;
												per = (int) Math .round(percentage);
												positiveResultUguGrid.setText( 0, 1, per + "%");

												percentage = (float) (positiveResulteThekwiniString * 100) / sputumResulteThekwiniString;
												per = (int) Math .round(percentage);
												positiveResulteThekwiniGrid .setText(0, 1, per + "%");

												percentage = (float) (positiveResultTotalString * 100) / sputumResultTotalString;
												per = (int) Math .round(percentage);
												positiveResultTotalGrid .setText(0, 1, per + "%");

												int negativeResultUguString = getNumber(result[0][9]);
												negativeResultUguGrid.setText(0, 0, String.valueOf(negativeResultUguString));

												int negativeResulteThekwiniString = getNumber(result[0][10]);
												negativeResulteThekwiniGrid.setText(0, 0, String.valueOf(negativeResulteThekwiniString));

												int negativeResultTotalString = getNumber(result[0][11]);
												negativeResultTotalGrid.setText(0, 0, String.valueOf(negativeResultTotalString));

												int errorResultUguString = getNumber(result[0][12]);
												errorResultUguGrid.setText(0, 0, String.valueOf(errorResultUguString));

												int errorResulteThekwiniString = getNumber(result[0][13]);
												errorResulteThekwiniGrid.setText(0, 0, String.valueOf(errorResulteThekwiniString));

												int errorResultTotalString = getNumber(result[0][14]);
												errorResultTotalGrid.setText(0, 0, String.valueOf(errorResultTotalString));

												int noResultUguString = getNumber(result[0][15]);
												noResultUguGrid.setText(0, 0, String.valueOf(noResultUguString));

												int noResulteThekwiniString = getNumber(result[0][16]);
												noResulteThekwiniGrid.setText(0, 0, String.valueOf(noResulteThekwiniString));

												int noResultTotalString = getNumber(result[0][17]);
												noResultTotalGrid.setText(0, 0, String.valueOf(noResultTotalString));

												int rifResistantUguString = getNumber(result[0][18]);
												rifResistenceUguGrid.setText(0, 0, String.valueOf(rifResistantUguString));

												int rifResistanteThekwiniString = getNumber(result[0][19]);
												rifResistenceeThekwiniGrid.setText(0, 0, String.valueOf(rifResistanteThekwiniString));

												int rifResistantTotalString = getNumber(result[0][20]);
												rifResistenceTotalGrid.setText(0, 0, String.valueOf(rifResistantTotalString));

												// Treatment Initiated (Ugu + ethekwini + total)
												String query = "Select "
														+ "IFNULL(SUM(case when ( treatment.district = 'Ugu') then treatment.tx_initiated else 0 end),0), "
														+ "IFNULL(SUM(case when ( treatment.district = 'eThekwini') then treatment.tx_initiated else 0 end),0), "
														+ "SUM(treatment.tx_initiated) "
														+ "from fact_treatment as treatment;";

												try {
													service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
																@Override
																public void onSuccess(final String[][] result) {

																	int treatmentInitiatedUguString = getNumber(result[0][0]);
																	treatmentInitiatedUguGrid.setText(0, 0, String.valueOf(treatmentInitiatedUguString));

																	int treatmentInitiatedeThekwiniString = getNumber(result[0][1]);
																	treatmentInitiatedeThekwiniGrid.setText(0, 0, String.valueOf(treatmentInitiatedeThekwiniString));

																	int treatmentInitiatedTotalString = getNumber(result[0][2]);
																	treatmentInitiatedTotalGrid.setText(0, 0, String.valueOf(treatmentInitiatedTotalString));

																	int positiveResultUguString = getNumber(positiveResultUguGrid.getText(0,0));
																	int positiveResulteThekwiniString = getNumber(positiveResulteThekwiniGrid.getText(0,0));
																	int positiveResultTotalString = getNumber(positiveResultTotalGrid.getText(0,0));

																	float percentage;
																	int per;

																	percentage = (float) (treatmentInitiatedUguString * 100) / positiveResultUguString;
																	per = (int) Math.round(percentage);
																	treatmentInitiatedUguGrid.setText(0, 1, per + "%");

																	percentage = (float) (treatmentInitiatedeThekwiniString * 100) / positiveResulteThekwiniString;
																	per = (int) Math.round(percentage);
																	treatmentInitiatedeThekwiniGrid.setText(0, 1, per+ "%");

																	percentage = (float) (treatmentInitiatedTotalString * 100) / positiveResultTotalString;
																	per = (int) Math.round(percentage);
																	treatmentInitiatedTotalGrid.setText(0, 1, per + "%");

																	// Treatment Initiated for Rif Resistant (Ugu + ethekwini + total)
																	String query = "SELECT  SUM(CASE WHEN sd.district = 'Ugu' THEN 1 ELSE 0 END), SUM(CASE WHEN sd.district = 'eThekwini' THEN 1 ELSE 0 END), count(*) FROM minetb_dw.sputum_data sd left join minetb_dw.treatment_data td on sd.patient_id = td.patient_id where sd.rif_result like '%Dectected%' or sd.rif_result like '%Yes%' and td.treatment_initiated = 'Yes';";
																	try {
																		service.getTableData(query.toString(),new AsyncCallback<String[][]>() {
																					@Override
																					public void onSuccess(final String[][] result) {

																						// fill Grids accordingly
																						int treatmentInitiatedUguString = getNumber(result[0][0]);
																						treatmentInitiatedRifResistanceUguGrid.setText(0,0,String.valueOf(treatmentInitiatedUguString));

																						int treatmentInitiatedeThekwiniString = getNumber(result[0][1]);
																						treatmentInitiatedRifResistanceeThekwiniGrid.setText(0, 0, String.valueOf(treatmentInitiatedeThekwiniString));

																						int treatmentInitiatedTotalString = getNumber(result[0][2]);
																						treatmentInitiatedRifResistanceTotalGrid.setText(0, 0, String.valueOf(treatmentInitiatedTotalString));

																						int positiveResultUguString = getNumber(rifResistenceUguGrid.getText(0, 0));
																						int positiveResulteThekwiniString = getNumber(rifResistenceeThekwiniGrid.getText(0, 0));
																						int positiveResultTotalString = getNumber(rifResistenceTotalGrid.getText(0, 0));

																						float percentage;
																						int per;

																						percentage = (float) (treatmentInitiatedUguString * 100) / positiveResultUguString;
																						per = (int) Math.round(percentage);
																						treatmentInitiatedRifResistanceUguGrid.setText(0, 1, per + "%");

																						percentage = (float) (treatmentInitiatedeThekwiniString * 100) / positiveResulteThekwiniString;
																						per = (int) Math .round(percentage);
																						treatmentInitiatedRifResistanceeThekwiniGrid.setText(0, 1, per + "%");

																						percentage = (float) (treatmentInitiatedTotalString * 100) / positiveResultTotalString;
																						per = (int) Math .round(percentage);
																						treatmentInitiatedRifResistanceTotalGrid.setText(0, 1, per + "%");

																					}

																					@Override
																					public void onFailure(Throwable caught) {
																						Window.alert(CustomMessage.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
																					}
																				});
																	} catch (Exception e) {
																		e.printStackTrace();
																	}

																}

																@Override
																public void onFailure(Throwable caught) {
																	Window.alert(CustomMessage.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
																}
															});
												} catch (Exception e) {
													e.printStackTrace();
												}

											}

											@Override
											public void onFailure(Throwable caught) {
												Window.alert(CustomMessage.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
											}
										});
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(Throwable caught) {Window.alert(CustomMessage.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Converts String number to int
	 * 
	 * @param number
	 * @return int
	 */
	private int getNumber(String number) {

		String temp = number;
		if (number.contains(".")) {
			int i = number.indexOf(".");
			temp = number.substring(0, i);
		}

		return Integer.parseInt(temp);

	}
	
}