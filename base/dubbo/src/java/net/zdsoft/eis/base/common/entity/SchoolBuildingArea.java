package net.zdsoft.eis.base.common.entity;import java.io.Serializable;import java.util.ArrayList;import java.util.List;import org.springframework.context.annotation.Scope;import org.springframework.stereotype.Component;import com.alibaba.fastjson.TypeReference;import net.zdsoft.eis.base.annotation.field.SchoolTypeGroupScope;import net.zdsoft.eis.base.annotation.type.ScopeConfigured;import net.zdsoft.eis.base.constant.enumeration.SchoolTypeGroup;import net.zdsoft.eis.base.util.SUtils;/** * 校舍面积  * @author  *  */@ScopeConfigured(scopeAnnotation={SchoolTypeGroupScope.class})@Component@Scope("prototype")public class SchoolBuildingArea implements Serializable{		public static SchoolBuildingArea dc(String data) {		return SUtils.dc(data, SchoolBuildingArea.class);	}		public static List<SchoolBuildingArea> dt(String data) {		List<SchoolBuildingArea> ts = SUtils.dt(data, new TypeReference<List<SchoolBuildingArea>>() {		});		if (ts == null)			ts = new ArrayList<SchoolBuildingArea>();		return ts;	}		private static final long serialVersionUID = 1L;		/**	 * 	 */	private String id;	/**	 * 	 */	private String schoolId;	/**	 * 校舍id	 */	private String schoolBuildingId;	/**	 * 建筑面积类型	 */	private String areaType;	/**	 * 面积	 */	private double area;	/**	 * 被外单位借用面积	 */	@SchoolTypeGroupScope(scope={SchoolTypeGroup.SECONDARY_VOCATIONAL})	private double lendArea;		/**	 * 设置	 */	 public void setId(String id){	 	this.id = id;	 }	 	 /**	  * 获取	  */	 public String getId(){	 	return this.id;	 }	/**	 * 设置	 */	 public void setSchoolId(String schoolId){	 	this.schoolId = schoolId;	 }	 	 /**	  * 获取	  */	 public String getSchoolId(){	 	return this.schoolId;	 }	/**	 * 设置校舍id	 */	 public void setSchoolBuildingId(String schoolBuildingId){	 	this.schoolBuildingId = schoolBuildingId;	 }	 	 /**	  * 获取校舍id	  */	 public String getSchoolBuildingId(){	 	return this.schoolBuildingId;	 }	/**	 * 设置建筑面积类型	 */	 public void setAreaType(String areaType){	 	this.areaType = areaType;	 }	 	 /**	  * 获取建筑面积类型	  */	 public String getAreaType(){	 	return this.areaType;	 }	/**	 * 设置面积	 */	 public void setArea(double area){	 	this.area = area;	 }	 	 /**	  * 获取面积	  */	 public double getArea(){	 	return this.area;	 }	public double getLendArea() {		return lendArea;	}	public void setLendArea(double lendArea) {		this.lendArea = lendArea;	}		}