/* 
 * @(#)EisuStudentTreeXmlAction.java    Created on May 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.tree.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.converter.XLoadTreeItemConverter;
import net.zdsoft.eis.base.tree.service.TreeService;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.eisu.base.common.entity.EisuGrade;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.entity.SpecialtyPoint;
import net.zdsoft.eisu.base.common.service.EisuClassService;
import net.zdsoft.eisu.base.common.service.EisuGradeService;
import net.zdsoft.eisu.base.common.service.EisuStudentService;
import net.zdsoft.eisu.base.common.service.SpecialtyPointService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;
import net.zdsoft.eisu.base.common.util.SortUtils;
import net.zdsoft.eisu.base.tree.EisuTreeConstant;
import net.zdsoft.leadin.tree.XLoadTreeItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 与学生相关的树：单位 -> 院、系 -> 专业 -> 专业方向 -> 年级 -> 班级 -> 学生
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 17, 2011 8:06:17 PM $
 */
public class EisuStudentTreeXmlAction extends InstituteTreeXmlAction {

    private static final long serialVersionUID = 1360626425622525155L;

    private SpecialtyService specialtyService;
    private SpecialtyPointService specialtyPointService;
    private EisuGradeService eisuGradeService;
    private EisuClassService eisuClassService;
    private EisuStudentService eisuStudentService;

    private XLoadTreeItemConverter<Specialty> specialtyTreeItemConverter;
    private XLoadTreeItemConverter<SpecialtyPoint> specialtyPointTreeItemConverter;
    private XLoadTreeItemConverter<EisuGrade> eisuGradeTreeItemConverter;
    private XLoadTreeItemConverter<EisuClass> eisuClassTreeItemConverter;
    private XLoadTreeItemConverter<EisuStudent> eisuStudentTreeItemConverter;
    
    private TreeService eisuClassTree;
    
    public void setEisuClassTree(TreeService eisuClassTree) {
        this.eisuClassTree = eisuClassTree;
    }

    protected TreeService getTreeService(){
        return eisuClassTree;
    }
    
    /**
     * 点击学校或院系节点，展示系和专业
     * 
     * @return
     * @throws Exception
     */
    public String expandInstituteSpecialty() throws Exception {
        List<XLoadTreeItem> items = new ArrayList<XLoadTreeItem>();

        // 系节点
        items.addAll(getInstituteItems());

        // 专业节点
        if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
                .getTreeDepth(EisuTreeConstant.ITEMTYPE_SPECIALTY)) {
            
            List<Specialty> list = null;
            if (treeParam.isShowInstitute()) {
                if (isReturn(treeParam)) {
                    return buildTreeXml(items);
                }
               
                list = specialtyService.getSpecialtysByParent(treeParam.getParentId(), treeParam
                        .getParentType(),!treeParam.isShowValid());
            } else {
                if (treeParam.isNeedPopedom()) {
                    list = getTreeService().getPopedomEntities(treeParam);
                } else {
                    list = specialtyService.getAllSpecialtysByParent(treeParam.getParentId(), treeParam
                            .getParentType(),!treeParam.isShowValid());
                }
            }
            List<String> ids = new ArrayList<String>();
            if (subsystemPopedomService != null && treeParam.isOnlyShowPopedom()){
            	ids = subsystemPopedomService.getPopedomSpecialtyIds(treeParam.getTeacherId());
            	List<Specialty> specialties = new ArrayList<Specialty>();
            	for(Specialty sp : list){
            		if(ids.contains(sp.getId()))
            			specialties.add(sp);
            	}
            	list = specialties;
            }
            
            List<XLoadTreeItem> _items = specialtyTreeItemConverter.buildTreeItems(list, treeParam);
            items.addAll(_items);
        }

