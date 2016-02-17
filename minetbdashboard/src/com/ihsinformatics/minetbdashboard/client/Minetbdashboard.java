package com.ihsinformatics.minetbdashboard.client;

import java.util.Date;
import java.util.HashSet;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ComboChart;
import com.googlecode.gwt.charts.client.corechart.ComboChartOptions;
import com.googlecode.gwt.charts.client.corechart.ComboChartSeries;
import com.googlecode.gwt.charts.client.corechart.LineChart;
import com.googlecode.gwt.charts.client.corechart.LineChartOptions;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.SeriesType;
import com.googlecode.gwt.charts.client.options.TitlePosition;
import com.googlecode.gwt.charts.client.options.VAxis;
import com.googlecode.gwt.charts.client.util.ChartHelper;
import com.ihsinformatics.minetbdashboard.shared.CollectionsUtil;
import com.ihsinformatics.minetbdashboard.shared.CustomMessage;
import com.ihsinformatics.minetbdashboard.shared.DataType;
import com.ihsinformatics.minetbdashboard.shared.ErrorType;
import com.ihsinformatics.minetbdashboard.shared.InfoType;
import com.ihsinformatics.minetbdashboard.shared.LocationDimension;
import com.ihsinformatics.minetbdashboard.shared.MineTB;
import com.ihsinformatics.minetbdashboard.shared.Parameter;
import com.ihsinformatics.minetbdashboard.shared.TimeDimenstion;
import com.sun.java.swing.plaf.windows.resources.windows;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Minetbdashboard implements EntryPoint, ClickHandler,
		KeyDownHandler, ChangeHandler {
	private static ServerServiceAsync service = GWT.create(ServerService.class);

	private static LoadingWidget loading = new LoadingWidget();
	private static RootPanel rootPanel;

	static VerticalPanel verticalPanel = new VerticalPanel();
	static VerticalPanel footerPanel = new VerticalPanel();
	static VerticalPanel userPanel = new VerticalPanel();
	static VerticalPanel chartHeaderPanel = new VerticalPanel();

	private FlexTable footerFlexTable = new FlexTable();
	private Image irdLogoImage = new Image("\\images\\irdSaLogo.png");
	private Image aurumLogoImage = new Image("\\images\\aurumLogo.png");
	private Image openmrsLogoImage = new Image("\\images\\openmrsLogo.png");
	private Image androidLogoImage = new Image("\\images\\androidLogo.png");
	
	private FlexTable headerFlexTable = new FlexTable();
	private FlexTable loginFlexTable = new FlexTable();
	private FlexTable optionsTable = new FlexTable();
	private FlexTable dateFilterTable = new FlexTable();

	private HTML formHeadingLabel = new HTML("<font size=\"4\"> Welcome to Reporting Dashboard. Please login to proceed. </font> ");
	private Label userNameLabel = new Label("User ID:");
	private Label passwordLabel = new Label("Password:");
	private Label logoutLabel = new Label("Logout");
	private Label loggedInLabel = new Label("Logged in as: ");
	private Label userLoggedInLabel = new Label();
	
	private Grid grid = new Grid(1, 2);

	private TextBox userTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();

	private HTML reportingOptionsLabel = new HTML("<font size=\"4\"> Reporting Options </font> <br> <br> ");
	
	private ListBox reportsList = new ListBox();
	private ListBox locationDimensionList = new ListBox();
	private ListBox timeDimensionList = new ListBox();
	
	private ListBox yearFrom = new ListBox();
	private ListBox yearTo = new ListBox();
	private ListBox quarterFrom = new ListBox();
	private ListBox quarterTo = new ListBox();
	private ListBox monthFrom = new ListBox();
	private ListBox monthTo = new ListBox();
	private ListBox weekFrom = new ListBox();
	private ListBox weekTo = new ListBox();

	private Button loginButton = new Button("Login");
	private Button showButton = new Button("Show Report");
	private Button clearButton = new Button("Clear");

	/* Chart objects */
	ChartLoader chartLoader;
	private VerticalPanel chartPanel = new VerticalPanel();
	

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		
		rootPanel = RootPanel.get();
		rootPanel.setStyleName("rootPanel");
		rootPanel.add(userPanel);
		rootPanel.add(verticalPanel);
		rootPanel.add(chartHeaderPanel);
		rootPanel.add(chartPanel);
		rootPanel.add(footerPanel);
		
		chartPanel.setVisible(false);
		userPanel.setVisible(false);
		footerPanel.setVisible(true);
		chartHeaderPanel.clear();
		
		chartHeaderPanel.setSize("100%", "");
		verticalPanel.setSize("100%","");
				
		footerPanel.setSize("100%", "100%");
		footerFlexTable.setWidget(0, 0, openmrsLogoImage);
		footerFlexTable.setWidget(0, 1, irdLogoImage);
		footerFlexTable.setWidget(0, 2, aurumLogoImage);
		footerFlexTable.setWidget(0, 3, androidLogoImage);
		
		HTML line = new HTML("<br> <hr  style=\"width:100%;\" />");
		footerPanel.add(line);
		footerPanel.add(footerFlexTable);
		footerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		footerPanel.setCellHorizontalAlignment(footerFlexTable,HasHorizontalAlignment.ALIGN_CENTER);
		
		userPanel.add(logoutLabel);
		userPanel.getElement().setAttribute("align", "right");
		userPanel.add(grid);
		
		loggedInLabel.setStyleName("orange-text");
		userLoggedInLabel.setStyleName("bold-orange-text");
		
		userPanel.setCellHorizontalAlignment(logoutLabel,HasHorizontalAlignment.ALIGN_RIGHT);
		userPanel.setCellHorizontalAlignment(grid,HasHorizontalAlignment.ALIGN_RIGHT);
		
		grid.setWidget(0,0, loggedInLabel);
		grid.setWidget(0, 1, userLoggedInLabel);
		
		logoutLabel.setStyleName("logout-button");
		
		logoutLabel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	logout();
            }
        });
		
		openmrsLogoImage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Window.open("https://105.235.161.203:8443/openmrs","_blank","enabled");
            }
        });
		
		irdLogoImage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Window.open("http://www.irdresearch.org/","_blank","enabled");
            }
        });
		
		aurumLogoImage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Window.open("http://www.auruminstitute.org/","_blank","enabled");
            }
        });
		
		androidLogoImage.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	Window.open("https://play.google.com/store/apps/details?id=com.ihsinformatics.tbr4mobile&hl=en","_blank","enabled");
            }
        });
		
		headerFlexTable.setWidget(0, 1, formHeadingLabel);
		headerFlexTable.getRowFormatter().addStyleName(0, "MineTBHeader");
		
		loginFlexTable.setWidget(1, 0, userNameLabel);
		loginFlexTable.setWidget(1, 1, userTextBox);
		loginFlexTable.setWidget(2, 0, passwordLabel);
		loginFlexTable.setWidget(2, 1, passwordTextBox);
		loginFlexTable.setWidget(3, 1, loginButton);

		verticalPanel.getElement().setAttribute("align", "center");
		verticalPanel.add(headerFlexTable);
		verticalPanel.setCellHorizontalAlignment(headerFlexTable,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(loginFlexTable);
		verticalPanel.setCellHorizontalAlignment(loginFlexTable,HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		
		chartPanel.setSize("100%", "100%");
		chartPanel.getElement().setAttribute("align", "center");
		reportingOptionsLabel.setStyleName("MineTBHeader");
		
		optionsTable.setStyleName("ReportingFlexTable");
		optionsTable.setWidget(0, 0, new Label("Report:"));
		optionsTable.setWidget(0, 1, reportsList);
		optionsTable.setWidget(1, 0, new Label("Reporting Level:"));
		optionsTable.setWidget(1, 1, locationDimensionList);
		optionsTable.setWidget(2, 0, new Label("Grouping:"));
		optionsTable.setWidget(2, 1, timeDimensionList);
		optionsTable.setWidget(3, 0, new Label("Select Date Range:"));
		optionsTable.setWidget(3, 1, dateFilterTable);
		
		reportsList.setWidth("300px");
		fillLists();
		
		loginButton.addClickHandler(this);
		showButton.addClickHandler(this);
		clearButton.addClickHandler(this);
		passwordTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				boolean enterPressed = event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER;
				if (enterPressed) {
					loginButton.click();
				}
			}
		});
		timeDimensionList.addChangeHandler(this);
		createDateFilterWidgets(TimeDimenstion.YEAR);
	}
	
	public void createDateFilterWidgets(TimeDimenstion time) {
		dateFilterTable.clear();
		switch (time) {
			case YEAR:
				dateFilterTable.setWidget(0, 0, yearFrom);
				dateFilterTable.setWidget(0, 1, yearTo);
				break;
			case MONTH:
				dateFilterTable.setWidget(0, 0, yearFrom);
				dateFilterTable.setWidget(1, 0, monthFrom);
				dateFilterTable.setWidget(1, 1, monthTo);
				break;
			case QUARTER:
				dateFilterTable.setWidget(0, 0, yearFrom);
				dateFilterTable.setWidget(1, 0, quarterFrom);
				dateFilterTable.setWidget(1, 1, quarterTo);
				break;
			case WEEK:
				dateFilterTable.setWidget(0, 0, yearFrom);
				dateFilterTable.setWidget(1, 0, weekFrom);
				dateFilterTable.setWidget(1, 1, weekTo);
				break;
			default:
		}
		
		login();
		
	}

	@SuppressWarnings("deprecation")
	public void fillLists() {
		String[] reports = { "Screening", "Sputum Submission", "GeneXpert: MTB Positive and Rif Resistants", "GeneXpert: MTB Negative and Other Results",
								"Treatment Initiated", "Treatment Not Initiated Reasons",
								"Followup Smear Results", "Treatment Outcome Results",
								"Sputum Submission Rate"
								};
		for (String str : reports) {
			reportsList.addItem(str);
		}
		for (LocationDimension dim : LocationDimension.values()) {
			locationDimensionList.addItem(dim.toString());
		}
		for (TimeDimenstion dim : TimeDimenstion.values()) {
			timeDimensionList.addItem(dim.toString());
		}
		for (int year = 2013; year <= new Date().getYear() + 1900; year++) {
			yearFrom.addItem(String.valueOf(year));
			yearTo.addItem(String.valueOf(year));
		}
		for (int quarter = 1; quarter <= 4; quarter++) {
			quarterFrom.addItem(String.valueOf(quarter));
			quarterTo.addItem(String.valueOf(quarter));
		}
		for (int month = 1; month <= 12; month++) {
			monthFrom.addItem(String.valueOf(month));
			monthTo.addItem(String.valueOf(month));
		}
		for (int week = 1; week <= 52; week++) {
			weekFrom.addItem(String.valueOf(week));
			weekTo.addItem(String.valueOf(week));
		}
		// TODO: ONLY FOR TESTING
		userTextBox.setText("owais");
		passwordTextBox.setText("Jingle94$");
	}

	public void drawChart() {
		
		chartHeaderPanel.clear();
		
		Label headingLabel = new Label(MineTBClient.get(reportsList).toUpperCase() + " by " + MineTBClient.get(locationDimensionList) + " per " + MineTBClient.get(timeDimensionList)); 
		chartHeaderPanel.add(headingLabel);
		
		headingLabel.setStyleName("ReportHeader");
		chartHeaderPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		chartHeaderPanel.setCellHorizontalAlignment(headingLabel,HasHorizontalAlignment.ALIGN_CENTER);
		
		chartPanel.clear();
		
		String report = MineTBClient.get(reportsList);
		if (report.equals("Screening")) {
			drawScreening();
		}
		else if (report.equals("Sputum Submission")){
			drawSubmission();
		}
		else if (report.equals("GeneXpert: MTB Positive and Rif Resistants")){
			drawMtbPositive();
		}
		else if (report.equals("GeneXpert: MTB Negative and Other Results")){
			drawMtbNegative();
		}
		else if (report.equals("Treatment Initiated")){
			drawTreatmentInitiated();
		}
		else if (report.equals("Treatment Not Initiated Reasons")){
			drawTreatmentNotInitiated();
		}
		else if (report.equals("Followup Smear Results")){
			drawFollowupSmearResults();
		}
		else if (report.equals("Treatment Outcome Results")){
			drawTreatmentOutcomeResults();
		}
		else if (report.equals("Sputum Submission Rate")){
			drawSputumSubmissionRate();
		}
		
	}
	
	/**
	 * Applies filter picked from date fields w.r.t. selected time dimension
	 * @param params
	 * @return
	 */
	private String getFilter(Parameter[] params, String alias) {
		
		if(!alias.equals(""))
			alias = alias+".";
		
		StringBuilder where = new StringBuilder(" where 1 = 1 ");
		// Append Date filter
		String yFrom = MineTBClient.get(yearFrom);
		String yTo = MineTBClient.get(yearTo);
		String qFrom = MineTBClient.get(quarterFrom);
		String qTo = MineTBClient.get(quarterTo);
		String mFrom = MineTBClient.get(monthFrom);
		String mTo = MineTBClient.get(monthTo);
		String wFrom = MineTBClient.get(weekFrom);
		String wTo = MineTBClient.get(weekTo);
		TimeDimenstion time = TimeDimenstion.valueOf(MineTBClient.get(timeDimensionList));
		switch(time) {
		case YEAR:
			where.append(" and "+alias+"year between " + yFrom + " and " + yTo);
			break;
		case QUARTER:
			where.append(" and "+alias+"year = " + yFrom);
			where.append(" and "+alias+"quarter between " + qFrom + " and " + qTo);
			break;
		case MONTH:
			where.append(" and "+alias+"year = " + yFrom);
			where.append(" and "+alias+"month between " + mFrom + " and " + mTo);
			break;
		case WEEK:
			where.append(" and "+alias+"year = " + yFrom);
			where.append(" and "+alias+"week between " + wFrom + " and " + wTo);
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
	
	private double findValueInData(String[][] data, String columnValue, String rowValue, int valueIndex) {
		double value = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][1].equals(columnValue) && data[i][0].equals(rowValue)) {
				value = Double.parseDouble(data[i][valueIndex]);
			}
		}
		return value;
	}
	
	private void drawLineChart(String[][] data, int valueIndex, String xLabel, String yLabel) {
		DataTable dataTable = DataTable.create();
		String[] timesStr = getUniqueValues(data, 0);
		//Double[] times = CollectionsUtil.convertToNumeric(timesStr);
		//times = CollectionsUtil.sortArray(times);
		String[] locations = getUniqueValues(data, 1);
		// Add grouping column for time dimension
		dataTable.addColumn(ColumnType.STRING, MineTBClient.get(timeDimensionList).toLowerCase());
		// Add number of rows equal to unique time dimensions
		dataTable.addRows(timesStr.length);
		// Add locations as columns
		for (String location : locations) {
			dataTable.addColumn(ColumnType.NUMBER, location);
		}
		for (int i = 0; i < timesStr.length; i++) {
			dataTable.setValue(i, 0, timesStr[i]);
		}
		// Convert values into 2D; 1st dimension is locations, 2nd is time
		for (int col = 0; col < locations.length; col++) {
			for (int row = 0; row < timesStr.length; row++) {
				double value = findValueInData(data, locations[col], timesStr[row], valueIndex);
				dataTable.setValue(row, col + 1, value);
			}
		}
		// Set options
		LineChartOptions options = LineChartOptions.create();
		options.setBackgroundColor("#f0f0f0");
		options.setHAxis(HAxis.create(xLabel));
		options.setVAxis(VAxis.create(yLabel));
		options.setLegend(Legend.create(LegendPosition.TOP));
		
		HTML lineBreak = new HTML("<br>");
		chartPanel.add(lineBreak);
		HTML line = new HTML("<hr  style=\"width:100%;\" />");
		// Draw a line break
		chartPanel.add(line);
		LineChart lineChart = new LineChart();
		// Draw the chart
		lineChart.draw(dataTable, options);
		chartPanel.add(lineChart);
		// Draw another line break
		HTML secondLine = new HTML("<hr  style=\"width:100%;\" />");
		chartPanel.add(secondLine);
	}
	
	private void drawComboChart(String[][] data, String xLabel, String yLabel, String lineBar) {
		
	 DataTable dataTable = DataTable.create();
	 String[] timesStr = getUniqueValues(data, 0);
	 String[] locations = getUniqueValues(data, 1);
	 // Add grouping column for time dimension
	 dataTable.addColumn(ColumnType.STRING, MineTBClient.get(timeDimensionList).toLowerCase());
	 // Add number of rows equal to unique time dimensions
	 dataTable.addRows(timesStr.length);
	 // Add locations as columns
	 dataTable.addColumn(ColumnType.NUMBER, locations[0]);
	 // Add Sputum Submission
	 dataTable.addColumn(ColumnType.NUMBER, lineBar);
	 

	 for (int i = 0; i < timesStr.length; i++) {
			dataTable.setValue(i, 0, timesStr[i]);
	 }
	 
	// Convert values into 2D; 1st dimension is locations, 2nd is time
	for (int col = 0; col < locations.length; col++) {
		for (int row = 0; row < timesStr.length; row++) {
			double value = findValueInData(data, locations[col], timesStr[row], 2);
			dataTable.setValue(row, col + 1, value);
			value = findValueInData(data, locations[col], timesStr[row], 3);
			dataTable.setValue(row, col + 2, value);
		}
	 }

	 // Set options
	 ComboChartOptions options = ComboChartOptions.create();
	 options.setBackgroundColor("#f0f0f0");
	 options.setHAxis(HAxis.create(xLabel));
	 options.setVAxis(VAxis.create(yLabel));
	 options.setSeriesType(SeriesType.BARS);
	 ComboChartSeries series = ComboChartSeries.create();
	 series.setType(SeriesType.LINE);
	 options.setSeries(1, series);
	 options.setLegend(Legend.create(LegendPosition.TOP));
	
	 HTML lineBreak = new HTML("<br>");
	 chartPanel.add(lineBreak);
	 HTML line = new HTML("<hr  style=\"width:100%;\" />");
	 // Draw a line break
	 chartPanel.add(line);
	 ComboChart chart = new ComboChart();
	 // Draw the chart
	 chart.draw(dataTable, options);
	 chartPanel.add(chart);
	 // Draw another line break
	 HTML secondLine = new HTML("<hr  style=\"width:100%;\" />");
	 chartPanel.add(secondLine);
	
	}
	
	private void drawScreening() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(screened) as screened, sum(suspects) as suspects, sum(non_suspects) as non_suspects from fact_screening ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "SCREENED");
							drawLineChart(result, 3, time, "PRESUMPTIVE");
							drawLineChart(result, 4, time, "NON-SUSPECTS");
							load(false);
						}
					});
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
	
	private void drawSubmission() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(total_submissions) as total, sum(accepted_submissions) as accepted, sum(rejected_submissions) as rejected from fact_sputumresults ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Total Submissions");
							drawLineChart(result, 3, time, "Accepted Submissions");
							drawLineChart(result, 4, time, "Rejected Submissions");
							load(false);
						}
					});
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
	
	private void drawMtbPositive() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(total_results) as total, sum(mtb_positives) as positive, sum(rif_resistants) as rif from fact_sputumresults ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Total GeneXpert Results");
							drawLineChart(result, 3, time, "MTB Postive");
							drawLineChart(result, 4, time, "Rif Resistants");
							load(false);
						}
					});
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
	
	private void drawMtbNegative() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(total_results) as total, sum(mtb_negatives) as negative, sum(unsuccessful) as successful, sum(leaked) as leaked, sum(insufficient_quantity) as insufficient_quantity, sum(incorrect_paperwork) as incorrect_paperwork , sum(rejected) as rejected, sum(errors) as error, sum(invalid) as invalid, sum(no_results) as no_result, sum(others) as other  from fact_sputumresults ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Total GeneXpert Results");
							drawLineChart(result, 3, time, "MTB Negative");
							drawLineChart(result, 4, time, "Unsuccessful");
							drawLineChart(result, 5, time, "Leaked");
							drawLineChart(result, 6, time, "Insufficient Quantity");
							drawLineChart(result, 7, time, "Incorrect Paperwork");
							drawLineChart(result, 8, time, "Rejected");
							drawLineChart(result, 9, time, "Error");
							drawLineChart(result, 10, time, "Invalid");
							drawLineChart(result, 11, time, "No Result");
							drawLineChart(result, 12, time, "Others");
							load(false);
						}
					});
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
	
	private void drawTreatmentInitiated() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(tx_initiated) as tx_initiated, sum(tx_initiated_at_clinic) as initiated_at_clinic, sum(tx_initiated_tranferred) as initiated_transferred_out from fact_treatment ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Total Treatment Initiated");
							drawLineChart(result, 3, time, "Treatment initiated at clinics");
							drawLineChart(result, 4, time, "Treatment transferred out");
							load(false);
						}
					});
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
	
	private void drawTreatmentNotInitiated() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(tx_not_initiated) as tx_not_initiated, sum(patient_refused_tx) as patient_refused, sum(patient_not_found) as not_found, sum(contact_info_missing) as info_missing, sum(patient_died) as died, sum(already_on_tx) as already_on_tx from fact_treatment ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Total Treatment Not Initiated");
							drawLineChart(result, 3, time, "Patient Refused Treatment");
							drawLineChart(result, 4, time, "Couldn't found patient from home visit");
							drawLineChart(result, 5, time, "Clinic didn't have address or phone number");
							drawLineChart(result, 6, time, "Patient Died");
							drawLineChart(result, 7, time, "Patient already on Treatment");
							load(false);
						}
					});
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
	
	private void drawFollowupSmearResults() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(followup_negative) as negative, sum(followup_pos_scanty) as positive_scanty, sum(followup_pos_plus_plus) as positive_plus_plus, sum(followup_pos_plus_plus_plus) as postive_plus_plus_plus, sum(followup_leaked) as leaked from fact_treatment ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Negative");
							drawLineChart(result, 3, time, "Positive Scanty");
							drawLineChart(result, 4, time, "Positive (++)");
							drawLineChart(result, 5, time, "Positive (+++)");
							drawLineChart(result, 6, time, "Leaked");
							load(false);
						}
					});
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
	
	private void drawTreatmentOutcomeResults() {
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select " + time + ", ");
		query.append(location + ", ");
		query.append("sum(patient_cured) as cure, sum(tx_completed) as completed, sum(tx_default) as default_outcome, sum(tx_failure) as failure, sum(patient_death) as death, sum(patient_transferred_out) as transferred_out from fact_treatment ");
		query.append(getFilter(params,""));
		query.append(" group by " + time + ", " + location);
		query.append(" order by " + time + ", " + location);
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							drawLineChart(result, 2, time, "Cured");
							drawLineChart(result, 3, time, "Treatment Completed");
							drawLineChart(result, 4, time, "Default");
							drawLineChart(result, 5, time, "Treatment Failure");
							drawLineChart(result, 6, time, "Patient Death");
							drawLineChart(result, 6, time, "Patient Transferred Out");
							load(false);
						}
					});
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
	
	private void drawSputumSubmissionRate(){
		
		final String location = MineTBClient.get(locationDimensionList).toLowerCase();
		final String time = MineTBClient.get(timeDimensionList).toLowerCase();
		Parameter[] params = null;
		StringBuilder query = new StringBuilder();
		query.append("select s." + time + ", ");
		query.append("s." + location + ", ");
		query.append("sum(suspects) as suspects, sum(accepted_submissions) as submissions from fact_screening f, fact_sputumresults s ");
		query.append(getFilter(params,"s"));
		query.append(" group by s." + time + ", s." + location);
		query.append(" order by s." + time + ", s." + location);
		
		try {
			service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
				@Override
				public void onSuccess(final String[][] result) {
					ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
					chartLoader.loadApi(new Runnable() {
						@Override
						public void run() {
							
							String[] locations = getUniqueValues(result, 1);
							for(String loc: locations){
								String[][] locationData = getDataFor(loc, 1, result);
								drawComboChart(locationData, time, "Number of Suspects" , "Sputum Submissions");
							}
							
							load(false);
						}
					});
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
	
	public String[][] getDataFor(String locationName, int index, String[][] data){
		
		String[] time = getUniqueValues(data, 0);
		int len = time.length;
		String[][] result = new String[len][];
		
		int i = 0;
		for(String[] stringArray : data){
			
			if(stringArray[index].equals(locationName)){
				result[i] = stringArray;
				i++;
			}
		}
		
		return result;
	}
	

	/**
	 * Display/Hide main panel and loading widget
	 * 
	 * @param status
	 */
	public void load(boolean status) {
		verticalPanel.setVisible(!status);
		if (status)
			loading.show();
		else
			loading.hide();
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
		MineTB.setPassCode(password);
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

	/**
	 * To renew browsing session
	 * 
	 * @return true if renew was successful
	 */
	public static boolean renewSession() {
		String passcode = Window
				.prompt(CustomMessage
						.getErrorMessage(ErrorType.SESSION_EXPIRED)
						+ "\n"
						+ "Please enter first 4 characters of your password to renew session.",
						"");
		if (MineTBClient.verifyClientPasscode(passcode)) {
			Window.alert(CustomMessage.getInfoMessage(InfoType.SESSION_RENEWED));
			return true;
		}
		Window.alert(CustomMessage
				.getErrorMessage(ErrorType.AUTHENTICATION_ERROR));
		return false;
	}

	private void doLogin() {
		// Check for empty fields
		if (MineTBClient.get(userTextBox).equals("")
				|| MineTBClient.get(passwordTextBox).equals("")) {
			Window.alert(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR));
			return;
		}
		try {
			service.authenticate(MineTBClient.get(userTextBox),
					MineTBClient.get(passwordTextBox),
					new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							if (result) {
								Window.alert(CustomMessage
										.getInfoMessage(InfoType.ACCESS_GRANTED));
								setCookies(MineTBClient.get(userTextBox),
										String.valueOf(MineTBClient
												.getSimpleCode(MineTBClient
														.get(passwordTextBox)
														.substring(0, 3))),
										MineTBClient.get(passwordTextBox));
								login();
							} else {
								Window.alert(CustomMessage
										.getErrorMessage(ErrorType.AUTHENTICATION_ERROR));
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
	 * Handle User Login. If user is already logged in, main menu will display
	 * otherwise session renewal window will appear
	 */
	private void login() {
		final String userName;
		String passCode;
		String password;
		String sessionLimit;
		try {
			// Try to get Cookies
			userName = Cookies.getCookie("UserName");
			passCode = Cookies.getCookie("Pass");
			password = Cookies.getCookie("Password");
			sessionLimit = Cookies.getCookie("SessionLimit");
			if (userName == null || passCode == null || sessionLimit == null)
				throw new Exception();
			userTextBox.setText(userName);

			// If session is expired then renew
			if (new Date().getTime() > Long.parseLong(sessionLimit))
				if (!renewSession())
					throw new Exception();
			setCookies(userName, passCode, password);
			service.setCurrentUser(userName, new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					
					verticalPanel.clear();
					
					verticalPanel.add(reportingOptionsLabel);
					verticalPanel.add(optionsTable);
					
					HorizontalPanel buttonsPanel = new HorizontalPanel();
					buttonsPanel.add(showButton);
					buttonsPanel.add(clearButton);
					buttonsPanel.setSpacing(5);
					verticalPanel.add(buttonsPanel);
					
					Grid grid = new Grid(1,2);
					verticalPanel.add(grid);
					
					Label lastUpdatedLabel = new Label("Last updated on: ");
					grid.setWidget(0,0, lastUpdatedLabel);
					lastUpdatedLabel.setStyleName("orange-text");
					final Label dateLabel = new Label();
					grid.setWidget(0,1, dateLabel);
					dateLabel.setStyleName("bold-orange-text");
					
					grid.getElement().setAttribute("align", "right");
					
					HTML line = new HTML("<hr  style=\"width:100%;\" /> <br> ");
					verticalPanel.add(line);
					
					chartPanel.setVisible(true);
					userPanel.setVisible(true);
					footerPanel.setVisible(false);
					userLoggedInLabel.setText(userName);
					
					String query = "SELECT * FROM minetb_dw.meta_data where tag = 'last_updated';";
					try {
						service.getTableData(query.toString(), new AsyncCallback<String[][]>() {
							@Override
							public void onSuccess(final String[][] result) {
							
								dateLabel.setText(result[0][1]);
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(CustomMessage.getErrorMessage(ErrorType.DATA_ACCESS_ERROR));
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
					load(false);
				}

				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					load(false);
				}
			});
		} catch (Exception e) {
			loginFlexTable.setVisible(true);
			load(false);
		}
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
	 * Remove all widgets from application
	 */
	public static void flushAll() {
		rootPanel.clear();
		rootPanel.add(new HTML("Application has been shut down. It is now safe to close the Browser window."));
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		load(true);
		if (sender == loginButton) {
			doLogin();
		} else if (sender == showButton) {
			drawChart();
		} else if (sender == clearButton) {
			load(false);
			chartPanel.clear();
			chartHeaderPanel.clear();
		}
	}

	@Override
	public void onKeyDown(KeyDownEvent event) {
		Object source = event.getSource();
		if (source == passwordTextBox || source == userTextBox) {
			doLogin();
		}
	}

	@Override
	public void onChange(ChangeEvent event) {
		Object source = event.getSource();
		if (source == timeDimensionList) {
			TimeDimenstion time = TimeDimenstion.valueOf(MineTBClient.get(timeDimensionList));
			createDateFilterWidgets(time);
		}
	}
}
