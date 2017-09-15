package net.zdsoft.eis.frame.action;

import com.opensymphony.xwork2.ModelDriven;



/* 
 * 实现了ModelDriven的Action
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: ModelBaseAction.java,v 1.1 2006/09/13 09:16:32 dongzk Exp $
 */
public class ModelBaseAction extends BaseAction implements ModelDriven<Object>  {
    private static final long serialVersionUID = 1L;

    public Object getModel() {
		return this;
	}
}



