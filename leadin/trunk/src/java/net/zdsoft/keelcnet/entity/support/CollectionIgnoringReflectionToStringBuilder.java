package net.zdsoft.keelcnet.entity.support;

import java.lang.reflect.Field;

import net.zdsoft.keelcnet.util.CollectionUtils;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Customized ReflectionToStringBuilder ignoring Collection fields.
 *
 * @see org.apache.commons.lang.builder.ReflectionToStringBuilder
 *
 * @author Carlos Sanchez
 * @version $Revision: 1.1 $
 */
public class CollectionIgnoringReflectionToStringBuilder
    extends ReflectionToStringBuilder {
    public CollectionIgnoringReflectionToStringBuilder(Object object) {
        super(object);
    }

    public CollectionIgnoringReflectionToStringBuilder(Object object,
        ToStringStyle style) {
        super(object, style);
    }

    /**
     * Check if the field is a collection and return false in that case.
     *
     * @see org.apache.commons.lang.builder.ReflectionToStringBuilder#accept(java.lang.reflect.Field)
     */
    protected boolean accept(Field field) {
        Class<?> cls = field.getType();

        if (CollectionUtils.isCollection(cls)) {
            return false;
        }

        if (CollectionUtils.isMap(cls)) {
            return false;
        }

        return super.accept(field);
    }
}
