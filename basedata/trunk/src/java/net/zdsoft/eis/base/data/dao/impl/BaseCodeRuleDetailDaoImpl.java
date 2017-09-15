package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.data.dao.BaseCodeRuleDetailDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhongh
 * @since 1.0
 * @version $Id: unitCodeRuleListDaoImpl.java,v 1.2 2006/10/24 12:38:56 dongzk Exp $
 */
public class BaseCodeRuleDetailDaoImpl extends BaseDao<CodeRuleDetail> implements
        BaseCodeRuleDetailDao {
    private static final String SQL_INSERT_STUCODERULELIST = "INSERT INTO base_code_rule_detail(id,rule_id,data_type,"
            + "rule_position,rule_number,constant,remark,in_serial_number,event_source,is_deleted) VALUES(?,?,?,?,?,?,?,?,?,0)";

    private static final String SQL_UPDATE_STUCODERULELIST = "UPDATE base_code_rule_detail SET rule_id=?,data_type=?,"
            + "rule_position=?,rule_number=?,constant=?,remark=?,in_serial_number=?,event_source=? WHERE id=?";

    private static final String SQL_DELETE_STUCODERULELIST_BY_ID_MQ = "update base_code_rule_detail set is_deleted=1,event_source=? WHERE id=?";

    private static final String SQL_FIND_STUCODERULELIST_BY_ID = "SELECT * FROM base_code_rule_detail WHERE id=? and is_deleted=0";

    private static final String SQL_FIND_MAX_RULEPOSITION_BY_RULEID = "SELECT max(rule_position) FROM base_code_rule_detail "
            + "WHERE rule_id=? AND data_type<>'serialno' and is_deleted=0";

    private static final String SQL_EXIST_RULEPOSITION_BY_RULEID = "SELECT count(id) FROM base_code_rule_detail "
            + "WHERE rule_id=? AND data_type='serialno' and is_deleted=0";

    private static final String SQL_EXIST_RULEPOSITION_BY_RULEID_RULEPOSITION = "SELECT * FROM base_code_rule_detail "
            + "WHERE rule_id=? AND rule_position>? AND rule_position<>99 and is_deleted=0";
    private static final String SQL_EXIST_RULEPOSITION_BY_TYPE_RULEPOSITION="select a.* from base_code_rule_detail a,base_code_rule b where a.rule_position=? " +
    		"and b.id=a.rule_id and b.code_type=? and a.is_deleted=0 and b.is_deleted=0";
    private static final String SQL_RULEDETAIL_BY_RULEID="select * from base_code_rule_detail where rule_id=? and is_deleted=0 order by rule_position";
    @Override
    public CodeRuleDetail setField(ResultSet rs) throws SQLException {
        CodeRuleDetail codeRuleDetail = new CodeRuleDetail();
        codeRuleDetail.setId(rs.getString("id"));
        codeRuleDetail.setRuleId(rs.getString("rule_id"));
        codeRuleDetail.setDataType(rs.getString("data_type"));
        codeRuleDetail.setRulePosition(rs.getInt("rule_position"));
        codeRuleDetail.setRuleNumber(rs.getInt("rule_number"));
        codeRuleDetail.setConstant(rs.getString("constant"));
        codeRuleDetail.setRemark(rs.getString("remark"));
        codeRuleDetail.setInSerialNumber(rs.getBoolean("in_serial_number"));
        return codeRuleDetail;
    }

    public void insertUnitCodeRuleList(CodeRuleDetail codeRuleDetail) {
        List<CodeRuleDetail> codeRuleContentList = new ArrayList<CodeRuleDetail>();
        codeRuleContentList.add(codeRuleDetail);
        batchInsertUnitCodeRuleList(codeRuleContentList);
    }

    public void batchInsertUnitCodeRuleList(List<CodeRuleDetail> codeRuleContentList) {
        List<Object[]> list = new ArrayList<Object[]>(codeRuleContentList.size());
        for (CodeRuleDetail dto : codeRuleContentList) {
        	if(StringUtils.isBlank(dto.getId()))
        	   dto.setId(getGUID());
            Object[] rs = new Object[] { dto.getId(), dto.getRuleId(), dto.getDataType(),
                    dto.getRulePosition(), dto.getRuleNumber(), dto.getConstant(), dto.getRemark(),dto.isInSerialNumber()?1:0,dto.getEventSourceValue() };
            list.add(rs);
        }
        batchUpdate(SQL_INSERT_STUCODERULELIST, list, new int[] { Types.CHAR, Types.CHAR,
                Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.INTEGER});
    }

    public void updateUnitCodeRuleList(CodeRuleDetail codeRuleDetail) {
        update(SQL_UPDATE_STUCODERULELIST, new Object[] { codeRuleDetail.getRuleId(),
                codeRuleDetail.getDataType(), codeRuleDetail.getRulePosition(),
                codeRuleDetail.getRuleNumber(), codeRuleDetail.getConstant(),
                codeRuleDetail.getRemark(),codeRuleDetail.isInSerialNumber()?1:0,codeRuleDetail.getEventSourceValue(), codeRuleDetail.getId() }, new int[] { Types.CHAR,
                Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.INTEGER,
                Types.CHAR });
    }

    public void updateUnitCodeRuleLists(CodeRuleDetail[] unitCodeRuleLists) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < unitCodeRuleLists.length; i++) {
            CodeRuleDetail stuList = unitCodeRuleLists[i];
            listOfArgs.add(new Object[] { stuList.getRuleId(), stuList.getDataType(),
                    stuList.getRulePosition(), stuList.getRuleNumber(), stuList.getConstant(),
                    stuList.getRemark(),stuList.isInSerialNumber()?1:0,stuList.getEventSourceValue(), stuList.getId() });

        }
        int[] argTypes = new int[] { Types.CHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER,Types.INTEGER, Types.CHAR };
        batchUpdate(SQL_UPDATE_STUCODERULELIST, listOfArgs, argTypes);
    }

    public void deleteUnitCodeRuleList(String id,EventSourceType e){
    	update(SQL_DELETE_STUCODERULELIST_BY_ID_MQ, new Object[] { e.getValue(),id });
    }
    public CodeRuleDetail getUnitCodeRuleList(String id) {
        return query(SQL_FIND_STUCODERULELIST_BY_ID, id, new SingleRow());
    }

    public List<CodeRuleDetail> getMoreRuleposition(String ruleid, int ruleposition) {
        Object[] objs = new Object[] { ruleid, ruleposition };
        return query(SQL_EXIST_RULEPOSITION_BY_RULEID_RULEPOSITION, objs, new MultiRow());
    }
    public List<CodeRuleDetail> getDetailByTypeAndPosition(String type,int ruleposition){
    	Object[] objs = new Object[] {  ruleposition,type };
        return query(SQL_EXIST_RULEPOSITION_BY_TYPE_RULEPOSITION, objs, new MultiRow());
    }
    public List<CodeRuleDetail> getDetailByRuleid(String ruleid){
    	Object[] objs = new Object[] {  ruleid };
        return query(SQL_RULEDETAIL_BY_RULEID, objs, new MultiRow());
    }
    public int getMaxRuleposition(String ruleId) {
        return queryForInt(SQL_FIND_MAX_RULEPOSITION_BY_RULEID, new Object[] { ruleId });
    }

    public boolean isExistsSerialno(String ruleId) {
        return queryForInt(SQL_EXIST_RULEPOSITION_BY_RULEID, new Object[] { ruleId }) > 0 ? true
                : false;
    }
}
