package net.zdsoft.keelcnet.entity.support;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * This converter creates a destination array and
 * populates it with mapped elements from the source,
 * which can be a Collection, Iterator or array.
 * </p>
 * @author Patrik Nordwall
 */
public class ToArrayConverter implements PropertyConverter {
    private static Logger LOG = LoggerFactory.getLogger(ToArrayConverter.class);
    private BeanMapper elementMapper;
    private Class<?> elementClass;

    /**
     * @param aElementClass it will be an array with elements of this type
     * @param aElementMapper this mapper will be used to map each element,
     *      null is allowed if no mapping is to be done
     */
    public ToArrayConverter(Class<?> aElementClass, BeanMapper aElementMapper) {
        elementClass = aElementClass;
        elementMapper = aElementMapper;
    }

    /**
     * <p>
     * Supported sourceValue types are Collection, Iterator and arrays.
     * </p>
     * @return array of the specified element class
     */
    @SuppressWarnings("unchecked")
    public Object convert(Object sourceValue) {
        if (sourceValue == null) {
            return null;
        }

        if (sourceValue instanceof Collection) {
            Collection c = (Collection) sourceValue;

            return convertCollection(c);
        } else if (sourceValue instanceof Iterator) {
            Iterator iter = (Iterator) sourceValue;
            Collection c = new ArrayList();

            while (iter.hasNext()) {
                c.add(iter.next());
            }

            return convertCollection(c);
        } else if (sourceValue.getClass().isArray()) {
            Collection c = Arrays.asList((Object[]) sourceValue);

            return convertCollection(c);
        } else {
            throw new IllegalArgumentException(
                "Unsupported input type to ToListConverter");
        }
    }

    @SuppressWarnings("unchecked")
    protected Object convertCollection(Collection c) {
        Object result = Array.newInstance(elementClass, c.size());
        Iterator iter = c.iterator();

        for (int i = 0; iter.hasNext(); i++) {
            Object sourceElement = iter.next();

            if (sourceElement == null) {
                continue;
            }

            Object destinationElement;

            if (elementClass.isAssignableFrom(sourceElement.getClass())) {
                destinationElement = sourceElement;
            } else {
                destinationElement = createElementInstance();

                if (elementMapper != null) {
                    elementMapper.copyProperties(sourceElement,
                        destinationElement);
                }
            }

            Array.set(result, i, destinationElement);
        }

        return result;
    }

    protected Object createElementInstance() {
        try {
            return elementClass.newInstance();
        } catch (InstantiationException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
