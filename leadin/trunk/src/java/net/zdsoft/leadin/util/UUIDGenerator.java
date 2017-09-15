package net.zdsoft.leadin.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import net.zdsoft.keel.util.UUIDUtils;

/**
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: UUIDGenerator.java, v 1.0 2007-7-2 下午01:44:24 zhaosf Exp $
 */
public class UUIDGenerator implements IdentifierGenerator {

	public Serializable generate(SessionImplementor session, Object obj) 
	throws HibernateException {
		
		return getUUID();
	}
	
	public static String getUUID() {
		return UUIDUtils.newId().toUpperCase();
	}

	public static void main(String[] arg){
        System.out.println(getUUID());

	}
}


