package com.ISMIS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dto.ClientDto;
import com.ISMIS.dto.Response;
import com.ISMIS.service.ClientService;
import com.ISMIS.service.UploadService;
import com.fasterxml.jackson.databind.ObjectMapper;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@RestController
public class ClientAction {

	static Logger logger = LogManager.getLogger(ClientAction.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private UploadService uploadservice;

	@Value("${upload-path}")
	String uploadPath;

	@RequestMapping(value = "/client/saveClientIntakeDetails", method = RequestMethod.POST)
	public Response saveClientIntakeDetails(@RequestPart(value = "file", required = false) MultipartFile multipartFiles,
			@RequestPart("document") String document) {

		Response response = null;
		ClientDto clientDto = new ClientDto();
		ObjectMapper mapper = new ObjectMapper();

		try {

			clientDto = mapper.readValue(document, ClientDto.class);
			response = clientService.saveClientIntakeDetails(clientDto, multipartFiles);

		} catch (Exception ex) {
			response = new Response();
			response.setKey(2);
			response.setMessage("Some Error has taken Place.");
			logger.info("Problem in Class - ClientAction ~~ method- saveClientIntakeDetails() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@RequestMapping(value = "/client/getClientUidList", method = RequestMethod.GET)
	public Response getClientUidList(@RequestParam(name = "clientUid") String clientUid,
			@RequestParam(name = "homeId") String homeId) {
		Response response = clientService.getClientUidList(clientUid, homeId);
		return response;
	}

	@RequestMapping(value = "/client/getClientBasicInfo", method = RequestMethod.GET)
	public Response getClientBasicInfo(@RequestParam(name = "clientId") String clientId,
			@RequestParam(name = "cohortYr") String cohortYr, @RequestParam(name = "homeId") String homeId) {
		Response response = clientService.getClientBasicInfoList(clientId, cohortYr, homeId);
		return response;
	}

	@RequestMapping(value = "/client/getCohortYrList", method = RequestMethod.GET)
	public Response getCohortYrList(@RequestParam(name = "homeId") String homeId) {
		Response response = clientService.getCohortYrList(homeId);
		return response;
	}

	@RequestMapping(value = "/client/getClientNameList", method = RequestMethod.GET)
	public Response getClientNameList(@RequestParam(name = "clientName") String clientName,
			@RequestParam(name = "homeId") String homeId) {
		Response response = clientService.getClientNameList(clientName, homeId);
		return response;
	}

	@RequestMapping(value = "/client/getClientIntakeDetails", method = RequestMethod.GET)
	public Response getClientBasicInfo(@RequestParam(name = "clientId") String clientId) {
		Response response = clientService.getClientIntakeDetails(clientId);
		return response;
	}

	@RequestMapping(value = "/client/uploadFile", method = RequestMethod.POST)
	public Response uploadFile(@RequestParam("file") MultipartFile file) { // Test Propose need to modify
		String realPath = null;
		Response response = new Response();
		String message = null;
		String status = null;
		try {
			realPath = uploadPath + "Clients/" + 00;
			status = uploadservice.deleteFiles(realPath, "Profile_Picture");
			if (!status.equals("error")) {
				message = uploadservice.uploadFile(realPath, file);
				if (message.equals("error")) {
					response.setKey(1);
					response.setMessage("Some error has taken place");
				} else {
					response.setKey(0);
					response.setMessage(message);
				}
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place");
			}
		} catch (Exception e) {
			response.setKey(1);
			response.setMessage("Some error has taken place");
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/client/getUploadedFile", method = RequestMethod.GET)
	public Response getUploadedFile(@RequestParam(name = "clientId") String clientId,
			@RequestParam(name = "documetType") String documetType) {

		String filePath = null;
		InputStream in = null;
		byte[] imageData = null;
		String utfString = null;
		FileInputStream imageInFile = null;
		Response response = new Response();

		try {

			filePath = clientService.getUploadedFileInfo(Integer.parseInt(clientId), documetType.trim());

			if (filePath != null && StringUtils.equalsIgnoreCase("Profile_Picture", documetType.trim())) {
				File file = new File(filePath);
				imageInFile = new FileInputStream(file);
				imageData = new byte[(int) file.length()];
				imageInFile.read(imageData);
				utfString = new sun.misc.BASE64Encoder().encode(imageData);

				response.setKey(0);
				response.setValue(utfString);
			}

		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
			response.setKey(2);
			response.setMessage("Some error has taken place.");

		} finally {
			try {
				if (imageInFile != null) {
					imageInFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response;
	}

	/*
	 * @RequestMapping(value = "/client/getUploadedFile", method =
	 * RequestMethod.GET) public HttpEntity<byte[]>
	 * getUploadedFile(@RequestParam(name = "clientId") String clientId,
	 * 
	 * @RequestParam(name = "documetType") String documetType) {
	 * 
	 * String filePath = null; InputStream in = null; byte[] bytes = null;
	 * HttpHeaders headers = null; try {
	 * 
	 * filePath = clientService.getUploadedFileInfo(Integer.parseInt(clientId),
	 * documetType.trim());
	 * 
	 * if (filePath != null && StringUtils.equalsIgnoreCase("Profile_Picture",
	 * documetType.trim())) { File file = new File(filePath); bytes = new byte[(int)
	 * file.length()]; in = new FileInputStream(file); in.read(bytes); headers = new
	 * HttpHeaders(); headers.setContentType(MediaType.IMAGE_JPEG);
	 * headers.setContentLength(bytes.length); }
	 * 
	 * } catch (Exception ex) { StringWriter sw = new StringWriter(); PrintWriter pw
	 * = new PrintWriter(sw); ex.printStackTrace(pw); String sStackTrace =
	 * sw.toString(); logger.info(sStackTrace); bytes =
	 * "Some Error has taken place".getBytes(); headers = new HttpHeaders();
	 * headers.setContentType(MediaType.TEXT_PLAIN);
	 * headers.setContentLength(bytes.length); } finally { try { if (in != null) {
	 * in.close(); } } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * return new HttpEntity<byte[]>(bytes, headers); }
	 */

}
