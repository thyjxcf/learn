package net.zdsoft.eis.sms.dao.impl;

import java.util.List;

import net.zdsoft.eis.frame.dao.HibernateDao;
import net.zdsoft.eis.sms.dao.SmsSendDao;
import net.zdsoft.eis.sms.entity.SmsSend;
import net.zdsoft.keel.util.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author lilj
 * @since 1.0
 * @version $Id: SmsSendDaoImpl.java,v 1.2 2007/01/09 11:00:39 chenzy Exp $
 */

public class SmsSendDaoImpl extends HibernateDao implements SmsSendDao {
	private static Logger log = LoggerFactory.getLogger(SmsSendDaoImpl.class);


	public Class getPersistentClass() {
		return SmsSend.class;
	}

	
	public SmsSend getSmsSendById(String id) {
		log.debug("=========getSmsSendById " + id);
		return (SmsSend) this.findById(id);
	}

	
	public List getSmsSendListByBatchid(Long batchid) {
		log.debug("========getSmsSendListByBatchid " + batchid);
		StringBuffer hql = new StringBuffer(" from ");
		hql.append(this.getPersistentClass().getName());
		hql.append(" as ss where ss.parent.id=?");

		return this.getSession().createQuery(hql.toString()).setParameter(0,
				batchid).list();
	}

	public List getSmsSendsByBatchid(String batchid) {
		log.debug("========getSmsSendsByBatchid " + batchid);
		StringBuffer hql = new StringBuffer(" from ");
		hql.append(this.getPersistentClass().getName());
		hql.append(" as ss where ss.parent.id=?");

		return this.getSession().createQuery(hql.toString()).setParameter(0,
				batchid).list();
	}

	@SuppressWarnings("unchecked")
	public List<SmsSend> getSmsSendListByBatchid(String batchid, Pagination page) {
		String hql = " from SmsSend as ss where ss.parent.id=?";
		String[] params=new String[]{batchid};
		List<SmsSend> result=this.findHqlByPage(hql, params, page);
		return result;
	}
	
}
