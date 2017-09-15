package net.zdsoft.eis.base.tree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SchoolDistrictService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.SchoolDistrictTreeItemConverter;
import net.zdsoft.eis.base.tree.converter.UnitTreeItemConverter;
import net.zdsoft.eis.base.tree.param.TreeItemParam;
import net.zdsoft.eis.base.tree.param.TreeParam;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.tree.XLoadTreeItem;
import net.zdsoft.leadin.tree.XTreeMaker;

/**
 * 教育局到学生树的service
 * 
 * @author Zhanghh
 * @version $Revision: 1.5 $
 * @since 1.5 $Id: EduTree.java,v 1.5 2006/12/14 09:48:18 zhanghh Exp $
 */
public class EduTree extends XTreeMaker implements TreeService {
	protected static Logger log = LoggerFactory.getLogger(EduTree.class);

	private UnitService unitService;
	private SchoolDistrictService schoolDistrictService; // 学区
	private SchoolService schoolService; // 学校基本信息
	private UnitTreeItemConverter unitTreeItemConverter;
	private SchoolDistrictTreeItemConverter schoolDistrictTreeItemConverter;
	protected UnitIniService unitIniService;

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public void setUnitTreeItemConverter(
			UnitTreeItemConverter unitTreeItemConverter) {
		this.unitTreeItemConverter = unitTreeItemConverter;
	}

