package net.zdsoft.eis.base.tree.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.tree.TreeConstant;
import net.zdsoft.eis.base.tree.service.TreeService;

import org.apache.commons.collections.CollectionUtils;

/**
 * <p> * ZDSoft学籍系统(stusys)V3.5 *
 * </p>
 * <p> * 教育局树的action *
 * </p>
 * 
 * @author Zhanghh
 * @version $Revision: 1.2 $
 * @since 1.5 $Id: EduTreeAction.java,v 1.2 2006/12/06 12:37:24 zhanghh Exp $
 */
public class EduTreeAction extends BaseTreeModelAction {

    private static final long serialVersionUID = 8040272990430562433L;

    private TreeService eduTreeService;
    private UnitService unitService;

    private String eduid;
    private String acadyear; // 学年

    private String codeField;
    private String valueField;


    @Override
    protected TreeService getTreeService() {        
        return eduTreeService;
    }
    
    public String execute() throws Exception {
        return SUCCESS;
    }

    /**
     * 教育局－学校的树,显示下属教育局
     * 
     * @return
     * @throws Exception
     */
    public String eduToSchoolTree() throws Exception {
        if (eduid == null || "".equals(eduid))
            eduid = this.getLoginInfo().getUnitID();
        treeParam.setUnitId(eduid);
        
        treeParam.setShowEdu(true);
        treeParam.setShowSch(true);
        treeParam.setLayer(TreeConstant.ITEMTYPE_SCHOOL);
        return buildTree();
    }

    /**
     * 教育局－学区－学校的树,显示下属教育局
     * 
     * @return
     * @throws Exception
     */
    public String eduToXqToSchTree() throws Exception {
        if (eduid == null || "".equals(eduid))
            eduid = this.getLoginInfo().getUnitID();

        treeParam.setUnitId(eduid);
        treeParam.setShowEdu(true);
        treeParam.setShowSch(true);
        treeParam.setShowSchDistrict(true);
        treeParam.setLayer(TreeConstant.ITEMTYPE_SCHOOL);
        return buildTree();
    }

    /**
     * 教育局－学区的树,显示下属教育局
     * 
     * @return
     * @throws Exception
     */
    public String eduToXqTree() throws Exception {
        String unitId = getLoginInfo().getUnitID();
        // 只有顶级单位不是全国教育部的时候，这里显示的是操作者所在省的学区。
        if (!unitId.equalsIgnoreCase(eduid)) {
            Unit topUnit = unitService.getUnit(eduid);
            if (topUnit.getRegion().equals(Unit.TOP_UNIT_REGION_CODE)) {
                Unit unit = unitService.getUnit(unitId);
                String unionId = unit.getUnionid();
                List<Unit> list = unitService.getUnitsByUnionCode(unionId.substring(0, 2),
                        Unit.UNIT_CLASS_EDU);
                if (CollectionUtils.isNotEmpty(list)) {
                    eduid = list.get(0).getId();
                }
            }
        }
        if (eduid == null || "".equals(eduid)) {
            eduid = this.getLoginInfo().getUnitID();
        }
        
        treeParam.setUnitId(eduid);
        treeParam.setLayer(TreeConstant.ITEMTYPE_XQ);
        treeParam.setShowEdu(true);
        treeParam.setShowSchDistrict(true);
        buildTree();

        return SUCCESS;
    }

    /**
     * 教育局－下属教育局的树
     * 
     * @return
     * @throws Exception
     */
    public String eduToSubEduTree() throws Exception {
        if (eduid == null || "".equals(eduid))
            eduid = this.getLoginInfo().getUnitID();

        treeParam.setUnitId(eduid);
        treeParam.setShowEdu(true);
        treeParam.setLayer(TreeConstant.ITEMTYPE_EDUCATION);     
        return buildTree();
    }

    public String getEduid() {
        return eduid;
    }

    public void setEduid(String eduid) {
        this.eduid = eduid;        
    }

    public void setEduTreeService(TreeService eduTreeService) {
        this.eduTreeService = eduTreeService;
    }

    public String getAcadyear() {
        return acadyear;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getCodeField() {
        return codeField;
    }

	public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }  

}
