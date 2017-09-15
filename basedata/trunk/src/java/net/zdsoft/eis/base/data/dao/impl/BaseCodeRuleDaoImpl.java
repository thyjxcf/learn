package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.data.dao.BaseCodeRuleDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseCodeRuleDaoImpl extends BaseDao<CodeRule> implements BaseCodeRuleDao {

    private static final String SQL_INSERT_STUCODERULE = "INSERT INTO base_code_rule(id,unit_id,section,"
            + "automatism,code_type,event_source,is_deleted,is_system_init) " + "VALUES(?,?,?,?,?,?,0,?)";

    private static final String SQL_UPDATE_STUCODERULE = "UPDATE base_code_rule SET unit_id=?,"
            + "automatism=?,code_type=?,event_source=? WHERE id=?";

    private static final String SQL_FIND_STUCODERULE_BY_ID = "SELECT * FROM base_code_rule WHERE id=? and is_deleted=0";
    
    private static final String SQL_FIND_STUCODERULE_BY_TYPE = "SELECT * FROM base_code_rule WHERE code_type=? and is_deleted=0";
    
    private static final String SQL_FIND_INIT_CODERULE_BY_TYPE = "SELECT * FROM base_code_rule WHERE code_type=? " +
    		"and unit_id='00000000000000000000000000000000' and is_system_init=1 and is_deleted=0";

    @Override
    public CodeRule setField(ResultSet rs) throws SQLException {
        CodeRule codeRule = new CodeRule();
        codeRule.setId(rs.getString("id"));
        codeRule.setUnitId(rs.getString("unit_id"));
        codeRule.setAutomatism(rs.getString("automatism"));
        codeRule.setCodeType(rs.getInt("code_type"));
        return codeRule;
    }

    public void insertUnitCodeRule(CodeRule codeRule) {
    	if(StringUtils.isBlank(codeRule.getId()))
           codeRule.setId(getGUID());
        update(SQL_INSERT_STUCODERULE,
				new Object[] { codeRule.getId(), codeRule.getUnitId(),
						codeRule.getSection(), codeRule.getAutomatism(),
						codeRule.getCodeType(), codeRule.getEventSourceValue(),
						codeRule.isSystemInit() ? 1 : 0 }, new int[] {
						Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR,
						Types.CHAR, Types.INTEGER, Types.INTEGER });
    }

    public void updateUnitCodeRule(CodeRule codeRule) {
        update(SQL_UPDATE_STUCODERULE, new Object[] { codeRule.getUnitId(),
                codeRule.getAutomatism(), codeRule.getCodeType(),codeRule.getEventSourceValue(), codeRule.getId() }, new int[] {
                Types.CHAR, Types.CHAR, Types.CHAR,Types.INTEGER, Types.CHAR });
    }

    public CodeRule getUnitCodeRule(String id) {
        return query(SQL_FIND_STUCODERULE_BY_ID, id, new SingleRow());
    }
    public List<CodeRule> getUnitCodeRuleList(String type){
    	Object[] objs = new Object[] {  type };
        return query(SQL_FIND_STUCODERULE_BY_TYPE, objs, new MultiRow());
    }
    public CodeRule getInitCodeRule(String type){
    	  return query(SQL_FIND_INIT_CODERULE_BY_TYPE, type, new SingleRow());
    }
}
