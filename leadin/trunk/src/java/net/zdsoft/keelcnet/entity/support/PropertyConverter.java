package net.zdsoft.keelcnet.entity.support;

/**
 * <p>
 * Interface for type converters.
 * </p>
 * 
 * @author Patrik Nordwall
 */
public interface PropertyConverter {
    /**
     * @param sourceValue
     *            object to convert from
     * @return new converted object
     */
    Object convert(Object sourceValue);
}
