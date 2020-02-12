package com.ISMIS.service;

import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dto.ClientDto;
import com.ISMIS.dto.Response;

public interface ClientService {

	
	Response saveClientIntakeDetails(ClientDto clientDto, MultipartFile file);
	
	Response getClientUidList(String clientUid, String homeId);
	
	Response getClientBasicInfoList(String clientId, String cohortYr, String homeId);
	
	Response getCohortYrList(String homeId);
	
	Response getClientNameList(String clientName, String homeId);
	
	Response getClientIntakeDetails(String clientId);
	
	String getUploadedFileInfo(Integer clientId, String documetType);
}
