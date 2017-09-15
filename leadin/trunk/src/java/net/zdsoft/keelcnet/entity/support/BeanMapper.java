package net.zdsoft.keelcnet.entity.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import net.zdsoft.keelcnet.exception.InfrastructureException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * BeanMapper is a utility for copying and converting one JavaBean into another.
 * They don't have to be of the same class.
 * </p>
 * <p>
 * Simple type differences of the properties are handled automatically, such as:
 * <ul>
 * <li>primitive types to/from object types</li>
 * <li>strings to/from numeric types</li>
 * </ul>
 * </p>
 * <p>
 * Structural differences, such as different names or different nestling are
 * supported by letting you define the mapping with the method
 * {@link #addPropertyNameMapping addPropertyNameMapping}.
 * </p>
 * <p>
 * Mapping of type differences are supported by letting you plug-in
 * {@link org.beanmapper.PropertyConverter} with the method
 * {@link #addConverter addConverter} or
 * {@link org.beanmapper.PropertyAggregator} with the method
 * {@link #addAggregator addAggregator}.
 * </p>
 * <p>
 * Arrays and collections (and iterators) are supported by letting you plug-in
 * {@link org.beanmapper.ToArrayConverter} and
 * {@link org.beanmapper.ToCollectionConverter}. When the destination property
 * is an array then a {@link org.beanmapper.ToArrayConverter} will be used
 * automatically.
 * </p>
 * <p>
 * The usage is best illustrated by the unit test class.
 * </p>
 * 
 * @author Patrik Nordwall
 */
public class BeanMapper {
    private ConverterMapper converterMapper;
    private AggregatorMapper aggregatorMapper;
    private boolean usingTargetProperties;
    protected Logger log = LoggerFactory.getLogger(getClass());

    public BeanMapper() {
        converterMapper = new ConverterMapper(this);
        aggregatorMapper = new AggregatorMapper(this);
    }

    /**
     * Add a {@link org.beanmapper.PropertyConverter} for the specified property
     * names to the mapper.
     */
    public void addConverter(String sourcePropertyName,
            String destinationPropertyName, PropertyConverter converter) {
        converterMapper.addConverter(sourcePropertyName,
                destinationPropertyName, converter);
    }

    /**
     * Add a {@link org.beanmapper.PropertyConverter} for the specified types to
     * the mapper.
     */
    public void addConverter(Class<?> sourceType, Class<?> destinationType,
            PropertyConverter converter) {
        converterMapper.addConverter(sourceType, destinationType, converter);
    }

    /**
     * Add a simple mapping from one name in the source to another name in the
     * destination.
     */
    public void addPropertyNameMapping(String sourcePropertyName,
            String destinationPropertyName) {
        converterMapper.addPropertyNameMapping(sourcePropertyName,
                destinationPropertyName);
    }

    /**
     * The specified property will be ignored.
     */
    public void ignoreProperty(String sourcePropertyName) {
        converterMapper.ignoreProperty(sourcePropertyName);
    }

    /**
     * Add a {@link org.beanmapper.PropertyAggregator} to the mapper.
     */
    public void addAggregator(String[] sourcePropertyNames,
            String destinationPropertyName, PropertyAggregator aggregator) {
        aggregatorMapper.addAggregator(sourcePropertyNames,
                destinationPropertyName, aggregator);
    }

    /**
     * This method will copy all properties from the source to the destination.
     * It will apply the added converters and aggregators.
     */
    public void copyProperties(Object source, Object destination) {
        if ((source == null) || (destination == null)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("copyProperties from: " + source.getClass().getName()
                    + "@" + Integer.toHexString(source.hashCode()) + "\n  to: "
                    + destination.getClass().getName() + "@"
                    + Integer.toHexString(destination.hashCode()));
        }

        try {
            int type = usingTargetProperties ? ((destination instanceof DynaBean) ? 0
                    : ((destination instanceof Map) ? 1 : (-1)))
                    : ((source instanceof DynaBean) ? 0
                            : ((source instanceof Map) ? 1 : (-1)));

            switch (type) {
            case 0:
                copyDynaBeanProperties(source, destination);

                break;

            case 1:
                copyMapProperties(source, destination);

                break;

            default:
                copyJavaBeanProperties(source, destination);

                break;
            }

            converterMapper.applyConverters(source, destination);
            aggregatorMapper.applyAggregators(source, destination);
        }
        catch (IllegalAccessException e) {
            throw new InfrastructureException("无法访问: " + source + ", error: "
                    + e.getLocalizedMessage(), e);
        }
        catch (InvocationTargetException e) {
            throw new InfrastructureException("获取属性调用出错: " + source
                    + ", error: " + e.getLocalizedMessage(), e);
        }
        catch (NoSuchMethodException e) {
            throw new InfrastructureException("没有该方法: " + source + ", error: "
                    + e.getLocalizedMessage(), e);
        }
    }

    public void copyProperties(Object source, Object destination,
            boolean usingTargetProperties) {
        this.usingTargetProperties = usingTargetProperties;
        this.copyProperties(source, destination);
    }

    private void copyDynaBeanProperties(Object source, Object destination)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        DynaProperty[] sourceDescriptors = ((usingTargetProperties) ? ((DynaBean) destination)
                .getDynaClass().getDynaProperties()
                : ((DynaBean) source).getDynaClass().getDynaProperties());

        for (int i = 0; i < sourceDescriptors.length; i++) {
            String name = sourceDescriptors[i].getName();
            copyProperty(source, destination, name);
        }
    }

    private void copyMapProperties(Object source, Object destination)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        @SuppressWarnings("unchecked")
        Iterator names = (usingTargetProperties) ? ((Map) destination).keySet()
                .iterator() : ((Map) source).keySet().iterator();

        while (names.hasNext()) {
            String name = (String) names.next();
            copyProperty(source, destination, name);
        }
    }

    private void copyJavaBeanProperties(Object source, Object destination)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        PropertyDescriptor[] sourceDescriptors = usingTargetProperties ? PropertyUtils
                .getPropertyDescriptors(destination)
                : PropertyUtils.getPropertyDescriptors(source);

        for (int i = 0; i < sourceDescriptors.length; i++) {
            String name = sourceDescriptors[i].getName();
            copyProperty(source, destination, name);
        }
    }

    private void copyProperty(Object source, Object destination, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        if ("class".equals(name)) {
            return; // No point in trying to set an object's class
        }

        if (converterMapper.hasConverterForDestination(name)) {
            return;
        }

        if (aggregatorMapper.hasAggregatorForDestination(name)) {
            return;
        }

        if (isReadableAndWriteable(source, destination, name)) {
            Object value = PropertyUtils.getProperty(source, name);

            if (value == null) {
                return; // ignore null values
            }

            if (converterMapper.applyTypeConverter(source, destination, name,
                    value)) {
                // type converter was used
                return;
            }

            if (isArrayProperty(source, destination, name)) {
                copyArray(source, destination, name);

                return;
            }

            try {
                BeanUtils.copyProperty(destination, name, value);

                if (log.isDebugEnabled()) {
                    log.debug("Copied: " + name);
                }
            }
            catch (IllegalArgumentException e) {
                logFailedProperty(source, destination, name, value);

                if (isAssignable(source, destination, name)) {
                    BeanUtils.setProperty(destination, name, value);

                    if (log.isDebugEnabled()) {
                        log.debug("Assigned: " + name);
                    }
                }
                else {
                    // different types that might be compatible
                    instantiateAndCopyProperty(source, destination, name);
                }
            }
        }
    }

    private boolean isReadableAndWriteable(Object source, Object destination,
            String name) {
        return PropertyUtils.isReadable(source, name)
                && PropertyUtils.isWriteable(destination, name);
    }

    @SuppressWarnings("unchecked")
    private boolean isAssignable(Object source, Object destination, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return PropertyUtils.getPropertyType(destination, name)
                .isAssignableFrom(PropertyUtils.getPropertyType(source, name));
    }

    private void instantiateAndCopyProperty(Object source, Object destination,
            String name) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        if (isCollectionType(source, name)
                || isCollectionType(destination, name)) {
            // collections are not supported by this method, return and ignore
            return;
        }

        // different types that might be compatible
        Object value = PropertyUtils.getProperty(source, name);
        BeanMapper subMapper = createSubMapperFor(name);
        Object destValue = instantiateProperty(destination, name);
        subMapper.copyProperties(value, destValue);
    }

    BeanMapper createSubMapperFor(String propertyName) {
        BeanMapper subMapper = new BeanMapper();
        subMapper.converterMapper = converterMapper.createSubMapperFor(
                subMapper, propertyName);
        subMapper.aggregatorMapper = aggregatorMapper.createSubMapperFor(
                subMapper, propertyName);

        return subMapper;
    }

    private boolean isArrayProperty(Object source, Object destination,
            String name) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        return isArrayType(destination, name)
                && (isArrayType(source, name) || isCollectionType(source, name));
    }

    private boolean isArrayType(Object o, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        return PropertyUtils.getPropertyType(o, name).isArray();
    }

    private boolean isCollectionType(Object o, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Class<?> type = PropertyUtils.getPropertyType(o, name);

        return (Collection.class.isAssignableFrom(type) || Iterator.class
                .isAssignableFrom(type));
    }

    private void copyArray(Object source, Object destination, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Class<?> arrayType = PropertyUtils.getPropertyType(destination, name);
        Object value = PropertyUtils.getProperty(source, name);

        if (arrayType.equals(PropertyUtils.getPropertyType(source, name))) {
            // exactly same type of arrays
            PropertyUtils.setProperty(destination, name, value);

            if (log.isDebugEnabled()) {
                log.debug("Assigned array: " + name);
            }
        }
        else {
            Class<?> elementType = arrayType.getComponentType();
            BeanMapper elementMapper = createSubMapperFor(name);
            PropertyConverter arrayConverter = new ToArrayConverter(
                    elementType, elementMapper);
            Object destinationValue = arrayConverter.convert(value);
            PropertyUtils.setProperty(destination, name, destinationValue);

            if (log.isDebugEnabled()) {
                log.debug("Copied array: " + name);
            }
        }
    }

    private void logFailedProperty(Object source, Object destination,
            String name, Object value) {
        if (log.isDebugEnabled()) {
            log.debug("Could not copy: " + source.getClass().getName() + "#"
                    + name + " to " + destination.getClass().getName() + "#"
                    + name + " (" + value + ")");
        }
    }

    boolean isNestedPropertiesNull(Object bean, String propertyName)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        if (propertyName.indexOf(".") == -1) { // simple property

            return false;
        }

        StringTokenizer st = new StringTokenizer(propertyName, ".");
        String partName = "";

        while (st.hasMoreTokens()) {
            String s = st.nextToken();

            if (!st.hasMoreTokens()) { // last element, skip

                break;
            }

            partName += s;

            if (!PropertyUtils.isReadable(bean, partName)) {
                return true;
            }

            if (PropertyUtils.getProperty(bean, partName) == null) {
                return true;
            }

            partName += ".";
        }

        return false;
    }

    void instantiateNestedProperties(Object bean, String propertyName)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        if (propertyName.indexOf(".") == -1) { // simple property

            return;
        }

        StringTokenizer st = new StringTokenizer(propertyName, ".");
        String partName = "";

        while (st.hasMoreTokens()) {
            String s = st.nextToken();

            if (!st.hasMoreTokens()) { // last element, skip

                break;
            }

            partName += s;
            instantiateProperty(bean, partName);
            partName += ".";
        }
    }

    private Object instantiateProperty(Object bean, String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        try {
            Object value = PropertyUtils.getProperty(bean, name);

            if (value == null) {
                Class<?> type = PropertyUtils.getPropertyType(bean, name);
                value = type.newInstance();
                PropertyUtils.setProperty(bean, name, value);

                if (log.isDebugEnabled()) {
                    log.debug("Instantiated: " + name);
                }
            }

            return value;
        }
        catch (InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
