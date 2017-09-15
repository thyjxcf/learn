/**
 * 
 */
package net.zdsoft.eis.base.constant.enumeration;

import java.util.EnumSet;
import java.util.Map;

import net.zdsoft.eis.base.constant.enumerable.CodeEnumerable;
import net.zdsoft.eis.base.constant.enumerable.SchoolTypeContainable;
import net.zdsoft.eis.base.util.CodeEnumUtils;

import org.apache.commons.collections.CollectionUtils;

/**
 * 学校类型
 * @author zhangkc
 * @date 2015年4月22日 下午2:02:30
 */
public enum SchoolType implements CodeEnumerable<String, SchoolType>, SchoolTypeContainable{
	/** 幼儿园 */
	PREEDU("111", "幼儿园", EnumSet.of(Section.INFANT)),
	/** 独立设置的少数民族幼儿园 */
	PREEDU_MINORITY("112", "独立设置的少数民族幼儿园", EnumSet.of(Section.INFANT)),
	/** 附设幼儿班 */
	PREEDU_ATTACHED("119", "附设幼儿班", EnumSet.of(Section.INFANT)),
	
	/** 小学 */
	PRIMARY("211", "小学", EnumSet.of(Section.PRIMARY)),
	/** 独立设置的少数民族小学 */
	PRIMARY_MINORITY("212", "独立设置的少数民族小学", EnumSet.of(Section.PRIMARY)),
	/** 一贯制学校小学部 */
	PRIMARY_MULTYLE_YEAR("213", "一贯制学校小学部", EnumSet.of(Section.PRIMARY)),
	/** 小学教学点 */
	PRIMARY_TEACHSCH("218", "小学教学点", EnumSet.of(Section.PRIMARY)),
	/** 其他学校附设小学班 */
	PRIMARY_ATTACHED("219", "其他学校附设小学班", EnumSet.of(Section.PRIMARY)),
	
	/** 职工小学 */
	PRIMARY_ADULT_STAFF("221", "职工小学", EnumSet.of(Section.PRIMARY)),
	/** 农民小学 */
	PRIMARY_ADULT_FARMER("222", "农民小学", EnumSet.of(Section.PRIMARY)),
	/** 小学班 */
	PRIMARY_ADULT_CLASS("228", "小学班", EnumSet.of(Section.PRIMARY)),
	/** 扫盲班 */
	PRIMARY_ADULT_ELIMINATE_ILLITERACY("229", "扫盲班", EnumSet.of(Section.PRIMARY)),
	
	/** 初级中学 */
	JUNIOR("311", "初级中学", EnumSet.of(Section.JUNIOR)),
	/** 九年一贯制学校 */
	JUNIOR_NINEYEAR("312", "九年一贯制学校", EnumSet.of(Section.PRIMARY,Section.JUNIOR)),
	/** 独立设置的少数民族初级中学 */
	JUNIOR_MINORITY("313", "独立设置的少数民族初级中学", EnumSet.of(Section.JUNIOR)),
	/** 独立设置的少数民族九年一贯制学校 */
	JUNIOR_MINORITY_NINEYEAR("314", "独立设置的少数民族九年一贯制学校", EnumSet.of(Section.PRIMARY,Section.JUNIOR)),
	/** 附设普通初中班 */
	JUNIOR_ATTACHED("319", "附设普通初中班", EnumSet.of(Section.JUNIOR)),
	
	/** 职业初中 */
	JUNIOR_CAREER("321", "职业初中", EnumSet.of(Section.JUNIOR)),
	/** 独立设置的少数民族职业初中 */
	JUNIOR_CAREER_MINORITY("322", "独立设置的少数民族职业初中", EnumSet.of(Section.JUNIOR)),
	/** 普通中学附设职教班 */
	JUNIOR_CAREER_MIDDLE_SCHOOL_ATTACHED("328", "普通中学附设职教班", EnumSet.of(Section.JUNIOR)),
	/** 附设职业初中 */
	JUNIOR_CAREER_ATTACHED("329", "附设职业初中班", EnumSet.of(Section.JUNIOR_CAREER)),
	
