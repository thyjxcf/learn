/* 
 * @(#)BaseEduInfoDaoImpl.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.data.dao.BaseEduInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:53:21 PM $
 */
public class BaseEduInfoDaoImpl extends BaseDao<EduInfo> implements
		BaseEduInfoDao {
	private static final String SQL_INSERT_EDUINFO = "INSERT INTO base_eduinfo(id,principal,nation_poverty,"
			+ "is_autonomy,is_frontier,manager,director,statistician,edu_code,is_use_domain,"
			+ "domain_url,homepage,creation_time,modify_time,is_deleted) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_EDUINFO_BY_IDS = "UPDATE base_eduinfo SET is_deleted = 1,modify_time = ? WHERE id IN ";

	private static final String SQL_UPDATE_EDUINFO = "UPDATE base_eduinfo SET principal=?,nation_poverty=?,"
			+ "is_autonomy=?,is_frontier=?,manager=?,director=?,statistician=?,edu_code=?,is_use_domain=?,"
			+ "domain_url=?,homepage=?,modify_time=?,is_deleted=? WHERE id=?";

	public EduInfo setField(ResultSet rs) throws SQLException {
		EduInfo eduInfo = new EduInfo();
		eduInfo.setId(rs.getString("id"));
		eduInfo.setPrincipal(rs.getString("principal"));
		eduInfo.setNationPoverty(rs.getInt("nation_poverty"));
		eduInfo.setIsAutonomy(rs.getBoolean("is_autonomy"));
		eduInfo.setIsFrontier(rs.getBoolean("is_frontier"));
		eduInfo.setManager(rs.getString("manager"));
		eduInfo.setDirector(rs.getString("director"));
		eduInfo.setStatistician(rs.getString("statistician"));
		eduInfo.setEduCode(rs.getString("edu_code"));
		eduInfo.setIsUseDomain(rs.getBoolean("is_use_domain"));
		eduInfo.setDomainUrl(rs.getString("domain_url"));
		eduInfo.setHomepage(rs.getString("homepage"));
		eduInfo.setCreationTime(rs.getTimestamp("creation_time"));
		eduInfo.setModifyTime(rs.getTimestamp("modify_time"));
		return eduInfo;
	}

	public void insertEduInfo(EduInfo eduInfo) {
		eduInfo.setCreationTime(new Date());
		eduInfo.setModifyTime(new Date());
		update(SQL_INSERT_EDUINFO, new Object[] { eduInfo.getId(),
				eduInfo.getPrincipal(), eduInfo.getNationPoverty(),
				eduInfo.getIsAutonomy(), eduInfo.getIsFrontier(),
				eduInfo.getManager(), eduInfo.getDirector(),
				eduInfo.getStatistician(), eduInfo.getEduCode(),
				eduInfo.getIsUseDomain(), eduInfo.getDomainUrl(),
				eduInfo.getHomepage(), eduInfo.getCreationTime(),
				eduInfo.getModifyTime(), eduInfo.getIsdeleted() }, new int[] {
				Types.CHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER });
	}

	public void deleteEduInfo(String[] eduInfoIds) {
		updateForInSQL(SQL_DELETE_EDUINFO_BY_IDS, new Object[] { new Date() },
				eduInfoIds);
	}

	public void updateEduInfo(EduInfo eduInfo) {
		eduInfo.setModifyTime(new Date());
		eduInfo.setIsdeleted(false);
		update(SQL_UPDATE_EDUINFO, new Object[] { eduInfo.getPrincipal(),
				eduInfo.getNationPoverty(), eduInfo.getIsAutonomy(),
				eduInfo.getIsFrontier(), eduInfo.getManager(),
				eduInfo.getDirector(), eduInfo.getStatistician(),
				eduInfo.getEduCode(), eduInfo.getIsUseDomain(),
				eduInfo.getDomainUrl(), eduInfo.getHomepage(),
				eduInfo.getModifyTime(), eduInfo.getIsdeleted(),
				eduInfo.getId() }, new int[] { Types.VARCHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.CHAR });
	}
}
