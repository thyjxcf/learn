package net.zdsoft.office.desktop.dao;

import java.util.*;

import net.zdsoft.office.desktop.entity.OfficeMemoEx;
import net.zdsoft.keel.util.Pagination;
/**
 * office_memo_ex
 * @author 
 * 
 */
public interface OfficeMemoExDao{

	/**
	 * 新增office_memo_ex
	 * @param officeMemoEx
	 * @return
	 */
	public OfficeMemoEx save(OfficeMemoEx officeMemoEx);

	public void allSave(List<OfficeMemoEx> officeMemoExs);
	/**
	 * 根据ids数组删除office_memo_ex
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public void deletebyMemoId(String[] memoId);

	/**
	 * 更新office_memo_ex
	 * @param officeMemoEx
	 * @return
	 */
	public Integer update(OfficeMemoEx officeMemoEx);

	/**
	 * 根据id获取office_memo_ex
	 * @param id
	 * @return
	 */
	public OfficeMemoEx getOfficeMemoExById(String id);

	/**
	 * 根据ids数组查询office_memo_exmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMemoEx> getOfficeMemoExMapByIds(String[] ids);

	/**
	 * 获取office_memo_ex列表
	 * @return
	 */
	public List<OfficeMemoEx> getOfficeMemoExList();

	public List<OfficeMemoEx> getOfficeMemoExListByMemoId(String memoId);
	/**
	 * 分页获取office_memo_ex列表
	 * @param page
	 * @return
	 */
	public List<OfficeMemoEx> getOfficeMemoExPage(Pagination page);
}