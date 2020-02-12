package com.ISMIS.dao;

import java.util.List;

import com.ISMIS.model.AgeClassBean;
import com.ISMIS.model.AttributeBean;
import com.ISMIS.model.ClientBmiClassBean;
import com.ISMIS.model.HomeBean;

public interface MasterDataDao {

	List<HomeBean> getHomeList();

	List<AttributeBean> getCategoryWiseAttrList(int categoryId);

	List<AgeClassBean> getAgeClassList();

	List<ClientBmiClassBean> getBmiClassList();

}
