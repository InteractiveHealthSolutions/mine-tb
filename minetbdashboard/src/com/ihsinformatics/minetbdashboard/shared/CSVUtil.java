/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */

package com.ihsinformatics.minetbdashboard.shared;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CSVUtil {

	
	public static void makeCsv(ZipOutputStream zip , String[][] record, String form) throws IOException {
		//File inputFile = new File("utf-8-data.txt");
		//File outputFile = new File("latin-1-data.zip");
       
		
		BufferedWriter writer = new BufferedWriter(
				   new OutputStreamWriter(zip, Charset.forName("utf-8"))
				   );	
		 if(form == null){	
		   ZipEntry entry = new ZipEntry("screeningdata.txt");
		   zip.putNextEntry(entry);
           writer.append("\"ScreeningID\",\"PatientID\",\"FirstName\",\"LastName\",\"ScreenerID\",\"Age\",\"Gender\",\"Suspect\",\"Cough\",\"CoughDuration\",\"ProductiveCough\",\"Fever\",\"DateEntered\",\"LocationName\"");
		 }else if(form.equalsIgnoreCase ("2")){
			 ZipEntry entry = new ZipEntry("patientdetailsdata.txt");
			 zip.putNextEntry(entry);
			 writer.append ("\"PatientID\",\"FirstName\",\"LastName\",\"Gender\",\"DOB\",\"AddressHouse\",\"AddressStreet\",\"AddressDistrict\",\"AddressFlat\",\"MobileNumber\",\"HomePhone\",\"MaritalStatus\",\"Education\",\"IncomeFamilyMember\",\"NominateVolunteer\",\"RelationshipOfVolunteer\", \"StartTreatmentDate\",\"HealthWorkerID\",\"DateEntered\",\"LocationName\"");
		 }else if(form.equalsIgnoreCase ("3")){
			 ZipEntry entry = new ZipEntry("baselinedetailsdata.txt");
			 zip.putNextEntry(entry);
			 writer.append ("\"PatientID\",\"BaselineSputum\",\"BaselineChest\",\"OtherXraySite\",\"XrayResult\",\"BaselineGeneXpert\",\"DrugSensitivity\",\"TypePatient\",\"PatientCategory\",\"weight\",\"Regimen\",\"FixedDose\",\"Streptomycin\",\"HealthWorkerID\",\"DateEntered\",\"LocationName\"");
		 }else if(form.equalsIgnoreCase ("4")){
			 ZipEntry entry = new ZipEntry("monitoringresultsdata.txt");
			 zip.putNextEntry(entry);
			 writer.append ("\"PatientID\",\"DateTreatmentInitiation\",\"BaselineSmear\",\"ResultForTreatmentMonth\",\"SmearResult\",\"PatientFeeling\",\"ExperiencedSideEffects\",\"PatientSideEffects\",\"OtherSideEffects\",\"PatientConsult\",\"HealthFacility\",\"MissMedication\",\"DurationMissMedication\",\"MedicineWrappers\",\"DoseConsumption\",\"DateEntered\",\"LocationName\"");
		 }else if (form.equalsIgnoreCase("5")){
			 ZipEntry entry = new ZipEntry("surveydata.txt");
			 zip.putNextEntry(entry);
			 writer.append ("\"PatientID\",\"Are you satisfied with the system when you store and take medicines at home?\",\"If not please explain what aspects were unsatisfactory?\",\"How can the offered system be improved?\",\"Are you satified with the SMS reminder system in place?\",\"If not, please explain what aspects were unsatisfactory?\",\"Has the sms reminder system added additional effort for you?\",\"Do you feel the sms reminder system helped in treatment compliance?\",\"Are you satisfied with the services provided by the health facility?\",\"Are you satisfied with the care provided by the TB specialist?\",\"Are you satisfied with the system for TB patients treatment in the country?\",\"HealthWorkerID\",\"DateEntered\",\"LocationName\"");
		 }
         
         writer.append('\n');
		 String[][] stringData = new String[record.length][];//[row][column]
				
		 for (int i = 0; i < record.length; i++)//first row length
		 {
			stringData[i] = new String[record[i].length];//row length which is 10
			for (int j = 0; j < record[i].length; j++)
			{
				if (record[i][j] == null)
					record[i][j] = "";
				String str = record[i][j].toString ();
				stringData[i][j] = str;

				writer.append(("\""+stringData[i][j]+"\""));
				
				writer.append(',');
				
			}
			writer.append('\n');
		 }
		
		 writer.flush(); // i've used a buffered writer, so make sure to flush to the
		// underlying zip output stream

		 zip.closeEntry();
		 zip.finish();

		//reader.close(); 
		 writer.close();

}
	
	public static void makeCsvPDF(ZipOutputStream zip , String[][] record) throws IOException {
		ByteArrayOutputStream fw = new ByteArrayOutputStream();
		fw.write("\"PatientID\",\"firstName\",\"lastName\",\"Gender\",\"LabSpecimenID\",\"DateRegistered\",\"Age\",\"CellPhone1\",\"CellPhone2\",\"CellPhone3\",\"ReferringFacilityCode\",\"GeneXpertSiteCode\",\"DateofSpecimen\",\"SpecimenCollection\",\"ResultStatus\",\"LC1Village\",\"SubCounty\",\"District\",\"DateTested\",\"DateResult\",\"MTBResults\",\"Error\",\"RIFResistant\",\"NTRL\",\"isPositive\",\"DateDOTSCBD\",\"TreatmentOutcome\",\"PlaceofTreatment\",\"TypeofDisease\",\"TreatmentStatus\",\"TypeofPatient\",\"Symptoms\",\"SymptomsOther\",\"HIVStatus\",\"ARV\",\"SputumSmearStatus\",\"XRayStatus\",\"DateEntered\",\"DateStarted\",\"DateEnded\",\"Cough\",\"Fever\",\"NightSweats\",\"WeightLoss\",\"Hemoptosis\"".getBytes());
		fw.write('\n');
		//Object[][] data = HibernateUtil.util.selectData (sqlQuery);
		String[][] stringData = new String[record.length][];//[row][column]
				
		for (int i = 0; i < record.length; i++)//first row length
		{
			stringData[i] = new String[record[i].length];//row length which is 10
			System.out.println("This is the string data......."+record[i].length);
			for (int j = 0; j < record[i].length; j++)
			{
				if (record[i][j] == null)
					record[i][j] = "";
				String str = record[i][j].toString ();
				stringData[i][j] = str;

				System.out.println(stringData[i][j]);
				fw.write(("\""+stringData[i][j]+"\"").getBytes());
				fw.write(',');
				
			}
			fw.write('\n');
		}
		String date=DateUtils.convertToString(new Date()).substring(0,10);
		byte[] b=fw.toString().getBytes("UTF-8");
		zip.putNextEntry(new ZipEntry("PIFExport_"+date+".csv"));
		zip.write(b);
		zip.closeEntry();
		zip.close();

}
	
	public static void makeMonthCsv(ZipOutputStream zip , String[][] record) throws IOException {
		ByteArrayOutputStream fw = new ByteArrayOutputStream();
		fw.write("\"Week\",\"# GXP tests performed\",\"# GXP +ve cases\",\"# Rif resistant cases\",\"# cases on treatment\",\"# cases successfully treated\"".getBytes());
		fw.write('\n');
		//Object[][] data = HibernateUtil.util.selectData (sqlQuery);
		String[][] stringData = new String[record.length][];//[row][column]
				
		for (int i = 0; i < record.length; i++)//first row length
		{
			stringData[i] = new String[record[i].length];//row length which is 10
			System.out.println("This is the string data......."+record[i].length);
			for (int j = 0; j < record[i].length; j++)
			{
				if (record[i][j] == null)
					record[i][j] = "";
				String str = record[i][j].toString ();
				stringData[i][j] = str;

				System.out.println(stringData[i][j]);
				fw.write(("\""+stringData[i][j]+"\"").getBytes());
				fw.write(',');
				
			}
			fw.write('\n');
		}
		String date=DateUtils.convertToString(new Date()).substring(0,10);
		byte[] b=fw.toString().getBytes();
		zip.putNextEntry(new ZipEntry("MonthCSV_"+date+".csv"));
		zip.write(b);
		zip.closeEntry();
		zip.close();

}
}