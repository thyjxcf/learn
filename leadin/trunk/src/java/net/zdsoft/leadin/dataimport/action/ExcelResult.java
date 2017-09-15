/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author wangsn
 * @since 1.0
 * @version $Id: ExcelResult.java,v 1.6 2007/01/04 08:18:08 linqz Exp $
 */
package net.zdsoft.leadin.dataimport.action;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.leadin.dataimport.template.ExcelTemplateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipOutputStream;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

public class ExcelResult implements Result {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ExcelResult.class);

    public void execute(ActionInvocation arg0) throws Exception {
        OutputStream out = null;
        try {
            ValueStack stack = ActionContext.getContext().getValueStack();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/x-msdownload");
            response.setHeader("Cache-Control", "");
            ExcelTemplateUtil excelTemplateUtil = (ExcelTemplateUtil) stack
                    .findValue("excelTemplateUtil");
            String fileName = excelTemplateUtil.getFileName();
            if (null == fileName)
                fileName = "Template";

            if (excelTemplateUtil.isHasMoreFileData()) {
                fileName += ".zip";
                out = new ZipOutputStream(response.getOutputStream());
            } else {
                fileName += ".xls";
                out = new BufferedOutputStream(response.getOutputStream());
            }

            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLUtils.encode(fileName, "UTF-8"));

            excelTemplateUtil.writeDataFile(out);
        } finally {
            try {
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("关闭流出错");
            }
        }

    }
}
