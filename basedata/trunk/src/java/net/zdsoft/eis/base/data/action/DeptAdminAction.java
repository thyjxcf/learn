package net.zdsoft.eis.base.data.action;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.service.BaseDeptService;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.util.AssembleTool;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.opensymphony.xwork2.ModelDriven;

public class DeptAdminAction extends BaseAction implements ModelDriven<Dept> { 

    /**
     * 
     */
    private static final long serialVersionUID = 8142562576488966100L;

    private String topDeptId = Dept.TOP_GROUP_GUID;

    private String topDeptName = Dept.TOP_GROUP_NAME;

    private Integer deptIdLength = 6;

    private String modID = "SYS003";

    public Dept dept = new Dept();

    public List<Dept> deptList;

    public String deptId;

    public List<User> userList;

    private LoginInfo loginInfo;

    private Dept parentDept;

    private BaseDeptService baseDeptService;

    private TeacherService teacherService;

    private UserService userService;

    private boolean hasSubDept;

    public boolean isHasSubDept() {
        return hasSubDept;
    }

    public void setHasSubDept(boolean hasSubDept) {
        this.hasSubDept = hasSubDept;
    }

    public String execute() {
        loginInfo = getLoginInfo();
        dept.setUnitId(loginInfo.getUser().getUnitid());

        return SUCCESS;

    }

    public String doDeptList() {
        // 得到当前部门，用于页面判断是否“学校管理员组”
        if (deptId == null || "".equals(deptId) || deptId.equals(Dept.TOP_GROUP_GUID)) {
            dept.setDeptname("");
        } else {
            dept = baseDeptService.getDept(deptId);
        }
        if (dept == null) {
            dept = new Dept();
        }
        loginInfo = getLoginInfo();
        deptList = baseDeptService.getDepts(dept.getUnitId(), deptId);
        Map<String, User> map = userService.getUserMap(dept.getUnitId());
        for (int i = 0; i < deptList.size(); i++) {
            Dept gd = (Dept) deptList.get(i);
            if (gd.getPrincipan() != null) {
                User u = map.get(gd.getPrincipan().toString());
                if (u != null) {
                        gd.setPrincipanname(u.getRealname());
                }
            }
        }
        return SUCCESS;

    }

    public String getDeptNew() {
        loginInfo = getLoginInfo();
        deptList = baseDeptService.getDepts(dept.getUnitId());
        userList = userService.getUsers(dept.getUnitId());
        dept = baseDeptService.getNewDept(deptId, loginInfo.getUnitID());
        if (dept.getDeptCode().equals("000000")) {
            addFieldError("deptid", "请确认部门编号已确实使用到了999999，如没有的话建议您修改不要使用数值过大的作为部门编号！");
        }
        return SUCCESS;

    }

    public String getDeptEdit() {
        dept = baseDeptService.getDept(dept.getId());
        List<Dept> list = baseDeptService.getDeptsByParentId(dept.getId());
        hasSubDept = true;
        if ((list == null) || (list.size() == 0)) {
            hasSubDept = false;
        }
        if (!dept.getParentid().equals(Dept.TOP_GROUP_GUID)) {
            parentDept = baseDeptService.getDept(dept.getParentid());
            dept.setParentName(parentDept.getDeptname());
        } else {
            dept.setParentName(topDeptName);
        }
        dept.setPreParentId(dept.getParentid());
        userList = userService.getUsers(dept.getUnitId());
        return SUCCESS;
    }

