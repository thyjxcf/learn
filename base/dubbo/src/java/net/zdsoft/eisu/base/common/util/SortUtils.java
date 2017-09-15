package net.zdsoft.eisu.base.common.util;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.subsystemcall.service.StusysSubsystemService;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.keelcnet.config.ContainerManager;


public class SortUtils<T>{
	/**
	 * 将留级生放在列表最后
	 * @param list 要排序的列表
	 * @param keyName 获取学生id的方法名 如 getStudentId不带（）注意大小写
	 * @param unitId 单位id
	 * @throws Exception
	 */
	public void sort(List<T> list,final String keyName,String unitId) throws Exception{
		StusysSubsystemService stusysSubsystemService = (StusysSubsystemService)ContainerManager.getComponent("stusysSubsystemService");
		EisuStudentService eisuStudentService = (EisuStudentService)ContainerManager.getComponent("eisuStudentService");
		if(null!=stusysSubsystemService){
			final List<String> stuIdlist = stusysSubsystemService.getStuAbnormalFlows(unitId, new String[]{"01"});
			Set<String> studentId = new HashSet<String>();
			for(T t : list){
				studentId.add((String)(t.getClass().getMethod(keyName).invoke(t)));
			}
			final Map<String,EisuStudent> stumap = eisuStudentService.getStudentMap(studentId.toArray(new String[0]));
			Collections.sort(list, new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					try {
						Method method1 = o1.getClass().getMethod(keyName);
						Method method2 = o1.getClass().getMethod(keyName);
						String sid1 = (String)method1.invoke(o1);
						String sid2 = (String)method2.invoke(o2);
						
						String classid = "";
						if(stumap.containsKey(sid1)){
							classid = stumap.get(sid1).getClassid();
						}
						if(stuIdlist.contains(sid1)){
							sid1 = classid + 1;
						}else{
							sid1 = classid + 0;
						}
						
						if(stumap.containsKey(sid2)){
							classid = stumap.get(sid2).getClassid();
						}
						
						if(stuIdlist.contains(sid2)){
							sid2 = classid + 1;
						}else{
							sid2 = classid + 0;
						}
						
						return sid1.compareTo(sid2);
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					} 
				}
				
			});
		}
	}
	
}
