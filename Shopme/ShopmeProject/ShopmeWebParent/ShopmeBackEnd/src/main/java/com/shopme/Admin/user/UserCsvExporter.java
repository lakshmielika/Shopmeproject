package com.shopme.Admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class UserCsvExporter extends  AbstractExporter {
	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.exportResponseHeader(response, "text/csv",".csv" );
		
		 ICsvBeanWriter csvWriter= new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		 String[] csvHeader = {"User ID","Email","First Name","Last Name","Roles","Enabled"};
		String[] fieldMapping= {"id","email","firstname","lastname","role","enabled"};
		 csvWriter.writeHeader(csvHeader);
		 for(User user : listUsers) {
			 csvWriter.write(user,fieldMapping);
		 }
		 csvWriter.close();
	}

}
