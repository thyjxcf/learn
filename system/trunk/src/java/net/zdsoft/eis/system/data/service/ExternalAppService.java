package net.zdsoft.eis.system.data.service;

import java.util.List;

import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;

public interface ExternalAppService {
	public void addExternalApp(ExternalApp app,UploadFile file);

	public void updateExternalApp(ExternalApp app);

	public void deleteExternalApp(String... ids);
	
	/**
	 * 更新app为正式可显示的 
	 */
	public void updateExternalAppNotTemp(String... ids);

	/**
	 * 
	 * @param temp true:临时，页面不显示;false:正式，页面显示
	 * @return
	 */
	public List<ExternalApp> getExternalAppListByUnitId(String unitId,int type,int num, boolean temp);
	
	/**
	 * 
	 * @param temp true:临时，页面不显示;false:正式，页面显示
	 * @return
	 */
	public List<ExternalApp> getExternalAppListByUnionId(String unionId,int type,int num, boolean temp);
	
	/**
	 * 
	 * @param temp true:临时，页面不显示;false:正式，页面显示
	 * @return
	 */
	public List<ExternalApp> getExternalAppListByCondition(String unionId,int type,String unitName,boolean temp, Pagination page);
}
