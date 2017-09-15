package net.zdsoft.keelcnet.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.cache.CacheProvider;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.type.CollectionType;
import org.hibernate.type.DateType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.CollectionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import net.zdsoft.keelcnet.entity.EntityObject;
import net.zdsoft.keelcnet.util.PaginatedList;

/**
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: HibernateObjectDao.java,v 1.4 2007/01/15 03:49:04 liangxiao Exp $
 */
@SuppressWarnings("unchecked")
public abstract class HibernateObjectDao extends HibernateDaoSupport implements
        ObjectDao {
    private static final int INITIALIZE_LAZY = 1;
    private static final int NULLIFY_LAZY = 2;
    private static final int FLAG_DELETE = 0;
    private static final int FLAG_UPDATE = 1;
    protected CacheProvider cacheProvider;
    // protected PlatformTransactionManager transactionManager;
    private JdbcTemplate jdbcTemplate;

    // public PlatformTransactionManager getTransactionManager() {
    // if (transactionManager == null) {
    // transactionManager = (PlatformTransactionManager)
    // ContainerManager.getComponent(BeanID.transactionManager);
    // }
    //
    // return transactionManager;
    // }

    // public void setTransactionManager(
    // PlatformTransactionManager transactionManager) {
    // this.transactionManager = transactionManager;
    // }

    // protected HibernateTemplate createHibernateTemplate(
    // SessionFactory sessionFactory) {
    // return new CNetHibernateTemplate(sessionFactory);
    // }

    public JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(SessionFactoryUtils
                    .getDataSource(getSessionFactory()), true);
        }
        return jdbcTemplate;
    }

    /**
     * @return Returns the jdbcTemplate.
     */
    // public final JdbcTemplate getJdbcTemplate() {
    // if (jdbcTemplate == null) {
    // jdbcTemplate = new JdbcTemplate(SessionFactoryUtils
    // .getDataSource(getSessionFactory()));
    //
    // try {
    // NativeJdbcExtractor nativeJdbcExtractor = (NativeJdbcExtractor)
    // ContainerManager
    // .getComponent(BeanID.automaticJdbcExtractor);
    // jdbcTemplate.setNativeJdbcExtractor(nativeJdbcExtractor);
    // }
    // catch (RuntimeException e) {
    // // ignore if not exist
    // }
    // }
    //
    // return jdbcTemplate;
    // }
    /**
     * cache object if needed
     * 
     * @param cacheProvider
     */
    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    /**
     * Create cache, use it if CacheProvider is inited by spring
     * 
     * @param regionName
     * @param prop
     * @return
     * @throws InfrastructureException
     */
    // protected Cache createCache(String regionName, Properties prop) {
    // if (cacheProvider == null) {
    // CacheManager cacheManager = (CacheManager) ContainerManager
    // .getComponent(BeanID.cacheManager);
    //
    // if (cacheManager != null) {
    // cacheProvider = cacheManager.getCacheProvider();
    // }
    //
    // if (cacheProvider == null) {
    // throw new InfrastructureException("CacheProvider not setted.");
    // }
    // }
    //
    // try {
    // Cache cache = cacheProvider.buildCache(regionName, prop);
    //
    // return cache;
    // }
    // catch (CacheException e) {
    // logger.warn("Error occured while create cache[" + regionName + "]",
    // e);
    //
    // return null;
    // }
    // }
    /***************************************************************************
     * load code part
     **************************************************************************/
    /**
     * Return the persistent instance with the given identifier, assuming that
     * the instance exists.
     * 
     * @param id
     *            a valid identifier of an existing persistent instance of the
     *            class
     * @return
     */
    protected EntityObject load(Serializable id) {
        return (EntityObject) getHibernateTemplate().load(
                this.getPersistentClass(), id);
    }

    /**
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#findById(java.io.Serializable)
     */
    public EntityObject findById(Serializable id) {
        try {
            return (EntityObject) getHibernateTemplate().get(
                    this.getPersistentClass(), id);
        }
        catch (DataAccessException e) {
            logger.warn("Entity not found: " + e.getLocalizedMessage(), e);

            return null;
        }
    }

    /**
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#findById(java.io.Serializable)
     */
    public EntityObject cloneById(Serializable id) {
        Class theClass = this.getPersistentClass();
        EntityObject entity = findWithDetails(id);

        try {
            return (EntityObject) BeanUtils.cloneBean(entity);
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
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#findByIds(java.util.Collection)
     */
    public List findByIds(final Collection ids) {
        final Class entity = this.getPersistentClass();

        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                // Criteria criteria =
                // getHibernateTemplate().createCriteria(session,
                // entity);
                // Spring2.0中已不再支持上面的方法，所以改为下面的方式创建Criteria
                Criteria criteria = session.createCriteria(entity);

                ClassMetadata classMetadata = getSessionFactory()
                        .getClassMetadata(entity);
                String idPropertyName = classMetadata
                        .getIdentifierPropertyName();
                criteria.add(Restrictions.in(idPropertyName, ids));

                return criteria.list();
            }
        });
    }

    /**
     * 返回一个外于PO状态的POJO对象，INITIALIZE_LAZY表示此对象成员属性表示的所有相关信息也一起导入，处于PO状态
     * 如：Company中有Set类型的Person对象时，当载入一个Company对象时，与他相关的Person对象也全部取出 即：非延迟抓取
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#findWithDetails(java.io.Serializable)
     */
    public EntityObject findWithDetails(Serializable id) {
        EntityObject entity = load(id);

        return (EntityObject) getHibernateTemplate().execute(
                getProcessCollectionsCallback(entity, INITIALIZE_LAZY));
    }

    /**
     * 返回一个外于PO状态的POJO对象，NULLIFY_LAZY表示此对象成员属性表示的所有相关信息暂时不会一起导入，暂不处于PO状态，只载入该对象
     * 只有当程序中明确表示访问或调用这些相关信息时，Hibernate自动装其载入，使其处于PO状态
     * 如：Company中有Set类型的Person对象时，当载入一个Company对象时，与他相关的Person对象暂时不会载入，只有当使用他们时Hiberante自动载入
     * 即：延迟抓取
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#findWithoutDetails(java.io.Serializable)
     */
    public EntityObject findWithoutDetails(Serializable id) {
        EntityObject entity = load(id);

        return (EntityObject) getHibernateTemplate().execute(
                getProcessCollectionsCallback(entity, NULLIFY_LAZY));
    }

    /**
     * Get POJO by class and id, protected method
     * 
     * @deprecated use findById(Serializable id) instead
     * @param guid
     * @return
     */
    protected EntityObject getByClassGuid(final String guid) {
        return findById(guid);
    }

    /**
     * 
     * @deprecated use findById(Serializable id) instead
     * @param id
     * @return
     */
    protected EntityObject getByClassId(final Long id) {
        return findById(id);
    }

    /**
     * Return the persistent entity count
     */
    public int getCount() {
        try {
            Iterator it = getHibernateTemplate().iterate(
                    "select count(*) from " + getPersistentClass().getName());

            return (it != null) ? ((Integer) it.next()).intValue() : 0;
        }
        catch (Exception e) {
            logger.warn(e.toString());

            return 0;
        }
    }

    /**
     * 返回所有实体, 如果为空，则返回Collections.EMPTY_LIST
     */
    public List findAll() {
        List result = getHibernateTemplate().loadAll(this.getPersistentClass());

        return (result != null) ? result : Collections.EMPTY_LIST;
    }

    public List findAllSorted(String sortField) {
        return findAllSorted(sortField, true);
    }

    /**
     * 返回所有实体, 如果为空，则返回Collections.EMPTY_LIST
     */
    public List findAllSorted(final String sortField, final boolean ascending) {
        if (sortField == null) {
            return findAll();
        }

        List result = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        // Criteria criteria = getHibernateTemplate()
                        // .createCriteria(session,
                        // getPersistentClass());
                        // 改为如下方式创建Criteria
                        Criteria criteria = session
                                .createCriteria(getPersistentClass());

                        criteria.addOrder(ascending ? Order.asc(sortField)
                                : Order.desc(sortField));

                        return criteria.list();
                    }
                });

        return (result != null) ? result : Collections.EMPTY_LIST;
    }

    /**
     * Get a new HibernateCallback to do some operation on lazy initialized
     * collections (initialize or set to null).
     * 
     * @param entity
     *            the persistent object
     * @param operation
     *            the operation to do with collection properties
     *            (INITIALIZE_LAZY or NULLIFY_LAZY)
     * @return a new non persistent object with collections processed
     */
    private HibernateCallback getProcessCollectionsCallback(
            final EntityObject entity, final int operation) {
        return new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                ClassMetadata classMetadata = getSessionFactory()
                        .getClassMetadata(entity.getClass());

                /* get persistent properties(反射出该PO对象的所有属性名称和属性的类型) */
                Type[] propertyTypes = classMetadata.getPropertyTypes();
                String[] propertyNames = classMetadata.getPropertyNames();

                /* destination bean */
                EntityObject dest = findById(entity.getId());

                if (dest == null) {
                    return null;
                }

                /* for each persistent property of the bean */
                for (int i = 0; i < propertyTypes.length; i++) {
                    if (!propertyTypes[i].isCollectionType()) {
                        continue;
                    }

                    CollectionMetadata collectionMetadata = getSessionFactory()
                            .getCollectionMetadata(
                                    ((CollectionType) propertyTypes[i])
                                            .getRole());

                    /* if it's a lazy collection operate on it */
                    if (collectionMetadata.isLazy()) {
                        switch (operation) {
                        // 非延迟抓取
                        case (INITIALIZE_LAZY):

                            Collection c = (Collection) classMetadata
                                    .getPropertyValue(entity, propertyNames[i],
                                            EntityMode.MAP);

                            classMetadata.setPropertyValue(dest,
                                    propertyNames[i], c, EntityMode.MAP);

                            break;

                        // 延迟抓取
                        case (NULLIFY_LAZY):
                            classMetadata.setPropertyValue(dest,
                                    propertyNames[i], null, EntityMode.MAP);

                            break;

                        default:
                            throw new IllegalArgumentException(
                                    "Unknown operation");
                        }
                    }
                }

                return dest;
            }
        };
    }

    /**
     * 分页返回记录
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.FinderDao#find(EntityObject,
     *      int, int)
     */
    public PaginatedList find(EntityObject entity, int firstElement,
            int maxElements) {
        return (PaginatedList) getHibernateTemplate().execute(
                getFindByValueCallback(entity, firstElement, maxElements));
    }

    /**
     * Get a new HibernateCallback for finding objects by a bean property
     * values, paginating the results. Properties with null values and
     * collections are ignored. If the property is mapped as String find a
     * partial match, otherwise find by exact match.
     * 
     * @todo Use Criteria.count() when available in next Hibernate versions
     * 
     * @param entity
     *            bean with the values of the parameters
     * @param firstElement
     *            the first result, numbered from 0
     * @param count
     *            the maximum number of results
     * @return
     */
    private HibernateCallback getFindByValueCallback(final Object entity,
            final int firstElement, final int count) {
        return new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                // Criteria criteria =
                // getHibernateTemplate().createCriteria(session,
                // entity.getClass());
                Criteria criteria = session.createCriteria(entity.getClass());

                ClassMetadata classMetadata = getSessionFactory()
                        .getClassMetadata(entity.getClass());

                if (classMetadata == null) {
                    throw new MappingException("No persister for: "
                            + entity.getClass().getName());
                }

                /* get persistent properties */
                Type[] propertyTypes = classMetadata.getPropertyTypes();
                String[] propertyNames = classMetadata.getPropertyNames();

                /* for each persistent property of the bean */
                for (int i = 0; i < propertyNames.length; i++) {
                    String name = propertyNames[i];
                    Object value = classMetadata.getPropertyValue(entity, name,
                            EntityMode.POJO);

                    if (value == null) {
                        continue;
                    }

                    /* ignore collections */
                    if (propertyTypes[i].isCollectionType()) {
                        continue;
                    }

                    Type type = classMetadata.getPropertyType(name);

                    /* if the property is mapped as String find partial match */
                    if (type.equals(StandardBasicTypes.STRING)) {
                        criteria.add(Restrictions.ilike(name, value.toString(),
                                MatchMode.ANYWHERE));
                    }
                    /*
                     * if the property is mapped as DateType, convert it to long
                     * type
                     */
                    else if (type.equals(Hibernate.custom(DateType.class))) {
                        Date date = (Date) value;
                        criteria.add(Restrictions.eq(name, new Long(date
                                .getTime())));
                    }
                    /* else find exact match */
                    else {
                        criteria.add(Restrictions.eq(name, value));
                    }
                }

                /*
                 * TODO Use Criteria.count() when available in next Hibernate
                 * versions
                 */
                int size = criteria.list().size();

                List list = criteria.setFirstResult(firstElement)
                        .setMaxResults(count).list();

                return new PaginatedList(list, firstElement, count, size);
            }
        };
    }

    /**
     * 通过主键判断数据是否存在
     */
    public boolean isExisted(final Serializable id) {
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
        }
        catch (HibernateException e) {
            // ignore
            logger.warn(e.toString());

            return false;
        }
    }

    /***************************************************************************
     * update/delete code part
     **************************************************************************/

    /*
     * 删除指定id的实体 (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.ObjectDao#remove(java.lang.Long)
     */
    public void remove(Serializable id) {
        this.removeAll(new Serializable[] { id });
    }

    /**
     * 批量删除实体, by id
     * 
     * @param entities
     */
    public int removeAll(Collection ids) {
        if ((ids == null) || ids.isEmpty()) {
            return 0;
        }

        Serializable[] idArray = new Serializable[ids.size()];

        return this.removeAll((Serializable[]) ids.toArray(idArray));
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.ObjectDao#removeAll(java.lang.Long[])
     */
    public int removeAll(final Serializable[] entityIds) {
        if ((entityIds == null) || (entityIds.length == 0)) {
            logger.warn("Do not delete entity with empty paramter!");

            return 0;
        }

        final Class entityClass = getPersistentClass();

        try {
            ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
                    entityClass);
            String idPropertyName = classMetadata.getIdentifierPropertyName();
            List entities = new ArrayList(entityIds.length);

            for (int i = 0; i < entityIds.length; i++) {
                try {
                    EntityObject entity = (EntityObject) org.springframework.beans.BeanUtils
                            .instantiateClass(entityClass);
                    PropertyUtils.setProperty(entity, idPropertyName,
                            entityIds[i]);
                    entities.add(entity);
                }
                catch (NoSuchMethodException e) {
                    logger.error("No method '" + idPropertyName + "' in '"
                            + entityClass.getName() + "'", e);
                }
                catch (InvocationTargetException e) {
                    logger.error("Method invocation error: "
                            + e.getLocalizedMessage(), e);
                }
                catch (IllegalAccessException e) {
                    logger.error("Access " + entityClass.getName()
                            + "'s method '" + idPropertyName + "' error: "
                            + e.getLocalizedMessage(), e);
                }
            }

            if (entities.size() > 0) {
                this.deleteOrUpdate(entities, FLAG_DELETE);
            }
        }
        catch (HibernateException e) {
            logger.error("Class '" + entityClass.getName()
                    + "' is not persisted: " + e.getLocalizedMessage(), e);
        }

        return 0;
    }

    /*
     * 批量删除实体 (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.ObjectDao#removeAll(java.util.List)
     */
    public void removeAll(EntityObject[] entities) {
        if ((entities == null) || (entities.length == 0)) {
            logger.warn("Do not delete entity with empty paramter!");

            return;
        }

        this.deleteOrUpdate(Arrays.asList(entities), FLAG_DELETE);
    }

    /*
     * 批量保存实体 (non-Javadoc)
     * 
     * @see net.zdsoft.cnet3.framework.persistence.dao.ObjectDao#saveAll(java.util.List)
     */
    public void saveAll(EntityObject[] entities) {
        if ((entities == null) || (entities.length == 0)) {
            logger.warn("Do not delete entity with empty paramter!");

            return;
        }

        this.deleteOrUpdate(Arrays.asList(entities), FLAG_UPDATE);
    }

    /**
     * Delete or update entity in batch mode.
     * 
     * @param entityList
     * @param operation
     * @return
     */
    private int deleteOrUpdate(final List entityList, final int operation) {
        Integer result = (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        // Get hibernate's batch size, if not set, 0 is default
                        // Properties hibernateProperties = (Properties)
                        // ContainerManager
                        // .getComponent(BeanID.hibernateProperties);
                        // final int batchSize = PropertiesHelper.getInt(
                        // Environment.STATEMENT_BATCH_SIZE,
                        // hibernateProperties, 0);

                        int batchSize = 50;

                        for (int i = 0; i < entityList.size(); i++) {
                            EntityObject entity = (EntityObject) entityList
                                    .get(i);

                            switch (operation) {
                            case FLAG_DELETE:
                                session.delete(entity);

                                break;

                            case FLAG_UPDATE:
                                updateModificationData(entity);
                                session.saveOrUpdate(entity);

                                break;

                            default:
                                throw new IllegalArgumentException(
                                        "Operation status is invalid");
                            }

                            if ((batchSize == 0)
                                    || (((i + 1) % batchSize) == 0)) {
                                session.flush();
                                session.clear();
                            }
                        }

                        return new Integer(entityList.size());
                    }
                });

        return result.intValue();
    }

    /**
     * 
     */
    public void save(EntityObject objectToSave) {
        updateModificationData(objectToSave);
        saveRaw(objectToSave);
    }

    protected void updateModificationData(EntityObject objectToSave)
            throws DataAccessException {
        java.util.Date date = objectToSave.getCurrentDate();
        objectToSave.setModificationDate(date);

        if (objectToSave.getCreationDate() == null) {
            objectToSave.setCreationDate(date);
        }
    }

    /**
     * call this method only if you do not have creationDate and
     * modificationDate properties
     */
    public void saveRaw(final EntityObject objectToSave) {
        getHibernateTemplate().saveOrUpdate(objectToSave);
    }

    /**
     * remove entity, unindex if necessary.
     */
    public void remove(final EntityObject objectToRemove) {
        getHibernateTemplate().delete(objectToRemove);
    }

    /**
     * 更新
     */
    public void refresh(final EntityObject objectToRefresh) {
        this.getHibernateTemplate().refresh(objectToRefresh);
    }

    /**
     * 复制
     */
    public void replicate(final Object objectToReplicate) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                session.replicate(objectToReplicate, ReplicationMode.OVERWRITE);

                return null;
            }
        });
    }

    /**
     * 反射当前PO类的类型 sub class must be implement this method
     */
    public abstract Class getPersistentClass();

    protected List findNamedQuery(String queryName) {
        return findNamedQuery(queryName, false);
    }

    protected List findNamedQuery(String queryName, boolean cacheable) {
        return findNamedQueryStringParam(queryName, null, null, cacheable);
    }

    protected List findNamedQuery(String queryName, boolean cacheable,
            int maxResultCount) {
        return findNamedQueryStringParam(queryName, null, null, cacheable,
                maxResultCount);
    }

    protected List findNamedQueryStringParam(String queryName,
            String paramName, Object paramValue) {
        return findNamedQueryStringParam(queryName, paramName, paramValue,
                false);
    }

    protected List findNamedQueryStringParam(String queryName,
            String paramName, Object paramValue, boolean cacheable) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                null, null, cacheable);
    }

    protected List findNamedQueryStringParam(String queryName,
            String paramName, Object paramValue, boolean cacheable,
            int maxResultCount) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                null, null, cacheable, maxResultCount);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, false);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, boolean cacheable) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, null, null, cacheable, -1);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, boolean cacheable, int maxResultCount) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, null, null, cacheable, maxResultCount);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, String param3Name, Object param3Value) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, param3Name, param3Value,
                ((String) (null)), null);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, String param3Name, Object param3Value,
            boolean cacheable) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, param3Name, param3Value, cacheable, -1);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, String param3Name, Object param3Value,
            boolean cacheable, int maxResultCount) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, param3Name, param3Value, null, null,
                cacheable, maxResultCount);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, String param3Name, Object param3Value,
            String param4Name, Object param4Value) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, param3Name, param3Value, param4Name,
                param4Value, false);
    }

    protected List findNamedQueryStringParams(String queryName,
            String paramName, Object paramValue, String param2Name,
            Object param2Value, String param3Name, Object param3Value,
            String param4Name, Object param4Value, boolean cacheable) {
        return findNamedQueryStringParams(queryName, paramName, paramValue,
                param2Name, param2Value, param3Name, param3Value, param4Name,
                param4Value, cacheable, Integer.MIN_VALUE);
    }

    protected List findNamedQueryStringParams(String queryName, Map paramMap) {
        return this.findNamedQueryStringParams(queryName, paramMap, false,
                Integer.MIN_VALUE);
    }

    protected List findNamedQueryStringParams(final String queryName,
            final Map paramMap, final boolean cacheable,
            final int maxResultCount) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = session.getNamedQuery(queryName);

                if (cacheable) {
                    queryObject.setCacheable(true);
                }

                SessionFactoryUtils.applyTransactionTimeout(queryObject,
                        getSessionFactory());

                for (Iterator iter = paramMap.entrySet().iterator(); iter
                        .hasNext();) {
                    Map.Entry e = (Map.Entry) iter.next();
                    String name = (String) e.getKey();
                    Object value = e.getValue();
                    queryObject.setParameter(name, value);
                }

                if (maxResultCount != Integer.MIN_VALUE) {
                    queryObject.setMaxResults(maxResultCount);
                }

                return queryObject.list();
            }
        });
    }

    protected List findNamedQueryStringParams(final String queryName,
            final String paramName, final Object paramValue,
            final String param2Name, final Object param2Value,
            final String param3Name, final Object param3Value,
            final String param4Name, final Object param4Value,
            final boolean cacheable, final int maxResultCount) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query queryObject = session.getNamedQuery(queryName);

                if (cacheable) {
                    queryObject.setCacheable(true);
                }

                SessionFactoryUtils.applyTransactionTimeout(queryObject,
                        getSessionFactory());

                if (paramName != null) {
                    queryObject.setParameter(paramName, paramValue);
                }

                if (param2Name != null) {
                    queryObject.setParameter(param2Name, param2Value);
                }

                if (param3Name != null) {
                    queryObject.setParameter(param3Name, param3Value);
                }

                if (param4Name != null) {
                    queryObject.setParameter(param4Name, param4Value);
                }

                if (maxResultCount != Integer.MIN_VALUE) {
                    queryObject.setMaxResults(maxResultCount);
                }

                return queryObject.list();
            }
        });
    }

    /**
     * 从得到的list集合中，得到一个实例，若集合有多个时报错 Find single object
     * 
     * @param results
     * @return
     */
    protected Object findSingleObject(List results) {
        if ((results != null) && (results.size() == 1)) {
            return results.get(0);
        }

        if ((results != null) && (results.size() > 1)) {
            logger
                    .error("Uh oh - found more than one object when single object requested: "
                            + results);
        }

        return null;
    }

    /**
     * 查找entity，限制结果集 Find list result by query, limit maxResultCount
     * 
     * @param query
     * @param maxResultCount
     * @return
     */
    protected List findByQuery(final String query, int maxResultCount) {
        return findByQuery(query, null, 0, maxResultCount);
    }

    /**
     * 查询前maxResultCount条数据库记录
     * 
     * @param query
     * @param params
     * @param maxResultCount
     * @return
     */
    protected List findByQuery(final String query, Object[] params,
            int maxResultCount) {
        return findByQuery(query, params, 0, maxResultCount);
    }

    /**
     * 分页查找entity
     * 
     * @param query
     * @param firstResult
     * @param maxResultCount
     * @return
     */
    protected List findByQuery(final String query, int firstResult,
            int maxResultCount) {
        return this.findByQuery(query, null, firstResult, maxResultCount);
    }

    protected List findByQuery(final String sql, final Object[] params,
            final int firstResult, final int maxResultCount) {
        // try {
        // final HibernateTemplate hibernateTemplate = getHibernateTemplate();

        List result = (List) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        // Query query = hibernateTemplate.createQuery(session,
                        // sql);
                        // Spring由1.4升到2.0时，HibernateTemplate中已不再有createQuery（）方法
                        // 需改为下面语句
                        Query query = session.createQuery(sql);

                        if ((params != null) && (params.length > 0)) {
                            for (int i = 0; i < params.length; i++) {
                                query.setParameter(i, params[i]);
                            }
                        }

                        if (maxResultCount > 0) {
                            query.setMaxResults(maxResultCount);
                        }
                        query.setFirstResult(firstResult);

                        List result = query.list();

                        return result;
                    }
                });

        return (result != null) ? result : new ArrayList();
        // } catch (Exception e) {
        // logger.error("查询错误!", e);
        // throw new InfrastructureException(e);
        // }
    }

    public PaginatedList find(final String sql, final Object[] args,
            final int firstResult, final int maxResultCount) {
        // query paged list
        // return (PaginatedList) getJdbcTemplate().query(sql, args,
        // new PagedResultSetExtractor(firstResult, maxResultCount));

        PreparedStatementCreator psCreator = new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                return con.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        // ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            }

        };

        PreparedStatementCallback psCallback = new PreparedStatementCallback() {

            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        StatementCreatorUtils.setParameterValue(ps, i + 1,
                                SqlTypeValue.TYPE_UNKNOWN, null, args[i]);
                    }
                }

                ResultSet rs = null;

                try {
                    rs = ps.executeQuery();
                    return new PagedResultSetExtractor(firstResult,
                            maxResultCount).extractData(rs);
                }
                finally {
                    org.springframework.jdbc.support.JdbcUtils
                            .closeResultSet(rs);
                }
            }
        };

        return (PaginatedList) getJdbcTemplate().execute(psCreator, psCallback);
    }

    protected static class PagedResultSetExtractor implements
            ResultSetExtractor {

        protected static Logger logger = LoggerFactory.getLogger(PagedResultSetExtractor.class);

        private int firstResult;
        private int maxResultCount;

        public PagedResultSetExtractor(int firstResult, int maxResultCount) {
            this.firstResult = firstResult;
            this.maxResultCount = maxResultCount;
        }

        private static Object getJdbcObject(ResultSet rs, int index)
                throws SQLException {
            Object obj = rs.getObject(index);

            if ((obj != null)
                    && obj.getClass().getName().startsWith(
                            "oracle.sql.TIMESTAMP")) {
                obj = rs.getTimestamp(index);
            }

            return obj;
        }
        
        public Object extractData(ResultSet rs) throws SQLException {
            try {
                if (firstResult > 0) {
                    rs.absolute(firstResult);
                }

                ResultSetMetaData rsmd = rs.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();
                List listOfRows = new ArrayList();

                for (int cursor = 0; rs.next() && (cursor < maxResultCount); cursor++) {
                    @SuppressWarnings("deprecation")
                    Map mapOfColValues = CollectionFactory
                            .createLinkedMapIfPossible(numberOfColumns);

                    for (int col = 1; col <= numberOfColumns; col++) {
                        Object o = getJdbcObject(rs, col);
                        mapOfColValues.put(rsmd.getColumnName(col), o);
                    }

                    listOfRows.add(mapOfColValues);
                }

                rs.last();

                int totalRows = rs.getRow();

                return new PaginatedList(listOfRows, firstResult,
                        maxResultCount, totalRows);
            }
            catch (SQLException e) {
                logger.error("Query paged data error: "
                        + e.getLocalizedMessage(), e);

                return new PaginatedList(new ArrayList(), firstResult,
                        maxResultCount, 0);
            }
        }
    }
}
