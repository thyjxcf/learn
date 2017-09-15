package net.zdsoft.eis.base.data.action;

import net.zdsoft.eis.frame.action.BaseAction;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: CodeRuleSetAdminAction.java,v 1.3 2006/12/29 06:58:42 linqz Exp $
 */
public class CodeRuleSetAdminAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4114611878050042433L;
    private String codeType; // 供查询用
    private boolean schoolUserType = false;
    private boolean initCodeRule = false;

    public boolean isSchoolUserType() {
        if (getLoginInfo().getUnitClass() == 2) {
            schoolUserType = true;
        } else {
            schoolUserType = false;
        }
        return schoolUserType;
    }

    public String execute() throws Exception {
        log.info("stuCodeRuleSetAdmin action: execute()......");

        return SUCCESS;
    }

    public boolean isInitCodeRule() {
        return initCodeRule;
    }

    public void setInitCodeRule(boolean initCodeRule) {
        this.initCodeRule = initCodeRule;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

  

}
