package com.ISMIS.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISMIS.dto.Response;
import com.ISMIS.service.MasterDataService;

@RestController
public class MasterDataController {
	
	static Logger logger = LogManager.getLogger(MasterDataController.class);
	
	@Autowired
	private MasterDataService masterDataService;

	@RequestMapping(value = "/getHomeList", method = RequestMethod.GET)
	public Response getHomeList() {
		Response response = masterDataService.getHomeList();
		return response;
	}
	
	
	@RequestMapping(value = "/getCategoryWiseAttrList/{categoryId}", method = RequestMethod.GET)
	public Response getCategoryList(@PathVariable(name = "categoryId") Integer categoryId) {
		Response response = masterDataService.getCategoryWiseAttrList(categoryId);
		return response;
	}
	
	
	@RequestMapping(value = "/getAgeClassList", method = RequestMethod.GET)
	public Response getAgeClassList() {
		Response response = masterDataService.getAgeClassList();
		return response;
	}
	
	
	@RequestMapping(value = "/getBmiClassList", method = RequestMethod.GET)
	public Response getBmiClassList() {
		Response response = masterDataService.getBmiClassList();
		return response;
	}

}
