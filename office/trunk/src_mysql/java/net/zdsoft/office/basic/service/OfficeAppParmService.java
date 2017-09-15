package net.zdsoft.office.basic.service;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.basic.entity.OfficeAppParm;
/**
 * office_app_parm(APP权限设置)
 * @author 
 * 
 */
public interface OfficeAppParmService{

	/**
	 * 新增office_app_parm(APP权限设置)
	 * @param officeAppParm
	 * @return
	 */
	public OfficeAppParm save(OfficeAppParm officeAppParm);

	/**
	 * 根据ids数组删除office_app_parm(APP权限设置)数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_app_parm(APP权限设置)
	 * @param officeAppParm
	 * @return
	 */
	public Integer update(OfficeAppParm officeAppParm);

	/**
	 * 根据id获取office_app_parm(APP权限设置)
	 * @param unitId
	 * @param type TODO
	 * @return
	 */
	public OfficeAppParm getOfficeAppParmByUnitId(String unitId, String type);
	
	/**
	 * 根据unitId获取app模块权限
	 * @param unitId
	 * @param isUsing true--获取启用的list，false--获取全部
	 * @return
	 */
	public Map<String, OfficeAppParm> getMapByUnitId(String unitId, boolean isUsing);

}