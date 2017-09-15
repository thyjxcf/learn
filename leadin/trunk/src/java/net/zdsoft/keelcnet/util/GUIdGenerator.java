package net.zdsoft.keelcnet.util;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Generate UUID, 128 bits It may seems like:
 * 6ba7b810-9dad-11d1-80b4-00c04fd430c8
 * 
 * referenced by *.hbm.xml
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: GUIdGenerator.java,v 1.3 2006/12/20 11:09:15 liangxiao Exp $
 */
public class GUIdGenerator implements IdentifierGenerator {

    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        return getUUID();
    }

    /**
     * Use this method to generate UUID, should use a id-pool later.
     * 
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
