package com.ISMIS.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISMIS.dto.CommonDto;
import com.ISMIS.dto.Response;
import com.ISMIS.service.ExportService;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@RestController
public class ExportAction {

	@Autowired
	ExportService exportService;

	@RequestMapping(value = "/client/exportToExcel", method = RequestMethod.POST)
	public ResponseEntity<byte[]> exportToExcel(@RequestBody CommonDto commonDto) {

		byte[] dataBytes = null;
		ResponseEntity<byte[]> res = null;
		res = exportService.exportToExcel(commonDto.getHomeId(), commonDto.getClientId(), commonDto.getCohortYr(),
				commonDto.getExportFor());
		dataBytes = res.getBody();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headers.setContentLength(dataBytes.length);
		headers.setContentDispositionFormData("attachment", "StyledTextReport");
		return new ResponseEntity<byte[]>(dataBytes, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/client/pathologicalTestExport", method = RequestMethod.POST)
	public ResponseEntity<byte[]> pathologicalTestExport(@RequestBody CommonDto commonDto) throws ParseException {
		// Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse("05-01-2020");
		// Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse("15-02-2020");
		byte[] dataBytes = null;
		ResponseEntity<byte[]> res = null;
		res = exportService.pathologicalTestExport(Integer.parseInt(commonDto.getHomeId()), commonDto.getFromDate(),
				commonDto.getToDate());

		dataBytes = res.getBody();
		HttpHeaders headers = new HttpHeaders();

		if (dataBytes != null) {
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
			headers.setContentLength(dataBytes.length);
			headers.setContentDispositionFormData("attachment", "StyledTextReport");
		} else {
			dataBytes = "No Data found".getBytes();
			headers.setContentType(MediaType.parseMediaType("text/html"));
			headers.setContentLength(dataBytes.length);
		}

		return new ResponseEntity<byte[]>(dataBytes, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/client/psychometryTestExport", method = RequestMethod.POST)
	public ResponseEntity<byte[]> psychometryTestExport(@RequestBody CommonDto commonDto) throws ParseException {
		byte[] dataBytes = null;
		ResponseEntity<byte[]> res = null;
		res = exportService.psychometryTestExport(Integer.parseInt(commonDto.getHomeId()), commonDto.getFromDate(),
				commonDto.getToDate());

		dataBytes = res.getBody();
		HttpHeaders headers = new HttpHeaders();

		if (dataBytes != null) {
			headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
			headers.setContentLength(dataBytes.length);
			headers.setContentDispositionFormData("attachment", "StyledTextReport");
		} else {
			dataBytes = "No Data found".getBytes();
			headers.setContentType(MediaType.parseMediaType("text/html"));
			headers.setContentLength(dataBytes.length);
		}

		return new ResponseEntity<byte[]>(dataBytes, headers, HttpStatus.OK);
	}
}