        return buildTreeXml(items);
    }
    
    /**
     * 点击专业节点，展示专业方向
     * 
     * @return
     * @throws Exception
     */
    public String expandSpecialtyPoint() throws Exception {
        List<SpecialtyPoint> points = specialtyPointService
                .getPointsBySpecialtyIdWithoutNone(treeParam.getSpecialtyId(),!treeParam.isShowValid());
        if ((points.size() > 0 || treeParam.isShowNoneSpecialtyPoint())
                && treeParam.isShowSpecialtyPoint()) {// 展现方向节点
            List<SpecialtyPoint> list = specialtyPointService.getPointsBySpecialtyId(treeParam
                    .getSpecialtyId(),!treeParam.isShowValid());
            
            List<String> ids = new ArrayList<String>();
            if (subsystemPopedomService != null && treeParam.isOnlyShowPopedom()){
            	ids = subsystemPopedomService.getPopedomSpecialtyPointIds(treeParam.getTeacherId());
            	List<SpecialtyPoint> specialties = new ArrayList<SpecialtyPoint>();
            	for(SpecialtyPoint spp : list){
            		if(ids.contains(spp.getSpecialtyId() + spp.getId()))
            			specialties.add(spp);
            	}
            	list = specialties;
            }
            
            List<XLoadTreeItem> items = specialtyPointTreeItemConverter.buildTreeItems(list,
                    treeParam);
            return buildTreeXml(items);
        } else {
            if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
                    .getTreeDepth(EisuTreeConstant.ITEMTYPE_GRADE)) {
                return expandGrade();
            } else {
                // 如果到了公共树的展现层次外，还有下级业务节点，则展现下级节点
                if (treeParam.getLayer() == EisuTreeConstant.ITEMTYPE_SPECIALTY_POINT
                        && StringUtils.isNotEmpty(treeParam.getCustomXmlSrc())) {
                    String likeParams = "specialtyId=" + treeParam.getSpecialtyId() + "&unitId="
                            + treeParam.getUnitId();
                    getResponse().sendRedirect(
                            specialtyPointTreeItemConverter.wrapXmlSrc(treeParam.getCustomXmlSrc(),
                                    likeParams, treeParam));
                }

            }
        }
        
        return buildTreeXml(null);// 空节点
    }

    /**
     * 点击专业或专业方向节点，展示年级
     * 
     * @return
     * @throws Exception
     */
    public String expandGrade() throws Exception {
        if (treeParam.isShowGrade()) {// 展现年级节点
            List<EisuGrade> list = eisuGradeService.getGrades(treeParam.getUnitId());
            if (subsystemPopedomService != null && treeParam.isOnlyShowPopedom()){
            	List<String> listOfAcadyear =subsystemPopedomService.getPopedomAcadyear(treeParam.getTeacherId(), treeParam.getUnitId());
	            List<EisuGrade> l = new ArrayList<EisuGrade>();
	            for(EisuGrade eg : list){
	            	if(listOfAcadyear.contains(treeParam.getSpecialtyId() + (treeParam.getSpecialtyPointId() == null ? BaseConstant.ZERO_GUID : treeParam.getSpecialtyPointId()) + eg.getAcadyear())){
	            		l.add(eg);
	            	}
	            }
	            list = l;
            }
            List<XLoadTreeItem> items = eisuGradeTreeItemConverter.buildTreeItems(list, treeParam);
            return buildTreeXml(items);
        } else {
            if (EisuTreeConstant.getTreeDepth(treeParam.getLayer()) >= EisuTreeConstant
                    .getTreeDepth(EisuTreeConstant.ITEMTYPE_CLASS)) {
                return expandClass();
            } else {
                // 如果到了公共树的展现层次外，还有下级业务节点，则展现下级节点
                if (treeParam.getLayer() == EisuTreeConstant.ITEMTYPE_GRADE
                        && StringUtils.isNotEmpty(treeParam.getCustomXmlSrc())) {
                    String likeParams = "specialtyId="
                            + treeParam.getSpecialtyId()
                            + "&specialtyPointId="
                            + (treeParam.getSpecialtyPointId() == null ? "" : treeParam
                                    .getSpecialtyPointId());
                    getResponse().sendRedirect(
                            eisuGradeTreeItemConverter.wrapXmlSrc(treeParam.getCustomXmlSrc(),
                                    likeParams, treeParam));
                }

            }
        }

        return buildTreeXml(null);// 空节点
    }

    /**
     * 点击年级节点，展示班级
     * 
     * @return
     * @throws Exception
     */
    public String expandClass() throws Exception {
    	List<EisuClass> list = null;
    	if(TreeConstant.TREETYPE_CLASS_GRADUATING==treeParam.getTreeType()){
    		if(StringUtils.isBlank(treeParam.getOpenAcadyear())){
    			list = eisuClassService.getGraduatingClassesBySpecialty(treeParam.getSpecialtyId(),
    					treeParam.getSpecialtyPointId(), treeParam.getGraduateAcadyear());
    		}else{
    			list = eisuClassService.getGraduatingClassesBySpecialty(treeParam.getSpecialtyId(),
    					treeParam.getSpecialtyPointId(), treeParam.getGraduateAcadyear(), treeParam.getOpenAcadyear());
    		}
    	}else{
    		//TODO
    		if(!treeParam.isShowPreGraduateCls() && StringUtils.isNotBlank(treeParam.getAcadyear())){
    			String taid = null;
    			taid = treeParam.getParentId();
    			list = eisuClassService.getClassByAcadyearTeaId(this.getUnitId(), taid, treeParam.getAcadyear(), treeParam.getTeacherId());
    		} else if (StringUtils.isNotBlank(treeParam.getParentId())
    				&& StringUtils.isNotBlank(treeParam.getOpenAcadyear())) {
    			// 校区和年级 -> 班级
    			String taid = null;
    			taid = treeParam.getParentId();
    			list = eisuClassService.getClassesByTeachAreaIdAndOpenAcadyear(
    					getUnitId(), taid, treeParam.getOpenAcadyear());
    		} else {
    			list = eisuClassService.getClassesBySpecialtyId(treeParam.getSpecialtyId(),
                    treeParam.getSpecialtyPointId(), treeParam.getOpenAcadyear(), treeParam
                            .isGraduateClass());
    		}
    	}
    	
    	 if (subsystemPopedomService != null && treeParam.isOnlyShowPopedom()){
         	List<String> listOfClassId =subsystemPopedomService.getPopedomClassIds(treeParam.getTeacherId());
	            List<EisuClass> l = new ArrayList<EisuClass>();
	            for(EisuClass ec : list){
	            	if(listOfClassId.contains(ec.getId())){
	            		l.add(ec);
	            	}
	            }
	            list = l;
         }
    	 
        List<XLoadTreeItem> items = eisuClassTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);
    }


    /**
     * 点击班级节点，展示学生
     * 
     * @return
     * @throws Exception
     */
    public String expandStudent() throws Exception {
        List<EisuStudent> list = eisuStudentService.getStudents(treeParam.getClassId());
      //排序，留级的学生排在后面
        if(CollectionUtils.isNotEmpty(list)){
        	SortUtils<EisuStudent> sort = new SortUtils<EisuStudent>();
        	sort.sort(list, "getId", list.get(0).getSchid());
        }
        List<XLoadTreeItem> items = eisuStudentTreeItemConverter.buildTreeItems(list, treeParam);
        return buildTreeXml(items);

    }
    
    public void setSpecialtyService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public void setSpecialtyPointService(SpecialtyPointService specialtyPointService) {
        this.specialtyPointService = specialtyPointService;
    }
    
    public void setEisuGradeService(EisuGradeService eisuGradeService) {
        this.eisuGradeService = eisuGradeService;
    }

    public void setEisuClassService(EisuClassService eisuClassService) {
        this.eisuClassService = eisuClassService;
    }

    public void setEisuStudentService(EisuStudentService eisuStudentService) {
        this.eisuStudentService = eisuStudentService;
    }

    public void setSpecialtyTreeItemConverter(
            XLoadTreeItemConverter<Specialty> specialtyTreeItemConverter) {
        this.specialtyTreeItemConverter = specialtyTreeItemConverter;
    }   

    public void setSpecialtyPointTreeItemConverter(
            XLoadTreeItemConverter<SpecialtyPoint> specialtyPointTreeItemConverter) {
        this.specialtyPointTreeItemConverter = specialtyPointTreeItemConverter;
    }

    public void setEisuGradeTreeItemConverter(
            XLoadTreeItemConverter<EisuGrade> eisuGradeTreeItemConverter) {
        this.eisuGradeTreeItemConverter = eisuGradeTreeItemConverter;
    }

    public void setEisuClassTreeItemConverter(
            XLoadTreeItemConverter<EisuClass> eisuClassTreeItemConverter) {
        this.eisuClassTreeItemConverter = eisuClassTreeItemConverter;
    }

    public void setEisuStudentTreeItemConverter(
            XLoadTreeItemConverter<EisuStudent> eisuStudentTreeItemConverter) {
        this.eisuStudentTreeItemConverter = eisuStudentTreeItemConverter;
    }

}
