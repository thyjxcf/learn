package net.zdsoft.eis.system.frame.action;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.frame.dto.ResultPack;
import net.zdsoft.eis.system.frame.service.ExceptionDataService;

public class ExceptionDataAction extends BaseAction {

    private static final long serialVersionUID = -6080665402869064668L;

    private ExceptionDataService exceptionDataService;
//    private UserService userService;

    private String taskContent;
    private ResultPack[] resultPacks;
    private Integer[] resultCodes;
    private Integer resultIndex;
    private String password;

    private boolean isSelect = false;
    private boolean isUpdate = false;

    public String execute() {

        return SUCCESS;
    }

    public String executeQuery() {
        if (StringUtils.isBlank(taskContent)) {
            //addActionError("请输入要执行的语法");
            return SUCCESS;
        }
        if (taskContent.indexOf(";") < 0) {
            addActionError("请输入结束符号\";\"");
            return SUCCESS;
        }
        String[] sqls = taskContent.split(";");
        if (null == resultIndex || resultIndex == -1) {
            resultIndex = 0;
        }
        for (String sql : sqls) {
            if (StringUtils.isBlank(sql) || sql.trim().indexOf("--") == 0)
                continue;
            if (sql.trim().toLowerCase().indexOf("select") != 0) {
                isUpdate = true;
                break;
            }
        }
        // 如果是更新删除数据，需要用户密码验证
        if (isUpdate) {
            //不允许再执行修改数据的语法
            addActionError("对不起，您不能执行修改数据的语法");
            return SUCCESS;
            
//            LoginInfo loginInfo = getLoginInfo();
//            String userName = loginInfo.getUser().getName();
//            User user = userService.getUserByUserName(userName);
//            if (user == null) {
//                addActionError("对不起，您没有权限更新删除数据");
//                return SUCCESS;
//            }
//
//            if (password != null && password.length() > 0) {
//                if (!password.equalsIgnoreCase(user.findClearPassword())) {
//                    addActionError("对不起，用户身份验证失败，请重新输入用户密码");
//                    return SUCCESS;
//                }
//            } else {
//                if (user.findClearPassword() == null || user.findClearPassword().trim().length() == 0) {
//
//                } else {
//                    addActionError("对不起，用户身份验证失败，请重新输入用户密码");
//                    return SUCCESS;
//                }
//            }
        }

        try {
            resultPacks = exceptionDataService.saveExecuteQuery(taskContent, resultIndex);
        } catch (SQLException e) {
            log.error("该sql语言不符合语法", e);
            addActionError("该sql语言不符合语法："
                    + e.getMessage().replaceAll("\r\n", ",").replaceAll("\"", "'"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            addActionError(e.getMessage());
            log.error(e.getMessage().replaceAll("\r\n", " ").replaceAll("\"", "'"));
        }

        return SUCCESS;
    }

    public void setExceptionDataService(ExceptionDataService exceptionDataService) {
        this.exceptionDataService = exceptionDataService;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public ResultPack[] getResultPacks() {
        return resultPacks;
    }

    public void setResultIndex(Integer resultIndex) {
        this.resultIndex = resultIndex;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Integer getResultIndex() {
        return resultIndex;
    }

    public Integer[] getResultCodes() {
        return resultCodes;
    }

    public ResultPack getCurrentResultPack() {
        if (resultPacks == null || resultPacks.length <= resultIndex) {
            return null;
        }
        return resultPacks[resultIndex];
    }

//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

}
