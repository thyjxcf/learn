package net.zdsoft.keelcnet.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.zdsoft.keelcnet.entity.EntityObject;
import net.zdsoft.keelcnet.util.PaginatedList;

/*
 * Entity search dao
 *
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: FinderDao.java,v 1.2 2006/12/08 06:18:58 liangxiao Exp $
 * @since
 */
@SuppressWarnings("unchecked")
public interface FinderDao {
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
    EntityObject findById(Serializable id);

    /**
     * find an object, and return a cloned object
     * 
     * @param id
     * @return
     */
    EntityObject cloneById(Serializable id);

    /**
     * Find an object, not the associated ones..
     * 
     * @param id
     *            identifier
     * @return the value with that id and all properties initialized
     * @throws org.springframework.dao.DataRetrievalFailureException
     *             if an object with that id doesn't exist
     */
    EntityObject findWithoutDetails(Serializable id);

    /**
     * Find an object, and all related entities by its identifier...
     * 
     * @param id
     *            identifier
     * @return the value with that id and all properties initialized
     * @throws org.springframework.dao.DataRetrievalFailureException
     *             if an object with that id doesn't exist
     */
    EntityObject findWithDetails(Serializable id);

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
    PaginatedList find(EntityObject entity, int firstElement, int maxElements);

    /**
     * Return all persistent instances
     * 
     * @return List of objects
     */
    List findAll();

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
