package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.CourseRemoteService;
import net.zdsoft.eis.base.common.dao.BasicSubjectDao;
import net.zdsoft.eis.base.common.entity.BaseCourse;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.util.EntityUtils;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MapRowMapper;

public class BasicSubjectDaoImpl extends BasicDAO implements BasicSubjectDao {
	
	@Autowired
	private  CourseRemoteService courseRemoteService;
	
	
	@Override
	public Map<String, String> getSubjectMap(String unitId, int isUsing, String likeName) {
		List<BaseCourse> baseCourses=new ArrayList<BaseCourse>();
		if(isUsing>0){
			if(StringUtils.isNotBlank(likeName)){
				baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectUnitIdIsUsingName(unitId,isUsing,likeName+"%"));
			}else{
				baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectUnitIdIsUsing(unitId,isUsing));
			}
		}else{
			if(StringUtils.isNotBlank(likeName)){
				baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectUnitIdName(unitId,likeName+"%"));
			}else{
				baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectUnitId(unitId));
			}
		}
		/*Map<String, String> rtnMap = new HashMap<String, String>();
		for(BaseCourse t : baseCourses){
			String idString=t.getId();
			
			rtnMap.put(t.getSubjectName(),idString);
		}
		*/
		return EntityUtils.getMap(baseCourses, "id", "subjectName");

//		String sql="SELECT id,subject_name FROM eduadm_subject WHERE unit_id = ? ";
//		if(isUsing>0){
//			sql+=" and is_using ="+ isUsing;
//		}
//		if(StringUtils.isNotBlank(likeName)){
//			sql+=" and subject_name like '"+ likeName+"%'";
//		}
//			sql+=" ORDER BY subject_type,order_id ";
//		return this.queryForMap(sql, new Object[]{unitId}, new MapRowMapper<String,String>(){
//
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("id");
//			}
//
//			@Override
//			public String mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("subject_name");
//			}
//			
//		});
	}

	@Override
	public Map<String, String> getSubjectMap(String[] subids) {
		
		List<BaseCourse> baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectIdIn(subids));
//	    List<BaseCourse> baseCourses=BaseCourse.dt(courseRemoteService.findByIn("id", subids));
//		Collections.sort(baseCourses,new Comparator<BaseCourse>(){
//		        @Override
//	            public int compare(BaseCourse o1, BaseCourse o2) {
//	             第一次比较SubjectName
//	            int i = o1.getSubjectName().compareTo(o2.getSubjectName());
//	            如果SubjectName相同则进行第二次order_id
//	            if(i==0){
//	                第二次比较
//	                int j=o1.getOrderId().compareTo(o2.getOrderId());
//              return j;
//	            }
//	            return i;
//	            }
//	        });
		/*Map<String, String> rtnMap = new HashMap<String, String>();
		for(BaseCourse t : baseCourses){
			String idString=t.getId();
			
			rtnMap.put(t.getSubjectName(),idString);
		}*/
		return EntityUtils.getMap(baseCourses, "id", "subjectName");
		
		
       
		
		
//		String sql="SELECT id,subject_name FROM eduadm_subject WHERE id in ";
//		return this.queryForInSQL(sql, null, subids,new MapRowMapper<String,String>(){
//
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("id");
//			}
//
//			@Override
//			public String mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("subject_name");
//			}
//			
//		}," ORDER BY subject_type,order_id ");
	}

	@Override
	public Map<String, String> getSubjectMapNo(String[] subids) {
		List<BaseCourse> baseCourses=BaseCourse.dt(courseRemoteService.findBySubjectIdIn(subids));
//		List<BaseCourse> baseCourses=BaseCourse.dt(courseRemoteService.findByIn("id", subids));
//		Collections.sort(baseCourses,new Comparator<BaseCourse>(){
//		        @Override
//	            public int compare(BaseCourse o1, BaseCourse o2) {
//	             第一次比较SubjectName
//	            int i = o1.getSubjectName().compareTo(o2.getSubjectName());
//	            如果SubjectName相同则进行第二次order_id
//	            if(i==0){
//	                第二次比较
//	                int j=o1.getOrderId().compareTo(o2.getOrderId());
//	                return j;
//	            }
//	            return i;
//	            }
//	        });
		/*Map<String, String> rtnMap = new HashMap<String, String>();
		for(BaseCourse t : baseCourses){
			String idString=t.getId();
			
			rtnMap.put(t.getSubjectCode(),idString);
		}*/
		return EntityUtils.getMap(baseCourses, "id", "subjectCode");
		
//		String sql="SELECT id,subject_code FROM eduadm_subject WHERE id in ";
//		return this.queryForInSQL(sql, null, subids,new MapRowMapper<String,String>(){
//
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("id");
//			}
//
//			@Override
//			public String mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("subject_code");
//			}
//			
//		}," ORDER BY subject_type,order_id ");
	}



}
