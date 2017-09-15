/**
 * 
 */
package net.zdsoft.leadin.tmptool;

import java.lang.reflect.Field;

/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: DtoAssembler.java, v 1.0 2007-5-28 下午04:38:11 zhaosf Exp $
 */

public class DtoAssemblerTmp {
	@SuppressWarnings("unchecked")
    public static void printSetCode(String src, String dest, Class clazz,
            boolean isOrGet) {
		Class superClazz = clazz;
		while(null != superClazz){
			
			 Field[] fields = superClazz.getDeclaredFields();
		        for (int i = 0; i < fields.length; i++) {
		            String fieldTypeName = fields[i].getType().getName();
		            String fieldName = fields[i].getName();
		            
		            //去掉更新戳、日志
		            if(fieldName.equals("updatestamp") || fieldName.equals("log") ) return;
		            
		            fieldName = fieldName.substring(0, 1).toUpperCase()
		                    + fieldName.substring(1);
		            
		            
		            String methodPrefix = "boolean".equals(fieldTypeName)
		                    || ("java.lang.Boolean".equals(fieldTypeName) && isOrGet) ? "is"
		                    : "get";
		            System.out.println(dest + ".set" + fieldName + "(" + src + "."
		                    + methodPrefix + fieldName + "());");
		        }
			superClazz = superClazz.getSuperclass();
		}
       
    }
	
	public static void main(String[] args) {
//		net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("dto", "entity", Grade.class, false);
	}
}
