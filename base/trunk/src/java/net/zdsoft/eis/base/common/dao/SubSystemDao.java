package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.keel.util.Pagination;

/**
 * @author luxingmu
 * @version $Revision: 1.10 $, $Date: 2007/01/09 10:04:08 $
 */
public interface SubSystemDao {
    /**
     * 增加
     * 
     * @param subSystem
     */
    public void insertSubSystem(SubSystem subSystem);

    /**
     * 更新
     * 
     * @param subSystem
     */
    public void updateSubSystem(SubSystem subSystem);

    /**
     * 子系统名称是否重复(应用注册时判断名称是否重复时使用)
     * 
     * @param name
     * @return
     */
    public boolean isRepeatSubSystemName(String name);

    /**
     * 通过子系统编号获取子系统信息
     * 
     * @param code 子系统编号
     * @return
     */
    public SubSystem getSubSystemByCode(String code);

    /**
     * 取出所有的子系统信息
     * 
     * @return
     */
    public List<SubSystem> getSubSystems();
    
    /**
     * 根据id取子系统信息
     * 
     * @return
     */
    public List<SubSystem> getSubSystems(Integer... ids);
    
    /**
     * 根据id取没有被特殊标注的子系统  特殊的处理方法
     * @param ids
     * @return
     */
    public List<SubSystem> getSpecialSubSystems(Integer... ids);
    
    /**
     * 根据类型获取第三方应用
     * @param source
     * @return
     */
    public List<SubSystem> getThirdPartAppSubSystems(String source);

    /**
     * 按条件获取
     * @param searchName 模块名称
     * @param searchCode 模块编码
     * @param searchSortType 所属AP(0-9)
     * @param searchSource 1-内置模块,2-外置模块
     * @param page
     * @return
     */
    public List<SubSystem> getSubSystemsByConditions(String searchName, String searchCode, String searchSortType, String searchSource, Pagination page);
    
    /**
     * 按ID删除外置模块
     * @param subSystemId
     */
    public void deleteSubSystem(Integer subSystemId);
    
    /**
     * 获取系统内最大的ID
     * @return
     */
    public int countMaxId();
}
