package com.shopme.Admin.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {
	public void exportResponseHeader( HttpServletResponse response,String contentType,String extension) throws IOException {
		DateFormat dataformatter= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timeStamp=dataformatter.format(new Date());
		String fileName="users_"+timeStamp+extension;
		response.setContentType(contentType);
		String headerKey="Content-Disposition";
		String headervalue="attachment; filename="+fileName;
		response.setHeader(headerKey, headervalue);
	}


}
