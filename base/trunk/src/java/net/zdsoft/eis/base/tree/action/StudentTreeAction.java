package net.zdsoft.eis.base.tree.action;


import net.zdsoft.eis.base.subsystemcall.service.SubsystemPopedomService;
import net.zdsoft.eis.base.tree.service.StudentTree;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author lilj
 * @since 1.0
 * @version $Id: StudentTreeAction.java,v 1.10 2006/10/23 03:09:20 luxm Exp $
 */

public class StudentTreeAction extends BaseTreeAction {
    private static final long serialVersionUID = 6411605925079100013L;

    /**
     * 学生树service
     */
    private StudentTree studentTreeService;
    private SubsystemPopedomService popedomService;// 各子系统权限

    private String schid;
    private String graduateAcadyear;// 毕业学年

    /**
     * 是否需要数据权限控制。 在需要控制的情况下，再判别平台参数设置中的“是否启用学籍数据权限”
     */
    private boolean needPopedom;

    /**
     * 树的类别。see TreeConstant 中TREETYPE_*
     */
    private String treeType;

    /**
     * 是否所有的树结点名称点击都需要开放链接。
     */
    private boolean allLinkOpen;

    public void setAllLinkOpen(String allLinkOpen) {
        this.allLinkOpen = ("true".equals(allLinkOpen.toLowerCase())) ? true : false;
    }

    public void setNeedPopedom(String needPopedom) {
        this.needPopedom = ("true".equals(needPopedom.toLowerCase())) ? true : false;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getTreeType() {
        return treeType;
    }

    public StudentTreeAction() {

    }

    public String execute() throws Exception {
        if (this.schid == null || this.schid.trim().length() == 0) {
            schid = getLoginInfo().getUnitID();
        }

        studentTreeService.setContextPath(getContextPath());
        treeJSCode = studentTreeService.getXTree(schid, allLinkOpen, treeType, graduateAcadyear,
                needPopedom, getLoginInfo().getUser(), popedomService);
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

    public void setStudentTreeService(StudentTree studentTreeService) {
        this.studentTreeService = studentTreeService;
    }

    public void setStusysPopedomService(SubsystemPopedomService stusysPopedomService) {
        this.popedomService = stusysPopedomService;
    }
}
