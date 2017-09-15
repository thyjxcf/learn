package net.zdsoft.keelcnet.entity;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.zdsoft.keelcnet.entity.support.CollectionIgnoringEqualsBuilder;
import net.zdsoft.keelcnet.entity.support.CollectionIgnoringHashCodeBuilder;
import net.zdsoft.keelcnet.entity.support.CollectionIgnoringReflectionToStringBuilder;
import net.zdsoft.keelcnet.util.CollectionUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ORMapping基类，凡是用到hibernate映射的，都需要继承自该类
 * POJO parent class
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: EntityObject.java,v 1.2 2006/12/11 09:17:50 liangxiao Exp $
 */
public abstract class EntityObject extends BaseObject {
    private Long id = null;
    private String guid = null;
    private String creatorName;
    private Date creationDate;
    private String modifierName;
    private Date modificationDate;
    private boolean isDeleted;

    public EntityObject() {
    }

    /**
     * @return Returns the guid.
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid The guid to set.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the isDeleted.
     */
    public boolean getDeleted() {
        return isDeleted;
    }

    /**
     * 标示当前实体是否软删除
     * 根据数据库设计原则，如果实体具有历史性，则应添加此特性
     *
     * @param isDeleted The isDeleted to set.
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     *
     * @return
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     *
     * @param creator Name
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * 根据数据库设计原则，如果实体具有时效性，则添加此属性
     *
     * @return      java.util.Date
     *
     * @hibernate.property
     *          column = "creationdate"
     *          type = "net.zdsoft.cnet3.framework.persistence.hibernate.type.DateType"
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * 根据数据库设计原则，如果实体具有时效性，则添加此属性
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return
     */
    public String getModifierName() {
        return modifierName;
    }

    /**
     *
     * @param last Modifier Name
     */
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    /**
     * 根据数据库设计原则，如果实体具有时效性，则添加此属性
     *
     * @return      java.util.Date
     *
     * @hibernate.property
     *          column = "modificationdate"
     *          type = "net.zdsoft.cnet3.framework.persistence.hibernate.type.DateType"
     */
    public Date getModificationDate() {
        return (modificationDate == null) ? creationDate : modificationDate;
    }

    /**
     *
     * @param last Modification Date
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public int hashCode() {
        return CollectionIgnoringHashCodeBuilder.reflectionHashCodeA(17, 37,
            this, false, getClass());
    }

    public boolean equals(Object obj) {
        return CollectionIgnoringEqualsBuilder.reflectionEquals(this, obj);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new CollectionIgnoringReflectionToStringBuilder(this,
            ToStringStyle.MULTI_LINE_STYLE).toString();
    }

    /**
     * This implementation calls super.clone and then clones collections without
     * cloning its elements. Returns <code>null</code> on any exception.
     *
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            Object obj = super.clone();
            PropertyDescriptor[] origDescriptor = PropertyUtils.getPropertyDescriptors(this);

            for (int i = 0; i < origDescriptor.length; i++) {
                Class clazz = origDescriptor[i].getPropertyType();

                if (CollectionUtils.isCollection(clazz)) {
                    /* if it's a Collection */
                    String name = origDescriptor[i].getName();
                    Collection old = (Collection) PropertyUtils.getSimpleProperty(this,
                            name);
                    PropertyUtils.setSimpleProperty(obj, name,
                        CollectionUtils.clone(old));
                } else if (CollectionUtils.isMap(clazz)) {
                    /* if it's a Collection */
                    String name = origDescriptor[i].getName();
                    Map old = (Map) PropertyUtils.getSimpleProperty(this, name);
                    PropertyUtils.setSimpleProperty(obj, name,
                        CollectionUtils.clone(old));
                }
            }

            return obj;
        } catch (Exception e) {
            log.error("Exception cloning bean: " + this + "\n" +
                "Exception was " + e + ": " + e.getLocalizedMessage());

            return null;
        }
    }
}
