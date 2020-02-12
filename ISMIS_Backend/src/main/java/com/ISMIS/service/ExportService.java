package com.ISMIS.service;

import java.util.Date;

import org.springframework.http.ResponseEntity;

public interface ExportService {
	
	ResponseEntity<byte[]> exportToExcel(String homeId, String clientId, String cohortYr, String exportFor);
	
	ResponseEntity<byte[]> pathologicalTestExport(Integer homeId, Date fromDate, Date toDate);
	
	ResponseEntity<byte[]> psychometryTestExport(Integer homeId, Date fromDate, Date toDate);
}
