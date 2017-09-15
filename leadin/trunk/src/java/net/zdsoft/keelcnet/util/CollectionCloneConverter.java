package net.zdsoft.keelcnet.util;

import org.apache.commons.beanutils.Converter;

import java.util.Collection;


/**
 * Given a Collection copies all its elements to a new Collection
 *
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: CollectionCloneConverter.java,v 1.1 2006/12/07 10:01:03 liangxiao Exp $
 * @since
 */
public class CollectionCloneConverter implements Converter {
    /**
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
     *      java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public Object convert(Class type, Object value) {
        return CollectionUtils.clone((Collection) value);
    }
}
