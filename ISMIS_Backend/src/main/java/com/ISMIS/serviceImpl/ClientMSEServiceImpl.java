package com.ISMIS.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISMIS.commonUtility.PersonalUtility;
import com.ISMIS.dao.ClientMSEDao;
import com.ISMIS.dto.ClientMseDto;
import com.ISMIS.dto.Response;
import com.ISMIS.model.ClientMasterBean;
import com.ISMIS.model.ClientMseBean;
import com.ISMIS.model.ClientMseTemplateBean;
import com.ISMIS.model.HomeBean;
import com.ISMIS.service.ClientMSEService;

@Service
public class ClientMSEServiceImpl implements ClientMSEService {

	static Logger logger = LogManager.getLogger(ClientMSEServiceImpl.class);

	@Autowired
	private ClientMSEDao clientMSEDao;

	@Override
	public Response getMseFormLabelsTemplate() {

		Response response = null;
		Map<Integer, ArrayList<ClientMseTemplateBean>> templateList = null;
		int caption_lvl = 0;
		ArrayList<ClientMseTemplateBean> childNodeList = null;
		LinkedHashMap<Integer, ClientMseTemplateBean> rootNodeList = null;
		ClientMseTemplateBean rootTemplateBean = null;
		ArrayList<ClientMseTemplateBean> rootChildNode = null;
		ClientMseTemplateBean finalTempList = new ClientMseTemplateBean();
		ArrayList<ClientMseTemplateBean> finalNodes = null;

		try {
			templateList = clientMSEDao.getMseFormLabelsTemplate();
			response = new Response();

			if (templateList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (templateList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					caption_lvl = clientMSEDao.getMaxMseCaptionLevel();

					if (caption_lvl == templateList.size() - 1) {

						for (int i = caption_lvl; i > 0; i--) {

							childNodeList = templateList.get(i);
							rootNodeList = PersonalUtility.convetListToMap(templateList.get(i - 1));

							for (ClientMseTemplateBean templateBean : childNodeList) {

								if (rootNodeList.get(templateBean.getMental_status_lbl_parent_id()) != null) {

									rootTemplateBean = rootNodeList.get(templateBean.getMental_status_lbl_parent_id());

									if (rootTemplateBean.getChildNode() == null) {
										rootChildNode = new ArrayList<ClientMseTemplateBean>();
										rootChildNode.add(templateBean);
										rootTemplateBean.setChildNode(rootChildNode);
									} else {
										rootChildNode = rootTemplateBean.getChildNode();
										rootChildNode.add(templateBean);
									}
								} else {
									logger.info("Parent not found -> " + templateBean.getMental_status_lbl_parent_id());
								}
							}
						}

						if (!rootNodeList.isEmpty()) { // Creating final list.

							finalNodes = new ArrayList<ClientMseTemplateBean>();

							for (Map.Entry<Integer, ClientMseTemplateBean> entry : rootNodeList.entrySet()) {
								finalNodes.add(entry.getValue());
							}

							finalTempList.setChildNode(finalNodes);
							response.setKey(0);
							response.setValue(finalTempList);
							response.setMessage(String.valueOf(caption_lvl));
						} else {
							response.setKey(2);
							response.setMessage("Some error has taken place.");
						}
					} else {
						response.setKey(2);
						response.setMessage("Some error has taken place.");
					}
				}
			}

		} catch (Exception ex) {
			response.setKey(2);
			response.setMessage("Some error has taken place.");
			logger.info("Problem in Class - ClientMSEServiceImpl ~~ method- getMseFormLabelsTemplate() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response saveUpdateMseResult(ClientMseDto clientMseDto) {

		Response response = null;
		try {
			String status = clientMSEDao.saveUpdateMseResult(clientMseDto);
			response = new Response();

			if (status != null) {
				response.setKey(2);
				response.setMessage(status);
			} else {
				response.setKey(0);
				response.setMessage("Saved successfully.");
			}
		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientMSEServiceImpl ~~ method- saveUpdateMseResult() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getClientMseResult(Integer clientId) {

		Response response = null;
		Map<Integer, ArrayList<ClientMseTemplateBean>> templateList = null;
		int caption_lvl = 0;
		ArrayList<ClientMseTemplateBean> childNodeList = null;
		LinkedHashMap<Integer, ClientMseTemplateBean> rootNodeList = null;
		ClientMseTemplateBean rootTemplateBean = null;
		ArrayList<ClientMseTemplateBean> rootChildNode = null;
		ClientMseTemplateBean finalTempList = new ClientMseTemplateBean();
		ArrayList<ClientMseTemplateBean> finalNodes = null;
		ClientMseDto clientMseDto = null;
		String summary = null;
		List<ClientMseBean> clientMseResultList = null;

		try {
			templateList = clientMSEDao.getClientMseResult(clientId);
			response = new Response();

			if (templateList == null) {
				response.setKey(2);
				response.setMessage("Some error has taken place.");
			} else {
				if (templateList.isEmpty()) {
					response.setKey(1);
					response.setMessage("No data found.");
				} else {
					caption_lvl = clientMSEDao.getMaxMseCaptionLevel();

					if (caption_lvl == templateList.size() - 1) {

						for (int i = caption_lvl; i > 0; i--) {

							childNodeList = templateList.get(i);
							rootNodeList = PersonalUtility.convetListToMap(templateList.get(i - 1));

							for (ClientMseTemplateBean templateBean : childNodeList) {

								if (rootNodeList.get(templateBean.getMental_status_lbl_parent_id()) != null) {

									rootTemplateBean = rootNodeList.get(templateBean.getMental_status_lbl_parent_id());

									if (rootTemplateBean.getChildNode() == null) {
										rootChildNode = new ArrayList<ClientMseTemplateBean>();
										rootChildNode.add(templateBean);
										rootTemplateBean.setChildNode(rootChildNode);
									} else {
										rootChildNode = rootTemplateBean.getChildNode();
										rootChildNode.add(templateBean);
									}
								} else {
									logger.info("Parent not found -> " + templateBean.getMental_status_lbl_parent_id());
								}
							}
						}

						if (!rootNodeList.isEmpty()) { // Creating final list.

							finalNodes = new ArrayList<ClientMseTemplateBean>();
							clientMseDto = new ClientMseDto();

							for (Map.Entry<Integer, ClientMseTemplateBean> entry : rootNodeList.entrySet()) {
								finalNodes.add(entry.getValue());
							}

							finalTempList.setChildNode(finalNodes);

							/****************
							 * Below code Get Summary against the current client
							 *************************/

							summary = clientMSEDao.getMseSummary(clientId);

							/*****************
							 * Below code GET only result list of the current client.
							 **************/

							clientMseResultList = clientMSEDao.getClientMseResultList(clientId);

							clientMseDto.setClientMseResultTempBeans(finalTempList);
							clientMseDto.setClientId(clientId);
							clientMseDto.setClientMseBean(clientMseResultList);
							clientMseDto.setSummeryTxt(summary);

							response.setKey(0);
							response.setValue(clientMseDto);
							response.setMessage(String.valueOf(caption_lvl));
						} else {
							response.setKey(2);
							response.setMessage("Some error has taken place.");
						}
					} else {
						response.setKey(2);
						response.setMessage("Some error has taken place.");
					}
				}
			}

		} catch (Exception ex) {
			response.setKey(2);
			response.setMessage("Some error has taken place.");
			logger.info("Problem in Class - ClientMSEServiceImpl ~~ method- getClientMseResult() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}

	@Override
	public Response getClientMsePendinglist(Integer homeId) {
		Response response = null;
		List<ClientMasterBean> clientList = null;
		try {
			response = new Response();
			clientList = clientMSEDao.getClientMsePendinglist(homeId);

			if (clientList != null) {
				response.setKey(2);
				response.setValue(clientList);
			} else {
				response.setKey(0);
				response.setMessage("Some error has taken place.");
			}
		} catch (Exception ex) {
			logger.info("Problem in Class - ClientMSEServiceImpl ~~ method- getClientMsePendinglist() - "
					+ ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;

	}

	@Override
	public Response getClientUidList(String clientUid, String homeId) {
		Response response = new Response();
		List<ClientMasterBean> uidList = null;
		try {

			uidList = clientMSEDao.getClientUidList(clientUid, homeId);

			if (uidList != null) {
				response.setKey(0);
				response.setValue(uidList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientMSEServiceImpl ~~ method- getClientUidList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientNameList(String clientName, String homeId) {

		Response response = new Response();
		List<ClientMasterBean> clientList = null;
		try {

			clientList = clientMSEDao.getClientNameList(clientName, homeId);

			if (clientList != null) {
				response.setKey(0);
				response.setValue(clientList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info("Problem in Class - ClientMSEServiceImpl ~~ method- getClientNameList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}
		return response;
	}

	@Override
	public Response getClientBasicInfoList(String clientId, String cohortYr, String homeId) {
		Response response = new Response();
		List<ClientMasterBean> clientList = null;
		try {

			clientList = clientMSEDao.getClientBasicInfoList(clientId, cohortYr, homeId);

			if (clientList != null) {
				response.setKey(0);
				response.setValue(clientList);
			} else {
				response.setKey(1);
				response.setMessage("Some error has taken place.");
			}

		} catch (Exception ex) {
			logger.info(
					"Problem in Class - ClientMSEServiceImpl ~~ method- getClientBasicInfoList() - " + ex.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String sStackTrace = sw.toString();
			logger.info(sStackTrace);
		}

		return response;
	}
}
