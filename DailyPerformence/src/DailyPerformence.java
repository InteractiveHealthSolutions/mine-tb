
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * Daily Performance Count for all the screeners
 * 
 * @author Rabbia
 *
 */

public class DailyPerformence {

	public static void main(String[] args) {
	
		// Fetch yesterday date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String dateYesterday = dateFormat.format(cal.getTime());
		//String dateTo = "2015-04-07";
		
		//Check for Weekend
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
		    System.out.println("Weekend!");
		    return;     
		}
		
		//Check for other holidays
		String getHolidays = "Select * from openmrs_rpt.holiday where date like '"+dateYesterday+"';";
		String[][] holidays = DatabaseUtil.getDbCon().executeQuery (getHolidays, null);
		
		if(holidays.length != 0){
			 System.out.println("Holiday! " + dateYesterday);
			 return;
		}
		
		// Keep backup of messages seen by the screeners
		String query = "insert into openmrs_rpt.daily_feedback_message_backup (screener_id, date, message, total_screened, total_sputum_collected, total_suspect, percentage, sent) "+
		                             "Select screener_id, date, message, total_screened, total_sputum_collected, total_suspect, percentage, sent from openmrs_rpt.daily_feedback_message where sent = 1";
		DatabaseUtil.getDbCon().execute (query);
		
		//delete messages which were already seen
		String truncateQuery = "delete from openmrs_rpt.daily_feedback_message where sent = 1";
		DatabaseUtil.getDbCon().execute (truncateQuery);
		
		//Get all screeners
		String userList = "Select username from openmrs.users u, openmrs.person_attribute pa where u.username like '0%' and u.retired = 0 and u.person_id = pa.person_id and pa.person_attribute_type_id = 14 and pa.value = 49;";
		String[][] users  = DatabaseUtil.getDbCon().executeQuery (userList, null);
		
		// for every screener ---
		for(int count = 0;count<users.length; count ++) {
			
			String username = users[count][0]; 
			
			//Get any unread message available
			String user = "Select * from openmrs_rpt.daily_feedback_message where screener_id like '"+username+"'";
			String[][] u  = DatabaseUtil.getDbCon().executeQuery (user, null);
			
			//If there's any unread message skip
			if(u.length == 0){
				
				// get yesterday's screening and sputums submitted numbers
				String queryScreeningNumberForYesterday = "select IFNULL(SUM(case when (e.encounter_type = 1) then 1 else 0 end),0) , IFNULL(SUM(case when (e.encounter_type = 2) then 1 else 0 end),0) , IFNULL(SUM(case when ( pa.value = 'Suspect' and e.encounter_type = 1 ) then 1 else 0 end),0) " + 
						"from openmrs.encounter e , openmrs.encounter_provider ep, openmrs.provider p, openmrs.person_attribute pa  " +
						"where e.encounter_datetime = '"+dateYesterday+" %' and e.encounter_id = ep.encounter_id and ep.provider_id = p.provider_id and p.identifier = '"+username+"' and ep.voided = 0 and (e.encounter_type = 1 or e.encounter_type = 2) and e.patient_id = pa.person_id and pa.person_attribute_type_id = 12;";
	
				String[][] data2  = DatabaseUtil.getDbCon().executeQuery (queryScreeningNumberForYesterday, null);
				
				// get Sputum collected for the patients screened on same day yesterday
				String querySputumFromScreeningYesterday = "select count(distinct e.patient_id) from openmrs.encounter e , openmrs.encounter_provider ep, openmrs.provider p "+
															"where e.encounter_datetime = '"+dateYesterday+" %' and e.encounter_id = ep.encounter_id and ep.provider_id = p.provider_id and p.identifier = '"+username+"' and ep.voided = 0 and e.encounter_type = 2 and e.patient_id IN ( " +
																	"select e.patient_id from openmrs.encounter e , openmrs.encounter_provider ep, openmrs.provider p, openmrs.person_attribute pa " +
																		"where e.encounter_datetime = '"+dateYesterday+" %' and e.encounter_id = ep.encounter_id and ep.provider_id = p.provider_id and p.identifier = '"+username+"' and ep.voided = 0 and e.encounter_type = 1 and e.patient_id = pa.person_id and pa.person_attribute_type_id = 12 and pa.value = 'Suspect');";
				String[][] pidCount  = DatabaseUtil.getDbCon().executeQuery (querySputumFromScreeningYesterday, null);
				
				
				// Calculate numbers 
				float percentage = 0;
				if(!(pidCount[0][0].equals("0") || data2[0][2].equals("0"))){
					 percentage = (Integer.parseInt(pidCount[0][0]) * 100/ Integer.parseInt(data2[0][2]));
				}
				int screenedToday = Integer.parseInt(data2[0][0]);
				int sputumSubmittedToday = Integer.parseInt(data2[0][1]);
				int suspectToday = Integer.parseInt(data2[0][2]);
				
				String message = "";
				
				// insert the screener screening numbers
				String insertQuery = "insert into openmrs_rpt.daily_feedback_message (screener_id, date, message, total_screened, total_sputum_collected, total_suspect, percentage, sputum_Submitted_of_screened_today) values ('"+username+"','"+dateYesterday+"','"+message+"',"+screenedToday+","+sputumSubmittedToday+","+suspectToday+","+percentage+","+pidCount[0][0]+");";
				DatabaseUtil.getDbCon().execute (insertQuery);
			}
			
		}
		
		
		System.out.println("End");
		
	}

	

}



















