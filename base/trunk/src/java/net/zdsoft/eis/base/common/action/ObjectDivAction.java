/* 
 * @(#)ObjectDivAction.java    Created on Jun 23, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.frame.action.BaseDivAction;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.util.PinyinUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 选择对象
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 23, 2011 2:46:46 PM $
 */
public abstract class ObjectDivAction<E extends BaseEntity> extends
		BaseDivAction {

	private static final long serialVersionUID = 5835994763723008730L;

	private String letter;
	private boolean supportFullQuery = false;// 是否支持全单位查询
	private Set<String> firstNameLetterSet = new TreeSet<String>();
	private String useCheckbox = "true";
	private int macroOrder;// 宏顺序
	private boolean iframe;// 是否为iframe
	private boolean showLetterIndex;// 是否显示首字母索引

	private String objectIds;// 多选的对象ID

	private boolean uniquely;// 是否惟一
	protected String groupId;
	protected List<SimpleObject> objects = new ArrayList<SimpleObject>();
	protected Map<String,List<SimpleObject>> objectMap = new TreeMap<String, List<SimpleObject>>();
	
	protected String queryObjectCode;
	protected String queryObjectName;
	protected int codeType = 2;// 号码类型：1统一编号 2单位内编号
	
	public void setSupportFullQuery(boolean supportFullQuery) {
		this.supportFullQuery = supportFullQuery;
	}

	protected String teacherId;
	
	/**
	 * 只显示有权限的节点(学籍)
	 */
	protected boolean stusysShowPopedom = false;

	/**
	 * 只显示有权限的节点(教务)
	 */
	protected boolean eduadmShowPopedom = false;
	
	/**
	 * 只显示有权限的节点(成绩)
	 */
	protected boolean achiShowPopedom = false;
	

	protected UnitService unitService;

	protected SubsystemPopedomService subsystemPopedomService;
	
	protected EduadmSubsystemService eduadmSubsystemService;

	public ObjectDivAction() {

	}

	/**
	 * 取全部数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasByAll() {
		return new ArrayList<E>();
	}

	/**
	 * 根据unitId取数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasByUnitId() {
		return new ArrayList<E>();
	}

	/**
	 * 根据groupId取数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasByGroupId() {
		return new ArrayList<E>();
	}

	/**
	 * 根据code/name 左匹配 查询数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasFaintness() {
		return new ArrayList<E>();
	}

	/**
	 * 获取数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasByConditon() {
		return new ArrayList<E>();
	}

	/**
	 * 获取数据，供子类覆盖
	 * 
	 * @return
	 */
	protected List<E> getDatasByParentId() {
		return new ArrayList<E>();
	}

	/**
	 * 将对象转化为SimpleObject
	 * 
	 * @param e
	 * @param object
	 */
	protected abstract void toObject(E e, SimpleObject object);

	public String getObjectsByAll() {
		List<E> datas = getDatasByAll();
		disposeObjects(datas);
		return SUCCESS;
	}

	public String getObjectsByUnitId() {
		System.out.println("hello");
		List<E> datas = getDatasByUnitId();
		disposeObjects(datas);
		return SUCCESS;
	}

	public String getObjectsByGroupId() {
		List<E> datas = getDatasByGroupId();
		System.out.println(datas.size());
		disposeObjects(datas);

		return SUCCESS;
	}

	public String getObjectsFaintness() {
		if (supportFullQuery) {
			List<E> datas = getDatasFaintness();
			disposeObjects(datas);
		} else {
			if (StringUtils.isEmpty(queryObjectName)
					&& StringUtils.isEmpty(queryObjectCode)) {

			} else {
				List<E> datas = getDatasFaintness();
				disposeObjects(datas);
			}
		}
		return SUCCESS;
	}

	public String getObjectsByCondition() {
		try {
			List<E> datas = getDatasByConditon();
			disposeObjects(datas);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	public String getObjectsByParentId() {
		try {
			List<E> datas = getDatasByParentId();
			disposeObjects(datas);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	protected void disposeObjects(List<E> datas) {
		if (null == datas || datas.size() == 0) {
			return;
		}

		// 转化为SimpleObject
		for (E e : datas) {
			SimpleObject object = new SimpleObject();
			toObject(e, object);
			// 如果使用统一编号，则界面上展现统一编号
			if (codeType == 1) {
				object.setObjectCode(object.getUnitiveCode());
			}
			objects.add(object);
		}

		if (objects.size() == 1)
			uniquely = true;// 唯一

		// 是否显示首字母索引
		if (!showLetterIndex){
			objectMap.put("#", objects);
			return;
		}

		if (StringUtils.isNotBlank(letter)) {
			for (int i = objects.size() - 1; i >= 0; i--) {
				String element = PinyinUtil.toHanyuPinyin(objects.get(i)
						.getObjectName().substring(0, 1), true);
				if (null != element) {
					firstNameLetterSet.add(element.toUpperCase());
				}
				if (!letter.equalsIgnoreCase(element)) {
					objects.remove(i);
				}
			}
		} else {
			for (SimpleObject object : objects) {
				if(StringUtils.isBlank(object.getObjectName())){
					continue;
				}
				String element = PinyinUtil.toHanyuPinyin(object
						.getObjectName().substring(0, 1), true);
				if (null != element) {
					firstNameLetterSet.add(element.toUpperCase());
				}
			}
		}
		
		//Map
		for (SimpleObject object : objects) {
			if(StringUtils.isBlank(object.getObjectName())){
				continue;
			}
			String element = PinyinUtil.toHanyuPinyin(object
					.getObjectName().substring(0, 1), true);
			if (null != element) {
				firstNameLetterSet.add(element.toUpperCase());
				String letter = element.toUpperCase();
				List<SimpleObject> os = objectMap.get(letter);
				if(os == null){
					os = new ArrayList<SimpleObject>();
					objectMap.put(letter, os);
				}
				os.add(object);
			}
		}
		
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Set<String> getFirstNameLetterSet() {
		return firstNameLetterSet;
	}

	public void setFirstNameLetterSet(Set<String> firstNameLetterSet) {
		this.firstNameLetterSet = firstNameLetterSet;
	}

	public String getUseCheckbox() {
		return useCheckbox;
	}

	public void setUseCheckbox(String useCheckbox) {
		this.useCheckbox = useCheckbox;
	}

	public boolean isUniquely() {
		return uniquely;
	}

	public void setUniquely(boolean uniquely) {
		this.uniquely = uniquely;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}

	public String getQueryObjectCode() {
		return queryObjectCode;
	}

	public void setQueryObjectCode(String queryObjectCode) {
		this.queryObjectCode = queryObjectCode;
	}

	public String getQueryObjectName() {
		return queryObjectName;
	}

	public void setQueryObjectName(String queryObjectName) {
		this.queryObjectName = queryObjectName;
	}

	public int getMacroOrder() {
		return macroOrder;
	}

	public void setMacroOrder(int macroOrder) {
		this.macroOrder = macroOrder;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<SimpleObject> getObjects() {
		return objects;
	}
	
	public Map<String, List<SimpleObject>> getObjectMap() {
		return objectMap;
	}

	public boolean isIframe() {
		return iframe;
	}

	public void setIframe(boolean iframe) {
		this.iframe = iframe;
	}

	public boolean isShowLetterIndex() {
		return showLetterIndex;
	}

	public void setShowLetterIndex(boolean showLetterIndex) {
		this.showLetterIndex = showLetterIndex;
	}

	public boolean isEduadmShowPopedom() {
		return eduadmShowPopedom;
	}

	public void setEduadmShowPopedom(boolean eduadmShowPopedom) {
		this.eduadmShowPopedom = eduadmShowPopedom;
	}

	public boolean isAchiShowPopedom() {
		return achiShowPopedom;
	}

	public void setAchiShowPopedom(boolean achiShowPopedom) {
		this.achiShowPopedom = achiShowPopedom;
	}

	public boolean isStusysShowPopedom() {
		return stusysShowPopedom;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	public void setStusysShowPopedom(boolean stusysShowPopedom) {
		this.stusysShowPopedom = stusysShowPopedom;
	}

	public void setSubsystemPopedomService(
			SubsystemPopedomService subsystemPopedomService) {
		this.subsystemPopedomService = subsystemPopedomService;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	
}
