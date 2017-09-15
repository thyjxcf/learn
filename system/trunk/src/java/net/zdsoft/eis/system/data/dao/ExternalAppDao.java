package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.keel.util.Pagination;

public interface ExternalAppDao {
	public void addExternalApp(ExternalApp app);

	public void updateExternalApp(ExternalApp app);

	public void deleteExternalApp(String... ids);
	
	/**
	 * 更新app成正式可显示的
	 * @param ids
	 */
	public void updateExternalAppNotTemp(String... ids);

	public List<ExternalApp> getExternalAppListByUnitId(String unitId,int type,int num, boolean temp);
	
	public List<ExternalApp> getExternalAppListByUnionId(String unionId,int type,int num, boolean temp);
	
	public List<ExternalApp> getExternalAppListByCondition(final String unionId,int type,String unitName,boolean temp, Pagination page);
}
