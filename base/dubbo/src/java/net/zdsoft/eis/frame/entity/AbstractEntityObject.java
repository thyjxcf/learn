/* 
 * @(#)AbstractEntityObject.java    Created on 2006-8-10
 * Copyright (c) 2005 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.frame.entity;

import java.util.Date;


public abstract class AbstractEntityObject extends HibernateEntity {

   
	protected long updatestamp;	//更新戳
	protected boolean isdeleted;	//软删除
	protected Date creationTime; //创建时间戳
    
	protected Date modifyTime;
    
    public AbstractEntityObject() {
    }
 

    /**
     * @return the isdeleted
     */
    public boolean getIsdeleted() {
        return this.isdeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    /**
     * @return the updatestamp
     */
    public long getUpdatestamp() {
        return updatestamp;
    }

    /**
     * @param updatestamp the updatestamp to set
     */
    public void setUpdatestamp(long updatestamp) {
        this.updatestamp = updatestamp;
    }


	public Date getCreationTime() {
		return creationTime;
	}


	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
    
}
