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
 * {@link org.beanmapper.PropertyAggregator}s.
 * </p>
 * 
 * @author Patrik Nordwall
 */
class AggregatorMapper {
    private static Logger LOG = LoggerFactory.getLogger(AggregatorMapper.class);
    private BeanMapper parentMapper;
    List<AggregatorHolder> aggregators = new ArrayList<AggregatorHolder>();

    AggregatorMapper(BeanMapper aParentMapper) {
        parentMapper = aParentMapper;
    }

    void addAggregator(String[] sourcePropertyNames,
            String destinationPropertyName, PropertyAggregator aggregator) {
        aggregators.add(new AggregatorHolder(sourcePropertyNames,
                destinationPropertyName, aggregator));
    }

    void applyAggregators(Object source, Object destination)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        for (Iterator<AggregatorHolder> iter = aggregators.iterator(); iter.hasNext();) {
            AggregatorHolder holder = (AggregatorHolder) iter.next();
            applyAggregator(source, destination, holder); 
        }
    }

    private void applyAggregator(Object source, Object destination,
            AggregatorHolder holder) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Object[] values = getSourceValues(source, holder);

        if (values == null) {
            // all source values are null, ignore null values
            return;
        }

        Object destinationValue = holder.getAggregator().convert(values);

        if (destinationValue == null) {
            return; // ignore null values
        }

        parentMapper.instantiateNestedProperties(destination, holder
                .getDestinationPropertyName());

        if (!isDestinationWriteable(destination, holder
                .getDestinationPropertyName())) {
            return;
        }

        BeanUtils.setProperty(destination, holder.getDestinationPropertyName(),
                destinationValue);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Applied aggregator: "
                    + holder.getDestinationPropertyName());
        }
    }

    private Object[] getSourceValues(Object source, AggregatorHolder holder)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Object[] values = new Object[holder.getSourcePropertyNames().length];
        boolean hasSomeValue = false;

        for (int i = 0; i < values.length; i++) {
            if (!isSourceReadable(source, holder.getSourcePropertyNames()[i])) {
                continue;
            }

            if (parentMapper.isNestedPropertiesNull(source, holder
                    .getSourcePropertyNames()[i])) {
                continue;
            }

            Object value = PropertyUtils.getProperty(source, holder
                    .getSourcePropertyNames()[i]);

            if (value == null) {
                continue;
            }

            values[i] = value;
            hasSomeValue = true;
        }

        if (hasSomeValue) {
            return values;
        }
        else {
            return null;
        }
    }

    private boolean isSourceReadable(Object source, String name) {
        if (PropertyUtils.isReadable(source, name)) {
            return true;
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Aggregator property is not readable in source: "
                        + source.getClass().getName() + "#" + name);
            }

            return false;
        }
    }

    private boolean isDestinationWriteable(Object destination, String name) {
        if (PropertyUtils.isWriteable(destination, name)) {
            return true;
        }
        else {
            if (LOG.isDebugEnabled()) {
                LOG
                        .debug("Aggregator property is not writable in destination: "
                                + destination.getClass().getName() + "#" + name);
            }

            return false;
        }
    }

    AggregatorMapper createSubMapperFor(BeanMapper newParent,
            String propertyName) {
        AggregatorMapper subMapper = new AggregatorMapper(newParent);
        String s = propertyName + ".";

        for (Iterator<AggregatorHolder> iter = aggregators.iterator(); iter.hasNext();) {
            AggregatorHolder holder = (AggregatorHolder) iter.next();

            if (holder.getDestinationPropertyName().startsWith(s)) {
                boolean ok = true;

                for (int i = 0; i < holder.getSourcePropertyNames().length; i++) {
                    if (!holder.getSourcePropertyNames()[i].startsWith(s)) {
                        ok = false;

                        break;
                    }
                }

                if (ok) {
                    String[] newSourcePropertyNames = new String[holder
                            .getSourcePropertyNames().length];

                    for (int i = 0; i < holder.getSourcePropertyNames().length; i++) {
                        newSourcePropertyNames[i] = holder
                                .getSourcePropertyNames()[i].substring(s
                                .length());
                    }

                    subMapper.addAggregator(newSourcePropertyNames,
                            holder.getDestinationPropertyName().substring(
                                    s.length()), holder.getAggregator());
                }
            }
        }

        return subMapper;
    }

    boolean hasAggregatorForDestination(String name) {
        for (Iterator<AggregatorHolder> iter = aggregators.iterator(); iter.hasNext();) {
            AggregatorHolder holder = (AggregatorHolder) iter.next();

            if (name.equals(holder.getDestinationPropertyName())) {
                return true;
            }
        }

        return false;
    }

    private static class AggregatorHolder {
        private String[] sourcePropertyNames;
        private String destinationPropertyName;
        private PropertyAggregator aggregator;

        public AggregatorHolder(String[] aSourcePropertyNames,
                String aDestinationPropertyName, PropertyAggregator aAggregator) {
            sourcePropertyNames = aSourcePropertyNames;
            destinationPropertyName = aDestinationPropertyName;
            aggregator = aAggregator;
        }

        public PropertyAggregator getAggregator() {
            return aggregator;
        }

        public String getDestinationPropertyName() {
            return destinationPropertyName;
        }

        public String[] getSourcePropertyNames() {
            return sourcePropertyNames;
        }
    }
}
