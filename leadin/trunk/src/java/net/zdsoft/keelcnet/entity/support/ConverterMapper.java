package net.zdsoft.keelcnet.entity.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This class is tightly coupled to the {@link org.beanmapper.BeanMapper} and it
 * is responsible for managing and executing the
 * {@link org.beanmapper.PropertyConverter}s.
 * </p>
 * 
 * @author patrik.nordwall
 */
class ConverterMapper {
    private static Logger LOG = LoggerFactory.getLogger(ConverterMapper.class);
    private static PropertyConverter doNothing = new DoNothingConverter();
    private static PropertyConverter ignore = new IgnoreConverter();
    private BeanMapper parentMapper;
    List<ConverterHolderForNames> convertersForNames = new ArrayList<ConverterHolderForNames>();
    List<ConverterHolderForTypes> convertersForTypes = new ArrayList<ConverterHolderForTypes>();

    ConverterMapper(BeanMapper aParentMapper) {
        parentMapper = aParentMapper;
    }

    void addConverter(String sourcePropertyName,
            String destinationPropertyName, PropertyConverter converter) {
        convertersForNames.add(new ConverterHolderForNames(sourcePropertyName,
                destinationPropertyName, converter));
    }

    void addConverter(Class<?> sourceType, Class<?> destinationType,
            PropertyConverter converter) {
        convertersForTypes.add(new ConverterHolderForTypes(sourceType,
                destinationType, converter));
    }

    void addPropertyNameMapping(String sourcePropertyName,
            String destinationPropertyName) {
        addConverter(sourcePropertyName, destinationPropertyName, doNothing);
    }

    void ignoreProperty(String sourcePropertyName) {
        addConverter(sourcePropertyName, sourcePropertyName, ignore);
    }

    void applyConverters(Object source, Object destination)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        for (Iterator<ConverterHolderForNames> iter = convertersForNames.iterator(); iter.hasNext();) {
            ConverterHolderForNames holder = (ConverterHolderForNames) iter
                    .next();
            applyConverter(source, destination, holder);
        }
    }

    private void applyConverter(Object source, Object destination,
            ConverterHolderForNames holder) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        if (parentMapper.isNestedPropertiesNull(source, holder
                .getSourcePropertyName())) {
            return;
        }

        if (!isSourceReadable(source, holder)) {
            return;
        }

        Object value = PropertyUtils.getProperty(source, holder
                .getSourcePropertyName());

        if (value == null) {
            return; // ignore null values
        }

        Object destinationValue = holder.getConverter().convert(value);

        if (destinationValue == null) {
            return;
        }

        parentMapper.instantiateNestedProperties(destination, holder
                .getDestinationPropertyName());

        if (!isDestinationWriteable(destination, holder)) {
            return;
        }

        BeanUtils.setProperty(destination, holder.getDestinationPropertyName(),
                destinationValue);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Applied converter: "
                    + holder.getDestinationPropertyName());
        }
    }

    private boolean isSourceReadable(Object source,
            ConverterHolderForNames holder) {
        if (PropertyUtils.isReadable(source, holder.getSourcePropertyName())) {
            return true;
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Converter property is not readable in source: "
                        + source.getClass().getName() + "#"
                        + holder.getSourcePropertyName());
            }

            return false;
        }
    }

    private boolean isDestinationWriteable(Object destination,
            ConverterHolderForNames holder) {
        if (PropertyUtils.isWriteable(destination, holder
                .getDestinationPropertyName())) {
            return true;
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Converter property is not writable in destination: "
                        + destination.getClass().getName() + "#"
                        + holder.getDestinationPropertyName());
            }

            return false;
        }
    }

    boolean applyTypeConverter(Object source, Object destination, String name,
            Object value) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        PropertyConverter typeConverter = getConverter(PropertyUtils
                .getPropertyType(source, name), PropertyUtils.getPropertyType(
                destination, name));

        if (typeConverter == null) {
            return false;
        }
        else {
            Object destinationValue = typeConverter.convert(value);
            BeanUtils.setProperty(destination, name, destinationValue);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Applied type converter: " + name);
            }

            return true;
        }
    }

    boolean hasConverterForDestination(String name) {
        for (Iterator<ConverterHolderForNames> iter = convertersForNames.iterator(); iter.hasNext();) {
            ConverterHolderForNames holder = (ConverterHolderForNames) iter
                    .next();

            if (name.equals(holder.getDestinationPropertyName())) {
                return true;
            }
        }

        return false;
    }

    ConverterMapper createSubMapperFor(BeanMapper newParent, String propertyName) {
        ConverterMapper subMapper = new ConverterMapper(newParent);
        subMapper.convertersForTypes = this.convertersForTypes;

        String s = propertyName + ".";

        for (Iterator<ConverterHolderForNames> iter = convertersForNames.iterator(); iter.hasNext();) {
            ConverterHolderForNames holder = (ConverterHolderForNames) iter
                    .next();

            if (holder.getSourcePropertyName().startsWith(s)
                    && holder.getDestinationPropertyName().startsWith(s)) {
                subMapper.addConverter(holder.getSourcePropertyName()
                        .substring(s.length()), holder
                        .getDestinationPropertyName().substring(s.length()),
                        holder.getConverter());
            }
        }

        return subMapper;
    }

    private PropertyConverter getConverter(Class<?> sourceType,
            Class<?> destinationType) {
        for (Iterator<ConverterHolderForTypes> iter = convertersForTypes.iterator(); iter.hasNext();) {
            ConverterHolderForTypes holder = (ConverterHolderForTypes) iter
                    .next();

            if (sourceType.equals(holder.getSourceType())
                    && destinationType.equals(holder.getDestinationType())) {
                return holder.getConverter();
            }
        }

        return null;
    }

    private static class DoNothingConverter implements PropertyConverter {
        /**
         * No conversion performed.
         * 
         * @return source
         */
        public Object convert(Object source) {
            return source;
        }
    }

    private static class IgnoreConverter implements PropertyConverter {
        public Object convert(Object source) {
            return null;
        }
    }

    private static class ConverterHolderForNames {
        private String sourcePropertyName;
        private String destinationPropertyName;
        private PropertyConverter converter;

        public ConverterHolderForNames(String aSourcePropertyName,
                String aDestinationPropertyName, PropertyConverter aConverter) {
            sourcePropertyName = aSourcePropertyName;
            destinationPropertyName = aDestinationPropertyName;
            converter = aConverter;
        }

        public String getDestinationPropertyName() {
            return destinationPropertyName;
        }

        public String getSourcePropertyName() {
            return sourcePropertyName;
        }

        public PropertyConverter getConverter() {
            return converter;
        }
    }

    private static class ConverterHolderForTypes {
        private Class<?> sourceType;
        private Class<?> destinationType;
        private PropertyConverter converter;

        public ConverterHolderForTypes(Class<?> aSourceType,
                Class<?> aDestinationType, PropertyConverter aConverter) {
            sourceType = aSourceType;
            destinationType = aDestinationType;
            converter = aConverter;
        }

        public Class<?> getDestinationType() {
            return destinationType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public PropertyConverter getConverter() {
            return converter;
        }
    }
}
