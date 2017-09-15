package net.zdsoft.eis.frame.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.dto.JSONMessageDto;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseActionSupport extends ActionSupport implements ResultNameAction {
    private static final long serialVersionUID = -1680215748066884557L;

    protected transient final Logger log = LoggerFactory.getLogger(getClass());
    
    // servlet对象
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    // =====================http参数=======================
    protected Object getSession(String parm) {
        return ActionContext.getContext().getSession().get(parm);
    }

    protected void setSession(String parm, Object obj) {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> params = context.getSession();
        params.put(parm, obj);
        context.setSession(params);
    }

    // 得到request
    public HttpServletRequest getRequest() {
        this.request = ServletActionContext.getRequest();
        return this.request;
    }

    // 得到response
    public HttpServletResponse getResponse() {
        this.response = ServletActionContext.getResponse();
        return response;
    }

    public ServletContext getServletContext() {
        return ServletActionContext.getServletContext();
    }

    public String getContextPath() {
        return ServletActionContext.getRequest().getContextPath();
    }

    /**
     * 重写了了这个方法，用于去除通过xwork的validator方法，输入类型和dto中不一致，导致系统报错的信息
     
    @SuppressWarnings("unchecked")
    public Map getFieldErrors() {
        Map<String, List<String>> map = super.getFieldErrors();
        Set<String> setOfKey = map.keySet();
        List<String> listOfError;
        String errorMsg = "invalid field value for field";
        for (String key : setOfKey) {
            if (map.get(key) instanceof List) {
                listOfError = map.get(key);
                if (listOfError.size() > 1) {
                    if (listOfError.get(0).toLowerCase().indexOf(errorMsg) == 0) {
                        listOfError.remove(0);
                    }
                }
            }
        }
        return map;
    }
     */
    
    // =====================分页参数=======================
    /**
     * 分页数据，当前页码
     */
    protected String ec_p;

    /**
     * 分页数据，每页size
     */
    protected String ec_crd;

    /**
     * 总页数
     */
    protected String totalPage;

    public String getEc_crd() {
        return ec_crd;
    }

    public String getEc_p() {
        return ec_p;
    }

    public void setEc_crd(String ec_crd) {
        this.ec_crd = ec_crd;
    }

    public void setEc_p(String ec_p) {
        this.ec_p = ec_p;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    // =====================以下为get/set方法=======================

    // 页面上提示信息封装对象
    protected PromptMessageDto promptMessageDto = new PromptMessageDto();
    protected String reportXmlData;// 报表数据
    private int moduleID;
    protected int appId = 0;
    protected int platform = BaseConstant.PLATFORM_TEACHER;
    protected String jsonError;
    protected JSONMessageDto jsonMessageDto = new JSONMessageDto();

	/**
     * 取当前年份
     * 
     * @return
     */
    public String getCurrentYear() {
        Date today = new Date();
        String year = DateUtils.date2String(today, "yyyy");
        return year;
    }

    // 传到页面的提示信息对象
    public PromptMessageDto getPromptMessageDto() {
        return promptMessageDto;
    }

    public void setPromptMessageDto(PromptMessageDto promptMessageDto) {
        this.promptMessageDto = promptMessageDto;
    }

    public String getReportXmlData() {
        return reportXmlData;
    }

    public void setReportXmlData(String reportXmlData) {
        this.reportXmlData = reportXmlData;
    }


    public String getJsonError() {
		return jsonError;
	}

	public JSONMessageDto getJsonMessageDto() {
		return jsonMessageDto;
	}

	public void setJsonMessageDto(JSONMessageDto jsonMessageDto) {
		this.jsonMessageDto = jsonMessageDto;
	}
	
	/**
     * 取页面title
     * 
     * @return 应用系统的title
     */
    public String getWebAppTitle() {
        return SubSystem.getTitle(appId);
    }

    /**
     * 取页面title
     * 
     * @return 应用系统名称 + 模块名称
     */
    public String getWebModuleTitle() {
        return SubSystem.getTitle(appId);// TODO 待扩展;
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public int getAppId() {
    	if(this.getSession("appId")==null){
    		return appId;
    	}
        return Integer.valueOf(this.getSession("appId").toString());
    }

    public void setAppId(int appId) {
        this.appId = appId;
        this.setSession("appId",appId);
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
    
    public String getStoreHome() {
        return BootstrapManager.getStoreHome();
    }
}
