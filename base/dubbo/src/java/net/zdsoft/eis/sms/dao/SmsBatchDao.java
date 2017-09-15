package net.zdsoft.eis.sms.dao;

import java.util.List;

import net.zdsoft.eis.frame.dao.IUpdateQueryDao;
import net.zdsoft.eis.sms.entity.SmsBatch;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author lilj
 * @since 1.0
 * @version $Id: SmsBatchDao.java,v 1.3 2007/01/09 11:00:34 chenzy Exp $
 */

public interface SmsBatchDao extends IUpdateQueryDao {
	/**
	 * 根据主键id得到短信批次entity
	 * 
	 * @param id
	 * @return
	 */
	public SmsBatch getSmsBatchById(String id);

	/**
	 * 根据主键id删除短信批次信息
	 * 
	 * @param ids
	 */
	public void deleteByIds(String[] ids);

	/**
	 * 根据用户id取得短信批次列表
	 * 
	 * @return
	 */
	public List<SmsBatch> getSmsBatchList(String userId, Pagination page);
	/**
	 * 根据用户id取得短信批次id列表
	 * @param userId
	 * @return
	 */
	public List<String> getSmsBatchIdByuserId(String userId);
	/**
	 * 根据userId删除短信批次
	 * @param userId
	 */
	public void deleteSmsBatchByUserId(String userId);
	/**
	 * 根据userId得到短信批次条数
	 * @param userId
	 * @return
	 */
	public int getSmsBatchNumByUserId(String userId);
}
