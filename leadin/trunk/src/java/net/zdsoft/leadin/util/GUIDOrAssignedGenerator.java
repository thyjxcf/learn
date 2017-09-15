package net.zdsoft.leadin.util;

import java.io.Serializable;
import java.util.UUID;

import net.zdsoft.keel.util.UUIDUtils;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Assigned;

/**
 * 结合guid 和 assigned 方式
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: GUIDOrAssignedGenerator.java,v 1.1 2006/12/11 03:30:29 zhaosf Exp $
 */
public class GUIDOrAssignedGenerator extends Assigned {
	
	private String entityName;

	public Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
		
		final Serializable id = session.getEntityPersister( entityName, obj ) 
		.getIdentifier( obj, session);
		
		//如果为空，用GUIDGenerator自动生成
		if (id==null) {
		    return UUIDUtils.newId().toUpperCase();
//			GUIDGenerator gUIDGenerator = new GUIDGenerator();
//			return gUIDGenerator.generate(session, obj);
		}else{
			return id;
		}		
	}

	
	public static void main(String[] args) {
		net.zdsoft.keel.dao.UUIDGenerator uuid = new net.zdsoft.keel.dao.UUIDGenerator();
        String uuidHex = uuid.generateHex();
        
        System.out.println(uuidHex);
        System.out.println(UUID.randomUUID().toString());
	}
}


