package net.zdsoft.eis.frame.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import net.zdsoft.eis.frame.client.BaseEntity;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

public class ToolUtils {
    
    public static <T extends BaseEntity> T getGeneric(Type type) {
        Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
        @SuppressWarnings("unchecked")
        Class<T> mTClass = (Class<T>) (generics[0]);
        try {
            T t2 = mTClass.newInstance();
            return t2;
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        return null;
    }
    
    public static String[] createArray(String... ss) {
        return ss;
    }

    /**
     * 生成32位UUID
     * 
     * @return
     */
    public static String createUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 类属性复制
     * @param dest
     * @param ori
     * @param ignoreNull，如果为true，则将源对象中属性为null的值不进行复制（尽量设置为false，复制效率提高很多）
     * @return
     */
    public static <T, K> K copyProperties(T source, K target, boolean ignoreNull) {
        if (source == null)
            return target;
        if (target == null)
            return null;

        if (!ignoreNull)
            return copyProperties(source, target);
        else {
            try {
                BeanMap map = BeanMap.create(target.getClass().newInstance());
                map.setBean(target);

                List<String> getters = new ArrayList<String>();

                for (Object key : map.keySet()) {
                	if(key.toString().equals("file")){
                		getters.add(key.toString());
                	}
                	else{
	                    Object v = map.get(key);
	                    if (v == null) {
	                        getters.add(key.toString());
	                    }
                    }
                }
                BeanUtils.copyProperties(source, target, getters.toArray(new String[0]));
                return target;

            }
            catch (InstantiationException e1) {
                e1.printStackTrace();
            }
            catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static <T, K> K copyProperties(T dest, K ori) {
        if (dest == null)
            return ori;
        if (ori == null)
            return null;

        BeanCopier bc = BeanCopier.create(dest.getClass(), ori.getClass(), false);
        try {
            bc.copy(dest, ori, null);
        }
        catch (Exception e) {
            return null;
        }
        return ori;
    }

    public static <T, K> K copyProperties(T dest, Class<K> classK) {
        if (dest == null)
            return null;
        BeanCopier bc = BeanCopier.create(dest.getClass(), classK, false);
        try {
            K k = classK.newInstance();
            bc.copy(dest, k, null);
            return k;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static <T, K> List<K> copyProperties(List<T> dest, Class<T> classt, Class<K> classK) {
        if (dest == null || CollectionUtils.isEmpty(dest))
            return new ArrayList<K>();
        BeanCopier bc = BeanCopier.create(classt, classK, false);
        List<K> list = new ArrayList<K>();
        try {
            for (T t : dest) {
                K k = classK.newInstance();
                bc.copy(t, k, null);
                list.add(k);
            }
        }
        catch (Exception e) {
            return new ArrayList<K>();
        }
        return list;
    }

}
