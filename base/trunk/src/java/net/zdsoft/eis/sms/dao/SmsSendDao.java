package net.zdsoft.eis.sms.dao;

import java.util.List;

import net.zdsoft.eis.frame.dao.IUpdateQueryDao;
import net.zdsoft.eis.sms.entity.SmsSend;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author lilj
 * @since 1.0
 * @version $Id: SmsSendDao.java,v 1.2 2007/01/09 11:00:34 chenzy Exp $
 */

public interface SmsSendDao extends IUpdateQueryDao {
	/**
	 * 根据主键id得到短信发送详细信息entity
	 * @param id
	 * @return
	 */
	public SmsSend getSmsSendById(String id);
	
	/**
	 * 根据短信批次id得到短信发送详细信息entity list
	 * @param batchid 短信批次id
	 * @return
	 */
	public List getSmsSendListByBatchid(Long batchid);
	
	/**
	 * 根据短信批次id得到短信发送详细信息列表
	 * @param batchid
	 * @return List(SmsSend)
	 */
	public List getSmsSendsByBatchid(String batchid);
	/**
	 * 根据短信批次id得到短信发送详细信息列表
	 * @param batchid
	 * @param page
	 * @return
	 */
	public List getSmsSendListByBatchid(String batchid,Pagination page);
}
