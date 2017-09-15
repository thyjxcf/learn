/* 
 * @(#)HibernateDao.java    Created on 2006-8-10
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import net.zdsoft.eis.frame.entity.HibernateEntity;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.util.PaginatedList;
import net.zdsoft.leadin.util.SQLUtils;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public abstract class HibernateDao extends HibernateDaoSupport implements
		IUpdateQueryDao {
	private Logger log = LoggerFactory.getLogger(HibernateDao.class);

	public PaginatedList find(HibernateEntity entity, int firstElement,
			int maxElements) {
		throw new UnsupportedOperationException();
	}

	public List find(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	public List find(String hql, Object value) {
		return this.getHibernateTemplate().find(hql, value);
	}

	public List find(String hql, Object[] values) {
		return this.getHibernateTemplate().find(hql, values);
	}

	public List findAll() {
		List result = getHibernateTemplate().loadAll(this.getPersistentClass());
		return (result != null) ? result : Collections.EMPTY_LIST;
	}

	public List findAllSorted(String sortField, boolean ascending) {
		if (sortField == null) {
			return findAll();
		}
		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		detachedCriteria.addOrder(ascending ? Order.asc(sortField) : Order
				.desc(sortField));
		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public List findAllSorted(String sortField) {
		return findAllSorted(sortField, true);
	}

	public HibernateEntity findById(Serializable id) {
		try {
			return (HibernateEntity) getHibernateTemplate().get(
					this.getPersistentClass(), id);
		} catch (DataAccessException e) {
			log.warn("Entity not found: " + e.getLocalizedMessage(), e);
			return null;
		}
	}

	public List findByIds(Collection ids) {
		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());

		ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
				getPersistentClass());
		String idPropertyName = classMetadata.getIdentifierPropertyName();
		detachedCriteria.add(Restrictions.in(idPropertyName, ids));

		return getHibernateTemplate().findByCriteria(detachedCriteria);

	}

	public List findByIds(Object[] ids) {
		if(ids==null||ids.length==0)
			return new ArrayList();
		
		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());

		ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
				getPersistentClass());
		String idPropertyName = classMetadata.getIdentifierPropertyName();
		detachedCriteria.add(Restrictions.in(idPropertyName, ids));

		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public HibernateEntity findWithDetails(Serializable id) {
		throw new UnsupportedOperationException();
	}

	public HibernateEntity findWithoutDetails(Serializable id) {
		throw new UnsupportedOperationException();
	}

	public int getCount() {
		List list = getHibernateTemplate().find(
				"select count(*) from " + getPersistentClass().getName());
		return ((Integer) list.get(0)).intValue();

	}

	public boolean isExisted(Serializable id) {
		final Class entity = this.getPersistentClass();

		try {
			ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
					entity);
			String idPropertyName = classMetadata.getIdentifierPropertyName();
			Iterator it = getHibernateTemplate().iterate(
					"select count(*) from " + entity.getName()
							+ " as entity where entity." + idPropertyName
							+ "=?", id);
			int count = ((Integer) it.next()).intValue();

			return (count > 0) ? true : false;
		} catch (HibernateException e) {
			log.warn(e.toString());

			return false;
		}
	}

	public void refresh(HibernateEntity objectToRefresh) {
		this.getHibernateTemplate().refresh(objectToRefresh);

	}

	// 彻底删除
	public void remove(HibernateEntity objectToRemove) {
		this.getHibernateTemplate().delete(objectToRemove);

	}

	public void remove(Serializable entityId) {
		this.getHibernateTemplate().delete(findById(entityId));
	}

	public int remove(String hqlString) {
		return this.remove(hqlString, (Object[]) null);
	}

	public int remove(String hqlString, Object value) {
		return this.remove(hqlString, new Object[] { value });
	}

	public int remove(String hqlString, Object[] values) {
		Session session = this.getSession();
		String hql = hqlString.toUpperCase().trim();
		String order = getSqlOrder(hql);
		if (!order.equals("DELETE")) {
			hql = "DELETE " + hqlString;
		} else {
			hql = hqlString;
		}

		Query query = session.createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query.executeUpdate();
	}

	private String getSqlOrder(String hql) {
		String order = "";
		for (int i = 0; i < hql.length(); i++) {
			char curChar = hql.charAt(i);
			if (curChar == ' ') {
				return order;
			}
			order = order + curChar;
		}
		return order;
	}

	public void removeAll(HibernateEntity[] entities) {
		for (int i = 0; i < entities.length; i++) {
			remove(entities[i]);
		}
	}

	public void removeAll(Collection ids) {
		for (Iterator it = ids.iterator(); it.hasNext();) {
			remove((String) it.next());
		}

	}

	public void removeAll(Serializable[] entityIds) {
		for (int i = 0; i < entityIds.length; i++) {
			remove(entityIds[i]);
		}

	}

	// 软删除
	public void funDelete(HibernateEntity objectToFunDelete) {
		// 给isdelete赋值
		this.iniIsdeleted(objectToFunDelete, true);

		this.saveOrUpdate(objectToFunDelete);
	}

	public void funDelete(Serializable entityId) {
		HibernateEntity objectToFunDelete = this.findById(entityId);
		this.funDelete(objectToFunDelete);
	}

	public int funDelete(Class clazz) {
		return this.funDelete(clazz, null, (Object[]) null);
	}

	public int funDelete(Class clazz, String whereString) {
		return this.funDelete(clazz, whereString, (Object[]) null);
	}

	public int funDelete(Class clazz, String whereString, Object value) {
		return this.funDelete(clazz, whereString, new Object[] { value });
	}

	public int funDelete(Class clazz, String whereString, Object[] values) {
		// 组装Where条件
		String hql;
		if (whereString != null) {
			hql = whereString.toUpperCase();
			if (hql.indexOf("WHERE") < 0) {
				hql = "WHERE " + hql;
			}
		} else {
			hql = "";
		}
		hql = "UPDATE " + clazz.getName() + " SET isdeleted='1' " + hql;

		Session session = this.getSession(true);
		Query query = session.createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}

		return query.executeUpdate();
	}

	public void funDeleteAll(HibernateEntity[] entities) {
		for (int i = 0; i < entities.length; i++) {
			this.funDelete(entities[i]);
		}
	}

	public void funDeleteAll(Collection ids) {
		for (Iterator it = ids.iterator(); it.hasNext();) {
			this.funDelete((String) it.next());
		}
	}

	public void funDeleteAll(Serializable[] entityIds) {
		for (int i = 0; i < entityIds.length; i++) {
			this.funDelete(entityIds[i]);
		}
	}

	public Serializable save(HibernateEntity objectToSave) {
		// 给updatesatmp赋初值
		this.iniUpdatestamp(objectToSave);

		return this.getHibernateTemplate().save(objectToSave);
	}

	public void saveAll(HibernateEntity[] entities) {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		for (int i = 0; i < entities.length; i++) {
			// 给updatesatmp赋初值
			this.iniUpdatestamp(entities[i]);

			hibernateTemplate.save(entities[i]);
		}
	}

	public void update(HibernateEntity objectToSave) {
		// 给updatesatmp赋初值
		this.iniUpdatestamp(objectToSave);

		this.getHibernateTemplate().update(objectToSave);
	}

	public void updateAll(HibernateEntity[] entities) {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		for (int i = 0; i < entities.length; i++) {
			// 给updatesatmp赋初值
			this.iniUpdatestamp(entities[i]);

			hibernateTemplate.update(entities[i]);
		}
	}

	public void saveOrUpdate(HibernateEntity objectToSave) {
		// 给updatesatmp赋初值
		this.iniUpdatestamp(objectToSave);
		//this.getSession().clear();
		this.getHibernateTemplate().saveOrUpdate(objectToSave);
//		this.getHibernateTemplate().merge(objectToSave);
	}

	public void saveOrUpdateAll(HibernateEntity[] entities) {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		for (int i = 0; i < entities.length; i++) {
			// 给updatesatmp赋初值
			this.iniUpdatestamp(entities[i]);

			hibernateTemplate.saveOrUpdate(entities[i]);
		}
	}

	/**
	 * 抽像方法,继承本类的dao类必须实现此方法,此方法返回dao所对应的实体类,可以由MyClass.class得到
	 * 因为在本类中有许多方法会用到具体的实体类
	 * 
	 * @return entity class
	 */
	public abstract Class getPersistentClass();

	/**
	 * 从查旬结果列表中得到一个对象，条件是结果列表必须只有一个元素
	 * 
	 * @param results
	 * @return Object
	 */
	protected Object findSingleObject(List results) {
		if ((results != null) && (results.size() == 1)) {
			return results.get(0);
		}

		if ((results != null) && (results.size() > 1)) {
		    log.error("Uh oh - found more than one object when single object requested: "
							+ results);
		}

		return null;
	}

	/**
	 * 给updatestamp和createstamp成员变量赋初值，反射方法
	 * @deprecated
	 * @param objectToSave
	 *            更新对象
	 */
	protected void iniStamp(HibernateEntity objectToSave) {
		String[] names = { "modifyTime", "creationTime" };
		iniDefaultValue(objectToSave, names, new Object[] {
				new Long(System.currentTimeMillis()), new Date() });
	}

	/**
	 * 给updatestamp成员变量赋初值，反射方法
	 * 
	 * @param objectToSave
	 *            更新对象
	 */
	protected void iniUpdatestamp(HibernateEntity objectToSave) {
		String[] names = { "modifyTime" };
		iniDefaultValue(objectToSave, names, new Object[] { new Date()});
	}

	private void iniDefaultValue(HibernateEntity objectToSave, String[] names,
			Object[] values) {

		String[] defaultMethods = new String[names.length];
		for (int j = 0; j < names.length; j++) {
			String name = names[j];
			defaultMethods[j] = "set" + (name.charAt(0) + "").toUpperCase()
					+ name.substring(1, name.length());
		}

		java.lang.reflect.Method[] methods = objectToSave.getClass()
				.getMethods();
		for (int i = 0; i < methods.length; i++) {
			for (int j = 0; j < names.length; j++) {
				if (methods[i].getName().equals(defaultMethods[j])) {
					// 给该属性赋值
					try {
						methods[i].invoke(objectToSave, values[j]);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 给isdelete成员变量赋值，用于软删除时
	 * 
	 * @param objectToSave
	 *            软删除对象
	 * @param isdelete
	 *            是否软删除，true?软删除：不软删除
	 */
	protected void iniIsdeleted(HibernateEntity objectToSave, boolean isdelete) {
		String name = "isdeleted";
		String method = "set" + (name.charAt(0) + "").toUpperCase()
				+ name.substring(1, name.length());

		Object[] values = { new Boolean(isdelete) };

		java.lang.reflect.Method[] methods = objectToSave.getClass()
				.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(method)) {
				// 给该属性赋值
				try {
					methods[i].invoke(objectToSave, values);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	@SuppressWarnings("deprecation")
    private void checkWriteOperationAllowed(Session session)
			throws InvalidDataAccessApiUsageException {
		if (this.getHibernateTemplate().isCheckWriteOperations()
				&& this.getHibernateTemplate().getFlushMode() != HibernateAccessor.FLUSH_EAGER
				&& FlushMode.NEVER.equals(session.getFlushMode())) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER) - turn your Session "
							+ "into FlushMode.AUTO or remove 'readOnly' marker from transaction definition");
		}
	}

	/**
	 * 用于批量更新数据;纠正缓存溢出问题
	 * 
	 * @param entities
	 * @throws DataAccessException
	 */
	@SuppressWarnings("deprecation")
    public void saveOrUpdateAll(final Collection entities)
			throws DataAccessException {
		this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				checkWriteOperationAllowed(session);
				int i = 0;
				for (Iterator it = entities.iterator(); it.hasNext();) {
					session.save(it.next());
					i++;
					if (i % 20 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}

				}
				session.flush();
				session.clear();
				return null;
			}
		});//, true
	}

	public void mergeEntity(HibernateEntity objectToMerge) {
		getHibernateTemplate().merge(objectToMerge);
	}

	public void mergeAll(HibernateEntity[] entities) {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		for (int i = 0; i < entities.length; i++) {
			hibernateTemplate.merge(entities[i]);
		}
	}

	@SuppressWarnings("unchecked")
	public List getSubList(List list, Pagination page) {
		List retList = null;
		if (page != null && (list != null && list.size() > 0)) {
			if (page.getPageIndex() == 0) {
				page.setPageIndex(1);
			}
			if (page.getMaxRowCount() == 0) {
				page.setMaxRowCount(list.size());
			}
			page.initialize();
			int pageIndex = page.getPageIndex();
			int pageSize = page.getPageSize();
			int maxRowCount = page.getMaxRowCount();

			int toIndex = 0;
			int fromIndex = 0;
			if (pageIndex * pageSize > maxRowCount) {
				toIndex = maxRowCount;
			} else {
				toIndex = pageIndex * pageSize;
			}
			fromIndex = (pageIndex - 1) * pageSize;

			retList = list.subList(fromIndex, toIndex);
		}
		if (retList == null) {
			return list;
		} else {
			return retList;
		}
	}

	/**
	 * 分页查询，并且设置分页对象的记录总数count
	 * <p>
	 * 不支持distinct & group by 查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param page
	 *            分页对象
	 * @return 当前页的记录列表
	 */
	public List findHqlByPage(final String hql, final Pagination page) {
		String hqlCount = SQLUtils.CountSQLString(hql);
		if (null == hqlCount)
			throw new UnsupportedOperationException(
					"不支持distinct & group by 查询！");
		return findHqlByPage(hql, hqlCount, page);
	}

	/**
	 * 分页查询，并且设置分页对象的记录总数count
	 * <p>
	 * 不支持distinct & group by 查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param params
	 *            以数组的形式按顺序传入查询条件
	 * @param page
	 *            分页对象
	 * @return 当前页的记录列表
	 */
	public List findHqlByPage(String hql, Object[] params, Pagination page) {
		String hqlCount = SQLUtils.CountSQLString(hql);
		if (null == hqlCount)
			throw new UnsupportedOperationException(
					"不支持distinct & group by 查询！");
		return findHqlByPage(hql, hqlCount, params, page);
	}

	/**
	 * 分页查询，并且设置分页对象的记录总数count
	 * 
	 * @param hql
	 *            查询语句
	 * @param hqlcount
	 *            count语句
	 * @param page
	 *            分页对象
	 * @return 当前页的记录列表
	 */
	public List findHqlByPage(final String hql, final String hqlCount,
			final Pagination page) {
		List countlist = getHibernateTemplate().find(hqlCount);
		int totalcount = getCount(countlist);
		if(page.getPageIndex()==0)
			page.setPageIndex(1);
		page.setMaxRowCount(totalcount);
		List resultlist = findByHql(hql, null, page);
		return resultlist;
	}

	/**
	 * 带参数的分页查询，并且设置分页对象的记录总数count
	 * 
	 * @param hql
	 *            查询语句
	 * @param hqlCount
	 *            count语句
	 * @param params
	 *            以数组的形式按顺序传入查询条件
	 * @param page
	 *            分页对象<tt>Pagination</tt>
	 * @return 当前页的记录列表
	 * @see net.zdsoft.keel.util.Pagination
	 */
	public List findHqlByPage(final String hql, final String hqlCount,
			final Object[] params, final Pagination page) {
		List countlist = getHibernateTemplate().find(hqlCount, params);
		int totalcount = getCount(countlist);
		if(page.getPageIndex()==0)
			page.setPageIndex(1);
		page.setMaxRowCount(totalcount);
		List resultlist = findByHql(hql, params, page);
		return resultlist;
	}

	/**
	 * hql分页查询
	 * 
	 * @param hql
	 *            hql语句，可以多表关联查询
	 * @param page
	 *            分页对象
	 * @return
	 */
	private List findByHql(final String hql, final Object[] params,
			final Pagination page) {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((page.getPageIndex() - 1)
						* page.getPageSize());// 设定开始记录
				query.setMaxResults(page.getPageSize());// 设定每页显示记录数
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				page.initialize();
				return query.list();
			}
		});
		return list;
	}

	/**
	 * 得到count数目
	 * 
	 * @param aList
	 *            只有一条包含count结果数据的list
	 * @return count数量
	 */
	public int getCount(List aList) {
		int retVal = 0;
		if (aList == null || aList.isEmpty()) {
			retVal = 0;
		} else {
			try {
				retVal = NumberUtils.createInteger(aList.get(0).toString())
						.intValue();
			} catch (NumberFormatException ex) {
			}
		}
		return retVal;
	}

	/**
	 * QBC的分页查询
	 * 
	 * @param detachedCriteria
	 * @param page
	 * @return
	 */
	@SuppressWarnings("deprecation")
    public List findByCriteria(final DetachedCriteria detachedCriteria,
			final List<Order> orders, final Pagination page) {
		List list = (List) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						int totalCount = ((Long) criteria.setProjection(
								Projections.rowCount()).uniqueResult())
								.intValue();
						criteria.setProjection(null);
						if (null != orders && orders.size() > 0) {
							for (Order order : orders) {
								criteria.addOrder(order);
							}
						}
						List querylist = criteria.setFirstResult(
								(page.getPageIndex() - 1) * page.getPageSize())
								.setMaxResults(page.getPageSize()).list();
						page.setMaxRowCount(totalCount);
						page.initialize();
						return querylist;
					}
				});//, true
		return list;
	}

	protected List findSimilarEntities(DynaBean entity) {
		DynaProperty[] props = entity.getDynaClass().getDynaProperties();
		if (0 == props.length) {
			throw new IllegalArgumentException(
					"no available property in the entity");
		}
		StringBuffer hql = new StringBuffer("FROM ");
		hql.append(this.getPersistentClass().getName());
		hql.append(" WHERE ");
		Object[] values = new Object[props.length];
		for (int i = 0; i < props.length; i++) {
			if (i > 0) {
				hql.append(" AND ");
			}
			hql.append(props[i].getName());
			hql.append(" = ? ");
			values[i] = entity.get(props[i].getName());
		}
		return find(hql.toString(), values);
	}
	
}
