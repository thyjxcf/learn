package net.zdsoft.leadin.dataimport.action;

import java.io.BufferedOutputStream;

import javax.servlet.http.HttpServletResponse;

import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.leadin.dataimport.template.ExcelTemplateUtil;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

public class ExcelErrorDataResult implements Result {

    private static final long serialVersionUID = 1323416331183484238L;

    public void execute(ActionInvocation arg0) throws Exception {
        BufferedOutputStream out = null;
        try {
            ValueStack stack = ActionContext.getContext().getValueStack();
            HttpServletResponse response = ServletActionContext.getResponse();
            out = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("Cache-Control", "");
            response.setContentType("application/x-msdownload");
            ExcelTemplateUtil excelTemplateUtil = (ExcelTemplateUtil) stack
                    .findValue("excelTemplateUtil");
            String fileName = excelTemplateUtil.getFileName();
            if (null == fileName)
                fileName = "ErrInfo.xls";
            else
                fileName += "Err.xls";

            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLUtils.encode(fileName, "UTF-8"));
            excelTemplateUtil.writeErrorFile(out);
        } finally {
            out.flush();
            out.close();
        }
    }
}
