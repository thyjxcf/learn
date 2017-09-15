package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.base.data.dao.BaseMcodeListDao;
import net.zdsoft.eis.base.data.entity.Mcodelist;
import net.zdsoft.eis.frame.client.BaseDao;

public class BaseMcodeListDaoImpl extends BaseDao<Mcodelist> implements BaseMcodeListDao {

    private static final String SQL_FIND_MCODELIST_BY_MCODEID = "SELECT * FROM base_mcode_list WHERE  mcode_id=?";

    private static final String SQL_FIND_MAINTAIN_BY_SUBSYSTEM1 = "SELECT * FROM base_mcode_list WHERE maintain="
            + Mcodelist.MCODE_MAINTAIN + " AND is_using=" + Mcodelist.MCODE_ISUSING;

    private static final String SQL_FIND_MAINTAIN_BY_SUBSYSTEM2 = "SELECT * FROM base_mcode_list WHERE maintain="
            + Mcodelist.MCODE_MAINTAIN
            + " AND is_using="
            + Mcodelist.MCODE_ISUSING
            + " AND subsystem=?";

    private static final String SQL_FIND_ACTIVE_BY_SUBSYSTEM1 = "SELECT * FROM base_mcode_list WHERE mcode_type="
            + Mcodelist.MCODE_TYPE_ACTIVE
            + " AND maintain="
            + Mcodelist.MCODE_NOT_MAINTAIN
            + " AND is_using=" + Mcodelist.MCODE_ISUSING;
    private static final String SQL_FIND_ACTIVE_BY_SUBSYSTEM2 = "SELECT * FROM base_mcode_list WHERE mcode_type="
            + Mcodelist.MCODE_TYPE_ACTIVE
            + " AND maintain="
            + Mcodelist.MCODE_NOT_MAINTAIN
            + " AND is_using=" + Mcodelist.MCODE_ISUSING + " AND subsystem=?";

    private static final String SQL_FIND_ONLYVISI_BY_SUBSYSTEM1 = "SELECT * FROM base_mcode_list WHERE mcode_type="
            + Mcodelist.MCODE_TYPE_STATIC_VISIABLE
            + "AND maintain="
            + Mcodelist.MCODE_NOT_MAINTAIN
            + " AND is_using=" + Mcodelist.MCODE_ISUSING;
    private static final String SQL_FIND_ONLYVISI_BY_SUBSYSTEM2 = "SELECT * FROM base_mcode_list WHERE mcode_type="
            + Mcodelist.MCODE_TYPE_STATIC_VISIABLE
            + "AND maintain="
            + Mcodelist.MCODE_NOT_MAINTAIN
            + " AND is_using=" + Mcodelist.MCODE_ISUSING + " AND subsystem=?";

    private static final String SQL_FIND_EXCEPT_BY_SUBSYSTEM1 = "SELECT * FROM base_mcode_list WHERE mcode_type<>"
            + Mcodelist.MCODE_TYPE_STATIC_NOVISIABLE + " AND is_using=" + Mcodelist.MCODE_ISUSING;

    private static final String SQL_FIND_EXCEPT_BY_SUBSYSTEM2 = "SELECT * FROM base_mcode_list WHERE mcode_type<>"
            + Mcodelist.MCODE_TYPE_STATIC_NOVISIABLE
            + " AND is_using="
            + Mcodelist.MCODE_ISUSING
            + " AND subsystem=?";

    public Mcodelist setField(ResultSet rs) throws SQLException {
        Mcodelist mcodelist = new Mcodelist();
        mcodelist.setId(rs.getString("id"));
        mcodelist.setMcodeId(rs.getString("mcode_id"));
        mcodelist.setMcodeName(rs.getString("mcode_name"));
        mcodelist.setLength(rs.getInt("mcode_length"));
        mcodelist.setType(rs.getInt("mcode_type"));
        mcodelist.setMaintain(rs.getInt("maintain"));
        mcodelist.setDescribe(rs.getString("mcode_remark"));
        mcodelist.setSubSystem(rs.getInt("subsystem"));
        mcodelist.setIsReport(rs.getInt("is_report"));
        mcodelist.setIsUsing(rs.getInt("is_using"));
        return mcodelist;
    }

    public Mcodelist getMcodeList(String mcodeId) {
        return query(SQL_FIND_MCODELIST_BY_MCODEID, new String[] { mcodeId }, new SingleRow());
    }

    public List<Mcodelist> getMaintainMcodeLists(Integer subSystem) {
        if (subSystem == Mcodelist.SUBSYSTEM_ALL) {
            return query(SQL_FIND_MAINTAIN_BY_SUBSYSTEM1, new MultiRow());
        } else {
            return query(SQL_FIND_MAINTAIN_BY_SUBSYSTEM2, new Object[] { subSystem },
                    new int[] { Types.INTEGER }, new MultiRow());
        }
    }

    public List<Mcodelist> getActiveMcodeLists(Integer subSystem) {
        if (subSystem == Mcodelist.SUBSYSTEM_ALL) {
            return query(SQL_FIND_ACTIVE_BY_SUBSYSTEM1, new MultiRow());
        } else {
            return query(SQL_FIND_ACTIVE_BY_SUBSYSTEM2, new Object[] { subSystem },
                    new int[] { Types.INTEGER }, new MultiRow());
        }
    }

    public List<Mcodelist> getOnlyVisiableMcodeLists(Integer subSystem) {
        if (subSystem == Mcodelist.SUBSYSTEM_ALL) {
            return query(SQL_FIND_ONLYVISI_BY_SUBSYSTEM1, new MultiRow());
        } else {
            return query(SQL_FIND_ONLYVISI_BY_SUBSYSTEM2, new Object[] { subSystem },
                    new int[] { Types.INTEGER }, new MultiRow());
        }
    }

    public List<Mcodelist> getExceptNovisiableMcodeLists(Integer subSystem) {
        if (subSystem == Mcodelist.SUBSYSTEM_ALL) {
            return query(SQL_FIND_EXCEPT_BY_SUBSYSTEM1, new MultiRow());
        } else {
            return query(SQL_FIND_EXCEPT_BY_SUBSYSTEM2, new Object[] { subSystem },
                    new int[] { Types.INTEGER }, new MultiRow());
        }
    }

}
