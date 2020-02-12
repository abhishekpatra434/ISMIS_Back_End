package com.ISMIS.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dto.ClientDto;
import com.ISMIS.dto.ClientMseDto;
import com.ISMIS.dto.Response;
import com.ISMIS.service.ClientMSEService;

@RestController
public class ClientMSEAction {

	static Logger logger = LogManager.getLogger(ClientMSEAction.class);

	@Autowired
	private ClientMSEService clientMSEService;

	@RequestMapping(value = "/client/mse/getMseFormLabelsTemplate", method = RequestMethod.GET)
	public Response getMseFormLabelsTemplate() {
		Response response = clientMSEService.getMseFormLabelsTemplate();
		return response;
	}

	@RequestMapping(value = "/client/mse/saveUpdateMseResult", method = RequestMethod.POST)
	public Response saveUpdateMseResult(@RequestBody ClientMseDto clientMseDto) {
		Response response = clientMSEService.saveUpdateMseResult(clientMseDto);
		return response;
	}

	@RequestMapping(value = "/client/mse/getClientMseResult", method = RequestMethod.GET)
	public Response getClientMseResult(@RequestParam(name = "clientId") Integer clientId) {
		Response response = clientMSEService.getClientMseResult(clientId);
		return response;
	}

	@RequestMapping(value = "/client/mse/getClientMsePendinglist", method = RequestMethod.GET)
	public Response getClientMsePendinglist(@RequestParam(name = "homeId") Integer homeId) {
		Response response = clientMSEService.getClientMsePendinglist(homeId);
		return response;
	}
	
	@RequestMapping(value = "/client/mse/getClientUidList", method = RequestMethod.GET)
	public Response getClientUidList(@RequestParam(name = "clientUid") String clientUid,
			@RequestParam(name = "homeId") String homeId) {
		Response response = clientMSEService.getClientUidList(clientUid, homeId);
		return response;
	}

	
	@RequestMapping(value = "/client/mse/getClientNameList", method = RequestMethod.GET)
	public Response getClientNameList(@RequestParam(name = "clientName") String clientName,
			@RequestParam(name = "homeId") String homeId) {
		Response response = clientMSEService.getClientNameList(clientName, homeId);
		return response;
	}
	
	@RequestMapping(value = "/client/mse/getClientBasicInfo", method = RequestMethod.GET)
	public Response getClientBasicInfo(@RequestParam(name = "clientId") String clientId,
			@RequestParam(name = "cohortYr") String cohortYr, @RequestParam(name = "homeId") String homeId) {
		Response response = clientMSEService.getClientBasicInfoList(clientId, cohortYr, homeId);
		return response;
	}

}
