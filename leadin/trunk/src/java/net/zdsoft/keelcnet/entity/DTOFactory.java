package net.zdsoft.keelcnet.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/*
 * DTO和Entity Object组装类，
 * 一般是singleton的，dto和entity转换的一般性原则：
 * 如果是属性少于8个的，采用hardcode，即手写dto和entity之间数据转换的get/set方法，
 * 如果属性超过8个，使用beanutils进行反射。
 *
 * <p>城域综合信息平台</p>
 * <p>CNet3.0</p>
 * <p>Copyright (c) 2003</p>
 * <p>Company: ZDSoft</p>
 * @author taoy
 * @since 1.0
 * @version $Id: DTOFactory.java,v 1.1 2006/12/11 10:11:50 liangxiao Exp $
 */
public interface DTOFactory {
    /**
     * 从DTO复制数据到实体，
     * 
     * @param dto
     * @param entity
     * @param isUpdate
     */
    public abstract void copyFromDTO(Serializable dto, EntityObject entity,
            boolean isUpdate);

    /**
     * 复制生成DTO对象
     * 
     * @param entity
     * @param dto
     */
    public abstract void copyToDTO(EntityObject entity, Serializable dto);

    /**
     * 多个Entity复制生成DTO对象
     * 
     * @param entity
     * @param dto
     */
    public abstract void copyToDTO(EntityObject[] entitys, Serializable dto);

    /**
     * DTO list
     * 
     * @param entityList
     * @param dtoClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public abstract List createDTOs(Collection entityList, Class dtoClass);
}
