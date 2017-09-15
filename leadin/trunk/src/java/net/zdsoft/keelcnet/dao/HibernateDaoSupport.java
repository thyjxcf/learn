package net.zdsoft.keelcnet.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import net.zdsoft.keelcnet.util.CollectionCloneConverter;

/*
 * Class with support for common Hibernate operations. Hibernate DAOs should
 * extend this class.
 *
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: HibernateDaoSupport.java,v 1.1 2006/12/07 10:01:03 liangxiao Exp $
 * @since
 */
@SuppressWarnings("unchecked")
public class HibernateDaoSupport extends
        org.springframework.orm.hibernate3.support.HibernateDaoSupport {
    static {
        ConvertUtils.register(new CollectionCloneConverter(), Collection.class);
    }

    /**
     * Return a clone of the persistent instance with the given identifier,
     * assuming that the instance exists. 得到一个持久对象的复制品，假定持久对象的已经存在一个实例
     * 
     * @param theClass
     *            class of the persistent object
     * @param id
     *            a valid identifier of an existing persistent instance of the
     *            class
     * @return the cloned object
     */
    protected Object loadAndClone(Class theClass, Serializable id) {
        try {
            return BeanUtils.cloneBean(getHibernateTemplate()
                    .load(theClass, id));
        }
        catch (InstantiationException e) {
            throw new ObjectRetrievalFailureException(theClass, id, e
                    .getMessage(), e);
        }
        catch (IllegalAccessException e) {
            throw new ObjectRetrievalFailureException(theClass, id, e
                    .getMessage(), e);
        }
        catch (InvocationTargetException e) {
            throw new ObjectRetrievalFailureException(theClass, id, e
                    .getMessage(), e);
        }
        catch (NoSuchMethodException e) {
            throw new ObjectRetrievalFailureException(theClass, id, e
                    .getMessage(), e);
        }
    }

    /**
     * Find objects by property value
     * 
     * @param c
     *            class of the hibernated object
     * @param propertyName
     *            name of the property
     * @param value
     *            value to find
     * @param firstElement
     *            the first result, numbered from 0
     * @param maxElements
     *            the maximum number of results
     * @return
     */
    protected List findByProperty(final Class c, final String propertyName,
            final Object value, final int firstElement, final int maxElements) {
        return getHibernateTemplate().executeFind(
                getFindCallback(c, propertyName, value, firstElement,
                        maxElements));
    }

    /**
     * Find objects by property value where the property contains the value
     * 
     * @param c
     *            class of the hibernated object
     * @param propertyName
     *            name of the property
     * @param value
     *            value to find
     * @param firstElement
     *            the first result, numbered from 0
     * @param maxElements
     *            the maximum number of results
     * @return
     */
    protected List findByPropertyContent(final Class c,
            final String propertyName, final String value,
            final int firstElement, final int maxElements) {
        return getHibernateTemplate().executeFind(
                getContentFindCallback(c, propertyName, value, firstElement,
                        maxElements));
    }

    /**
     * Get a new HibernateCallback for finding objects by the value of a
     * property, paginating the results
     * 
     * @param c
     *            class of the hibernated object
     * @param propertyName
     *            name of the property
     * @param value
     *            value of the property
     * @param firstElement
     *            the first result, numbered from 0
     * @param maxElements
     *            the maximum number of results
     * @return
     */
    private static HibernateCallback getFindCallback(final Class c,
            final String propertyName, final Object value,
            final int firstElement, final int maxElements) {
        return new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                return session.createCriteria(c).add(
                        Restrictions.like(propertyName, value)).setFirstResult(
                        firstElement).setMaxResults(maxElements).list();
            }
        };
    }

    /**
     * Get a new HibernateCallback for finding objects by the content of a
     * property, paginating the results
     * 
     * @param c
     *            class of the hibernated object
     * @param propertyName
     *            name of the property
     * @param value
     *            property must contain this value
     * @param firstElement
     *            the first result, numbered from 0
     * @param maxElements
     *            the maximum number of results
     * @return
     */
    private static HibernateCallback getContentFindCallback(final Class c,
            final String propertyName, final String value,
            final int firstElement, final int maxElements) {
        return new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                return session.createCriteria(c).add(
                        Restrictions.ilike(propertyName, value,
                                MatchMode.ANYWHERE)).setFirstResult(
                        firstElement).setMaxResults(maxElements).list();
            }
        };
    }

    /**
     * 说明：
     */
    /**
     * Apply a filter to a persistent collection. A filter is a Hibernate query
     * that may refer to this, the collection element. Filters allow efficient
     * access to very large lazy collections. (Executing the filter does not
     * initialize the collection.)
     * 
     * @param collection
     *            a persistent collection to filter
     * @param filter
     *            a filter query string
     * @return Collection the resulting collection
     * 
     * @see Session.filter()
     */
    protected Collection filter(Collection collection, String filter) {
        return (Collection) getHibernateTemplate().execute(
                getFilterCallback(collection, filter));
    }

    /**
     * <p>
     * Get a new HibernateCallback for filtering collections.
     * </p>
     * <p>
     * Apply a filter to a persistent collection. A filter is a Hibernate query
     * that may refer to this, the collection element. Filters allow efficient
     * access to very large lazy collections. (Executing the filter does not
     * initialize the collection.)
     * </p>
     * 
     * @param collection
     *            a persistent collection to filter
     * @param filter
     *            a filter query string
     * @return Collection the resulting collection
     */
    private static HibernateCallback getFilterCallback(
            final Collection collection, final String filter) {
        return new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {

                // 将Hibernate2.0升到3.0时，Session的Filter（）方法不再支持，考虑用createFilter()方法实现
                // return session.filter(collection, filter);
                return session.createFilter(collection, filter).list();
            }
        };
    }
}
