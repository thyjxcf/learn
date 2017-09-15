package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.EduInfoRemoteService;
import net.zdsoft.eis.base.common.dao.EduInfoDao;
import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.util.EntityUtils;
import net.zdsoft.eis.frame.client.BaseDao;

public class EduInfoDaoImpl extends BaseDao<EduInfo> implements EduInfoDao {

	@Autowired
	private EduInfoRemoteService eduInfoRemoteService;

//	private static final String SQL_FIND_EDUINFO_BY_ID = "SELECT * FROM base_eduinfo WHERE id=?";
//
//	private static final String SQL_FIND_EDUINFOS_BY_IDS = "SELECT * FROM base_eduinfo WHERE is_deleted = 0 AND id IN";

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

	public EduInfo getEduInfo(String eduInfoId) {
		return EduInfo.dc(eduInfoRemoteService.findById(eduInfoId));
//		return query(SQL_FIND_EDUINFO_BY_ID, eduInfoId, new SingleRow());
	}

	public Map<String, EduInfo> getEduInfos(String[] eduInfoIds) {
		List<EduInfo> edus =  EduInfo.dt(eduInfoRemoteService.findByIds(eduInfoIds));
		return EntityUtils.getMap(edus, "id");
//		return queryForInSQL(SQL_FIND_EDUINFOS_BY_IDS, null, eduInfoIds, new MapRow());
	}

}
