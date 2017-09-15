package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.ModelOperatorDao;
import net.zdsoft.eis.system.frame.entity.ModelOperator;

public class ModelOperatorDaoImpl extends BaseDao<ModelOperator> implements ModelOperatorDao {
    private static final String SQL_FIND_MODELOPERATOR_BY_ID = "SELECT * FROM sys_model_operation WHERE id=?";

    private static final String SQL_FIND_MODELOPERATOR = "SELECT * FROM sys_model_operation "
            + "WHERE isactive='1' order by orderid";

    public ModelOperator setField(ResultSet rs) throws SQLException {
        ModelOperator modelOperator = new ModelOperator();
        modelOperator.setId(rs.getLong("id"));
        modelOperator.setModuleid(rs.getLong("moduleid"));
        modelOperator.setOperatorname(rs.getString("operatorname"));
        modelOperator.setOperheading(rs.getString("operheading"));
        modelOperator.setDescription(rs.getString("description"));
        modelOperator.setOperatortype(rs.getInt("operatortype"));
        modelOperator.setIsactive(rs.getBoolean("isactive"));
        modelOperator.setOrderid(rs.getInt("orderid"));
        modelOperator.setOpergroup(rs.getInt("opergroup"));
        modelOperator.setOperweight(rs.getInt("operweight"));
        return modelOperator;
    }

    public ModelOperator getModelOperator(long modelOperatorId) {
        return query(SQL_FIND_MODELOPERATOR_BY_ID, new Object[] { modelOperatorId },
                new SingleRow());
    }

    public List<ModelOperator> getModelOperators() {
        return query(SQL_FIND_MODELOPERATOR, new MultiRow());

    }

}
