/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $zhangza$
 */
package net.zdsoft.eis.frame.entity.type;

import java.io.Serializable;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.StringType;
import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;
import org.hibernate.type.descriptor.sql.CharTypeDescriptor;

public class YesNoType extends AbstractSingleColumnStandardBasicType<Boolean> implements
        PrimitiveType<Boolean>, DiscriminatorType<Boolean> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4437644343039143268L;

    public static final YesNoType INSTANCE = new YesNoType();

    public YesNoType() {
        super(CharTypeDescriptor.INSTANCE, CustomBooleanTypeDescriptor.CUSTOM_INSTANCE);
    }

    public String getName() {
        return "custom_yes_no";
    }

    public Class<Boolean> getPrimitiveClass() {
        return boolean.class;
    }

    public Boolean stringToObject(String xml) throws Exception {
        return fromString(xml);
    }

    public Serializable getDefaultValue() {
        return Boolean.FALSE;
    }

    public String objectToSQLString(Boolean value, Dialect dialect) throws Exception {
        return StringType.INSTANCE.objectToSQLString(value.booleanValue() ? "1" : "0", dialect);
    }

    /**
     * 1表示true; 0表示false
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Nov 19, 2010 8:43:22 PM $
     */
    private static class CustomBooleanTypeDescriptor extends BooleanTypeDescriptor {

        /**
         * Comment for <code>serialVersionUID</code>
         */
        private static final long serialVersionUID = -7929796774601961721L;

        public static final CustomBooleanTypeDescriptor CUSTOM_INSTANCE = new CustomBooleanTypeDescriptor();

        public CustomBooleanTypeDescriptor() {
            super('1', '0');
        }
    }
}