	/** 成人职工初中 */
	JUNIOR_ADULT_STAFF("331", "成人职工初中", EnumSet.of(Section.JUNIOR)),
	/** 成人农民初中 */
	JUNIOR_ADULT_FARMER("332", "成人农民初中", EnumSet.of(Section.JUNIOR)),
	
	/** 完全中学 */
	SENIOR_FULL("341", "完全中学", EnumSet.of(Section.JUNIOR, Section.SENIOR)),
	/** 高级中学 */
	SENIOR("342", "高级中学", EnumSet.of(Section.SENIOR)),
	/** 独立设置的少数民族完全中学 */
	SENIOR_FULL_MINORITY("343", "独立设置的少数民族完全中学", EnumSet.of(Section.JUNIOR, Section.SENIOR)),
	/** 独立设置的少数民族高级中学 */
	SENIOR_MINORITY("344", "独立设置的少数民族高级中学", EnumSet.of(Section.SENIOR)),
	/** 十二年一贯制学校 */
	SENIOR_TWELVEYEAR("345", "十二年一贯制学校", EnumSet.of(Section.PRIMARY, Section.JUNIOR, Section.SENIOR)),
	/** 附设普通高中班 */
	SENIOR_ATTACHED("349", "附设普通高中班", EnumSet.of(Section.SENIOR)),
	
	/** 成人职工高中 */
	SENIOR_ADULT_STAFF("351", "成人职工高中", EnumSet.of(Section.SENIOR)),
	/** 成人农民高中 */
	SENIOR_ADULT_FARMER("352", "成人农民高中", EnumSet.of(Section.SENIOR)),
	
	/** 调整后中等职业学校 */
	SECONDARY_VOCATIONAL("361", "调整后中等职业学校", null),
	/** 中等技术学校 */
	SECONDARY_VOCATIONAL_TECHNICAL("362", "中等技术学校", null),
	/** 中等师范学校 */
	SECONDARY_VOCATIONAL_TEACHER_TRAINING("363", "中等师范学校", null),
	/** 成人中等专业学校 */
	SECONDARY_VOCATIONAL_ADULT("364", "成人中等专业学校", null),
	/** 职业高中学校 */
	SECONDARY_VOCATIONAL_CAREER("365", "职业高中学校", null),
	/** 技工学校 */
	SECONDARY_VOCATIONAL_SKILLED_WORKER("366", "技工学校", null),
	/** 附设中职班 */
	SECONDARY_VOCATIONAL_ATTACHED("368", "附设中职班", null),
	/** 中等职业其他机构 */
	SECONDARY_VOCATIONAL_OTHER("369", "中等职业其他机构", null),
	
	/** 工读学校 */
	REFORM_SCHOOL("371", "工读学校", EnumSet.of(Section.JUNIOR, Section.SENIOR)),
	
	/** 本科院校：大学 */
	HIGHER_EDUCATION_UNIVERSITY("411", "本科院校：大学", null),
	/** 本科院校：学院 */
	HIGHER_EDUCATION_COLLEGE("412", "本科院校：学院", null),
	/** 本科院校：独立学院 */
	HIGHER_EDUCATION_INDEPENDENT_INSTITUTE("413", "本科院校：独立学院", null),
	/** 专科院校：高等专科学校 */
	HIGHER_EDUCATION_SPECIALIST("414", "专科院校：高等专科学校", null),
	/** 专科院校：高等职业学校 */
	HIGHER_EDUCATION_VOCATIONAL("415", "专科院校：高等职业学校", null),
	/** 其他机构：分校、大专班 */
	HIGHER_EDUCATION_OTHER("419", "其他机构：分校、大专班", null),
	
	/** 职工高校 */
	ADULT_HIGHER_EDUCATION_STAFF("421", "职工高校", null),
	/** 农民高校 */
	ADULT_HIGHER_EDUCATION_FARMER("422", "农民高校", null),
	/** 管理干部学院 */
	ADULT_HIGHER_EDUCATION_CADRE("423", "管理干部学院", null),
	/** 教育学院 */
	ADULT_HIGHER_EDUCATION_EDUCATION_COLLEGE("424", "教育学院", null),
	/** 独立函授学院 */
	ADULT_HIGHER_EDUCATION_CORRESPONDENCE("425", "独立函授学院", null),
	/** 广播电视大学 */
	ADULT_HIGHER_EDUCATION_RADIO_TELEVISION("426", "广播电视大学", null),
	/** 其他成人高等教育机构 */
	ADULT_HIGHER_EDUCATION_OTHER("429", "其他成人高等教育机构", null),
	
