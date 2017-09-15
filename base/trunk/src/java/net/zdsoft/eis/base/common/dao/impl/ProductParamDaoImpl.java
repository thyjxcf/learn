/* 
 * @(#)ProductParamDaoImpl.java    Created on Jun 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.ProductParamDao;
import net.zdsoft.eis.base.common.entity.ProductParam;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 13, 2011 4:37:28 PM $
 */
public class ProductParamDaoImpl extends BaseDao<ProductParam> implements ProductParamDao {

    private static final String SQL_FIND_PRODUCTPARAM_BY_CODE = "SELECT * FROM sys_product_param WHERE param_code=?";
    private static final String SQL_FIND_PRODUCTPARAMS = "SELECT * FROM sys_product_param";

    public ProductParam setField(ResultSet rs) throws SQLException {
        ProductParam productParam = new ProductParam();
        productParam.setId(rs.getString("id"));
        productParam.setParamCode(rs.getString("param_code"));
        productParam.setParamName(rs.getString("param_name"));
        productParam.setParamValue(rs.getString("param_value"));
        productParam.setDisplayOrder(rs.getInt("display_order"));
        productParam.setDescription(rs.getString("description"));
        return productParam;
    }

    public String getProductParamValue(String paramCode) {
        return query(SQL_FIND_PRODUCTPARAM_BY_CODE, paramCode, new SingleRowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs) throws SQLException {
                return rs.getString("param_value");
            }

        });
    }

    public Map<String, String> getProductParamCodeValueMap() {
        return queryForMap(SQL_FIND_PRODUCTPARAMS, new MapRowMapper<String, String>() {

            @Override
            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("param_code");
            }

            @Override
            public String mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("param_value");
            }
        });
    }
}
