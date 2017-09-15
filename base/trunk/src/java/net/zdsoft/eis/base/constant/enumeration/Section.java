/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.SubSchool;
import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

/**
 * 学段--其实仅适用于基础教育阶段
 * @author zhangkc
 * @date 2015年3月30日 下午5:10:22
 */
public enum Section implements CodeEnumerable<String, Section>{
	/** 幼儿园 */
	INFANT("0","幼儿园"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			return 4;
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			return new String[]{"","托班","小班","中班","大班"}[gradeInt];
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
		
	},
	/** 小学 */
	PRIMARY("1","小学"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			return subSchool.getGradeYear();
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			return "小"+numberMap[gradeInt];
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
	},
	/** 初中 */
	JUNIOR("2","初中"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			return subSchool.getJuniorYear();
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			return "初"+numberMap[gradeInt];
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
	},
	/** 高中 */
	SENIOR("3","高中"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			return 3;
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			return "高"+numberMap[gradeInt];
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
	},
	/** 职业初中 */
	@Deprecated
	JUNIOR_CAREER("4","职业初中"),
	/** 中职 */
	@Deprecated
	SECONDARY_VOCATIONAL("5","中职"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			return 5;
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			return new String[]{"","一年级","二年级","三年级","四年级","五年级"}[gradeInt];
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
		
	},
	/** 特教 */
	@Deprecated
	SPECIAL_EDUCATION("6","特教"){
		@Override
		public int gradeLength(SubSchool subSchool) {
			if(subSchool.getSubschoolType().equals("518") || subSchool.getSubschoolType().equals("519")){
				return 9;
			}else{
				return 12;
			}
		}
		@Override
		public String gradeName(SubSchool subSchool, int gradeInt) {
			//特教的年级名称分别与小学、初中一样
//			if(gradeInt <= subSchool.getGradeYear()){
//				return PRIMARY.gradeName(subSchool, gradeInt);
//			}
//			return JUNIOR.gradeName(subSchool, gradeInt - subSchool.getGradeYear());
			return numberMap[gradeInt]+"年级";
		}
		@Override
		public boolean isFixedOptionsGradeName() {
			return true;
		}
	},
	/** 成人中学 */
	@Deprecated
	ADULT_MIDDLE_SCHOOL("7","成人中学"),
	/** 工读学校 */
	@Deprecated
	REFORM_SCHOOL("8","工读学校"),
	/** 大学专科 */
	@Deprecated
	COLLEGE("9","大学专科"),
	/** 大学本科 */
	@Deprecated
	UNIVERSITY("10","大学本科"),
	/** 研究生 */
	@Deprecated
	GRADUATE("11","研究生"),
	/** 其他 */
	@Deprecated
	OTHER("99","其他");
	
	private String code;
	private String desc;
	
	//缓存数组
	private static Map<String, Section> typeMap = CodeEnumUtils.getCacheMap(Section.class);
	private static final String[] numberMap = {"", "一" , "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};			
	/**
	 * @param code
	 * @param desc
	 */
	private Section(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return CodeEnumUtils.formattedToString(this);
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getDesc(){
		return this.desc;
	}
	/**
	 * 由code转化为枚举对象
	 * @param code
	 * @return
	 * @author zhangkc
	 * @date 2015年3月30日 下午6:09:02
	 */
	public static Section fromCode(String code){
		return typeMap.get(code);
	}
	/**
	 * 获取学段对于指定主体校/附设班的年制，默认6
	 * @param subSchool
	 * @return
	 * @author zhangkc
	 * @date 2015年6月17日 下午6:46:21
	 */
	public int gradeLength(SubSchool subSchool){
		return 6;
	}
	/**
	 * 获取年级名称
	 * @param subSchool
	 * @param gradeInt
	 * @return
	 * @author zhangkc
	 * @date 2015年6月17日 下午6:55:46
	 */
	public String gradeName(SubSchool subSchool, int gradeInt){
		return null;
	}
	/**
	 * 年级名称是否为固定选项
	 * @return
	 * @author zhangkc
	 * @date 2015年6月18日 下午4:24:53
	 */
	public boolean isFixedOptionsGradeName(){
		return false;
	}
}