	/** 盲人学校 */
	SPECIAL_EDUCATION_BLIND("511", "盲人学校", EnumSet.of(Section.PRIMARY, Section.JUNIOR, Section.SENIOR)),
	/** 聋人学校 */
	SPECIAL_EDUCATION_DEAF("512", "聋人学校", EnumSet.of(Section.PRIMARY, Section.JUNIOR, Section.SENIOR)),
	/** 弱智学校 */
	SPECIAL_EDUCATION_MENTALLY_RETARDED("513", "弱智学校", EnumSet.of(Section.PRIMARY, Section.JUNIOR, Section.SENIOR)),
	/** 其他特殊教育学校（指对两类以上残疾人进行教育的学校） */
	SPECIAL_EDUCATION_OTHER("514", "其他特殊教育学校（指对两类以上残疾人进行教育的学校）", EnumSet.of(Section.PRIMARY, Section.JUNIOR, Section.SENIOR)),
	/** 小学附设特教班 */
	SPECIAL_EDUCATION_PRIMARY_ATTACHED("518", "小学附设特教班", EnumSet.of(Section.PRIMARY)),
	/** 初中附设特教班 */
	SPECIAL_EDUCATION_JUNIOR_ATTACHED("519", "附设特教班(小学、初中)", EnumSet.of(Section.PRIMARY,Section.JUNIOR)),
	
	/** 培养研究生的科研机构 */
	POSTGRADUATE("911", "培养研究生的科研机构", null),
	
	/** 民办的其他高等教育机构 */
	OTHER_HIGHER_EDUCATION("921", "民办的其他高等教育机构", null),
	
	/** 职工技术培训学校（机构） */
	MEDIUM_CAREER_EDUCATION_STAFF("931", "职工技术培训学校（机构）", null),
	/** 农村成人文化技术培训学校（机构） */
	MEDIUM_CAREER_EDUCATION_FARMER("932", "农村成人文化技术培训学校（机构）", null),
	/** 其他培训机构（含社会培训机构） */
	MEDIUM_CAREER_EDUCATION_OTHER("933", "其他培训机构（含社会培训机构）", null);
	
	private String code;
	private String desc;
	/** 所含学段 */
	private EnumSet<Section> sections;
	
	/** 所属学校类型分组 */
	private SchoolTypeGroup schoolTypeGroup;
	//缓存数组
	public static Map<String, SchoolType> typeMap = CodeEnumUtils.getCacheMap(SchoolType.class);
	
	/**
	 * @param code
	 * @param desc
	 */
	private SchoolType(String code, String desc, EnumSet<Section> sections) {
		this.code = code;
		this.desc = desc;
		this.sections = sections;
		//学校类型分组即学校类型的前两位
		this.schoolTypeGroup = SchoolTypeGroup.fromCode(code.substring(0, 2));
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
	
	public SchoolTypeGroup getSchoolTypeGroup() {
		return schoolTypeGroup;
	}
	
	public UnitEducationType getUnitEducationType() {
		return schoolTypeGroup.getUnitEducationType();
	}
	
	/**
	 * 由code转化为枚举对象
	 * @param code
	 * @return
	 * @author zhangkc
	 * @date 2015年3月30日 下午6:09:02
	 */
	public static SchoolType fromCode(String code){
		return typeMap.get(code);
	}
	
	/**
	 * 获取学校所含学段
	 * @return
	 * @author zhangkc
	 * @date 2015年4月22日 下午3:22:34
	 */
	public EnumSet<Section> getSections(){
		return sections;
	}
	/**
	 * 是否包含某个指定学段
	 * @param section
	 * @return
	 * @author zhangkc
	 * @date 2015年4月22日 下午3:34:42
	 */
	public boolean containSection(Section section){
		return CollectionUtils.isNotEmpty(sections) && sections.contains(section);
	}
	@Override
	public boolean contains(SchoolType schoolType) {
		return this.equals(schoolType);
	}
}
