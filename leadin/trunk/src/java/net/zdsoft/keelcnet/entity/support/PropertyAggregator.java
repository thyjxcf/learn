package net.zdsoft.keelcnet.entity.support;

/**
 * <p>
 * Interface for aggregators, i.e. kind of converter that takes several
 * properties in the source object and converts them to one property in the
 * destination object.
 * </p>
 * 
 * @author Patrik Nordwall
 * 
 */
public interface PropertyAggregator {
    Object convert(Object[] sourceValues);
}
