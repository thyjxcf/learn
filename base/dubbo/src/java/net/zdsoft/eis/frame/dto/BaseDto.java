package net.zdsoft.eis.frame.dto;

import java.io.Serializable;


/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: BaseDto.java,v 1.3 2007/01/09 10:03:05 jiangl Exp $
 */
public abstract class BaseDto implements Serializable {
	protected String id;		//数据模型的主健字段

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}



