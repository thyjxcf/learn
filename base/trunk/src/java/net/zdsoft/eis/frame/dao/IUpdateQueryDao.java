/* 
 * @(#)IUpdateQueryDao.java    Created on 2006-8-10
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.dao;

import java.io.Serializable;
import java.util.Collection;

import net.zdsoft.eis.frame.entity.HibernateEntity;

@SuppressWarnings("unchecked")
public interface IUpdateQueryDao extends IQueryDao {
	// 彻底删除
	/**
	 * 删除指定id的实体
	 * 
	 * @param entityId
	 */
	void remove(Serializable entityId);

	/**
	 * 删除Entity
	 * 
	 * @param objectToRemove
	 */
	void remove(HibernateEntity objectToRemove);

	/**
	 * 删除符合HQL语句的记录（不带delete的HQL语句）
	 * 
	 * @param hqlString
	 *            Hql语句
	 * @return int 影响的行数
	 */
	int remove(String hqlString);

	/**
	 * 删除符合HQL语句的记录（不带delete的HQL语句）
	 * 
	 * @param hqlString
	 *            Hql语句
	 * @param value
	 *            参数值
	 * @return int 影响的行数
	 */
	int remove(String hqlString, Object value);

	/**
	 * 删除符合HQL语句的记录（不带delete的HQL语句）
	 * 
	 * @param hqlString
	 *            Hql语句
	 * @param values
	 *            参数值
	 * @return int 影响的行数
	 */
	int remove(String hqlString, Object[] values);

	/**
	 * 批量删除实体
	 * 
	 * @param entities
	 */
	void removeAll(Serializable[] entityIds);

	/**
	 * 批量删除实体, by id
	 * 
	 * @param entities
	 */
	void removeAll(Collection ids);

	/**
	 * 批量删除实体
	 * 
	 * @param entities
	 */
	void removeAll(HibernateEntity[] entities);

	// 软删除
	/**
	 * 软删除指定id的实体
	 * 
	 * @param entityId
	 */
	void funDelete(Serializable entityId);

	/**
	 * 软删除Entity
	 * 
	 * @param objectToRemove
	 */
	void funDelete(HibernateEntity objectToFunDelete);

	/**
	 * 软删除符合Hql语句where条件的记录
	 * 
	 * @param clazz
	 *            PO类
	 * @return int
	 */
	int funDelete(Class clazz);

	/**
	 * 软删除符合Hql语句where条件的记录
	 * 
	 * @param clazz
	 *            PO类
	 * @param whereString
	 *            where条件（Hql语句，可以不带where关键字）
	 * @return int
	 */
	int funDelete(Class clazz, String whereString);

	/**
	 * 软删除符合Hql语句where条件的记录
	 * 
	 * @param clazz
	 *            PO类
	 * @param whereString
	 *            where条件（Hql语句，可以不带where关键字）
	 * @param value
	 *            条件参数值
	 * @return int
	 */
	int funDelete(Class clazz, String whereString, Object value);

	/**
	 * 软删除符合Hql语句where条件的记录
	 * 
	 * @param clazz
	 *            PO类
	 * @param whereString
	 *            where条件（Hql语句，可以不带where关键字）
	 * @param values
	 *            条件参数值
	 * @return int
	 */
	int funDelete(Class clazz, String whereString, Object[] values);

	/**
	 * 批量软删除实体
	 * 
	 * @param entities
	 */
	void funDeleteAll(Serializable[] entityIds);

	/**
	 * 批量软删除实体, by id
	 * 
	 * @param entities
	 */
	void funDeleteAll(Collection ids);

	/**
	 * 批量软删除实体
	 * 
	 * @param entities
	 */
	void funDeleteAll(HibernateEntity[] entities);

	/**
	 * 重新从数据库读取entity
	 * 
	 * @param objectToRefresh
	 */
	void refresh(HibernateEntity objectToRefresh);

	/**
	 * 插入实体，同时更新entity的修改时间(updatestamp)
	 * 
	 * @param objectToSave
	 */
	Serializable save(HibernateEntity objectToSave);

	/**
	 * 批量保存实体，同时更新entity的修改时间(updatestamp)
	 * 
	 * @param entities
	 */
	void saveAll(HibernateEntity[] entities);

	/**
	 * 更新实体，同时更新entity的修改时间(updatestamp)
	 * 
	 * @param objectToSave
	 */
	void update(HibernateEntity objectToSave);

	/**
	 * 更新多个实体 同时更新entity的修改时间(updatestamp)
	 * 
	 * @param entities
	 */
	void updateAll(HibernateEntity[] entities);

	/**
	 * 更新实体，对已存在的实体修改后保存实体时 插入实体，也可以用在新增加一个实体时 同时更新entity的修改时间(updatestamp)
	 * 
	 * @param objectToSave
	 */
	void saveOrUpdate(HibernateEntity objectToSave);

	/**
	 * 合并实体
	 * 
	 * @param objectToMerge
	 */
	void mergeEntity(HibernateEntity objectToMerge);

	/**
	 * 合并实体all add by jiangf 2008-6-4
	 * 
	 * @param objectToMerge
	 */
	void mergeAll(HibernateEntity[] entities);

	/**
	 * 更新多个实体集 插入多个实体集 同时更新entity的修改时间(updatestamp)
	 * 
	 * @param entities
	 */
	void saveOrUpdateAll(HibernateEntity[] entities);

}
