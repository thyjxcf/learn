package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.BaseRemoteApCodeDao;
import net.zdsoft.eis.base.common.entity.RemoteApp;
import net.zdsoft.eis.frame.client.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public class BaseRemoteApCodeDaoImpl extends BaseDao<RemoteApp> implements BaseRemoteApCodeDao {

    @Override
    public List<RemoteApp> getAppsByBusinessType(String businessType) {
        String sql = "select * from base_remote_apcode where is_validate = 1 and business_type = ? order by display_order";
        return query(sql, businessType, new MultiRow());
    }

    @Override
    public RemoteApp setField(ResultSet rs) throws SQLException {
        RemoteApp ra = new RemoteApp();
        ra.setApNoticeUrl(rs.getString("ap_notice_url"));
        ra.setAppCode(rs.getString("ap_code"));
        ra.setAppDescription(rs.getString("ap_description"));
        ra.setAppName(rs.getString("ap_name"));
        ra.setBusinessType(rs.getString("business_type"));
        ra.setIsValidate(rs.getInt("is_validate"));
        ra.setTicketKey(rs.getString("ticket_key"));
        ra.setId(rs.getString("id"));
        ra.setDisplayOrder(rs.getInt("display_order"));
        return ra;
    }

}
