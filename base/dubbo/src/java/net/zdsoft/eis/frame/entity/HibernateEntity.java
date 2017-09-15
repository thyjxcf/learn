package net.zdsoft.eis.frame.entity;

import net.zdsoft.keelcnet.entity.BaseObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class HibernateEntity extends BaseObject {
	
	 /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 869847216907364525L;
    protected String id;//表的UUID主键

	/**
	 * 32位GUID
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 32位GUID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/** 
	 * 重写equals方法
	 * 说明：使用简单的判断主键是否相等确定两个entity是否相等，这样效率高；
	 * 原来是使用reflectionEquals实现的，这样效率低，而且在安全-受限的JVMs中，在使用中的字段修改将会导致这个方法失效
	 * 
	 */
	public boolean equals(Object entity) {
		if (this == entity) {
			return true;
		}
		if (entity instanceof HibernateEntity == false){
			return false;
		}
			
		HibernateEntity tmpEntity = (HibernateEntity) entity;
		return new EqualsBuilder().append(id, tmpEntity.id).isEquals();
	}

	
	/** 
	 * 重写hashCode方法
	 * 说明：只简单的对entity的主键进行hashCode转换即可
	 * 若使用reflectionHashCode将entity的全部属性进行hashCode转换时，
	 * 在双向关联的情况下存取关联对象时会出现StackOverflowError错误提示
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
					.append(id)
					.toHashCode();
	}
	 
}
