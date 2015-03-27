/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceutecsps.adminplanillas.reports;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author jhandal
 */
@Named
@ViewScoped
public class ReportBean implements Serializable {

    // Constants ----------------------------------------------------------------------------------
	private Date fromDate;
	private Date toDate;
	private String reportType;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
    // Actions ------------------------------------------------------------------------------------
	public void generarReporte() {
		Map parameters = new HashMap();
		parameters.put("fromDate", sdf.format(fromDate)+"");
		parameters.put("toDate", sdf.format(toDate)+"");
		String template = reportType; // Suppose your jrxml file is sample.jrxml  
		ReportGenerator gen = new ReportGenerator();
		if (gen.printReport(parameters,
				new StringBuffer("/WEB-INF/reports/").append(template)
				.append(".jrxml").toString(),
				template)) {
			System.out.println("REPORTE CREADO!");
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	

}
