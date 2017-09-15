/**
 * 报表控件
 */
package net.zdsoft.eis.frame.action;

/**
 * <p>
 * ZDSoft学籍系统（stusys）V3.5
 * </p>
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: ChinaExcelAction.java,v 1.2 2006/10/12 07:07:25 zhengyy Exp $
 */

public class ChinaExcelAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    boolean onload = true; // 是否执行chinaexcelreport.ftl里body的onload事件

    public String execute() throws Exception {
        return SUCCESS;
    }

    public boolean isOnload() {
        return onload;
    }

    public void setOnload(boolean onload) {
        this.onload = onload;
    }

}
