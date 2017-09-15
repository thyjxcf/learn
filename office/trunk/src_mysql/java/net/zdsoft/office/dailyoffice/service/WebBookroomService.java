package net.zdsoft.office.dailyoffice.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.WebBookroom;
/**
 * web_bookroom
 * @author 
 * 
 */
public interface WebBookroomService{

	/**
	 * 新增web_bookroom
	 * @param webBookroom
	 * @return
	 */
	public WebBookroom save(WebBookroom webBookroom);
	
	/**
	 * 批量保存
	 * @param webBookrooms
	 */
	public void batchSave(List<WebBookroom> webBookrooms);
	
	/**
	 * 撤销是修改状态
	 * @param webBookrooms
	 */
	public void batchUpdate(List<WebBookroom> webBookrooms);

	/**
	 * 根据staffids数组删除web_bookroom数据
	 * @param staffids
	 * @return
	 */
	public Integer delete(String[] staffids);

	/**
	 * 更新web_bookroom
	 * @param webBookroom
	 * @return
	 */
	public Integer update(WebBookroom webBookroom);

	/**
	 * 根据staffid获取web_bookroom
	 * @param staffid
	 * @return
	 */
	public WebBookroom getWebBookroomById(String staffid);

	/**
	 * 根据staffids数组查询web_bookroommap
	 * @param staffids
	 * @return
	 */
	public Map<String, WebBookroom> getWebBookroomMapByIds(String[] staffids);

	/**
	 * 获取web_bookroom列表
	 * @return
	 */
	public List<WebBookroom> getWebBookroomList();

	/**
	 * 分页获取web_bookroom列表
	 * @param page
	 * @return
	 */
	public List<WebBookroom> getWebBookroomPage(Pagination page);
}