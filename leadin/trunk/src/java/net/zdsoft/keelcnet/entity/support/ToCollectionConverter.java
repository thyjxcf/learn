package net.zdsoft.keelcnet.entity.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This converter creates a destination Collection and populates it with mapped
 * elements from the source, which can be a Collection, Iterator or array.
 * </p>
 * 
 * @author Patrik Nordwall
 */
public class ToCollectionConverter implements PropertyConverter {
    private static Logger LOG = LoggerFactory.getLogger(ToCollectionConverter.class);
    private BeanMapper elementMapper;
    private Class<?> elementClass;
    private Class<?> collectionClass;

    /**
     * @param aCollectionClass
     *            the instantiated collection will be of this type, e.g.
     *            ArrayList.class
     * @param aElementClass
     *            the instantiated elements will be of this type
     * @param aElementMapper
     *            this mapper will be used to map each element, null is allowed
     *            if no mapping is to be done
     */
    public ToCollectionConverter(Class<?> aCollectionClass, Class<?> aElementClass,
            BeanMapper aElementMapper) {
        if (!Collection.class.isAssignableFrom(aCollectionClass)) {
            throw new IllegalArgumentException(
                    "aCollectionClass must be a Collection class");
        }

        collectionClass = aCollectionClass;
        elementClass = aElementClass;
        elementMapper = aElementMapper;
    }

    /**
     * <p>
     * Supported sourceValue types are Collection, Iterator and arrays.
     * </p>
     * 
     * @return collection of the specified collection class, containing elements
     *         of the specified element class
     */
    @SuppressWarnings("unchecked")
    public Object convert(Object sourceValue) {
        if (sourceValue == null) {
            return null;
        }

        if (sourceValue instanceof Collection) {
            Collection c = (Collection) sourceValue;

            return convertCollection(c);
        }
        else if (sourceValue instanceof Iterator) {
            Iterator iter = (Iterator) sourceValue;
            Collection c = new ArrayList();

            while (iter.hasNext()) {
                c.add(iter.next());
            }

            return convertCollection(c);
        }
        else if (sourceValue.getClass().isArray()) {
            Object[] array = (Object[]) sourceValue;
            Collection c = Arrays.asList(array);

            return convertCollection(c);
        }
        else {
            throw new IllegalArgumentException(
                    "Unsupported input type to ToListConverter");
        }
    }

    @SuppressWarnings("unchecked")
    protected Collection convertCollection(Collection c) {
        Collection result = createCollection();
        Iterator iter = c.iterator();

        for (int i = 0; iter.hasNext(); i++) {
            Object sourceElement = iter.next();

            if (sourceElement == null) {
                result.add(null);
            }
            else {
                Object destinationElement;

                if (elementClass.isAssignableFrom(sourceElement.getClass())) {
                    destinationElement = sourceElement;
                }
                else {
                    destinationElement = createElementInstance();

                    if (elementMapper != null) {
                        elementMapper.copyProperties(sourceElement,
                                destinationElement);
                    }
                }

                result.add(destinationElement);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    protected Collection createCollection() {
        try {
            if (collectionClass.equals(List.class)
                    || collectionClass.equals(ArrayList.class)) {
                return new ArrayList();
            }
            else if (collectionClass.equals(Set.class)
                    || collectionClass.equals(HashSet.class)) {
                return new HashSet();
            }
            else {
                return (Collection) collectionClass.newInstance();
            }
        }
        catch (InstantiationException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        catch (IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    protected Object createElementInstance() {
        try {
            return elementClass.newInstance();
        }
        catch (InstantiationException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        catch (IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
