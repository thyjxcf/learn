package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.CodeRuleBuildDao;
import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;

/**
 * @author yanb
 * 
 */
public class CodeRuleBuildDaoImpl extends BaseDao<CodeRuleDetail> implements CodeRuleBuildDao {
    // 全局号码规则
    private static final String SQL_FIND_CODERULE_BY_SECTION_TYPE = "SELECT * FROM base_code_rule "
            + "WHERE section=? AND code_type=? AND is_deleted=0 AND is_system_init=0";
    private static final String SQL_FIND_CODERULE_BY_TYPE = "SELECT * FROM base_code_rule "
            + "WHERE code_type=? AND is_deleted=0 AND is_system_init=0";

    // 单位号码规则
    private static final String SQL_FIND_CODERULE_BY_UNITID_CODETYPE = "SELECT * FROM base_code_rule WHERE unit_id=? AND code_type=? AND is_deleted=0";

    // 号码规则明细
    private static final String SQL_FIND_CODERULE_DETAIL_BY_RULEID = "SELECT * FROM base_code_rule_detail "
            + "WHERE rule_id=? AND is_deleted=0 order by rule_position";

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

    private CodeRule setCodeRuleField(ResultSet rs) throws SQLException {
        CodeRule codeRule = new CodeRule();
        codeRule.setId(rs.getString("id"));
        codeRule.setUnitId(rs.getString("unit_id"));
        codeRule.setAutomatism(rs.getString("automatism"));
        codeRule.setCodeType(rs.getInt("code_type"));
        codeRule.setSection(rs.getInt("section"));
        codeRule.setSystemInit(rs.getBoolean("is_system_init"));
        return codeRule;
    }

    public CodeRule getCodeRule(String section, int type) {
        return query(SQL_FIND_CODERULE_BY_SECTION_TYPE, new Object[] { section, type },
                new SingleRowMapper<CodeRule>() {

                    public CodeRule mapRow(ResultSet rs) throws SQLException {
                        return setCodeRuleField(rs);
                    }
                });
    }

    public List<CodeRule> getCodeRules(int type) {
        return query(SQL_FIND_CODERULE_BY_TYPE, new Object[] { type },
                new MultiRowMapper<CodeRule>() {
                    @Override
                    public CodeRule mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return setCodeRuleField(rs);
                    }
                });
    }

    public CodeRule getUnitCodeRule(String schoolId, int codeType) {
        return query(SQL_FIND_CODERULE_BY_UNITID_CODETYPE, new Object[] { schoolId, codeType },
                new SingleRowMapper<CodeRule>() {

                    public CodeRule mapRow(ResultSet rs) throws SQLException {
                        return setCodeRuleField(rs);
                    }
                });
    }

    public List<CodeRuleDetail> getCodeRuleDetails(String ruleId) {
        return query(SQL_FIND_CODERULE_DETAIL_BY_RULEID, new Object[] { ruleId }, new MultiRow());
    }

}
