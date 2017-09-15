package net.zdsoft.keelcnet.entity;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.text.DateFormat;


/**
 * <p>
 * Base class for objects, implementing toString, equals and hashCode using
 * commons-lang.
 * </p>
 *
 * <p>
 * Also implements swallow clone using commons-beanutils.
 * </p>
 *
 * <p>
 * Note that there are security and performance constraints with this
 * implementation, specifically they may fail under a security manager. Check
 * commons-lang documentation for more info.
 * </p>
 *
 * @author Brave Tao
 * @since 2004-12-16
 * @version $Id: BaseObject.java,v 1.1 2006/12/11 10:11:50 liangxiao Exp $
 * @since
 */
public abstract class BaseObject implements Serializable, Cloneable {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    /**
     * This method delegates to ReflectionToStringBuilder.
     *
     * @see java.lang.Object#toString()
     * @see ReflectionToStringBuilder
     * @see DateFormat#getDateTimeInstance()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * This method delegates to EqualsBuilder.reflectionEquals()
     *
     * @see java.lang.Object#equals(Object)
     * @see EqualsBuilder#reflectionEquals(Object, Object)
     */
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * This method delegates to HashCodeBuilder.reflectionHashCode()
     *
     * @see java.lang.Object#hashCode()
     * @see HashCodeBuilder#reflectionHashCode(Object)
     */
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Swallow cloning. This method delegates to BeanUtils.cloneBean(). Returns
     * <code>null</code> on any exception
     *
     * @see java.lang.Object#clone()
     * @see BeanUtils#cloneBean(Object)
     * @see PropertyUtils#copyProperties(Object, Object)
     */
    public Object clone() {
        try {
            return BeanUtils.cloneBean(this);
        } catch (Exception e) {
            log.error("Exception cloning bean: " + this + "\n" +
                "Exception was " + e + ": " + e.getLocalizedMessage());

            return null;
        }
    }
}
