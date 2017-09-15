package net.zdsoft.office.msgcenter.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.msgcenter.entity.OfficeMsgMainsend;
/**
 * 信息主送信息表
 * @author 
 * 
 */
public interface OfficeMsgMainsendDao{

	/**
	 * 新增信息主送信息表
	 * @param officeMsgMainsend
	 * @return
	 */
	public OfficeMsgMainsend save(OfficeMsgMainsend officeMsgMainsend);
	
	/**
	 * 批量插入数据
	 * @param officeMsgMainsends
	 */
	public void batchSave(List<OfficeMsgMainsend> officeMsgMainsends);

	/**
	 * 根据ids数组删除信息主送信息表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 根据消息id删除表数据
	 * @param messageId
	 */
	public void deleteByMessageId(String messageId);

	/**
	 * 更新信息主送信息表
	 * @param officeMsgMainsend
	 * @return
	 */
	public Integer update(OfficeMsgMainsend officeMsgMainsend);

	/**
	 * 根据id获取信息主送信息表
	 * @param id
	 * @return
	 */
	public OfficeMsgMainsend getOfficeMsgMainsendById(String id);

	/**
	 * 根据ids数组查询信息主送信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMsgMainsend> getOfficeMsgMainsendMapByIds(String[] ids);

	/**
	 * 获取信息主送信息表列表
	 * @param messageId
	 * @return
	 */
	public List<OfficeMsgMainsend> getOfficeMsgMainsendList(String messageId);

	/**
	 * 分页获取信息主送信息表列表
	 * @param page
	 * @return
	 */
	public List<OfficeMsgMainsend> getOfficeMsgMainsendPage(Pagination page);
}