package net.zdsoft.keelcnet.entity;

import java.lang.reflect.Field;

import net.zdsoft.keelcnet.entity.support.BeanMapper;

/**
 * Use common-beanutils or cglib or ognl, need benchmark
 * 
 * @author Brave Tao
 * @since 2004-5-20
 * @version $Id: DtoAssembler.java,v 1.5 2007/01/15 02:09:58 liangxiao Exp $
 * @since
 */
public class DtoAssembler {

    @SuppressWarnings("unused")
    private static final BeanMapper BEANMAPPER = new BeanMapper();

    public static void toDto(Object entity, Object dto) {
        new BeanMapper().copyProperties(entity, dto, true);

        // try {
        // BeanUtils.copyProperties(dto, entity);
        // }
        // catch (IllegalAccessException e) {
        // e.printStackTrace();
        // }
        // catch (InvocationTargetException e) {
        // e.printStackTrace();
        // }
    }

    public static void toEntity(Object dto, Object entity) {
        new BeanMapper().copyProperties(dto, entity);

        // try {
        // BeanUtils.copyProperties(entity, dto);
        // }
        // catch (IllegalAccessException e) {
        // e.printStackTrace();
        // }
        // catch (InvocationTargetException e) {
        // e.printStackTrace();
        // }
    }

    /**
     * 打印赋值语句，比如：obj1.setId(obj0.getId());
     * 
     * @param src
     *            源对象的名称，比如：obj0
     * @param dest
     *            目标对象的名称，比如：obj1
     * @param clazz
     *            类定义
     * @param isOrGet
     *            针对Boolean的属性，采用的是is还是get开始的方法名，true表示is，false表示get
     */
    public static <T> void printSetCode(String src, String dest, Class<T> clazz,
            boolean isOrGet) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String fieldTypeName = fields[i].getType().getName();
            String fieldName = fields[i].getName();
            fieldName = fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            String methodPrefix = "boolean".equals(fieldTypeName)
                    || ("java.lang.Boolean".equals(fieldTypeName) && isOrGet) ? "is"
                    : "get";
            System.out.println(dest + ".set" + fieldName + "(" + src + "."
                    + methodPrefix + fieldName + "());");
        }
    }

}