    public String setDeptUpdate() {        
        promptMessageDto = new PromptMessageDto();
        loginInfo = getLoginInfo();
        String unitId = loginInfo.getUser().getUnitid();
        if (!deptValidate(dept)) {
            deptList = baseDeptService.getDepts(unitId);
            userList = userService.getUsers(unitId);
            deptId = dept.getParentid();
            return returnResult(dept);
        }

        if (dept.getParentid().equals(dept.getId())) {
            deptList = baseDeptService.getDepts(unitId);
            userList = userService.getUsers(unitId);
            deptId = dept.getParentid();
            addFieldError("parentName", "请不要选择自己作为上级部门");
            return returnResult(dept);
        }

        if (dept.getParentName().equals(Dept.SCH_ADMIN_GROUP_NAME)
                || dept.getParentName().equals(Dept.EDU_ADMIN_GROUP_NAME)) {
            deptList = baseDeptService.getDepts(unitId);
            userList = userService.getUsers(unitId);
            deptId = dept.getParentid();
            // addFieldError("parentName", GlobalConstant.SCH_ADMIN_GROUP_NAME
            // +" 不能作为上级部门，请选择其他部门！");
            addFieldError("parentName", "请不要选择 " + dept.getParentName() + " 作为上级部门");
            return returnResult(dept);
        }

        Dept parentDept = baseDeptService.getDept(dept.getParentid());
        if (dept.getParentid() != null
                && !dept.getParentid().equals(Dept.TOP_GROUP_GUID)) {
            /**
             * 科室类型验证，教研室的节点只能为叶子，下面不能再加其他节点。
             */
            if (parentDept != null && parentDept.getJymark() != null) {
                if (parentDept == null || parentDept.getJymark() == Dept.STUFF_ROOM_MARK) {
                    deptList = baseDeptService.getDepts(unitId);
                    userList = userService.getUsers(unitId);
                    deptId = dept.getParentid();
                    addFieldError("parentName", "教研室标识为教研室的部门不能新增下级部门");
                    return returnResult(dept);
                }
            }
        }

        String logMsg = "";
        if (dept.getId() == null || dept.getId().length() == 0) {
            logMsg = "新增";
        } else {
            logMsg = "修改";
        }

        try {
            dept.setCreationTime(new Date());
            baseDeptService.saveDept(dept);

            String js;
            if (dept.getParentid().equals(Dept.TOP_GROUP_GUID)
                    || dept.getPreParentId().equals(Dept.TOP_GROUP_GUID)
                    || (dept.getPreParentId().length() > 0 && !dept.getParentid().equals(
                            dept.getPreParentId()))) {
                // 对于修改上级部门至顶级部门,或者原顶级部门修正至其他部门,或者上级部门变更的情况下,部门树的刷新
                promptMessageDto
                        .addOperation(new String[] {
                                "确定",
                                "deptAdmin-list.action?deptId=" + dept.getParentid() + "&&unitId="
                                        + dept.getUnitId() + "&&ec_p=" + getEc_p() + "&&ec_crd="
                                        + getEc_crd(),
                                "parent.frames['deptTreeFrame'].window.location.href=parent.frames['deptTreeFrame'].window.location.href" });
            } else {
                promptMessageDto.addOperation(new String[] {
                        "确定",
                        "deptAdmin-list.action?deptId=" + dept.getParentid() + "&&unitId="
                                + dept.getUnitId() + "&&ec_p=" + getEc_p() + "&&ec_crd="
                                + getEc_crd() });
                js = "parent.frames['deptTreeFrame'].selectRefresh('" + parentDept.getDeptname()
                        + "');";
                promptMessageDto.setJavaScript(js);
            }

            SystemLog.log(modID, logMsg + dept.getDeptname() + "部门信息成功！");

            promptMessageDto.setPromptMessage("保存" + dept.getDeptname() + "部门信息成功！");
            promptMessageDto.setOperateSuccess(true);
            return PROMPTMSG;
        } catch (ItemExistsException e) {
            addFieldError(e.getField(), e.getMessage());

            deptList = baseDeptService.getDepts(unitId);
            userList = userService.getUsers(unitId);
            deptId = dept.getParentid();
            return returnResult(dept);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            addActionError("保存部门信息出错：" + e.getMessage());
            SystemLog.log(modID, logMsg + dept.getDeptname() + "部门信息失败!");

            deptList = baseDeptService.getDepts(unitId);
            userList = userService.getUsers(unitId);
            deptId = dept.getParentid();
            return returnResult(dept);
        }
    }

