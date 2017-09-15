/* 
 * @(#)BaseMcodeDetailDaoImpl.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.data.dao.BaseMcodeDetailDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 10:08:31 AM $
 */
public class BaseMcodeDetailDaoImpl extends BaseDao<Mcodedetail> implements BaseMcodeDetailDao {
    private static final String SQL_INSERT_MCODEDETAIL = "INSERT INTO base_mcode_detail(id,mcode_id,this_id,"
            + "mcode_content,is_using,mcode_type,display_order) VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_MCODEDETAIL = "DELETE FROM base_mcode_detail WHERE id IN ";

    private static final String SQL_UPDATE_MCODEDETAIL = "UPDATE base_mcode_detail SET mcode_id=?,this_id=?,"
            + "mcode_content=?,is_using=?,mcode_type=?,display_order=? WHERE id=?";

    private static final String SQL_UPDATE_ISUSING = "UPDATE base_mcode_detail SET is_using=? WHERE id=?";

    private static final String SQL_FIND_MAX_ORDERID = "SELECT MAX(display_order) FROM base_mcode_detail WHERE mcode_id=?";

    private static final String SQL_FIND_MCODEDETAIL_BY_ID = "SELECT * FROM base_mcode_detail WHERE id=?";

    public Mcodedetail setField(ResultSet rs) throws SQLException {
        Mcodedetail mcodedetail = new Mcodedetail();
        mcodedetail.setId(rs.getString("id"));
        mcodedetail.setMcodeId(rs.getString("mcode_id"));
        mcodedetail.setThisId(rs.getString("this_id"));
        mcodedetail.setContent(rs.getString("mcode_content"));
        mcodedetail.setIsUsing(rs.getInt("is_using"));
        mcodedetail.setType(rs.getInt("mcode_type"));
        mcodedetail.setOrderId(rs.getInt("display_order"));
        return mcodedetail;
    }

    public void insertMcodeDetail(Mcodedetail m) {
        m.setId(getGUID());
        update(SQL_INSERT_MCODEDETAIL, new Object[] { m.getId(), m.getMcodeId(), m.getThisId(),
                m.getContent(), m.getIsUsing(), m.getType(), m.getOrderId() }, new int[] {
                Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER,
                Types.INTEGER });
    }

    public void updateMcodeDetail(Mcodedetail m) {
        update(SQL_UPDATE_MCODEDETAIL, new Object[] { m.getMcodeId(), m.getThisId(),
                m.getContent(), m.getIsUsing(), m.getType(), m.getOrderId(), m.getId() },
                new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER, Types.INTEGER,
                        Types.INTEGER, Types.CHAR });
    }

    public void updateStateChange(String id, int using) {
        update(SQL_UPDATE_ISUSING, new Object[] { using, id }, new int[] { Types.INTEGER,
                Types.CHAR });
    }

    public void deleteMcodeDetail(String[] ids) {
        updateForInSQL(SQL_DELETE_MCODEDETAIL, null, ids);
    }

    public Integer getAvaOrderId(String mcodeId) {
        int max = queryForInt(SQL_FIND_MAX_ORDERID, new String[] { mcodeId });
        max++;
        return max;
    }    

    public Mcodedetail getMcodeDetail(String id) {
        return (Mcodedetail) query(SQL_FIND_MCODEDETAIL_BY_ID, new Object[] { id }, new SingleRow());
    }

}
