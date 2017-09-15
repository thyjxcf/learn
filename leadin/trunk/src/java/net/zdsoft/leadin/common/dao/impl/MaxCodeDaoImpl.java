/* 
 * @(#)MaxCodeCodeImpl.java    Created on Oct 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.leadin.common.dao.MaxCodeDao;
import net.zdsoft.leadin.common.entity.MaxCodeMetadata;
import net.zdsoft.leadin.common.entity.MaxCodeParam;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 14, 2010 2:13:06 PM $
 */
public class MaxCodeDaoImpl extends BasicDAO implements MaxCodeDao {
    public int getMaxSerialNumber(MaxCodeMetadata metadata, MaxCodeParam param) {
    	String sql = null;
		if (StringUtils.isNotBlank(param.getPrefix()))
			sql = metadata.getSql();
		else
			sql = metadata.getNoPrefixSql();
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(param);

        String code = getSimpleJdbcTemplate().queryForObject(sql, String.class, namedParameters);
        int num = 0;
        if (NumberUtils.isNumber(code)) {
            num = NumberUtils.toInt(code);
        }

        return num;
    }
}
