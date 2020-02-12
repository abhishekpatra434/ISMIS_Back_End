package com.ISMIS.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ISMIS.dto.ClientMseDto;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientMseBean;
import com.ISMIS.model.ClientMseTemplateBean;

public interface ClientMSEDao {

	Map<Integer, ArrayList<ClientMseTemplateBean>> getMseFormLabelsTemplate();

	Integer getMaxMseCaptionLevel();
	
	String saveUpdateMseResult(ClientMseDto clientMseDto);
	
	Map<Integer, ArrayList<ClientMseTemplateBean>> getClientMseResult(Integer clientId);
	
	String getMseSummary(Integer clientId);
	
	List<ClientMseBean> getClientMseResultList(Integer clientId);
	
	List<ClientMasterBean> getClientMsePendinglist(Integer homeId);
	
	List<ClientMasterBean> getClientUidList(String clientUid, String homeId);
	
	List<ClientMasterBean> getClientNameList(String clientName, String homeId);
	
	List<ClientMasterBean> getClientBasicInfoList(String clientId, String cohortYr, String homeId);

}
