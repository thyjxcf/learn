package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.cache.CacheManager;

public interface SubSystemService extends CacheManager {
	/**
	 * 增加或更新
	 * 
	 * @param subsystem
	 */
	public void saveSubsystem(SubSystem subsystem);

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
	 * @param code
	 *            子系统编号
	 * @return
	 */
	public SubSystem getSubSystemByCode(String code);

	/**
	 * 通过id获取module
	 * 
	 * @param id
	 * @return
	 */
	public SubSystem getSubSystem(Integer id);

	/**
	 * 取出所有的子系统信息
	 * 
	 * @return
	 */
	public List<SubSystem> getSubSystems();
	
	/**
	 * 取出子系统列表(不走缓存)
	 * @return
	 */
	public List<SubSystem> getSubSystemsWithoutCache();

	/**
	 * 根据id取子系统信息
	 * 
	 * @param ids
	 * @return
	 */
	public List<SubSystem> getSubSystems(Integer[] ids);

	/**
	 * 获取所有的子系统名称
	 * 
	 * @return
	 */
	public Map<Integer, SubSystem> getCacheSubSystemMap();

	public List<AppRegistry> getUserSystemList(String contextPath,
			int userOwnerType, Integer[] ids,boolean showAll);
	
    /**
     * 根据id取没有被特殊标注的子系统  特殊的处理方法
     * @param ids
     * @return
     */
    public List<SubSystem> getSpecialSubSystems(Set<Integer> ids);
    
    /**
     * 获取第三方应用
     * @return
     */
    public List<SubSystem> getThirdPartSubSystems(final String source);
    
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
    
    public void insertSubSystem(SubSystem subsystem);
	
	public void updateSubSystem(SubSystem subsystem);
    
    /**
     * 获取系统内最大的ID
     * @return
     */
    public int countMaxId();
}
