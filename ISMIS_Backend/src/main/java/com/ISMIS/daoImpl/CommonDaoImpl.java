package com.ISMIS.daoImpl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class CommonDaoImpl {
	@Autowired
    protected DataSource dataSource;
}
