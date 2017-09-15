package net.zdsoft.leadin.chinaexcel;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * 超级报表xml文件转码
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 29, 2010 7:47:10 PM $
 */
public class ChinaexcelResult extends FreemarkerResult {
    private static final long serialVersionUID = 1L;

    private String encoding;

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    protected boolean preTemplateProcess(Template template, TemplateModel model) throws IOException {
        Object attrContentType = template.getCustomAttribute("content_type");

        if (attrContentType != null) {
            ServletActionContext.getResponse().setContentType(attrContentType.toString());
        } else {
            String contentType = getContentType();

            if (contentType == null) {
                contentType = "text/html";
            }

            // String encoding = template.getEncoding();
            //
            // if (encoding != null) {
            // contentType = contentType + "; charset=" + encoding;
            // }
            
            contentType = contentType + "; charset=" + encoding;

            ServletActionContext.getResponse().setContentType(contentType);
        }

        return true;
    }

}
