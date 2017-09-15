package net.zdsoft.eis.frame.entity.type;

import java.util.Date;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor;

/*
 * 将sql中的表示时间的numeric(15,0)类型数据转换为java中的java.util.Date类型
 *
 * <p> 城域综合信息平台 </p><p> CNet3.0 </p><p> Copyright (c) 2003 </p><p> Company:
 * ZDSoft </p> @author Brave Tao
 *
 * @since 1.0
 * @version $Id: DateType.java,v 1.1 2007/01/09 11:00:38 chenzy Exp $
 */
public class DateType extends AbstractSingleColumnStandardBasicType<Date> implements
        IdentifierType<Date>, LiteralType<Date> {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7657257446658742207L;

    public static final DateType INSTANCE = new DateType();

    public DateType() {
        super(org.hibernate.type.descriptor.sql.BigIntTypeDescriptor.INSTANCE,
                JdbcDateTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "long2date";
    }

    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public Date stringToObject(String xml) throws Exception {
        return new Date(Long.parseLong(xml));
    }

    @Override
    public String objectToSQLString(Date value, Dialect dialect) throws Exception {
        return String.valueOf(value.getTime());
    }

}
