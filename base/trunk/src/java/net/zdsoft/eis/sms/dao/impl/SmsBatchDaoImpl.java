package net.zdsoft.eis.sms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.frame.dao.HibernateDao;
import net.zdsoft.eis.frame.entity.HibernateEntity;
import net.zdsoft.eis.sms.dao.SmsBatchDao;
import net.zdsoft.eis.sms.entity.SmsBatch;
import net.zdsoft.keel.util.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author lilj
 * @since 1.0
 * @version $Id: SmsBatchDaoImpl.java,v 1.3 2007/01/09 11:00:39 chenzy Exp $
 */

public class SmsBatchDaoImpl extends HibernateDao implements SmsBatchDao {
	private static Logger log = LoggerFactory.getLogger(SmsBatchDaoImpl.class);

	public Class getPersistentClass() {
		return SmsBatch.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.zdsoft.cnet3.office.sms.persistence.dao.SmsBatchDao#getSmsBatchById(java.lang.Long)
	 */
	public SmsBatch getSmsBatchById(String id) {
		log.debug("=====getSmsBatchById " + id);

		return (SmsBatch) this.findById(id);
	}

	public void deleteByIds(String[] ids) {
		log.debug("=====deleteSmsBatchByIds" + ids.toString());
		for (int i = 0; i < ids.length; i++) {
			HibernateEntity entity = findById(ids[i]);
			remove(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SmsBatch> getSmsBatchList(String userId, Pagination page) {
		String hql = " from SmsBatch where userid=?";
		String[] params = new String[] { userId };
		//List<SmsBatch> result = this.getHibernateTemplate().find(hql, params);
		//List<SmsBatch> result = this.findHqlByPage(hql, params, page);
		
		DetachedCriteria dc = DetachedCriteria.forClass(SmsBatch.class);
		dc.add(Restrictions.eq("userid", userId));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("intId"));
		//orders.add(Order.desc("sendhour"));
		//orders.add(Order.desc("sendminutes"));
		List<SmsBatch> result = findByCriteria(dc, orders, page);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> getSmsBatchIdByuserId(String userId) {
		String hql = "select id from SmsBatch where userid=?";
		String[] params = new String[] { userId };
		List<String> result = this.find(hql, params);
		return result;
	}

	public void deleteSmsBatchByUserId(String userId) {
		String hql = "DELETE FROM SmsBatch WHERE userid = '" + userId + "'";
		this.remove(hql);

	}

	@SuppressWarnings("unchecked")
	public int getSmsBatchNumByUserId(String userId) {
		String hql = "SELECT count(id) FROM SmsBatch where userid= ?";
		Object[] params = new Object[] { userId };
		List<Integer> result = getHibernateTemplate().find(hql, params);
		if (result == null || result.size() == 0) {
			return 0;
		}
		Integer i = (Integer) result.get(0);
		return i;
	}

}
