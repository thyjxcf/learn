package net.zdsoft.office.util;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.zdsoft.keel.util.UUIDUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public abstract class EntityUtils {

    private static String maxDate;

    private static String minDate;

    static {
        minDate = "1900-01-01";
        maxDate = "3000-12-12";
    }

    private static Set<Object> fetchExceptPropertyNames(Object[] objects) {
        Set<Object> set = new HashSet<Object>();
        set.add("class"); // 默认class属性排除
        if (ArrayUtils.isEmpty(objects)) {
            return set;
        }
        for (Object object : objects) {
            set.add(object);
        }
        return set;
    }

    /**
     * 获取对象的属性值，按照属性名的字母顺序排序，以List形式返回。
     * 
     * @param entity
     * @return
     */
    public static List<Object> entityToList(Serializable entity,
            String[] exceptPropertyNames) {
        List<Object> list = new LinkedList<Object>();
        PropertyDescriptor[] descriptors = PropertyUtils
                .getPropertyDescriptors(entity);
        String propertyName = null;
        Set<Object> excepts = fetchExceptPropertyNames(exceptPropertyNames);
        for (PropertyDescriptor descriptor : descriptors) {
            propertyName = descriptor.getName();
            if (excepts.contains(propertyName)) {// 排除不需要的属性名的值
                continue;
            }
            try {
                list.add(PropertyUtils.getSimpleProperty(entity, propertyName));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取一个 包含类中属性值的数组，按照属性名称的字母顺序排列。
     * 
     * @param entity
     * @return
     */
    public static Object[] entityToArguments(Serializable entity) {
        return entityToList(entity, null).toArray(new Object[0]);
    }

    /**
     * 获取一个 包含类中属性值的数组，按照属性名的字母顺序排列。
     * 
     * @param entity
     *            实体
     * @param exceptPropertyNames
     *            不需要返回值的属性名称
     * @return
     */
    public static Object[] entityToArguments(Serializable entity,
            String[] exceptPropertyNames) {
        List<Object> list = entityToList(entity, exceptPropertyNames);
        for (int i = 0; i < exceptPropertyNames.length; i++) {
            if (list.contains(exceptPropertyNames[i])) {
                list.remove(exceptPropertyNames[i]);
            }
        }
        return list.toArray(new Object[0]);
    }

    /**
     * <pre>
     *      简单的处理带有IN参数的SQL语句，由于JDBC使用IN(?) 
     *      传进去一个数组或列表产生问题。 
     *      用此方法转换成新的SQL语句形式为 IN(?,?,?...)
     *      注意：该方法只处理SQL语句只有一个IN的情况
     * </pre>
     * 
     * @param sql
     *            为处理SQL语句，含有IN(?)字符串
     * @param inKeyArgs
     *            SQL语句的IN关键字中，占位符所对应参数数组
     * @return
     */
    public static String simpleProcessSQLForInKey(String sql, Object[] inKeyArgs) {
        if (ArrayUtils.isEmpty(inKeyArgs)) { // 参数为空，忽略
            return sql;
        }
        StringBuffer sb = new StringBuffer(" IN(");
        for (int i = 0; i < inKeyArgs.length; i++) {
            if (0 == i) {
                sb.append("?");
            }
            else {
                sb.append(",?");
            }
        }
        sb.append(")");
        String targetSql = sql.replaceFirst("\\s.(IN|in).*?\\(\\?\\)", sb
                .toString());
        return targetSql;
    }

    /**
     * 主要用于SQL 中时间的比较，如果穿进去的时间字符串为空，则返回"3000-12-12" 否则返回传入参数
     * 
     * @param beginDate
     * @return
     */
    public static String getBeginDate(String beginDate) {
        if (!StringUtils.isBlank(beginDate)) {
            return beginDate;
        }
        return minDate;
    }

    public static String getBeginTime(String beginTime) {
        if (!StringUtils.isBlank(beginTime)) {
            return beginTime;
        }
        return minDate + " 00:00:00";
    }

    /**
     * 主要用于SQL 中时间的比较，如果穿进去的时间字符串为空，则返回"1970-01-01" 否则返回传入参数
     * 
     * @param beginDate
     * @return
     */
    public static String getEndDate(String endDate) {
        if (!StringUtils.isBlank(endDate)) {
            return endDate;
        }
        return maxDate;
    }

    public static String getEndTime(String endTime) {
        if (!StringUtils.isBlank(endTime)) {
            return endTime;
        }
        return maxDate + " 23:59:59";
    }

    /**
     * 生成32位的大写唯一字符串，主要用于数据库主键
     * 
     * @return
     */
    public static String generateUUID() {
        return UUIDUtils.newId().toUpperCase();
    }
}
