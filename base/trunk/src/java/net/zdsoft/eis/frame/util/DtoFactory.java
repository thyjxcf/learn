package net.zdsoft.eis.frame.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.frame.entity.HibernateEntity;
import net.zdsoft.keelcnet.entity.DtoAssembler;
import net.zdsoft.keelcnet.entity.EntityObject;



/* 
 * DTO和Entity Object(BaseEntity)的复制转换封装类，一般是以目标对象的成员属性为准去复制的
 * 一般是singleton的，dto和entity转换的一般性原则：
 * 如果是属性少于8个的，采用hardcode，即手写dto和entity之间数据转换的get/set方法，
 * 如果属性超过8个，使用beanutils进行反射。
 *
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: DtoFactory.java,v 1.6 2007/01/09 10:03:05 jiangl Exp $
 */
@SuppressWarnings("unchecked")
public class DtoFactory {
	private final static Logger log = LoggerFactory.getLogger(DtoFactory.class);
	
	public DtoFactory(){
	}
	
	/**
     * 从DTO对象复制数据生成Entity对象，以目标对象（即entity）的成员属性为准复制
     * 注：在将DTO复制成entity时，DTO中若""值时，把他转换为null，否则在存入数据库时可能出现数据类型转换的错误提示
     * @param dto
     * @param entity
     */    
    public static void copyFromDTO(Serializable dto, HibernateEntity entity){
    	DtoAssembler.toEntity(dto,entity);
    	
    	//把是String类型的成员变量的""值变为null，否则若是uniqueidentifier类型时会出错
		Method[] methods = entity.getClass().getMethods();
		for(int i=0;i<methods.length;i++){
			Class para = methods[i].getReturnType();
			String nameGet = methods[i].getName();
			//返回值类型是String的get方法
			if(para == String.class && "get".equals(nameGet.substring(0, 3))){
				Object returnValue = null; 
				try {
					//执行get方法，得到其值
					returnValue = methods[i].invoke(entity,new Object[]{});
					//将""值转为null
					if("".equals(returnValue)){
						returnValue = null;
						//得到对应set方法的名称
						String nameSet = "set" + nameGet.substring(3);
						
						Method methodSet = entity.getClass().getMethod(nameSet, new Class[]{String.class});
						methodSet.invoke(entity, new Object[]{returnValue});
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
    }

    /**
     * 从Entity对象复制数据生成DTO对象，以目标对象（即dto）的成员属性为准复制
     * @param entity
     * @param dto
     */
    public static void copyToDTO(HibernateEntity entity, Serializable dto){
    	DtoAssembler.toDto(entity, dto);
    }
    
    public static Object copyToDTO(HibernateEntity entity, Class dtoClass){
    	Object obj = null;
		try {
			obj = dtoClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    	DtoAssembler.toDto(entity, obj);
    	return obj;
    }
    
    public static Object copyToEntity(Serializable dto, HibernateEntity entity){    	
		copyFromDTO(dto, entity);
		return entity;
    }

    /**
     * 把多个Entity对象复制生成一个DTO对象，后者会覆盖前者
     * @param entitys
     * @param dto
     */
    public static void copyToDTO(HibernateEntity[] entitys, Serializable dto){
    	if(entitys!=null){
			for (int i = 0; i < entitys.length; i++) {
				HibernateEntity object = entitys[i];
				copyToDTO(object,dto);
			}
		}
    }

    /**
     * 把Enity列表中的数据复制到指定DTO Class 的DTO列表中，并生成返回DTO的列表
     * @param entityList
     * @param dtoClass
     * @return List
     */
    public static List createDTOs(Collection entityList, Class dtoClass){
        if(entityList==null||entityList.size()==0)
            return new ArrayList();
        
        Serializable dto;
        HibernateEntity entity;
    	List dtos = new ArrayList(entityList.size());
        for (Iterator iter = entityList.iterator(); iter.hasNext();) {
            entity = (HibernateEntity) iter.next();
            try {
                dto = (Serializable) dtoClass.newInstance();
                copyToDTO(entity, dto);
                dtos.add(dto);
            }
            catch (Exception e) {
            	log.error(e.getMessage(), e);
            }
        }
		
		return dtos;
    }
    
    /**
	 * 从指定的DTO把数据复制到目标DTO里
	 * @param srcDto 源DTO
	 * @param desDto 目标DTO
	 */
	public static void copyToDTOFromDTO(Serializable srcDto,Serializable desDto){
		DtoAssembler.toDto(srcDto, desDto);
	}
	
	
	/**
	 * 从指定的entity把数据复制到目标entity里
	 * @param srcEntity 源entity
	 * @param destEntity 目标entity
	 */
	public static void copyToEntityFromEntity(HibernateEntity srcEntity,HibernateEntity destEntity){
		DtoAssembler.toEntity(srcEntity,destEntity);
	}
	
	
	//测试
//	public static void main(String[] args){
//		BasicClassDto dto = new BasicClassDto();
//		dto.setClasscode("");
//		dto.setClassname("");
//		
//		BasicClass entity = new BasicClass();
//		
//		copyFromDTO(dto,entity);
//		
//		if(entity.getClasscode() == null){
//			System.out.println("code: null");
//		}else{
//			if("".equals(entity.getClasscode())){
//				System.out.println("code: ''");
//			}else{
//				System.out.println("name:"+entity.getClasscode());
//			}
//		}
//	}	
    
    /**
     * 把Enity列表中的数据复制到指定DTO Class 的DTO列表中，并生成返回DTO的列表
     * 对于DTO继承自EntityObject
     * @param entityList
     * @param dtoClass
     * @return List
     */
    public static List createDTOs4EntityObject(Collection entityList, Class dtoClass){
        List dtos = new ArrayList();
        if(entityList!=null){
            for (Iterator iter = entityList.iterator(); iter.hasNext();) {
                EntityObject entity = (EntityObject) iter.next();
                try {
                    Serializable dto = (Serializable)dtoClass.newInstance();
                    DtoAssembler.toDto(entity, dto);
                    dtos.add(dto);          
                } catch (Exception e) {
                    
                }               
            }
        }
        return dtos;
    }
}



