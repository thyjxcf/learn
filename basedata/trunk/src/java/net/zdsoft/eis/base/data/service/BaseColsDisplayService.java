package net.zdsoft.eis.base.data.service;

import net.zdsoft.eis.base.common.service.ColsDisplayService;

/**
 * @author yanb
 * 
 */
public interface BaseColsDisplayService extends ColsDisplayService {

	/**
	 * 保存页面设置
	 * 
	 * @param schID
	 *            真实schid
	 * @param schGUID
	 *            临时schid，可能0
	 * @param stuAdjustClassDto
	 *            页面设置信息DTO
	 * @throws Exception
	 */
	public void saveColsDisplay(String schID, String schGUID,
			String columnsIds, String type);
}
