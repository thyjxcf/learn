package net.zdsoft.keelcnet.dao;

import java.io.Serializable;
import java.util.Collection;

import net.zdsoft.keelcnet.entity.EntityObject;

/**
 * Root DAO interface, just CRUD(Create, Read, Update, Delete) functions.
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: ObjectDao.java,v 1.2 2006/12/08 06:18:58 liangxiao Exp $
 */
@SuppressWarnings("unchecked")
public interface ObjectDao extends FinderDao {
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
    void remove(EntityObject objectToRemove);

    /**
     * 批量删除实体
     * 
     * @param entities
     */
    int removeAll(Serializable[] entityIds);

    /**
     * 批量删除实体, by id
     * 
     * @param entities
     */
    int removeAll(Collection ids);

    /**
     * 批量删除实体
     * 
     * @param entities
     */
    void removeAll(EntityObject[] entities);

    /**
     * 重新从数据库读取entity
     * 
     * @param objectToRefresh
     */
    void refresh(EntityObject objectToRefresh);

    /**
     * 复制entity
     * 
     * @param objectToReplicate
     */
    void replicate(Object objectToReplicate);

    /**
     * Insert or update entity 具体是insert还是update通过id unsaved-value 来判断
     * 同时更新entity的修改时间
     * 
     * @param objectToSave
     */
    void save(EntityObject objectToSave);

    /**
     * Insert or update entity 具体是insert还是update通过id unsaved-value 来判断 无时间属性
     * 
     * @param objectToSave
     */
    void saveRaw(EntityObject objectToSave);

    /**
     * 批量保存实体
     * 
     * @param entities
     */
    void saveAll(EntityObject[] entities);

    /**
     * 子类指定相应的Entity class
     * 
     * @return
     */
    Class getPersistentClass();
}
