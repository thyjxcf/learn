package net.zdsoft.keelcnet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * Some utility methods to use Collections
 *
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: CollectionUtils.java,v 1.1 2006/12/07 10:01:03 liangxiao Exp $
 * @since
 */
public class CollectionUtils {    
    public static boolean isCollection(Class<?> theClass) {
        return Collection.class.isAssignableFrom(theClass);
    }

    public static boolean isMap(Class<?> theClass) {
        return Map.class.isAssignableFrom(theClass);
    }

    /**
     * Clone a Collection copying all its elements to a new one. If map is
     * <code>null</code> return <code>null</code>.
     *
     * @param collection
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Collection clone(Collection collection) {
        if (collection == null) {
            return null;
        }

        Class clazz = collection.getClass();
        Collection clone = null;

        if (List.class.isAssignableFrom(clazz)) {
            clone = new ArrayList(collection);
        } else if (SortedSet.class.isAssignableFrom(clazz)) {
            clone = new TreeSet(collection);
        } else if (Set.class.isAssignableFrom(clazz)) {
            clone = new HashSet(collection);
        } else {
            throw new IllegalArgumentException("Unknown collection class: " +
                clazz);
        }

        return clone;
    }

    /**
     * Clone a Map copying all its elements to a new one. If map is
     * <code>null</code> return <code>null</code>.
     *
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map clone(Map map) {
        if (map == null) {
            return null;
        }

        Class clazz = map.getClass();
        Map clone = null;

        if (SortedMap.class.isAssignableFrom(clazz)) {
            clone = new TreeMap(map);
        } else if (Map.class.isAssignableFrom(clazz)) {
            clone = new HashMap(map);
        } else {
            throw new IllegalArgumentException("Unknown map class: " + clazz);
        }

        return clone;
    }
}
