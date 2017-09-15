package net.zdsoft.eis.system.frame.dao;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.ModelOperator;

public interface ModelOperatorDao {

    /**
     * 通过id获取ModelOperator对象
     * 
     * @param modelOperatorId
     * @return
     */
    public ModelOperator getModelOperator(long modelOperatorId);

    /**
     * 获取所有模块的操作列表
     * 
     * @return
     */
    public List<ModelOperator> getModelOperators();

}
