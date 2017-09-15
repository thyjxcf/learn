package net.zdsoft.eis.base.tree.action;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.service.ClassTree;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author lilj
 * @since 1.0
 * @version $Id: ClassTreeAction.java,v 1.9 2006/11/16 09:56:34 zhanghh Exp $
 */

public class ClassTreeAction extends BaseTreeAction {
    private static final long serialVersionUID = 1L;

    private SemesterService semesterService;
    private ClassTree classTreeService;// 班级树service
    private SubsystemPopedomService stusysPopedomService;// 各子系统权限

    /**
     * 学校guid 班级树的类型TreeConstant 中TREETYPE_CLASS* 学年（格式为：2005-2006，主要用于毕业班结构树）
     */
    private String schid;
    private String treeType;
    private String graduateAcadyear;
    private String graduatesign; // 已毕业标志

    /**
     * 是否需要数据权限控制。需要时传入true 在需要控制的情况下，再判别平台参数设置中的“是否启用学籍数据权限”
     */
    private boolean needPopedom;

    /**
     * 是否所有的树结点名称点击都需要开放链接。
     */
    private boolean allLinkOpen;

    private String acadyear;// 学年

    public ClassTreeAction() {
    }

    public String execute() throws Exception {
        if (this.schid == null || this.schid.trim().length() == 0) {
            schid = getLoginInfo().getUnitID();
        }

        if (needPopedom == true && null == stusysPopedomService) {
            log.error("popedomService must be not null");
            return SUCCESS;
        }

        if(StringUtils.isBlank(acadyear)){
            acadyear = semesterService.getCurrentAcadyear();
        }
        
        classTreeService.setContextPath(getContextPath());
        treeJSCode = classTreeService
                .getXTree(schid, allLinkOpen, treeType, graduateAcadyear, graduatesign,
                        needPopedom, getLoginInfo().getUser(), stusysPopedomService, acadyear);
        return SUCCESS;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

    public String getGraduateAcadyear() {
        return graduateAcadyear;
    }

    public void setGraduateAcadyear(String graduateAcadyear) {
        this.graduateAcadyear = graduateAcadyear;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public void setClassTreeService(ClassTree classTreeService) {
        this.classTreeService = classTreeService;
    }

    public String getGraduatesign() {
        return graduatesign;
    }

    public void setGraduatesign(String graduatesign) {
        this.graduatesign = graduatesign;
    }

    public void setNeedPopedom(String needPopedom) {
        this.needPopedom = ("true".equals(needPopedom.toLowerCase())) ? true : false;
    }

    public void setAllLinkOpen(String allLinkOpen) {
        this.allLinkOpen = ("true".equals(allLinkOpen.toLowerCase())) ? true : false;
    }

    public void setStusysPopedomService(SubsystemPopedomService stusysPopedomService) {
        this.stusysPopedomService = stusysPopedomService;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

}