    public String setDeptDelete() {
        String unitId = dept.getUnitId();
        promptMessageDto = new PromptMessageDto();
        String deptNames = "";
        try {
            String[] ids = dept.getArrayIds();
            Map<String, Integer> employeeCounts = teacherService.getCountInDept(ids);
            Map<String, Integer> subDeptCounts = baseDeptService.getDeptCount(ids);
            Map<String, Dept> deptMap = baseDeptService.getDeptMap(ids);

            if (deptMap == null || subDeptCounts == null) {
                promptMessageDto.setErrorMessage("删除部门失败,部门不存在或已删除");
                promptMessageDto.addOperation(new String[] { "返回",
                        "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId });
                return PROMPTMSG;
            }

            String[] deptNameStr = new String[ids.length];
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    dept = deptMap.get(ids[i]);
                    int employnum = 0;
                    int deptnum = 0;
                    if (null != employeeCounts && employeeCounts.containsKey(ids[i])) {
                        employnum = employeeCounts.get(ids[i]);
                    }
                    if (null != subDeptCounts && subDeptCounts.containsKey(ids[i])) {
                        deptnum = subDeptCounts.get(ids[i]);
                    }
                    deptNameStr[i] = dept.getDeptname();
                    if ( employnum > 0) {
                        promptMessageDto.setPromptMessage(dept.getDeptname() + "部门尚有职工,不能删除");
                        promptMessageDto.setOperateSuccess(false);
                        promptMessageDto.addOperation(new String[] { "确定",
                                "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId });
                        return PROMPTMSG;
                    } else if (deptnum > 0) {
                        promptMessageDto.setPromptMessage(dept.getDeptname() + "部门尚有下属部门，不能删除");
                        promptMessageDto.setOperateSuccess(false);
                        promptMessageDto.addOperation(new String[] { "确定",
                                "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId });
                        return PROMPTMSG;
                    }
                }
            }
            deptNames = AssembleTool.getAssembledString(deptNameStr, "、");
            baseDeptService.deleteDept(ids);

            promptMessageDto.setPromptMessage("删除" + deptNames + "成功！");

            promptMessageDto.setOperateSuccess(true);
            if (deptId.equals(Dept.TOP_GROUP_GUID)) {
                promptMessageDto
                        .addOperation(new String[] { "确定",
                                "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId,
                                "parent.frames['deptTreeFrame'].location.href=parent.frames['deptTreeFrame'].location.href" });
            } else {
                promptMessageDto.addOperation(new String[] { "确定",
                        "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId });
                String js = "parent.frames['deptTreeFrame'].selectRefresh();";
                promptMessageDto.setJavaScript(js);
            }
            SystemLog.log(modID, "删除" + deptNames + "部门成功！");
            return PROMPTMSG;
        } catch (Exception e) {
            e.printStackTrace();
            promptMessageDto.setErrorMessage("删除" + deptNames + "部门失败！");
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.addOperation(new String[] { "确定",
                    "deptAdmin-list.action?deptId=" + deptId + "&&unitId=" + unitId });
            SystemLog.log(modID, "删除" + deptNames + "部门失败！");
            return PROMPTMSG;
        }
    }

    public Dept getModel() {
        return dept;
    }

    public void setBaseDeptService(BaseDeptService baseDeptService) {
        this.baseDeptService = baseDeptService;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTeacherService(TeacherService employeeService) {
        this.teacherService = employeeService;
    }

    public static boolean isContainsEspecial(String content) {
        // 如果是存在[].在字符中需用\转义
        // String regularExpression="[@\"*&%$#!$'?/>:;{}<,~`-+\\.\\[\\]]";
        // //"[@\"*&%$#!$']"
        String regularExpression = "[:/@\"*&%$,#;!$'-+\\.\\[\\]]"; // "[@\"*&%$#!$']"
        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern;
        try {
            pattern = compiler.compile(regularExpression);
            PatternMatcher matcher = new Perl5Matcher();
            return matcher.contains(content, pattern);
        }
        catch (MalformedPatternException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    /**
     * 部门信息的验证
     * 
     * @param dept
     * @return
     */
    private boolean deptValidate(Dept dept) {
        if (org.apache.commons.lang.StringUtils.isBlank(dept.getDeptname())) {
            addFieldError("deptname", "请输入部门名称");
            return false;
        } else if (net.zdsoft.keel.util.StringUtils.getRealLength(dept.getDeptname()) > Dept.DEPTNAME_LENGTH) {
            addFieldError("deptname", "请确认部门名称不超过" + Dept.DEPTNAME_LENGTH + "个字符");
            return false;
        } else if (isContainsEspecial(dept.getDeptname())) {
            addFieldError("deptname", "部门名称存在特殊字符,请重新输入");
            return false;
        } else if (dept.getJymark() == null) {
            addFieldError("jymark", "请选择教研组标识");
            return false;
        } else if (dept.getDeptCode() == null
                || dept.getDeptCode().trim().length() != Dept.DEPTID_LENGTH
                || !org.apache.commons.lang.StringUtils.isNumeric(dept.getDeptCode())) {
            addFieldError("deptCode", "请确认部门编号长度为" + Dept.DEPTID_LENGTH + "位整数");
            return false;
        } else if (dept.getParentid() == null || dept.getParentid().trim().length() == 0) {
            addFieldError("parentName", "请选择上级部门");
            return false;
        } else if (!dept.getParentid().equals(Dept.TOP_GROUP_GUID)
                && baseDeptService.getDept(dept.getParentid()) == null) {
            addFieldError("parentName", "该上级部门暂时不可用,请重新选择");
            return false;
        } else if (!org.apache.commons.lang.StringUtils.isNumeric(String.valueOf(dept
                .getOrderid()))
                || dept.getOrderid() < 0
                || dept.getOrderid() >= Math.pow(10, Dept.ORDERID_LENGTH)) {
            addFieldError("orderid", "请确认部门排序号为不大于" + Dept.ORDERID_LENGTH + "位的整数");
            return false;
        } else if (dept.getDepttel() != null && dept.getDepttel().trim().length() != 0
                && !Validators.isPhoneNumber(dept.getDepttel())) {
            addFieldError("deptTel", "请确认部门电话号码填写正确");
            return false;
        } else if (net.zdsoft.keel.util.StringUtils.getRealLength(dept.getAbout()) > Dept.ABOUT_LENGTH) {
            addFieldError("about", "请确认描述信息不超过" + Dept.ABOUT_LENGTH + "个字符");
            return false;
        }
        return true;
    }

    /**
     * 对于修改和新增的不同页面跳转
     * 
     * @param dept
     * @return
     */
    private String returnResult(Dept dept) {
        if (dept.getId() == null) {
            return INPUT;
        } else {
            return ERROR;
        }
    }

    public String getDeptTree() {
        return SUCCESS;
    }

    public String getTopDeptName() {
        return topDeptName;
    }

    public Integer getDeptIdLength() {
        return deptIdLength;
    }

    public String getTopDeptId() {
        return topDeptId;
    }

    public List<Dept> getDeptList() {
        return deptList;
    }

}
