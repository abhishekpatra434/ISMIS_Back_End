package com.ISMIS.service;

import com.ISMIS.dto.Response;

public interface MasterDataService {

	Response getHomeList();
	Response getCategoryWiseAttrList(int categoryId);
	Response getAgeClassList();
	Response getBmiClassList();

}
