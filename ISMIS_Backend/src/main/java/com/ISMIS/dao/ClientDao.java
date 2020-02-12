package com.ISMIS.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ISMIS.dto.ClientDto;
import com.ISMIS.model.ClientMasterBean;

public interface ClientDao {

	String saveClientIntakeDetails(ClientDto clientDto, MultipartFile file);
	
	String updateClientIntakeDetails(ClientDto clientDto, MultipartFile file);
	
	List<ClientMasterBean> getClientUidList(String clientUid, String homeId);
	
	List<ClientMasterBean> getClientBasicInfoList(String clientId, String cohortYr, String homeId);
	
	List<String> getCohortYrList(String homeId);
	
	ClientDto getClientIntakeDetails(String clientId);
	
	List<ClientMasterBean> getClientNameList(String clientName, String homeId);
	
	String getUploadedFileInfo(Integer clientId, String documetType);
	
	List<ClientDto> getClientIntakeList(String homeId, String clientId, String cohortYr);

}
