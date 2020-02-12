package com.ISMIS.service;

import com.ISMIS.dto.ClientMseDto;
import com.ISMIS.dto.Response;

public interface ClientMSEService {

	Response getMseFormLabelsTemplate();

	Response saveUpdateMseResult(ClientMseDto clientMseDto);

	Response getClientMseResult(Integer clientId);

	Response getClientMsePendinglist(Integer homeId);

	Response getClientUidList(String clientId, String homeId);
	
	Response getClientNameList(String clientName, String homeId);
	
	Response getClientBasicInfoList(String clientId, String cohortYr, String homeId);

}
