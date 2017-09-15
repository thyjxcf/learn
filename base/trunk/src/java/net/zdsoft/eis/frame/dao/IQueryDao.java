/* 
 * @(#)IQueryDao.java    Created on 2006-8-10
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.zdsoft.eis.frame.entity.HibernateEntity;
import net.zdsoft.keelcnet.util.PaginatedList;

@SuppressWarnings("unchecked")
public interface IQueryDao {
	/**
	 * Find an object by its identifier. This should return only the specified
	 * entity, lazy load other associated entities.
	 * 
	 * This method returns the same object passed as argument.
	 * 
	 * @param id
	 *            identifier
	 * @return the value with that id, may have properties not initialized
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if an object with that id doesn't exist
	 */
	HibernateEntity findById(Serializable id);

	/**
	 * Find an object, not the associated ones..
	 * 
	 * @param id
	 *            identifier
	 * @return the value with that id and all properties initialized
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if an object with that id doesn't exist
	 */
	HibernateEntity findWithoutDetails(Serializable id);

	/**
	 * Find an object, and all related entities by its identifier...
	 * 
	 * @param id
	 *            identifier
	 * @return the value with that id and all properties initialized
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if an object with that id doesn't exist
	 */
	HibernateEntity findWithDetails(Serializable id);

	/**
	 * Find a List of objects by their identifiers. This should return only the
	 * specified entity, not the associated ones.
	 * 
	 * @param ids
	 *            collection of identifiers
	 * @return the values with that ids
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if an object with that id doesn't exist
	 */
	List findByIds(Collection ids);

	/**
	 * Find a List of objects by their identifiers. This should return only the
	 * specified entity, not the associated ones.
	 * 
	 * @param ids
	 *            collection of identifiers
	 * @return the values with that ids
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if an object with that id doesn't exist
	 */
	List findByIds(Object[] ids);

	/**
	 * Return entity count.
	 * 
	 * @return
	 */
	int getCount();

	/**
	 * check entity existence
	 * 
	 * @param id
	 * @return
	 */
	boolean isExisted(Serializable id);

	/**
	 * Find objects with properties matching those of value. This should not
	 * return the associated entities. Properties whose value is null and
	 * Collections are ignored.
	 * 
	 * @param value
	 *            parameters to filter on
	 * @param firstElement
	 *            the first result, numbered from 0
	 * @param maxElements
	 *            the maximum number of results
	 * @return
	 */
	PaginatedList find(HibernateEntity entity, int firstElement, int maxElements);

	/**
	 * Return all persistent instances
	 * 
	 * @return List of objects
	 */

	List findAll();

	/**
	 * use hql query string to find the persistent classes
	 * 
	 * @param hql
	 *            - a query expressed in Hibernate's query language
	 * @return a List containing 0 or more persistent instances
	 */
	List find(String hql);

	/**
	 * Return all persistent instances, sorted by sortField, ascending
	 * 
	 * @param sortField
	 * @return
	 */
	List findAllSorted(String sortField);

	/**
	 * Return all persistent instances, sorted by sortField
	 * 
	 * @param sortField
	 * @param ascending
	 * @return
	 */
	List findAllSorted(String sortField, boolean ascending);
}
