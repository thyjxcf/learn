package net.zdsoft.basedata.remote.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.basedata.remote.dao.BaseRemoteFeedbackDao;
import net.zdsoft.eis.base.common.entity.Feedback;
import net.zdsoft.eis.frame.client.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public class BaseRemoteFeedbackDaoImpl extends BaseDao<Feedback> implements BaseRemoteFeedbackDao {

    @Override
    public Feedback setField(ResultSet rs) throws SQLException {
        Feedback fb = new Feedback();
        fb.setId(getString(rs, "id"));
        fb.setAccessDate(getString(rs, "access_date"));
        fb.setModuleName(getString(rs, "module_name"));
        fb.setSystemVersion(getString(rs, "system_version"));
        fb.setTopUnitName(getString(rs, "top_unit_name"));
        fb.setUploadTime(getString(rs, "upload_time"));
        fb.setAccessCount(getInt(rs, "access_count"));
        fb.setUrl(getString(rs, "url"));
        return fb;
    }

    @Override
    public void addFeedback(Feedback... fbs) {
        String sql = "insert into base_remote_feedback(id, top_unit_name, access_count, system_version, access_date, url,"
                + "module_name, upload_time) values(?,?,?,?,?,?,?,?)";

        List<Object[]> values = new ArrayList<Object[]>();
        for (Feedback fb : fbs) {
            values.add(new Object[] { fb.getId(), fb.getTopUnitName(), fb.getAccessCount(),
                    fb.getSystemVersion(), fb.getAccessDate(), fb.getUrl(), fb.getModuleName(),
                    fb.getUploadTime() });
        }
        batchUpdate(sql, values, new int[] { Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR,
                Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR });
    }

}