	public void setSchoolDistrictTreeItemConverter(
			SchoolDistrictTreeItemConverter schoolDistrictTreeItemConverter) {
		this.schoolDistrictTreeItemConverter = schoolDistrictTreeItemConverter;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public void setSchoolDistrictService(
			SchoolDistrictService schoolDistrictService) {
		this.schoolDistrictService = schoolDistrictService;
	}
	
	public XLoadTreeItem getTree(TreeParam param) {
		// 对于非自定义树进行参数封装
		if (StringUtils.isEmpty(param.getCustomXmlSrc())) {
			wrapTreeParam(param);
		}

		this.contextPath = param.getContextPath();
		param.setParentId(param.getUnitId());

		if (param.isShowSchDistrict()) {
			return getEduDistrictTree(param);
		} else {
			return getEduTree(param);
		}

	}

	/**
	 * 取得指定教育局的树
	 * 
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getEduTree(TreeParam param) {
		String eduid = param.getUnitId();
		if (eduid == null || "".equals(eduid)) {
			return new XLoadTreeItem("未指定单位", "tree", "0");
		}

		XLoadTreeItem root = null;

		// 生成根节点
		Unit unit = unitService.getUnit(eduid);

		root = unitTreeItemConverter.buildTreeItem(unit, param, null, "tree");
		root.setXmlSrc(null);
		// -----------------------------------------------
		// 如果是学校，则跳过教育局的下属节点，直接定位到班级。
		// 目的：使学校和教育端的调用统一起来，不用再在界面上进行判断
		if (Unit.UNIT_CLASS_SCHOOL == unit.getUnitclass()) {
			// 生成点击加号时激活的事件，获取子节点的xml
			if (TreeConstant.ITEMTYPE_SCHOOL == param.getLayer()) {// 若只到学校这层

			} else {// 显示学校下级节点层
				addSchoolChildNodes(root, param);
			}
		} else {
			// -----------------------------------------------
			// 获取下属教育局列表
			if (param.isShowEdu()) {
				List<Unit> underlingEduList = unitService.getUnderlingUnits(
						eduid, 1);
				// 生成下属教育局节点（包括下属学校）
				for (Iterator<Unit> iter = underlingEduList.iterator(); iter
						.hasNext();) {
					Unit u = (Unit) iter.next();
					XLoadTreeItem item = getEduItem(u, param);
					root.appendNode(item);
				}
			}

			// 获取下属学校列表
			if (param.isShowSch()) {
				List<Unit> underlingSchoolList = new ArrayList<Unit>();
				if (param.getUnitType() == 0)
					underlingSchoolList = unitService.getUnderlingUnits(eduid,
							2);
				else
					underlingSchoolList = unitService.getUnderlingUnits(eduid,
							2, param.getUnitType());
				// 生成下属学校节点
				for (Iterator<Unit> iter = underlingSchoolList.iterator(); iter
						.hasNext();) {
					Unit u = (Unit) iter.next();
					XLoadTreeItem item = getSchoolItem(u, param);
					root.appendNode(item);
				}
			}

			// 其它下级节点：如部门
			addEduChildNodes(root, param);
		}

		// 返回脚本
//		String js = root.getJScript();
//		log.info("教育局下属学校树：" + js);
		return root;
	}

	/**
	 * 取得指定教育局的树(可显示学区层)
	 * 
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getEduDistrictTree(TreeParam param) {
		String eduid = param.getUnitId();
		if (eduid == null || "".equals(eduid)) {
			return new XLoadTreeItem("未指定单位", "tree", "0");
		}

		// 生成根节点
		Unit eduRoot = unitService.getUnit(eduid);
		XLoadTreeItem root = unitTreeItemConverter.buildTreeItem(eduRoot,
				param, null, "tree");
		root.setXmlSrc(null);

		// 获取下属教育局列表
		if (param.isShowEdu()) {
			List<Unit> underlingEduList = unitService.getUnderlingUnits(eduid,
					1);
			// 生成下属教育局节点（包括下属学区）
			for (Iterator<Unit> iter = underlingEduList.iterator(); iter
					.hasNext();) {
				Unit u = (Unit) iter.next();
				XLoadTreeItem item = getEduDistrictItem(u, param);
				root.appendNode(item);
			}
		}

		// 获取下属学区列表
		if (param.isShowSchDistrict()) {
			List<SchoolDistrict> underlingXqList = schoolDistrictService
					.getSchoolDistricts(eduid);
			// 生成下属学区节点（包括下属学校）
			for (Iterator<SchoolDistrict> iter = underlingXqList.iterator(); iter
					.hasNext();) {
				SchoolDistrict sd = (SchoolDistrict) iter.next();
				XLoadTreeItem item = getSchDistrictItem(sd, param);
				root.appendNode(item);
			}
		}

		// 获取直属未设置学区的学校列表
		if (param.isShowSch()) {
			List<Unit> underlingSchoolList = unitService.getUnderlingUnits(
					eduid, 2);
			if (underlingSchoolList != null && underlingSchoolList.size() > 0) {
				String[] unitIds = new String[underlingSchoolList.size()];
				for (int i = 0; i < underlingSchoolList.size(); i++) {
					Unit u = (Unit) underlingSchoolList.get(i);
					unitIds[i] = u.getId();
				}
				List<School> schidList = schoolService.getSchools(unitIds);
				// 取得学校map
				Map<String, School> schMap = new HashMap<String, School>();
				for (int i = 0; i < schidList.size(); i++) {
					School bs = (School) schidList.get(i);
					schMap.put(bs.getId(), bs);
				}

				// 生成下属学校节点
				for (Iterator<Unit> iter = underlingSchoolList.iterator(); iter
						.hasNext();) {
					Unit u = (Unit) iter.next();
					School bs = schMap.get(u.getId());
					if (bs == null)
						continue;
					if (bs.getSchdistrictid() == null
							|| (bs.getSchdistrictid().trim()).equals("")) {
						XLoadTreeItem item = getSchoolItem(u, param);
						root.appendNode(item);
					}
				}
			}
		}

		// 返回脚本
//		String js = root.getJScript();
//		log.info("教育局下属学校树：" + js);
		return root;
	}

	/**
	 * 取得教育局的节点
	 * 
	 * @param edu
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getEduItem(Unit edu, TreeParam param) {
        // 把当前教育局包装成一个节点
        XLoadTreeItem item = unitTreeItemConverter.buildTreeItem(edu, param, null, makeXTreeItemName());
        item.setXmlSrc("");

        // 获取下属教育局列表
        if (param.isShowEdu()) {
        	List<Unit> underlingEduList = unitService.getUnderlingUnits(edu.getId(), Unit.UNIT_CLASS_EDU);
            // 生成下属教育局节点（包括下属学校）
            for (Iterator<Unit> iter = underlingEduList.iterator(); iter.hasNext();) {
                Unit u = iter.next();
                XLoadTreeItem temp = this.getEduItem(u,param);
                item.appendNode(temp);
            }
        }

        // 获取下属学校列表
        if (param.isShowSch()) {
        	List<Unit> underlingSchoolList =new ArrayList<Unit>();
        	if(param.getUnitType()==0)
        		underlingSchoolList = unitService.getUnderlingUnits(edu.getId(), Unit.UNIT_CLASS_SCHOOL);
        	else
        		underlingSchoolList = unitService.getUnderlingUnits(edu.getId(), Unit.UNIT_CLASS_SCHOOL,param.getUnitType());
            // 生成下属学校节点
            for (Iterator<Unit> iter = underlingSchoolList.iterator(); iter.hasNext();) {
                Unit u = iter.next();
                XLoadTreeItem temp = getSchoolItem(u, param);
                item.appendNode(temp);
            }
        }

        return item;
    }

	/**
	 * 取得教育局的节点(可以生成学区节点)
	 * 
	 * @param edu
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getEduDistrictItem(Unit edu, TreeParam param) {
		// 把当前教育局包装成一个节点
		XLoadTreeItem item = unitTreeItemConverter.buildTreeItem(edu, param,
				null, makeXTreeItemName());
		item.setXmlSrc("");

		// 获取下属教育局列表
		if (param.isShowEdu()) {
			List<Unit> underlingEduList = unitService.getUnderlingUnits(edu
					.getId(), 1);
			// 生成下属教育局节点（包括下属学区）
			for (Iterator<Unit> iter = underlingEduList.iterator(); iter
					.hasNext();) {
				Unit u = iter.next();
				XLoadTreeItem temp = this.getEduDistrictItem(u, param);
				item.appendNode(temp);
			}
		}

		// 获取下属学区列表
		if (param.isShowSchDistrict()) {
			List<SchoolDistrict> underlingXqList = schoolDistrictService
					.getSchoolDistricts(edu.getId());
			// 生成下属学区节点
			for (Iterator<SchoolDistrict> iter = underlingXqList.iterator(); iter
					.hasNext();) {
				SchoolDistrict sd = iter.next();
				XLoadTreeItem temp = getSchDistrictItem(sd, param);
				item.appendNode(temp);
			}
		}

		// 获取直属未设置学区的学校列表
		if (param.isShowSch()) {
			List<Unit> underlingSchoolList = unitService.getUnderlingUnits(edu
					.getId(), 2);
			if (underlingSchoolList != null && underlingSchoolList.size() > 0) {
				String[] unitIds = new String[underlingSchoolList.size()];
				for (int i = 0; i < underlingSchoolList.size(); i++) {
					Unit u = (Unit) underlingSchoolList.get(i);
					unitIds[i] = u.getId();
				}

				List<School> schidList = schoolService.getSchools(unitIds);
				// 取得学校map
				Map<String, School> schMap = new HashMap<String, School>();
				for (int i = 0; i < schidList.size(); i++) {
					School bs = schidList.get(i);
					schMap.put(bs.getId(), bs);
				}

				// 生成下属学校节点
				for (Iterator<Unit> iter = underlingSchoolList.iterator(); iter
						.hasNext();) {
					Unit u = (Unit) iter.next();
					School bs = schMap.get(u.getId());
					if (bs == null)
						continue;
					if (bs.getSchdistrictid() == null
							|| (bs.getSchdistrictid().trim()).equals("")) {
						XLoadTreeItem temp = getSchoolItem(u, param);
						item.appendNode(temp);
					}
				}
			}
		}

		return item;
	}

	/**
	 * 取得学区节点
	 * 
	 * @param sd
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getSchDistrictItem(SchoolDistrict sd, TreeParam param) {
		XLoadTreeItem item = schoolDistrictTreeItemConverter.buildTreeItem(sd,
				param, null, makeXTreeItemName());
		item.setXmlSrc("");

		// 获取下属学校列表
		if (param.isShowSch()) {
			List<School> underlingSchoolList = schoolService
					.getUnderlingSchools(sd.getId());
			if (underlingSchoolList == null)
				underlingSchoolList = new ArrayList<School>();
			String[] unitIds = new String[underlingSchoolList.size()];
			for (int i = 0; i < underlingSchoolList.size(); i++) {
				School bs = (School) underlingSchoolList.get(i);
				unitIds[i] = bs.getId();
			}

			// 取得学校map
			Map<String, Unit> unitMap = unitService.getUnitMap(unitIds);

			// 生成下属学校节点
			for (Iterator<School> iter = underlingSchoolList.iterator(); iter
					.hasNext();) {
				School basicSchool = (School) iter.next();
				Unit u = unitMap.get(basicSchool.getId());
				XLoadTreeItem temp = getSchoolItem(u, param);
				item.appendNode(temp);
			}
		}

		return item;
	}

	/**
	 * 取得学校节点
	 * 
	 * @param school
	 * @param param
	 * @return
	 */
	private XLoadTreeItem getSchoolItem(Unit school, TreeParam param) {
		XLoadTreeItem item = unitTreeItemConverter.buildTreeItem(school, param,
				null, makeXTreeItemName());

		// 生成点击加号时激活的事件，获取子节点的xml
		if (TreeConstant.ITEMTYPE_SCHOOL == param.getLayer()) {// 若只到学校这层
			item.setXmlSrc("");
		} else {// 显示学校下级节点
			param.setUnitId(school.getId());
			TreeItemParam itemParam = new TreeItemParam();
			fillSchoolChildNodeParam(param, itemParam);
			String xmlSrc = unitTreeItemConverter.wrapXmlSrc(itemParam
					.getXmlSrc(), itemParam.getItemLinkParams(), param);
			item.setXmlSrc(xmlSrc);
		}

		return item;
	}

	/**
	 * 填充学校子节点参数，可供子类覆盖
	 * 
	 * @param param
	 * @return
	 */
	protected void fillSchoolChildNodeParam(TreeParam param, TreeItemParam item) {

	}

	/**
	 * 增加学校子节点
	 * 
	 * @param schoolItem
	 * @param param
	 */
	protected void addSchoolChildNodes(XLoadTreeItem schoolItem, TreeParam param) {

	}

	/**
	 * 增加教育局子节点
	 * 
	 * @param eduItem
	 * @param param
	 */
	protected void addEduChildNodes(XLoadTreeItem eduItem, TreeParam param) {

	}

	/**
	 * 取权限Map
	 * 
	 * @param treeParam
	 * @return
	 */
	public Map<String, XLoadTreeItem> getPopedomTreeItemMap(TreeParam treeParam) {
		return new HashMap<String, XLoadTreeItem>();
	}

	/**
	 * 是否返回
	 * 
	 * @param param
	 * @return
	 */
	public boolean isReturn(TreeParam param) {
		// 受权限控制
		if (param.isNeedPopedom()) {
			Map<String, XLoadTreeItem> treeItemMap = getPopedomTreeItemMap(param);
			if(treeItemMap ==null){
				return true;
			}
			XLoadTreeItem item = treeItemMap.get(param.getParentId());
			if (null != item) {// 有下级节点，则进行权限控制
				return true;
			}
		}
		return false;
	}

	public void wrapTreeParam(TreeParam param) {
		 param.setTreeServiceBeanId(getTreeServiceBeanId());
	}
	
	protected String getTreeServiceBeanId() {
        String className = getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

	public <E extends BaseEntity> List<E> getPopedomEntities(TreeParam param) {
		return null;
	}
}
